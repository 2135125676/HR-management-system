package com.group2.cms.web.vo;

import com.group2.cms.entity.Article;
import com.group2.cms.entity.Category;
import com.group2.cms.entity.Comment;
import com.group2.cms.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class ArticlePageVO extends Article {
    // 栏目字段
    private Category category;
    // 用户字段
    private User baseUser;
    // 评论字段
    private List<Comment> comments;
}
