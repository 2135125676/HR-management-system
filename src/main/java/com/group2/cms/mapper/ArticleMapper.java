package com.group2.cms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.group2.cms.entity.Article;
import com.group2.cms.entity.Comment;
import com.group2.cms.web.vo.ArticlePageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article>{

    ArticlePageVO selectByArticleId(@Param(Constants.WRAPPER) Wrapper<Article> wrapper);

    IPage<Article> selectArticleIdPage(Page<Article> page,
                                       @Param(Constants.WRAPPER) Wrapper<Article> wrapper);

    List<ArticlePageVO> selectArticleByIds(@Param(Constants.WRAPPER) Wrapper<Article> wrapper,
                                           @Param("articleIdList") List<Integer> articleIdList);
}
