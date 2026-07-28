package com.group2.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.group2.cms.entity.Carousel;
import com.group2.cms.service.dto.CarouselAddDTO;
import com.group2.cms.service.dto.CarouselUpdateDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface ICarouselService extends IService<Carousel> {
    List<Carousel> getAllEnabledCcarousel();

    IPage<Carousel> findByPage(Integer pageNum, Integer pageSize);

    void save(@Valid CarouselAddDTO dto);

    void updateById(@Valid CarouselUpdateDTO dto);

    void deleteById(Integer id);
}
