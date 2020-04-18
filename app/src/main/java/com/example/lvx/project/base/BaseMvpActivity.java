package com.example.lvx.project.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import com.example.lvx.project.R;
import com.example.lvx.project.dialog.BaseDialog;
import com.example.lvx.project.dialog.WaitDialog;
import com.example.lvx.project.utils.IntentUtil;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description: BaseMvpActivity
 * Author: Administrator
 * CreateDate: 2020/4/7
 */
public abstract class BaseMvpActivity<P extends BaseMvpPresenter> extends AppCompatActivity implements IBaseView {

    protected abstract P   createPresenter();

    protected abstract int setLayoutView();

    protected abstract void initView();

    protected abstract void initData();

    protected P mPresenter;

    protected EventBus eventBus;

    private Unbinder mUnbinder;

    public BaseMvpActivity activity;

    public BaseDialog waitDialog;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
        //设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mPresenter = createPresenter();
        if(mPresenter!=null){
          //  mPresenter.attachView(this);
        }
        setContentView(setLayoutView());
        initBase();
        initView();
        initData();
    }

    private void initBase() {
        initSoftKeyboard();
        mUnbinder=ButterKnife.bind(this);
        eventBus = EventBus.getDefault();
        setStatusBar();
    }


    /**
     *   基类统一设置
     *   白底黑字
     */
    protected void setStatusBar() {
        //ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).init();
    }

    public void showDialog() {
        showDialog("");
    }

    public void showDialog(String message) {
        if (waitDialog != null) {
            waitDialog.dismiss();
        }
        waitDialog = null;
        waitDialog = new WaitDialog.Builder(activity)
                .setMessage(message.equals("")?getString(R.string.str_loading):message).show();
    }

    public void dismissDialog(){
        if (waitDialog != null) {
            waitDialog.dismiss();
        }
    }

    /**
     * 注册eventbus
     */
    public void registEvent() {
       if (!eventBus.isRegistered(this)) eventBus.register(this);
    }
    public void intenToActivity(Class toActivity){
        IntentUtil.IntenToActivity(activity,toActivity);
    }
    public void intenToActivityWithBundle(Class toActivity, Bundle bundle) {
        IntentUtil.IntenToActivityWithBundle(activity,toActivity,bundle);
    }

    public void intenToActivityResult(Class toActivity, int requstCode) {
        IntentUtil.IntenToActivityResult(activity,toActivity,requstCode);
    }

    public void intenToActivityResultWithBundle(Class toActivity, int requstCode, Bundle bundle) {
        IntentUtil.IntenToActivityResultWithBundle(activity,toActivity,requstCode,bundle);
    }

    public void intentFinishActivityResult(Bundle bundle) {
        IntentUtil.IntentFinishActivityResult(activity,bundle);
    }

    /**
     * 和 setContentView 对应的方法
     */
    public ViewGroup getContentView() {
        return findViewById(Window.ID_ANDROID_CONTENT);
    }
    /**
     * 初始化软键盘
     */
    protected void initSoftKeyboard() {
        // 点击外部隐藏软键盘，提升用户体验
        getContentView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });
    }

    /**
     * 隐藏软键盘
     */
    private void hideSoftKeyboard() {
        // 隐藏软键盘，避免软键盘引发的内存泄露
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (manager != null) {
                manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (eventBus.isRegistered(this)) eventBus.unregister(this);
        super.onDestroy();
        if(mPresenter!=null){
            mPresenter.detachView();
        }
       // mManagerActivity.popOneActivity(this);
        mUnbinder.unbind();
    }

}
