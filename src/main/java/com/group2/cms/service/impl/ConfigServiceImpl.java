package com.group2.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group2.cms.annotation.Role;
import com.group2.cms.entity.Config;
import com.group2.cms.exception.ServiceException;
import com.group2.cms.mapper.ConfigMapper;
import com.group2.cms.service.IConfigService;
import com.group2.cms.service.dto.ConfigAddDTO;
import com.group2.cms.service.dto.ConfigSelectDTO;
import com.group2.cms.service.dto.ConfigUpdateDTO;
import com.group2.cms.util.BriupAsserts;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 配置模块实现类
 * @author tangjy
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements IConfigService {
    @Autowired
    private ConfigMapper configMapper;

    /**
     * 查找当前启用的所有配置类
     * @return
     */
    @Override
    public ConfigSelectDTO getAllEnabledConfig() {

        LambdaQueryWrapper<Config> queryWrapper = Wrappers.lambdaQuery(Config.class)
                .eq(Config::getStatus, 1)    // 已启用
                .eq(Config::getDeleted, 0)   // 未删除
                .last("LIMIT 1");            // 强制返回1条，适配「仅一个启用」的业务规则

        Config enabledConfig = configMapper.selectOne(queryWrapper);

        BriupAsserts.notNull(enabledConfig, "暂无启用的配置信息");

        ConfigSelectDTO resultDTO = new ConfigSelectDTO();
        BeanUtils.copyProperties(enabledConfig, resultDTO);

        return resultDTO;
    }

    /**
     * 分页查找配置信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public IPage<Config> findByPage(Integer pageNum, Integer pageSize) {
        return new LambdaQueryChainWrapper<>(Config.class)
                .eq(Config::getDeleted,0)
                .orderByDesc(Config::getId)
                .page(new Page<>(pageNum, pageSize, true));
    }

    /**
     * 保存配置信息
     * @param dto
     */
    @Role(value = "ADMIN")
    @Override
    public void save(ConfigAddDTO dto) {

        LambdaQueryWrapper<Config> nameWrapper = Wrappers.lambdaQuery(Config.class)
                .eq(Config::getName, dto.getName())
                .eq(Config::getDeleted, 0); // 未删除的配置中名称唯一
        boolean nameExists = configMapper.exists(nameWrapper);
        if (nameExists) {
            throw new ServiceException("系统名称已存在，请更换");
        }

        Config config = new Config();
        BeanUtils.copyProperties(dto, config);
        config.setStatus(0);//默认禁用
        config.setDeleted(0); // 默认未删除


        int insertCount = configMapper.insert(config);
        if (insertCount <= 0) {
            throw new ServiceException("系统配置保存失败");
        }
    }

    /**
     * 根据ID更新配置信息
     * @param dto
     */
    @Role(value = "ADMIN")
    @Override
    public void updateById(ConfigUpdateDTO dto) {
        BriupAsserts.notNull(dto.getId(), "配置id不能为空!");

        Config config = configMapper.selectOne(
                Wrappers.lambdaQuery(Config.class)
                        .eq(Config::getId, dto.getId())
                        .eq(Config::getDeleted, 0)
        );
        BriupAsserts.notNull(config, "该系统配置不存在");

        // 判断名称是否唯一（排除自己）
        LambdaQueryWrapper<Config> wrapper = Wrappers.lambdaQuery(Config.class)
                .eq(Config::getName, dto.getName())
                .ne(Config::getId, dto.getId())
                .eq(Config::getDeleted, 0);

        boolean nameExists = configMapper.exists(wrapper);

        if (nameExists) {
            throw new ServiceException("系统配置名称已存在，请更换");
        }
        BeanUtils.copyProperties(dto, config);
        int updateCount = configMapper.updateById(config);
        if (updateCount <= 0) {
            throw new ServiceException("系统配置更新失败");
        }

    }

    /**
     * 根据ID更新配置状态
     * @param id
     * @param status
     */
    @Role(value = "ADMIN")
    @Override
    public void updateConfigStatus(Integer id, Integer status) {
        BriupAsserts.notNull(id, "配置id不能为空!");
        BriupAsserts.notNull(status, "状态不能为空!");
        BriupAsserts.isTrue((status == 0 || status == 1), "状态只能是0（禁用）或1（启用）");

        // 判断配置是否存在（未删除）
        Config config = configMapper.selectOne(
                Wrappers.lambdaQuery(Config.class)
                        .eq(Config::getId, id)
                        .eq(Config::getDeleted, 0)
        );
        BriupAsserts.notNull(config, "该系统配置不存在");
        if (status == 0) {
            // 统计当前所有已启用且未删除的配置
            LambdaQueryWrapper<Config> totalEnabledWrapper = Wrappers.lambdaQuery(Config.class)
                    .eq(Config::getStatus, 1)
                    .eq(Config::getDeleted, 0);
            long totalEnabledCount = configMapper.selectCount(totalEnabledWrapper);

            // 若仅1个启用配置（就是当前要禁用的），禁止禁用
            BriupAsserts.isTrue(totalEnabledCount > 1, "当前为唯一启用的系统配置，禁止禁用！");
        }

        // 启用一个配置，自动关闭其他启用的配置
        if (status == 1) {
            int disableCount = configMapper.update(
                    null,
                    Wrappers.lambdaUpdate(Config.class)
                            .set(Config::getStatus, 0)
                            .eq(Config::getStatus, 1)
                            .eq(Config::getDeleted, 0)
                            .ne(Config::getId, id)
            );
            // 5.2 启用目标配置
            int enableCount = configMapper.update(
                    null,
                    Wrappers.lambdaUpdate(Config.class)
                            .set(Config::getStatus, 1)
                            .eq(Config::getId, id)
                            .eq(Config::getDeleted, 0)
            );
            BriupAsserts.isTrue(enableCount > 0, "启用目标配置失败");
            return; // 启用逻辑完成，直接返回
        }
        // 6. 常规禁用配置（非唯一启用配置）
        int updateCount = configMapper.update(
                null,
                Wrappers.lambdaUpdate(Config.class)
                        .set(Config::getStatus, status)
                        .eq(Config::getId, id)
                        .eq(Config::getDeleted, 0)
        );
        if (updateCount <= 0) {
            throw new ServiceException("配置状态更新失败");
        }
    }

    /**
     * 根据ID删除配置
     * @param id
     */
    @Role(value = "ADMIN")
    @Override
    public void deleteConfigById(Integer id) {
        BriupAsserts.notNull(id, "配置id不能为空!");

        Config config = configMapper.selectOne(
                Wrappers.lambdaQuery(Config.class)
                        .eq(Config::getId, id)
                        .eq(Config::getDeleted, 0)
        );
        BriupAsserts.notNull(config, "该系统配置不存在");

        // 3. 判断是否保留至少一个开启配置
        if (config.getStatus() == 1) {
            LambdaQueryWrapper<Config> countWrapper = Wrappers.lambdaQuery(Config.class)
                    .ne(Config::getId, id)
                    .eq(Config::getDeleted, 0)
                    .eq(Config::getStatus, 1);
            long enabledCount = configMapper.selectCount(countWrapper);
            BriupAsserts.isTrue(enabledCount > 0, "删除失败：配置模块至少保留一个开启状态");
        }

        int updateCount = configMapper.update(
                null,
                Wrappers.lambdaUpdate(Config.class)
                        .set(Config::getDeleted, 1)
                        .eq(Config::getId, id)
        );
        if (updateCount <= 0) {
            throw new ServiceException("配置删除失败");
        }
    }
}
