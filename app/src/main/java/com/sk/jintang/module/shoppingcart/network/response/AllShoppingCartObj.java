package com.sk.jintang.module.shoppingcart.network.response;

import com.sk.jintang.base.BaseObj;

import java.util.List;

/**
 * Created by Administrator on 2017/11/18.
 */

public class AllShoppingCartObj extends BaseObj {
    /**
     * status : 1
     * tuijian : [{"goods_id":42,"goods_image":"http://121.40.186.118:5008/upload/201711/06/201711061809002433.jpg","goods_name":"不防油床单","addresss":"杭州","price":0.01,"sales_volume":2,"baoyou":1},{"goods_id":23,"goods_image":"http://121.40.186.118:5008/upload/201711/02/201711020933008510.jpg","goods_name":"半高领毛衣女短款套头秋冬长袖韩版修身显瘦内搭纯色针织打底衫","addresss":"上海","price":128,"sales_volume":2,"baoyou":0},{"goods_id":27,"goods_image":"http://121.40.186.118:5008/upload/201711/15/201711151649462267.png","goods_name":"丹麦B&O BeoLab 50 lab50 HIFI家庭影院音箱前置2017 国内现货","addresss":"广州","price":20,"sales_volume":0,"baoyou":0},{"goods_id":26,"goods_image":"http://121.40.186.118:5008/upload/201711/15/201711151649186436.png","goods_name":"TCL 55C5 65C5 蓝调电视 曲面4K HDR 人工智能 哈曼卡顿音响","addresss":"南京","price":100,"sales_volume":0,"baoyou":0},{"goods_id":33,"goods_image":"http://121.40.186.118:5008/upload/201711/15/201711151115064693.jpg","goods_name":"ifashion欧洲站短靴女明星街拍真皮中跟金属镂空及裸靴机车马丁靴","addresss":"上海","price":200,"sales_volume":0,"baoyou":0},{"goods_id":24,"goods_image":"http://121.40.186.118:5008/upload/201711/03/201711031122275227.jpg","goods_name":"韩版秋季棒球服春秋男宽松情侣连帽夹克男士青年百搭运动外套潮流","addresss":"北京","price":30,"sales_volume":0,"baoyou":0},{"goods_id":39,"goods_image":"http://121.40.186.118:5008/upload/201711/06/201711061804482482.jpg","goods_name":"艾艾贴","addresss":"深圳","price":380,"sales_volume":0,"baoyou":0},{"goods_id":37,"goods_image":"http://121.40.186.118:5008/upload/201711/06/201711061800467365.jpg","goods_name":"SPA醋疗浴","addresss":"广州","price":50,"sales_volume":0,"baoyou":1},{"goods_id":36,"goods_image":"http://121.40.186.118:5008/upload/201711/06/201711061757512619.jpg","goods_name":"sod蜜润肤乳","addresss":"上海","price":19,"sales_volume":0,"baoyou":1},{"goods_id":35,"goods_image":"http://121.40.186.118:5008/upload/201711/06/201711061754254045.jpg","goods_name":"1000ml泡泡浴","addresss":"上海","price":37,"sales_volume":0,"baoyou":0},{"goods_id":34,"goods_image":"http://121.40.186.118:5008/upload/201711/06/201711061749354015.jpg","goods_name":"500ml泡泡浴","addresss":"上海","price":20,"sales_volume":0,"baoyou":0},{"goods_id":22,"goods_image":"http://121.40.186.118:5008/upload/201711/01/201711011400420061.jpg","goods_name":"deHub卫生间电吹风机架子浴室置物架吸盘壁挂收纳风筒架百货","addresss":"上海","price":12,"sales_volume":0,"baoyou":0}]
     * shopping_cart : [{"id":141,"goods_id":42,"goods_name":"不防油床单","goods_image":"http://121.40.186.118:5008/upload/201711/06/201711061809002433.jpg","specification":"不防油床单","specification_id":168,"stock":0,"number":1,"price":0.01}]
     */

    private int status;
    private List<TuijianBean> tuijian;
    private List<ShoppingCartObj> shopping_cart;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<TuijianBean> getTuijian() {
        return tuijian;
    }

    public void setTuijian(List<TuijianBean> tuijian) {
        this.tuijian = tuijian;
    }

    public List<ShoppingCartObj> getShopping_cart() {
        return shopping_cart;
    }
    public void setShopping_cart(List<ShoppingCartObj> shopping_cart) {
        this.shopping_cart = shopping_cart;
    }
    public static class TuijianBean {
        /**
         * goods_id : 42
         * goods_image : http://121.40.186.118:5008/upload/201711/06/201711061809002433.jpg
         * goods_name : 不防油床单
         * addresss : 杭州
         * price : 0.01
         * sales_volume : 2
         * baoyou : 1
         */

        private int goods_id;
        private String goods_image;
        private String goods_name;
        private String addresss;
        private double price;
        private int sales_volume;
        private int baoyou;

        private double original_price;

        public double getOriginal_price() {
            return original_price;
        }

        public void setOriginal_price(double original_price) {
            this.original_price = original_price;
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

        public int getBaoyou() {
            return baoyou;
        }

        public void setBaoyou(int baoyou) {
            this.baoyou = baoyou;
        }
    }


}
