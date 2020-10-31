package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyActivity extends AppCompatActivity {

    private List<My> mData = new ArrayList<>();
    private Context mContext;
    private MyAdapter mAdapter = null;
    private ListView list_my;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        //点击底部的状态栏跳转到其他活动
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_detail://流水
//                        Intent intent1 = new Intent(ChartActivity.this, zhichu_page.class);
//                        startActivity(intent1);
                        break;
                    case R.id.navigation_chart://图表
                        Intent intent2 = new Intent(MyActivity.this, ChartActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.navigation_person://我的
//                        Intent intent3 = new Intent(ChartActivity.this, shouru_page.class);
//                        startActivity(intent3);
                        break;
                }
                return false;
            }
        });

        //向Listview中传数据
        mContext = MyActivity.this;

        mData = new LinkedList<My>();
        mData.add(new My("导入", "导入数据", R.drawable.ic_import_blue_32dp));
        mData.add(new My("导出", "导出数据", R.drawable.ic_export2_blue_32dp));
        mData.add(new My("建议", "建议与反馈", R.drawable.ic_advice_blue_32dp));
        mData.add(new My("版本", "版本许可信息", R.drawable.ic_version_blue_32dp));
        mData.add(new My("关于", "开发者信息", R.drawable.ic_member_blue_32dp));

        MyAdapter mAdapter = new MyAdapter((LinkedList<My>) mData, mContext);

        ListView list_my = (ListView) findViewById(R.id.list_my_view);
        list_my.setAdapter(mAdapter);

        //点击list发生的事件
        list_my.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                My my = mData.get(position);
                switch (position){
                    case 0:
                        Toast.makeText(MyActivity.this, "导入数据成功", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(MyActivity.this, "导出数据成功", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(MyActivity.this, "欢迎联系开发人员提交您的建议与反馈", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
//                        Intent intent1 = new Intent(MyActivity.this, zhichu_page.class);
//                        startActivity(intent1);
                        break;
                    case 4:
                        Intent intent2 = new Intent(MyActivity.this, AboutActivity.class);
                        startActivity(intent2);
                        break;
                }

            }
        });
    }
}
