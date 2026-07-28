package com.group2.cms.web.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.group2.cms.service.IArticleService;
import com.group2.cms.service.dto.ArticleDTO;
import com.group2.cms.util.Result;
import com.group2.cms.web.vo.ArticlePageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "资讯模块")
@RestController
@RequestMapping("/auth/article")
public class ArticleController {
    @Autowired
    private IArticleService service;

    @GetMapping("/page")
    @Operation(summary = "分页多条件查询资讯信息")
    @Parameters({
            @Parameter(name = "categoryId", description = "栏目id",required = false),
            @Parameter(name = "pageNum", description = "当前页"),
            @Parameter(name = "pageSize", description = "每页大小"),
            @Parameter(name = "status", description = "资讯状态",required = false),
            @Parameter(name = "title", description = "咨询标题，支持模糊查询",required = false)
    })
    public Result<IPage<ArticlePageVO>> findByPage(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String title){
        IPage<ArticlePageVO> page = service.findByPage(categoryId, pageNum, pageSize, status, title);
        return Result.success(page);
    }


    @GetMapping
    @Operation(summary = "根据id查询资讯详细信息")
    @Parameters({
            @Parameter(name = "id", description = "资讯id",required = false)
    })
    public Result<ArticlePageVO> findPageById(@RequestParam(required = false) Integer id){
        return Result.success(service.findPageById(id));
    }

    @PostMapping
    @Operation(summary = "保存或更新资讯信息") // 传参为Body 且为application/x-www-form-urlencoded 不加@ModelAttribute也可以，这只是说明为接收表单对象
    public Result<String> undateArticle(@ModelAttribute ArticleDTO dto){
        service.updateArticle(dto);
        return Result.success();
    }

    @PutMapping("/status")
    @Operation(summary = "修改资讯状态")
    public Result<String> updateArticleStatus(Integer id, Integer status){
        service.updateArticleStatus(id, status);
        return Result.success();
    }

    @DeleteMapping
    @Operation(summary = "通过id删除资讯信息")
    public Result<String> deleteArticleById(Integer id){
        service.deleteArticleById(id);
        return Result.success();
    }
}
