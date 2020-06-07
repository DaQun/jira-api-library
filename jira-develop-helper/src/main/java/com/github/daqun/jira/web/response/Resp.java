package com.github.daqun.jira.web.response;

import com.github.daqun.jira.error.CommonError;

import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @Classname Resp
 * @Description
 * @Date 2019/3/13 14:40
 * @Created by chenq
 */
public class Resp {

    public static Response ok(Object data) {
        return Response.ok(data).build();
    }

    public static Response ok() {
        return Response.ok().build();
    }

    public static Response err(Object data) {
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrData(CommonError.DEFAULT_CODE, data)).build();
    }

    public static Response err(CommonError commonError) {
        return Response.status(commonError.getErrStatus()).entity(toRespData(commonError)).build();
    }

    private static ErrData toRespData(CommonError commonError) {
        return new ErrData(commonError.getErrCode(), commonError.getErrMsg());
    }

    @XmlAccessorType
    public static class ErrData {
        private int errCode;
        private Object errMsg;

        public ErrData(int errCode, Object errMsg) {
            this.errCode = errCode;
            this.errMsg = errMsg;
        }

        public int getErrCode() {
            return errCode;
        }

        public void setErrCode(int errCode) {
            this.errCode = errCode;
        }

        public Object getErrMsg() {
            return errMsg;
        }

        public void setErrMsg(String errMsg) {
            this.errMsg = errMsg;
        }
    }

}
