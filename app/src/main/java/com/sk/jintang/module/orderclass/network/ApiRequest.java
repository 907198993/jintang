package com.sk.jintang.module.orderclass.network;

import com.github.retrofitutil.NoNetworkException;
import com.sk.jintang.Config;
import com.sk.jintang.base.BaseApiRequest;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.base.ResponseObj;
import com.sk.jintang.module.home.network.response.BrandObj;
import com.sk.jintang.module.orderclass.network.request.ShoppingCartItem;
import com.sk.jintang.module.orderclass.network.response.CityObj;
import com.sk.jintang.module.shoppingcart.network.response.StoreOrderItem;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/28.
 */

public class ApiRequest extends BaseApiRequest {

    /*public static Observable getRegisterXieYi(String rnd, String sign){
        return getCommonClient(com.sk.yangyu.module.home.network.IRequest.class).getPayNotifyUrl(rnd,sign).compose(RxResult.appSchedulers()).compose(RxResult.handleResult());
    }*/
    public static void getGoodsList(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).getGoodsList(map).enqueue(callBack);
    }

    public static void getStoreGoodsList(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).getStoreGoodsList(map).enqueue(callBack);
    }

    public static void getGoodsDetail(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).getGoodsDetail(map).enqueue(callBack);
    }
    //商铺
    public static void getShopsDetail(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).getShopDetail(map).enqueue(callBack);
    }

    //获取特殊通道商铺
    public static void getSpecialShopDetail(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).getSpecialShopDetail(map).enqueue(callBack);
    }

    //关注店铺
    public static void getShopAttention(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).getShopAttention(map).enqueue(callBack);
    }

    //取消关注店铺
    public static void getShopAbolishAttention(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).getShopAbolishAttention(map).enqueue(callBack);
    }

    public static void getGoodsDetailXianShi(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).getGoodsDetailXianShi(map).enqueue(callBack);
    }
    public static void getGoodsGuiGe(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).getGoodsGuiGe(map).enqueue(callBack);
    }
    public static void collectGoods(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).collectGoods(map).enqueue(callBack);
    }
    public static void addShoppingCart(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).addShoppingCart(map).enqueue(callBack);
    }
    public static void getShoppingCartNum(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getShoppingCartNum(map).enqueue(callBack);
    }
    public static void getAllGoodsEvaluate(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getAllGoodsEvaluate(map).enqueue(callBack);
    }
    public static void getAllGoodsEvaluateNum(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getAllGoodsEvaluateNum(map).enqueue(callBack);
    }
    public static void getGoodsParams(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getGoodsParams(map).enqueue(callBack);
    }
    public static void getGoodsCategory(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getGoodsCategory(map).enqueue(callBack);
    }
    public static ResponseObj<List<BrandObj>> getBrandList(Map map) {
        try {
            return (ResponseObj<List<BrandObj>>) getGeneralClient(IRequest.class).getBrandList(map).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ResponseObj<List<CityObj>> getCityForGoodsList(Map map) {
        try {
            return (ResponseObj<List<CityObj>>) getGeneralClient(IRequest.class).getCityForGoodsList(map).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void getMakeSureOrder(Map map, ShoppingCartItem item, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getMakeSureOrder(map,item).enqueue(callBack);
    }
    public static void getYouHuiQuan(Map map , MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getYouHuiQuan(map).enqueue(callBack);
    }
    public static void commitGoods(Map map ,ShoppingCartItem item, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).commitGoods(map,item).enqueue(callBack);
    }
    public static void yuEPay(Map map  , MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).yuEPay(map).enqueue(callBack);
    }
    public static void commitTuanGouGoods(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).commitTuanGouGoods(map).enqueue(callBack);
    }
    public static void commitHourDaoGoods(Map map , StoreOrderItem storeOrderItem, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).commitHourDaoGoods(map,storeOrderItem).enqueue(callBack);
    }
    public static void getJiuJinFaHuo(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getJiuJinFaHuo(map).enqueue(callBack);
    }
    public static void xianShiTime(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).xianShiTime(map).enqueue(callBack);
    }
    public static void xianShiQiangGouList(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).xianShiQiangGouList(map).enqueue(callBack);
    }
    public static void xianShiTiXing(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).xianShiTiXing(map).enqueue(callBack);
    }
    public static void getVouchersList(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getVouchersList(map).enqueue(callBack);
    }
    public static void getVouchers(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getVouchers(map).enqueue(callBack);
    }
    public static void getDifferentGoods(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getDifferentGoods(map).enqueue(callBack);
    }
    public static void getSearchRecord(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getSearchRecord(map).enqueue(callBack);
    }
    public static void deleteSearchRecord(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).deleteSearchRecord(map).enqueue(callBack);
    }
    public static void goodsSearch(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).goodsSearch(map).enqueue(callBack);
    }
    public static void goodsForGC(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).goodsForGC(map).enqueue(callBack);
    }
    public static void goodsForPP(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).goodsForPP(map).enqueue(callBack);
    }
    public static void getAllCity(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getAllCity(map).enqueue(callBack);
    }
    public static void getHotCity(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getHotCity(map).enqueue(callBack);
    }
    public static void getProvince(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getProvince(map).enqueue(callBack);
    }
    public static void getCityForProvince(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getCityForProvince(map).enqueue(callBack);
    }
    public static void getHourDaoList(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getHourDaoList(map).enqueue(callBack);
    }
    public static void getTuanGouList(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getTuanGouList(map).enqueue(callBack);
    }
    public static void getTuanGouDetail(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getTuanGouDetail(map).enqueue(callBack);
    }
    public static void tuanGouSureOrder(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).tuanGouSureOrder(map).enqueue(callBack);
    }
    public static void xianShiSureOrder(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).xianShiSureOrder(map).enqueue(callBack);
    }
    public static void hourDaoSureOrder(Map map , ShoppingCartItem item, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).hourDaoSureOrder(map,item).enqueue(callBack);
    }

    public static void specialSureOrder(Map map , ShoppingCartItem item, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).specialSureOrder(map,item).enqueue(callBack);
    }

    public static void commitXianShiSureOrder(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).commitXianShiSureOrder(map).enqueue(callBack);
    }
    public static void getQuestionList(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getQuestionList(map).enqueue(callBack);
    }
    public static void getQuestionDetail(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getQuestionDetail(map).enqueue(callBack);
    }
    public static void huiDaQuestion(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).huiDaQuestion(map).enqueue(callBack);
    }
    public static void faBuQuestion(Map map ,MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).faBuQuestion(map).enqueue(callBack);
    }
}
