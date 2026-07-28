package com.group2.cms.util;

/**
 * 用户信息工具类
 * 可以在程序任意位置调用方法获取当前访问的用户信息
 *
 * 通过直接操作每个线程的成员变量。实现数据隔离
 * @author liuzc
 */
public class UserInfoUtil {
    //静态ThreadLocal，存储当前请求的用户名
    public final static ThreadLocal<String> threadLocalUser = new ThreadLocal<>();
    // 存储当前请求的token
    public final static ThreadLocal<String> threadLocalToken = new ThreadLocal<>();
    // 存储当前请求的id
    public final static ThreadLocal<Object> threadLocalId = new ThreadLocal<>();

    public static void setName(String name){
        threadLocalUser.set(name);
    }
    public static String getName(){
        return threadLocalUser.get();
    }

    public static void setToken(String Token){threadLocalToken.set(Token);}
    public static String getToken(){return threadLocalToken.get();}

    public static void setId(Object id){threadLocalId.set(id);}
    public static Object getId(){return threadLocalId.get();}

    public static void clear(){
        threadLocalUser.remove();
        threadLocalToken.remove();
    }
}
