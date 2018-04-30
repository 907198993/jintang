package com.sk.jintang.module.orderclass.activity;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.orderclass.network.ApiRequest;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by administartor on 2017/9/2.
 */

public class JiuJinFaHuoActivity extends BaseActivity {
    @BindView(R.id.iv_jiu_jin)
    ImageView iv_jiu_jin;
    @Override
    protected int getContentView() {
        setAppTitle("就近发货");
        return R.layout.act_jiu_jin_fa_huo;
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
        map.put("rnd",getRnd());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getJiuJinFaHuo(map, new MyCallBack<BaseObj>(mContext,pl_load) {
            @Override
            public void onSuccess(BaseObj obj) {
                Glide.with(mContext).load(obj.getImg()).error(R.color.c_press).into(iv_jiu_jin);
            }
        });

    }

    @Override
    protected void onViewClick(View v) {

    }
}
