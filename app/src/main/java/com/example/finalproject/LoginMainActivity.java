package com.example.finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.dbHelper.MyHelper;

import java.util.Random;

public class LoginMainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private TextView tv_password;
    private EditText et_password;
    private Button btn_forget;
    private CheckBox ck_remember;
    private EditText et_phone;
    private RadioButton rb_password;
    private RadioButton rb_verifycode;
    private Button btn_login;
    private Button btn_create;
    private String mPassword;
    private String mPhone;
    private String mVerifyCode;
    private SharedPreferences preferences;
    MyHelper myHelper;
    String dbPhone;
    String dbPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        RadioGroup rb_login = findViewById(R.id.rg_login);
        tv_password = findViewById(R.id.tv_password);
        et_phone = findViewById(R.id.et_phone);
        et_password = findViewById(R.id.et_password);
        btn_forget = findViewById(R.id.btn_forget);
        ck_remember = findViewById(R.id.ck_remember);
        rb_password = findViewById(R.id.rb_password);
        rb_verifycode = findViewById(R.id.rb_verifycode);
        btn_create=findViewById(R.id.btn_create);
        btn_login = findViewById(R.id.btn_login);
        // 给rg_login设置单选监听器
        rb_login.setOnCheckedChangeListener(this);
        //设置按钮监听器
        btn_forget.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_create.setOnClickListener(this);

        myHelper=new MyHelper(this);

        setPhonePassword();
    }

    public void setPhonePassword(){
        preferences=getSharedPreferences("ID", Context.MODE_PRIVATE);
        et_phone.setText(preferences.getString("phone",""));
        et_password.setText(preferences.getString("password",""));
    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            // 选择了密码登录
            case R.id.rb_password:
                tv_password.setText(getString(R.string.login_password));
                et_password.setHint(getString(R.string.input_password));
                btn_forget.setText(getString(R.string.forget_password));
                ck_remember.setVisibility(View.VISIBLE);
                break;
            // 选择了验证码登录
            case R.id.rb_verifycode:
                tv_password.setText(getString(R.string.verifycode));
                et_password.setHint(getString(R.string.input_verifycode));
                btn_forget.setText(getString(R.string.get_verifycode));
                ck_remember.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        String phone = et_phone.getText().toString();
        mPhone=et_phone.getText().toString();
        mPassword=et_password.getText().toString();
        switch (v.getId()) {
            case R.id.btn_forget:
                // 选择了密码方式校验，此时要跳到找回密码页面
                if (rb_password.isChecked()) {
                    Intent intent = new Intent(this, LoginForgetActivity.class);
                    startActivity(intent);
                } else if (rb_verifycode.isChecked()) {
                    // 生成六位随机数字的验证码
                    mVerifyCode = String.format("%06d", new Random().nextInt(999999));
                    // 以下弹出提醒对话框，提示用户记住六位验证码数字
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("请记住验证码");
                    builder.setMessage("手机号" + phone + ",本次验证码是" + mVerifyCode + ",请输入验证码");
                    builder.setPositiveButton("好的", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                break;
            case R.id.btn_login:
                // 密码方式校验
                if (rb_password.isChecked()) {
                    checkAccount();
                } else if (rb_verifycode.isChecked()) {
                    // 验证码方式校验
                    if (!mVerifyCode.equals(et_password.getText().toString())) {
                        Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // 提示用户登录成功
                    loginSuccess();
                }
                break;
            case  R.id.btn_create:
                //跳转到账号创建界面
                Intent intent = new Intent(this, LoginCreateActivity.class);
                startActivity(intent);
        }
    }
    public void checkAccount() {
        SQLiteDatabase db;
        db = myHelper.getReadableDatabase();
        int flag = 0;
        Cursor cursor = db.query("account", null, null, null, null, null, null);
        if (cursor.getCount() == 0) {
            Toast.makeText(LoginMainActivity.this, "无用户！", Toast.LENGTH_SHORT).show();
        }
        if (cursor.moveToFirst()) {
            do {
                // 在此处访问每行数据的列
                dbPhone = cursor.getString(0);
                // 其他操作...
                if (mPhone.equals(dbPhone)) {
                    flag = 1;
                    break;
                }
            } while (cursor.moveToNext());
        }
        if (flag == 0) {
            Toast.makeText(LoginMainActivity.this, "手机号错误或不存在！", Toast.LENGTH_SHORT).show();
        }
        String[] s = {"password"};
        Cursor cursor2 = db.query("account", s, "phone='"+mPhone+"'", null, null, null, null);
        if (cursor2.moveToFirst()) {
            dbPassword = cursor2.getString(0);
            if (dbPassword.equals(mPassword)) {
                // 以下弹出提醒对话框，提示用户登录成功
                loginSuccess();
            }
            else {
                Toast.makeText(LoginMainActivity.this, "密码错误或不存在！", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void loginSuccess() {
        if (ck_remember.isChecked()) {
            preferences=getSharedPreferences("ID", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("phone", et_phone.getText().toString());
            editor.putString("password", et_password.getText().toString());
            editor.putBoolean("isRemember", ck_remember.isChecked());
            editor.commit();
        }
        else{
            preferences=getSharedPreferences("ID", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
        }
        String desc = String.format("您的手机号码是%s，恭喜你通过登录验证",
                et_phone.getText().toString());
        // 以下弹出提醒对话框，提示用户登录成功
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("登录成功");
        builder.setMessage(desc);
        builder.setPositiveButton("点击进入", (dialog, which) -> {
            // 结束当前的活动页面
            Intent intent = new Intent(this, GameMainActivity.class);
            startActivity(intent);
        });
        builder.setNegativeButton("我再看看", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}