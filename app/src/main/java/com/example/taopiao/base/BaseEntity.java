package com.example.taopiao.base;

/**
 * 定义数据实体基类，（一个基本的数据模型的封装）
 * @param <T>
 */

public class BaseEntity<T> {
    private double version;
    private static int SUCCESS_CODE=200;  //成功的code
    private int code;                     //响应码
    private String token;                 //token
    private String message;               //提示信息
    private T params;                    //返回具体数据

    public boolean isSuccess(){
        return getCode()==SUCCESS_CODE;
    }
    public static int getSuccessCode() {
        return SUCCESS_CODE;
    }

    public static void setSuccessCode(int successCode) {
        SUCCESS_CODE = successCode;
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public int getCode() {
        return code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getParams() {
        return params;
    }

    public void setParams(T params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "version=" + version +
                ", code=" + code +
                ", token='" + token + '\'' +
                ", message='" + message + '\'' +
                ", params=" + params +
                '}';
    }
}
