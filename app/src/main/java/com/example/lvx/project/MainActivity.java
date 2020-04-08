package com.example.lvx.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.lvx.project.base.BaseBean;
import com.example.lvx.project.base.BaseMvpActivity;
import com.example.lvx.project.base.BaseMvpPresenter;
import com.example.lvx.project.view.activity.LoginActivity;

public class MainActivity extends BaseMvpActivity {

    @Override
    protected BaseMvpPresenter createPresenter() {
        return null;
    }

    @Override
    protected int setLayoutView() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
      intenToActivity(LoginActivity.class);
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onShowLoading() {

    }

    @Override
    public void onHideLoading() {

    }

    @Override
    public void onShowToast(String toastMessage) {

    }

    @Override
    public void onErrorCode(BaseBean bean) {

    }
}
