package com.sk.jintang.module.my.network;

import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.ResponseObj;
import com.sk.jintang.module.my.network.request.AgainEvaluateItem;
import com.sk.jintang.module.my.network.request.FaBiaoPingJiaBodyItem;
import com.sk.jintang.module.my.network.request.UploadImgItem;
import com.sk.jintang.module.my.network.response.AccountObj;
import com.sk.jintang.module.my.network.response.AddressObj;
import com.sk.jintang.module.my.network.response.AliAccountObj;
import com.sk.jintang.module.my.network.response.BalanceObj;
import com.sk.jintang.module.my.network.response.BankObj;
import com.sk.jintang.module.my.network.response.DefaultBankObj;
import com.sk.jintang.module.my.network.response.EvaluateDetailObj;
import com.sk.jintang.module.my.network.response.FaBiaoPingJiaObj;
import com.sk.jintang.module.my.network.response.FenXiangObj;
import com.sk.jintang.module.my.network.response.FenXiaoObj;
import com.sk.jintang.module.my.network.response.LoginObj;
import com.sk.jintang.module.my.network.response.MakeAccountOrderObj;
import com.sk.jintang.module.my.network.response.MessageDetailObj;
import com.sk.jintang.module.my.network.response.MessageListObj;
import com.sk.jintang.module.my.network.response.MyCollectObj;
import com.sk.jintang.module.my.network.response.MyEvaluateObj;
import com.sk.jintang.module.my.network.response.MySheQuObj;
import com.sk.jintang.module.my.network.response.OrderDetailObj;
import com.sk.jintang.module.my.network.response.OrderListObj;
import com.sk.jintang.module.my.network.response.OrderNumObj;
import com.sk.jintang.module.my.network.response.QianDaoObj;
import com.sk.jintang.module.my.network.response.StoreCollectObj;
import com.sk.jintang.module.my.network.response.TuiHuanHuoObj;
import com.sk.jintang.module.my.network.response.TuiHuoDetailObj;
import com.sk.jintang.module.my.network.response.TuiKuanMoneyObj;
import com.sk.jintang.module.my.network.response.TuiKuanReasonObj;
import com.sk.jintang.module.my.network.response.UserInfoObj;
import com.sk.jintang.module.my.network.response.VouchersNumObj;
import com.sk.jintang.module.my.network.response.VouchersObj;
import com.sk.jintang.module.my.network.response.WuLiuObj;
import com.sk.jintang.module.my.network.response.XiaJiObj;
import com.sk.jintang.module.my.network.response.YangShengDouObj;
import com.sk.jintang.module.my.network.response.YaoQingCodeObj;
import com.sk.jintang.module.my.network.response.YongJinObj;

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
    //登录
    @GET("api/UserBase/GetUserLogin")
    Call<ResponseObj<LoginObj>> userLogin(@QueryMap Map<String,String>map);

    //第三方登录
    @GET("api/MyLib/GetAddWXUser")
    Call<ResponseObj<LoginObj>> platformLogin(@QueryMap Map<String,String>map);

    //退出登录
    @GET("api/Lib/GetLogOut")
    Call<ResponseObj<BaseObj>> exitApp(@QueryMap Map<String,String>map);

    //获取用户资料
    @GET("api/UserBase/GetUserInfo")
    Call<ResponseObj<UserInfoObj>> getUserInfo(@QueryMap Map<String,String>map);

    //单独修改用户头像
    @GET("api/UserBase/GetSetUserAvatar")
    Call<ResponseObj<BaseObj>> updateUserImg(@QueryMap Map<String,String>map);

    //获取验证码
    @GET("api/Lib/GetSMSCode")
    Call<ResponseObj<BaseObj>> getSMSCode(@QueryMap Map<String, String> map);

    //注册
    @GET("api/UserBase/GetUserRegister")
    Call<ResponseObj<BaseObj>> register(@QueryMap Map<String, String> map);

    //忘记密码
    @GET("api/UserBase/GetSetPassword")
    Call<ResponseObj<BaseObj>> forgetPWD(@QueryMap Map<String, String> map);

    //意见反馈
    @GET("api/UserBase/GetSubmitFeedback")
    Call<ResponseObj<BaseObj>> YJFK(@QueryMap Map<String, String> map);

    //设置消息提醒
    @GET("api/UserBase/GetMessageSink")
    Call<ResponseObj<BaseObj>> setMsgTiXing(@QueryMap Map<String, String> map);

    //获取消息列表
    @GET("api/UserBase/GetNewsList")
    Call<ResponseObj<List<MessageListObj>>> getMsgList(@QueryMap Map<String, String> map);

    //获取消息详情
    @GET("api/UserBase/GetNewsDetail")
    Call<ResponseObj<MessageDetailObj>> getMsgDetail(@QueryMap Map<String, String> map);

    //获取优惠券数量
    @GET("api/UserBase/GetMyCouponsNum")
    Call<ResponseObj<VouchersNumObj>> getVouchersNum(@QueryMap Map<String, String> map);

    //获取优惠券列表
    @GET("api/UserBase/GetMyCoupons")
    Call<ResponseObj<List<VouchersObj>>> getVouchersList(@QueryMap Map<String, String> map);

    //图片上传
    @POST("api/Lib/PostUploadFileBase64")
    Call<ResponseObj<BaseObj>> uploadImg(@QueryMap Map<String, String> map, @Body UploadImgItem item);

    //修改个人资料
    @GET("api/UserBase/GetEditUserInfo")
    Call<ResponseObj<BaseObj>> updateInfo(@QueryMap Map<String, String> map);

    //获取养生豆
    @GET("api/UserBase/GetMyKeepingBean")
    Call<ResponseObj<YangShengDouObj>> getYangShengDou(@QueryMap Map<String, String> map);

    //获取账户余额
    @GET("api/UserBase/GetMyBalance")
    Call<ResponseObj<BalanceObj>> getBalanceList(@QueryMap Map<String, String> map);

    //修改密码
    @GET("api/UserBase/GetSetNewPassword")
    Call<ResponseObj<BaseObj>> updatePWD(@QueryMap Map<String, String> map);

    //收货地址列表
    @GET("api/ShippingAddress/GetUserAddress")
    Call<ResponseObj<List<AddressObj>>> getAddress(@QueryMap Map<String, String> map);

    //添加收货地址
    @GET("api/ShippingAddress/GetAddUserAddress")
    Call<ResponseObj<BaseObj>> addAddress(@QueryMap Map<String, String> map);

    //编辑收货地址
    @GET("api/ShippingAddress/GetSaveUserAddress")
    Call<ResponseObj<BaseObj>> editAddress(@QueryMap Map<String, String> map);

    //删除收货地址
    @GET("api/ShippingAddress/GetUserAddressDel")
    Call<ResponseObj<BaseObj>> deleteAddress(@QueryMap Map<String, String> map);

    //设置默认地址
    @GET("api/ShippingAddress/GetEditDefalut")
    Call<ResponseObj<BaseObj>> setDefaultAddress(@QueryMap Map<String, String> map);

    //获取账户余额
    @GET("api/UserBase/GetWithdShow")
    Call<ResponseObj<BaseObj>> getBalance(@QueryMap Map<String, String> map);

    //获取默认银行卡列表
    @GET("api/CashWithdrawal/GetAccountDefault")
    Call<ResponseObj<List<DefaultBankObj>>> getDefaultBank(@QueryMap Map<String, String> map);

    //获取账户列表
    @GET("api/CashWithdrawal/GetAccount")
    Call<ResponseObj<List<AccountObj>>> getAccountList(@QueryMap Map<String, String> map);

    //获取支付宝账户列表
    @GET("api/CashWithdrawal/GetAlipayAccount")
    Call<ResponseObj<List<AliAccountObj>>> getAlipayAccount(@QueryMap Map<String, String> map);

    //添加支付宝账号
    @GET("api/CashWithdrawal/GetAddAccount")
    Call<ResponseObj<BaseObj>> addAlipayAccount(@QueryMap Map<String, String> map);

    //删除银行卡账户
    @GET("api/CashWithdrawal/GetDelAccount")
    Call<ResponseObj<BaseObj>> deleteAccount(@QueryMap Map<String, String> map);

    //删除支付宝账户
    @GET("api/CashWithdrawal/GetDelAlipayAccount")
    Call<ResponseObj<BaseObj>> deleteAliAccount(@QueryMap Map<String, String> map);

    //设置默认账户
    @GET("api/CashWithdrawal/GetEditDefalut")
    Call<ResponseObj<BaseObj>> setDefaultAccount(@QueryMap Map<String, String> map);

    //获取银行列表
    @GET("api/CashWithdrawal/GetBankList")
    Call<ResponseObj<List<BankObj>>> getBankList(@QueryMap Map<String, String> map);

    //添加账户
    @GET("api/CashWithdrawal/GetAddAccount")
    Call<ResponseObj<BaseObj>> addAccount(@QueryMap Map<String, String> map);

    //账户余额提现申请
    @GET("api/CashWithdrawal/GetWithdrawals")
    Call<ResponseObj<BaseObj>> TiXian(@QueryMap Map<String, String> map);

    //佣金提现申请
    @GET("api/CashWithdrawal/GetCommissionWithdrawals")
    Call<ResponseObj<BaseObj>> yongJinTiXian(@QueryMap Map<String, String> map);

    //获取签到信息
    @GET("api/HomePage/GetSignInList")
    Call<ResponseObj<QianDaoObj>> getQianDaoList(@QueryMap Map<String, String> map);

    //签到
    @GET("api/HomePage/GetSignIn")
    Call<ResponseObj<BaseObj>> qianDao(@QueryMap Map<String, String> map);

    //我的收藏
    @GET("api/UserBase/GetMyCollectionList")
    Call<ResponseObj<List<MyCollectObj>>> getMyCollectionList(@QueryMap Map<String, String> map);

    //我的商铺收藏
    @GET("api/Store/GetAttentionStore")
    Call<ResponseObj<List<StoreCollectObj>>> getMyStoreCollectionList(@QueryMap Map<String, String> map);

    //取消收藏
    @GET("api/UserBase/GetCollection")
    Call<ResponseObj<BaseObj>> cancelCollection(@QueryMap Map<String, String> map);

    //订单列表
    @GET("api/UserBase/GetOrderList")
    Call<ResponseObj<List<OrderListObj>>> getOrderList(@QueryMap Map<String, String> map);

    //删除订单
    @GET("api/UserBase/GetDelOrder")
    Call<ResponseObj<BaseObj>> deleteOrder(@QueryMap Map<String, String> map);

    //订单列表-取消订单
    @GET("api/UserBase/GetCancelOrder")
    Call<ResponseObj<BaseObj>> cancelOrder(@QueryMap Map<String, String> map);

    //验证订单
    @GET("api/GoodsClassiFication/GetPaymentVerification")
    Call<ResponseObj<BaseObj>> yanZhengOrder(@QueryMap Map<String, String> map);

    //订单状态数量
    @GET("api/UserBase/GetOrderCount")
    Call<ResponseObj<OrderNumObj>> getOrderNum(@QueryMap Map<String, String> map);

    //订单详情
    @GET("api/UserBase/GetOrderMore")
    Call<ResponseObj<OrderDetailObj>> getOrderDetail(@QueryMap Map<String, String> map);

    //订单详情-物流信息
    @GET("api/UserBase/GetLogisticsInformation")
    Call<ResponseObj<WuLiuObj>> getOrderWuLiuDetail(@QueryMap Map<String, String> map);

    //退款原因
    @GET("api/UserBase/GetRefundReason")
    Call<ResponseObj<List<TuiKuanReasonObj>>> getTuiKuanReason(@QueryMap Map<String, String> map);

    //退款金额
    @GET("api/UserBase/GetApplyForRefundsMoney")
    Call<ResponseObj<TuiKuanMoneyObj>> getTuiKuanMoney(@QueryMap Map<String, String> map);

    //退款申请
    @GET("api/UserBase/GetApplyForRefunds")
    Call<ResponseObj<BaseObj>> tuiKuanCommit(@QueryMap Map<String, String> map);

    //确认收货
    @GET("api/UserBase/GetConfirmReceiptGoods")
    Call<ResponseObj<BaseObj>> sureOrder(@QueryMap Map<String, String> map);

    //发布评价-获取图片
    @GET("api/UserBase/GetPublishAppraiseImg")
    Call<ResponseObj<List<FaBiaoPingJiaObj>>> getPingJiaImg(@QueryMap Map<String, String> map);

    //发布评价
    @POST("api/UserBase/PostPublishAppraise")
    Call<ResponseObj<BaseObj>> pingJia(@QueryMap Map<String, String> map,@Body FaBiaoPingJiaBodyItem item);

    //退换货列表
    @GET("api/UserBase/GetMyApplyForRefunds")
    Call<ResponseObj<List<TuiHuanHuoObj>>> getTuiHuanHuoList(@QueryMap Map<String, String> map);

    //取消退换货申请
    @GET("api/UserBase/GetCancelRequest")
    Call<ResponseObj<BaseObj>> cancelTuiHuanHuo(@QueryMap Map<String, String> map);

    //我的分销
    @GET("api/UserBase/GetMyDistribution")
    Call<ResponseObj<FenXiaoObj>> getFenXiao(@QueryMap Map<String, String> map);

    //我的下级
    @GET("api/UserBase/GetMyLowerLevel")
    Call<ResponseObj<List<XiaJiObj>>> getMyXiaJi(@QueryMap Map<String, String> map);

    //邀请码
    @GET("api/UserBase/GetMyDistributionYard")
    Call<ResponseObj<YaoQingCodeObj>> getYaoQingCode(@QueryMap Map<String, String> map);

    //我的佣金
    @GET("api/UserBase/GetMyCommission")
    Call<ResponseObj<YongJinObj>> getYongJin(@QueryMap Map<String, String> map);

    //退换货详情
    @GET("api/UserBase/GetMyApplyForRefundsDetails")
    Call<ResponseObj<TuiHuoDetailObj>> tuiHuanHuoDetail(@QueryMap Map<String, String> map);

    //我的评价-列表
    @GET("api/UserBase/GetMyAppraise")
    Call<ResponseObj<List<MyEvaluateObj>>> getMyEvaluate(@QueryMap Map<String, String> map);

    //我的评价-详情
    @GET("api/UserBase/GetAppraiseDetail")
    Call<ResponseObj<EvaluateDetailObj>> getEvaluateDetail(@QueryMap Map<String, String> map);

    //追加评价
    @POST("api/UserBase/PostAdditionalEvaluation")
    Call<ResponseObj<BaseObj>> againEvaluate(@QueryMap Map<String, String> map, @Body AgainEvaluateItem item);

    //账户充值-显示余额
    @GET("api/UserBase/GetWithdShow")
    Call<ResponseObj<BaseObj>> getAccountBalance(@QueryMap Map<String, String> map);

    //账户充值
    @GET("api/CashWithdrawal/GetCreateOrder")
    Call<ResponseObj<MakeAccountOrderObj>> accountChongZhi(@QueryMap Map<String, String> map);

    //分享
    @GET("api/Lib/GetShareInformation")
    Call<ResponseObj<FenXiangObj>> fenXiang(@QueryMap Map<String, String> map);

    //我的社区
    @GET("api/UserBase/GetMyCommunity")
    Call<ResponseObj<List<MySheQuObj>>> getMySheQu(@QueryMap Map<String, String> map);

    //商铺搜索列表
    @GET("api/Store/GetQueryStore")
    Call<ResponseObj<List<StoreCollectObj>>> getShopList(@QueryMap Map<String, String> map);

}
