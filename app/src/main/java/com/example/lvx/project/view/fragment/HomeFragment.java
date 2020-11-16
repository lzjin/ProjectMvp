package com.example.lvx.project.view.fragment;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lvx.project.R;
import com.example.lvx.project.base.BaseMvpFragment;
import com.example.lvx.project.base.BaseResponseBean;
import com.example.lvx.project.mvp.presenter.HomePresenter;
import com.example.lvx.project.mvp.view.IHomeView;
import com.example.lvx.project.utils.SystemUtil;
import com.example.lvx.project.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description: 作用描述
 * Author: Administrator
 * CreateDate: 2020/4/7
 */
public class HomeFragment extends BaseMvpFragment<HomePresenter> implements IHomeView {
    private static volatile HomeFragment instance = null;
    @BindView(R.id.btn_1)
    Button btn1;
    @BindView(R.id.btn_2)
    Button btn2;
    @BindView(R.id.tvFont1)
    TextView tvFont1;
    @BindView(R.id.tvFont2)
    TextView tvFont2;
    @BindView(R.id.tvFont3)
    TextView tvFont3;

    public static HomeFragment getInstance() {
        if (instance == null) {
            synchronized (HomeFragment.class) {
                if (instance == null) {
                    instance = new HomeFragment();
                }
            }
        }
        return instance;
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        AssetManager assets = getActivity().getAssets();
        Typeface tf1 = Typeface.createFromAsset(assets, "fonts/STXINGKA.TTF");//华文行楷
        Typeface tf3 = Typeface.createFromAsset(assets, "fonts/STHUPO.TTF");//华文琥珀

        tvFont1.setTypeface(tf1);
        tvFont2.setTypeface(tf1,Typeface.BOLD);
        tvFont3.setTypeface(tf3);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_1, R.id.btn_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                Map<String, Object> params = new HashMap<>();
                params.put("account", "13890909090");
                params.put("password", "123456&" + SystemUtil.getPhoneMac(fragmentActivity));
                mPresenter.testPost(params);
                break;
            case R.id.btn_2:
                break;
        }
    }


    @Override
    public void onShowLoading() {
        showDialog("测试中");
    }

    @Override
    public void onHideLoading() {
        dismissDialog();
    }

    @Override
    public void onShowToast(String toastMessage) {
        ToastUtil.showShort(fragmentActivity, toastMessage);
    }

    @Override
    public void onErrorCode(BaseResponseBean bean) {

    }


}
