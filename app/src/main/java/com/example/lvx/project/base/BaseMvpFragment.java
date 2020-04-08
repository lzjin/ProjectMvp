package com.example.lvx.project.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lvx.project.utils.IntentUtil;
import com.yechaoa.yutils.ActivityUtil;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description: 作用描述
 * Author: Administrator
 * CreateDate: 2020/4/7
 */
public abstract class BaseMvpFragment <P extends BaseMvpPresenter> extends Fragment implements IBaseView {

    protected abstract P   createPresenter();

    protected abstract int setLayoutView();

    protected abstract void initView();

    protected abstract void initData();

    protected P mPresenter;

    protected EventBus eventBus;

    private Unbinder mUnbinder;

    protected View mRootView;

    public Activity fragmentActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentActivity=getActivity();
        //=new XProgressDialog(fragmentActivity);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(setLayoutView(), container, false);
        fragmentActivity = ActivityUtil.getCurrentActivity();
        mPresenter = createPresenter();
        if(mPresenter!=null){
          //  mPresenter.attachView(this);
        }
        initBase();
        initView();
        initData();
        return mRootView;
    }
    private void initBase() {
        mUnbinder = ButterKnife.bind(this, mRootView);
        //addToken();
    }

    public void registEvent() {
        if (!eventBus.isRegistered(this)) eventBus.register(this);
    }
    public void intenToActivity(Class toActivity){
        IntentUtil.IntenToActivity(fragmentActivity,toActivity);
    }
    public void intenToActivityWithBundle(Class toActivity, Bundle bundle) {
        IntentUtil.IntenToActivityWithBundle(fragmentActivity,toActivity,bundle);
    }

    public void intenToActivityResult(Class toActivity, int requstCode) {
        IntentUtil.IntenToActivityResult(fragmentActivity,toActivity,requstCode);
    }

    public void intenToActivityResultWithBundle(Class toActivity, int requstCode, Bundle bundle) {
        IntentUtil.IntenToActivityResultWithBundle(fragmentActivity,toActivity,requstCode,bundle);
    }

    public void intentFinishActivityResult(Bundle bundle) {
        IntentUtil.IntentFinishActivityResult(fragmentActivity,bundle);
    }

    @Override
    public void onDestroyView() {
        if (eventBus.isRegistered(this)) eventBus.unregister(this);
        super.onDestroyView();
        if(mPresenter!=null){
            mPresenter.detachView();
        }
        mUnbinder.unbind();
    }
}
