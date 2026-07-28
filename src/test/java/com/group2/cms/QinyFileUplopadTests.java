package com.group2.cms;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

/**
 * 七牛云文件上传，使用对象存储
 * @author Administrator
 * @date 2025-12-29
 * @description TODO
 */
public class QinyFileUplopadTests {
    @Test
    public void upload() throws Exception{
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = Configuration.create(Region.region0());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "ZSX1EkPkwQJOoBfvpoSNnddvINnU7mvqQh-QK5bB";
        String secretKey = "JKUr51rYuCKRxa_qW8r7iRd-7oiJGULILjiwzfMo";
        String bucket = "cms-0103";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String filename = "test.jpg";
        String id = UUID.randomUUID().toString();
        String key = String.join("-", id, filename);

        String path = "\"C:\\Users\\xiaot\\Pictures\\Screenshots\\OIP-C.jpg\"";
        byte[] uploadBytes = Files.readAllBytes(Path.of(path));
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        Response response = uploadManager.put(uploadBytes, key, upToken);
        //解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        System.out.println(putRet.key);
        System.out.println(putRet.hash);

    }
}
