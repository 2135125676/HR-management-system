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
 *
 * 七牛云文件上传，使用对象存储
 * */
public class QinyFileUploadTests {
    @Test
    public void upload() throws Exception{
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = Configuration.create(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "niFvrP0MX70x5y7pfNbgGb5fSKDz6l5PTZ8FItqA";
        String secretKey = "hQNBl9vGwb-qcxYyZery6_6RstxLeE-iPmOP0VXE";
        String bucket = "cms-0101";
        //默认不指定key的情况下，以文件内容的hash值作为文件名

        // 设置文件保存的名字
        String filename = "a.png";
        String id = UUID.randomUUID().toString();
        String key = String.join("-", id, filename);

        // 上传的字节信息

        String path = "D:\\Backup\\Documents\\My Pictures\\Saved Pictures\\哈基米.jpg";
        byte[] uploadBytes = Files.readAllBytes(Path.of(path));
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        Response response = uploadManager.put(uploadBytes, key, upToken);
        //解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        System.out.println(putRet.key); // 文件名  c9b5b2a1-3561-4d0f-bced-0b1c38ad39d8-a.png
        System.out.println(putRet.hash);// 哈希值  Fk3WXwRYClXmlkrVnAKjyfe2BBWA
        // 合成图片最终的访问地址：
        String url = "http://t80ro68sj.hn-bkt.clouddn.com/" + putRet.key;
    }

}
