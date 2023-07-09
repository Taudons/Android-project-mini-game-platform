package com.example.shudu;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

//该类用于实现Dialog,实现自定义的对话框功能
public class KeyDialog extends Dialog {
    //用来存放对话框中按钮的对象
    private final View keys[] = new View[9];
    private Button button;
    private final int used[];
    private ShuduView shuduView;
//构造函数的第二个参数中保存者当前单元格已经使用过的数字
    public KeyDialog(@NonNull Context context, int[] used,ShuduView shuduView) {
        super(context);
        this.used = used;
        this.shuduView=shuduView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("请选择数字");
        setContentView(R.layout.keypad_shudu);
        findViews();
        //为对话框当中所有按钮设置监听器
        setListeners();
        //遍历整个used数组
        for (int i = 0; i < used.length; i++) {
            if(used[i]!=0) keys[used[i]-1].setVisibility(View.INVISIBLE);
        }
    }
    private void findViews(){
        keys[0]=findViewById(R.id.keypad_1);
        keys[1]=findViewById(R.id.keypad_2);
        keys[2]=findViewById(R.id.keypad_3);
        keys[3]=findViewById(R.id.keypad_4);
        keys[4]=findViewById(R.id.keypad_5);
        keys[5]=findViewById(R.id.keypad_6);
        keys[6]=findViewById(R.id.keypad_7);
        keys[7]=findViewById(R.id.keypad_8);
        keys[8]=findViewById(R.id.keypad_9);
        button = findViewById(R.id.delete);
    }

    private void setListeners(){
        //遍历整个keys数组
        for (int i = 0; i < keys.length; i++) {
            final int t=i+1;
            keys[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    returnResult(t);
                }
            });
        }
        //删除按钮的监听器
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //调用shuduView的setDeleteTile方法
               //shuduView.setDeleteTile();
                returnResult(0);
            }
        });
    }
    //通知ShuduView对象，刷新整个九宫格显示的数据
    private void returnResult(int tile){
        shuduView.setSelectedTile(tile);
        //取消对话框的显示
        dismiss();
    }

}
