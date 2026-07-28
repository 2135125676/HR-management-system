package com.group2.cms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.group2.cms.annotation.Role;
import com.group2.cms.entity.Log;
import com.group2.cms.mapper.LogMapper;
import com.group2.cms.service.ILogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author luojy
 * @since 2025-12-22
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {

    @Role(value = "ADMIN")
    @Override
    public IPage<Log> findByPage(Integer pageNum, Integer pageSize) {
        return new LambdaQueryChainWrapper<>(Log.class)
                .orderByDesc(Log::getTime)
                .page(new Page<>(pageNum, pageSize, true));
    }
}
