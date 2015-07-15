package com.jiayantech.jyandroid.model.web;

/**
 * Created by liangzili on 15/7/15.
 * JS调用native时的Json数据
 *
 */


public class BaseJsCall<T> {
    /**
     * @param action 本次请求要做的事情
     * @param success 当native逻辑处理正确时，通过调用它来响应Js的本次调用
     * @param error 当native出错时，通过调用它来响应Js的本次调用
     *
     */
    public String action;
    public String success;
    public String error;
    public T data;
}
