package com.sk.jintang.module.my.network.response;

/**
 * Created by administartor on 2017/9/19.
 */

public class MyCollectObj {
    /**
     * goods_id : 1
     * goods_name : 【阿芙】按摩精油通经络全身推油身体肩颈足部开背推拿刮痧推油正品
     * goods_image : http://121.40.186.118:5008/upload/goods.png
     * price : 72.5
     */

    private int goods_id;
    private String goods_name;
    private String goods_image;
    private double price;
    //code商品类别(0商品不存在 1普通商品 2限时抢购 3团购),staus商品状态(0商品不存在或者活动已结束 1商品存在活动没结束)
    private int code;
    private int staus;

    public int getGoods_id() {
        return goods_id;
    }

    public int getStaus() {
        return staus;
    }

    public void setStaus(int staus) {
        this.staus = staus;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_image() {
        return goods_image;
    }

    public void setGoods_image(String goods_image) {
        this.goods_image = goods_image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
