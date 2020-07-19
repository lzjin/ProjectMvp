package com.example.lvx.project.view.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lvx.project.R;
import com.example.lvx.project.base.BaseResponseBean;
import com.example.lvx.project.base.BaseMvpActivity;
import com.example.lvx.project.entity.LoginBean;
import com.example.lvx.project.mvp.presenter.LoginPresenter;
import com.example.lvx.project.mvp.view.ILoginView;
import com.example.lvx.project.utils.MatcherUtil;
import com.example.lvx.project.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description: 登录
 * Author: Administrator
 * CreateDate: 2020/4/7
 */
public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements ILoginView {
    @BindView(R.id.edt_userName)
    EditText edtUserName;
    @BindView(R.id.edt_userPassword)
    EditText edtUserPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_login2)
    Button btnLogin2;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int setLayoutView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({ R.id.btn_login,R.id.btn_login2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                 passLogin();
                break;
            case R.id.btn_login2:
                passLogin2();
                break;
        }
    }

    /**
     * 密码登录 1
     */
    private void passLogin() {
        if (TextUtils.isEmpty(edtUserName.getText().toString())) {
            ToastUtil.showShort( this,"手机号不能为空");
            return;
        } else if (edtUserName.getText().toString().trim().length() != 11) {
            ToastUtil.showShort( this,"请输入11位手机号码");
            return;
        } else if (!MatcherUtil.matcherPhone(edtUserName.getText().toString().trim())) {
            ToastUtil.showShort( this, "请输入正确的手机号");
            return;
        } else if (TextUtils.isEmpty(edtUserPassword.getText().toString())) {
            ToastUtil.showShort( this, "密码不能为空");
            return;
        } else {
            hasWindowFocus();
            Map<String, Object> params = new HashMap<>();
            params.put("account", edtUserName.getText().toString());
            params.put("password", edtUserPassword.getText().toString());
            mPresenter.login(params);
        }
    }
    private void passLogin2() {
        if (TextUtils.isEmpty(edtUserName.getText().toString())) {
            ToastUtil.showShort( this,"手机号不能为空");
            return;
        } else if (edtUserName.getText().toString().trim().length() != 11) {
            ToastUtil.showShort( this,"请输入11位手机号码");
            return;
        } else if (!MatcherUtil.matcherPhone(edtUserName.getText().toString().trim())) {
            ToastUtil.showShort( this, "请输入正确的手机号");
            return;
        } else if (TextUtils.isEmpty(edtUserPassword.getText().toString())) {
            ToastUtil.showShort( this, "密码不能为空");
            return;
        } else {
            hasWindowFocus();
            Map<String, Object> params = new HashMap<>();
            params.put("account", edtUserName.getText().toString());
            params.put("password", edtUserPassword.getText().toString());
            mPresenter.login2(params);
        }
    }
    @Override
    public void doSuccess(Object object) {
        LoginBean bean= (LoginBean) object;
        //储存token
        //bean.getToken();
        intenToActivity(HomeActivity.class);
    }


    @Override
    public void onShowLoading() {
        showDialog("登录中");
       // YUtils.showLoading(this, "加载中");
    }

    @Override
    public void onHideLoading() {
        dismissDialog();
        //YUtils.dismissLoading();
    }

    @Override
    public void onShowToast(String toastMessage) {
        ToastUtil.showShort(activity, toastMessage);
    }

    @Override
    public void onErrorCode(BaseResponseBean bean) {

    }
}
