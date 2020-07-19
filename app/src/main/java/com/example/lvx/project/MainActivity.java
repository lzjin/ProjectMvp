package com.example.lvx.project;

import com.example.lvx.project.base.BaseResponseBean;
import com.example.lvx.project.base.BaseMvpActivity;
import com.example.lvx.project.base.BaseMvpPresenter;
import com.example.lvx.project.view.activity.HomeActivity;

/**
 * Description: 欢迎页
 * Author: Administrator
 * CreateDate: 2020/4/8
 */
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
      intenToActivity(HomeActivity.class);
      finish();
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
    public void onErrorCode(BaseResponseBean bean) {

    }
}
