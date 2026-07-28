package com.group2.cms.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group2.cms.config.QiniuOssProperties;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * 七牛云文件上传工具类（Spring Bean）
 * @author liuzc
 */
@Component // 标记为Bean，可被Spring注入
@RequiredArgsConstructor // Lombok自动生成构造器，注入依赖
public class QiniuFileUploadUtil {

    // 注入配置类和ObjectMapper
    private final QiniuOssProperties qiniuOssProperties;
    private final ObjectMapper objectMapper;

    /**
     * 上传文件到七牛云
     * @param file 前端传入的MultipartFile文件
     * @return 文件访问URL
     * @throws Exception 上传异常
     */
    public String uploadFile(MultipartFile file) throws Exception {
        // 1. 校验文件是否为空
        if (file.isEmpty()) {
            throw new IllegalArgumentException("上传失败：文件不能为空");
        }

        // 2. 初始化七牛云配置
        Configuration cfg = Configuration.create(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
        UploadManager uploadManager = new UploadManager(cfg);

        // 3. 生成唯一文件Key（UUID + 原始文件名，避免重复）
        String originalFilename = file.getOriginalFilename(); // 获取前端传入的文件名
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String fileKey = uuid + "-" + originalFilename;

        // 4. 获取文件字节（替代固定本地路径）
        byte[] uploadBytes = file.getBytes();

        // 5. 生成上传凭证
        Auth auth = Auth.create(qiniuOssProperties.getAccessKey(), qiniuOssProperties.getSecretKey());
        String upToken = auth.uploadToken(qiniuOssProperties.getBucket());

        // 6. 执行上传
        Response response = uploadManager.put(uploadBytes, fileKey, upToken);
        DefaultPutRet putRet = objectMapper.readValue(response.bodyString(), DefaultPutRet.class);

        // 7. 拼接文件访问URL
        return qiniuOssProperties.getDomain() + putRet.key;
    }
}