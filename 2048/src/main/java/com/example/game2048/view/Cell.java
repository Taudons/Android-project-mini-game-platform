package com.example.game2048.view;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.example.game2048.R;

public class Cell extends FrameLayout {

    private TextView cellShowText;
    private int digital;

    public Cell(Context context) {
        super(context);
    }
    public Cell(@NonNull Context context, int leftMargin, int topMargin, int bottomMargin) {
        super(context);
        init(context, leftMargin, topMargin, bottomMargin);
    }


    private void init(@NonNull Context context, int leftMargin, int topMargin, int bottomMargin) {
        cellShowText = new TextView(context);
        cellShowText.setTextSize(40);
        cellShowText.setGravity(Gravity.CENTER);
        // 抗锯齿
        cellShowText.getPaint().setAntiAlias(true);
        // 粗体
        cellShowText.getPaint().setFakeBoldText(true);
        // 字体
        cellShowText.setTypeface(Typeface.MONOSPACE);
        // 颜色
        cellShowText.setTextColor(ContextCompat.getColor(context, R.color.colorTextDark));
        // 填充整个父容器
        LayoutParams params = new LayoutParams(-1, -1);
        params.setMargins(leftMargin, topMargin, 0, bottomMargin);
        addView(cellShowText, params);
        setDigital(0);
    }

    //获取卡片
    public TextView getItemCell() {
        return cellShowText;
    }

    //获取数字
    public int getDigital() {
        return digital;
    }

    //设置数字
    public void setDigital(int digital) {
        this.digital = digital;
        cellShowText.setBackgroundResource(getBackgroundResource(digital));
        if (digital <= 0) {
            cellShowText.setText("");
        } else {
            cellShowText.setText(String.valueOf(digital));
        }
    }

   //设置数字背景
    private int getBackgroundResource(int number) {
        switch (number) {
            case 0:
                return R.drawable.bg_cell_0;
            case 2:
                return R.drawable.bg_cell_2;
            case 4:
                return R.drawable.bg_cell_4;
            case 8:
                return R.drawable.bg_cell_8;
            case 16:
                return R.drawable.bg_cell_16;
            case 32:
                return R.drawable.bg_cell_32;
            case 64:
                return R.drawable.bg_cell_64;
            case 128:
                return R.drawable.bg_cell_128;
            case 256:
                return R.drawable.bg_cell_256;
            case 512:
                return R.drawable.bg_cell_512;
            case 1024:
                return R.drawable.bg_cell_1024;
            case 2048:
                return R.drawable.bg_cell_2048;
            default:
                return R.drawable.bg_cell_default;
        }
    }

}
