package com.group2.cms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.group2.cms.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * 评论服务类
 * </p>
 *
 * @author [cherj]
 * @since 2025-12-22
 */
public interface ICommentService extends IService<Comment> {

    /**
     * 带关键词搜索的分页查询评论
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param keywords 搜索关键词
     * @return 分页评论数据
     */
    Page<Comment> pageWithKeywords(Integer pageNum, Integer pageSize, String keywords);
    
    /**
     * 审核评论
     * @param id 评论ID
     * @param status 评论状态：0-待审核，1-不通过，2-通过
     * @return 是否审核成功
     */
    boolean updateCommentStatus(Integer id, Integer status);
    
    /**
     * 根据用户ID分页查询评论
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @param userId 用户ID
     * @return 分页评论数据
     */
    Page<Comment> pageByUserId(Integer pageNum, Integer pageSize, Integer userId);
    
    /**
     * 级联删除评论（先删除子评论，再删除父评论）
     * @param id 评论ID
     * @return 是否删除成功
     */
    boolean deleteCommentCascade(Integer id);
    
    /**
     * 批量级联删除评论
     * @param ids 评论ID列表
     * @return 是否删除成功
     */
    boolean deleteCommentsCascade(List<Integer> ids);
}