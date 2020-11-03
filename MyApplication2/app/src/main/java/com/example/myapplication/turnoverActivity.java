package com.example.myapplication;
//这是流水界面，展示所有流水信息
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.material.bottomnavigation.LabelVisibilityMode.LABEL_VISIBILITY_LABELED;

public class turnoverActivity extends AppCompatActivity {

    private RecyclerView recyclerView;  //声明一个组件
    private RecyclerView.Adapter myAdapter; //声明适配器
    private LinearLayoutManager myLayoutManager;    //声明一个线性布局管理类
    private List<DataBase> allData = statistic.getAllBill(); //所有符合条件的账单数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turnover);

        //点击底部的状态栏跳转到其他活动
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setLabelVisibilityMode(LABEL_VISIBILITY_LABELED);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_detail://统计
                        Intent intent1 = new Intent(turnoverActivity.this, statisticActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.navigation_liushui://流水
                        Intent intent2 = new Intent(turnoverActivity.this, turnoverActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.navigation_add://记账
                        Intent intent3 = new Intent(turnoverActivity.this, zhichu_page.class);
                        startActivity(intent3);
                        break;
                    case R.id.navigation_chart://图表
                        Intent intent4 = new Intent(turnoverActivity.this, ChartActivity.class);
                        startActivity(intent4);
                        break;
                    case R.id.navigation_person://我的
                        Intent intent5 = new Intent(turnoverActivity.this, MyActivity.class);
                        startActivity(intent5);
                        break;
                }
                return false;
            }
        });

        //下面设置余额的显示
        TextView remain = findViewById(R.id.the_account_remain_data);    //找到余额的显示文本
        float result = statistic.getAllAccountRemain();
        remain.setText(result + "元");
        if(result > 0)      remain.setTextColor(Color.rgb(255,69,0));   //余额为正，设置为红色字体
        else    remain.setTextColor(Color.rgb(255,69,0));   //余额为负，显示为绿色
        //下面开始设置RecyclerView
        recyclerView = (RecyclerView) this.findViewById(R.id.billRecylcer);    //开始设置RecyclerView
        recyclerView.setHasFixedSize(true); //设置固定大小
        myLayoutManager = new LinearLayoutManager(this); //创建线性布局
        //myLayoutManager.setOrientation(OrientationHelper.VERTICAL);//设置组件放置方向为垂直方向
        recyclerView.setLayoutManager(myLayoutManager); //给RecyclerView设置布局管理器
        myAdapter = new turnoverAdapter(allData,new ArrayList<Integer>(),0);  //创建适配器，并且设置
        recyclerView.setAdapter(myAdapter);
    }
}