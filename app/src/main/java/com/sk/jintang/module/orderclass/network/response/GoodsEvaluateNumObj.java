package com.sk.jintang.module.orderclass.network.response;

import com.sk.jintang.base.BaseObj;

/**
 * Created by administartor on 2017/9/14.
 */

public class GoodsEvaluateNumObj extends BaseObj {
    /**
     * haopin : 0
     * zhongpin : 0
     * chapin : 2
     */

    private int haopin;
    private int zhongpin;
    private int chapin;

    public int getHaopin() {
        return haopin;
    }

    public void setHaopin(int haopin) {
        this.haopin = haopin;
    }

    public int getZhongpin() {
        return zhongpin;
    }

    public void setZhongpin(int zhongpin) {
        this.zhongpin = zhongpin;
    }

    public int getChapin() {
        return chapin;
    }

    public void setChapin(int chapin) {
        this.chapin = chapin;
    }
}
