package com.example.myapplication;
//这里是统计界面的活动（总体余额+各账户的余额）
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.view.View;
import android.widget.TextView;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class statisticActivity extends AppCompatActivity {

    private RecyclerView recyclerView;  //声明一个组件
    private RecyclerView.Adapter myAdapter; //声明适配器
    private LinearLayoutManager myLayoutManager;    //声明一个线性布局管理类
    private List<String> allData = statistic.getAllAccount(); //所有账户名

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        TextView remain = findViewById(R.id.all_account_remain_data);    //找到余额文本显示
        float result = statistic.getAllAccountRemain();
        remain.setText(String.valueOf(result) + "元");
        if(result > 0)      remain.setTextColor(Color.rgb(255,69,0));   //余额为正，设置为红色字体
        else    remain.setTextColor(Color.rgb(255,69,0));   //余额为负，显示为绿色

        recyclerView = (RecyclerView) this.findViewById(R.id.accountRecylcer);    //开始设置RecyclerView
        recyclerView.setHasFixedSize(true); //设置固定大小
        myLayoutManager = new LinearLayoutManager(this); //创建线性布局
        //myLayoutManager.setOrientation(OrientationHelper.VERTICAL);//设置组件放置方向为垂直方向
        recyclerView.setLayoutManager(myLayoutManager); //给RecyclerView设置布局管理器
        myAdapter = new accountAdapter(allData);  //创建适配器，并且设置
        recyclerView.setAdapter(myAdapter);
    }
}