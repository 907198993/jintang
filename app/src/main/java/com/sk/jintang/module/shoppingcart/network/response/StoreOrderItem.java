package com.sk.jintang.module.shoppingcart.network.response;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/7.
 */

public class StoreOrderItem {

    private StoreOrder body;

    public StoreOrder getBody() {
        return body;
    }

    public void setBody(StoreOrder body) {
        this.body = body;
    }

    public static class StoreOrder {

        private List<SureGoodsItem> goods_list;
        private List remarkList;



    public List<SureGoodsItem> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<SureGoodsItem> goods_list) {
        this.goods_list = goods_list;
    }

        public List getRemarkList() {
            return remarkList;
        }

        public void setRemarkList(List remarkList) {
            this.remarkList = remarkList;
        }
    }
}
