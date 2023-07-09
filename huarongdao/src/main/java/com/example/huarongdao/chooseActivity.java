package com.example.huarongdao;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

public class chooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huarongdao_choose);
    }

    public void startPass(View view) {
        Intent intent = new Intent(chooseActivity.this, gamectivity.class);
        int id = view.getId();
        if (id == R.id.pass1) {
            intent.putExtra("passId", "1");
        } else if (id == R.id.pass2) {
            intent.putExtra("passId", "2");
        } else if (id == R.id.pass3) {
            intent.putExtra("passId", "3");
        } else if (id == R.id.pass4) {
            intent.putExtra("passId", "4");
        } else if (id == R.id.pass5) {
            intent.putExtra("passId", "5");
        } else if (id == R.id.pass6) {
            intent.putExtra("passId", "6");
        } else if (id == R.id.pass7) {
            intent.putExtra("passId", "7");
        } else if (id == R.id.pass8) {
            intent.putExtra("passId", "8");
        } else if (id == R.id.pass9) {
            intent.putExtra("passId", "9");
        } else if (id == R.id.pass10) {
            intent.putExtra("passId", "10");
        } else if (id == R.id.pass11) {
            intent.putExtra("passId", "11");
        } else if (id == R.id.pass12) {
            intent.putExtra("passId", "12");
        } else {
            intent.putExtra("passId", "1");
        }
        startActivity(intent);
    }
}
