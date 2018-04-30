package com.sk.jintang.module.shoppingcart.network.response;

/**
 * Created by Administrator on 2018/4/7.
 */

public class Remark {

    private  int StoreID;
    private  String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStoreID() {
        return StoreID;
    }

    public void setStoreID(int storeID) {
        StoreID = storeID;
    }
}
