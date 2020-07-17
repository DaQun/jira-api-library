package com.chenq.jira.plugin.util;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.sal.api.ApplicationProperties;
import com.chenq.jira.plugin.api.ao.entity.FileUploadEntity;
import com.chenq.jira.plugin.api.ao.service.FileUploadService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

/**
 * 文件（图片）处理类
 * 2020/4/21 13:10
 * Created by chenq
 */
@Slf4j
@Component
public class FileUploadManager {

    private final FileUploadService fileUploadService;

    @Inject
    public FileUploadManager(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    /**
     * 从servlet中读取图片，这里的图片一般只有一张
     * @param servletRequest servlet
     * @throws Exception 异常
     * @return 将文件信息放在数据库之后得到的entity id集合
     */
    public   List<Integer> imageUpload(HttpServletRequest servletRequest) throws Exception {
        File realPath = getImageDirectory();

        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
        List<FileItem> items = servletFileUpload.parseRequest(servletRequest);
        Iterator<FileItem> iterator = items.iterator();
        List<Integer> imageFileIds = Lists.newArrayList();
        while (iterator.hasNext()) {
            FileItem item = iterator.next();
            String filename = item.getName();
            if (!item.isFormField() && StringUtils.isNotBlank(filename)) {
                String formattedFileName = formatFileName(item.getName());
                File file = new File(realPath + File.separator + formattedFileName);
                item.write(file);
                FileUploadEntity fileUploadEntity = fileUploadService
                        .createRecord(item.getName(), formattedFileName, item.getSize());
                imageFileIds.add(fileUploadEntity.getID());
            }
        }

        return imageFileIds;
    }

    /**
     * 删除文件
     * @param fileId 文件id
     */
    public void deleteFile(int fileId) {
        FileUploadEntity record = fileUploadService.getRecord(fileId);
        new File(getImageDirectory(), record.getParsedName()).delete();
        fileUploadService.deleteFile(fileId);
    }

    /**
     * 根据fileId获取图片
     * @param fileId 文件id
     * @return
     * @throws FileNotFoundException
     */
    public Pair<File, String> getFile(int fileId) throws FileNotFoundException {
        FileUploadEntity record = fileUploadService.getRecord(fileId);
        String parsedName = record.getParsedName();
        File file = new File(getImageDirectory(), parsedName);

        return Pair.of(file.exists() ? file : null, record.getOriginalName());
    }

    /**
     * 在文件名前加当前时间的毫秒数，防止文件名重复
     * @param originalName 初始文件名
     * @return 格式化后的文件名
     */
    private String formatFileName(String originalName) {
       return System.currentTimeMillis()+"-"+ originalName;
    }

    /**
     * 获取图片存放路径，默认是放在jira home目录下的jira-files下
     * @return 图片存放路径
     */
    private static File getImageDirectory() {
        ApplicationProperties applicationProperties =  ComponentAccessor.getOSGiComponentInstanceOfType(ApplicationProperties.class);

        File homeDirectory = applicationProperties.getHomeDirectory();
        File file = new File(homeDirectory, "jira-files");
        if (!file.exists()) {
            file.mkdirs();
        }

        return file;
    }

}
