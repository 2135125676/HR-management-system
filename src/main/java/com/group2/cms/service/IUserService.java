package com.group2.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.group2.cms.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.group2.cms.web.vo.UserVO;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qimz
 * @since 2025-12-22
 */
public interface IUserService extends IService<User> {
    User selectByInfo(String username,String password);
    User getAll(Integer id);
    IPage<UserVO> findByPage(Integer pageNum, Integer pageSize, Integer role, Integer id, String username);
    String updateIcon(Integer id, String userIcon);
    void updateUser(Integer id, String dob, String email, Integer gender, String realname, String telephone);
    
    Integer getOperatorIdFromToken(String authHeader);



    void deleteById(Integer id);
}
