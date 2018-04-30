package com.sk.jintang.module.shoppingcart.network;

import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.ResponseObj;
import com.sk.jintang.module.shoppingcart.network.response.AllShoppingCartObj;
import com.sk.jintang.module.shoppingcart.network.response.ShopGoodsTypeObj;
import com.sk.jintang.module.shoppingcart.network.response.ShopIntroduceObj;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/6/28.
 */

public interface IRequest {
    //购物车
    @GET("api/ShoppingCart/GetShoppingCart")
    Call<ResponseObj<AllShoppingCartObj>> getShoppingCart(@QueryMap Map<String, String> map);

    //删除购物车
    @GET("api/ShoppingCart/GetDelShoppingCart")
    Call<ResponseObj<BaseObj>> deleteShoppingCart(@QueryMap Map<String, String> map);

    //购物车数量修改
    @GET("api/ShoppingCart/GetEditNum")
    Call<ResponseObj<BaseObj>> updateShoppingCartNum(@QueryMap Map<String, String> map);

    //商铺简介
    @GET("api/Store/GetStoreIntro")
    Call<ResponseObj<ShopIntroduceObj>> getShopIntroduce(@QueryMap Map<String, String> map);

    //商铺宝贝分类
    @GET("api/Store/GetStoreType")
    Call<ResponseObj<ShopGoodsTypeObj>> getShopGoodsType(@QueryMap Map<String, String> map);
}
