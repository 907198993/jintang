package com.sk.jintang.module.orderclass.network;

import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.ResponseObj;
import com.sk.jintang.module.home.network.response.BrandObj;
import com.sk.jintang.module.orderclass.network.request.ShoppingCartItem;
import com.sk.jintang.module.orderclass.network.response.CityObj;
import com.sk.jintang.module.orderclass.network.response.CommitGoodsObj;
import com.sk.jintang.module.orderclass.network.response.DifferentGoodsObj;
import com.sk.jintang.module.orderclass.network.response.GetVouchersObj;
import com.sk.jintang.module.orderclass.network.response.GoodsCategoryObj;
import com.sk.jintang.module.orderclass.network.response.GoodsDetailObj;
import com.sk.jintang.module.orderclass.network.response.GoodsDetailXianShiObj;
import com.sk.jintang.module.orderclass.network.response.GoodsEvaluateNumObj;
import com.sk.jintang.module.orderclass.network.response.GoodsEvaluateObj;
import com.sk.jintang.module.orderclass.network.response.GoodsListObj;
import com.sk.jintang.module.orderclass.network.response.GoodsParamsObj;
import com.sk.jintang.module.orderclass.network.response.GoodsQuestionObj;
import com.sk.jintang.module.orderclass.network.response.GoodsSearchObj;
import com.sk.jintang.module.orderclass.network.response.GuiGeObj;
import com.sk.jintang.module.orderclass.network.response.HourDaoObj;
import com.sk.jintang.module.orderclass.network.response.QuestionDetailObj;
import com.sk.jintang.module.orderclass.network.response.SearchRecordObj;
import com.sk.jintang.module.orderclass.network.response.ShopDetailObj;
import com.sk.jintang.module.orderclass.network.response.StatusObj;
import com.sk.jintang.module.orderclass.network.response.TuanGouDetailObj;
import com.sk.jintang.module.orderclass.network.response.TuanGouObj;
import com.sk.jintang.module.orderclass.network.response.XianShiQiangGouObj;
import com.sk.jintang.module.orderclass.network.response.XianShiTimeObj;
import com.sk.jintang.module.orderclass.network.response.YouHuiQuanObj;
import com.sk.jintang.module.shoppingcart.network.response.StoreOrderItem;
import com.sk.jintang.module.shoppingcart.network.response.SureOrderObj;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/6/28.
 */

public interface IRequest {
    //获取商品列表
    @GET("api/GoodsClassiFication/GetGoodsList")
    Call<ResponseObj<List<GoodsListObj>>> getGoodsList(@QueryMap Map<String, String> map);

    //获取商铺商品列表
    @GET("api/Store/GetGoodsList")
    Call<ResponseObj<List<GoodsListObj>>> getStoreGoodsList(@QueryMap Map<String, String> map);

    //获取商品详情
    @GET("api/GoodsClassiFication/GetGoodsDetails")
    Call<ResponseObj<GoodsDetailObj>> getGoodsDetail(@QueryMap Map<String, String> map);

    //获取商铺
    @GET("api/Store/GetStoreHome")
    Call<ResponseObj<ShopDetailObj>> getShopDetail(@QueryMap Map<String, String> map);

    //获取特殊通道商铺
    @GET("api/Store/GetSpecialStoreHome")
    Call<ResponseObj<SpecialShopDetailObj>> getSpecialShopDetail(@QueryMap Map<String, String> map);



    //关注店铺
    @POST("api/Store/AttentionStore")
    Call<ResponseObj<StatusObj>> getShopAttention(@QueryMap Map<String, String> map);

    //取消关注店铺
    @POST("api/Store/AbolishAttentionStore")
    Call<ResponseObj<StatusObj>> getShopAbolishAttention(@QueryMap Map<String, String> map);


    //限时抢购-获取商品详情
    @GET("api/HomePage/GetGoodsDetails")
    Call<ResponseObj<GoodsDetailXianShiObj>> getGoodsDetailXianShi(@QueryMap Map<String, String> map);

    //获取商品规格
    @GET("api/GoodsClassiFication/GetProductSpecification")
    Call<ResponseObj<List<GuiGeObj>>> getGoodsGuiGe(@QueryMap Map<String, String> map);

    //收藏和取消收藏
    @GET("api/UserBase/GetCollection")
    Call<ResponseObj<BaseObj>> collectGoods(@QueryMap Map<String, String> map);

    //加入购物车
    @GET("api/ShoppingCart/GetAddShoppingCart")
    Call<ResponseObj<BaseObj>> addShoppingCart(@QueryMap Map<String, String> map);

    //获取购物车数量
    @GET("api/ShoppingCart/GetShoppingCartCount")
    Call<ResponseObj<BaseObj>> getShoppingCartNum(@QueryMap Map<String, String> map);

    //商品全部评价
    @GET("api/GoodsClassiFication/GetAppraiseList")
    Call<ResponseObj<List<GoodsEvaluateObj>>> getAllGoodsEvaluate(@QueryMap Map<String, String> map);

    //商品全部评价数量
    @GET("api/GoodsClassiFication/GetAppraiseNum")
    Call<ResponseObj<GoodsEvaluateNumObj>> getAllGoodsEvaluateNum(@QueryMap Map<String, String> map);

    //商品产品参数
    @GET("api/GoodsClassiFication/GetProductParameter")
    Call<ResponseObj<List<GoodsParamsObj>>> getGoodsParams(@QueryMap Map<String, String> map);

    //商品列表获取分类
    @GET("api/GoodsClassiFication/GetGoodsType")
    Call<ResponseObj<List<GoodsCategoryObj>>> getGoodsCategory(@QueryMap Map<String, String> map);

    //获取更多品牌
    @GET("api/HomePage/GetBrandMore")
    Call<ResponseObj<List<BrandObj>>> getBrandList(@QueryMap Map<String,String> map);

    //获取商品发货地
    @GET("api/GoodsClassiFication/GetGoodsCity")
    Call<ResponseObj<List<CityObj>>> getCityForGoodsList(@QueryMap Map<String,String> map);

    //结算订单显示
    @POST("api/GoodsClassiFication/PostMakeSureOrder")
    Call<ResponseObj<SureOrderObj>> getMakeSureOrder(@QueryMap Map<String,String> map, @Body ShoppingCartItem item);

    //结算订单获取优惠券
    @GET("api/GoodsClassiFication/GetAvailableCoupon")
    Call<ResponseObj<List<YouHuiQuanObj>>> getYouHuiQuan(@QueryMap Map<String,String> map);

    //提交订单
    @POST("api/GoodsClassiFication/PostSubmitOrder")
    Call<ResponseObj<CommitGoodsObj>> commitGoods(@QueryMap Map<String,String> map, @Body ShoppingCartItem item);

    //余额支付
    @GET("api/GoodsClassiFication/GetBalancePayment")
    Call<ResponseObj<BaseObj>> yuEPay(@QueryMap Map<String,String> map);

    //团购提交订单
    @GET("api/HomePage/GetSubmitProductGroupOrder")
    Call<ResponseObj<CommitGoodsObj>> commitTuanGouGoods(@QueryMap Map<String,String> map);


    //24小时提交订单
    @GET("api/HomePage/GetTwoFourSubmitOrder")
    Call<ResponseObj<CommitGoodsObj>> commitHourDaoGoodsOld(@QueryMap Map<String,String> map);
    //24小时提交订单
    @POST("api/HomePage/PostTwoFourSubmitOrder")
    Call<ResponseObj<CommitGoodsObj>> commitHourDaoGoods(@QueryMap Map<String,String> map, @Body StoreOrderItem storeOrderItem);

    //就近发货图片
    @GET("api/HomePage/GetShippingNearest")
    Call<ResponseObj<BaseObj>> getJiuJinFaHuo(@QueryMap Map<String,String> map );

    //限时抢购-时间
    @GET("api/HomePage/GetFlashTime")
    Call<ResponseObj<List<XianShiTimeObj>>> xianShiTime(@QueryMap Map<String,String> map );
    //限时抢购-时间
    @GET("api/HomePage/GetTimeBuyingTime")
    Call<ResponseObj<XianShiTimeObj>> xianShiTimeOld(@QueryMap Map<String,String> map );

    //限时抢购-列表
    @GET("api/HomePage/GetFlashTimeGoodsList")
    Call<ResponseObj<List<XianShiQiangGouObj>>> xianShiQiangGouList(@QueryMap Map<String,String> map );
    //限时抢购-列表
    @GET("api/HomePage/GetTimeBuying")
    Call<ResponseObj<List<XianShiQiangGouObj>>> xianShiQiangGouListOld(@QueryMap Map<String,String> map );

    //限时抢购-提醒
    @GET("api/HomePage/GetTeaTimer")
    Call<ResponseObj<BaseObj>> xianShiTiXing(@QueryMap Map<String,String> map );

    //首页领取优惠券-列表
    @GET("api/HomePage/GetCouponRedemption")
    Call<ResponseObj<List<GetVouchersObj>>> getVouchersList(@QueryMap Map<String,String> map );

    //首页优惠券list-领取
    @GET("api/HomePage/GetAddCoupon")
    Call<ResponseObj<BaseObj>> getVouchers(@QueryMap Map<String,String> map );

    //4今日爆款 5聚实惠 6家庭必备 7免费试用 9微商代发
    @GET("api/HomePage/GetTodayHotStyle")
    Call<ResponseObj<List<DifferentGoodsObj>>> getDifferentGoods(@QueryMap Map<String,String> map );

    //历史搜索记录
    @GET("api/HomePage/GetHottestSearch")
    Call<ResponseObj<SearchRecordObj>> getSearchRecord(@QueryMap Map<String,String> map );


    //删除历史搜索
    @GET("api/HomePage/GetDelRecentlySearch")
    Call<ResponseObj<BaseObj>> deleteSearchRecord(@QueryMap Map<String,String> map );

    //商品搜索
    @GET("api/HomePage/GetSearchGoods")
    Call<ResponseObj<List<GoodsSearchObj>>> goodsSearch(@QueryMap Map<String,String> map);

    //工厂-商品列表
    @GET("api/HomePage/GetFactoryList")
    Call<ResponseObj<List<GoodsSearchObj>>> goodsForGC(@QueryMap Map<String,String> map);

    //品牌-商品列表
    @GET("api/HomePage/GetBrandList")
    Call<ResponseObj<List<GoodsSearchObj>>> goodsForPP(@QueryMap Map<String,String> map);

    //所有城市
    @GET("api/Lib/GetAllCity")
    Call<ResponseObj<List<CityObj>>> getAllCity(@QueryMap Map<String,String> map);

    //热门城市
    @GET("api/Lib/GetCityHot")
    Call<ResponseObj<List<CityObj>>> getHotCity(@QueryMap Map<String,String> map);

    //获取省份
    @GET("api/Lib/GetProvince")
    Call<ResponseObj<List<CityObj>>> getProvince(@QueryMap Map<String,String> map);

    //根据省份获取城市
    @GET("api/Lib/GetCity")
    Call<ResponseObj<List<CityObj>>> getCityForProvince(@QueryMap Map<String,String> map);

    //24小时到
    @GET("api/HomePage/Get24HoursTo")
    Call<ResponseObj<HourDaoObj>> getHourDaoList(@QueryMap Map<String,String> map);

    //团购列表
    @GET("api/HomePage/GetNewProductGroup")
    Call<ResponseObj<List<TuanGouObj>>> getTuanGouList(@QueryMap Map<String,String> map);

    //团购详情
    @GET("api/HomePage/GetNewProductGroupDetail")
    Call<ResponseObj<TuanGouDetailObj>> getTuanGouDetail(@QueryMap Map<String,String> map);

    //团购-订单显示
    @GET("api/HomePage/GetProductGroupOrderShow")
    Call<ResponseObj<SureOrderObj>> tuanGouSureOrder(@QueryMap Map<String,String> map);

    //限时抢购-订单显示
    @GET("api/HomePage/GetFlashOrderShow")
    Call<ResponseObj<SureOrderObj>> xianShiSureOrder(@QueryMap Map<String,String> map);

    //24小时下单显示
    @GET("api/HomePage/GetTwoFourOrderShow")
    Call<ResponseObj<SureOrderObj>> hourDaoSureOrderOld(@QueryMap Map<String,String> map);

    //24小时下单显示
    @POST("api/HomePage/PostTwoFourOrderShow")
    Call<ResponseObj<SureOrderObj>> hourDaoSureOrder(@QueryMap Map<String,String> map , @Body ShoppingCartItem item);

    //特殊通道下单显示
    @POST("api/HomePage/PostSpecialOrderShow")
    Call<ResponseObj<SureOrderObj>> specialSureOrder(@QueryMap Map<String,String> map , @Body ShoppingCartItem item);

    //限时抢购-提交订单
    @GET("api/HomePage/GetSubmitFlashOrder")
    Call<ResponseObj<CommitGoodsObj>> commitXianShiSureOrder(@QueryMap Map<String,String> map);

    //问题列表
    @GET("api/GoodsClassiFication/GetAskEveryoneList")
    Call<ResponseObj<List<GoodsQuestionObj>>> getQuestionList(@QueryMap Map<String,String> map);

    //问题详情
    @GET("api/GoodsClassiFication/GetAskEveryoneMore")
    Call<ResponseObj<List<QuestionDetailObj>>> getQuestionDetail(@QueryMap Map<String,String> map);

    //回答或者发布问题
    @GET("api/GoodsClassiFication/GetAskQuestion")
    Call<ResponseObj<BaseObj>> huiDaQuestion(@QueryMap Map<String,String> map);
    //回答或者发布问题
    @GET("api/GoodsClassiFication/GetAskQuestion")
    Call<ResponseObj<BaseObj>> faBuQuestion(@QueryMap Map<String,String> map);

}
