package com.sk.jintang.module.community.network;

import com.github.retrofitutil.NoNetworkException;
import com.sk.jintang.Config;
import com.sk.jintang.base.BaseApiRequest;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.community.network.request.FaBuHuaTiItem;
import com.sk.jintang.module.community.network.request.FaBuTieZiItem;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/28.
 */

public class ApiRequest extends BaseApiRequest {
    public static void getSheQuList(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getSheQuList(map).enqueue(callBack);
    }
    public static void getSheQuHuaTi(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getSheQuHuaTi(map).enqueue(callBack);
    }
    public static void getMoreHuaTi(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getMoreHuaTi(map).enqueue(callBack);
    }
    public static void getTieZiForHuaTi(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getTieZiForHuaTi(map).enqueue(callBack);
    }
    public static void getYangShengXiaoZhiShi(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getYangShengXiaoZhiShi(map).enqueue(callBack);
    }
    public static void faBuTieZi(Map map, FaBuTieZiItem item, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).faBuTieZi(map,item).enqueue(callBack);
    }
    public static void faBuHuaTi(Map map, FaBuHuaTiItem item, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).faBuHuaTi(map,item).enqueue(callBack);
    }
    public static void tieZiDetail(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).tieZiDetail(map).enqueue(callBack);
    }
    public static void dianZan(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).dianZan(map).enqueue(callBack);
    }
    public static void dianZanHuaTi(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).dianZanHuaTi(map).enqueue(callBack);
    }
    public static void getAllEvaluateDetail(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).getAllEvaluateDetail(map).enqueue(callBack);
    }
    public static void huiFuEvaluate(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).huiFuEvaluate(map).enqueue(callBack);
    }
    public static void huiFuSecondEvaluate(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).huiFuSecondEvaluate(map).enqueue(callBack);
    }
    public static void tieZiSearch(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) { callBack.onFailure(null, new NoNetworkException(Config.noNetWork)); return;}
        getGeneralClient(IRequest.class).tieZiSearch(map).enqueue(callBack);
    }

}
