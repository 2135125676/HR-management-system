package com.group2.cms.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评论DTO
 * @author [cherj]
 */
@Data
public class CommentDTO {
    private Integer id;
    private String content;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
    private Integer status;
    private Integer userId;
    private Integer articleId;
    private Integer parentId;
    private Integer deleted;
}