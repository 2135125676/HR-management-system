package com.group2.cms.util;

import com.group2.cms.exception.ServiceException;

import static com.group2.cms.constant.BriupConstant.*;

public abstract class BriupAccounts {
    // ===== 检查是否锁定 =====
    public static void isAccountLocked(String username) {
        Integer count = FAILURE_COUNT_MAP.get(username);
        Long lastTime = LAST_ATTEMPT_TIME_MAP.get(username);

        // 情况1：从未失败过 → 不锁定
        if (count == null || count < MAX_ATTEMPTS) {
            return;
        }

        // 情况2：失败次数超限且仍在锁定期内 → 锁定
        if (lastTime != null && System.currentTimeMillis() - lastTime < LOCK_DURATION_MS) {
            throw new ServiceException("登录失败次数过多，请15分钟后重试");
        }

        // 情况3：失败次数超限但已超时 → 自动解锁
        resetFailedAttempts(username);
    }

    // 判断密码是否正确
    public static void isPasswordIncorrect(boolean expression, String username, String msg) {
        if (!expression) {
            // ===== 记录失败次数 =====
            recordFailedAttempt(username);
            throw new ServiceException(msg);
        }
    }

    // ===== 记录失败次数 =====
    public static void recordFailedAttempt(String username) {
        // 失败次数+1（ConcurrentHashMap安全操作）
        FAILURE_COUNT_MAP.merge(username, 1, Integer::sum);
        // 记录最后失败时间
        LAST_ATTEMPT_TIME_MAP.put(username, System.currentTimeMillis());
    }

    // ===== 重置失败次数（登录成功或超时后）=====
    public static void resetFailedAttempts(String username) {
        FAILURE_COUNT_MAP.remove(username);
        LAST_ATTEMPT_TIME_MAP.remove(username);
    }

}
