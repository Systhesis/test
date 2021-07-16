package com.neil.test.mvp.bean;

import java.util.List;

/**
 * @author zhongnan1
 * @time 2021/7/16 15:06
 *
 */
public class BaseArrayBean<T> {

    /**
     * status : 1
     * msg : 获取成功
     * result : [] 数组
     */

    private int errorCode;
    private String errorMsg;
    private List<T> result;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
