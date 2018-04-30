package com.sk.jintang.module.orderclass.network.response;

import com.sk.jintang.base.BaseObj;

import java.io.Serializable;
import java.util.List;

/**
 * Created by administartor on 2017/10/10.
 */

public class TuanGouDetailObj extends BaseObj {
    /**
     * img_list : [{"goods_image":"http://121.40.186.118:5008/upload/goods.png"},{"goods_image":"http://121.40.186.118:5008/upload/goods.png"},{"goods_image":"http://121.40.186.118:5008/upload/goods.png"}]
     * end_time : 1507989600
     * goods_id : 2
     * specification_id : 4
     * goods_name : 【阿芙】按摩精油通经络全身推油身体肩颈足部开背推拿刮痧推油正品
     * price : 72.5
     * group_price : 12.5
     * group_num_people : 10
     * number_tuxedo : 2
     * courier : 0
     * addresss : 广州
     * pingjia_num : 1
     * pingjia_list : [{"photo":"http://121.40.186.118:5008/upload/201709/20/170920114844884558.jpg","nickname":"哟嚯","content":"Fiufiuuigiugu","add_time":"2017/9/26"}]
     * goods_details : 详情没有
     */

    private long end_time;
    private int num=1000;
    private int goods_id;
    private int specification_id;
    private String goods_name;
    private double price;
    private double group_price;
    private int group_num_people;
    private int number_tuxedo;
    private double courier;
    private String addresss;
    private int pingjia_num;
    private List<String> goods_details;
    private List<ImgListBean> img_list;
    private List<PingjiaListBean> pingjia_list;

    public long getEnd_time() {
        return end_time*num;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getSpecification_id() {
        return specification_id;
    }

    public void setSpecification_id(int specification_id) {
        this.specification_id = specification_id;
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

    public double getCourier() {
        return courier;
    }

    public void setCourier(double courier) {
        this.courier = courier;
    }

    public String getAddresss() {
        return addresss;
    }

    public void setAddresss(String addresss) {
        this.addresss = addresss;
    }

    public int getPingjia_num() {
        return pingjia_num;
    }

    public void setPingjia_num(int pingjia_num) {
        this.pingjia_num = pingjia_num;
    }

    public List<String> getGoods_details() {
        return goods_details;
    }

    public void setGoods_details(List<String> goods_details) {
        this.goods_details = goods_details;
    }

    public List<ImgListBean> getImg_list() {
        return img_list;
    }

    public void setImg_list(List<ImgListBean> img_list) {
        this.img_list = img_list;
    }

    public List<PingjiaListBean> getPingjia_list() {
        return pingjia_list;
    }

    public void setPingjia_list(List<PingjiaListBean> pingjia_list) {
        this.pingjia_list = pingjia_list;
    }

    public static class ImgListBean implements Serializable {
        /**
         * goods_image : http://121.40.186.118:5008/upload/goods.png
         */

        private String goods_image;

        public String getGoods_image() {
            return goods_image;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image;
        }
    }

    public static class PingjiaListBean implements Serializable{
        /**
         * photo : http://121.40.186.118:5008/upload/201709/20/170920114844884558.jpg
         * nickname : 哟嚯
         * content : Fiufiuuigiugu
         * add_time : 2017/9/26
         */

        private String photo;
        private String nickname;
        private String content;
        private String add_time;

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }
    }
}
