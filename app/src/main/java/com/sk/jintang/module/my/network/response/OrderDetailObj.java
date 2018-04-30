package com.sk.jintang.module.my.network.response;

import com.sk.jintang.base.BaseObj;

import java.util.List;

/**
 * Created by administartor on 2017/9/26.
 */

public class OrderDetailObj extends BaseObj {
    /**
     * order_no : P170922141900378302
     * order_status : 4
     * courier_list : [{"time":"2017/9/26 11:15:36","context":"暂无物流信息"}]
     * address_recipient : 666
     * address_phone : 1345687865
     * shipping_address : 上海市长宁区长宁路
     * goods_list : [{"order_no":"P170922141900378302","goods_id":1,"goods_name":"【阿芙】按摩精油通经络全身推油身体肩颈足部开背推拿刮痧推油正品","goods_images":"http://121.40.186.118:5008/upload/goods.png","goods_specification":"红色*100ml","goods_specification_id":3,"goods_unitprice":76,"goods_number":1},{"order_no":"P170922141900378302","goods_id":1,"goods_name":"【阿芙】按摩精油通经络全身推油身体肩颈足部开背推拿刮痧推油正品","goods_images":"http://121.40.186.118:5008/upload/goods.png","goods_specification":"蓝色*100ml","goods_specification_id":2,"goods_unitprice":78,"goods_number":1},{"order_no":"P170922141900378302","goods_id":1,"goods_name":"【阿芙】按摩精油通经络全身推油身体肩颈足部开背推拿刮痧推油正品","goods_images":"http://121.40.186.118:5008/upload/goods.png","goods_specification":"黄色*100ml","goods_specification_id":1,"goods_unitprice":77,"goods_number":1},{"order_no":"P170922141900378302","goods_id":5,"goods_name":"【阿芙】按摩精油通经络全身推油身体肩颈足部开背推拿刮痧推油正品","goods_images":"http://121.40.186.118:5008/upload/goods.png","goods_specification":"红色*100ml","goods_specification_id":15,"goods_unitprice":73,"goods_number":1},{"order_no":"P170922141900378302","goods_id":5,"goods_name":"【阿芙】按摩精油通经络全身推油身体肩颈足部开背推拿刮痧推油正品","goods_images":"http://121.40.186.118:5008/upload/goods.png","goods_specification":"蓝色*100ml","goods_specification_id":14,"goods_unitprice":75,"goods_number":2},{"order_no":"P170922141900378302","goods_id":5,"goods_name":"【阿芙】按摩精油通经络全身推油身体肩颈足部开背推拿刮痧推油正品","goods_images":"http://121.40.186.118:5008/upload/goods.png","goods_specification":"黄色*100ml","goods_specification_id":13,"goods_unitprice":76,"goods_number":2}]
     * goods_total_amount : 455
     * goods_list_count : 6
     * combined : 606
     * freight : 0
     * youhui_money : 0
     * payment_add_time :
     * pay_id : 无
     * courier_name :
     * invoice_type :
     */

    private String order_no;
    private int order_status;
    private String address_recipient;
    private String address_phone;
    private String shipping_address;
    private double goods_total_amount;
    private int goods_list_count;
    private double combined;
    private double freight;
    private double youhui_money;
    private double keeping_bean;
    private String payment_add_time;
    private String create_add_time;
    private String cancel_order_time;
    private String pay_id;
    private String courier_name;
    private String invoice_type;
    private int code;//(1货到付款订单 0普通订单)
    private List<CourierListBean> courier_list;
    private List<GoodsListBean> goods_list;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getCreate_add_time() {
        return create_add_time;
    }

    public void setCreate_add_time(String create_add_time) {
        this.create_add_time = create_add_time;
    }

    public String getCancel_order_time() {
        return cancel_order_time;
    }

    public void setCancel_order_time(String cancel_order_time) {
        this.cancel_order_time = cancel_order_time;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public String getAddress_recipient() {
        return address_recipient;
    }

    public void setAddress_recipient(String address_recipient) {
        this.address_recipient = address_recipient;
    }

    public String getAddress_phone() {
        return address_phone;
    }

    public void setAddress_phone(String address_phone) {
        this.address_phone = address_phone;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public double getGoods_total_amount() {
        return goods_total_amount;
    }

    public void setGoods_total_amount(double goods_total_amount) {
        this.goods_total_amount = goods_total_amount;
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

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public double getYouhui_money() {
        return youhui_money;
    }

    public void setYouhui_money(double youhui_money) {
        this.youhui_money = youhui_money;
    }

    public double getKeeping_bean() {
        return keeping_bean;
    }

    public void setKeeping_bean(double keeping_bean) {
        this.keeping_bean = keeping_bean;
    }

    public String getPayment_add_time() {
        return payment_add_time;
    }

    public void setPayment_add_time(String payment_add_time) {
        this.payment_add_time = payment_add_time;
    }

    public String getPay_id() {
        return pay_id;
    }

    public void setPay_id(String pay_id) {
        this.pay_id = pay_id;
    }

    public String getCourier_name() {
        return courier_name;
    }

    public void setCourier_name(String courier_name) {
        this.courier_name = courier_name;
    }

    public String getInvoice_type() {
        return invoice_type;
    }

    public void setInvoice_type(String invoice_type) {
        this.invoice_type = invoice_type;
    }

    public List<CourierListBean> getCourier_list() {
        return courier_list;
    }

    public void setCourier_list(List<CourierListBean> courier_list) {
        this.courier_list = courier_list;
    }

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }

    public static class CourierListBean {
        /**
         * time : 2017/9/26 11:15:36
         * context : 暂无物流信息
         */

        private String time;
        private String context;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }
    }

    public static class GoodsListBean {
        /**
         * order_no : P170922141900378302
         * goods_id : 1
         * goods_name : 【阿芙】按摩精油通经络全身推油身体肩颈足部开背推拿刮痧推油正品
         * goods_images : http://121.40.186.118:5008/upload/goods.png
         * goods_specification : 红色*100ml
         * goods_specification_id : 3
         * goods_unitprice : 76
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
        private int code;//0商品不存在 1普通商品 2限时抢购 3团购
        private int staus;//0商品不存在或者活动已结束 1商品存在活动没结束

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public int getStaus() {
            return staus;
        }

        public void setStaus(int staus) {
            this.staus = staus;
        }

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
