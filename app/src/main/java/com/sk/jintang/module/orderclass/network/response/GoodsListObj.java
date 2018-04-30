package com.sk.jintang.module.orderclass.network.response;

/**
 * Created by administartor on 2017/8/19.
 */

public class GoodsListObj {


    /**
     * goods_id : 5
     * goods_image : http://121.40.186.118:5008/upload/goods.png
     * goods_name : 【阿芙】按摩精油通经络全身推油身体肩颈足部开背推拿刮痧推油正品
     * addresss : 上海
     * price : 72.5
     * sales_volume : 8332
     */

    private int goods_id;
    private String goods_image;
    private String goods_name;
    private String addresss;
    private double price;
    private int sales_volume;
    private int baoyou;//是否包邮(1是 0否)
    private double original_price;
    private String add_time;
    private int sort;

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public double getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(double original_price) {
        this.original_price = original_price;
    }

    public int getBaoyou() {
        return baoyou;
    }

    public void setBaoyou(int baoyou) {
        this.baoyou = baoyou;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_image() {
        return goods_image;
    }

    public void setGoods_image(String goods_image) {
        this.goods_image = goods_image;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getAddresss() {
        return addresss;
    }

    public void setAddresss(String addresss) {
        this.addresss = addresss;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSales_volume() {
        return sales_volume;
    }

    public void setSales_volume(int sales_volume) {
        this.sales_volume = sales_volume;
    }
}
