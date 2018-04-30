package com.sk.jintang.module.shoppingcart.network;

import com.github.retrofitutil.NoNetworkException;
import com.sk.jintang.Config;
import com.sk.jintang.base.BaseApiRequest;
import com.sk.jintang.base.MyCallBack;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/28.
 */

public class ApiRequest extends BaseApiRequest {
    public static void getShoppingCart(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getShoppingCart(map).enqueue(callBack);
    }
    public static void deleteShoppingCart(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).deleteShoppingCart(map).enqueue(callBack);
    }
    public static void updateShoppingCartNum(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).updateShoppingCartNum(map).enqueue(callBack);
    }
    public static void getShopIntroduce(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getShopIntroduce(map).enqueue(callBack);
    }
    public static void getShopGoodsType(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getShopGoodsType(map).enqueue(callBack);
    }

}
