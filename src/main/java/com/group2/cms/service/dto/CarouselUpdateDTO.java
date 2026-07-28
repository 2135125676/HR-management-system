package com.group2.cms.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "轮播图更新参数")
public class CarouselUpdateDTO {
    @NotNull(message = "轮播图ID不能为空")
    @Schema(description = "轮播图编号",defaultValue = "1")
    private Integer id;
    @NotNull(message = "轮播图名称不能为空")
    @Schema(description = "轮播图名称",defaultValue = "企业产品")
    private String name;
    @Schema(description = "轮播图描述信息",defaultValue = "这个图片主要做为宣传企业产品")
    private String info;
    @NotNull(message = "轮播图地址不能为空")
    @Schema(description = "轮播图地址",defaultValue = "www.baidu.com/url/a.png")
    private String url;
    @Schema(description = "状态：0-禁用，1-启用",defaultValue = "0")
    private Integer status;
}
