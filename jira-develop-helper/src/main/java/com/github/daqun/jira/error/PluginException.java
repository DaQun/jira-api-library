package com.github.daqun.jira.error;

/**
 *  2019/4/13 16:38
 * created by chenq
 */
public final class PluginException extends PluginBaseException {

    // 直接接受EmDSdError的传参用于构造业务异常
    public PluginException(CommonError commonError) {
        // 父类有一些初始化的方法
        super();
        this.commonError = commonError;
    }

    public PluginException(CommonError commonError, String errMsg) {
        this(commonError);
        this.commonError.setErrMsg(errMsg);
    }

    @Override
    public CommonError getError() {
        return this.commonError;
    }
}
