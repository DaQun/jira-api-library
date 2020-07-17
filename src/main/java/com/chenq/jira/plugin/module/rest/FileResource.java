package com.chenq.jira.plugin.module.rest;

import com.chenq.jira.plugin.api.ao.service.FileUploadService;
import com.chenq.jira.plugin.util.FileUploadManager;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 2020/7/16 14:46
 * Created by chenq
 */
@Slf4j
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/file")
public class FileResource {

    private final FileUploadService fileUploadService;
    private final FileUploadManager fileUploadManager;

    public FileResource(FileUploadService fileUploadService, FileUploadManager fileUploadManager) {
        this.fileUploadService = fileUploadService;
        this.fileUploadManager = fileUploadManager;
    }

    /**
     * 上传图片
     * @param servletRequest request
     */
    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response imgUpload(@Context HttpServletRequest servletRequest) throws Exception {
        List<Integer> fileIds = fileUploadManager.imageUpload(servletRequest);
        Map<String, Integer> result = Maps.newHashMap();
        if (fileIds.size() > 0) {
            result.put("fileId", fileIds.get(0));
        }

        return Response.ok(result).build();
    }

    /**
     * 获取图片
     * @param fileId 图片Id
     */
    @GET
    @Path("{fileId}")
    @Produces("image/*")
    public Response getImg(@PathParam("fileId") Integer fileId) throws FileNotFoundException {
        Pair<File, String> file = fileUploadManager.getFile(fileId);

        return Response.ok(file.getKey())
                .header("Content-Disposition", "inline; filename=\"" + file.getRight() + "\"")
                .header("Cache-Control", "public")
                .header("Cache-Control", "max-age=86400")
                .build();
    }

    /**
     * 导出excel
     * TODO 导出部门下的雇员列表
     * @param groupId 部门ID
     */
    @GET
    @Path("export-groupusers/{groupId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response excel(@PathParam("groupId") Integer groupId) throws IOException {
        ByteArrayOutputStream write = null;

        return Response.ok(write.toByteArray(), MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + new String( (System.currentTimeMillis()+ "").getBytes("gbk"), "iso8859-1") + ".xls\"")
                .build();
    }
}
