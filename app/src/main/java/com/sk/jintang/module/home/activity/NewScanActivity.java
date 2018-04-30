package com.sk.jintang.module.home.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.github.androidtools.StatusBarUtils;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.home.network.ApiRequest;
import com.sk.jintang.module.home.network.response.GoodsIdObj;
import com.sk.jintang.module.my.Constant;
import com.sk.jintang.module.orderclass.activity.GoodsDetailActivity;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/10/24.
 */

public class NewScanActivity extends BaseActivity {
    @BindView(R.id.ll_scan_top)
    LinearLayout ll_scan_top;
    private boolean isLight;
    private CaptureFragment captureFragment;

    @Override
    protected int getContentView() {
        return R.layout.act_scan_new;
    }

    @Override
    protected void initView() {
        ll_scan_top.setPadding(0, StatusBarUtils.getStatusBarHeight(mContext), 0, 0);
        CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
            @Override
            public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                Log.i("===","==="+result);
                /*Message restartMessage = new Message();
                restartMessage.what = com.uuzuche.lib_zxing.R.id.restart_preview;
                captureFragment.getHandler().sendMessageDelayed(restartMessage, 5000);*/
                /*Intent resultIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
                bundle.putString(CodeUtils.RESULT_STRING, result);
                resultIntent.putExtras(bundle);
                setResult(RESULT_OK, resultIntent);
                finish();*/
                Map<String,String> map=new HashMap<String,String>();
                map.put("bar_code",result);
                map.put("sign", GetSign.getSign(map));
                ApiRequest.getGoodsIdForScabCode(map, new MyCallBack<GoodsIdObj>(mContext) {
                    @Override
                    public void onSuccess(GoodsIdObj obj) {
                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.goodsId,obj.getGoods_id());
                        STActivity(intent,GoodsDetailActivity.class);
                        finish();
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Message restartMessage = new Message();
                        restartMessage.what = com.uuzuche.lib_zxing.R.id.restart_preview;
                        captureFragment.getHandler().sendMessageDelayed(restartMessage, 5000);
                    }
                });
            }

            @Override
            public void onAnalyzeFailed() {
                /*Intent resultIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
                bundle.putString(CodeUtils.RESULT_STRING, "");
                resultIntent.putExtras(bundle);
                setResult(RESULT_OK, resultIntent);
                finish();*/
            }
        };
        /**
         * 执行扫面Fragment的初始化操作
         */
        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.scan);

        captureFragment.setAnalyzeCallback(analyzeCallback);
        /**
         * 替换我们的扫描控件
         */getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.iv_scan_back,R.id.ll_scan_shou_dian})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.iv_scan_back:
                finish();
                break;
            case R.id.ll_scan_shou_dian:
                CodeUtils.isLightEnable(!isLight);
                isLight=!isLight;
                break;
        }
    }

}
