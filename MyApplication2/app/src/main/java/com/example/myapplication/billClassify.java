package com.example.myapplication;
//用于显示按年/月筛选的分账户账单的活动界面（复用了流水界面（赶进度.jpg））
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import static com.google.android.material.bottomnavigation.LabelVisibilityMode.LABEL_VISIBILITY_LABELED;

public class billClassify extends AppCompatActivity {

    private RecyclerView recyclerView;  //声明一个组件
    private RecyclerView.Adapter myAdapter; //声明适配器
    private LinearLayoutManager myLayoutManager;    //声明一个线性布局管理类
    private List<DataBase> allData; //所有符合条件的账单数据

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
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
                        Intent intent1 = new Intent(billClassify.this, statisticActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.navigation_liushui://流水
                        Intent intent2 = new Intent(billClassify.this, turnoverActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.navigation_add://记账
                        Intent intent3 = new Intent(billClassify.this, zhichu_page.class);
                        startActivity(intent3);
                        break;
                    case R.id.navigation_chart://图表
                        Intent intent4 = new Intent(billClassify.this, ChartActivity.class);
                        startActivity(intent4);
                        break;
                    case R.id.navigation_person://我的
                        Intent intent5 = new Intent(billClassify.this, MyActivity.class);
                        startActivity(intent5);
                        break;
                }
                return false;
            }
        });

        Intent intent = getIntent();    //取出传来的数据
        String account = intent.getStringExtra("account");  //拿出账户名
        int features = intent.getIntExtra("features",0);    //拿出功能号
        allData = statistic.getOneAccountBill(account); //数据就位
        //更换页首的说明字符串
        TextView title = findViewById(R.id.all_account_remain);
        String result = account + "账户的";     //用于存放页面题目字符串
        if(features == 1)   result += "年度账单";
        else if(features == 2)      result += "月度账单";
        title.setText(result);
        //余额显示设置，由于是静态内容，所以不在数据适配器adapter中设置，否则出错
        TextView remain = findViewById(R.id.the_account_remain_data);
        Float remainResult = statistic.getRemain(allData);
        if(remainResult > 0)      remain.setTextColor(Color.rgb(255,69,0));   //余额为正，设置为红色字体
        else    remain.setTextColor(Color.rgb(255,69,0));   //余额为负，显示为绿色
        remain.setText(remainResult + "元" );
        //下面开始设置RecyclerView
        recyclerView = this.findViewById(R.id.billRecycler);    //开始设置RecyclerView
        recyclerView.setHasFixedSize(true); //设置固定大小
        myLayoutManager = new LinearLayoutManager(this); //创建线性布局
        //myLayoutManager.setOrientation(OrientationHelper.VERTICAL);//设置组件放置方向为垂直方向
        recyclerView.setLayoutManager(myLayoutManager); //给RecyclerView设置布局管理器
        //功能号需要从那边传过来，所以活动跳转的时候还需要加信息传输部分
        myAdapter = new turnoverAdapter(allData,statistic.getAllYear(),features);  //创建适配器，并且设置
        //注意，这里调用turnover的数据适配器，因为UI格式是流水形式！
        recyclerView.setAdapter(myAdapter);
    }
}