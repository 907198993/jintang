package com.sk.jintang.module.my.network.response;

import com.sk.jintang.base.BaseObj;

/**
 * Created by Administrator on 2017/11/16.
 */

public class OrderNumObj extends BaseObj {
    /**
     * count : 35
     * dfk_count : 15
     * dfh_count : 15
     * dsh_count : 1
     * dpj_count : 1
     */

    private int count;
    private int dfk_count;
    private int dfh_count;
    private int dsh_count;
    private int dpj_count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getDfk_count() {
        return dfk_count;
    }

    public void setDfk_count(int dfk_count) {
        this.dfk_count = dfk_count;
    }

    public int getDfh_count() {
        return dfh_count;
    }

    public void setDfh_count(int dfh_count) {
        this.dfh_count = dfh_count;
    }

    public int getDsh_count() {
        return dsh_count;
    }

    public void setDsh_count(int dsh_count) {
        this.dsh_count = dsh_count;
    }

    public int getDpj_count() {
        return dpj_count;
    }

    public void setDpj_count(int dpj_count) {
        this.dpj_count = dpj_count;
    }
}
