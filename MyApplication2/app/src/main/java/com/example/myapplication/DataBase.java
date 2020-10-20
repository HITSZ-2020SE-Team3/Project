package com.example.myapplication;

import android.widget.Spinner;

import org.litepal.crud.LitePalSupport;

public class DataBase extends LitePalSupport {
    private int money;
    private String sprcial;
    private String account;
    private String people;
    private String seller;
    private String remarks;
    private int year;
    private int month;
    private int day;

    public DataBase(int money, String sprcial, String account, String people, String seller, String remarks, int year, int month, int day) {
        this.money = money;
        this.sprcial = sprcial;
        this.account = account;
        this.people = people;
        this.seller = seller;
        this.remarks = remarks;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getMoney() {
        return money;
    }

    public String getSprcial() {
        return sprcial;
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

    public void setSprcial(String sprcial) {
        this.sprcial = sprcial;
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
