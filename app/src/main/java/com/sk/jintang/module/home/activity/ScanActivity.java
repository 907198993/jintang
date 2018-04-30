/*
package com.sk.yangyu.module.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.androidtools.StatusBarUtils;
import com.github.baseclass.utils.ActUtils;
import com.github.baseclass.view.MyDialog;
import com.github.customview.MyLinearLayout;
import com.google.zxing.Result;
import com.google.zxing.client.android.AutoScannerView;
import com.google.zxing.client.android.BaseCaptureActivity;
import com.sk.yangyu.GetSign;
import com.sk.yangyu.R;
import com.sk.yangyu.base.MyCallBack;
import com.sk.yangyu.module.home.network.ApiRequest;
import com.sk.yangyu.module.home.network.response.GoodsIdObj;
import com.sk.yangyu.module.orderclass.Constant;
import com.sk.yangyu.module.orderclass.activity.GoodsDetailActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

*/
/**
 * Created by administartor on 2017/9/1.
 *//*


public class ScanActivity extends BaseCaptureActivity    {
    private View status_bar;
    @BindView(R.id.iv_scan_back)
    ImageView iv_scan_back;
//    @BindView(R.id.zxingview)
//    QRCodeView mQRCodeView;
    @BindView(R.id.ll_scan_shou_dian)
    MyLinearLayout ll_scan_shou_dian;
    @BindView(R.id.ll_scan_top)
    LinearLayout ll_scan_top;
    private MyDialog myDialog;
    private Activity mContext;

    @OnClick({R.id.iv_scan_back})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.iv_scan_back:
                finish();
            break;
        }
    }


    private static final String TAG = ScanActivity.class.getSimpleName();

    private SurfaceView surfaceView;
    private AutoScannerView autoScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        setContentView(R.layout.act_scan);
        ButterKnife.bind(this);
        if(Build.VERSION.SDK_INT< Build.VERSION_CODES.LOLLIPOP){
//            StatusBarUtils.setTransparent(this);
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }

        if(null!=findViewById(R.id.status_bar)){
            status_bar = findViewById(R.id.status_bar);
            int statusBarHeight = StatusBarUtils.getStatusBarHeight(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.height=statusBarHeight;
            status_bar.setLayoutParams(layoutParams);
            status_bar.setBackgroundColor(ContextCompat.getColor(mContext,R.color.border_e52));
        }


        surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        autoScannerView = (AutoScannerView) findViewById(R.id.autoscanner_view);


        ll_scan_top.setPadding(0, StatusBarUtils.getStatusBarHeight(mContext), 0, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoScannerView.setCameraManager(cameraManager);
    }

    @Override
    public SurfaceView getSurfaceView() {
        return (surfaceView == null) ? (SurfaceView) findViewById(R.id.preview_view) : surfaceView;
    }

    @Override
    public void dealDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
        Log.i(TAG, "dealDecode ~~~~~ " + rawResult.getText() + " " + barcode + " " + scaleFactor);
//        playBeepSoundAndVibrate(true, false);
        playBeepSoundAndVibrate();
//        Toast.makeText(this, rawResult.getText(), Toast.LENGTH_LONG).show();
//        对此次扫描结果不满意可以调用
//
        Map<String,String> map=new HashMap<String,String>();
        map.put("bar_code",rawResult.getText());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getGoodsIdForScabCode(map, new MyCallBack<GoodsIdObj>(mContext) {
            @Override
            public void onSuccess(GoodsIdObj obj) {
                Intent intent=new Intent();
                intent.putExtra(Constant.IParam.goodsId,obj.getGoods_id());
                ActUtils.STActivity(mContext,intent,GoodsDetailActivity.class);
                finish();
            }
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                reScan();
            }
        });
    }
}
*/
