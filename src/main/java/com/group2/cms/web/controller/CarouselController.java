package com.group2.cms.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.group2.cms.annotation.Role;
import com.group2.cms.entity.Carousel;
import com.group2.cms.service.ICarouselService;
import com.group2.cms.service.dto.CarouselAddDTO;
import com.group2.cms.service.dto.CarouselUpdateDTO;
import com.group2.cms.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 轮播图模块
 * @author tangjy
 */
@RestController
@RequestMapping("/auth/carousel")
@Tag(name = "轮播图模块")
public class CarouselController {
    @Autowired
    private ICarouselService carouselService;

    @Operation(summary = "查询所有启用的轮播图")
    @GetMapping("/yes")
    public Result<List<Carousel>> getAllCarousel() {
        List<Carousel> carousels = carouselService.getAllEnabledCcarousel();
        return Result.success("操作成功",carousels);
    }

    @Operation(summary = "分页查询所有轮播图")
    @Parameters({
            @Parameter(name = "pageNum", description = "当前页码",example = "1"),
            @Parameter(name = "pageSize", description = "每页大小",example = "5"),
    })
    @GetMapping("/page")
    public Result<IPage> findCarouseByPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize){
        IPage<Carousel> page = carouselService.findByPage(pageNum, pageSize);
        return Result.success("操作成功",page);
    }

    @Role(value = "ADMIN")
    @Operation(summary = "保存轮播图信息")
    @PostMapping
    public Result<String> addCarousel(@Valid CarouselAddDTO dto){
        carouselService.save(dto);
        return Result.success("操作成功",null);
    }

    @Role(value = "ADMIN")
    @Operation(summary = "更新轮播图信息")
    @PutMapping
    public Result<String> updateCarousel(@Valid CarouselUpdateDTO dto){
        carouselService.updateById(dto);
        return Result.success("操作成功",null);
    }

    @Role(value = "ADMIN")
    @Operation(summary = "通过id删除轮播图")
    @Parameters(
            @Parameter(name = "id", description = "编号",example = "1")
    )
    @DeleteMapping
    public Result<String> deleteCarouselById(@RequestParam Integer id){
        carouselService.deleteById(id);
        return Result.success("删除成功",null);
    }

}
