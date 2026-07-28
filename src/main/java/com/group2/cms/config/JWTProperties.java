package com.group2.cms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 配置参数类：自动读取外部yml文件配置信息，加载bean对象中
 * */

@Component
@ConfigurationProperties(prefix = "jwt") // 映射全局变量数据
@Data
public class JWTProperties {
    private String key; // 秘钥：写字符串类型
    private int expTime;
}
