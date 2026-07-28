package com.group2.cms.web.controller;

import com.group2.cms.annotation.Log;
import com.group2.cms.service.ILoginService;
import com.group2.cms.service.dto.LoginDTO;
import com.group2.cms.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;

import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录模块接口
 */

@Tag(name = "登录模块")
@RestController
public class LoginController {
    @Autowired
    private ILoginService loginService;

    /**
     * 接收表单数据
     * 1. 请求体中 id=1&name=jack 每个参数单独接收
     * 2. 如果参数比较多:封装成一个dto对象，接收参数的方式
     * */
    @Log
    @Operation(summary = "登录接口")
    // @RequestBody 为表单数据
    @Parameters({
            @Parameter(name = "username", description = "用户名"),
            @Parameter(name = "password", description = "密码")
    })
    @PostMapping("/login")
    public Result<String> login(@Valid LoginDTO dto){

        return Result.success("操作成功",loginService.login(dto));
    }


    @Log
    @Operation(summary = "用户退出")
    @PostMapping("/logout")
    public Result<String> logout(){

        return Result.success();
    }
}
