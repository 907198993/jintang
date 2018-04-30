package com.sk.jintang.module.my.activity;

import android.view.View;
import android.widget.TextView;

import com.github.customview.MyTextView;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.response.YaoQingCodeObj;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/9/6.
 */

public class YaoQingActivity extends BaseActivity {
    @BindView(R.id.tv_yaoqing_content)
    TextView tv_yaoqing_content;
    @BindView(R.id.tv_yaoqing_code)
    MyTextView tv_yaoqing_code;

    @Override
    protected int getContentView() {
        setAppTitle("邀请有礼");
        return R.layout.act_yao_qing;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        showProgress();
        getData();
    }

    private void getData() {
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getYaoQingCode(map, new MyCallBack<YaoQingCodeObj>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(YaoQingCodeObj obj) {
                tv_yaoqing_content.setText(obj.getContent());
                tv_yaoqing_code.setText(obj.getDistribution_yard());
            }
        });

    }

    @OnClick({R.id.iv_yaoqing_wx, R.id.iv_yaoqing_friend, R.id.iv_yaoqing_qq, R.id.iv_yaoqing_qzone,})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.iv_yaoqing_wx:
                if (!UMShareAPI.get(mContext).isInstall(mContext, SHARE_MEDIA.WEIXIN)) {
                    showMsg("请安装微信之后再试");
                    return;
                }
                fenXiang(SHARE_MEDIA.WEIXIN,"0");
                break;
            case R.id.iv_yaoqing_friend:
                if (!UMShareAPI.get(mContext).isInstall(mContext, SHARE_MEDIA.WEIXIN)) {
                    showMsg("请安装微信之后再试");
                    return;
                }
                fenXiang(SHARE_MEDIA.WEIXIN_CIRCLE,"0");
                break;
            case R.id.iv_yaoqing_qq:
                if (!UMShareAPI.get(mContext).isInstall(mContext, SHARE_MEDIA.QQ)) {
                    showMsg("请安装QQ之后再试");
                    return;
                }
                fenXiang(SHARE_MEDIA.QQ,"0");
                break;
            case R.id.iv_yaoqing_qzone:
                if (!UMShareAPI.get(mContext).isInstall(mContext, SHARE_MEDIA.QQ)) {
                    showMsg("请安装QQ之后再试");
                    return;
                }
                fenXiang(SHARE_MEDIA.QZONE,"0");
                break;

        }
    }
}
