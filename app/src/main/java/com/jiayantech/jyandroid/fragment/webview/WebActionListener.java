package com.jiayantech.jyandroid.fragment.webview;

import com.jiayantech.jyandroid.model.web.BaseJsCall;

import java.lang.reflect.Type;

/**
 * Created by liangzili on 15/8/31.
 */
public abstract class WebActionListener<T extends BaseJsCall> {
    private String action;
    private Type dataType;

    public String getAction() {
        return action;
    }

    public Type getDataType() {
        return dataType;
    }

    public WebActionListener(String action, Type dataType){
//        Object[] genericClass = dataType.getClass().getClasses();
//        boolean flag = false;
//        for(Object generic: genericClass) {
//            if ((dataType instanceof BaseJsCall)) {
//                flag = true;
//                break;
//               // throw new IllegalArgumentException("WebActionListener dataType must be subclass of BaseJsCall");
//            }
//        }
//        if(!flag){
//            throw new IllegalArgumentException("WebActionListener dataType must be subclass of BaseJsCall");
//        }
        this.action = action;
        this.dataType = dataType;
    }

    public abstract void execute(T data);
}
