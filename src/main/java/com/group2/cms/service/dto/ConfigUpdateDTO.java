package com.group2.cms.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ConfigUpdateDTO {
    @NotNull(message = "系统id不能为空")
    @Schema(description = "系统编号", example = "1")
    private Integer id;

    @NotBlank(message = "系统名称不能为空")
    @Size(max = 30, message = "系统名称长度不能超过30字")
    @Schema(description = "系统名称", example = "学生管理系统")
    private String name;

    @Size(max = 100, message = "系统描述长度不能超过100字")
    @Schema(description = "系统描述", example = "这是一个学生管理系统")
    private String info;

    @NotBlank(message = "系统图标地址不能为空")
    @Schema(description = "系统图标地址", example = "www.baidu.com/a.png")
    private String icon;


    @Schema(description = "状态：0-禁用，1-启用", example = "0", allowableValues = {"0", "1"})
    private Integer status;
}
