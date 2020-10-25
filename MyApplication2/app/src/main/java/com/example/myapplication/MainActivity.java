package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.squareup.haha.perflib.Main;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    private void init() {
        Button btn_pswlogin = findViewById(R.id.btn_pswlogin);
        Button btn_register_main = findViewById(R.id.btn_register_main);
        Button btn_geslogin = findViewById(R.id.btn_geslogin);
        Button btn_finlogin = findViewById(R.id.btn_finlogin);
        btn_pswlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到密码登录界面，并实现密码登录功能
                Intent intent = new Intent(MainActivity.this,LoginActicity.class);
                startActivityForResult(intent,1);
            }
        });
        btn_register_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到注册界面，并实现注册功能
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivityForResult(intent,1);
            }
        });
        btn_geslogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到手势密码登录界面，并实现手势密码登录功能
//                Intent intent = new Intent(MainActivity.this,OtherLoginActivity.class);
//                startActivityForResult(intent,1);
                GestureUnlock.getInstance().init(MainActivity.this.getApplicationContext());
                GestureUnlock.getInstance().verifyGestureUnlock(MainActivity.this);
            }
        });
        btn_finlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到指纹登录界面，并实现指纹登录功能
                Intent intent = new Intent(MainActivity.this,OtherLoginActivity.class);
                startActivityForResult(intent,1);

            }
        });
    }
}