package com.example.myapplication;
//这是RecyclerView的数据适配器，将数据和展示用的item绑定
//分账户余额的数据适配器
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class accountAdapter extends RecyclerView.Adapter<accountAdapter.ViewHolder> {

    private List<String> allAccount;  //所有账户名
    //private List<Float> allAccountRemain;   //所有分账户的余额，在这一个类中求出

    public accountAdapter(List<String> data){
        allAccount = data;
    }//初始化函数，传入账户名数组即可

    static class ViewHolder extends RecyclerView.ViewHolder{    //指在布局中需要展示的内容
        TextView billAccount;   //账单账户
        TextView accountRemain;      //账单类型

        public ViewHolder(View view){
            super(view);
            accountRemain = view.findViewById(R.id.account_data);
            billAccount = view.findViewById(R.id.account_name);
        }
    }

    @NonNull
    @Override
    public accountAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.statistic_item,parent,false);
        return new accountAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull accountAdapter.ViewHolder holder, int position) {
        String example = allAccount.get(position);
        float result = statistic.getOneAccountRemain(example);   //求出这一账户的余额
        holder.billAccount.setText(example);    //账单账户
        holder.accountRemain.setText(String.valueOf(result) + "元");   //账户余额，调用方法统计
        if(result > 0)      holder.accountRemain.setTextColor(Color.rgb(255,69,0));   //余额为正，设置为红色字体
        else    holder.accountRemain.setTextColor(Color.rgb(255,69,0));   //余额为负，显示为绿色
    }

    @Override
    public int getItemCount() {
        return allAccount.size();
    }
}
