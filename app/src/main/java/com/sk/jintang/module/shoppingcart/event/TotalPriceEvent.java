package com.sk.jintang.module.shoppingcart.event;

/**
 * Created by administartor on 2017/9/18.
 */

public class TotalPriceEvent {
    public String totalPrice;
    public boolean isSelectAll;

    public TotalPriceEvent(String totalPrice,boolean isSelectAll) {
        this.totalPrice = totalPrice;
        this.isSelectAll = isSelectAll;
    }
}
