package com.github.daqun.jira.license;

import lombok.Builder;
import lombok.Data;

import java.io.*;

/**
 * @Classname LicenseData
 * @Description
 * @Date 2019/4/9 14:04
 * @Created by chenq
 */
@Data
@Builder
public class LicenseData implements Serializable {
    // 购买插件的公司名
    private String companyName;
    // 插件名
    private String pluginName;
    // 插件版本
    private String pluginVersion;
    // 用户限制
    private Integer userLimit;
    // 有效期截止日
    private String expiry;

    /**
     * 将对象转为字节数组
     */
    public byte[] toByte() {
        byte[] bytes = new byte[0];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(this);
            bytes = byteArrayOutputStream.toByteArray();

            objectOutputStream.close();
            byteArrayOutputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return bytes;
    }

    /**
     * 转为对象
     * @param bytes 字节数组
     * @return licenseData对象
     */
    public static LicenseData toObject(byte[] bytes) {
        LicenseData licenseData = null;
        ByteArrayInputStream byteArrayOutputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream;
        try {
            objectInputStream = new ObjectInputStream(byteArrayOutputStream);
            licenseData = (LicenseData) objectInputStream.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return licenseData;
    }

}
