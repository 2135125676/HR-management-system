package com.group2.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.group2.cms.entity.Article;
import com.group2.cms.service.dto.ArticleDTO;
import com.group2.cms.web.vo.ArticlePageVO;

import java.util.List;

public interface IArticleService extends IService<Article> {
    Page<ArticlePageVO> findByPage(Integer categoryId, Integer pageNum, Integer pageSize, Integer status, String title);
    ArticlePageVO findPageById(Integer id);
    void updateArticle(ArticleDTO dto);
    void updateArticleStatus(Integer id, Integer status);
    void deleteArticleById(Integer id);
}
