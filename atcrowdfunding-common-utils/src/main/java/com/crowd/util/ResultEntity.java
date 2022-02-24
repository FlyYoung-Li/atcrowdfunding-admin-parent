package com.crowd.util;

/**
 * @author 李晓扬
 * @version 1.0
 * @description: TODO
 * @date 2021/8/29 12:48
 */
public class ResultEntity<T> {
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";
    //用来判断当前请求处理的结果是成功还是失败
    private String result;
    //请求处理失败时返回的错误信息
    private String message;
    //要返回的数据
    private T data;

    public ResultEntity() {
    }

    public ResultEntity(String result, String message, T data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    public String getResult() {
        return result;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    //请求处理成功，不需要返回数据的工具方法
    public static <Type> ResultEntity<Type> successWithoutData(){
        return new ResultEntity<Type>(SUCCESS,null,null);

    //请求处理成功，需要返回数据的工具方法
    } public static <Type> ResultEntity<Type> successWithData(Type data){
        return new ResultEntity<Type>(SUCCESS,null,data);
    }
    //请求处理失败，返回错误信息的工具方法
    public static <Type> ResultEntity<Type> failed(String message){
        return new ResultEntity<Type>(FAILED,message,null);
    }
}
