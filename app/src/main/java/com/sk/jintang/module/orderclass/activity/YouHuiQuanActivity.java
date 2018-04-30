package com.sk.jintang.module.orderclass.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.androidtools.SPUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.orderclass.Constant;
import com.sk.jintang.module.orderclass.network.ApiRequest;
import com.sk.jintang.module.orderclass.network.response.YouHuiQuanObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/9/19.
 */

public class YouHuiQuanActivity extends BaseActivity {
    @BindView(R.id.rv_you_hui_quan)
    RecyclerView rv_you_hui_quan;
    BaseRecyclerAdapter adapter;
    private double money;
    private int youHuiNum=0;
    @Override
    protected int getContentView() {
        setAppTitle("优惠券");
        return R.layout.act_you_hui_quan;
    }

    @Override
    protected void initView() {
        money =getIntent().getDoubleExtra(Constant.IParam.orderMoney,0.0);
        adapter=new BaseRecyclerAdapter<YouHuiQuanObj>(mContext,R.layout.item_my_vouchers) {
            @Override
            public void bindData(RecyclerViewHolder holder, int i, YouHuiQuanObj bean) {
                holder.setText(R.id.tv_vouchers_available,String.format("满%s元可用",bean.getAvailable()+""))
                        .setText(R.id.tv_vouchers_date,bean.getBegin_time()+"~"+bean.getEnd_time())
                        .setText(R.id.tv_vouchers_money,bean.getFace_value()+"");
                LinearLayout ll_vouchers_gb = (LinearLayout) holder.getView(R.id.ll_vouchers_gb);
                TextView tv_vouchers_yong = (TextView) holder.getView(R.id.tv_vouchers_yong);
                ImageView iv_vouchers_type = (ImageView) holder.getView(R.id.iv_vouchers_type);

                ll_vouchers_gb.setBackgroundResource(R.drawable.order11);
                tv_vouchers_yong.setVisibility(View.VISIBLE);
                iv_vouchers_type.setVisibility(View.GONE);

                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.youHuiId,bean.getCoupons_id());
                        intent.putExtra(Constant.IParam.youHuiMoney,bean.getFace_value());
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                });
            }
        };

        rv_you_hui_quan.setLayoutManager(new LinearLayoutManager(mContext));
        rv_you_hui_quan.addItemDecoration(getItemDivider());
        rv_you_hui_quan.setAdapter(adapter);


    }

    @Override
    protected void initData() {
        showProgress();
        getData();
    }

    private void getData() {
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("money",money+"");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getYouHuiQuan(map, new MyCallBack<List<YouHuiQuanObj>>(mContext,pl_load) {
            @Override
            public void onSuccess(List<YouHuiQuanObj> list) {
                if(notEmpty(list)){
                    youHuiNum=list.size();
                }
                SPUtils.setPrefString(mContext,Constant.IParam.youHuiNum,youHuiNum+"");
                adapter.setList(list);
            }
        });

    }

    @OnClick({R.id.tv_no_you_hui})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_no_you_hui:
                Intent intent=new Intent();
                intent.putExtra(Constant.IParam.youHuiId,0);
                intent.putExtra(Constant.IParam.youHuiMoney,0);
                setResult(RESULT_OK,intent);
                finish();
            break;
        }
    }
}
