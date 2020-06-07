package com.github.daqun.jira.encryption;

import java.util.Base64;

public class CryptUtils {
    // 公钥
    public static final String RSA_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDNU3keYj7mwuN+75Y/fF5JocEKIMlRfj32qdTJqA1h/K8IpkIUNDNyOY2XZXgskHtGFuo/BzG/uv4FTXHcBcef4gjvFKNkhRlIdrG4MQKpSlJLN7BWJC2ens7m9H7TUTh8x1LEEt4jpEI3V8T9nd5sWfc7lV6QVH0nIQlTxxuvtQIDAQAB";

    /**
     * 加密数据。RSA 算法加密
     */
    public static byte[] decrypt(byte[] data) {
        byte[] encrypted = null;
        try {
            encrypted = RSACrypt.decryptByPublicKey(data, RSA_PUBLIC_KEY);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return encrypted;
    }

    /**
     * 根据license字符串解密
     * @param license
     * @return
     */
    public static byte[] decrypt(String license) {
        return decrypt(Base64.getDecoder().decode(license));
    }

    /**
     * 解密数据。RSA 算法加密
     */
    public static byte[] encrypt(byte[] data, String privateKey) {
        byte[] decrypted = null;
        try {
            decrypted = RSACrypt.encryptByPrivateKey(data, privateKey);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return decrypted;
    }


}
