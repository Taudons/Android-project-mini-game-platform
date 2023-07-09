package com.example.shudu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SkipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(new ShuduView(this));
    }
}