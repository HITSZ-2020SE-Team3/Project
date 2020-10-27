package com.example.myapplication;

import android.widget.Spinner;

import org.litepal.crud.LitePalSupport;

public class DataBase extends LitePalSupport {
    private float money;      //账单数额，改为float浮点数
    private String special; //分类
    private String account; //账户（指各个银行卡等）
    private String people;  //账单发起人
    private String seller;  //商家名
    private String remarks; //备注
    private int year;   //年
    private int month;  //月
    private int day;    //日

    public DataBase(float money, String special, String account, String people, String seller, String remarks, int year, int month, int day) {
        this.money = money;
        this.special = special;
        this.account = account;
        this.people = people;
        this.seller = seller;
        this.remarks = remarks;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public float getMoney() {
        return money;
    }

    public String getSpecial() {
        return special;
    }

    public String getAccount() {
        return account;
    }

    public String getPeople() {
        return people;
    }

    public String getSeller() {
        return seller;
    }

    public String getRemarks() {
        return remarks;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
