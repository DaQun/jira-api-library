package com.github.daqun.jira.error;

import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * 2019/3/4 18:04
 * created by chenq
 */
public interface CommonError {
    int DEFAULT_CODE = 10001;

    Response.Status getErrStatus();
    int getErrCode();
    String getErrMsg();
    CommonError setErrMsg(String errMsg);
    CommonError getError();
    Map getErrorMap();
}
