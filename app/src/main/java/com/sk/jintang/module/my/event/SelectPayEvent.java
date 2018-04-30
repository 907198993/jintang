package com.sk.jintang.module.my.event;

/**
 * Created by administartor on 2017/10/11.
 */

public class SelectPayEvent {
    public String orderNo;
    public double price;

    public SelectPayEvent(String orderNo, double price) {
        this.orderNo = orderNo;
        this.price = price;
    }
}
