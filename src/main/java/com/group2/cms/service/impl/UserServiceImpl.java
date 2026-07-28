package com.group2.cms.service.impl;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group2.cms.annotation.Role;
import com.group2.cms.config.JWTProperties;
import com.group2.cms.entity.User;
import com.group2.cms.exception.ServiceException;
import com.group2.cms.mapper.UserMapper;
import com.group2.cms.service.IUserService;
import com.group2.cms.util.BriupAsserts;
import com.group2.cms.util.BriupBeanUtils;
import com.group2.cms.web.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Objects;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qimz
 * @since 2025-12-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper mapper;
    @Autowired
    private JWTProperties jwtProperties;
    /**
     * 从JWT令牌中解析操作者ID
     *
     * @param authHeader 认证头
     * @return 操作者ID
     */
    public Integer getOperatorIdFromToken(String authHeader) {
        Long userId;
        try {
            // 处理JWT令牌，去掉Bearer前缀
            String token = authHeader.replace("Bearer ", "").trim();
            JWT jwt = JWTUtil.parseToken(token)
                    .setKey(jwtProperties.getKey().getBytes(StandardCharsets.UTF_8));

            if (!jwt.verify()) {
                throw new ServiceException("令牌无效或已过期");
            }
            // 从JWT中获取用户ID
            userId = Long.valueOf(jwt.getPayload("userId").toString());
        } catch (Exception e) {
            throw new ServiceException("获取用户身份失败：" + e.getMessage());
        }
        return userId.intValue();
    }

    @Override
    public User selectByInfo(String username, String password) {
        // 校验用户是否存在
        User user = mapper.selectUserByUsername(username);
        BriupAsserts.notNull(user, "用户不存在！");
        // 校验密码
        if(!(user.getPassword().equals(password))){
            throw new ServiceException("密码错误！");
        }
        // 校验权限
        if(user.getStatus() != 1){
            throw new ServiceException("账号已禁用！");
        }
        return  user;
    }
    /**
     * 1.获取当前登录用户的基本信息
     */

    @Override
    public User getAll(Integer id) {
        BriupAsserts.isTrue(id > 0,"用户名输入错误");
        User dbUser = mapper.getById(id);
        BriupAsserts.notNull(dbUser, "当前用户不存在");

        return User.builder()
                .id(dbUser.getId())
                .username(dbUser.getUsername())
                .password(null)
                .telephone(dbUser.getTelephone())
                .realname(dbUser.getRealname())
                .icon(dbUser.getIcon())
                .gender(dbUser.getGender())
                .dob(dbUser.getDob())
                .email(dbUser.getEmail())
                .registerTime(dbUser.getRegisterTime())
                .status(dbUser.getStatus())
                .role(dbUser.getRole())
                .build();
    }

    /**
     * 2.分页多条件查询用户信息
     */

    @Role(value = "ADMIN")
    @Override
    public IPage<UserVO> findByPage(Integer pageNum, Integer pageSize, Integer role, Integer id, String username) {
        return new LambdaQueryChainWrapper<>(User.class)
                                            .eq(Objects.nonNull(id), User::getId, id)
                                            .like(StringUtils.hasText(username), User::getUsername, username)
                                            .eq(Objects.nonNull(role), User::getRole, role)
                                            .orderByDesc(User::getUsername)
                                            .page(new Page<>(pageNum, pageSize, true))
                                            // 直接转换为UserVO（单条用户数据），而非自定义分页VO
                                            .convert(user -> BriupBeanUtils.copyProperties(user, UserVO.class));
    }

    /**
     * 3.更新用户信息
     */
    @Override
    public void updateUser(Integer id, String dob, String email, Integer gender, String realname, String telephone) {
        // 1. 校验ID和用户存在性
        BriupAsserts.notNull(id,"用户ID不能为空");
        User existingUser = getById(id);
        BriupAsserts.notNull(existingUser,"用户不存在，无法更新！");

        // 2. 构建UpdateWrapper，仅更新允许的字段
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        BriupAsserts.notNull(telephone,"电话为空");
        BriupAsserts.notNull(realname,"真实姓名为空");
        BriupAsserts.notNull(gender,"性别为空");
        BriupAsserts.notNull(dob,"生日为空");
        BriupAsserts.notNull(email,"邮箱为空");

        // 设置WHERE条件
        updateWrapper.eq("id", id);
        // 关键：添加要更新的字段
        updateWrapper.set("dob", dob)          // 生日
                .set("email", email)      // 邮箱
                .set("gender", gender)    // 性别
                .set("realname", realname)// 真实姓名
                .set("telephone", telephone); // 电话
        // 3. 执行更新
        update(updateWrapper);
    }


    /**
     * 4.修改用户头像
     */

    @Override
    public String updateIcon(Integer id, String userIcon) {
        // 参数校验
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("用户ID不能为空且必须大于0");
        }
        // 构建User对象用于更新
        User user = new User();
        user.setId(id);
        user.setIcon(userIcon);
        // 添加对头像路径格式的验证
        if (!isValidIconPath(user.getIcon())) {
            throw new IllegalArgumentException("头像路径格式不正确");
        }
        // 校验用户是否存在
        User existingUser = this.getById(user.getId());
        if (existingUser == null) {
            throw new IllegalArgumentException("用户不存在，无法修改头像");
        }
        // 构建UpdateWrapper：仅更新icon字段
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", user.getId())
                .set("icon", user.getIcon());

        // 执行更新
        boolean isSuccess = update(updateWrapper);
        if (!isSuccess) {
            throw new IllegalArgumentException("头像修改失败，请重试");
        }

        return "头像修改成功";
    }
    /**
     * 验证头像路径是否合法
     */
    private boolean isValidIconPath(String iconPath) {
        if (StringUtils.isEmpty(iconPath)) {
            return false;
        }
        // 检查是否为合法的头像路径格式
        return iconPath.startsWith("/api/uploads/avatars/") ||
                iconPath.startsWith("http") ||
                iconPath.matches("/uploads/avatars/.*\\.(jpg|jpeg|png|gif|bmp|webp)$");
    }

    //5.根据id删除用户信息
    @Role(value = "ADMIN")
    @Override
    public void deleteById(Integer id) {
        // 1. 查询用户是否存在
        User user = mapper.selectById(id);
        BriupAsserts.notNull(user, "待删除的用户不存在！");

        // 2. 校验用户是否已被逻辑删除
        if (user.getStatus() == 1) {
            throw new ServiceException("该用户已被逻辑删除，无需重复操作！");
        }
        User updateUser = new User();
        updateUser.setId(id);
        updateUser.setStatus(1);

        // 3. 执行更新操作
        mapper.updateById(updateUser);

    }
}
