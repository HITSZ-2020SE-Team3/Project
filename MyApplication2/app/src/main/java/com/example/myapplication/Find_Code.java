package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Find_Code extends AppCompatActivity {
    private TextView tv_back;
    private Button btn_find_psw;
    private EditText find_back_user_name, find_back_answer;
    private String user_name, answer, md5psw, psw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find__code);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    private void init(){
        btn_find_psw = findViewById(R.id.btn_find_psw);
        find_back_user_name = findViewById(R.id.find_back_user_name);
        find_back_answer = findViewById(R.id.find_back_answer);
        tv_back = findViewById(R.id.tv_back);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回键
                Find_Code.this.finish();
            }
        });
        //找回密码按钮
        btn_find_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入在相应控件中的字符串
                getEditString();
                //判断输入框内容
                if(TextUtils.isEmpty(user_name)){
                    Toast.makeText(Find_Code.this, "请输入学号", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(answer)){
                    Toast.makeText(Find_Code.this, "请输入密保问题答案", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!isExistUserName(user_name)){
                    Toast.makeText(Find_Code.this, "该账号不存在", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    md5psw = readPsw(user_name);
                    psw = readPsw(md5psw);
                    AlertDialog dialog=new AlertDialog.Builder(Find_Code.this)
                            .setTitle("您的密码是")
                            .setMessage(psw)
                            .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //取消
                                }
                            })
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //确认
                                    Toast.makeText(Find_Code.this,"找回密码成功", Toast.LENGTH_SHORT).show();
                                    Intent data = new Intent();
                                    data.putExtra("userName", user_name);
                                    setResult(RESULT_OK, data);
                                    // RESULT_OK为Activity系统常量，状态码为-1，
                                    // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                                    Find_Code.this.finish();
                                    startActivity(new Intent(Find_Code.this, MainActivity.class));
                                }
                            })
                            .show();
                    // 设置弹窗内前端
                    TextView tvMsg = (TextView) dialog.findViewById(android.R.id.message);
                    tvMsg.setTextSize(20);
                    tvMsg.setTextColor(Color.parseColor("#FF0000"));
                    dialog.getButton(dialog.BUTTON_NEGATIVE).setTextSize(16);
                    dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#8C8C8C"));
                    dialog.getButton(dialog.BUTTON_POSITIVE).setTextSize(16);
                    dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#1DA6DD"));
                }
            }
        });

    }
    /**
     *从SharedPreferences中根据用户名读取密码
     */
    private String readPsw(String userName){
        //getSharedPreferences("loginInfo",MODE_PRIVATE);
        //"loginInfo",mode_private; MODE_PRIVATE表示可以继续写入
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        //sp.getString() userName, "";
        return sp.getString(userName , "");
    }
    /**
     * 获取控件中的字符串
     */
    private void getEditString(){
        user_name = find_back_user_name.getText().toString().trim();
        answer = find_back_answer.getText().toString().trim();
    }
    /**
     * 从SharedPreferences中读取输入的用户名，判断SharedPreferences中是否有此用户名
     */
    private boolean isExistUserName(String userName){
        boolean has_userName = false;
        //mode_private SharedPreferences sp  =  getSharedPreferences( );
        // "loginInfo", MODE_PRIVATE
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取密码
        String spPsw = sp.getString(userName, "");//传入用户名获取密码
        //如果密码不为空则确实保存过这个用户名
        if(!TextUtils.isEmpty(spPsw)) {
            has_userName = true;
        }
        return has_userName;
    }
}
