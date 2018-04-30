package com.sk.jintang.wxapi;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import com.github.androidtools.MD5;
import com.github.androidtools.SPUtils;
import com.github.androidtools.ToastUtils;
import com.github.baseclass.view.Loading;
import com.sk.jintang.Config;
import com.sk.jintang.module.orderclass.bean.OrderBean;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class WXPay {
    private Context mContext;
    // IWXAPI 是第三方app和微信通信的openapi接口
    PayReq req;
    IWXAPI api;
    Map<String, String> resultunifiedorder;
    StringBuffer sb;

    public WXPay(Context context ) {
        api = WXAPIFactory.createWXAPI(context, Config.weixing_id);
        mContext=context;
    }
    OrderBean orderBean;
    public void pay(OrderBean bean) {
        orderBean=bean;
        req = new PayReq();
        sb = new StringBuffer();
        api.registerApp(Config.weixing_id);
//        ToastUtils toast = new ToastUtils(mContext);
        if (!api.isWXAppInstalled()) {
            ToastUtils.showToast(mContext,"亲,您还没有安装微信APP哦!");

        } else {
            if (!api.isWXAppSupportAPI()) {
                ToastUtils.showToast(mContext,"亲,当前版本不支持微信支付功能!");
            } else {
                GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
                getPrepayId.execute();
            }
        }
    }

    /**
     * 微信支付 生成预支付订单
     */
    private class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String, String>> {
        @Override
        protected void onPreExecute() {
            Loading.show(mContext);
        }
        @Override
        protected Map<String, String> doInBackground(Void... params) {

            String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
            String entity = genProductArgs();

            byte[] buf = Util.httpPost(url, entity);

            String content = new String(buf);
            Log.e("orion", content);
            Map<String, String> xml = decodeXml(content);
            return xml;
        }
        @Override
        protected void onPostExecute(Map<String, String> result) {
            Loading.dismissLoading();
            if(result.get("return_code")!=null&&"FAIL".equals(result.get("return_code"))){
                ToastUtils.showToast(mContext,"微信支付订单生成失败");
                return;
            }
            if(result.get("err_code_des")!=null){
                ToastUtils.showToast(mContext,result.get("err_code_des"));
            }
            Log.e("Tag", "生成预支付订单" + result.get("prepay_id"));
            sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
            resultunifiedorder = result;
            genPayReq(result.get("prepay_id"));
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }


    }
    // ///////////////////////////////////一//////////////////////////////////////

    /**
     * 生成支付参数 genPayReq(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
     *
     * @throws
     * @since 1.0.0
     */
    private void genPayReq(String prepayid) {
        req.appId =Config.weixing_id;
        req.partnerId =Config.weixing_mch_id;
        req.prepayId = prepayid;
        req.packageValue = "Sign=WXPay";
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());

        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = genAppSign(signParams);
        sb.append("sign\n" + req.sign + "\n\n");
        api.sendReq(req);
        Log.e("orion_genPayReq", signParams.toString());

    }

    /**
     * 生成随机字符串 genNonceStr(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
     *
     * @return String
     * @throws
     * @since 1.0.0
     */
    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Config.weixing_miyao);

        this.sb.append("sign str\n" + sb.toString() + "\n\n");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("orion_genAppSign", appSign);
        return appSign;
    }
    public double getPrice(double num){
        BigDecimal b = new BigDecimal(num);
        double result = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
    }
    // ///////////////////////////////二/////////////////////////////////////////
    private String genProductArgs() {
        String outTradeNo=genOutTradNo();
        String notifyUrl = SPUtils.getPrefString(mContext,Config.payType_WX,null);
        Log.i("====","===="+((int)getPrice(orderBean.totalFee))+""+"");
        StringBuffer xml = new StringBuffer();
        try {
            String nonceStr = genNonceStr();
            xml.append("</xml>");
            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("appid", Config.weixing_id));
            packageParams.add(new BasicNameValuePair("body", orderBean.body));
            packageParams.add(new BasicNameValuePair("mch_id", Config.weixing_mch_id));
            packageParams.add(new BasicNameValuePair("nonce_str",orderBean.nonceStr));
            packageParams.add(new BasicNameValuePair("notify_url", notifyUrl));
            packageParams.add(new BasicNameValuePair("out_trade_no",orderBean.out_trade_no));
            packageParams.add(new BasicNameValuePair("spbill_create_ip", orderBean.IP));
//            if(BuildConfig.DEBUG){
//                packageParams.add(new BasicNameValuePair("total_fee", 1+""));
//            }else{
                packageParams.add(new BasicNameValuePair("total_fee", ((int)getPrice(orderBean.totalFee))+""));
//            }
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));
            // 微信支付金额
             /*  packageParams.add(new BasicNameValuePair("total_fee", "1"));
               PreferenceUtils.setPrefString(mContext, "Sum_money", "0.01");*/
//          PreferenceUtils.setPrefString(mContext, "Sum_money", count_price);

            String sign = genPackageSign(packageParams);
            packageParams.add(new BasicNameValuePair("sign", sign));
            String xmlstring = toXml(packageParams);
            // 改变拼接之后xml字符串格式
            return new String(xmlstring.toString().getBytes(), "ISO8859-1");
        } catch (Exception e) {
            Log.e("tag_e", "genProductArgs fail, ex = " + e.getMessage());
            return null;
        }
    }

    /**
     * 获取商户订单号 genOutTradNo(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
     *
     * @return String
     * @throws
     * @since 1.0.0
     */
    private String genOutTradNo() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    /**
     * 生成签名 genPackageSign(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
     *
     * @param params
     * @return String
     * @throws
     * @since 1.0.0
     */
    private String genPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Config.weixing_miyao);
        Log.e("url", sb.toString());
        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("orion_genPackageSign", packageSign);
        return packageSign;
    }

    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");

            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");
        Log.e("orion_toXml", sb.toString());
        return sb.toString();
    }

    // //////////////////////////////////////////////////////////////////////////////
    public Map<String, String> decodeXml(String content) {
        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if ("xml".equals(nodeName) == false) {
                            // 实例化student对象
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }
            return xml;
        } catch (Exception e) {
            Log.e("orion_decodeXml_e", e.toString());
        }
        return null;
    }
}
