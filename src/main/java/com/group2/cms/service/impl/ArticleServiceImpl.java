package com.group2.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.group2.cms.annotation.Role;
import com.group2.cms.entity.Article;
import com.group2.cms.entity.Comment;
import com.group2.cms.mapper.ArticleMapper;
import com.group2.cms.mapper.CommentMapper;
import com.group2.cms.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group2.cms.service.dto.ArticleDTO;
import com.group2.cms.util.BriupAsserts;
import com.group2.cms.util.BriupBeanUtils;
import com.group2.cms.util.UserInfoUtil;
import com.group2.cms.web.vo.ArticlePageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author luojy
 * @since 2025-12-25
 */
@Slf4j
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {
    @Autowired
    private ArticleMapper mapper;

    @Autowired
    private CommentMapper commentMapper;

    // 按分类id查询、按状态查询、按标题查询like（模糊查询）
    @Override
    public Page<ArticlePageVO> findByPage(Integer categoryId, Integer pageNum, Integer pageSize, Integer status, String title) {
        Page<Article> page = new Page<>(pageNum, pageSize,true);

        // 1. 先查询所有资讯
        LambdaQueryWrapper<Article> wrapper1 = Wrappers.lambdaQuery(Article.class)
                .eq(Article::getDeleted, 0);
        if (categoryId != null) {
            wrapper1.eq(Article::getCategoryId, categoryId);
        }
        if (status != null) {
            wrapper1.eq(Article::getStatus, status);
        }
        if (StringUtils.hasText(title)) {
            wrapper1.like(Article::getTitle, title);
        }

        Page<Article> articlePage = new Page<>(pageNum, pageSize);
        // 把所有资讯的id查出来
        IPage<Article> articleIdPage = mapper.selectArticleIdPage(articlePage, wrapper1);
        List<Article> articleList = articleIdPage.getRecords();

        // 如果一条数据都没有则返回空页面
        if (articleList.isEmpty()) {
            Page<ArticlePageVO> emptyPage = new Page<>(page.getCurrent(), page.getSize(), 0);
            emptyPage.setRecords(List.of());
            return emptyPage;
        }

        List<Integer> articleIdList = articleList.stream()
                .map(Article::getId)
                .collect(Collectors.toList());

        LambdaQueryWrapper<Article> wrapper2 = Wrappers.lambdaQuery(Article.class);

        if (categoryId != null) {
            wrapper2.eq(Article::getCategoryId , categoryId);
        }
        if (status != null) {
            wrapper2.eq(Article::getStatus, status);
        }
        if (StringUtils.hasText(title)) {
            wrapper2.like(Article::getTitle, title);
        }

        List<ArticlePageVO> articleExtList = mapper.selectArticleByIds(wrapper2, articleIdList);
        Page<ArticlePageVO> resultPage = new Page<>(
                page.getCurrent(),
                page.getSize(),
                articleIdPage.getTotal()
        );
        resultPage.setRecords(articleExtList);
        return resultPage;
    }


    @Override
    public ArticlePageVO findPageById(Integer id) {
        BriupAsserts.notNull(id, "资讯id不能为空!");
        LambdaQueryWrapper<Article> wrapper = Wrappers.lambdaQuery(Article.class)
                .eq(Article::getId, id);
        ArticlePageVO articlePageVO = mapper.selectByArticleId(wrapper);
        return articlePageVO;
    }

    @Override
    public void updateArticle(ArticleDTO dto) {
        Article article = BriupBeanUtils.copyProperties(dto, Article.class);
        Integer id = article.getId();

        // 校验文章标题是否重复
        Article selectArticle1 = mapper.selectOne(Wrappers.lambdaQuery(Article.class)
                .eq(Article::getTitle, article.getTitle()));
        BriupAsserts.isTrue((selectArticle1 == null), "资讯标题重复，请修改");

        // 如果id为空，则插入文章
        if (id == null || id <= 0) {
            BriupAsserts.notNull(article.getTitle(), "资讯标题不能为空");
            BriupAsserts.notNull(article.getContent(), "资讯内容不能为空");
            BriupAsserts.notNull(article.getCategoryId(), "分类id不能为空");

            Object id1 = UserInfoUtil.getId();

            Integer userId = id1 != null ? Integer.valueOf(id1.toString()) : null;

            article.setUserId(userId);
            article.setDeleted(0);
            mapper.insert(article);
            return;
        }

        // 校验文章是否存在
        Article selectArticle2 = mapper.selectById(id);
        BriupAsserts.notNull(selectArticle2, "资讯不存在，请勿重复操作");

        mapper.updateById(article);
    }

    @Role(value = "ADMIN")
    @Override
    public void updateArticleStatus(Integer id, Integer status) {
        BriupAsserts.notNull(id, "资讯id不能为空!");
        BriupAsserts.notNull(status, "状态不能为空!");

        // 验证状态值的有效性
        BriupAsserts.isTrue(( status >= 0 && status <= 3), "无效的状态值");

        // 验证文章是否存在
        Article article = mapper.selectById(id);
        BriupAsserts.notNull(article, "资讯不存在，请勿重复操作");

        mapper.update(
                Wrappers.lambdaUpdate(Article.class)
                .set(Article::getStatus, status)
                .eq(Article::getId, id)
                );
    }

    @Role(value = "ADMIN")
    @Override
    @Transactional(rollbackFor = Exception.class)  // 回滚所有异常
    public void deleteArticleById(Integer id) {
        BriupAsserts.notNull(id, "资讯id不能为空!");

        // 校验文章是否存在
        Article selectArticle = mapper.selectById(id);
        BriupAsserts.notNull(selectArticle, "资讯不存在，请勿重复操作");

        // 逻辑删除
        mapper.update(
                Wrappers.lambdaUpdate(Article.class)
                        .set(Article::getDeleted, 1)
                        .eq(Article::getId, id)
        );

        // 删除后对应的评论也删除
        commentMapper.update(Wrappers.lambdaUpdate(Comment.class)
                .set(Comment::getDeleted, 1)
                .eq(Comment::getArticleId, id)
        );
    }
}
