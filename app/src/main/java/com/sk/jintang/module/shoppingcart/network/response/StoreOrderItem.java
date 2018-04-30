package com.sk.jintang.module.shoppingcart.network.response;

import java.util.List;

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
        private List<Remark> remarkList;



    public List<SureGoodsItem> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<SureGoodsItem> goods_list) {
        this.goods_list = goods_list;
    }

    public List<Remark> getRemarkList() {
        return remarkList;
    }

    public void setRemarkList(List<Remark> remarkList) {
        this.remarkList = remarkList;
    }
    }
}
