package com.group2.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.group2.cms.entity.User;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author luojy
 * @since 2025-12-22
 */
public interface UserMapper extends BaseMapper<User> {
    User selectUserByUsername(String username);
    User getById(Integer id);
    boolean deleteUserById(Integer id);



}
