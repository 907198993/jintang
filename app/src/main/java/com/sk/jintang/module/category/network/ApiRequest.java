package com.sk.jintang.module.category.network;

import com.github.retrofitutil.NoNetworkException;
import com.sk.jintang.Config;
import com.sk.jintang.base.BaseApiRequest;
import com.sk.jintang.base.MyCallBack;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/28.
 */

public class ApiRequest extends BaseApiRequest {

    public static void getGoodsCateyoryList(Map map, MyCallBack callBack) {
        if (notNetWork(callBack.getContext())) {
            callBack.onFailure(null, new NoNetworkException(Config.noNetWork));
            return;
        }
        getGeneralClient(IRequest.class).getGoodsCateyoryList(map).enqueue(callBack);
    }


}
