package com.github.daqun.jira.error;

/**
 * @Description
 * @Date 2019/4/13 16:38
 * @Created by chenq
 */
public final class DsdException extends DsdBaseException {

    // 直接接受EmDSdError的传参用于构造业务异常
    public DsdException(CommonError commonError) {
        // 父类有一些初始化的方法
        super();
        this.commonError = commonError;
    }

    public DsdException(CommonError commonError, String errMsg) {
        this(commonError);
        this.commonError.setErrMsg(errMsg);
    }

    @Override
    public CommonError getError() {
        return this.commonError;
    }
}
