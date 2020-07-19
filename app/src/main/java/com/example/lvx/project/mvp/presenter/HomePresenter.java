package com.example.lvx.project.mvp.presenter;

import com.example.lvx.project.base.BaseResponseBean;
import com.example.lvx.project.base.BaseMvpPresenter;
import com.example.lvx.project.base.BaseObserver;
import com.example.lvx.project.mvp.view.IHomeView;

import java.util.Map;

/**
 * Description: 作用描述
 * Author: Lzj
 * CreateDate: 2020/5/6
 */
public class HomePresenter extends BaseMvpPresenter<IHomeView> {
    public HomePresenter(IHomeView baseView) {
        super(baseView);
    }

    /**
     * 登录
     * @param map
     */
    public void testPost(Map<String,Object> map){
        addDisposable(httpRequest.login(map), new BaseObserver<BaseResponseBean>(baseView,true) {
            @Override
            public void onSuccess(BaseResponseBean bean) {
                if(baseView!=null){
                    if(bean.getCode()==10000&&bean.getData()!=null){
                       // baseView.doSuccess(bean.getData());
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
