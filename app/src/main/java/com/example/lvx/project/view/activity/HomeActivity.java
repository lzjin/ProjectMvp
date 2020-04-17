package com.example.lvx.project.view.activity;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.lvx.project.R;
import com.example.lvx.project.base.BaseBean;
import com.example.lvx.project.base.BaseMvpActivity;
import com.example.lvx.project.base.BaseMvpPresenter;
import com.example.lvx.project.utils.FragmentUtil;
import com.example.lvx.project.view.fragment.HomeFragment;
import com.example.lvx.project.view.fragment.MyFragment;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description: 作用描述
 * Author: Administrator
 * CreateDate: 2020/4/8
 */
public class HomeActivity extends BaseMvpActivity {
    @BindView(R.id.main_fragment)
    FrameLayout mainFragment;
    @BindView(R.id.main_rb_home)
    RadioButton mainRbHome;
    @BindView(R.id.main_rb_my)
    RadioButton mainRbMy;
    @BindView(R.id.main_group)
    RadioGroup mainGroup;

    HomeFragment homeFragment = HomeFragment.getInstance();
    MyFragment myFragment = MyFragment.getInstance();

    private Fragment indexFragment = homeFragment;
    @Override
    protected BaseMvpPresenter createPresenter() {
        return null;
    }

    @Override
    protected int setLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        FragmentUtil.addFragment(R.id.main_fragment, activity, indexFragment);
        mainRbHome.setChecked(true);
        mainGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.main_rb_home:
                        FragmentUtil.setContentFragment(R.id.main_fragment, activity, homeFragment, indexFragment);
                        indexFragment = homeFragment;
                        break;
                    case R.id.main_rb_my:
                        FragmentUtil.setContentFragment(R.id.main_fragment, activity, myFragment, indexFragment);
                        indexFragment = myFragment;
                        break;
                }
            }
        });
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
