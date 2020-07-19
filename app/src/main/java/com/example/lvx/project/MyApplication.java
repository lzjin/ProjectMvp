package com.example.lvx.project;

import android.app.Application;
import android.content.Context;

import com.example.lvx.project.http.HttpRetrofit;
import com.example.lvx.project.prefs.AppPreference;
import com.example.lvx.project.utils.MLog;
import com.yechaoa.yutils.ActivityUtil;
import com.yechaoa.yutils.YUtils;

/**
 * Description: 作用描述
 * Author: Administrator
 * CreateDate: 2020/4/7
 */
public class MyApplication extends Application {
    private static Context context;
    public  static MyApplication app;
    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        MyApplication.context = getApplicationContext();
        //初始化
        YUtils.initialize(this);
        //设置打印开关
        //LogUtil.setIsLog(true);
        //注册Activity生命周期
        registerActivityLifecycleCallbacks(ActivityUtil.getActivityLifecycleCallbacks());
        //设置打印开关
        MLog.isShowLogo=true;
        AppPreference.init(this);
        //初始化网络
        initRetrofitUtil();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }

    private void initRetrofitUtil() {
        new HttpRetrofit(getAppContext());
        //new RetrofitUtilTest(getApplicationContext());
//        LogUtil.i("初始化网络请求加载器");
    }
}
