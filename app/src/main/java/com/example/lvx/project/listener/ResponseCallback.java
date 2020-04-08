package com.example.lvx.project.listener;

/**
 * Description: 作用描述
 * Author: Administrator
 * CreateDate: 2020/4/7
 */
public interface ResponseCallback {
    void _onSucceed(Object object);

    void _onFail(String msg);

    void _onCompleted();
}
