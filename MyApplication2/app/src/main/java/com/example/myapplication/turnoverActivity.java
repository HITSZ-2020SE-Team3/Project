package com.example.myapplication;
//这是流水界面，展示所有流水信息
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class turnoverActivity extends AppCompatActivity {

    private RecyclerView recyclerView;  //声明一个组件
    private RecyclerView.Adapter myAdapter; //声明适配器
    private LinearLayoutManager myLayoutManager;    //声明一个线性布局管理类
    private List<DataBase> allData = statistic.getAllBill(); //所有符合条件的账单数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turnover);
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