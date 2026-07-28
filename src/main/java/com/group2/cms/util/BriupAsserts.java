package com.group2.cms.util;


import com.group2.cms.exception.ServiceException;

/**
 * 工具类
 * 工具类不能new操作，通过调用静态方法实现
 * 1.设置为抽象类
 * 2.构造方法私有化(通过反射获取私有构造器，创建对象) + final
 * @author liuzc
 */
public abstract class BriupAsserts {
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new ServiceException(message);
        }
    }

    /**
     * 如果条件不成立，抛出异常
     * @param expression
     * @param message
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new ServiceException(message);
        }
    }
}
