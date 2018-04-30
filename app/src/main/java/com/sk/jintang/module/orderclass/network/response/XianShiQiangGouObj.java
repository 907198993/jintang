package com.sk.jintang.module.orderclass.network.response;

/**
 * Created by administartor on 2017/9/26.
 */

public class XianShiQiangGouObj  {
    /**
     * goods_id : 4
     * goods_image : http://121.40.186.118:5008/upload/goods.png
     * goods_name : 【阿芙】按摩精油通经络全身推油身体肩颈足部开背推拿刮痧推油正品
     * price : 72.5
     * flash_price : 12.5
     * has_been_robbed : 19
     * surplus : 200
     */
    private int goods_id;
    private String goods_image;
    private String goods_name;
    private double price;
    private double flash_price;
    private int has_been_robbed;
    private int surplus;
    private int is_tixing;//0没提醒，1已提醒

    public int getIs_tixing() {
        return is_tixing;
    }

    public void setIs_tixing(int is_tixing) {
        this.is_tixing = is_tixing;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getFlash_price() {
        return flash_price;
    }

    public void setFlash_price(double flash_price) {
        this.flash_price = flash_price;
    }

    public int getHas_been_robbed() {
        return has_been_robbed;
    }

    public void setHas_been_robbed(int has_been_robbed) {
        this.has_been_robbed = has_been_robbed;
    }

    public int getSurplus() {
        return surplus;
    }

    public void setSurplus(int surplus) {
        this.surplus = surplus;
    }
}
