package com.sk.jintang.module.community.network;

import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.ResponseObj;
import com.sk.jintang.module.community.network.request.FaBuHuaTiItem;
import com.sk.jintang.module.community.network.request.FaBuTieZiItem;
import com.sk.jintang.module.community.network.response.HuaTiObj;
import com.sk.jintang.module.community.network.response.MoreHuaTiObj;
import com.sk.jintang.module.community.network.response.SheQuHuaTiObj;
import com.sk.jintang.module.community.network.response.SheQuObj;
import com.sk.jintang.module.community.network.response.TieZiAllEvaluateObj;
import com.sk.jintang.module.community.network.response.TieZiDetailObj;
import com.sk.jintang.module.community.network.response.TieZiSearchObj;
import com.sk.jintang.module.community.network.response.YangShengXiaoZhiShiObj;
import com.sk.jintang.module.community.network.response.ZanObj;

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
    //社区
    @GET("api/Community/GetCommunityList")
    Call<ResponseObj<List<SheQuObj>>> getSheQuList(@QueryMap Map<String, String> map);

    //社区-话题
    @GET("api/Community/GetCommunityImg")
    Call<ResponseObj<SheQuHuaTiObj>> getSheQuHuaTi(@QueryMap Map<String, String> map);

    //更多话题
    @GET("api/Community/GetMoreTopic")
    Call<ResponseObj<List<MoreHuaTiObj>>> getMoreHuaTi(@QueryMap Map<String, String> map);

    //话题列表
    @GET("api/Community/GetTopicList")
    Call<ResponseObj<List<HuaTiObj>>> getTieZiForHuaTi(@QueryMap Map<String, String> map);

    //养生小知识
    @GET("api/Community/GetHealthEducationList")
    Call<ResponseObj<List<YangShengXiaoZhiShiObj>>> getYangShengXiaoZhiShi(@QueryMap Map<String, String> map);

    //发布帖子
    @POST("api/Community/PostMessage")
    Call<ResponseObj<BaseObj>> faBuTieZi(@QueryMap Map<String, String> map, @Body FaBuTieZiItem item);

    //发布话题
    @POST("api/Community/PostTopic")
    Call<ResponseObj<BaseObj>> faBuHuaTi(@QueryMap Map<String, String> map, @Body FaBuHuaTiItem item);


    //帖子-详情
    @GET("api/Community/GetCommunityDetails")
    Call<ResponseObj<TieZiDetailObj>> tieZiDetail(@QueryMap Map<String, String> map);

    //帖子-评论点赞 1点赞帖子 2点赞评论
    @GET("api/Community/GetthumbupForum")
    Call<ResponseObj<ZanObj>> dianZan(@QueryMap Map<String, String> map);

    //话题-评论点赞
    @GET("api/Community/GetthumbupForum")
    Call<ResponseObj<BaseObj>> dianZanHuaTi(@QueryMap Map<String, String> map);


    //帖子—所有评论详情
    @GET("api/Community/GetCommentDetail")
    Call<ResponseObj<TieZiAllEvaluateObj>> getAllEvaluateDetail(@QueryMap Map<String, String> map);

    //评论帖子的评论
    @GET("api/Community/GetAddComment")
    Call<ResponseObj<BaseObj>> huiFuEvaluate(@QueryMap Map<String, String> map);

    //评论  帖子的评论的评论
    @GET("api/Community/GetAddReply")
    Call<ResponseObj<BaseObj>> huiFuSecondEvaluate(@QueryMap Map<String, String> map);

    //社区搜索
    @GET("api/Community/GetSearchForum")
    Call<ResponseObj<List<TieZiSearchObj>>> tieZiSearch(@QueryMap Map<String, String> map);


}
