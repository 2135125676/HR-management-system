package com.group2.cms.web.interceptor;

import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;

import com.group2.cms.config.JWTProperties;

import com.group2.cms.util.BriupAsserts;
import com.group2.cms.util.UserInfoUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Data
@Slf4j
@Component // 将拦截器对象的管理交给spring 容器
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    JWTProperties jwtProperties;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 请求方式
        String method = request.getMethod();
        if(method.equals("OPTIONS")){
            // 预检请求，不进行拦截
            return true;
        }

        log.info("拦截的请求方法：{}，拦截的路径：{}", request.getMethod(),request.getRequestURI());
        log.info("拦截的请求头信息：{}", request.getHeader("host"));

        // 判断用户是否提供了请求头信息token、Auth
        String token = request.getHeader("Authorization");
        log.info("认证信息：{}", token);

        BriupAsserts.notNull(token, "用户未登录");

        /** 校验token信息是否合法？
         1.token无效,请重新登录
         2.token过期，请重新登录
         */
        boolean verify = JWTUtil.verify(token, jwtProperties.getKey().getBytes()); // false
        BriupAsserts.isTrue(verify, "认证无效，请重新登录");
        JWTValidator.of(token).validateDate();

        //通过工具类记录当前操作的用户
        String username = JWTUtil.parseToken(token).getPayload("username")
                .toString();
        UserInfoUtil.setName(username);

        // 通过上下文对象来保存用户信息与ThreadLoacl中,之后可能用解析器把token中的数据解析出来
        UserInfoUtil.setToken(token);

        Object id = JWTUtil.parseToken(token).getPayload("userId");

        UserInfoUtil.setId(id);

        //return false; // 没有通过拦截，默认返回空信息
        return true; //通过拦截，继续访问 controller
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("afterCompletion");
        UserInfoUtil.clear();
    }
}
