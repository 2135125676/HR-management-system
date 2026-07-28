package com.group2.cms.aop;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.group2.cms.annotation.Role;
import com.group2.cms.entity.User;
import com.group2.cms.util.BriupAsserts;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

/**
 * 补充模块：拦截所有的service层中添加@Role注解的方法，根据当前访问用户的权限与
 * 注解中要求权限是否相同，来实现判断，如果权限相同，继续执行，如果权限不同，抛出异常，提示用户：
 * 无管理员权限
 */
@Component
@Aspect
public class RoleAspect {
    // 切入点规则
    @Pointcut("execution(* com.group2.cms.service..*.*(..)) && @annotation(com.group2.cms.annotation.Role)")
    public void rolepointCut(){
    }

    @Before("rolepointCut()")
    public void before(JoinPoint joinPoint){
    }

    // 通知：环绕通知
    @Around("rolepointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        // 将 ra 强制转换为 ServletRequestAttributes，以便访问底层的 HttpServletRequest
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String token = request.getHeader("Authorization");


        String username = null;
        if(token != null){
            JWT jwt = JWTUtil.parseToken(token);
            username = jwt.getPayload("username").toString();
        }
        LambdaQueryWrapper<User> wrapper =
                Wrappers.lambdaQuery(User.class)
                        .eq(StringUtils.hasText(username), User::getUsername, username);
        User user = Db.getOne(wrapper);
        int role = user.getRole();
        String permission = null;

        BriupAsserts.notNull(role, "权限错误，请联系管理员");

        Signature signature = pjp.getSignature();
        // MethodSignature接口中包含getMethod获取method对象
        if(! (signature instanceof MethodSignature)){
            throw new RuntimeException("系统错误，无法获取方法信息");
        }
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Role annotation = method.getAnnotation(Role.class); // 获取到了方法的值
        BriupAsserts.notNull(annotation, "系统错误，未知权限");
        if(role == 1)
            permission = "USER";
        if(role == 0)
            permission = "ADMIN";

        BriupAsserts.isTrue(annotation.value().equals(permission), "无管理员权限");

        // 权限校验通过后，再执行目标方法
        Object result = pjp.proceed();


        return result;
    }

}
