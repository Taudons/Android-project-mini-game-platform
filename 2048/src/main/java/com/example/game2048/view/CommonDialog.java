package com.example.game2048.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.example.game2048.R;

public class CommonDialog extends Dialog {

    private String title;
    private String message;
    private String cancelText;
    private String confirmText;
    private View.OnClickListener onPositiveClickedListener;
    private View.OnClickListener onNegativeClickedListener;

    public CommonDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common);
        init();
    }

    private void init() {
        TextView title = findViewById(R.id.tv_title_message);
        TextView message = findViewById(R.id.tv_content_message);
        TextView cancel = findViewById(R.id.tv_cancel);
        TextView confirm = findViewById(R.id.tv_confirm);
        View dividerH = findViewById(R.id.view_divider_h);
        View dividerV = findViewById(R.id.view_divider_v);
        if (onNegativeClickedListener != null) {
            if (!TextUtils.isEmpty(this.cancelText)) {
                cancel.setText(this.cancelText);
            }
            cancel.setOnClickListener(onNegativeClickedListener);
        }
        if (onPositiveClickedListener != null) {
            if (!TextUtils.isEmpty(this.confirmText)) {
                confirm.setText(this.confirmText);
            }
            confirm.setOnClickListener(onPositiveClickedListener);
        }
        if (!TextUtils.isEmpty(this.title)) {
            title.setVisibility(View.VISIBLE);
            title.setText(this.title);
        } else {
            title.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(this.message)) {
            dividerH.setVisibility(View.VISIBLE);
            dividerV.setVisibility(View.VISIBLE);
            message.setVisibility(View.VISIBLE);
            message.setText(this.message);
        } else {
            message.setVisibility(View.INVISIBLE);
            dividerH.setVisibility(View.INVISIBLE);
            dividerV.setVisibility(View.GONE);
        }
    }

    public CommonDialog setOnPositiveClickedListener(
            String confirmText,
            View.OnClickListener onPositiveClickedListener) {
        this.confirmText = confirmText;
        this.onPositiveClickedListener = onPositiveClickedListener;
        return this;
    }

    public CommonDialog setOnNegativeClickListener(
            String cancelText,
            View.OnClickListener onNegativeClickedListener) {
        this.cancelText = cancelText;
        this.onNegativeClickedListener = onNegativeClickedListener;
        return this;
    }

    //设置标题
    public CommonDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    //设置文本内容
    public CommonDialog setMessage(String message) {
        this.message = message;
        return this;
    }

}
