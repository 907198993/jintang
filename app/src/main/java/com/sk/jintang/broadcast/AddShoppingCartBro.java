package com.sk.jintang.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by administartor on 2017/8/10.
 */

public class AddShoppingCartBro extends BroadcastReceiver {
    public interface AddShoppingCartBroInter{
       void addShoppingCart();
    }

    private AddShoppingCartBroInter inter;
    public AddShoppingCartBro(AddShoppingCartBroInter inter) {
        this.inter=inter;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        inter.addShoppingCart();
    }
}
