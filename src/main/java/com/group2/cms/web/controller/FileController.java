package com.group2.cms.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.group2.cms.util.Result;
import com.group2.cms.util.QiniuFileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 通用模块：文件管理操作
 * <p>
 * 当前代码有什么问题；
 * 1.代码中使用sk ak name都是配置信息，不能直接写死代码中，写在统一配置文件中：
 * 自定义配置类自动读取封装xxxProperties对象
 * 2.考虑是否可以将刚才写文件上传代码封装工具类代码
 * 将工具类设计为Bean对象   非静态方法
 * 3.从MultipartFile对象中真正解析到外面文件名和字节信息
 * 直接调用 getXXXX getXXXX
 */
@RestController
@RequiredArgsConstructor
public class FileController {
    // 注入封装好的工具类
    private final QiniuFileUploadUtil qiniuFileUploadUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/file/upload")
    public Result upload(MultipartFile file) {
        try {
            // 调用工具类上传文件
            String fileUrl = qiniuFileUploadUtil.uploadFile(file);
            return Result.success(fileUrl);
        } catch (IllegalArgumentException e) {
            // 业务异常（如文件为空）
            return Result.error(e.getMessage());
        } catch (Exception e) {
            // 系统异常
            e.printStackTrace();
            return Result.error("文件上传失败：系统异常");
        }
}
}
