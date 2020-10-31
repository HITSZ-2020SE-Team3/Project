package com.example.myapplication;
//这是RecyclerView的数据适配器，将数据和展示用的item绑定
//流水的数据适配器
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class turnoverAdapter extends RecyclerView.Adapter<turnoverAdapter.ViewHolder> {

    private List<DataBase> myBill;  //所有展示用账本数据

    static class ViewHolder extends RecyclerView.ViewHolder{    //指在布局中需要展示的内容
        TextView billYear;      //账单年份
        TextView billMonth;     //账单月份
        TextView billDay;       //账单日期
        TextView billNumber;    //账单数额，后面会加上收入/支出的注明
        TextView billType;      //账单类型
        TextView billAccount;   //账单账户

        public ViewHolder(View view){
            super(view);
            billYear = view.findViewById(R.id.year);//这里对应上面类内部声明的变量
            billMonth = view.findViewById(R.id.month);
            billDay = view.findViewById(R.id.day);
            billNumber = view.findViewById(R.id.money);
            billType = view.findViewById(R.id.special_data);
            billAccount = view.findViewById(R.id.account_data);
        }

    }

    public turnoverAdapter(List<DataBase> allData) {
        myBill = allData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.turnover_item,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {    //根据position找到对应数据
        DataBase example = myBill.get(position);
        holder.billYear.setText(String.valueOf(example.getYear()) );  //与上面的自定义ViewHolder类接起来了
        holder.billMonth.setText(String.valueOf(example.getMonth()) );
        holder.billDay.setText(String.valueOf(example.getDay()) );
        if(example.getMoney() > 0){//是收入
            String temp = "收入 " + String.valueOf(example.getMoney()) + " 元";
            holder.billNumber.setText(temp);
            holder.billNumber.setTextColor(Color.rgb(255,69,0));   //红色
        }
        else{//其他情况为支出
            String temp = "支出 " + String.valueOf(example.getMoney()) + " 元";
            holder.billNumber.setText(temp);
            holder.billNumber.setTextColor(Color.rgb(60,179,113));   //适中的绿色
        }
        holder.billType.setText(example.getSpecial());   //账单类别
        holder.billAccount.setText(example.getAccount());    //账单账户

    }

    @Override
    public int getItemCount() {
        return myBill.size();
    }

}
