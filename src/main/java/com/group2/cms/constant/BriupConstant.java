package com.group2.cms.constant;

import cn.hutool.core.util.CharsetUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 系统使用的常量信息
 * @author qimz
 */
public interface BriupConstant {
    // 用户账号状态：正常
    int USER_STATUS_ENABLE = 0;
    // 用户账号状态：禁用
    int USER_STATUS_DISABELD = 1;

    // 最大允许失败次数
    int MAX_ATTEMPTS = 5;
    // 锁定15分钟（毫秒）
    long LOCK_DURATION_MS = 15 * 60 * 1000;
    // 内存存储失败次数
    Map<String, Integer> FAILURE_COUNT_MAP = new ConcurrentHashMap<>();
    Map<String, Long> LAST_ATTEMPT_TIME_MAP = new ConcurrentHashMap<>();
}
