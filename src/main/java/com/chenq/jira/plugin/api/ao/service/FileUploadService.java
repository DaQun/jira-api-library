package com.chenq.jira.plugin.api.ao.service;

import com.chenq.jira.plugin.api.ao.entity.FileUploadEntity;

/**
 * 2020/4/21 11:40
 * Created by chenq
 */
public interface FileUploadService {

    /**
     * 创建上传记录
     * @param originalName 文件初始名称
     * @param parsedName 转换后的名称
     * @param size 文件大小
     * @return 创建后的实体
     */
    FileUploadEntity createRecord(String originalName, String parsedName, long size);

    /**
     * 删除文件
     * @param fileId 文件id
     */
    void deleteFile(int fileId);

    /**
     * 获取记录
     * @param fileid id
     * @return 实体
     */
    FileUploadEntity getRecord(int fileid);

}
