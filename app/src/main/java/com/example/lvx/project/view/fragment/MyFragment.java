package com.example.lvx.project.view.fragment;

import com.example.lvx.project.R;
import com.example.lvx.project.base.BaseBean;
import com.example.lvx.project.base.BaseMvpFragment;
import com.example.lvx.project.base.BaseMvpPresenter;

/**
 * Description: 我的
 * Author: Administrator
 * CreateDate: 2020/4/8
 */
public class MyFragment extends BaseMvpFragment {
    private static volatile MyFragment instance = null;
    public static MyFragment getInstance() {
        if (instance == null) {
            synchronized (MyFragment.class) {
                if (instance == null) {
                    instance = new MyFragment();
                }
            }
        }
        return instance;
    }
    @Override
    protected BaseMvpPresenter createPresenter() {
        return null;
    }

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView() {

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
