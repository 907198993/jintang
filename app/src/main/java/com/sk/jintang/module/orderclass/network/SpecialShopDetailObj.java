package com.sk.jintang.module.orderclass.network;

import java.util.List;

/**
 * Created by Administrator on 2018/4/27.
 */

public class SpecialShopDetailObj {


        /**
         * isFreePs : 0
         * startPs : 50
         * HXUserId : admin
         * storeName : XX运动鞋店
         * storeAddress : 古城街3号
         * storeHeadImg : http://47.104.102.17:8001/upload/201804/09/201804091815226721.jpg
         * slideImgList : []
         * isAttention : 0
         */

        private int isFreePs;
        private int startPs;
        private String HXUserId;
        private String storeName;
        private String storeAddress;
        private String storeHeadImg;
        private String isAttention;
        private List<?> slideImgList;

        public int getIsFreePs() {
            return isFreePs;
        }

        public void setIsFreePs(int isFreePs) {
            this.isFreePs = isFreePs;
        }

        public int getStartPs() {
            return startPs;
        }

        public void setStartPs(int startPs) {
            this.startPs = startPs;
        }

        public String getHXUserId() {
            return HXUserId;
        }

        public void setHXUserId(String HXUserId) {
            this.HXUserId = HXUserId;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStoreAddress() {
            return storeAddress;
        }

        public void setStoreAddress(String storeAddress) {
            this.storeAddress = storeAddress;
        }

        public String getStoreHeadImg() {
            return storeHeadImg;
        }

        public void setStoreHeadImg(String storeHeadImg) {
            this.storeHeadImg = storeHeadImg;
        }

        public String getIsAttention() {
            return isAttention;
        }

        public void setIsAttention(String isAttention) {
            this.isAttention = isAttention;
        }

        public List<?> getSlideImgList() {
            return slideImgList;
        }

        public void setSlideImgList(List<?> slideImgList) {
            this.slideImgList = slideImgList;
        }

}
