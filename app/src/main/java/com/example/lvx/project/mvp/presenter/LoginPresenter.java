package com.example.lvx.project.mvp.presenter;

import com.example.lvx.project.base.BaseHttpObserver;
import com.example.lvx.project.base.BaseResponseBean;
import com.example.lvx.project.base.BaseMvpPresenter;
import com.example.lvx.project.http.HttpRetrofit;
import com.example.lvx.project.http.api.UserInfoApi;
import com.example.lvx.project.mvp.view.ILoginView;

import java.util.Map;

/**
 * Description: 作用描述
 * Author: Administrator
 * CreateDate: 2020/4/7
 */
public class LoginPresenter extends BaseMvpPresenter<ILoginView> {
    public LoginPresenter(ILoginView baseView) {
        super(baseView);
    }

    /**
     * 登录
     * @param map
     */
    public void login(Map<String,Object> map){
        addDisposable(httpRequest.login(map), new BaseHttpObserver<BaseResponseBean>(baseView,true) {
            @Override
            public void onSuccess(BaseResponseBean bean) {
                if(baseView!=null){
                    if(bean.getCode()==10000&&bean.getData()!=null){
                        baseView.doSuccess(bean.getData());
                    }else {
                        baseView.onShowToast(bean.getMessage());
                    }
                }
            }
            @Override
            public void onError(String msg) {
                if(baseView!=null){
                    baseView.onShowToast(msg);
                }
            }
        });
    }

    /**
     * 登录
     * @param map
     */
    public void login2(Map<String,Object> map){
        addDisposable( HttpRetrofit.createApi(UserInfoApi.class).login2(map), new BaseHttpObserver<BaseResponseBean>(baseView,true) {
            @Override
            public void onSuccess(BaseResponseBean bean) {
                if(baseView!=null){
                    if(bean.getCode()==10000&&bean.getData()!=null){
                        baseView.doSuccess(bean.getData());
                    }else {
                        baseView.onShowToast(bean.getMessage());
                    }
                }
            }
            @Override
            public void onError(String msg) {
                if(baseView!=null){
                    baseView.onShowToast(msg);
                }
            }
        });
    }
}
