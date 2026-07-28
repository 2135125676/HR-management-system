package com.group2.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author luojy
 * @since 2025-12-25
 */
@Getter
@Setter
@ToString
@TableName("cms_article")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "article_id",type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    @TableField("article_title")
    private String title;

    /**
     * 内容
     */
    @TableField("article_content")
    private String content;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("article_publish_time")
    private LocalDateTime publishTime;

    /**
     * 阅读次数
     */
    @TableField("article_read_times")
    private Integer readTimes;

    /**
     * 状态：0-待审核，1-不通过，2-通过，3-推荐
     */
    @TableField("article_status")
    private Integer status;

    /**
     * 点赞数量
     */
    @TableField("article_thump_up")
    private Integer thumpUp;

    /**
     * 封面图片地址
     */
    @TableField("article_cover")
    private String cover;

    /**
     * 所属用户id
     */
    @TableField("article_user_id")
    private Integer userId;

    /**
     * 所属栏目id
     */
    @TableField("article_category_id")
    private Integer categoryId;

    /**
     * 是否删除：0 未删除 1 已删除
     */
    @JsonIgnore
    @TableField("deleted")
    private Integer deleted;
}
