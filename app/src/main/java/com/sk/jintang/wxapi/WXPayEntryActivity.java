package com.sk.jintang.wxapi;


import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;

import com.github.androidtools.SPUtils;
import com.github.androidtools.ToastUtils;
import com.github.baseclass.rx.RxBus;
import com.sk.jintang.Config;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.module.my.event.PayEvent;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    /*@BindView(R.id.tv_pay_result)
    TextView tv_pay_result;
    @BindView(R.id.tv_pay_txt)
    TextView tv_pay_txt;*/

    private IWXAPI api;
    private Intent intent;


    @Override
    protected int getContentView() {
        return 0;
    }

    @Override
    protected void initView() {
        api = WXAPIFactory.createWXAPI(this, Config.weixing_id);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void initData() {

    }

//    @OnClick({R.id.tv_pay_lookorder, R.id.tv_pay_back})
    protected void onViewClick(View view) {
        switch (view.getId()) {
            /*case R.id.tv_pay_lookorder:
                finish();
                break;
            case R.id.tv_pay_back:
                intent = new Intent(Config.backHome);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                STActivity(intent,ConversationActivity.class);
                break;*/
        }
    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent(Config.Bro.paySuccessBro));//finish订单页面
        Log.e("tag", "onPayFinish, errCode = " + resp.errCode + "---" + resp.getType());
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            Log.e("tag", "支付结果:" + resp.errCode);
            boolean prefBoolean = SPUtils.getPrefBoolean(mContext, Config.accountChongZhi, false);
            if(prefBoolean){
                SPUtils.removeKey(mContext, Config.accountChongZhi);
                if (resp.errCode == 0) {// 支付成功
                    RxBus.getInstance().post(new PayEvent());
                    ToastUtils.showToast(this, "充值成功");
                } else if (resp.errCode == -2) {
                    ToastUtils.showToast(this, "充值已取消");
                } else if (resp.errCode == -1) {
                    ToastUtils.showToast(this, "充值失败");
                }
            }else{
                intent=new Intent();
                intent.putExtra("result",resp.errCode);
                STActivity(intent,PayResultActivity.class);
            }
        }
		finish();
    }


}



/*extends Activity implements IWXAPIEventHandler {

	private static final String TAG = "WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pay_success);
    	api = WXAPIFactory.createWXAPI(this, Config.weixing_id);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Log.d("onNewIntent===", "onNewIntent==== ");
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq baseReq) {

		Log.d("onResp===", "baseReq, errCode = " + baseReq.getType());
	}

	@Override
	public void onResp(BaseResp baseResp) {
		Log.d("onResp===", "onPayFinish, errCode = " + baseResp.errCode);
		if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			Log.d("onResp22===", "onPayFinish, errCode = " + baseResp.errCode);
		}
	}
}*/
