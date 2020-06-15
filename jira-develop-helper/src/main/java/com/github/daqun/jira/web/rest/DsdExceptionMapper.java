package com.github.daqun.jira.web.rest;

import com.github.daqun.jira.error.PluginException;
import com.github.daqun.jira.web.response.Resp;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *  resource异常处理
 *  2019/3/13 14:58
 * created by chenq
 */
@Provider
@Produces({MediaType.APPLICATION_JSON})
public class DsdExceptionMapper implements ExceptionMapper<PluginException> {
    public DsdExceptionMapper() {
    }

    @Override
    public Response toResponse(PluginException exception) {
        return Resp.err(exception);
    }
}