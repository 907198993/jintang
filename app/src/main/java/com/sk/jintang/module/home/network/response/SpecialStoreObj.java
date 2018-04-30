package com.sk.jintang.module.home.network.response;

/**
 * Created by Administrator on 2018/4/24.
 */

public class SpecialStoreObj  {
    private  String storeName;
    private  int  storeID;
    private  String storeImg;
    private  int isFreePs;//是否免配送费（0：否，1：是）
    private  int startPs;//起送价格

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public String getStoreImg() {
        return storeImg;
    }

    public void setStoreImg(String storeImg) {
        this.storeImg = storeImg;
    }

    public int getIsFreePs() {
        return isFreePs;
    }

    public void setIsFreePs(int isFreePs) {
        this.isFreePs = isFreePs;
    }

    public int getStartPs() {
        return startPs;
    }

    public void setStartPs(int startPs) {
        this.startPs = startPs;
    }
}
