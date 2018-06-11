package com.sk.jintang.module.shoppingcart.network.response;

import com.sk.jintang.base.BaseObj;

import java.util.List;

/**
 * Created by administartor on 2017/9/19.
 */

public class SureOrderObj extends BaseObj {
        /**
         * address_list : [{"id":106,"recipient":"发的","phone":"18683573481","shipping_address":"四川省成都市锦江区","shipping_address_details":"金科中心","quanbu":"四川省成都市锦江区金科中心"}]
         * orderGoods_list : [{"store":{"storeID":3,"storeName":"时尚衣帽店","storeImg":"http://localhost:20001/upload/201803/27/201803271405588357.jpg"},"goodsList":[{"shopping_cart_id":0,"store_id":3,"store_name":"时尚衣帽店","goods_id":412,"goods_name":"帅气男装","goods_image":"http://localhost:20001/upload/201803/27/201803271410316733.jpg","specification":"我是规格","specification_id":0,"stock":99999,"price":98.01,"number":2},{"shopping_cart_id":0,"store_id":3,"store_name":"时尚衣帽店","goods_id":414,"goods_name":"帅气男装TXU","goods_image":"http://localhost:20001/upload/201803/27/201803271408574739.jpg","specification":"我是规格","specification_id":0,"stock":99999,"price":10.89,"number":2},{"shopping_cart_id":0,"store_id":3,"store_name":"时尚衣帽店","goods_id":415,"goods_name":"漂亮女装","goods_image":"http://localhost:20001/upload/201803/27/201803271413092193.jpg","specification":"我是规格","specification_id":0,"stock":99999,"price":1.44,"number":2},{"shopping_cart_id":0,"store_id":3,"store_name":"时尚衣帽店","goods_id":416,"goods_name":"丑陋男装","goods_image":"http://localhost:20001/upload/201803/27/201803271414237686.jpg","specification":"我是规格","specification_id":0,"stock":99999,"price":5.29,"number":2},{"shopping_cart_id":0,"store_id":3,"store_name":"时尚衣帽店","goods_id":417,"goods_name":"ttttt","goods_image":"http://localhost:20001/upload/201803/29/201803291927464575.jpg","specification":"我是规格","specification_id":0,"stock":99999,"price":5.29,"number":2},{"shopping_cart_id":0,"store_id":3,"store_name":"时尚衣帽店","goods_id":418,"goods_name":"12312","goods_image":"http://localhost:20001/upload/201803/29/201803291928354593.jpg","specification":"我是规格","specification_id":0,"stock":99999,"price":4.84,"number":2}]},{"store":{"storeID":5,"storeName":"帅气鞋店","storeImg":"http://localhost:20001/upload/201804/04/201804041821534629.jpg"},"goodsList":[{"shopping_cart_id":0,"store_id":5,"store_name":"帅气鞋店","goods_id":419,"goods_name":"阿迪达斯男鞋","goods_image":"http://localhost:20001/upload/201804/04/201804041919216583.jpg","specification":"我是规格","specification_id":0,"stock":99999,"price":4.84,"number":2},{"shopping_cart_id":0,"store_id":5,"store_name":"帅气鞋店","goods_id":420,"goods_name":"33","goods_image":"http://localhost:20001/upload/201804/04/201804041921000905.jpg","specification":"我是规格","specification_id":0,"stock":99999,"price":0.04,"number":2},{"shopping_cart_id":0,"store_id":5,"store_name":"帅气鞋店","goods_id":421,"goods_name":"123123","goods_image":"http://localhost:20001/upload/201804/04/201804041922146624.jpg","specification":"我是规格","specification_id":0,"stock":99999,"price":0.01,"number":2}]}]
         * subtotal : 261.3
         * gong : 1
         * courier : 0
         * city_list : null
         * youhui_num : 0
         * keeping_bean : 200
         * is_yincang : 0
         * keeping_bean_proportion : 10
         * total_up : 261.3
         */

        private double subtotal;
        private int gong;
        private String courier;
        private Object city_list;
        private int youhui_num;
        private int keeping_bean;
        private int is_yincang;
        private int keeping_bean_proportion;
        private double total_up;
        private List<AddressListBean> address_list;
        private List<OrderGoodsListBean> orderGoods_list;

        public double getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(double subtotal) {
            this.subtotal = subtotal;
        }

        public int getGong() {
            return gong;
        }

        public void setGong(int gong) {
            this.gong = gong;
        }

        public String getCourier() {
            return courier;
        }

        public void setCourier(String courier) {
            this.courier = courier;
        }

        public Object getCity_list() {
            return city_list;
        }

        public void setCity_list(Object city_list) {
            this.city_list = city_list;
        }

        public int getYouhui_num() {
            return youhui_num;
        }

        public void setYouhui_num(int youhui_num) {
            this.youhui_num = youhui_num;
        }

        public int getKeeping_bean() {
            return keeping_bean;
        }

        public void setKeeping_bean(int keeping_bean) {
            this.keeping_bean = keeping_bean;
        }

        public int getIs_yincang() {
            return is_yincang;
        }

        public void setIs_yincang(int is_yincang) {
            this.is_yincang = is_yincang;
        }

        public int getKeeping_bean_proportion() {
            return keeping_bean_proportion;
        }

        public void setKeeping_bean_proportion(int keeping_bean_proportion) {
            this.keeping_bean_proportion = keeping_bean_proportion;
        }

        public double getTotal_up() {
            return total_up;
        }

        public void setTotal_up(double total_up) {
            this.total_up = total_up;
        }

    public List<AddressListBean> getAddress_list() {
        return address_list;
    }

    public void setAddress_list(List<AddressListBean> address_list) {
        this.address_list = address_list;
    }

    public List<OrderGoodsListBean> getOrderGoods_list() {
            return orderGoods_list;
        }

        public void setOrderGoods_list(List<OrderGoodsListBean> orderGoods_list) {
            this.orderGoods_list = orderGoods_list;
        }

        public static class AddressListBean {
            /**
             * id : 106
             * recipient : 发的
             * phone : 18683573481
             * shipping_address : 四川省成都市锦江区
             * shipping_address_details : 金科中心
             * quanbu : 四川省成都市锦江区金科中心
             */

            private int id;
            private String recipient;
            private String phone;
            private String shipping_address;
            private String shipping_address_details;
            private String quanbu;
            private String  coord;

            public String getCoord() {
                return coord;
            }

            public void setCoord(String coord) {
                this.coord = coord;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getRecipient() {
                return recipient;
            }

            public void setRecipient(String recipient) {
                this.recipient = recipient;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getShipping_address() {
                return shipping_address;
            }

            public void setShipping_address(String shipping_address) {
                this.shipping_address = shipping_address;
            }

            public String getShipping_address_details() {
                return shipping_address_details;
            }

            public void setShipping_address_details(String shipping_address_details) {
                this.shipping_address_details = shipping_address_details;
            }

            public String getQuanbu() {
                return quanbu;
            }

            public void setQuanbu(String quanbu) {
                this.quanbu = quanbu;
            }
        }

        public static class OrderGoodsListBean {
            /**
             * store : {"storeID":3,"storeName":"时尚衣帽店","storeImg":"http://localhost:20001/upload/201803/27/201803271405588357.jpg"}
             * goodsList : [{"shopping_cart_id":0,"store_id":3,"store_name":"时尚衣帽店","goods_id":412,"goods_name":"帅气男装","goods_image":"http://localhost:20001/upload/201803/27/201803271410316733.jpg","specification":"我是规格","specification_id":0,"stock":99999,"price":98.01,"number":2},{"shopping_cart_id":0,"store_id":3,"store_name":"时尚衣帽店","goods_id":414,"goods_name":"帅气男装TXU","goods_image":"http://localhost:20001/upload/201803/27/201803271408574739.jpg","specification":"我是规格","specification_id":0,"stock":99999,"price":10.89,"number":2},{"shopping_cart_id":0,"store_id":3,"store_name":"时尚衣帽店","goods_id":415,"goods_name":"漂亮女装","goods_image":"http://localhost:20001/upload/201803/27/201803271413092193.jpg","specification":"我是规格","specification_id":0,"stock":99999,"price":1.44,"number":2},{"shopping_cart_id":0,"store_id":3,"store_name":"时尚衣帽店","goods_id":416,"goods_name":"丑陋男装","goods_image":"http://localhost:20001/upload/201803/27/201803271414237686.jpg","specification":"我是规格","specification_id":0,"stock":99999,"price":5.29,"number":2},{"shopping_cart_id":0,"store_id":3,"store_name":"时尚衣帽店","goods_id":417,"goods_name":"ttttt","goods_image":"http://localhost:20001/upload/201803/29/201803291927464575.jpg","specification":"我是规格","specification_id":0,"stock":99999,"price":5.29,"number":2},{"shopping_cart_id":0,"store_id":3,"store_name":"时尚衣帽店","goods_id":418,"goods_name":"12312","goods_image":"http://localhost:20001/upload/201803/29/201803291928354593.jpg","specification":"我是规格","specification_id":0,"stock":99999,"price":4.84,"number":2}]
             */

            private StoreBean store;
            private List<GoodsListBean> goodsList;
            private double xiaoji;
            private int count;
            private String yunfei;
            private String liuyan;

            public String getLiuyan() {
                return liuyan;
            }

            public void setLiuyan(String liuyan) {
                this.liuyan = liuyan;
            }

            public String getYunfei() {
                return yunfei;
            }

            public void setYunfei(String yunfei) {
                this.yunfei = yunfei;
            }

            public double getXiaoji() {
                return xiaoji;
            }

            public void setXiaoji(double xiaoji) {
                this.xiaoji = xiaoji;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public StoreBean getStore() {
                return store;
            }

            public void setStore(StoreBean store) {
                this.store = store;
            }

            public List<GoodsListBean> getGoodsList() {
                return goodsList;
            }

            public void setGoodsList(List<GoodsListBean> goodsList) {
                this.goodsList = goodsList;
            }

            public static class StoreBean {
                /**
                 * storeID : 3
                 * storeName : 时尚衣帽店
                 * storeImg : http://localhost:20001/upload/201803/27/201803271405588357.jpg
                 */

                private int storeID;
                private String storeName;
                private String storeImg;

                public int getStoreID() {
                    return storeID;
                }

                public void setStoreID(int storeID) {
                    this.storeID = storeID;
                }

                public String getStoreName() {
                    return storeName;
                }

                public void setStoreName(String storeName) {
                    this.storeName = storeName;
                }

                public String getStoreImg() {
                    return storeImg;
                }

                public void setStoreImg(String storeImg) {
                    this.storeImg = storeImg;
                }
            }

            public static class GoodsListBean {
                /**
                 * shopping_cart_id : 0
                 * store_id : 3
                 * store_name : 时尚衣帽店
                 * goods_id : 412
                 * goods_name : 帅气男装
                 * goods_image : http://localhost:20001/upload/201803/27/201803271410316733.jpg
                 * specification : 我是规格
                 * specification_id : 0
                 * stock : 99999
                 * price : 98.01
                 * number : 2
                 */

                private int shopping_cart_id;
                private int store_id;
                private String store_name;
                private int goods_id;
                private String goods_name;
                private String goods_image;
                private String specification;
                private int specification_id;
                private int stock;
                private double price;
                private int number;

                public int getShopping_cart_id() {
                    return shopping_cart_id;
                }

                public void setShopping_cart_id(int shopping_cart_id) {
                    this.shopping_cart_id = shopping_cart_id;
                }

                public int getStore_id() {
                    return store_id;
                }

                public void setStore_id(int store_id) {
                    this.store_id = store_id;
                }

                public String getStore_name() {
                    return store_name;
                }

                public void setStore_name(String store_name) {
                    this.store_name = store_name;
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

                public String getGoods_image() {
                    return goods_image;
                }

                public void setGoods_image(String goods_image) {
                    this.goods_image = goods_image;
                }

                public String getSpecification() {
                    return specification;
                }

                public void setSpecification(String specification) {
                    this.specification = specification;
                }

                public int getSpecification_id() {
                    return specification_id;
                }

                public void setSpecification_id(int specification_id) {
                    this.specification_id = specification_id;
                }

                public int getStock() {
                    return stock;
                }

                public void setStock(int stock) {
                    this.stock = stock;
                }

                public double getPrice() {
                    return price;
                }

                public void setPrice(double price) {
                    this.price = price;
                }

                public int getNumber() {
                    return number;
                }

                public void setNumber(int number) {
                    this.number = number;
                }
            }
        }

}
