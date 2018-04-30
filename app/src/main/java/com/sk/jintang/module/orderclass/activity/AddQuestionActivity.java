package com.sk.jintang.module.orderclass.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.Constant;
import com.sk.jintang.module.orderclass.network.ApiRequest;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2018/2/6.
 */

public class AddQuestionActivity extends BaseActivity {
    @BindView(R.id.et_goods_question_fabu)
    EditText et_goods_question_fabu;
    @BindView(R.id.tv_goods_question_fabu)
    TextView tv_goods_question_fabu;
    private String goodsId;

    @Override
    protected int getContentView() {
        setAppTitle("商品提问");
        return R.layout.act_add_question;
    }

    @Override
    protected void initView() {

        goodsId = getIntent().getStringExtra(Constant.IParam.goodsId);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_goods_question_fabu})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_goods_question_fabu:
                String content=getSStr(et_goods_question_fabu);
                if(TextUtils.isEmpty(content.trim())){
                    showMsg("请输入内容");
                    return;
                }
                commit(content);
            break;
        }
    }

    private void commit(String content) {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("type","1");
        map.put("goods_question_id",goodsId);
        map.put("content",content);
        map.put("sign",GetSign.getSign(map));
        ApiRequest.faBuQuestion(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                setResult(RESULT_OK);
                finish();
            }
        });

    }
}
