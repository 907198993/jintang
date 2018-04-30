package com.sk.jintang.module.my.network.response;

import com.sk.jintang.base.BaseObj;

/**
 * Created by administartor on 2017/9/21.
 */

public class FenXiaoObj extends BaseObj {
    /**
     * user_id : 1
     * lower_level : 2
     * commission : 800.0
     */

    private int user_id;
    private int lower_level;
    private double commission;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getLower_level() {
        return lower_level;
    }

    public void setLower_level(int lower_level) {
        this.lower_level = lower_level;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }
}
