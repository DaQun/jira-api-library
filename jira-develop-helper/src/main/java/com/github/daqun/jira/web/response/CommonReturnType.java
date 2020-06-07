package com.github.daqun.jira.web.response;

/**
 * @Classname CommonReturnType
 * @Description
 * @Date 2019/1/17 11:36
 * @Created by chenq
 */
public class CommonReturnType {

    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";

    private String status;
    private Object data;

    public static CommonReturnType ok(Object result) {
        return CommonReturnType.create(result, SUCCESS);
    }
    public static CommonReturnType create(Object result) {
        return CommonReturnType.create(result, SUCCESS);
    }

    public static CommonReturnType create(Object result, String status) {
        CommonReturnType type = new CommonReturnType();
        type.setStatus(status);
        type.setData(result);
        return type;
    }

    public static CommonReturnType fail(Object result) {
        return CommonReturnType.create(result, FAIL);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
