package com.sk.jintang.module.my.network.response;

import java.util.List;

/**
 * Created by administartor on 2017/9/22.
 */

public class FaBiaoPingJiaObj {
    /**
     * goods_id : 1
     * content : sample string 2
     * stars_num : 3
     * specifications_id : 4
     * image_list : ["sample string 1","sample string 2"]
     */

    private int goods_id;
    private String content;
    private int stars_num;
    private int specifications_id;
    private String goods_images;
    private List<String>image_list;

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGoods_images() {
        return goods_images;
    }

    public void setGoods_images(String goods_images) {
        this.goods_images = goods_images;
    }

    public int getStars_num() {
        return stars_num;
    }

    public void setStars_num(int stars_num) {
        this.stars_num = stars_num;
    }

    public int getSpecifications_id() {
        return specifications_id;
    }

    public void setSpecifications_id(int specifications_id) {
        this.specifications_id = specifications_id;
    }

    public List<String> getImage_list() {
        return image_list;
    }

    public void setImage_list(List<String> image_list) {
        this.image_list = image_list;
    }
}
