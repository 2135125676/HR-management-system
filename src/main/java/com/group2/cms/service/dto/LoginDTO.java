package com.group2.cms.service.dto;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 登录请求参数
 * */
@Data
public class LoginDTO {
    // 当添加校验注解偶，不需要编写参数校验代码，自动校验web层接收参数的要求
    @NotNull(message = "账号不能为空")
    private String username;
    @NotNull(message = "密码不能为空")
    private String password;
}
