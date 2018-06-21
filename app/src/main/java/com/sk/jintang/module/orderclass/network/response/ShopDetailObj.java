package com.sk.jintang.module.orderclass.network.response;

import java.util.List;

/**
 * Created by Administrator on 2018/3/27.
 */

public class ShopDetailObj {



        /**
         * storeName : 时尚衣帽店
         * storeAddress : 成都市高新区天府五街112号
         * storeHeadImg : http://192.168.0.19:20001/upload/201803/27/201803271405588357.jpg
         * goodsList : [{"goodsName":"sod蜜润肤乳","goodsPrice":80,"goodsImg":"http://192.168.0.19:20001/upload/201711/06/201711061757512619.jpg","originalprice":4.37},{"goodsName":"12312","goodsPrice":99,"goodsImg":"http://192.168.0.19:20001/upload/201803/27/201803271354088841.jpg","originalprice":2},{"goodsName":"帅气男装TXU","goodsPrice":33,"goodsImg":"http://192.168.0.19:20001/upload/201803/27/201803271408574739.jpg","originalprice":123},{"goodsName":"帅气男装","goodsPrice":99,"goodsImg":"http://192.168.0.19:20001/upload/201803/27/201803271410316733.jpg","originalprice":100},{"goodsName":"SPA醋疗浴","goodsPrice":80,"goodsImg":"http://192.168.0.19:20001/upload/201711/06/201711061800467365.jpg","originalprice":31.25},{"goodsName":"大号温灸棒","goodsPrice":50,"goodsImg":"http://192.168.0.19:20001/upload/201711/06/201711061810372423.jpg","originalprice":70},{"goodsName":"华康八年陈艾条","goodsPrice":50,"goodsImg":"http://192.168.0.19:20001/upload/201711/26/201711261129539675.jpg","originalprice":45},{"goodsName":"华康三年陈艾柱","goodsPrice":50,"goodsImg":"http://192.168.0.19:20001/upload/201711/26/201711261203454547.jpg","originalprice":19.8},{"goodsName":"华康","goodsPrice":0,"goodsImg":"http://192.168.0.19:20001/upload/201712/05/201712050010374360.jpg","originalprice":5},{"goodsName":"华康五年陈艾柱","goodsPrice":0,"goodsImg":"http://192.168.0.19:20001/upload/201712/05/201712051342095483.jpg","originalprice":6},{"goodsName":"漂亮女装","goodsPrice":12,"goodsImg":"http://192.168.0.19:20001/upload/201803/27/201803271413092193.jpg","originalprice":12},{"goodsName":"集幽香 30ml精油","goodsPrice":50,"goodsImg":"http://192.168.0.19:20001/upload/201803/21/201803211859011406.jpg","originalprice":12},{"goodsName":"丑陋男装","goodsPrice":23,"goodsImg":"http://192.168.0.19:20001/upload/201803/27/201803271414237686.jpg","originalprice":22}]
         * slideImgList : [{"Img":"http://192.168.0.19:20001/upload/201803/26/201803261800594345.jpg"},{"Img":"http://192.168.0.19:20001/upload/201803/27/201803271406035550.jpg"},{"Img":"http://192.168.0.19:20001/upload/201803/27/201803271406036700.jpg"},{"Img":"http://192.168.0.19:20001/upload/201803/27/201803271406037880.jpg"},{"Img":"http://192.168.0.19:20001/upload/201803/27/201803271406039000.jpg"},{"Img":"http://192.168.0.19:20001/upload/201803/27/201803271406040160.jpg"},{"Img":"http://192.168.0.19:20001/upload/201803/27/201803271406045790.jpg"},{"Img":"http://192.168.0.19:20001/upload/201803/27/201803271406050781.jpg"}]
         */
        private String HXUserId;
        private String storeName;
        private String storeAddress;
        private String storeHeadImg;
        private List<GoodsListBean> goodsList;
        private List<SlideImgListBean> slideImgList;
        private String isAttention; //关注

    public String getHXUserId() {
        return HXUserId;
    }

    public void setHXUserId(String HXUserId) {
        this.HXUserId = HXUserId;
    }

    public String getIsAttention() {
        return isAttention;
    }

    public void setIsAttention(String isAttention) {
        this.isAttention = isAttention;
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

        public List<GoodsListBean> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<GoodsListBean> goodsList) {
            this.goodsList = goodsList;
        }

        public List<SlideImgListBean> getSlideImgList() {
            return slideImgList;
        }

        public void setSlideImgList(List<SlideImgListBean> slideImgList) {
            this.slideImgList = slideImgList;
        }

        public static class GoodsListBean {
            /**
             * goodsName : sod蜜润肤乳
             * goodsPrice : 80
             * goodsImg : http://192.168.0.19:20001/upload/201711/06/201711061757512619.jpg
             * originalprice : 4.37
             */
            private String  goods_id;
            private String goodsName;
            private int goodsPrice;
            private String goodsImg;
            private double originalprice;

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public int getGoodsPrice() {
                return goodsPrice;
            }

            public void setGoodsPrice(int goodsPrice) {
                this.goodsPrice = goodsPrice;
            }

            public String getGoodsImg() {
                return goodsImg;
            }

            public void setGoodsImg(String goodsImg) {
                this.goodsImg = goodsImg;
            }

            public double getOriginalprice() {
                return originalprice;
            }

            public void setOriginalprice(double originalprice) {
                this.originalprice = originalprice;
            }
        }

        public static class SlideImgListBean {
            /**
             * Img : http://192.168.0.19:20001/upload/201803/26/201803261800594345.jpg
             */

            private String Img;

            public String getImg() {
                return Img;
            }

            public void setImg(String Img) {
                this.Img = Img;
            }
        }

}
