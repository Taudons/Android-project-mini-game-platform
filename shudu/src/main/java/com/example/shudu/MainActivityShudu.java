package com.example.shudu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivityShudu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.difficultyselection_shudu);
      //  setContentView(new ShuduView(this));
        Button Prebutton=(Button) findViewById(R.id.primary_game);
        Button Midbutton=(Button) findViewById(R.id.middle_game);
        Button Highbutton=(Button) findViewById(R.id.high_game);
        Prebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivityShudu.this,SkipActivity.class);
                startActivity(intent);
            }
        });
        Midbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivityShudu.this,SkipActivity.class);
                startActivity(intent);
            }
        });
        Highbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivityShudu.this,SkipActivity.class);
                startActivity(intent);
            }
        });
    }
}