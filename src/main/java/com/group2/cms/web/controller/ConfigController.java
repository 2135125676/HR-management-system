package com.group2.cms.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.group2.cms.entity.Config;
import com.group2.cms.service.IConfigService;
import com.group2.cms.service.dto.ConfigAddDTO;
import com.group2.cms.service.dto.ConfigSelectDTO;
import com.group2.cms.service.dto.ConfigUpdateDTO;

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
 * 配置模块
 * @author tangjy
 */
@RestController
@RequestMapping("/auth/config")
@Tag(name = "配置类模块")
public class ConfigController {
    @Autowired
    private IConfigService configService;

    @Operation(summary = "查找当前启用的系统配置")
    @GetMapping("/yes")
    public Result<ConfigSelectDTO> getAllConfig() {
        ConfigSelectDTO getAllEnabledConfig = configService.getAllEnabledConfig();
        return Result.success("操作成功",getAllEnabledConfig);
    }

    @Operation(summary = "分页查询配置信息")
    @Parameters({
            @Parameter(name = "pageNum", description = "当前页码",example = "1"),
            @Parameter(name = "pageSize", description = "每页大小",example = "5"),
    })
    @GetMapping("/page")
    public Result<IPage> findConfigByPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        IPage<Config> page = configService.findByPage(pageNum,pageSize);
        return Result.success("操作成功", page);
    }
    @Operation(summary = "保存配置信息" )
    @PostMapping
    public Result<String> addConfig(@Valid ConfigAddDTO dto) {
        configService.save(dto);
        return Result.success("操作成功",null);
    }

    @Operation(summary = "更新配置")
    @PutMapping
    public Result<String> updateConfig(@Valid ConfigUpdateDTO dto) {
        configService.updateById(dto);
        return Result.success("操作成功",null);
    }
    @Operation(summary = "更新配置信息状态")

    @Parameters({
            @Parameter(name = "id", description = "编号",example = "1"),
            @Parameter(name = "status", description = "状态",example = "1"),
    })
    @PutMapping("/status")
    public Result<String> updateConfigStatus(@RequestParam Integer id, @RequestParam Integer status) {
        configService.updateConfigStatus(id, status);
        return Result.success("操作成功",null);
    }

    @Operation(summary = "通过id删除配置信息")
    @Parameters({
            @Parameter(name = "id", description = "编号",example = "1")
    })
    @DeleteMapping
    public Result<String> deleteConfigById(@RequestParam Integer id) {
        configService.deleteConfigById(id);
        return Result.success("删除成功",null);
    }
}
