package com.example.lvx.project.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.lvx.project.R;
import com.example.lvx.project.dialog.action.AnimAction;

import androidx.annotation.StringRes;



/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/12/2
 *    desc   : 等待加载对话框
 */
public final class WaitDialog {

    public static final class Builder
            extends BaseDialog.Builder<Builder> {

        private final TextView mMessageView;

        public Builder(Context context) {
            super(context);
            setContentView(R.layout.dialog_wait);
            setAnimStyle(AnimAction.TOAST);
            setBackgroundDimEnabled(false);
            setCancelable(false);

            mMessageView = findViewById(R.id.tv_wait_message);
        }

        public Builder setMessage(@StringRes int id) {
            return setMessage(getString(id));
        }
        public Builder setMessage(CharSequence text) {
            mMessageView.setText(text);
            mMessageView.setVisibility(text == null ? View.GONE : View.VISIBLE);
            return this;
        }
    }
}