package com.sk.jintang.module.my.network.response;

import java.util.List;

/**
 * Created by administartor on 2017/9/21.
 */

public class TuiHuanHuoObj {
    /**
     * order_no : P201709081329270955
     * goods_list : [{"order_no":"P201709081329270955","goods_id":4,"goods_name":"【阿芙】按摩精油通经络全身推油身体肩颈足部开背推拿刮痧推油正品","goods_images":"http://121.40.186.118:5008/upload/goods.png","goods_specification":"蓝色*100ml","goods_specification_id":11,"goods_unitprice":75,"goods_number":1}]
     * goods_list_count : 1
     * combined : 70
     * return_goods_status : 1
     * refund_status : 1
     */

    private String order_no;
    private int goods_list_count;
    private double combined;
    private int return_goods_status;
    private int refund_status;
    private List<GoodsListBean> goods_list;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getGoods_list_count() {
        return goods_list_count;
    }

    public void setGoods_list_count(int goods_list_count) {
        this.goods_list_count = goods_list_count;
    }

    public double getCombined() {
        return combined;
    }

    public void setCombined(double combined) {
        this.combined = combined;
    }

    public int getReturn_goods_status() {
        return return_goods_status;
    }

    public void setReturn_goods_status(int return_goods_status) {
        this.return_goods_status = return_goods_status;
    }

    public int getRefund_status() {
        return refund_status;
    }

    public void setRefund_status(int refund_status) {
        this.refund_status = refund_status;
    }

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }

    public static class GoodsListBean {
        /**
         * order_no : P201709081329270955
         * goods_id : 4
         * goods_name : 【阿芙】按摩精油通经络全身推油身体肩颈足部开背推拿刮痧推油正品
         * goods_images : http://121.40.186.118:5008/upload/goods.png
         * goods_specification : 蓝色*100ml
         * goods_specification_id : 11
         * goods_unitprice : 75
         * goods_number : 1
         */

        private String order_no;
        private int goods_id;
        private String goods_name;
        private String goods_images;
        private String goods_specification;
        private int goods_specification_id;
        private double goods_unitprice;
        private int goods_number;

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public int getGoods_id() {
            return goods_id;
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

        public String getGoods_images() {
            return goods_images;
        }

        public void setGoods_images(String goods_images) {
            this.goods_images = goods_images;
        }

        public String getGoods_specification() {
            return goods_specification;
        }

        public void setGoods_specification(String goods_specification) {
            this.goods_specification = goods_specification;
        }

        public int getGoods_specification_id() {
            return goods_specification_id;
        }

        public void setGoods_specification_id(int goods_specification_id) {
            this.goods_specification_id = goods_specification_id;
        }

        public double getGoods_unitprice() {
            return goods_unitprice;
        }

        public void setGoods_unitprice(double goods_unitprice) {
            this.goods_unitprice = goods_unitprice;
        }

        public int getGoods_number() {
            return goods_number;
        }

        public void setGoods_number(int goods_number) {
            this.goods_number = goods_number;
        }
    }
}
