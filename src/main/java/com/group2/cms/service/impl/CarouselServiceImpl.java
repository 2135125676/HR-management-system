package com.group2.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group2.cms.annotation.Role;
import com.group2.cms.entity.Carousel;
import com.group2.cms.exception.ServiceException;
import com.group2.cms.mapper.CarouselMapper;
import com.group2.cms.service.ICarouselService;
import com.group2.cms.service.dto.CarouselAddDTO;
import com.group2.cms.service.dto.CarouselUpdateDTO;
import com.group2.cms.util.BriupAsserts;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 轮播图实现类
 * @author tangjy
 */
@Service
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements ICarouselService {
    @Autowired
    private CarouselMapper carouselMapper;

    /**
     * 查询所有启用的轮播图
     */
    @Override
    public List<Carousel> getAllEnabledCcarousel() {
        LambdaQueryWrapper<Carousel> wrapper = Wrappers.lambdaQuery(Carousel.class)
                .eq(Carousel::getStatus, 1) // 只查启用的轮播图（status=1）
                .orderByDesc(Carousel::getId);

        return carouselMapper.selectList(wrapper);
    }

    /**
     * 分页查询轮播图
     */
    @Role(value = "ADMIN")
    @Override
    public IPage<Carousel> findByPage(Integer pageNum, Integer pageSize) {

        Page<Carousel> page = new Page<>(pageNum, pageSize, true);

        LambdaQueryWrapper<Carousel> wrapper = Wrappers.lambdaQuery(Carousel.class)
                .orderByDesc(Carousel::getId);

        return carouselMapper.selectPage(page, wrapper);
    }

    /**
     * 保存轮播图
     */
    @Role(value = "ADMIN")
    @Override
    public void save(CarouselAddDTO dto) {
        LambdaQueryWrapper<Carousel> nameWrapper = Wrappers.lambdaQuery(Carousel.class)
                .eq(Carousel::getName, dto.getName());
        boolean nameExists = carouselMapper.exists(nameWrapper);
        if (nameExists) {
            throw new ServiceException("轮播图名称已存在，请更换");
        }

        // 判断url是否唯一
        LambdaQueryWrapper<Carousel> urlWrapper = Wrappers.lambdaQuery(Carousel.class)
                .eq(Carousel::getUrl, dto.getUrl());
        boolean urlExists = carouselMapper.exists(urlWrapper);
        if (urlExists) {
            throw new ServiceException("轮播图地址已存在，请更换");
        }

        Carousel carousel = new Carousel();
        BeanUtils.copyProperties(dto, carousel);

        int insertCount = carouselMapper.insert(carousel);
        if (insertCount <= 0) {
            throw new ServiceException("轮播图保存失败");
        }
    }

    /**
     * 根据ID更新轮播图信息
     * @param dto
     */
    @Role(value = "ADMIN")
    @Override
    public void updateById(CarouselUpdateDTO dto) {
        BriupAsserts.notNull(dto, "轮播图更新信息不能为空!");

        Carousel carousel = carouselMapper.selectById(dto.getId());
        BriupAsserts.notNull(carousel, "轮播图不存在");

        // 判断名称是否重复
        LambdaQueryWrapper<Carousel> nameWrapper = Wrappers.lambdaQuery(Carousel.class)
                .eq(Carousel::getName, dto.getName().trim())
                .ne(Carousel::getId, dto.getId());
        boolean nameExists = carouselMapper.exists(nameWrapper);
        if (nameExists) {
            throw new ServiceException("更新失败：轮播图名称已存在，请更换名称");
        }


        LambdaUpdateWrapper<Carousel> updateWrapper = Wrappers.lambdaUpdate(Carousel.class)
                .eq(Carousel::getId, dto.getId())
                .set(Carousel::getName, dto.getName())
                .set(Carousel::getInfo, dto.getInfo())
                .set(Carousel::getStatus, dto.getStatus())
                .set(Carousel::getUrl, dto.getUrl());

        int updateCount = carouselMapper.update(null, updateWrapper);
        System.out.println("--------------进行-----------------");
        if (updateCount <= 0) {
            throw new ServiceException("更新失败，数据未变更");
        }
    }

    /**
     * 根据ID删除轮播图
     * @param id
     */
    @Role(value = "ADMIN")
    @Override
    public void deleteById(Integer id) {
        BriupAsserts.notNull(id, "id不能为空");

        // 判断轮播图是否存在
        Carousel carousel = carouselMapper.selectById(id);
        BriupAsserts.notNull(carousel, "该轮播图不存在");

        // 判断轮播图是否开启
        BriupAsserts.isTrue(!"1".equals(carousel.getStatus()), "无法删除开启状态的轮播图，请先禁用");
        //至少有一个开启的轮播图存在
        LambdaQueryWrapper<Carousel> countWrapper = Wrappers.lambdaQuery(Carousel.class)
                .eq(Carousel::getStatus, "1");
        BriupAsserts.isTrue(carouselMapper.selectCount(countWrapper) > 1,
                "删除失败：至少保留一个轮播图");

        int deleteCount = carouselMapper.deleteById(id);
        if (deleteCount <= 0) {
            throw new ServiceException("轮播图删除失败");
        }
    }
}
