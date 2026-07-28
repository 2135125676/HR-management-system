package com.group2.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author liuzc
 */
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("cms_category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "category_id", type = IdType.AUTO)
    private Integer id;

    /**
     * 分类名称
     */
    @TableField("category_name")
    private String name;

    /**
     * 栏目描述
     */
    @TableField("category_description")
    private String description;

    /**
     * 栏目序号
     */
    @TableField("category_order")
    @JsonProperty("order")
    private Integer corder;

    /**
     * 栏目所属父栏目
     */
    @TableField("category_parent_id")
    private Integer parentId;

    /**
     * 状态：0-未删除，1-已删除
     */
    @JsonIgnore
    @TableField("deleted")
    private Integer deleted;
}
