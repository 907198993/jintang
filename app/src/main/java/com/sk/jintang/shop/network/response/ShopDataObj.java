package com.sk.jintang.shop.network.response;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;

import com.sk.jintang.shop.utils.ViewUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2018/4/24.
 */

public class ShopDataObj  implements Serializable{


        private int isFreePs;
        private int startPs;
        private List<StoreTypeListBean> storeTypeList;
        private List<GoodsListBean> goodsList;


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

    public List<StoreTypeListBean> getStoreTypeList() {
            return storeTypeList;
        }

        public void setStoreTypeList(List<StoreTypeListBean> storeTypeList) {
            this.storeTypeList = storeTypeList;
        }

        public List<GoodsListBean> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<GoodsListBean> goodsList) {
            this.goodsList = goodsList;
        }

        public static class StoreTypeListBean {
            /**
             * storeId : 6
             * typeName : 畅销鞋
             * id : 38
             * sort : 1
             */

            private int storeId;
            private String typeName;
            private int id;
            private int sort;

            public int getStoreId() {
                return storeId;
            }

            public void setStoreId(int storeId) {
                this.storeId = storeId;
            }

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }
        }

        public static class GoodsListBean implements Serializable {
            /**
             * goods_name : 好看的运动鞋
             * price : 0
             * goods_image : http://192.168.0.108:20001/upload/201804/09/201804091915232961.jpg
             * sales_volume : 0
             * store_typeId : 38
             * typeName : 畅销鞋
             */
            private String goodsId;
            private String goods_name;
            private BigDecimal price;
            private String goods_image;
            private int sales_volume;
            private int store_typeId;
            private String typeName;
            private int selectCount;
            private List<SpecificationListBean> specificationList;

            public List<SpecificationListBean> getSpecificationList() {
                return specificationList;
            }

            public void setSpecificationList(List<SpecificationListBean> specificationList) {
                this.specificationList = specificationList;
            }

            public int getSelectCount() {
                return selectCount;
            }

            public void setSelectCount(int selectCount) {
                this.selectCount = selectCount;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public BigDecimal getPrice() {
                return price;
            }

            public void setPrice(BigDecimal price) {
                this.price = price;
            }
            public SpannableString getStrPrice(Context context) {
                String priceStr = String.valueOf(getPrice());
                SpannableString spanString = new SpannableString("¥" + priceStr);
                AbsoluteSizeSpan span = new AbsoluteSizeSpan(ViewUtils.sp2px(context, 11));
                spanString.setSpan(span, 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                return spanString;
            }

            public SpannableString getStrPrice(Context context, BigDecimal price) {
                String priceStr = String.valueOf(price);
                SpannableString spanString = new SpannableString("¥" + priceStr);
                AbsoluteSizeSpan span = new AbsoluteSizeSpan(ViewUtils.sp2px(context, 11));
                spanString.setSpan(span, 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                return spanString;
            }
            public String getGoods_image() {
                return goods_image;
            }

            public void setGoods_image(String goods_image) {
                this.goods_image = goods_image;
            }

            public int getSales_volume() {
                return sales_volume;
            }

            public void setSales_volume(int sales_volume) {
                this.sales_volume = sales_volume;
            }

            public int getStore_typeId() {
                return store_typeId;
            }

            public void setStore_typeId(int store_typeId) {
                this.store_typeId = store_typeId;
            }

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public String getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(String goodsId) {
                this.goodsId = goodsId;
            }
            public static class SpecificationListBean implements Serializable {
                    /**
                     * id : 2226
                     * goods_id : 422
                     * specification : 1
                     * price : 999
                     * images : http://47.104.102.17:8001/upload/201804/09/201804091915503326.jpg
                     * stock : 86
                     */

                    private int id;
                    private int goods_id;
                    private String specification;
                    private int price;
                    private String images;
                    private int stock;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public int getGoods_id() {
                        return goods_id;
                    }

                    public void setGoods_id(int goods_id) {
                        this.goods_id = goods_id;
                    }

                    public String getSpecification() {
                        return specification;
                    }

                    public void setSpecification(String specification) {
                        this.specification = specification;
                    }

                    public int getPrice() {
                        return price;
                    }

                    public void setPrice(int price) {
                        this.price = price;
                    }

                    public String getImages() {
                        return images;
                    }

                    public void setImages(String images) {
                        this.images = images;
                    }

                    public int getStock() {
                        return stock;
                    }

                    public void setStock(int stock) {
                        this.stock = stock;
                    }
                }

        }
}
