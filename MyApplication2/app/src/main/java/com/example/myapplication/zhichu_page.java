package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.litepal.LitePal;

import java.time.LocalDate;

public class zhichu_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //页面显示
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhichu_page);

        //去除状态栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
        }

        //活动跳转到收入界面
        TextView Text1 = (TextView) findViewById(R.id.shouru_page);
        Text1.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(zhichu_page.this, shouru_page.class);
                startActivity(intent);
            }
        }));

        //活动跳转到转账界面
        TextView Text2 = (TextView) findViewById(R.id.ZhuanZhang_page);
        Text2.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(zhichu_page.this, ZhuanZhang_page.class);
                startActivity(intent);
            }
        }));

        BottomNavigationView navView = findViewById(R.id.nav_view);

        //点击底部的状态栏跳转到其他活动
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_detail://流水
//                        Intent intent1 = new Intent(zhichu_page.this, zhichu_page.class);
//                        startActivity(intent1);
                        break;
                    case R.id.navigation_chart://图表
                        Intent intent2 = new Intent(zhichu_page.this, ChartActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.navigation_person://我的
                        Intent intent3 = new Intent(zhichu_page.this, MyActivity.class);
                        startActivity(intent3);
                        break;
                }
                return false;
            }
        });

        //数据库创建
        LitePal.initialize(this);
        load_data();
    }

    String mount;
    String year, month, day;
    String special, account, people, seller, remarks;
    public void load_data(){

        TextView commit;
        TextView change_to_win;
        final EditText mount_et, year_et, month_et, day_et, hour_et, special_et, account_et, seller_et, remarks_et;
        //记录点击的人物
        Spinner people_sp = (Spinner) findViewById(R.id.location_choice);
        people_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] locations = getResources().getStringArray(R.array.people);
                people = locations[pos];
                //Toast.makeText(Drawer_Layout.this, "你点击的是:"+location, 2000).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        mount_et = findViewById(R.id.weight_edit);
        year_et = findViewById(R.id.year_edit);
        month_et = findViewById(R.id.month_edit);
        day_et = findViewById(R.id.day_edit);
        special_et = findViewById(R.id.volume_edit);
        account_et = findViewById(R.id.reward_edit);
        seller_et = findViewById(R.id.call_edit);
        remarks_et = findViewById(R.id.message_edit);

        commit = findViewById(R.id.save_the_bill);

        commit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                mount = mount_et.getText().toString();
                special = special_et.getText().toString();
                account = account_et.getText().toString();
                seller = seller_et.getText().toString();
                remarks = remarks_et.getText().toString();
                year = year_et.getText().toString().trim();
                month = month_et.getText().toString().trim();
                day = day_et.getText().toString().trim();
                if (TextUtils.isEmpty(mount)) {
                    Toast.makeText(zhichu_page.this, "请输入金额", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(special)) {
                    Toast.makeText(zhichu_page.this, "请输入种类", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(account)) {
                    Toast.makeText(zhichu_page.this, "请输入账户", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(seller)) {
                    Toast.makeText(zhichu_page.this, "请输入商家", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(remarks)) {
                    Toast.makeText(zhichu_page.this, "请输入备注", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    int year_int = 0;
                    int month_int = 0;
                    int day_int = 0;
                    if (TextUtils.isEmpty(year) || TextUtils.isEmpty(month) || TextUtils.isEmpty(day)) {
                        LocalDate date = LocalDate.now();
                        year_int = date.getYear();
                        month_int = date.getMonthValue();
                        day_int = date.getDayOfMonth();
                    } else {
                        year_int = Integer.valueOf(year);
                        month_int = Integer.valueOf(month);
                        day_int = Integer.valueOf(day);
                    }
                    // 2020-10-19
                    Toast.makeText(zhichu_page.this, "保存成功", Toast.LENGTH_SHORT).show();
                    DataBase db = new DataBase(0 - Float.valueOf(mount), special, account, people, seller, remarks, year_int, month_int, day_int);
                    db.save();
                    Intent intent4 = new Intent(zhichu_page.this, ChartActivity.class);
                    startActivity(intent4);
                }
            }
        });


    }
}
