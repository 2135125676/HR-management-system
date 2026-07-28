package com.group2.cms.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 保存轮播图返回值，隐藏id
 */
@Data
public class CarouselAddDTO {
    @NotBlank(message = "轮播图名称不能为空")
    @Size(max = 30, message = "轮播图名称长度不能超过30个字符")
    @Schema(description = "轮播图名称（唯一）",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "企业产品")
    private String name;
    @Size(max = 200, message = "轮播图描述长度不能超过200个字符")
    @Schema(description = "轮播图描述信息", example = "这个图片主要做为宣传企业产品")
    private String info;
    @NotBlank(message = "轮播图地址不能为空")
    private String url;
    @NotNull(message = "状态不能为空")
    @Schema(description = "状态（0=禁用，1=启用）",
            example = "1", allowableValues = {"0", "1"})
    private Integer status;
}
