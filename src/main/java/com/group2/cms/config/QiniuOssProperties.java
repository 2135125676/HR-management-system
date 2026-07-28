package com.group2.cms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 七牛云OSS配置属性类
 * @author liuzc
 */
@Data
@Component
@ConfigurationProperties(prefix = "qiniu.oss")
public class QiniuOssProperties {
    private String accessKey;   // AK
    private String secretKey;   // SK
    private String bucket;      // 存储空间名
    private String domain;      // 访问域名
}