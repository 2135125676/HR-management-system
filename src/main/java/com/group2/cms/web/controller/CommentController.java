package com.group2.cms.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.group2.cms.annotation.Role;
import com.group2.cms.entity.Comment;
import com.group2.cms.exception.ServiceException;
import com.group2.cms.service.ICommentService;
import com.group2.cms.service.dto.CommentDTO;
import com.group2.cms.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 评论前端控制器
 * </p>
 *
 * @author [cherj]
 * @since 2025-12-22
 */
@Tag(name = "评论管理")
@RestController
@RequestMapping("/auth/comment")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    // 分页查询评论
    @Operation(summary = "分页查询评论")
    @GetMapping("/page")
    public Result<Object> list(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam(required = false) String keywords) {
        // 带关键词搜索的分页查询评论
        Page<Comment> commentPage = commentService.pageWithKeywords(pageNum, pageSize, keywords);
        
        // 将Comment实体转换为CommentDTO
        List<CommentDTO> commentDTOList = commentPage.getRecords().stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setId(comment.getId());
            commentDTO.setContent(comment.getContent());
            commentDTO.setTime(comment.getTime());
            commentDTO.setStatus(comment.getStatus());
            commentDTO.setUserId(comment.getUserId());
            commentDTO.setArticleId(comment.getArticleId());
            commentDTO.setParentId(comment.getParentId());
            commentDTO.setDeleted(comment.getDeleted());
            return commentDTO;
        }).collect(Collectors.toList());
        
        // 构建分页数据结构
        Page<CommentDTO> commentDTOPage = new Page<>();
        commentDTOPage.setTotal(commentPage.getTotal());
        commentDTOPage.setRecords(commentDTOList);
        commentDTOPage.setSize(commentPage.getSize());
        commentDTOPage.setCurrent(commentPage.getCurrent());
        commentDTOPage.setPages(commentPage.getPages());
        
        return Result.success("操作成功", commentDTOPage);
    }
    
    @Operation(summary = "根据用户ID查询评论")
    @Parameters({
        @Parameter(name = "pageNum", description = "页码", example = "1"),
        @Parameter(name = "pageSize", description = "每页条数", example = "10"),
        @Parameter(name = "userId", description = "用户ID", example = "1")
    })
//    @GetMapping("/user")
    public Result<Object> listByUserId(@RequestParam Integer pageNum,
                                      @RequestParam Integer pageSize,
                                      @RequestParam Integer userId) {
        // 验证参数
        if (userId == null) {
            return Result.error("用户ID不能为空");
        }
        
        try {
            // 根据用户ID分页查询评论
            Page<Comment> commentPage = commentService.pageByUserId(pageNum, pageSize, userId);
            
            // 将Comment实体转换为CommentDTO
            List<CommentDTO> commentDTOList = commentPage.getRecords().stream().map(comment -> {
                CommentDTO commentDTO = new CommentDTO();
                commentDTO.setId(comment.getId());
                commentDTO.setContent(comment.getContent());
                commentDTO.setTime(comment.getTime());
                commentDTO.setStatus(comment.getStatus());
                commentDTO.setUserId(comment.getUserId());
                commentDTO.setArticleId(comment.getArticleId());
                commentDTO.setParentId(comment.getParentId());
                commentDTO.setDeleted(comment.getDeleted());
                return commentDTO;
            }).collect(Collectors.toList());
            
            // 构建分页数据结构
            Page<CommentDTO> commentDTOPage = new Page<>();
            commentDTOPage.setTotal(commentPage.getTotal());
            commentDTOPage.setRecords(commentDTOList);
            commentDTOPage.setSize(commentPage.getSize());
            commentDTOPage.setCurrent(commentPage.getCurrent());
            commentDTOPage.setPages(commentPage.getPages());
            
            return Result.success("操作成功", commentDTOPage);
        } catch (Exception e) {
            return Result.error("查询失败，系统错误");
        }
    }
    @Role(value = "ADMIN")
    @Operation(summary = "审核评论")
    @Parameters({
        @Parameter(name = "id", description = "评论编号", example = "1"),
        @Parameter(name = "status", description = "评论状态：0-待审核，1-不通过，2-通过", example = "2")
    })
    @PutMapping("/status")
    public Result<Object> updateCommentStatus(@RequestParam(required = false) Integer id,
                                              @RequestParam(required = false) Integer status) {
        // 验证参数
        if (id == null || status == null) {
            return Result.error("评论编号和状态不能为空");
        }
        
        try {
            // 更新评论状态
            boolean success = commentService.updateCommentStatus(id, status);
            if (success) {
                return Result.success("审核成功");
            } else {
                return Result.error("审核失败，评论不存在或已被删除");
            }
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("无管理员权限");
        }
    }
    @Role(value = "ADMIN")
    @Operation(summary = "根据ID删除评论")
    @Parameter(name = "id", description = "评论编号", example = "1")
    @DeleteMapping
    public Result<Object> deleteComment(@RequestParam Integer id) {
        // 验证参数
        if (id == null) {
            return Result.error("评论编号不能为空");
        }
        
        try {
            // 执行级联删除
            commentService.deleteCommentCascade(id);
            // 方法正常返回，说明删除成功
            return Result.success("删除成功");
        } catch (ServiceException e) {
            // 捕获业务异常，返回具体的业务错误信息
            return Result.error(e.getMessage());
        } catch (Exception e) {
            // 捕获其他异常，返回系统错误
            e.printStackTrace(); // 可以记录日志
            return Result.error("删除失败，系统错误123");
        }
    }
    @Role(value = "ADMIN")
    @Operation(summary = "批量删除评论")
    @Parameter(name = "ids", description = "评论编号列表，多个ID用逗号分隔", example = "1,2,3")
    @DeleteMapping("/batch")
    public Result<Object> batchDeleteComments(@RequestParam String ids) {
        // 验证参数
        if (ids == null || ids.trim().isEmpty()) {
            return Result.error("评论编号列表不能为空");
        }
        
        try {
            // 解析字符串为List<Integer>，同时处理中文逗号和英文逗号
            List<Integer> idList = new ArrayList<>();
            String[] idArray = ids.split("[,，]"); // 匹配中文逗号和英文逗号
            
            for (String idStr : idArray) {
                String trimmedId = idStr.trim();
                if (!trimmedId.isEmpty()) {
                    idList.add(Integer.parseInt(trimmedId));
                }
            }
            
            // 验证解析结果
            if (idList.isEmpty()) {
                return Result.error("评论编号列表不能为空");
            }
            
            // 执行批量级联删除
            commentService.deleteCommentsCascade(idList);
            return Result.success("批量删除成功");
        } catch (NumberFormatException e) {
            return Result.error("评论编号格式错误，请输入正确的数字");
        } catch (Exception e) {
            return Result.error("批量删除失败，系统错误");
        }
    }
}