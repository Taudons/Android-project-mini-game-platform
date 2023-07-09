package com.example.finalproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dadishu.MainActivityDadishu;
import com.example.game2048.SplashActivity2048;
import com.example.huarongdao.MainActivityHuarongdao;
import com.example.shudu.MainActivityShudu;
import com.example.sl.MainActivitySaolei;


public class GameMainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_allgame);

        //获取控件id
        btn1=(Button) findViewById(R.id.btn_1);
        btn2=(Button) findViewById(R.id.btn_2);
        btn3=(Button) findViewById(R.id.btn_3);
        btn4=(Button) findViewById(R.id.btn_4);
        btn5=(Button) findViewById(R.id.btn_5);
        btn6=(Button) findViewById(R.id.btn_6);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
    }
    //监听点击事件
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_1:
                //第一个游戏
                //打地鼠
                // 跳转到打地鼠的界面
                intent=new Intent(this, MainActivityDadishu.class);
                startActivity(intent);
                break;
            case R.id.btn_2:
                //第二个游戏
                //扫雷
                // 跳转到扫雷的界面
               intent=new Intent(this, MainActivitySaolei.class);
                startActivity(intent);
                break;
            case R.id.btn_3:
                //第三个游戏
                // 华容道
                // 跳转到华容道的界面
                intent=new Intent(this, MainActivityHuarongdao.class);
                startActivity(intent);
                break;
            case R.id.btn_4:
                //第四个游戏
                //2048
                // 跳转到2048的界面
                intent=new Intent(this, SplashActivity2048.class);
                startActivity(intent);
                break;
            case R.id.btn_5:
                //第五个游戏
                //数独
                // 跳转到数独的界面
                intent=new Intent(this, MainActivityShudu.class);
                startActivity(intent);
                break;
            case R.id.btn_6:
                //第六个游戏
                //更多游戏
                //
                intent=new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://www.taptap.cn/");
                intent.setData(content_url);
                //开始于当前intent
                startActivity(intent);

                break;
        }
    }
}