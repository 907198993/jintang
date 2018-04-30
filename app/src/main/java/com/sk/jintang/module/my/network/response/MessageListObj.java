package com.sk.jintang.module.my.network.response;

import com.sk.jintang.base.BaseObj;

/**
 * Created by administartor on 2017/9/11.
 */

public class MessageListObj extends BaseObj {

    /**
     * id : 4
     * title : 注册成功!
     * zhaiyao : 恭喜，您已成功注册养御商城会员，系统自动赠送您价值200元的优惠券，快去看看吧～
     * add_time : 2017/8/9
     */

    private int id;
    private String title;
    private String zhaiyao;
    private String add_time;
    private int news_is_check;

    public int getNews_is_check() {
        return news_is_check;
    }

    public void setNews_is_check(int news_is_check) {
        this.news_is_check = news_is_check;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getZhaiyao() {
        return zhaiyao;
    }

    public void setZhaiyao(String zhaiyao) {
        this.zhaiyao = zhaiyao;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
