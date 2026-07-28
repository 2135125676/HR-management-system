package com.group2.cms.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 去除查询到的deleted属性
 * @author liuzc
 */
@Data
public class CategoryDTO {
    private static final long serialVersionUID = 1L;

    /**
     * 栏目ID（对应数据库category_id）
     */
    @NotNull(message = "栏目ID不能为空")
    private Integer id;

    /**
     * 栏目名称（对应数据库category_name）
     */
    @NotBlank(message = "栏目名称不能为空")
    private String name;

    /**
     * 栏目描述（对应数据库category_description）
     */
    private String description;

    /**
     * 栏目排序序号（对应数据库category_order）
     * 因为order在sql语句中有算法，所以需要添加JsonProperty注释
     */
    @NotNull(message = "栏目排序序号不能为空")
    @PositiveOrZero(message = "排序序号必须为非负数")
    @JsonProperty("order") // 关键：将前端的order参数映射到corder字段
    private Integer corder;

    /**
     * 父栏目ID（对应数据库category_parent_id）
     */
    private Integer parentId;
}
