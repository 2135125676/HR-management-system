package com.group2.cms;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SM4;

public class EncryptTest {
    public static void main(String[] args) {
        String password = "123"; // dc2ada59b357d6ae7d1a809e4c8ce7bf
        // 进行sm4 密码加密
        // 使用 ECB 模式 + PKCS5Padding 填充
        SM4 sm4 = SmUtil.sm4();
        // 将 password使用 UTF-8 编码为字节，然后进行 SM4 加密。结果以 十六进制字符串（Hex） 形式返回。
        String encryptPassword = sm4.encryptHex(password);
        // 将十六进制密文解密，并按 UTF-8 解码为原始字符串。
        String str = sm4.decryptStr(encryptPassword, CharsetUtil.CHARSET_UTF_8);

        System.out.println("加密后密码：" + encryptPassword);
        System.out.println("解密后密码：" + str);

    }
}
