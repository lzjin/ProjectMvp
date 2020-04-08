package com.example.lvx.project;

import android.app.Application;

import com.yechaoa.yutils.ActivityUtil;
import com.yechaoa.yutils.YUtils;

/**
 * Description: 作用描述
 * Author: Administrator
 * CreateDate: 2020/4/7
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化
        YUtils.initialize(this);
        //设置打印开关
        //LogUtil.setIsLog(true);
        //注册Activity生命周期
        registerActivityLifecycleCallbacks(ActivityUtil.getActivityLifecycleCallbacks());
    }
}
