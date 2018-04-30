package com.sk.jintang.module.my.network.response;

import com.sk.jintang.base.BaseObj;

/**
 * Created by administartor on 2017/9/11.
 */

public class VouchersNumObj extends BaseObj{

    /**
     * count_wsy : 5
     * count_ysy : 2
     * count_ygq : 2
     */

    private int count_wsy;
    private int count_ysy;
    private int count_ygq;

    public int getCount_wsy() {
        return count_wsy;
    }

    public void setCount_wsy(int count_wsy) {
        this.count_wsy = count_wsy;
    }

    public int getCount_ysy() {
        return count_ysy;
    }

    public void setCount_ysy(int count_ysy) {
        this.count_ysy = count_ysy;
    }

    public int getCount_ygq() {
        return count_ygq;
    }

    public void setCount_ygq(int count_ygq) {
        this.count_ygq = count_ygq;
    }
}
