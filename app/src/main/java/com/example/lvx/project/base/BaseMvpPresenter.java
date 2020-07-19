package com.example.lvx.project.base;

import com.example.lvx.project.MyApplication;
import com.example.lvx.project.http.ApiService;
import com.example.lvx.project.http.HttpRetrofit;
import com.example.lvx.project.http.RetrofitService;

import java.lang.ref.SoftReference;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description: 作用描述
 * Author: Administrator
 * CreateDate: 2020/4/7
 */
public class BaseMvpPresenter<V extends IBaseView> implements IBasePresenter {
    protected ApiService httpRequest= RetrofitService.getInstance().apiService;
    private CompositeDisposable compositeDisposable;
    private SoftReference<IBaseView> mReference;
    public V baseView;

    public BaseMvpPresenter(V baseView) {
        this.baseView = baseView;
    }
    /**
     * 绑定View
     * @param view
     */
    @Override
    public void attachView(IBaseView view) {
        mReference=new SoftReference<>(view);
    }
    /**
     * 解除绑定
     */
    @Override
    public void detachView() {
        baseView = null;
        removeDisposable();
       // mReference.clear();
        //mReference=null;
    }
    /**
     * 返回 view
     */
    @Override
    public IBaseView getBaseView() {
        return baseView;
    }

    public void addDisposable(Observable<?> observable, BaseObserver observer) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(observer));
    }

    private void removeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}
