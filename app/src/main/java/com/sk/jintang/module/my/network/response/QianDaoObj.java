package com.sk.jintang.module.my.network.response;

import com.sk.jintang.base.BaseObj;

import java.util.List;

/**
 * Created by administartor on 2017/9/14.
 */

public class QianDaoObj extends BaseObj {
    /**
     * userid : 1
     * keeping_bean : 200
     * year : 2017
     * month : 9
     * list : [5,14]
     */
    private int userid;
    private int keeping_bean;
    private int year;
    private int month;
    private List<Integer> list;
    private String keeping_bean_rule;

    public String getKeeping_bean_rule() {
        return keeping_bean_rule;
    }

    public void setKeeping_bean_rule(String keeping_bean_rule) {
        this.keeping_bean_rule = keeping_bean_rule;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getKeeping_bean() {
        return keeping_bean;
    }

    public void setKeeping_bean(int keeping_bean) {
        this.keeping_bean = keeping_bean;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }
}
