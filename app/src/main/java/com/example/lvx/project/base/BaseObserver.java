package com.example.lvx.project.base;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.lvx.project.MainActivity;
import com.example.lvx.project.MyApplication;
import com.example.lvx.project.R;
import com.example.lvx.project.dialog.BaseDialog;
import com.example.lvx.project.dialog.InputDialog;
import com.example.lvx.project.utils.MLog;
import com.example.lvx.project.utils.ToastUtil;
import com.example.lvx.project.view.activity.LoginActivity;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * Description: 自定义观察者
 * Author: Administrator
 * CreateDate: 2020/4/7
 */
public abstract class BaseObserver <T> extends DisposableObserver<BaseBean<T>> {
    protected IBaseView view;
    private boolean isShowDialog;

    /**
     * 带进度条的初始化方法
     *
     * @param view         view
     * @param isShowDialog 是否显示进度条
     */
    protected BaseObserver(IBaseView view, boolean isShowDialog) {
        this.view = view;
        this.isShowDialog = isShowDialog;
    }

    @Override
    protected void onStart() {
        if (view != null && isShowDialog) {
            view.onShowLoading();
        }
    }

    @Override
    public void onNext(BaseBean<T> t) {
        if(t.getCode()==100051){
            MLog.e("-----------登录过期code");
            Intent intent = new Intent(MyApplication.getAppContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MyApplication.getAppContext().startActivity(intent);
        }else if( t.getCode() == 10005 ){
            MLog.e("-----------强制下线code");
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    MLog.e("--------2---强制下线code");

//                    AlertDialog.Builder builer=new AlertDialog.Builder();
//                    builer.setTitle("强制下线广播")
//                            .setMessage("您的账号在异地登录")
//                            .setCancelable(true)//设置取消按钮不能使用,
//                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                }
//                            }).show();
                }
            });
        } else if( t.getCode() != 200 ){
            onError( new BaseException(t.getCode(),t.getMessage() ) );
        }else {
            onSuccess(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        if (view != null && isShowDialog) {
            view.onHideLoading();
        }
        BaseException be;
        if (e != null) {
            //自定义异常
            if (e instanceof BaseException) {
                be = (BaseException) e;
                //回调到view层 处理 或者根据项目情况处理
                if (view != null) {
                    // 411处理登录失效 更新
                    view.onErrorCode(new BaseBean(be.getErrorCode(), be.getErrorMsg()));
                } else {
                    onError(be.getErrorMsg());
                }
                //系统异常
            } else {
                if (e instanceof HttpException) {
                    //HTTP错误
                    be = new BaseException(BaseException.BAD_NETWORK_MSG, e);
                } else if (e instanceof ConnectException || e instanceof UnknownHostException) {
                    //连接错误
                    be = new BaseException(BaseException.CONNECT_ERROR_MSG, e);
                } else if (e instanceof InterruptedIOException) {
                    //连接超时
                    be = new BaseException(BaseException.CONNECT_TIMEOUT_MSG, e);
                } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
                    //解析错误
                    be = new BaseException(BaseException.PARSE_ERROR_MSG, e);
                } else {
                    be = new BaseException(BaseException.OTHER_MSG, e);
                }
            }
        } else {
            be = new BaseException(BaseException.OTHER_MSG);
        }
        onError(be.getErrorMsg());
    }

    @Override
    public void onComplete() {
        if (view != null && isShowDialog) {
            view.onHideLoading();
        }
    }

    public abstract void onSuccess(BaseBean<T>  obj);

    public abstract void onError(String msg);
}
