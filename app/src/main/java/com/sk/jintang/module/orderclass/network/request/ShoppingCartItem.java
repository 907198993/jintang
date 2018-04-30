package com.sk.jintang.module.orderclass.network.request;
import java.io.Serializable;
import java.util.List;

/**
 * Created by administartor on 2017/9/18.
 */

public class ShoppingCartItem  implements Serializable {

    private List<BodyBean> body;


    public List<BodyBean> getBody() {
        return body;
    }

    public void setBody(List<BodyBean> body) {
        this.body = body;
    }


    public static class BodyBean  implements Serializable  {
        /**
         * shopping_cart_id : 1
         * goods_id : 2
         * specification_id : 3
         * number : 4
         */

        private int shopping_cart_id;
        private String goods_id;
        private int specification_id;
        private int number;

        public int getShopping_cart_id() {
            return shopping_cart_id;
        }

        public void setShopping_cart_id(int shopping_cart_id) {
            this.shopping_cart_id = shopping_cart_id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public int getSpecification_id() {
            return specification_id;
        }

        public void setSpecification_id(int specification_id) {
            this.specification_id = specification_id;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }
}
