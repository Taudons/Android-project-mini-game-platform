package com.example.game2048.view;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import com.example.game2048.R;

public class GameOverDialog extends Dialog {

    private String finalScore;
    private String title;
    private View.OnClickListener onGoOnClickListener;

    public GameOverDialog(@NonNull Context context, int themeResId) {
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
        return R.layout.dialog_game_over;
    }


    protected void initData() {
        init();
    }

    private void init() {
        TextView title = findViewById(R.id.tv_custom_title);
        TextView finalScore = findViewById(R.id.tv_final_score);
        MaterialButton goOn = findViewById(R.id.tv_go_on);

        if (onGoOnClickListener != null) {
            goOn.setOnClickListener(onGoOnClickListener);
        }
        if (!TextUtils.isEmpty(this.finalScore)) {
            finalScore.setText(this.finalScore);
        }
        if (!TextUtils.isEmpty(this.title)) {
            title.setText(this.title);
        }
    }


    public GameOverDialog setOnGoOnClickListener(
            View.OnClickListener onGoOnClickListener) {
        this.onGoOnClickListener = onGoOnClickListener;
        return this;
    }

    //设置最终得分
    public GameOverDialog setFinalScore(String finalScore) {
        this.finalScore = finalScore;
        return this;
    }

    //设置标题
    public GameOverDialog setTitle(String title) {
        this.title = title;
        return this;
    }

}
