package com.group2.cms.service.impl;

import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SM4;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.group2.cms.config.CryptoConfig;
import com.group2.cms.config.JWTProperties;
import com.group2.cms.entity.User;
import com.group2.cms.exception.ServiceException;
import com.group2.cms.service.ILoginService;
import com.group2.cms.service.dto.LoginDTO;
import com.group2.cms.util.BriupAccounts;
import com.group2.cms.util.BriupAsserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static com.group2.cms.constant.BriupConstant.USER_STATUS_ENABLE;
import static com.group2.cms.util.BriupAccounts.resetFailedAttempts;

/**
 * 登录模块实现类
 */
@Service
public class LoginServiceImpl implements ILoginService {
    @Autowired
    private JWTProperties jwtProperties;
    @Autowired
    private CryptoConfig cryptoConfig;

    @Override
    public String login(LoginDTO dto) {
        // 1.参数校验 ，用户名和密码不能为空，都通过注解实现
        String username = dto.getUsername();
        String password = dto.getPassword();

        // ===== 检查是否被锁定 =====
        BriupAccounts.isAccountLocked(username);

        // 2.查询数据库
        // 判断用户名是否存在?
/*        User user = new LambdaQueryChainWrapper<User>(User.class)
                .eq(User::getUsername, username).one();
        BriupAsserts.notNull(user, "用户名不存在");
        */
        // user对象可能存在或不存在 ，oneOpt拿到容器
        User user = new LambdaQueryChainWrapper<User>(User.class)
                .eq(User::getUsername, username)
                .oneOpt()
                .orElseThrow(() -> new ServiceException("用户名或密码错误"));

        SM4 sm4 = SmUtil.sm4(cryptoConfig.getSm4Key());
        // 将用户输入的密码转化为密文
        String encryptPassword = sm4.encryptHex(password);

        BriupAccounts.isPasswordIncorrect(user.getPassword().equals(encryptPassword),username, "用户名或密码错误" );
        // 密码正确，重置失败次数
        resetFailedAttempts(username);

        BriupAsserts.isTrue(user.getStatus() == USER_STATUS_ENABLE, "账号已禁用");

        // 通过上述逻辑校验，表示用户登录校验成功，返回认证信息
        Map<String, Object> userMap = Map.of(
                "userId", user.getId(),
                "username", user.getUsername(),
                "password", user.getPassword(),
                "realname", user.getRealname());
        // 生成token返回                                                    统一编码规则
        return JWTUtil.createToken(userMap,jwtProperties.getKey().getBytes(StandardCharsets.UTF_8));
    }
}
