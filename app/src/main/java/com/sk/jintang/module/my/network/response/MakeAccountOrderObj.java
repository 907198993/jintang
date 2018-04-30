package com.sk.jintang.module.my.network.response;

import com.sk.jintang.base.BaseObj;

/**
 * Created by administartor on 2017/10/12.
 */

public class MakeAccountOrderObj extends BaseObj {
    /**
     * order_no : C201710121441222800
     * money : 1
     */

    private String order_no;
    private double money;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
