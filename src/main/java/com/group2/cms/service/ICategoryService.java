package com.group2.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.group2.cms.service.dto.CategoryDTO;
import com.group2.cms.util.Result;

import java.util.List;

/**
 * @author liuzc
 */
public interface ICategoryService {
    List<CategoryDTO> selectAll();

    IPage<CategoryDTO> findCategoryByPage(Integer pageNum, Integer pageSize);


    Result<String> addCategory(CategoryDTO addDTO);

    Result<String> updateCategory(CategoryDTO updateDTO);

    Result<String> deleteById(Integer id);

    Result<String> deleteBatch(List<Integer> ids);
}
