package com.sk.jintang.module.orderclass.network.response;

/**
 * Created by administartor on 2017/10/10.
 */

public class TuanGouObj {
    /**
     * goods_id : 2
     * goods_image : http://121.40.186.118:5008/upload/goods.png
     * goods_name : 【阿芙】按摩精油通经络全身推油身体肩颈足部开背推拿刮痧推油正品
     * price : 72.5
     * group_price : 12.5
     * group_num_people : 10
     * number_tuxedo : 2
     * end_time : 2017-10-14 22:00:00
     */

    private int goods_id;
    private String goods_image;
    private String goods_name;
    private double price;
    private double group_price;
    private int group_num_people;
    private int number_tuxedo;
    private String end_time;

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getGroup_price() {
        return group_price;
    }

    public void setGroup_price(double group_price) {
        this.group_price = group_price;
    }

    public int getGroup_num_people() {
        return group_num_people;
    }

    public void setGroup_num_people(int group_num_people) {
        this.group_num_people = group_num_people;
    }

    public int getNumber_tuxedo() {
        return number_tuxedo;
    }

    public void setNumber_tuxedo(int number_tuxedo) {
        this.number_tuxedo = number_tuxedo;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
}
