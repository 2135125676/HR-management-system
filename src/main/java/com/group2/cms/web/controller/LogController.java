package com.group2.cms.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.group2.cms.entity.Log;
import com.group2.cms.service.ILogService;
import com.group2.cms.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author luojy
 * @since 2025-12-22
 */
@RestController
@RequestMapping("/auth/log")
@Tag(name = "日志模块")
public class LogController {
    @Autowired
    private ILogService service;

    @Operation(summary = "分页查询日志")
    @Parameters({
            @Parameter(name = "pageNum", description = "当前页码",example = "1"),
            @Parameter(name = "pageSize", description = "每页大小",example = "5"),
    })
    @GetMapping("/page")
    public Result<IPage> logView(@RequestParam Integer pageNum,
                                  @RequestParam Integer pageSize){
        IPage<Log> page = service.findByPage(pageNum, pageSize);
        return Result.success(page);
    }
}
