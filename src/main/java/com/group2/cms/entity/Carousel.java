package com.group2.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author tangjy
 * 轮播图类
 */
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("cms_carousel")
public class Carousel {
    @NotNull(message = "id不能为空")
    @TableId(value = "carousel_id",type = IdType.AUTO)
    private Integer id;
    @TableField("carousel_name")
    private String name;
    /**
     * 轮播图信息
     */
    @TableField("carousel_info")
    private String info;
    /**
     * 轮播图地址
     */
    @TableField("carousel_url")
    private String url;
    /**
     * 状态：0-禁用，1-启用
     */
    @TableField("carousel_status")
    private Integer status;
}
