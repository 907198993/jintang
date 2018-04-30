package com.sk.jintang.module.orderclass.network.response;

import com.sk.jintang.base.BaseObj;

/**
 * Created by administartor on 2017/9/19.
 */

public class CommitGoodsObj extends BaseObj {
    /**
     * order_no : P170919145727757585
     * combined : 77.0
     */

    private String order_no;
    private double combined;
    private int is_huodao;//是否要货到付款(1是 0否)

    public int getIs_huodao() {
        return is_huodao;
    }

    public void setIs_huodao(int is_huodao) {
        this.is_huodao = is_huodao;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public double getCombined() {
        return combined;
    }

    public void setCombined(double combined) {
        this.combined = combined;
    }
}
