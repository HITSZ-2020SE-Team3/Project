package com.example.myapplication;
//用于显示按年筛选的分账户账单的活动界面（直接按年筛选数据库，然后降序排列即可，只不过需要加上年份的标识）
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class billSortByYear extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_sort_by_year);
    }
}