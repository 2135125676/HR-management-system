package com.group2.cms.web.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Schema(name = "UserIconVO",description = "修改用户头像请求参数")
@Data
public class UserIconVO {
    @Schema(description = "用户编号", example = "1")
    @NotNull(message = "用户ID不能为空")
    private Integer id;

    @Schema(description = "用户头像地址", example = "www.baidu.com/image/a.png")
    @NotBlank(message = "头像地址不能为空")
    private String userIcon;
}