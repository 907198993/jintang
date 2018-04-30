package com.sk.jintang.module.my.activity;

import android.view.View;
import android.widget.TextView;

import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.Constant;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.response.MessageDetailObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by administartor on 2017/8/4.
 */

public class MessageDetailActivity extends BaseActivity {
    @BindView(R.id.tv_msgdetail_title)
    TextView tv_msgdetail_title;
    @BindView(R.id.tv_msgdetail_content)
    TextView tv_msgdetail_content;
    private String msgId;
    @Override
    protected int getContentView() {
        setAppTitle("我的消息");
        return R.layout.act_message_detail;
    }

    @Override
    protected void initView() {
        msgId = getIntent().getStringExtra(Constant.IParam.msgId);
    }

    @Override
    protected void initData() {
        showProgress();
        getData();
    }
    private void getData() {
        Map<String,String> map=new HashMap<String,String>();
        map.put("news_id",msgId);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getMsgDetail(map, new MyCallBack<MessageDetailObj>(mContext) {
            @Override
            public void onSuccess(MessageDetailObj obj) {
                tv_msgdetail_title.setText(obj.getTitle());
                tv_msgdetail_content.setText(obj.getContent());
            }
        });
    }
    @Override
    protected void onViewClick(View v) {

    }
}
