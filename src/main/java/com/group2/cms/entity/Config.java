package com.group2.cms.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("base_config")
public class Config {
    @TableId(value = "config_id",type = IdType.AUTO)
    private Integer id;
    @TableField("config_name")
    private String name;
    /**
     *系统信息
     */
    @TableField("config_info")
    private String info;
    /**
     *系统图标地址
     */
    @TableField("config_icon")
    private String icon;
    /**
     *状态：0-禁用，1-启用
     */
    @TableField("config_status")
    private Integer status;
    /**
     *是否删除：0 未删除 1 已删除
     */
    @TableField(value = "deleted",updateStrategy = FieldStrategy.NOT_NULL)
    private Integer deleted;
}
