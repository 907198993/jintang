package com.sk.jintang.tools;

import com.github.rxjava.rxbus.RxUtils;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.ResponseObj;
import com.sk.jintang.base.ServerException;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2017/7/20.
 */

public class RxResult extends RxUtils {
    public static <T> Observable.Transformer<ResponseObj<T>, T> handleResult(){
        return apiResponse -> apiResponse.flatMap(
                new Func1<ResponseObj<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(ResponseObj<T> response) {
                        if (response==null){
                            return Observable.empty();
                        }else if (response.isSuccess()){
                            T res = response.getResponse();
                            if(res==null){
                                return returnDataForMsg(res,response.getErrMsg());
                            }
                            Class<?> responseClass = res.getClass();
                            Class baseObjClass=BaseObj.class;
//                            Class ListClass=List.class;
                            boolean assignableFromBaseObj = baseObjClass.isAssignableFrom(responseClass);
//                            boolean assignableFromList = ListClass.isAssignableFrom(responseClass);
                            if(assignableFromBaseObj){
                                return returnDataForMsg(res,response.getErrMsg());
                            }else{
                                return returnData(response.getResponse());
                            }
                        }else{
                            return Observable.error(new ServerException(response.getErrMsg()+""));
                        }
                    }
                });
    }
    private static <T> Observable<T> returnData(final T result) {
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(result);
                subscriber.onCompleted();
            }catch (Exception e){
                subscriber.onError(e);
            }
        });
    }
    private static <T> Observable<T> returnDataForMsg(final T result, final String msg) {
        return Observable.create(subscriber -> {
            try {
                if(result!=null){
                    ((BaseObj)result).setMsg(msg);
                    subscriber.onNext(result);
                }else{
                    T newResult = (T) new BaseObj();
                    ((BaseObj)newResult).setMsg(msg);
                    subscriber.onNext(newResult);
                }
                subscriber.onCompleted();
            }catch (Exception e){
                subscriber.onError(e);
            }
        });
    }
    public static <T> Observable.Transformer<ResponseObj<T>, T> listResult(){
        return apiResponse -> apiResponse.flatMap(
                new Func1<ResponseObj<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(ResponseObj<T> response) {
                        if (response==null){
                            return Observable.empty();
                        }else if (response.isSuccess()){
                            return returnData(response.getResponse());
                        }else{
                            return Observable.error(new ServerException(response.getErrMsg()+""));
                        }
                    }
                });
    }
}
