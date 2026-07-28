package com.group2.cms.exception;

import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.json.JSONException;

import com.group2.cms.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 全局异常处理器
 * 将Controller中所有的异常代码，统一写在全局异常处理器中
 */
@Slf4j//日志门面框架
@RestControllerAdvice//通知 增强
public class GlobalExceptionHandler {
    //处理所有的异常信息

    /**
     * 处理服务器内部错误的异常：语法异常，例如空指针，类型转化异常
     * @param ex
     * @return
     * @author liuzc
     */
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception ex) {
        commomHandle(ex);
        return Result.error("程序内部错误，联系管理员");
    }

    /**
     * 处理拦截器抛出异常，token无效，过期，不合法
     * @param ex
     * @return 提示用户token无效或过期，请重新登录
     */
    @ExceptionHandler({ValidateException.class, JSONException.class})
    public Result<String> handleJWTException(Exception ex){
        commomHandle(ex);
        //区分JWT校验信息
        if(ex instanceof ValidateException){
            return Result.error("token认证过期，请重新登录");
        }
        return Result.error("认证无效，请重新登录");
    }

    @ExceptionHandler(BindException.class)
    public Result<String> handleValidException(BindException ex){
        commomHandle(ex);
        // 获取所有的参数校验错误的信息，一次返回给用户，减少多次重复的参数提交
        List<String> messages = ex.getFieldErrors()
                .stream().map(error -> error.getDefaultMessage())
                .toList();
        // 将多个错误信息以！分割显示
        return Result.error(String.join("!", messages));
    }
    /**
     * service层抛出异常：密码错误，权限不足
     * @param ex
     * @return 根据异常的message信息直接显示
     */
    @ExceptionHandler(ServiceException.class)
    public Result<String> handleServiceException(Exception ex){
        commomHandle(ex);
        //区分不同类型异常，返回不同的提示信息
        //将异常的信息返回给浏览器
        return Result.error(ex.getMessage());
    }



    private void commomHandle(Exception ex){
        //打印异常信息
        ex.printStackTrace();
        //异常日志记录
        log.error(ex.getMessage());
    }
    @ExceptionHandler(DeleteException.class)
    public Result<String> handleDeleteException(DeleteException ex){
        commonHandle(ex);
        return Result.error("禁止删除自身管理员账号!");
    }

    private void commonHandle(DeleteException ex) {
        log.error(ex.getMessage());

    }

}
