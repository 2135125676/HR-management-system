package com.group2.cms.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.group2.cms.service.ICategoryService;
import com.group2.cms.service.dto.CategoryDTO;
import com.group2.cms.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author liuzc
 */
@RequestMapping("/auth/category")
@RestController
@Tag(name = "栏目模块")
public class CategoryController {
    @Autowired
    private ICategoryService service;

    //查询所有栏目信息
    @Operation(summary = "查询所有栏目信息")
    @GetMapping("/all")
    public Result<List<CategoryDTO>> findAll() {
        List<CategoryDTO> categories = service.selectAll();
        return Result.success(categories);
    }

    //分页查询栏目信息
    @Operation(summary = "分页查询栏目信息")
    @Parameters({
            @Parameter(name = "pageNum", description = "当前页码",example = "1"),
            @Parameter(name = "pageSize", description = "每页大小",example = "5"),
    })
    @GetMapping("/page")
    public Result<IPage> categoryPage(@RequestParam Integer pageNum,
                                 @RequestParam Integer pageSize){
        IPage<CategoryDTO> page = service.findCategoryByPage(pageNum, pageSize);
        return Result.success(page);
    }

    //保存栏目信息(新增)
    /**
     * 1.直接@RequestParam(name = "order") corder接受order值，后在方法中直接使用corder
     * 2.通过HttpServletRequest获取order值传给addDTO中的corder值
     * 3.创建新的categoryAddDTO包，将corder改为order
     * @param addDTO
     * @param request
     * @return
     */
    @Operation(summary = "保存栏目信息")
    @PostMapping // 显式指定返回JSON
    public Result<String> addCategory(@ModelAttribute CategoryDTO addDTO, HttpServletRequest request) {
        String orderParam = request.getParameter("order");
        if (orderParam != null) {
            addDTO.setCorder(Integer.parseInt(orderParam));
        }
        return service.addCategory(addDTO);
    }

    //更新栏目信息(修改)
    @Operation(summary = "更新栏目信息")
    @PutMapping
    public Result<String> updateCategory(@ModelAttribute CategoryDTO updateDTO) {
        return service.updateCategory(updateDTO);
    }
    /**
     * 单ID删除栏目（逻辑删除）
     */
    @Operation(summary = "通过ID删除栏目")
    @DeleteMapping
    public Result<String> deleteCategory(@Parameter(name = "id", description = "栏目ID", required = true) @RequestParam Integer id) {
        return service.deleteById(id);
    }
    /**
     * 批量删除栏目（逻辑删除）
     */
    @Operation(summary = "批量删除栏目")
    @DeleteMapping("/batch")
    public Result<String> deleteCategoryBatch(@RequestParam("ids") List<Integer> ids) {
        return service.deleteBatch(ids);
    }

}
