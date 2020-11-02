package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.google.android.material.bottomnavigation.LabelVisibilityMode.LABEL_VISIBILITY_LABELED;


public class ChartActivity extends AppCompatActivity {


    private PieChart pieChart;
    private HashMap dataMap;
    //    private List<DataBase> jizhangben;
    private boolean isOUT = true;//点击收入或支出，true代表支出
    private Double IN = 0.0, OUT = 0.0, TOTAL = 0.0;
    private Button bt_OUT, bt_IN;
    private String choice;
    private String start_year, start_month, start_day, end_year, end_month, end_day;

    private int start_year_int = 0;
    private int start_month_int = 0;
    private int start_day_int = 0;

    private int end_year_int = 0;
    private int end_month_int = 0;
    private int end_day_int = 0;

    private int start_time = 0;//存放用户输入的开始时间
    private int end_time = 0;//存放用户输入的结束时间

    private boolean time_flag = false;//判断是否需要用时间筛选，true则需要

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        dataMap=new HashMap();
        pieChart=(PieChart)findViewById(R.id.pie_chart);
        Button bt_OUT = (Button) findViewById(R.id.textView_out_chart);
        Button bt_IN = (Button) findViewById(R.id.textView_in_chart);
        TextView Text_account = (TextView) findViewById(R.id.chart_choice_account);
        TextView Text_Sprcial = (TextView) findViewById(R.id.chart_choice_special);
        TextView Text_People = (TextView) findViewById(R.id.chart_choice_people);
        TextView Text_Seller = (TextView) findViewById(R.id.chart_choice_seller);


        //点击底部的状态栏跳转到其他活动
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setLabelVisibilityMode(LABEL_VISIBILITY_LABELED);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_detail://统计
                        Intent intent1 = new Intent(ChartActivity.this, statisticActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.navigation_liushui://流水
                        Intent intent2 = new Intent(ChartActivity.this, turnoverActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.navigation_add://记账
                        Intent intent3 = new Intent(ChartActivity.this, zhichu_page.class);
                        startActivity(intent3);
                        break;
                    case R.id.navigation_chart://图表
                        Intent intent4 = new Intent(ChartActivity.this, ChartActivity.class);
                        startActivity(intent4);
                        break;
                    case R.id.navigation_person://我的
                        Intent intent5 = new Intent(ChartActivity.this, MyActivity.class);
                        startActivity(intent5);
                        break;
                }
                return false;
            }
        });

        //收入
        bt_IN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOUT = false;
                renewChart();
            }
        });

        //支出
        bt_OUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOUT = true;
                renewChart();
            }
        });

        //按照账户生成图表
        Text_account.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                choice = "按照账户";
                renewChart();
            }
        }));

        //按照分类生成图表
        Text_Sprcial.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                choice = "按照分类";
                renewChart();
            }
        }));

        //按照成员生成图表
        Text_People.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                choice = "按照成员";
                renewChart();
            }
        }));

        //按照商家生成图表
        Text_Seller.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                choice = "按照商家";
                renewChart();
            }
        }));

//        choose();
        renewChart();
        renewChart_choose_by_time();

        //点击事件
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                PieEntry pieEntry = (PieEntry) e;
                pieChart.setCenterText(pieEntry.getLabel());
            }

            @Override
            public void onNothingSelected() {
            }
        });
    }


    //更新UI
    public void renewChart(){
        List<DataBase> jizhangben = LitePal.findAll(DataBase.class);
        dataMap=new HashMap();

        if (jizhangben.isEmpty()){
            PieChartUtil.getPitChart().setPieChart(pieChart, dataMap, isOUT ? "支出" : "收入", true);
            Toast.makeText(ChartActivity.this, "当前无数据", Toast.LENGTH_SHORT).show();
        }

        if (choice == "按照账户") {
            renewChart_by_account(jizhangben);
        } else if (choice == "按照分类") {
            renewChart_by_Special(jizhangben);
        } else if (choice == "按照成员") {
            renewChart_by_People(jizhangben);
        } else if (choice == "按照商家"){
            renewChart_by_Seller(jizhangben);
        } else {//默认按照账户更新图表
            renewChart_by_account(jizhangben);
        }

        if(dataMap.isEmpty()){
            Toast.makeText(ChartActivity.this, "无满足当前条件的数据", Toast.LENGTH_SHORT).show();
        }

        PieChartUtil.getPitChart().setPieChart(pieChart, dataMap, isOUT ? "支出" : "收入", true);

        pieChart.notifyDataSetChanged();//图表的刷新
        pieChart.invalidate();

    }

    //按照账户更新图表
    public void renewChart_by_account(List<DataBase> jizhangben){
        if(jizhangben != null && !jizhangben.isEmpty()) {
            List<DataBase> jizhangbentemp = new ArrayList<>();
            String categoriesTmp = "";
            double[] cWeight = new double[jizhangben.size()];
            String[] categories = new String[jizhangben.size()];

            if (isOUT == false) {//收入，正
                int i = 0;
                for (DataBase w : jizhangben) {
                    if(time_select(w)){//w满足时间范围的筛选条件
                        if (w.getMoney() > 0) {
                            String cTmp = w.getAccount();
                            if (!categoriesTmp.contains(cTmp)) {//不含有某个种类
                                jizhangbentemp.add(w);
                                categoriesTmp += cTmp + " ";
                                categories[i] = cTmp;
                                cWeight[i] += w.getMoney();
                                i++;
                            } else {
                                for (int j = 0; j < i; j++)
                                    if (categories[j].equals(cTmp)) {
                                        cWeight[j] += w.getMoney();
                                        continue;
                                    }
                            }
                        }
                    }
                }
                for (int j = 0; j < i; j++) {
                    dataMap.put(categories[j], cWeight[j]);
                }
            } else {//支出，负
                int i = 0;
                for (DataBase w : jizhangben) {
                    if(time_select(w)) {//w满足时间范围的筛选条件
                        if (w.getMoney() < 0) {
                            String cTmp = w.getAccount();
                            if (!categoriesTmp.contains(cTmp)) {//不含有某个种类
                                jizhangbentemp.add(w);
                                categoriesTmp += cTmp + " ";
                                categories[i] = cTmp;
                                cWeight[i] -= w.getMoney();
                                i++;
                            } else {
                                for (int j = 0; j < i; j++)
                                    if (categories[j].equals(cTmp)) {
                                        cWeight[j] -= w.getMoney();
                                        continue;
                                    }
                            }
                        }
                    }
                }
                for (int j = 0; j < i; j++) {
                    dataMap.put(categories[j], cWeight[j]);
                }
            }
        }
    }

    //按照分类更新图表
    public void renewChart_by_Special(List<DataBase> jizhangben){
        if(jizhangben != null && !jizhangben.isEmpty()) {
            List<DataBase> jizhangbentemp = new ArrayList<>();
            String categoriesTmp = "";
            double[] cWeight = new double[jizhangben.size()];
            String[] categories = new String[jizhangben.size()];

            if (isOUT == false) {//收入，正
                int i = 0;
                for (DataBase w : jizhangben) {
                    if(time_select(w)) {//w满足时间范围的筛选条件
                        if (w.getMoney() > 0) {
                            String cTmp = w.getSpecial();
                            if (!categoriesTmp.contains(cTmp)) {//不含有某个种类
                                jizhangbentemp.add(w);
                                categoriesTmp += cTmp + " ";
                                categories[i] = cTmp;
                                cWeight[i] += w.getMoney();
                                i++;
                            } else {
                                for (int j = 0; j < i; j++)
                                    if (categories[j].equals(cTmp)) {
                                        cWeight[j] += w.getMoney();
                                        continue;
                                    }
                            }
                        }
                    }
                }
                for (int j = 0; j < i; j++) {
                    dataMap.put(categories[j], cWeight[j]);
                }
            } else {//支出，负
                int i = 0;
                for (DataBase w : jizhangben) {
                    if(time_select(w)) {//w满足时间范围的筛选条件
                        if (w.getMoney() < 0) {
                            String cTmp = w.getSpecial();
                            if (!categoriesTmp.contains(cTmp)) {//不含有某个种类
                                jizhangbentemp.add(w);
                                categoriesTmp += cTmp + " ";
                                categories[i] = cTmp;
                                cWeight[i] -= w.getMoney();
                                i++;
                            } else {
                                for (int j = 0; j < i; j++)
                                    if (categories[j].equals(cTmp)) {
                                        cWeight[j] -= w.getMoney();
                                        continue;
                                    }
                            }
                        }
                    }
                }
                for (int j = 0; j < i; j++) {
                    dataMap.put(categories[j], cWeight[j]);
                }
            }
        }
    }

    //按照成员更新图表
    public void renewChart_by_People(List<DataBase> jizhangben){
        if(jizhangben != null && !jizhangben.isEmpty()) {
            List<DataBase> jizhangbentemp = new ArrayList<>();
            String categoriesTmp = "";
            double[] cWeight = new double[jizhangben.size()];
            String[] categories = new String[jizhangben.size()];

            if (isOUT == false) {//收入，正
                int i = 0;
                for (DataBase w : jizhangben) {
                    if(time_select(w)) {//w满足时间范围的筛选条件
                        if (w.getMoney() > 0) {
                            String cTmp = w.getPeople();
                            if (!categoriesTmp.contains(cTmp)) {//不含有某个种类
                                jizhangbentemp.add(w);
                                categoriesTmp += cTmp + " ";
                                categories[i] = cTmp;
                                cWeight[i] += w.getMoney();
                                i++;
                            } else {
                                for (int j = 0; j < i; j++)
                                    if (categories[j].equals(cTmp)) {
                                        cWeight[j] += w.getMoney();
                                        continue;
                                    }
                            }
                        }
                    }
                }
                for (int j = 0; j < i; j++) {
                    dataMap.put(categories[j], cWeight[j]);
                }
            } else {//支出，负
                int i = 0;
                for (DataBase w : jizhangben) {
                    if(time_select(w)) {//w满足时间范围的筛选条件
                        if (w.getMoney() < 0) {
                            String cTmp = w.getPeople();
                            if (!categoriesTmp.contains(cTmp)) {//不含有某个种类
                                jizhangbentemp.add(w);
                                categoriesTmp += cTmp + " ";
                                categories[i] = cTmp;
                                cWeight[i] -= w.getMoney();
                                i++;
                            } else {
                                for (int j = 0; j < i; j++)
                                    if (categories[j].equals(cTmp)) {
                                        cWeight[j] -= w.getMoney();
                                        continue;
                                    }
                            }
                        }
                    }
                }
                for (int j = 0; j < i; j++) {
                    dataMap.put(categories[j], cWeight[j]);
                }
            }
        }
    }

    //按照商家更新图表
    public void renewChart_by_Seller(List<DataBase> jizhangben){
        if(jizhangben != null && !jizhangben.isEmpty()) {
            List<DataBase> jizhangbentemp = new ArrayList<>();
            String categoriesTmp = "";
            double[] cWeight = new double[jizhangben.size()];
            String[] categories = new String[jizhangben.size()];

            if (isOUT == false) {//收入，正
                int i = 0;
                for (DataBase w : jizhangben) {
                    if(time_select(w)){//w满足时间范围的筛选条件
                        if (w.getMoney() > 0) {
                            String cTmp = w.getSeller();
                            if (!categoriesTmp.contains(cTmp)) {//不含有某个种类
                                jizhangbentemp.add(w);
                                categoriesTmp += cTmp + " ";
                                categories[i] = cTmp;
                                cWeight[i] += w.getMoney();
                                i++;
                            } else {
                                for (int j = 0; j < i; j++)
                                    if (categories[j].equals(cTmp)) {
                                        cWeight[j] += w.getMoney();
                                        continue;
                                    }
                            }
                        }
                    }
                }
                for (int j = 0; j < i; j++) {
                    dataMap.put(categories[j], cWeight[j]);
                }
            } else {//支出，负
                int i = 0;
                for (DataBase w : jizhangben) {
                    if(time_select(w)) {//w满足时间范围的筛选条件
                        if (w.getMoney() < 0) {
                            String cTmp = w.getSeller();
                            if (!categoriesTmp.contains(cTmp)) {//不含有某个种类
                                jizhangbentemp.add(w);
                                categoriesTmp += cTmp + " ";
                                categories[i] = cTmp;
                                cWeight[i] -= w.getMoney();
                                i++;
                            } else {
                                for (int j = 0; j < i; j++)
                                    if (categories[j].equals(cTmp)) {
                                        cWeight[j] -= w.getMoney();
                                        continue;
                                    }
                            }
                        }
                    }
                }
                for (int j = 0; j < i; j++) {
                    dataMap.put(categories[j], cWeight[j]);
                }
            }
        }
    }


    //时间的筛选
    public void renewChart_choose_by_time(){
        final EditText start_year_et, start_month_et, start_day_et, end_year_et, end_month_et, end_day_et;
        TextView Save_time = (TextView) findViewById(R.id.save_time);

        start_year_et = findViewById(R.id.start_year_edit);
        start_month_et = findViewById(R.id.start_month_edit);
        start_day_et = findViewById(R.id.start_day_edit);

        end_year_et = findViewById(R.id.end_year_edit);
        end_month_et = findViewById(R.id.end_month_edit);
        end_day_et = findViewById(R.id.end_day_edit);

        renewChart();
        Save_time.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                time_flag = false;

                start_year = start_year_et.getText().toString();
                start_month = start_month_et.getText().toString();
                start_day = start_day_et.getText().toString();
                end_year = end_year_et.getText().toString();
                end_month = end_month_et.getText().toString();
                end_day = end_day_et.getText().toString();

                start_year = start_year_et.getText().toString().trim();
                start_month = start_month_et.getText().toString().trim();
                start_day = start_day_et.getText().toString().trim();
                end_year = end_year_et.getText().toString().trim();
                end_month = end_month_et.getText().toString().trim();
                end_day = end_day_et.getText().toString().trim();

                //如果所有空都填上了才会开始判断
                if (!TextUtils.isEmpty(start_year) && !TextUtils.isEmpty(end_year) && !TextUtils.isEmpty(start_month) && !TextUtils.isEmpty(end_month) && !TextUtils.isEmpty(start_day) && !TextUtils.isEmpty(end_day)){
                    start_year_int = Integer.valueOf(start_year);
                    start_month_int = Integer.valueOf(start_month);
                    start_day_int = Integer.valueOf(start_day);
                    end_year_int = Integer.valueOf(end_year);
                    end_month_int = Integer.valueOf(end_month);
                    end_day_int = Integer.valueOf(end_day);

//                    int start_time = 0;
//                    int end_time = 0;
                    start_time = statistic.dateChange(start_year_int, start_month_int, start_day_int);
                    end_time = statistic.dateChange(end_year_int, end_month_int, end_day_int);
                    if(start_time <= end_time){
                        Toast.makeText(ChartActivity.this, "时间筛选器保存成功", Toast.LENGTH_SHORT).show();
                        time_flag = true;//需要进行时间筛选
                        renewChart();
                    }else{
                        Toast.makeText(ChartActivity.this, "请输入正确的起止时间", Toast.LENGTH_SHORT).show();
                    }

//                    if(start_year_int <= end_year_int){
//                        if((start_year_int == end_year_int && start_month_int <= end_month_int)|| start_year_int < end_year_int){
//                            if(start_day_int <= end_day_int){
//                                Toast.makeText(ChartActivity.this, "时间筛选器保存成功", Toast.LENGTH_SHORT).show();
//                                time_flag = true;//需要进行时间筛选
//                                renewChart();
//                            }else{
//                                Toast.makeText(ChartActivity.this, "日期错误", Toast.LENGTH_SHORT).show();
//                            }
//                        }else {
//                            Toast.makeText(ChartActivity.this, "月份错误", Toast.LENGTH_SHORT).show();
//                        }
//                    }else {
//                        Toast.makeText(ChartActivity.this, "年份错误", Toast.LENGTH_SHORT).show();
//                    }
                }else{
                    Toast.makeText(ChartActivity.this, "请输入完整的年月日", Toast.LENGTH_SHORT).show();
                }
            }
        }));
    }

    //判断w的数据能不能满足时间上的筛选条件，
    public boolean time_select(DataBase w){
//        int time = w.getDate();//存放w中的时间信息
        //需要判断时间范围时
        if(time_flag){
//            if(w.getYear() >= start_year_int && w.getYear() <= end_year_int) {
//                if (w.getMonth() >= start_month_int && w.getMonth() <= end_month_int) {
//                    if (w.getDay() >= start_day_int && w.getDay() <= end_day_int) {
//                        return true;
//                    }
//                }
//            }
            if(w.getDate() >= start_time && w.getDate() <= end_time){
                return true;
            }
        } else {
            return true;//不需要判断时间范围时
        }
        return false;
    }

}

