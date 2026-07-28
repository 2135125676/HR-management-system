package com.group2.cms.util;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 全局获取请求工具类
 * @author liuzc
 * @since 2025-12-20
 * @version 1.0
 * @author liuzc
 * @see org.springframework.web.context.request.RequestContextHolder
 */
public abstract class BriupRequestContextHolder {//j获取执行方法线程对象的局部数据
    //ThreadLocal本地线程空间
    public static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal();

    /**
     * 保存当前线程的请求信息
     * @param request 请求对象
     */
    public static void setRequest(HttpServletRequest request) {
        requestHolder.set(request);
    }

    /**
     * 获取当前线程的请求信息
     * @return 请求对象
     */
    public static HttpServletRequest getRequest(){
        return requestHolder.get();
    }

    /**
     * 重置当前线程的请求信息
     */
    public void reset(){
        requestHolder.remove();
    }
}
