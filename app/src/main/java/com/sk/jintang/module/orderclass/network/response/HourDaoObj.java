package com.sk.jintang.module.orderclass.network.response;

import com.sk.jintang.base.BaseObj;

import java.util.List;

/**
 * Created by administartor on 2017/10/10.
 */

public class HourDaoObj extends BaseObj {
    /**
     * delivery_station_name : 上海配送站
     * delivery_station_price : 88
     * list : [{"goods_id":9,"goods_image":"http://121.40.186.118:5008/upload/goods.png","goods_name":"【阿芙】按摩精油通经络全身推油身体肩颈足部开背推拿刮痧推油正品","addresss":"深圳","price":72.5,"sales_volume":8332},{"goods_id":8,"goods_image":"http://121.40.186.118:5008/upload/goods.png","goods_name":"【阿芙】按摩精油通经络全身推油身体肩颈足部开背推拿刮痧推油正品","addresss":"深圳","price":72.5,"sales_volume":8332}]
     */

    private String delivery_station_name;
    private String delivery_notice_top;
    private String delivery_notice_down;
    private double delivery_station_price;
    private List<ListBean> list;

    public String getDelivery_station_name() {
        return delivery_station_name;
    }

    public void setDelivery_station_name(String delivery_station_name) {
        this.delivery_station_name = delivery_station_name;
    }

    public double getDelivery_station_price() {
        return delivery_station_price;
    }

    public void setDelivery_station_price(double delivery_station_price) {
        this.delivery_station_price = delivery_station_price;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public String getDelivery_notice_top() {
        return delivery_notice_top;
    }

    public void setDelivery_notice_top(String delivery_notice_top) {
        this.delivery_notice_top = delivery_notice_top;
    }

    public String getDelivery_notice_down() {
        return delivery_notice_down;
    }

    public void setDelivery_notice_down(String delivery_notice_down) {
        this.delivery_notice_down = delivery_notice_down;
    }

    public static class ListBean {
        /**
         * goods_id : 9
         * goods_image : http://121.40.186.118:5008/upload/goods.png
         * goods_name : 【阿芙】按摩精油通经络全身推油身体肩颈足部开背推拿刮痧推油正品
         * addresss : 深圳
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
