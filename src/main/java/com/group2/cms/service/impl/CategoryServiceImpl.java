package com.group2.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.group2.cms.annotation.Role;
import com.group2.cms.entity.Article;
import com.group2.cms.entity.Category;
import com.group2.cms.mapper.ArticleMapper;
import com.group2.cms.mapper.CategoryMapper;
import com.group2.cms.service.ICategoryService;

import com.group2.cms.service.dto.CategoryDTO;

import com.group2.cms.util.BriupBeanUtils;
import com.group2.cms.util.Result;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author liuzc
 */
@Slf4j
@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private CategoryMapper mapper;
    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 查询所有栏目信息
     */
    @Override
    public List<CategoryDTO> selectAll() {
        try {
            // 调用Mapper查询未删除的栏目，并按序号排序
            return mapper.selectAll();
        } catch (Exception e) {
            // 捕获异常
            e.printStackTrace();
            // 异常时返回空集合
            return List.of();
        }
    }

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public IPage<CategoryDTO> findCategoryByPage(Integer pageNum, Integer pageSize) {
        IPage<Category> categoryPage = new LambdaQueryChainWrapper<>(Category.class)
                .eq(Category::getDeleted, 0)
                .orderByAsc(Category::getCorder)
                .page(new Page<>(pageNum, pageSize, true));
        return categoryPage.convert(category -> {
            CategoryDTO dto = new CategoryDTO();
            dto.setId(category.getId()); // 实体category_id → DTO id
            dto.setName(category.getName()); // 实体category_name → DTO name
            dto.setDescription(category.getDescription()); // 实体category_description → DTO description
            dto.setCorder(category.getCorder()); // 实体category_order → DTO corder（对应JSON的order）
            dto.setParentId(category.getParentId()); // 实体category_parent_id → DTO parentId
            return dto;
        });

    }
    //判定id与parentId是否相同
    private boolean checkIdAndParentIdNotEqual(Integer id, Integer parentId) {
        // 父ID为null（顶级栏目）：无需校验，直接通过
        if (parentId == null || parentId == 0) {
            return true;
        }
        if (id == null){
            return true;
        }
        // 校验ID和父ID是否相等，相等则返回false（校验失败）
        return !id.equals(parentId);
    }

    //判定新增和修改的name不能与数据库中的name相等
    private boolean checkCategoryNameUnique(String name, Integer excludeId) {
        // 名称为空：直接返回false
        if (StringUtils.isBlank(name)) {
            return false;
        }
        // 调用Mapper的自定义方法，执行XML中的SQL
        Integer count = 0;
        try {
            count = mapper.countDuplicateName(name.trim(), excludeId);
        } catch (Exception e) {
            e.printStackTrace();
            return false; // 异常时视为重复
        }
        // 0表示唯一，非0表示重复
        return count == 0;
    }
    /**
     * 新增栏目信息
     * @param addDTO
     */
    @Override
    public Result<String> addCategory(CategoryDTO addDTO) {
        try {
            // DTO转换为实体类
            Category category = new Category();
            category.setName(addDTO.getName()); // 栏目名称
            category.setDescription(addDTO.getDescription()); // 栏目描述
            category.setCorder(addDTO.getCorder()); // 排序序号
            // 名称唯一性校验
            boolean nameUniqueCheck = checkCategoryNameUnique(addDTO.getName(), null);
            if (!nameUniqueCheck) {
                return Result.error("新增栏目失败：栏目名称已存在");
            }
            // 父栏目ID：传0/null视为顶级栏目，设为null
            category.setParentId(addDTO.getParentId() == null || addDTO.getParentId() == 0
                    ? null : addDTO.getParentId());
            category.setDeleted(0); // 默认未删除
            BeanUtils.copyProperties(addDTO, category);
            int insertCount = mapper.insert(category);
            if (insertCount != 1) {
                return Result.error("新增栏目失败：数据插入异常");
            }
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            // 捕获异常，返回失败信息
            return Result.error("新增栏目失败：系统异常");
        }
    }

    /**
     * 更新栏目信息
     * @param updateDTO
     * @return
     */
    @Role(value = "ADMIN")
    @Override
    public Result<String> updateCategory(CategoryDTO updateDTO) {
        try {

            // 参数前置校验
            Assert.notNull(updateDTO, "修改参数不能为空");
            Assert.notNull(updateDTO.getId(), "栏目ID不能为空");

            // 校验栏目是否存在
            Category existCategory = mapper.selectById(updateDTO.getId());
            if (existCategory == null) {
                return Result.error("栏目ID不存在");
            }
//            // 构建更新实体
            Category updateCategory = BriupBeanUtils.copyProperties(updateDTO,Category.class);

            // 参数校验 id与parentId不能相同
            if (!checkIdAndParentIdNotEqual(updateDTO.getId(), updateDTO.getParentId())) {
                return Result.error("修改栏目失败：父栏目ID不能与当前栏目ID相同");
            }
            //名称唯一性校验
            boolean nameUniqueCheck = checkCategoryNameUnique(updateDTO.getName(), updateDTO.getId());
            if (!nameUniqueCheck) {
                return Result.error("修改栏目失败：栏目名称已存在");
            }
            // 执行更新
            int updateCount = mapper.updateById(updateCategory);
            // 影响行数>0则更新成功，否则失败
            if (updateCount <= 0) {
                return Result.error("修改栏目失败：数据更新异常");
            }
            return Result.success("操作成功", null);
        } catch (IllegalArgumentException e) {
            // 参数校验异常
            return Result.error("修改失败：" + e.getMessage());
        } catch (Exception e) {
            // 其他异常（如数据库异常）
            e.printStackTrace();
            return Result.error("修改栏目失败：系统异常");
        }
    }

    /**
     * 递归查询指定ID的所有子栏目ID（含子、孙层级）
     */
    private List<Integer> getAllChildIds(Integer parentId) {
        List<Integer> allChildIds = new ArrayList<>();
        // 1. 查询当前父栏目的直接子栏目ID
        List<Integer> directChildIds = mapper.selectChildIdsByParentId(parentId);
        if (CollectionUtils.isEmpty(directChildIds)) {
            return allChildIds;
        }
        // 2. 收集直接子栏目ID + 递归查询孙子栏目ID
        allChildIds.addAll(directChildIds);
        for (Integer childId : directChildIds) {
            allChildIds.addAll(getAllChildIds(childId));
        }
        return allChildIds;
    }

    /**
     * 校验栏目ID列表是否关联了文章（cms_article表）
     * @param categoryIds 待校验的栏目ID列表
     * @return true=有关联文章，false=无关联文章
     */
    private boolean checkCategoryHasArticle(List<Integer> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            return false;
        }
        // 查询cms_article表中是否有关联这些栏目的文章
        // 此处假设你有ArticleMapper，也可以用LambdaQueryChainWrapper简化
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Article::getCategoryId, categoryIds);
        // 只需判断是否存在，无需查全部数据，用exists效率更高
        return articleMapper.exists(queryWrapper);
    }

    /**
     * 根据id删除栏目信息
     * @param id
     * @return
     */
    @Role(value = "ADMIN")
    @Override
    public Result<String> deleteById(Integer id) {
        try {
            // 前置参数校验
            Assert.notNull(id, "栏目ID不能为空");

            // 校验栏目是否存在
            Category existCategory = mapper.selectById(id);
            if (existCategory == null) {
                return Result.error("删除失败：栏目ID不存在");
            }

            // 收集所有需要逻辑删除的ID（自身 + 所有子栏目）
            List<Integer> deleteIds = new ArrayList<>();
            deleteIds.add(id); // 加入当前栏目ID
            List<Integer> childIds = getAllChildIds(id);
            deleteIds.addAll(childIds); // 加入所有子栏目ID
            // 校验待删除的栏目是否关联文章
            if (checkCategoryHasArticle(deleteIds)) {
                return Result.error("删除失败：该栏目（或其子栏目）关联了用户文章资源，无法删除");
            }
            int childCount = childIds.size();
            // 校验栏目是否已被删除
            if (existCategory.getDeleted() == 1) {
                return Result.error("删除失败：该数据已被删除");
            }

            // 批量修改deleted=1（逻辑删除）
            int updateCount = mapper.batchUpdateDeleted(deleteIds, 1);
            if (updateCount <= 0) {
                return Result.error("删除失败：数据无更新");
            }

            // 返回成功结果
            return Result.success("删除成功，该栏目下有"+childCount+"条栏目");
        } catch (IllegalArgumentException e) {
            // 参数校验异常
            return Result.error("删除失败：" + e.getMessage());
        } catch (Exception e) {
            // 系统异常
            e.printStackTrace();
            return Result.error("删除失败：系统异常");
        }
    }

    /**
     * 批量删除栏目信息
     事务控制：要么全删成功，要么全回滚
     * @Transactional 注解关键配置：
     * rollbackFor = Exception.class：捕获所有异常（包括非运行时异常）并回滚（默认只回滚 RuntimeException）
     * propagation = Propagation.REQUIRED：默认传播行为，确保在一个事务中执行
     */
    @Role(value = "ADMIN")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<String> deleteBatch(List<Integer> ids) {
        try {
            // 校验ID列表非空
            Assert.notNull(ids, "删除失败：栏目ID列表不能为空");
            Assert.isTrue(!ids.isEmpty(), "删除失败：栏目ID列表不能为空");

            // 收集所有需删除的ID + 校验每个ID的有效性
            Set<Integer> allDeleteIds = new HashSet<>();
            List<String> errorMsgList = new ArrayList<>(); // 记录每个ID的错误信息
            int childCount = 0;//收集子id的数量
            for (Integer id : ids) {
                // 校验单个ID非空
                if (id == null) {
                    errorMsgList.add("ID为空，跳过");
                    continue;
                }
                // 校验栏目是否存在
                Category existCategory = mapper.selectById(id);
                if (existCategory == null) {
                    errorMsgList.add("ID[" + id + "]：栏目不存在，跳过");
                    continue;
                }
                // 校验栏目是否已被删除
                if (existCategory.getDeleted() != null && existCategory.getDeleted() == 1) {
                    errorMsgList.add("ID[" + id + "]：该数据已被删除，跳过");
                    continue;
                }
                // 收集当前ID + 所有子栏目ID
                allDeleteIds.add(id);
                List<Integer> childIds = getAllChildIds(id);
                childCount += childIds.size();

                allDeleteIds.addAll(getAllChildIds(id));
            }

            // 校验待删除的栏目是否关联文章
            if (checkCategoryHasArticle(new ArrayList<>(allDeleteIds))) {
                return Result.error("删除失败：部分栏目（或其子栏目）关联了用户文章资源，无法删除");
            }

            // 批量修改deleted=1（逻辑删除）
            int updateCount = mapper.batchUpdateDeleted(new ArrayList<>(allDeleteIds), 1);

            if(updateCount != (childCount+ids.size())) {
                return Result.error("批量删除数量不匹配，可能存在无效ID或删除失败");
            }
            // 构造返回提示
            String successMsg = "批量删除成功，共处理" + updateCount + "条数据";
            return Result.success(successMsg);

        } catch (IllegalArgumentException e) {
            return Result.error("删除失败：删除列表不能为空");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("删除失败：事务已回滚");
        }
    }
}
