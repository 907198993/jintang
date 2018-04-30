package com.sk.jintang.module.shoppingcart.network.response;

/**
 * Created by Administrator on 2018/4/5.
 */

public class ShopIntroduceObj {


        /**
         * storeAddress : 成都市高新区天府五街112号
         * storeTel : 13544152452
         * storeUserName : 张三29
         * storeName : 时尚衣帽店
         * storeHeadImg : http://192.168.0.19:20001/upload/201803/27/201803271405588357.jpg
         */

        private String storeAddress;
        private String storeTel;
        private String storeUserName;
        private String storeName;
        private String storeHeadImg;

        public String getStoreAddress() {
            return storeAddress;
        }

        public void setStoreAddress(String storeAddress) {
            this.storeAddress = storeAddress;
        }

        public String getStoreTel() {
            return storeTel;
        }

        public void setStoreTel(String storeTel) {
            this.storeTel = storeTel;
        }

        public String getStoreUserName() {
            return storeUserName;
        }

        public void setStoreUserName(String storeUserName) {
            this.storeUserName = storeUserName;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStoreHeadImg() {
            return storeHeadImg;
        }

        public void setStoreHeadImg(String storeHeadImg) {
            this.storeHeadImg = storeHeadImg;
        }

}
