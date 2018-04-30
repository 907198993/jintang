package com.sk.jintang.shop.network;

import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.ResponseObj;
import com.sk.jintang.module.home.network.response.AdverImgObj;
import com.sk.jintang.module.home.network.response.AppVersionObj;
import com.sk.jintang.module.home.network.response.BrandObj;
import com.sk.jintang.module.home.network.response.FactoryObj;
import com.sk.jintang.module.home.network.response.GoodsIdObj;
import com.sk.jintang.module.home.network.response.HomeBannerObj;
import com.sk.jintang.module.home.network.response.HomeButtomObj;
import com.sk.jintang.module.home.network.response.HomeGoodsObj;
import com.sk.jintang.module.home.network.response.HomeHuoDongObj;
import com.sk.jintang.module.home.network.response.SpecialRoadObj;
import com.sk.jintang.module.home.network.response.SpecialStoreObj;
import com.sk.jintang.shop.network.response.ShopDataObj;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/6/28.
 */

public interface IRequest {
    //特殊通道商品列表
    @GET("api/Store/GetSpecialStoreGoodsList")
    Call<ResponseObj<ShopDataObj>> getSpecialStoreGoodsList(@QueryMap Map<String, String> map);




}
