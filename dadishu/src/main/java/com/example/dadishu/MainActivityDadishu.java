package com.example.dadishu;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivityDadishu extends AppCompatActivity {
    public static final int MSG_CODE = 87987987;
    public static final int MAX_NUMBER = 20;
    public static final int RANDOM_NUMBER = 500;

    private TextView result_View,cd_view;
    private Button start_Btn;
    private ImageView diglet_View;
    private int totalCount,hitCount,steps,delayTime;
    private int[][] position_Array;
    private Handler handler = new Handler(){
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_CODE:
                    if (totalCount > MAX_NUMBER){
                        clear();
                        Toast.makeText(MainActivityDadishu.this, "游戏结束", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int cc = MAX_NUMBER-totalCount;
                    cd_view.setText("已出现了"+totalCount+"只地鼠，还剩"+cc+"只地鼠");
                    steps = msg.arg1;
                    MainActivityDadishu.this.diglet_View.setX(MainActivityDadishu.this.position_Array[steps][0]);
                    MainActivityDadishu.this.diglet_View.setY(MainActivityDadishu.this.position_Array[steps][1]);
                    MainActivityDadishu.this.diglet_View.setVisibility(View.VISIBLE);
                    int randomTime = new Random().nextInt(RANDOM_NUMBER) + RANDOM_NUMBER;
                    MainActivityDadishu.this.code(randomTime);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dadishu);
        initView();
        initData();
    }
    private void initData() {
        totalCount=0;
        hitCount=0;
        //地鼠位置
        position_Array=new int[][]{
                {123,446},{298,157},
                {674,199},{485,677},
                {396,987},{766,677},
                {555,198},{198,555},
        };
    }
    private void initView() {
        result_View=findViewById(R.id.result_View);
        start_Btn=findViewById(R.id.start_Btn);
        diglet_View=findViewById(R.id.diglet_View);
        cd_view= findViewById(R.id.cd_View);
    }
    public void myClick(View v) {
        int id = v.getId();
        if (id == R.id.start_Btn) {//请添加代码
            start_Btn.setText("游戏中");
            start_Btn.setEnabled(false);
            code(0);
        } else if (id == R.id.diglet_View) {//请添加代码
            diglet_View.setVisibility(View.GONE);
            hitCount++;
            result_View.setText("已击中" + hitCount + "只");
        }
    }
    private void code(int delayTime) {
        int position = new Random().nextInt(position_Array.length);
        Message message = Message.obtain();
        message.what = MSG_CODE;
        message.arg1 = position;
        handler.sendMessageDelayed(message, delayTime);
        totalCount++;
    }
    private void clear() {
        totalCount=0;
        hitCount=0;
        start_Btn.setText("开始游戏");
        start_Btn.setEnabled(true);
        diglet_View.setVisibility(View.GONE);
    }

}