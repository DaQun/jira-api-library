package com.chenq.jira.plugin.module.rest.interceptor;

import com.github.daqun.jira.web.response.Resp;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * 用来拦截rest接口中的抛出的异常，返回同一的异常信息
 * 2019/3/14 17:05
 * created by chenq
 */
@Provider
@Produces({MediaType.APPLICATION_JSON})
@Slf4j
public class JiraExceptionMapper implements ExceptionMapper<Exception> {
    public JiraExceptionMapper() {
    }

    @Override
    public Response toResponse(Exception exception) {
        log.error("DsdExceptionMapper:rest api exec error!!!", exception);
        return Resp.err(exception);
    }
}