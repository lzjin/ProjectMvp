package com.example.lvx.project.base;

import com.example.lvx.project.http.ApiService;
import com.example.lvx.project.http.RetrofitService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description: 作用描述
 * Author: Administrator
 * CreateDate: 2020/4/7
 */
public abstract class BaseModel {
    public ApiService httpRequest= RetrofitService.getInstance().apiService;
    private CompositeDisposable compositeDisposable;

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
