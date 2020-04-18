package com.example.lvx.project.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.lvx.project.MyApplication;
import com.example.lvx.project.contants.AppConstant;

import java.util.Map;

/**
 * Description: 作用描述
 * Author: Lzj
 * CreateDate: 2020/4/17
 */
public class SPUtils {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;//同步操作
    private static  SPUtils spUtils;
    public static SPUtils getInstance(){
       if(spUtils==null){
           synchronized (SPUtils.class){
               if(spUtils==null){
                   spUtils=new SPUtils();
               }
           }
       }
       return spUtils;
    }
    private SPUtils(){
        preferences = MyApplication.getAppContext().getSharedPreferences(AppConstant.SAVE_DATA_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }


    /**
     * 获取SP数据里指定key对应的value。如果key不存在，则返回默认值defValue。
     * @param key
     * @param defValue
     * @return
     */
    public String getString(String key, String defValue) {
        return preferences.getString(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return preferences.getBoolean(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return preferences.getFloat(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return preferences.getLong(key, defValue);
    }

    /**
     * 向SP存入指定key对应的数据
     * 其中value可以是String、boolean、float、int、long等各种基本类型的值
     * @param key
     * @param value
     */
    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void putFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public void putLong(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 删除SP里指定key对应的数据项
     */
    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }
    /**
     * 查询指定key是否存在
     */
    public  boolean contains(String key) {
        return preferences.contains(key);
    }

    /**
     * 返回所有的键值对
     */
    public Map<String, ?> getAll() {
        return preferences.getAll();
    }
    /**
     * 清空SP里所以数据
     */
    public void clearAll() {
        editor.clear();
        editor.commit();
    }
}
