package com.example.lvx.project.mvp.presenter;

import android.util.Log;

import com.example.lvx.project.base.BaseHttpObserver;
import com.example.lvx.project.base.BaseResponseBean;
import com.example.lvx.project.base.BaseMvpPresenter;
import com.example.lvx.project.entity.BannerBean;
import com.example.lvx.project.entity.LoginBean;
import com.example.lvx.project.entity.TogetherBean;
import com.example.lvx.project.entity.VersionBean;
import com.example.lvx.project.http.ApiService;
import com.example.lvx.project.http.HttpRetrofit;
import com.example.lvx.project.http.api.UserInfoApi;
import com.example.lvx.project.mvp.view.ILoginView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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
     *
     * @param map
     */
    public void login(Map<String, Object> map) {
        //httpRequest.login(map)
        //HttpRetrofit.createApi(UserInfoApi.class).login2(map)
        addDisposable(HttpRetrofit.createApi(UserInfoApi.class).login(map), new BaseHttpObserver<BaseResponseBean>(baseView, true) {
            @Override
            public void onSuccess(BaseResponseBean bean) {
                if (baseView != null) {
                    if (bean.getCode() == 10000 && bean.getData() != null) {
                        baseView.doSuccess(bean.getData());
                    } else {
                        baseView.onShowToast(bean.getMessage());
                    }
                }
            }

            @Override
            public void onError(String msg) {
                if (baseView != null) {
                    baseView.onShowToast(msg);
                }
            }
        });
    }

    /**
     * 并联接口都成功情况下 显示数据
     * 其一失败 则失败
     */
    // @SuppressLint("CheckResult")
    public void togetherRequest() {
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(observer)
       // addDisposable();
        Observable.zip(HttpRetrofit.createApi(ApiService.class).getBanner(),
                HttpRetrofit.createApi(ApiService.class).getVersionCode("APPVersion,APPDownAddress"),
                new BiFunction<BaseResponseBean<List<BannerBean>>, BaseResponseBean<VersionBean>, TogetherBean>() {
                    @NonNull
                    @Override
                    public TogetherBean apply(@NonNull BaseResponseBean<List<BannerBean>> responseBanner, @NonNull BaseResponseBean<VersionBean> versionBean) throws Exception {
                        TogetherBean togetherBean = new TogetherBean();
                        //实际先判断code==200，组装2个接口数据
                        if (responseBanner != null && responseBanner.getData() != null && responseBanner.getData().size() > 0) {
                            togetherBean.setUrl(responseBanner.getData().get(0).getAdvUrl());
                        }
                        if (versionBean != null && versionBean.getData() != null) {
                            togetherBean.setVersionCode(versionBean.getData().getAPPVersion());
                        }
                        return togetherBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<TogetherBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        // d.dispose();
                    }

                    @Override
                    public void onNext(@NonNull TogetherBean togetherBean) {
                        Log.e("testz", "----------" + togetherBean.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("testz", "----------并联接口中有失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 接口队列依次发送
     * 先接口1数据拿到后，再带着数据去请求接口2
     */
    public void queueListRequest(){
        HttpRetrofit.createApi(ApiService.class).getBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                 //接口1返回数据类型,接口2返回数据类型= Observable<BaseResponse<xx>>
                .flatMap(new Function<BaseResponseBean<List<BannerBean>>, Observable<BaseResponseBean<VersionBean>>>() {
                    @Override
                    public Observable<BaseResponseBean<VersionBean>> apply(@NonNull BaseResponseBean<List<BannerBean>> responseBanner) throws Exception {
                        //实际先判断code==200
                        if (responseBanner != null && responseBanner.getData() != null && responseBanner.getData().size() > 0) {
                            //拿到第一次请求结果
                            //执行第二次请求
                            Log.e("testz", "----------队列接口1完成调用接口2");
                        }
                        return  HttpRetrofit.createApi(ApiService.class).getVersionCode("APPVersion,APPDownAddress");
                    }
                })
                //.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<BaseResponseBean<VersionBean>, Observable<BaseResponseBean<LoginBean>>>() {
                    @Override
                    public Observable<BaseResponseBean<LoginBean>> apply(@NonNull BaseResponseBean<VersionBean> versionBean) throws Exception {
                        // //执行第三次请求
                        Log.e("testz", "----------队列接口2完成调用接口3");
                        Map<String, Object> params = new HashMap<>();
                        params.put("account", "18285216355");
                        params.put("password", "120120");
                        return HttpRetrofit.createApi(ApiService.class).login(params);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                //.subscribeOn(Schedulers.io())
                .subscribe(new Observer<BaseResponseBean<LoginBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BaseResponseBean<LoginBean> loginBean) {
                        Log.e("testz", "----------队列接口3成功="+loginBean.getMessage());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("testz", "----------队列接口中有失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
