package com.sk.jintang.module.orderclass.network.response;

import com.sk.jintang.base.BaseObj;

import java.io.Serializable;
import java.util.List;

/**
 * Created by administartor on 2017/9/13.
 */

public class GoodsDetailObj extends BaseObj {
    /**
     * img_list : [{"goods_image":"http://121.40.186.118:5008/upload/goods.png"},{"goods_image":"http://121.40.186.118:5008/upload/goods.png"},{"goods_image":"http://121.40.186.118:5008/upload/goods.png"}]
     * goods_id : 1
     * goods_name : 【阿芙】按摩精油通经络全身推油身体肩颈足部开背推拿刮痧推油正品
     * goods_image : http://121.40.186.118:5008/upload/goods.png
     * price : 72.5
     * courier : 0
     * sales_volume : 8332
     * addresss : 上海
     * keeping_bean : 72
     * stock : 600
     * pingjia_num : 2
     * pingjia_list : [{"photo":"http://121.40.186.118:5008/upload/201709/12/17091211231873585059.jpg","nickname":"哟嚯","content":"\t商品很好","add_time":"2017/9/1"},{"photo":"http://121.40.186.118:5008/upload/201709/12/17091211231873585059.jpg","nickname":"哟嚯","content":"\t商品很好","add_time":"2017/8/25"}]
     * goods_details : 详情没有
     * tuijian_list : [{"goods_id":5,"goods_image":"http://121.40.186.118:5008/upload/goods.png","goods_name":"【阿芙】按摩精油通经络全身推油身体肩颈足部开背推拿刮痧推油正品","addresss":"上海","price":72.5,"sales_volume":8332},{"goods_id":1,"goods_image":"http://121.40.186.118:5008/upload/goods.png","goods_name":"【阿芙】按摩精油通经络全身推油身体肩颈足部开背推拿刮痧推油正品","addresss":"上海","price":72.5,"sales_volume":8332}]
     * is_collect : 0
     */

    private String HxUserId;
    private int store_id;
    private int goods_id;
    private String goods_name;
    private String goods_image;
    private double price;
    private double max_price;
    private double original_price;
    private double courier;
    private int sales_volume;
    private String addresss;
    private int keeping_bean;
    private int stock;
    private int pingjia_num;
    private List<String> goods_details;
    private int is_collect;
    private List<ImgListBean> img_list;
    private List<PingjiaListBean> pingjia_list;
    private List<TuijianListBean> tuijian_list;

    public String getHxUserId() {
        return HxUserId;
    }

    public void setHxUserId(String hxUserId) {
        HxUserId = hxUserId;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public double getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(double original_price) {
        this.original_price = original_price;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public double getMax_price() {
        return max_price;
    }

    public void setMax_price(double max_price) {
        this.max_price = max_price;
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

    public double getCourier() {
        return courier;
    }

    public void setCourier(double courier) {
        this.courier = courier;
    }

    public int getSales_volume() {
        return sales_volume;
    }

    public void setSales_volume(int sales_volume) {
        this.sales_volume = sales_volume;
    }

    public String getAddresss() {
        return addresss;
    }

    public void setAddresss(String addresss) {
        this.addresss = addresss;
    }

    public int getKeeping_bean() {
        return keeping_bean;
    }

    public void setKeeping_bean(int keeping_bean) {
        this.keeping_bean = keeping_bean;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
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

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
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

    public List<TuijianListBean> getTuijian_list() {
        return tuijian_list;
    }

    public void setTuijian_list(List<TuijianListBean> tuijian_list) {
        this.tuijian_list = tuijian_list;
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
         * photo : http://121.40.186.118:5008/upload/201709/12/17091211231873585059.jpg
         * nickname : 哟嚯
         * content : 	商品很好
         * add_time : 2017/9/1
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

    public static class TuijianListBean implements Serializable{
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
}
