package com.chenq.jira.plugin.module.rest.interceptor;

import com.github.daqun.jira.error.EnumBusinessError;
import com.github.daqun.jira.web.response.Resp;
import lombok.extern.log4j.Log4j;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * 异常捕获类
 * 2019/4/23 14:18
 * created by chenq
 */
@Provider
@Produces({MediaType.APPLICATION_JSON})
@Log4j
public class ThrowableMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable throwable) {
        log.error("ThrowableMapper:rest api exec error!!!", throwable);
        return Resp.err(throwable.getMessage() == null ? EnumBusinessError.UNKNOW_ERROR.getErrMsg() : throwable.getMessage());
    }
}
