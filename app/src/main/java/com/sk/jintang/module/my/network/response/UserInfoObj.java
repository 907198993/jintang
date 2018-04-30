package com.sk.jintang.module.my.network.response;

import com.sk.jintang.base.BaseObj;

/**
 * Created by administartor on 2017/9/27.
 */

public class UserInfoObj extends BaseObj {
    /**
     * user_id : 1
     * user_name : 15738504592
     * nick_name : 哟嚯
     * avatar : http://121.40.186.118:5008/upload/201709/20/170920114844884558.jpg
     * sex : 男
     * mobile : 15738504592
     * birthday : 2005/9/11
     * amount : 54512
     * count_wsy : 6
     * keeping_bean : 7010
     */

    private int user_id;
    private String user_name;
    private String nick_name;
    private String avatar;
    private String sex;
    private String mobile;
    private String birthday;
    private double amount;
    private int count_wsy;
    private int keeping_bean;
    private int news_is_check;

    public int getNews_is_check() {
        return news_is_check;
    }

    public void setNews_is_check(int news_is_check) {
        this.news_is_check = news_is_check;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getCount_wsy() {
        return count_wsy;
    }

    public void setCount_wsy(int count_wsy) {
        this.count_wsy = count_wsy;
    }

    public int getKeeping_bean() {
        return keeping_bean;
    }

    public void setKeeping_bean(int keeping_bean) {
        this.keeping_bean = keeping_bean;
    }
}
