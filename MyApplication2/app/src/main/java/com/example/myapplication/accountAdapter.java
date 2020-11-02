package com.example.myapplication;
//这是RecyclerView的数据适配器，将数据和展示用的item绑定
//分账户余额的数据适配器
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class accountAdapter extends RecyclerView.Adapter<accountAdapter.ViewHolder> {

    private List<String> allAccount;  //所有账户名
    //private List<Float> allAccountRemain;   //所有分账户的余额，在这一个类中求出

    public accountAdapter(List<String> data){
        allAccount = data;
    }//初始化函数，传入账户名数组即可

    static class ViewHolder extends RecyclerView.ViewHolder{    //指在布局中需要展示的内容
        View accountView;   //为下面的点击事件注册一个组件
        TextView billAccount;   //账单账户
        TextView accountRemain;      //账单类型

        public ViewHolder(View view){
            super(view);
            accountView = view;
            accountRemain = view.findViewById(R.id.account_data);
            billAccount = view.findViewById(R.id.account_name);
        }
    }

    @NonNull
    @Override
    public accountAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.statistic_item,parent,false);
        //以下为注册点击事件多加的代码
        final ViewHolder holder = new accountAdapter.ViewHolder(view);
        holder.accountView.setOnClickListener(new View.OnClickListener() {  //点击各个账户余额栏，应进入分账户统计界面
            @Override
            public void onClick(final View v) {
                //活动跳转到逐年/月查看账单的界面，需要传递账号、年/月选择的数据（由提示窗传递）
                int position = holder.getAdapterPosition(); //取得当前账户的坐标
                final String account = allAccount.get(position);
                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());   //适配器是一个java类而非活动，所以需要传入这个上下文进行跳转控制
                dialog.setTitle("您是否要查看" + account + "账户的逐年/逐月的账单数据？"); //设置标题
                dialog.setMessage("请点击下面的按钮或点击窗口外以退出选择...");  //设置提醒词
                dialog.setPositiveButton("按年查看", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {    //点击后，跳转到按年查看的界面，功能号1
                        //活动跳转到转账界面
                        Intent intent = new Intent(v.getContext(), billClassify.class);
                        intent.putExtra("features",1);  //传输功能号1
                        intent.putExtra("account",account);    //传输账户名
                        v.getContext().startActivity(intent);   //适配器是一个java类而非活动，不能直接用startActivity来搞
                        }
                });
                dialog.setNegativeButton("",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {    //点击后，跳转到按月查看的界面，功能号2
                        Intent intent = new Intent(v.getContext(), billClassify.class);
                        intent.putExtra("features",1);  //传输功能号2
                        intent.putExtra("account",account);    //传输账户名
                        v.getContext().startActivity(intent);   //适配器是一个java类而非活动，不能直接用startActivity来搞
                    }
                });
            };
        });
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull accountAdapter.ViewHolder holder, int position) {
        String example = allAccount.get(position);
        float result = statistic.getOneAccountRemain(example);   //求出这一账户的余额
        holder.billAccount.setText(example);    //账单账户
        holder.accountRemain.setText(String.valueOf(result) + "元");   //账户余额，调用方法统计
        if(result > 0)      holder.accountRemain.setTextColor(Color.rgb(255,69,0));   //余额为正，设置为红色字体
        else    holder.accountRemain.setTextColor(Color.rgb(60,179,113));   //余额为负，显示为绿色
    }

    @Override
    public int getItemCount() {
        return allAccount.size();
    }
}
