package com.sk.jintang.module.my.network.response;

/**
 * Created by Administrator on 2018/4/9.
 */

public class StoreCollectObj {

        /**
         * storeID : 3
         * storeName : 时尚衣帽店
         * storeImg : http://localhost:8022/upload/201803/27/201803271405588357.jpg
         * userId : 2
         */

        private int storeID;
        private String storeName;
        private String storeImg;
        private int userId;
        private int storeType;//1 为特殊通道

    public int getStoreType() {
        return storeType;
    }

    public void setStoreType(int storeType) {
        this.storeType = storeType;
    }

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

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

}
