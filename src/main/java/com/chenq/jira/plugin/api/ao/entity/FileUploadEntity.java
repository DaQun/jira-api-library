package com.chenq.jira.plugin.api.ao.entity;

import com.github.daqun.jira.ao.column.CreateColumn;
import com.github.daqun.jira.ao.column.DeleteColumn;
import net.java.ao.schema.Table;

/**
 * 2020/4/21 11:37
 * Created by chenq
 */
@Table("FILE_UPLOAD")
public interface FileUploadEntity extends CreateColumn, DeleteColumn {
    /**
     * 文件初始名
     */
    String getOriginalName();
    void setOriginalName(String originalName);

    /**
     * 转换后的名称
     */
    String getParsedName();
    void setParsedName(String parsedName);

     /**
     * 文件大小
     */
    long getSize();
    void setSize(long size);

    enum COLUMN {
        ORIGINAL_NAME,PARSED_NAME,SIZE
    }
}
