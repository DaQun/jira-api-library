package com.github.daqun.jira.error;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 *  2019/4/13 17:02
 * created by chenq
 */
@Builder
@Getter
@Setter
public class ErrorInfo implements CommonError  {
    private int errCode;
    private String errMsg;
    private Response.Status errStatus;

    @Override
    public CommonError getError() {
        return this;
    }

    @Override
    public Map getErrorMap() {
        Map errorMap = new HashMap();
        errorMap.put("errCode", this.getErrCode());
        errorMap.put("errMsg", this.getErrMsg());
        return errorMap;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
