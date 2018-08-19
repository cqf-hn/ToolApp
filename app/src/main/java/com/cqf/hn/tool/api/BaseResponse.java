package com.cqf.hn.tool.api;

/**
 * Created by Admin on 2018/3/12.
 */

public class BaseResponse {

    /**
     * SUCCESS
     * -->statusCode : SUCCESS
     * -->code : 200
     * -->message : success
     *
     *
     * ERROR
     * -->statusCode: ERROR
     * -->errorCode: 401
     * -->message: 登录超时,请重新登录
     */

    private String statusCode;//SUCCESS  ERROR
    private String code;
    private String errorCode;
    private String message;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return statusCode.equals("SUCCESS");
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
