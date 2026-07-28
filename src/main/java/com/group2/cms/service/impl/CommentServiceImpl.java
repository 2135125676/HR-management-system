package com.group2.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.group2.cms.annotation.Role;
import com.group2.cms.entity.Comment;
import com.group2.cms.mapper.CommentMapper;
import com.group2.cms.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.group2.cms.util.BriupAsserts;
import java.util.List;

/**
 * <p>
 * 评论服务实现类
 * </p>
 *
 * @author [cherj]
 * @since 2025-12-22
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Override
    public Page<Comment> pageWithKeywords(Integer pageNum, Integer pageSize, String keywords) {
        // 创建分页对象
        Page<Comment> page = new Page<>(pageNum, pageSize);
        
        // 创建查询条件
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        
        // 如果有关键词，添加模糊查询条件
        if (keywords != null && !keywords.isEmpty()) {
            queryWrapper.like(Comment::getContent, keywords);
        }
        
        // 分页查询评论
        return this.page(page, queryWrapper);
    }

    @Role(value = "ADMIN")
    @Override
    public boolean updateCommentStatus(Integer id, Integer status) {
        // 验证状态值是否合法
        if (status != Comment.STATUS_PENDING && status != Comment.STATUS_REJECTED && status != Comment.STATUS_APPROVED) {
            throw new IllegalArgumentException("无效的评论状态");
        }
        
        // 创建更新条件
        Comment comment = new Comment();
        comment.setId(id);
        comment.setStatus(status);
        
        // 更新评论状态
        return this.updateById(comment);
    }
    
    @Override
    public Page<Comment> pageByUserId(Integer pageNum, Integer pageSize, Integer userId) {
        // 创建分页对象
        Page<Comment> page = new Page<>(pageNum, pageSize);
        
        // 创建查询条件
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        
        // 根据用户ID查询评论
        queryWrapper.eq(Comment::getUserId, userId);
        
        // 分页查询评论
        return this.page(page, queryWrapper);
    }

    @Role(value = "ADMIN")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteCommentCascade(Integer id) {
        //评论是否存在
        Comment comment = this.getById(id);
        BriupAsserts.notNull(comment, "评论不存在");

        // 1. 查询该评论的所有子评论
        List<Comment> childComments = this.list(new LambdaQueryWrapper<Comment>()
                .eq(Comment::getParentId, id));
        
        // 2. 递归删除所有子评论
        for (Comment child : childComments) {
            deleteCommentCascade(child.getId());
        }
        
        // 3. 删除当前评论，使用BriupAsserts确保删除失败时抛出异常
        boolean success = this.removeById(id);
        BriupAsserts.isTrue(success, "删除评论失败");
        return true;
    }

    @Role(value = "ADMIN")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteCommentsCascade(List<Integer> ids) {
        // 遍历所有要删除的评论ID
        for (Integer id : ids) {
            // 级联删除每个评论
            deleteCommentCascade(id);
        }
        return true;
    }
}