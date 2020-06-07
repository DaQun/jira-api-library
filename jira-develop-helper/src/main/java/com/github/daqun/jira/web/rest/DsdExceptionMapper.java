package com.github.daqun.jira.web.rest;

import com.github.daqun.jira.error.DsdException;
import com.github.daqun.jira.web.response.Resp;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @Classname ExceptionMapper
 * @Description resource异常处理
 * @Date 2019/3/13 14:58
 * @Created by chenq
 */
@Provider
@Produces({MediaType.APPLICATION_JSON})
public class DsdExceptionMapper implements ExceptionMapper<DsdException> {
    public DsdExceptionMapper() {
    }

    @Override
    public Response toResponse(DsdException exception) {
        return Resp.err(exception);
    }
}