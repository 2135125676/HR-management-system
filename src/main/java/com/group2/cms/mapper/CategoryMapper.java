package com.group2.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.group2.cms.entity.Category;
import com.group2.cms.service.dto.CategoryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuzc
 * @since 2025-12-25
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    List<CategoryDTO> selectAll();

    int batchUpdateDeleted(@Param("ids") List<Integer> ids, @Param("deleted") Integer deleted);

    List<Integer> selectChildIdsByParentId(Integer parentId);

    Integer countDuplicateName(@Param("name") String trim, @Param("excludeId") Integer excludeId);
}
