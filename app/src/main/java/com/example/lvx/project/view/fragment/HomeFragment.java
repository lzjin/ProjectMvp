package com.example.lvx.project.view.fragment;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.lvx.project.R;
import com.example.lvx.project.base.BaseResponseBean;
import com.example.lvx.project.base.BaseMvpFragment;
import com.example.lvx.project.mvp.presenter.HomePresenter;
import com.example.lvx.project.mvp.view.IHomeView;
import com.example.lvx.project.utils.SystemUtil;
import com.example.lvx.project.utils.ToastUtil;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

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
        test();
    }

    public void test(){
        String str1 = "{\\\"resourceId\":\"dfead70e4ec5c11e43514000ced0cdcaf\",\"properties\":{\"process_id\":\"process4\",\"name\":\"\",\"documentation\":\"\",\"processformtemplate\":\"\"}}";
        Log.e("testz","--------------str1="+str1);
        String tmp = StringEscapeUtils.unescapeJson(str1);
        Log.e("testz","--------------tmp="+tmp);
    }




    @OnClick({R.id.btn_1, R.id.btn_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                Map<String, Object> params = new HashMap<>();
                params.put("account", "13890909090");
                params.put("password", "123456&"+ SystemUtil.getPhoneMac(fragmentActivity));
                mPresenter.testPost(params);
                break;
            case R.id.btn_2:
                break;
        }
    }

    @Override
    protected void initData() {

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
