package com.group2.cms.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 评论实体类
 * </p>
 *
 * @author [cherj]
 * @since 2025-12-22
 */
@Getter
@Setter
@ToString
@TableName("cms_comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论状态常量
     */
    public static final Integer STATUS_PENDING = 0;   // 待审核
    public static final Integer STATUS_REJECTED = 1;  // 不通过
    public static final Integer STATUS_APPROVED = 2;  // 通过

    /**
     * 评论ID
     */
    @TableId(value = "comment_id", type = IdType.AUTO)
    private Integer id;

    /**
     * 评论内容
     */
    @TableField("comment_content")
    private String content;

    /**
     * 评论时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("comment_time")
    private LocalDateTime time;

    /**
     * 评论状态：0-待审核，1-不通过，2-通过
     */
    @TableField("comment_status")
    private Integer status;

    /**
     * 用户ID
     */
    @TableField("comment_user_id")
    private Integer userId;

    /**
     * 文章ID
     */
    @TableField("comment_article_id")
    private Integer articleId;

    /**
     * 父评论ID
     */
    @TableField("comment_parent_id")
    private Integer parentId;

    /**
     * 逻辑删除字段
     */
    @TableField("deleted")
    @TableLogic
    private Integer deleted;
}