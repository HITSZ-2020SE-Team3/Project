package com.example.myapplication;

//本类为统计、筛选功能的后端代码

import android.database.Cursor;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

public class statistic {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<String> getAllAccount(){
        List<DataBase> result = LitePal.select("account").find(DataBase.class);
        List<String> final_result = new ArrayList<>();
        for(DataBase data:result){
            String temp = data.getAccount();
            if(!final_result.contains(temp))    final_result.add(temp); //不存在的元素才添加，从而达到去重的效果
        }//取出所有账户的字符串
        return final_result; //去重
    }//取出所有的账户（去重后放入一个列表中），前端应显示这些账户的余额

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Integer> getAllDate(int dateType){
        String column;
        switch (dateType){
            //筛选出年份
            case 2: //筛选出月份
                column = "month";
                break;
            default:
                column = "year";
                break;
        }
        List<DataBase> result = LitePal.order(column+" desc").select("?",column).find(DataBase.class);
        //按照时间倒序（新的在前面）筛选数据
        List<Integer> final_result = new ArrayList<>();
        for(DataBase data:result){
            int temp = Integer.parseInt(data.getAccount());
            final_result.add(temp);
        }//取出所有日期的数值
        return (List<Integer>) final_result.stream().distinct();    //去重
    }//取出所有的日期（包括年、月、日，区分它们的不同在于输入的日期种类）
    //似乎不需要这么麻烦吗？不行，至少得筛选出年份

    public static List<DataBase> getOneAccountBill(String account){
        return LitePal.where("account = ?",account).find(DataBase.class);
    }//取出单个账户的所有数据，传入账户名

    public static List<DataBase> getAllBill(){
        return LitePal.order("date desc").find(DataBase.class);
    }//取出所有数据，用于流水展示（格式化要求：按时间降序，新的在上面处理逻辑见其他函数）

    public static List<DataBase> getAllBill(int start, int finish){
        return LitePal.where("date > ? and date < ?",String.valueOf(start - 1),String.valueOf(finish + 1))
                .order("date desc").find(DataBase.class);
    }//取出所有数据，用于流水展示，这是限制时间的版本（格式化要求：按时间降序，新的在上面处理逻辑见其他函数）

    public static List<List<DataBase>> classifyData(List<DataBase> data,List<Integer> time){
        List<List<DataBase>> result = new ArrayList<>(time.size());
        for(DataBase i:data){
            Integer j = i.getYear();
            Integer index = time.indexOf(j);    //当前数据的年份所在位置
            List<DataBase> temp = result.get(index);    //取出对应年份数组，将数据加入进去
            temp.add(i);
        }//遍历每一条账单数据，新建一个对应的数组，存放不同年份/月份的数据
        return result;
    }//按照一年的跨度筛选数据成组（为二维数组，第二维表示每一年对应的所有账单）
    //同理，这个函数也可以向下内推，得到一年中每个月/每天的账单二维数组
    //现在的思路不用这么麻烦，直接总体上进行筛选

    public static List<Float> getRemainInAPeriodOfTime(List<DataBase> data){
        List<Float> balance = new ArrayList<>(2);   //0位装收入，1位装支出
        balance.set(0,(float)0);
        balance.set(1,(float)0);    //初始化赋值为0
        for(DataBase temp:data){
            if(temp.getMoney() > 0) //说明为收入，放在0位累加
                balance.set(0, balance.get(0) + temp.getMoney());
            else balance.set(1, balance.get(1) + temp.getMoney());   //反之，则放在1位（支出）累加
        }
        return balance;
    }//考虑求和计算一段时间的收入、支出值（如单个月，整年）

    public static float getOneAccountRemain(String account){
        List<DataBase> anAccountBill = statistic.getOneAccountBill(account);
        float result = 0;
        for(DataBase temp:anAccountBill){
            result += temp.getMoney();
        }//遍历单个账户的所有账单，取出数据相加
        return result;
    }//求出单个账户的余额值

    public static float getAllAccountRemain(){
        float result = LitePal.sum("database","money",float.class);
        return result;
    }//求出整个账户的余额值

    public static int dateChange(int year, int month, int day){
        int result;
        String tempMonth,tempDay,temp;
        if(month < 10) tempMonth = "0" + String.valueOf(month);//如果月份数小于10，需要补0才能正确存储！日期同理
        else    tempMonth = String.valueOf(month);
        if(day < 10)   tempDay = "0" + String.valueOf(day);
        else    tempDay =String.valueOf(day);
        temp = String.valueOf(year) + tempMonth + tempDay;   //换字符串形式拼接起来
        result = Integer.parseInt(temp);    //再切换成数字形式
        return result;
    }   //更改日期存储格式为一个整数，方便排序

    public static List<DataBase> getBillPerYear(int year){
        List<DataBase> result = LitePal.where("year = ?",String.valueOf(year))
                .order("date desc").find(DataBase.class);    //获取当年的所有数据，并按时间降序排列
        return result;
    }//获取一年的所有流水

    public static List<DataBase> getBillPerMonth(int year, int month){
        List<DataBase> result = LitePal.where("year = ? and month = ?",String.valueOf(year),String.valueOf(month))
                .order("date desc").find(DataBase.class);    //获取当年当月的所有数据，并按时间降序排列
        return result;
    }//获取单个月的所有流水，适于循环调用(直接循环12次就行，不用检测月份多少，节省时间)

    /*public List<DataBase> sortDate(int year){
        Cursor c = LitePal.findBySQL("SELECT * FROM DATABASE WHERE year = :year ORDER BY date DESC");
        //暂时不用
    }//对日期进行排序，然后返回合适的数据*/

}