package com.example.game2048.view;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.game2048.R;
import com.example.game2048.app.Config;


public class SetDialog extends Dialog {

    private View.OnClickListener onPositiveClickListener;
    private View.OnClickListener onNegativeClickListener;

    //游戏音效状态
    private boolean volumeState = Config.VolumeState;

    public SetDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        assert window != null;
        setContentView(setView());
        // 设置Dialog全屏
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        initData();
    }

    protected int setView() {
        return R.layout.dialog_config;
    }


    protected void initData() {
        init();
    }

    private void init() {
        Button cancel = findViewById(R.id.btn_return);
        Button confirm = findViewById(R.id.btn_confirm);
        Button volumeON = findViewById(R.id.btn_volume_on);
        Button volumeOFF = findViewById(R.id.btn_volume_off);
        TextView getGoalTime = findViewById(R.id.tv_goal_get_time);


        // 根据配置参数选中按钮
        if (Config.VolumeState) {
            volumeON.setBackgroundResource(R.drawable.bg_button_select);
        } else {
            volumeOFF.setBackgroundResource(R.drawable.bg_button_select);
        }

        if (onNegativeClickListener != null) {
            cancel.setOnClickListener(onNegativeClickListener);
        }
        if (onPositiveClickListener != null) {
            confirm.setOnClickListener(onPositiveClickListener);
        }


        volumeON.setOnClickListener(v -> {
            volumeState = true;
            volumeON.setBackgroundResource(R.drawable.bg_button_select);
            volumeOFF.setBackgroundResource(R.drawable.bg_button_white);
        });
        volumeOFF.setOnClickListener(v -> {
            volumeState = false;
            volumeON.setBackgroundResource(R.drawable.bg_button_white);
            volumeOFF.setBackgroundResource(R.drawable.bg_button_select);
        });
        getGoalTime.setText(Config.GetGoalTime == 0 ? "暂未达成" : String.valueOf(Config.GetGoalTime));

    }

    //确认按钮点击
    public SetDialog setOnPositiveClickListener(
            View.OnClickListener onPositiveClickListener) {
        this.onPositiveClickListener = onPositiveClickListener;
        return this;
    }

    //取消按钮点击
    public SetDialog setOnNegativeClickListener(
            View.OnClickListener onNegativeClickListener) {
        this.onNegativeClickListener = onNegativeClickListener;
        return this;
    }

    //获取游戏音效状态
    public boolean getVolumeState() {
        return volumeState;
    }

}
