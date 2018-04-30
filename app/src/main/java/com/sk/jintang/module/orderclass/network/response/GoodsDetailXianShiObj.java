package com.sk.jintang.module.orderclass.network.response;

import com.sk.jintang.base.BaseObj;

import java.io.Serializable;
import java.util.List;

/**
 * Created by administartor on 2017/10/9.
 */

public class GoodsDetailXianShiObj extends BaseObj {
    /**
     * img_list : [{"goods_image":"http://121.40.186.118:5008/upload/goods.png"},{"goods_image":"http://121.40.186.118:5008/upload/goods.png"},{"goods_image":"http://121.40.186.118:5008/upload/goods.png"}]
     * begin_time : 1507510800
     * end_time : 1507557600
     * goods_id : 4
     * goods_name : 【阿芙】按摩精油通经络全身推油身体肩颈足部开背推拿刮痧推油正品
     * price : 72.5
     * keeping_bean : 72
     * flash_price : 12.5
     * has_been_robbed : 175
     * surplus : 200
     * courier : 0
     * sales_volume : 8332
     * addresss : 深圳
     * pingjia_num : 1
     * pingjia_list : [{"photo":"http://121.40.186.118:5008/upload/201709/20/170920114844884558.jpg","nickname":"哟嚯","content":"Ufufuyvyuv","add_time":"2017/9/26"}]
     * goods_details : 详情没有
     * tuijian_list : [{"goods_id":9,"goods_image":"http://121.40.186.118:5008/upload/goods.png","goods_name":"【阿芙】按摩精油通经络全身推油身体肩颈足部开背推拿刮痧推油正品","addresss":"深圳","price":72.5,"sales_volume":8332},{"goods_id":8,"goods_image":"http://121.40.186.118:5008/upload/goods.png","goods_name":"【阿芙】按摩精油通经络全身推油身体肩颈足部开背推拿刮痧推油正品","addresss":"深圳","price":72.5,"sales_volume":8332},{"goods_id":4,"goods_image":"http://121.40.186.118:5008/upload/goods.png","goods_name":"【阿芙】按摩精油通经络全身推油身体肩颈足部开背推拿刮痧推油正品","addresss":"深圳","price":72.5,"sales_volume":8332}]
     * is_collect : 0
     */

    private long begin_time;
    private long end_time;
    private int goods_id;
    private String goods_name;
    private String goods_image;
    private double price;
    private int keeping_bean;
    private double flash_price;
    private int has_been_robbed;
    private int surplus;
    private double courier;
    private int sales_volume;
    private String addresss;
    private int pingjia_num;
    private List<String> goods_details;
    private int is_collect;
    private List<ImgListBean> img_list;
    private List<PingjiaListBean> pingjia_list;
    private List<TuijianListBean> tuijian_list;

    private long num=1000;
    public long getBegin_time() {
        return begin_time*num;
    }

    public void setBegin_time(long begin_time) {
        this.begin_time = begin_time;
    }

    public String getGoods_image() {
        return goods_image;
    }

    public void setGoods_image(String goods_image) {
        this.goods_image = goods_image;
    }

    public long getEnd_time() {
        return end_time*num;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getKeeping_bean() {
        return keeping_bean;
    }

    public void setKeeping_bean(int keeping_bean) {
        this.keeping_bean = keeping_bean;
    }

    public double getFlash_price() {
        return flash_price;
    }

    public void setFlash_price(double flash_price) {
        this.flash_price = flash_price;
    }

    public int getHas_been_robbed() {
        return has_been_robbed;
    }

    public void setHas_been_robbed(int has_been_robbed) {
        this.has_been_robbed = has_been_robbed;
    }

    public int getSurplus() {
        return surplus;
    }

    public void setSurplus(int surplus) {
        this.surplus = surplus;
    }

    public double getCourier() {
        return courier;
    }

    public void setCourier(double courier) {
        this.courier = courier;
    }

    public int getSales_volume() {
        return sales_volume;
    }

    public void setSales_volume(int sales_volume) {
        this.sales_volume = sales_volume;
    }

    public String getAddresss() {
        return addresss;
    }

    public void setAddresss(String addresss) {
        this.addresss = addresss;
    }

    public int getPingjia_num() {
        return pingjia_num;
    }

    public void setPingjia_num(int pingjia_num) {
        this.pingjia_num = pingjia_num;
    }

    public List<String> getGoods_details() {
        return goods_details;
    }

    public void setGoods_details(List<String> goods_details) {
        this.goods_details = goods_details;
    }

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

    public List<ImgListBean> getImg_list() {
        return img_list;
    }

    public void setImg_list(List<ImgListBean> img_list) {
        this.img_list = img_list;
    }

    public List<PingjiaListBean> getPingjia_list() {
        return pingjia_list;
    }

    public void setPingjia_list(List<PingjiaListBean> pingjia_list) {
        this.pingjia_list = pingjia_list;
    }

    public List<TuijianListBean> getTuijian_list() {
        return tuijian_list;
    }

    public void setTuijian_list(List<TuijianListBean> tuijian_list) {
        this.tuijian_list = tuijian_list;
    }

    public static class ImgListBean implements Serializable {
        /**
         * goods_image : http://121.40.186.118:5008/upload/goods.png
         */

        private String goods_image;

        public String getGoods_image() {
            return goods_image;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image;
        }
    }

    public static class PingjiaListBean implements Serializable{
        /**
         * photo : http://121.40.186.118:5008/upload/201709/20/170920114844884558.jpg
         * nickname : 哟嚯
         * content : Ufufuyvyuv
         * add_time : 2017/9/26
         */

        private String photo;
        private String nickname;
        private String content;
        private String add_time;

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }
    }

    public static class TuijianListBean implements Serializable{
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
