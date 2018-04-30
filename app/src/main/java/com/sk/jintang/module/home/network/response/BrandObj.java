package com.sk.jintang.module.home.network.response;

/**
 * Created by administartor on 2017/9/13.
 */

public class BrandObj {
    /**
     * brand_id : 6
     * brand_name : 腾讯
     * brand_img : http://121.40.186.118:5008/upload/2.jpg
     */

    private int brand_id;
    private String brand_name;
    private String brand_img;

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getBrand_img() {
        return brand_img;
    }

    public void setBrand_img(String brand_img) {
        this.brand_img = brand_img;
    }
}
