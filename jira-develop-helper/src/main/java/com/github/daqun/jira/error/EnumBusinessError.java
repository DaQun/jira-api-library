package com.github.daqun.jira.error;

import org.apache.commons.lang3.ObjectUtils;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * 2019/3/4 18:06
 * created by chenq
 */
public enum EnumBusinessError implements CommonError {
    //通用错误类型10001
    PARAMETER_VALLIDATION_ERROR(ErrorInfo.builder().errCode(DEFAULT_CODE).errMsg("参数不合法").build()),

    // 20000开头为用户信息相关错误定义
    FORBIDDEN_ERROR(ErrorInfo.builder().errStatus(Response.Status.FORBIDDEN).errCode(20002).build()),

    UNKNOW_ERROR(ErrorInfo.builder().errCode(90001).errMsg("未知错误").build());

    private int errCode;
    private String errMsg;
    private Response.Status errStatus;

    EnumBusinessError(ErrorInfo errorInfo) {
        this.errStatus = ObjectUtils.defaultIfNull(errorInfo.getErrStatus(), Response.Status.FORBIDDEN);
        this.errCode = ObjectUtils.defaultIfNull(errorInfo.getErrCode(), DEFAULT_CODE);
        this.errMsg = ObjectUtils.defaultIfNull(errorInfo.getErrMsg(), "操作失败");
    }

    EnumBusinessError(Response.Status status, int errCode, String errMsg) {
        this.errStatus = status;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public Map getErrorMap() {
        Map errorMap = new HashMap();
        errorMap.put("errCode", this.getErrCode());
        errorMap.put("errMsg", this.getErrMsg());
        return errorMap;
    }

    @Override
    public CommonError getError() {
        return this;
    }

    public Response.Status getErrStatus() {
        return errStatus;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
