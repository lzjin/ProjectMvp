package com.example.lvx.project.view.fragment;

import android.view.View;
import android.widget.Button;

import com.example.lvx.project.R;
import com.example.lvx.project.base.BaseResponseBean;
import com.example.lvx.project.base.BaseMvpFragment;
import com.example.lvx.project.base.BaseMvpPresenter;
import com.example.lvx.project.dialog.BaseDialog;
import com.example.lvx.project.dialog.InputDialog;
import com.example.lvx.project.dialog.MessageDialog;
import com.example.lvx.project.dialog.ToastDialog;
import com.example.lvx.project.dialog.WaitDialog;
import com.example.lvx.project.utils.ToastUtil;
import com.example.lvx.project.view.activity.LoginActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description: 我的
 * Author: Administrator
 * CreateDate: 2020/4/8
 */
public class MyFragment extends BaseMvpFragment {
    private static volatile MyFragment instance = null;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnWiat)
    Button btnWiat;
    @BindView(R.id.btnSuccess)
    Button btnSuccess;
    @BindView(R.id.btnFail)
    Button btnFail;
    @BindView(R.id.btnWarn)
    Button btnWarn;
    @BindView(R.id.btnIput)
    Button btnIput;
    @BindView(R.id.btnMsg)
    Button btnMsg;

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
    @OnClick({R.id.btnLogin, R.id.btnWiat,R.id.btnWarn,R.id.btnMsg, R.id.btnSuccess, R.id.btnFail, R.id.btnIput})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                intenToActivity(LoginActivity.class);
                break;
            case R.id.btnWiat:
                // 等待对话框
                final BaseDialog waitDialog = new WaitDialog.Builder(fragmentActivity)
                        // 消息文本可以不用填写
                        .setMessage(getString(R.string.str_loading))
                        .show();
                btnWiat.postDelayed(waitDialog::dismiss, 2000);
                break;
            case R.id.btnSuccess:
                // 成功对话框
                new ToastDialog.Builder(fragmentActivity)
                        .setType(ToastDialog.Type.FINISH)
                        .setMessage("完成")
                        .show();
                break;
            case R.id.btnFail:
                // 失败对话框
                new ToastDialog.Builder(fragmentActivity)
                        .setType(ToastDialog.Type.ERROR)
                        .setMessage("错误")
                        .show();
                break;
            case R.id.btnWarn:
                // 警告对话框
                new ToastDialog.Builder(fragmentActivity)
                        .setType(ToastDialog.Type.WARN)
                        .setMessage("警告")
                        .show();
                break;
            case R.id.btnIput:
               // 输入对话框
                new InputDialog.Builder(fragmentActivity)
                        // 标题可以不用填写
                        .setTitle("我是标题")
                        // 内容可以不用填写
                        .setContent("我是内容")
                        // 提示可以不用填写
                        .setHint("我是提示")
                        // 确定按钮文本
                        .setConfirm(getString(R.string.str_confirm))
                        // 设置 null 表示不显示取消按钮
                        .setCancel(getString(R.string.str_cancel))
                        // 设置点击按钮后不关闭对话框
                        //.setAutoDismiss(false)
                        .setListener(new InputDialog.OnListener() {

                            @Override
                            public void onConfirm(BaseDialog dialog, String content) {
                                ToastUtil.showShort(fragmentActivity,"确定了：" + content);
                            }

                            @Override
                            public void onCancel(BaseDialog dialog) {
                                ToastUtil.showShort(fragmentActivity,"取消了" );
                            }
                        })
                        .show();
                break;
            case R.id.btnMsg:
                // 消息对话框
                new MessageDialog.Builder(fragmentActivity)
                        // 标题可以不用填写
                        .setTitle("我是标题")
                        // 内容必须要填写
                        .setMessage("我是内容")
                        // 确定按钮文本
                        .setConfirm(getString(R.string.str_confirm))
                        // 设置 null 表示不显示取消按钮
                        .setCancel(getString(R.string.str_cancel))
                        // 设置点击按钮后不关闭对话框
                        //.setAutoDismiss(false)
                        .setListener(new MessageDialog.OnListener() {

                            @Override
                            public void onConfirm(BaseDialog dialog) {
                                ToastUtil.showShort(fragmentActivity,"确定了" );
                            }

                            @Override
                            public void onCancel(BaseDialog dialog) {
                                ToastUtil.showShort(fragmentActivity,"取消了" );
                            }
                        })
                        .show();
                break;
        }
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
