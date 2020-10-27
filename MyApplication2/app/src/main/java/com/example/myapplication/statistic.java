package com.example.myapplication;

//本类为统计、筛选功能的后端代码

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class statistic {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<String> getAllInfo(String dateType){
        String column;
        switch (dateType){
            case "special": //筛选出分类（只有1级，2级做不出来……）
                column = "year";
                break;
            case "account": //筛选出账户
                column = "month";
                break;
            default:
                column = "year";    //（这里可能不需要swtich，睡起来有空再看看改改）
                break;
        }
        List<DataBase> result = LitePal.select(column).find(DataBase.class);
        List<String> final_result = new ArrayList<>();
        for(DataBase data:result){
            String temp = data.getAccount();
            final_result.add(temp);
        }//取出所有账户的字符串
        return (List<String>) final_result.stream().distinct();
    }//取出所有的账户，前端应显示下拉栏展示这些账户(也可以设置成传入string的形式，方便筛选其他条件)

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Integer> getAllDate(int dateType){
        String column;
        switch (dateType){
            case 1: //筛选出年份
                column = "year";
                break;
            case 2: //筛选出月份
                column = "month";
                break;
            default:
                column = "year";
                break;
        }
        List<DataBase> result = LitePal.select(column).find(DataBase.class);
        List<Integer> final_result = new ArrayList<>();
        for(DataBase data:result){
            int temp = Integer.parseInt(data.getAccount());
            final_result.add(temp);
        }//取出所有日期的数值
        return (List<Integer>) final_result.stream().distinct();
    }//取出所有的日期（包括年、月、日，区分它们的不同在于输入的日期种类）

    public List<DataBase> getOneAccount(String account){
        List<DataBase> result = LitePal.select("?",account).find(DataBase.class);
        return result;
    }//取出单个账户的所有数据，传入账户名

    public List<DataBase> getAllData(String account){
        return LitePal.findAll(DataBase.class);
    }//取出所有数据，用于流水展示

    public List<List<DataBase>> classifyDataByYear(List<DataBase> data){
        List<List<DataBase>> result = new ArrayList<>();
        return result;
    }//按照一年的跨度筛选数据成组(待完善，下面再补充一个按月的，逐层调用时间跨度更大的分类函数进行再分组)

    public float getBalance(List<DataBase> data){
        float balance = 0;
        for(DataBase temp:data){
            balance += temp.getMoney();
        }
        return balance;
    }//考虑求和计算余额值（对于一组账单数据进行求和）

    //补充：应该需要分开统计收入/支出？用于在每一年的下面显示（后面图表中的分成员也会用到）

}