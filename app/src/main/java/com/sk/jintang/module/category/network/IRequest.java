package com.sk.jintang.module.category.network;

import com.sk.jintang.base.ResponseObj;
import com.sk.jintang.module.category.network.response.CategoryObj;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/6/28.
 */

public interface IRequest {
    //获取商品分类
    @GET("api/Store/GetGoodsType")
    Call<ResponseObj<CategoryObj>> getGoodsCateyoryList(@QueryMap Map<String, String> map);

}
