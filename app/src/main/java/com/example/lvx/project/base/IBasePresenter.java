package com.example.lvx.project.base;

/**
 * Description: 作用描述
 * Author: Administrator
 * CreateDate: 2020/4/7
 */
public interface IBasePresenter<V extends IBaseView> {
    /**
     * 绑定View
     * @param view
     */
    void attachView(V view);

    /**
     * 解绑view
     */
    void detachView();

    /**
     * 得到view
     */
    V getBaseView();

}
