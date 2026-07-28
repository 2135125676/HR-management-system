package com.group2.cms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class CryptoConfig {

    @Value("${app.crypto.sm4-key}")
    private String sm4Key;

    // 获取密钥字节数组
    public byte[] getSm4Key() {
        return sm4Key.getBytes(StandardCharsets.UTF_8);
    }
}