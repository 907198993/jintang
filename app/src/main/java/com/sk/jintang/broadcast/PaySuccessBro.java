package com.sk.jintang.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by administartor on 2017/8/10.
 */

public class PaySuccessBro extends BroadcastReceiver {
    public interface PaySuccessBroInter{
       void paySuccessBro();
    }

    private PaySuccessBroInter inter;
    public PaySuccessBro(PaySuccessBroInter inter) {
        this.inter=inter;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        inter.paySuccessBro();
    }
}
