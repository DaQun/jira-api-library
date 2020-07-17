package com.chenq.jira.plugin.api.ao.service.impl;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.chenq.jira.plugin.api.ao.entity.FileUploadEntity;
import com.chenq.jira.plugin.api.ao.service.FileUploadService;
import net.java.ao.DBParam;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * 2020/4/21 13:03
 * Created by chenq
 */
@Component
public class FileUploadServiceImpl implements FileUploadService {
    @ComponentImport
    private final ActiveObjects ao;

    @Inject
    public FileUploadServiceImpl(ActiveObjects ao) {
        this.ao = ao;
    }

    @Override
    public FileUploadEntity createRecord(String originalName, String parsedName, long size) {
        return ao.create(FileUploadEntity.class,
                new DBParam(FileUploadEntity.COLUMN.ORIGINAL_NAME.name(), originalName),
                new DBParam(FileUploadEntity.COLUMN.PARSED_NAME.name(), parsedName),
                new DBParam(FileUploadEntity.COLUMN.SIZE.name(), size)
        );
    }

    @Override
    public void deleteFile(int fileId) {
        FileUploadEntity record = getRecord(fileId);
        if (record != null) {
            record.setDeleted(true);

            record.save();
        }
    }

    @Override
    public FileUploadEntity getRecord(int fileid) {
        return ao.get(FileUploadEntity.class, fileid);
    }
}
