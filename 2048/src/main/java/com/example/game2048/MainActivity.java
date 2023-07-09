package com.example.game2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.game2048.view.CommonDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button Restart,Continue;
    public static boolean Re_or_Con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Restart=findViewById(R.id.btn_restart);
        Continue=findViewById(R.id.btn_continue);

        Restart.setOnClickListener(this );
        Continue.setOnClickListener(this);
    }
    //退出提醒对话框
    @Override
    public void onBackPressed() {
        CommonDialog dialog = new CommonDialog(this, R.style.CustomDialog);
        dialog.setCancelable(false);
        dialog.setTitle("确认退出")
                .setMessage("")
                .setOnNegativeClickListener("狠心离开",
                        v -> {
                            finish();
                        })
                .setOnPositiveClickedListener("再玩一会儿", v -> dialog.cancel())
                .show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_restart) {
            Re_or_Con = false;
        } else if (id == R.id.btn_continue) {
            Re_or_Con = true;
        }
        Intent intent=new Intent(MainActivity.this,GameActivity.class);
        startActivity(intent);
    }
}