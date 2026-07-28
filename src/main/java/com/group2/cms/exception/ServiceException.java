package com.group2.cms.exception;

/**
 * 自定义异常：必须是Exception的子类或者间接子类
 * JVM 异常处理机制
 * @author liuzc
 */
public class ServiceException extends RuntimeException {
    public ServiceException(String message) {

        super(message);
    }
}
