package com.group2.cms.aop;


import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.group2.cms.entity.Log;
import com.group2.cms.entity.User;
import com.group2.cms.exception.ServiceException;

import com.group2.cms.util.UserInfoUtil;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


//@Component
//@Aspect
public class LogAspect {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    // 任何连接点
    @Pointcut("execution(* com.group2.cms.web.controller.*.*(..)) && @annotation(com.group2.cms.annotation.Log)")
    public void logpointCut(){
        // 用户访问web层代码就可以实现记录日志
    }

    @Before("logpointCut()")
    public void beforeAdvice(JoinPoint joinPoint){
        // 通过获取请求对象，实现日志对象的属性赋值
        // RequestContextHolder 是 Spring 提供的一个工具类，用于绑定当前线程的请求上下文
        // getRequestAttributes() 方法返回当前线程中与 Web 请求关联的 RequestAttributes 对象。
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        // 将 ra 强制转换为 ServletRequestAttributes，以便访问底层的 HttpServletRequest
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        String method = request.getMethod();            // 方法的请求方式
        String uri = request.getRequestURI();           // 方法的请求路径

        String token = request.getHeader("Authorization");

        // 逻辑1：当用户登录时，请求头没有认证信息，无法进行token解析操作
        String username = null;
        String realname = null;
        if(token != null){
            JWT jwt = JWTUtil.parseToken(token);
            username = jwt.getPayload("username").toString();
            realname = jwt.getPayload("realname").toString();
        }
        // 当用户进行登录访问时，没有提供请求头，可以请求参数来获取
        if("/login".equals(uri)){
            // 当token为空时，用户登录时，没有提供请求头，可以请求参数获取
            username = request.getParameter("username");
            // 访问数据库获取真实姓名
            LambdaQueryWrapper<User> wrapper =
                    Wrappers.lambdaQuery(User.class)
                            .eq(StringUtils.hasText(username), User::getUsername, username);
            User user = Db.getOne(wrapper);
            if(user != null){
                realname = user.getRealname();
            }
        }

        //  创建日志对象
        Log log = Log.builder()
                .time(LocalDateTime.now())
                .requestMethod(method)
                .requestUri(uri)
                .username(username)
                .realname(realname)
                .build();
        Db.save(log);
    }

    // @Around("mypointCut()")
    public Object aroudAdvice(ProceedingJoinPoint pjp) throws Throwable {
        String token = UserInfoUtil.getToken();
        Number idNumber = (Number) UserInfoUtil.getId(); // 把Object转为number再转为int
        int id = idNumber.intValue();
        Object result = pjp.proceed();

        User user = Db.getById(id, User.class);
        // 需要访问人，真实姓名，访问方式，访问路径，访问时间
            String username = user.getUsername();
            String realname = user.getRealname();

            // 通过签名拿到方法 取进一步获取访问方式，访问路径，访问时间
            Signature signature = pjp.getSignature();
            // AOP 中的前置安全校验确保当前
            // AOP 切点拦截的是「方法类型」的目标对象，只有当切点是方法时，才继续执行切面后续的业务逻辑；
            if(! (signature instanceof MethodSignature)){
                return result; // 程序不执行剩余代码
            }
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            GetMapping get = method.getAnnotation(GetMapping.class);
            PostMapping post = method.getAnnotation(PostMapping.class);
            PutMapping put = method.getAnnotation(PutMapping.class);

            String[] value = new String[]{""};
            String way = " ";
            if(get != null){
                // 路径 value 方式way Get
                value = get.value();
                way = "GET";
            }else if(post != null){
                value = post.value();
                way = "POST";
            }else if(put != null){
                value = put.value();
                way = "PUT";
            }
            String pathStr = String.join(",", value);
            if(value.length == 0 && way.length() == 0){
                throw new ServiceException("获取不到注释信息！");
            }

        Log log = Log.builder()
                    .username(username)
                    .realname(realname)
                    .requestMethod(way)
                    .requestUri(pathStr)
                    .time(LocalDateTime.now())
                    .build();

        Db.save(log);

        return result;
    }
}
