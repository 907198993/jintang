package com.sk.jintang.module.home.network.response;

import com.sk.jintang.base.BaseObj;

import java.util.List;

/**
 * Created by administartor on 2017/9/12.
 */

public class HomeButtomObj extends BaseObj {
    /*goods_type_list 商品类别[goods_type_id 类别ID,goods_type_name 标题,goods_type_img 图片路径],
    factory_list 工厂直达[factory_id 工厂ID,factory_name 名称],
    brand_list 品牌[brand_id 品牌ID,brand_img 图片],
    goods_list 推荐商品[goods_id 商品ID,goods_image 列表图片,goods_name 名称,addresss 地点,price 价格,sales_volume 销量]*/
    private List<GoodsTypeListBean> goods_type_list;
    private List<FactoryListBean> factory_list;
    private List<BrandListBean> brand_list;
    private List<ShopListBean> shop_List;


    public List<ShopListBean> getShop_List() {
        return shop_List;
    }

    public void setShop_List(List<ShopListBean> shop_List) {
        this.shop_List = shop_List;
    }

    public List<GoodsTypeListBean> getGoods_type_list() {
        return goods_type_list;
    }

    public void setGoods_type_list(List<GoodsTypeListBean> goods_type_list) {
        this.goods_type_list = goods_type_list;
    }

    public List<FactoryListBean> getFactory_list() {
        return factory_list;
    }

    public void setFactory_list(List<FactoryListBean> factory_list) {
        this.factory_list = factory_list;
    }

    public List<BrandListBean> getBrand_list() {
        return brand_list;
    }

    public void setBrand_list(List<BrandListBean> brand_list) {
        this.brand_list = brand_list;
    }


    public static class  ShopListBean{
        private String shop_type_id;
        private String shop_type_name;
        private String shop_type_img;

        public String getShop_type_id() {
            return shop_type_id;
        }

        public void setShop_type_id(String shop_type_id) {
            this.shop_type_id = shop_type_id;
        }

        public String getShop_type_name() {
            return shop_type_name;
        }

        public void setShop_type_name(String shop_type_name) {
            this.shop_type_name = shop_type_name;
        }

        public String getShop_type_img() {
            return shop_type_img;
        }

        public void setShop_type_img(String shop_type_img) {
            this.shop_type_img = shop_type_img;
        }
    }
    public static class GoodsTypeListBean {
        /**
         * goods_type_id : 8
         * goods_type_name : 按摩
         * goods_type_img : http://121.40.186.118:5008/upload/2.jpg
         */

        private String goods_type_id;
        private String goods_type_name;
        private String goods_type_img;

        public String getGoods_type_id() {
            return goods_type_id;
        }

        public void setGoods_type_id(String goods_type_id) {
            this.goods_type_id = goods_type_id;
        }

        public String getGoods_type_name() {
            return goods_type_name;
        }

        public void setGoods_type_name(String goods_type_name) {
            this.goods_type_name = goods_type_name;
        }

        public String getGoods_type_img() {
            return goods_type_img;
        }

        public void setGoods_type_img(String goods_type_img) {
            this.goods_type_img = goods_type_img;
        }
    }

    public static class FactoryListBean {
        /**
         * factory_id : 6
         * factory_name : 富士康厂
         */

        private int factory_id;
        private String factory_name;
        private String factory_image;

        public String getFactory_image() {
            return factory_image;
        }

        public void setFactory_image(String factory_image) {
            this.factory_image = factory_image;
        }

        public int getFactory_id() {
            return factory_id;
        }

        public void setFactory_id(int factory_id) {
            this.factory_id = factory_id;
        }

        public String getFactory_name() {
            return factory_name;
        }

        public void setFactory_name(String factory_name) {
            this.factory_name = factory_name;
        }
    }

    public static class BrandListBean {
        /**
         * brand_id : 6
         * brand_img : http://121.40.186.118:5008/upload/2.jpg
         */

        private int brand_id;
        private String brand_img;

        public int getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(int brand_id) {
            this.brand_id = brand_id;
        }

        public String getBrand_img() {
            return brand_img;
        }

        public void setBrand_img(String brand_img) {
            this.brand_img = brand_img;
        }
    }
}
