package com.example.myapplication;
//这里是统计界面的活动（总体余额+各账户的余额）
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
import android.os.TestLooperManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import static com.google.android.material.bottomnavigation.LabelVisibilityMode.LABEL_VISIBILITY_LABELED;

@RequiresApi(api = Build.VERSION_CODES.N)
public class statisticActivity extends AppCompatActivity {

    private RecyclerView recyclerView;  //声明一个组件
    private RecyclerView.Adapter myAdapter; //声明适配器
    private LinearLayoutManager myLayoutManager;    //声明一个线性布局管理类
    private List<String> allData = statistic.getAllAccount(); //所有账户名


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        //点击底部的状态栏跳转到其他活动
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setLabelVisibilityMode(LABEL_VISIBILITY_LABELED);//使底部图标个数>5时能显示文字
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_detail://统计
                        Intent intent1 = new Intent(statisticActivity.this, statisticActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.navigation_liushui://流水
                        Intent intent2 = new Intent(statisticActivity.this, turnoverActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.navigation_add://记账
                        Intent intent3 = new Intent(statisticActivity.this, zhichu_page.class);
                        startActivity(intent3);
                        break;
                    case R.id.navigation_chart://图表
                        Intent intent4 = new Intent(statisticActivity.this, ChartActivity.class);
                        startActivity(intent4);
                        break;
                    case R.id.navigation_person://我的
                        Intent intent5 = new Intent(statisticActivity.this, MyActivity.class);
                        startActivity(intent5);
                        break;
                }
                return false;
            }
        });

       /* Button refresh = (Button) findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           allData.clear();
                                           allData = statistic.getAllAccount(); //重新存储数据
                                           myAdapter.notifyDataSetChanged();   //数据库可能发生变化，重新生成UI界面
                                       }
                                   }

        );*/    //这按钮反而把数据刷没了……暂时去掉它
        if(allData.size() == 0)     Toast.makeText(statisticActivity.this, "当前无数据", Toast.LENGTH_SHORT).show();
        TextView remain = findViewById(R.id.all_account_remain_data);    //找到余额的显示文本
        float result = statistic.getAllAccountRemain();
        remain.setText(result + "元");
        if(result > 0)      remain.setTextColor(Color.rgb(255,69,0));   //余额为正，设置为红色字体
        else    remain.setTextColor(Color.rgb(255,69,0));   //余额为负，显示为绿色
        //下面开始设置ReclyclerView
        recyclerView = (RecyclerView) this.findViewById(R.id.accountRecycler);
        recyclerView.setHasFixedSize(true); //设置固定大小
        myLayoutManager = new LinearLayoutManager(this); //创建线性布局
        //myLayoutManager.setOrientation(OrientationHelper.VERTICAL);//设置组件放置方向为垂直方向
        recyclerView.setLayoutManager(myLayoutManager); //给RecyclerView设置布局管理器
        myAdapter = new accountAdapter(allData);  //创建适配器，并且初始化数据
        recyclerView.setAdapter(myAdapter);
    }

}