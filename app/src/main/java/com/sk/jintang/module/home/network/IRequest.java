package com.sk.jintang.module.home.network;

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

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/6/28.
 */

public interface IRequest {
    //首页轮播
    @GET("api/HomePage/GetHomePageTop")
    Call<ResponseObj<HomeBannerObj>> getHomePage(@QueryMap Map<String,String> map);

    //app更新
    @GET("api/MyLib/GetVersionUpdate")
    Call<ResponseObj<AppVersionObj>> getAppVersion(@QueryMap Map<String,String> map);

    //广告图片
    @GET("api/HomePage/GetAdvertisingFigure")
    Call<ResponseObj<AdverImgObj>> getAdverImg(@QueryMap Map<String,String> map);

    //首页活动相关
    @GET("api/HomePage/GetHomePageCenter")
    Call<ResponseObj<HomeHuoDongObj>> getHomeHuoDong(@QueryMap Map<String,String> map);

    //首页商品推荐
    @GET("api/HomePage/GetHomePageRecommendGoods")
    Call<ResponseObj<List<HomeGoodsObj>>> getHomeGoods(@QueryMap Map<String,String> map);

    //首页底部数据
    @GET("api/HomePage/GetHomePageBottom")
    Call<ResponseObj<HomeButtomObj>> getHomeButtomData(@QueryMap Map<String,String> map);

    //获取更多工厂
    @GET("api/HomePage/GetFactoryMore")
    Call<ResponseObj<List<FactoryObj>>> getFactoryList(@QueryMap Map<String,String> map);

    //获取更多品牌
    @GET("api/HomePage/GetBrandMore")
    Call<ResponseObj<List<BrandObj>>> getBrandList(@QueryMap Map<String,String> map);

    //获取支付url
    @GET("api/MyLib/GetPayInfo")
    Call<ResponseObj<BaseObj>> getPayNotifyUrl(@QueryMap Map<String,String> map);

    //扫码
    @GET("api/HomePage/GetScan")
    Call<ResponseObj<GoodsIdObj>> getGoodsIdForScabCode(@QueryMap Map<String,String> map);

    //特殊通道
    @GET("api/Store/GetHomeSpecialStore")
    Call<ResponseObj<List<SpecialRoadObj>>> getSpecialRoad(@QueryMap Map<String,String> map);

    //通道商铺
    @GET("api/Store/GetSpecialStore")
    Call<ResponseObj<List<SpecialStoreObj>>> getSpecialStore(@QueryMap Map<String,String> map);


}
