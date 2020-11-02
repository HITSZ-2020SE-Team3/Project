package com.example.myapplication;
//用于显示按年/月筛选的分账户账单的活动界面（复用了流水界面（赶进度.jpg））
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class billClassify extends AppCompatActivity {

    private RecyclerView recyclerView;  //声明一个组件
    private RecyclerView.Adapter myAdapter; //声明适配器
    private LinearLayoutManager myLayoutManager;    //声明一个线性布局管理类
    private List<DataBase> allData; //所有符合条件的账单数据

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turnover);

        Intent intent = getIntent();    //取出传来的数据
        String account = intent.getStringExtra("account");  //拿出账户名
        int features = intent.getIntExtra("features",0);    //拿出功能号
        //更换页首的说明字符串
        TextView title = (TextView) findViewById(R.id.all_account_remain);
        String result = account + "账户的";     //用于存放页面题目字符串
        if(features == 1)   result += "年度账单";
        else if(features == 2)      result += "月度账单";
        title.setText(result);
        //下面开始设置RecyclerView
        recyclerView = (RecyclerView) this.findViewById(R.id.billRecylcer);    //开始设置RecyclerView
        recyclerView.setHasFixedSize(true); //设置固定大小
        myLayoutManager = new LinearLayoutManager(this); //创建线性布局
        //myLayoutManager.setOrientation(OrientationHelper.VERTICAL);//设置组件放置方向为垂直方向
        recyclerView.setLayoutManager(myLayoutManager); //给RecyclerView设置布局管理器
        //功能号需要从那边传过来，所以活动跳转的时候还需要加信息传输部分
        myAdapter = new turnoverAdapter(statistic.getAllBill(),statistic.getAllYear(),features);  //创建适配器，并且设置
        recyclerView.setAdapter(myAdapter);
    }
}