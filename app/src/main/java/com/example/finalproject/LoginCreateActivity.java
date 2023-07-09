package com.example.finalproject;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.dbHelper.MyHelper;

import java.util.Random;

public class LoginCreateActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText phoneEdit;
    private EditText passwordEdit;
    private EditText verifyEdit;
    private String mVerifyCode;
    MyHelper myHelper;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_creat);
        myHelper = new MyHelper(this);
        //获取组件
        Button login = findViewById(R.id.btn_login);
        Button verify = findViewById(R.id.btn_verifycode);
        phoneEdit=findViewById(R.id.phone);
        passwordEdit=findViewById(R.id.password);
        verifyEdit=findViewById(R.id.verifycode);
        TextView tv_a = findViewById(R.id.tv_account);
        TextView tv_p = findViewById(R.id.tv_password);
        //注册监听器
        login.setOnClickListener(this);
        verify.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_verifycode:
                mVerifyCode = String.format("%06d", new Random().nextInt(999999));
                // 以下弹出提醒对话框，提示用户记住六位验证码数字
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("请记住验证码");
                builder.setMessage("手机号" + phoneEdit.getText() + ",本次验证码是" + mVerifyCode + ",请输入验证码");
                builder.setPositiveButton("好的", null);
                AlertDialog dialog = builder.create();
                dialog.show();
                break;

            case R.id.btn_login:
                String phone=phoneEdit.getText().toString();
                String password=passwordEdit.getText().toString();
                String verify=verifyEdit.getText().toString();
                if(phone.isEmpty()){
                    Toast.makeText(LoginCreateActivity.this,"请输入手机号",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.isEmpty()){
                    Toast.makeText(LoginCreateActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(verify.isEmpty()){
                    Toast.makeText(LoginCreateActivity.this,"请输入验证码",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(verify.compareTo(mVerifyCode) != 0){
                    Toast.makeText(LoginCreateActivity.this,"验证码错误",Toast.LENGTH_SHORT).show();
                    return;
                }
                addAccount();
        }
    }
    public void addAccount(){
        SQLiteDatabase db;
        String phone=phoneEdit.getText().toString();
        String password=passwordEdit.getText().toString();
        db = myHelper.getWritableDatabase();//获取可读写SQLiteDatabase对象
        ContentValues values = new ContentValues();        //创建ContentValues对象
        values.put("phone", phone);             //将数据添加到ContentValues对象
        values.put("password", password);
        db.insert("account", null, values);
        Toast.makeText(this, "信息已添加", Toast.LENGTH_SHORT).show();
        db.close();
    }
}

