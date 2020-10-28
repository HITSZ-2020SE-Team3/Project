package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class ChartActivity extends AppCompatActivity {


    private PieChart pieChart;
    private HashMap dataMap;
    //    private List<DataBase> jizhangben;
    private boolean isOUT = true;//点击收入或支出，true代表支出
    private Double IN = 0.0, OUT = 0.0, TOTAL = 0.0;
    private Button bt_OUT, bt_IN;
    private String choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        dataMap=new HashMap();
        pieChart=(PieChart)findViewById(R.id.pie_chart);
        Button bt_OUT = (Button) findViewById(R.id.textView_out_chart);
        Button bt_IN = (Button) findViewById(R.id.textView_in_chart);
        TextView Text_account = (TextView) findViewById(R.id.chart_choice_account);
        TextView Text_Sprcial = (TextView) findViewById(R.id.chart_choice_sprcial);
        TextView Text_People = (TextView) findViewById(R.id.chart_choice_people);

        bt_IN.setOnClickListener(new View.OnClickListener() {//收入
            @Override
            public void onClick(View v) {
                isOUT = false;
                renewChart();
            }
        });

        bt_OUT.setOnClickListener(new View.OnClickListener() {//支出
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

        choose();
        renewChart();

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
        }

        if (choice == "按照账户") {
            renewChart_by_account(jizhangben);
        } else if (choice == "按照分类") {
            renewChart_by_Special(jizhangben);
        } else if (choice == "按照成员") {
            renewChart_by_People(jizhangben);
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
                for (int j = 0; j < i; j++) {
                    dataMap.put(categories[j], cWeight[j]);
                }
            } else {//支出，负
                int i = 0;
                for (DataBase w : jizhangben) {
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
                for (int j = 0; j < i; j++) {
                    dataMap.put(categories[j], cWeight[j]);
                }
            } else {//支出，负
                int i = 0;
                for (DataBase w : jizhangben) {
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
                for (int j = 0; j < i; j++) {
                    dataMap.put(categories[j], cWeight[j]);
                }
            } else {//支出，负
                int i = 0;
                for (DataBase w : jizhangben) {
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
                for (int j = 0; j < i; j++) {
                    dataMap.put(categories[j], cWeight[j]);
                }
            }
        }
    }

    public void choose(){
        TextView commit;
        TextView change_to_win;
        final EditText mount_et, year_et, month_et, day_et, hour_et, sprcial_et, account_et, seller_et, remarks_et;
        //记录点击的人物
        Spinner choice_sp = (Spinner) findViewById(R.id.chart_choice);
        choice_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] locations = getResources().getStringArray(R.array.choice);
                choice = locations[pos];
//                renewChart();
//                Toast.makeText(Drawer_Layout.this, "你点击的是:"+location, 2000).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

}

