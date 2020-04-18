package com.example.lvx.project.contants;

import android.os.Environment;

/**
 * Description: App常量池
 * Author: Administrator
 * CreateDate: 2020/4/7
 */
public class AppConstant {
    public static final String IS_Frist = "isFrist";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String COOKIES = "cookies";

    public static final String APP_ROOT_PATH= Environment.getExternalStorageDirectory().getAbsolutePath()+"/AA_test/";//根路径
    public static final String IMA_PATH= APP_ROOT_PATH+"/user_images/";//图片
    public static final String DOWNLOAD_APK_PATH=APP_ROOT_PATH+"/download";//下载路径
    public static final String SAVE_DATA_NAME = "SavaData";
}
