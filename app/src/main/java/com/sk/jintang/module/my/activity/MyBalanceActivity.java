package com.sk.jintang.module.my.activity;

import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.customview.MyTextView;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.response.BalanceObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/9/7.
 */

public class MyBalanceActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener {
    @BindView(R.id.rv_account)
    RecyclerView rv_account;

    LoadMoreAdapter adapter;
    @BindView(R.id.tv_account_amount)
    MyTextView tv_account_amount;
    @BindView(R.id.tv_account_chongzhi)
    TextView tv_account_chongzhi;
    @BindView(R.id.tv_account_tixian)
    TextView tv_account_tixian;
    @BindView(R.id.nsv)
    NestedScrollView nsv;

    @Override
    protected int getContentView() {
        setAppTitle("我的账户");
        return R.layout.act_my_balance;
    }

    @Override
    protected void initView() {
        adapter = new LoadMoreAdapter<BalanceObj.BalanceDetailListBean>(mContext, R.layout.item_yang_sheng_dou, pageSize,nsv) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, BalanceObj.BalanceDetailListBean bean) {
                holder.setText(R.id.tv_yangshengdou_date,bean.getAdd_time())
                        .setText(R.id.tv_yangshengdou_type,bean.getRemark());
                TextView textView = holder.getTextView(R.id.tv_yangshengdou_amount);
                double value = bean.getValue();
                if(value>=0){
                    textView.setTextColor(getResources().getColor(R.color.green));
                    textView.setText(""+value);
                }else{
                    textView.setTextColor(getResources().getColor(R.color.red));
                    textView.setText(""+value);
                }
            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_account.setNestedScrollingEnabled(false);
        rv_account.setLayoutManager(new LinearLayoutManager(mContext));
        rv_account.addItemDecoration(getItemDivider());
        rv_account.setAdapter(adapter);

        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(1,false);
            }
        });
    }

    @Override
    protected void initData() {
        showLoading();
        getData(1,false);
    }

    private void getData(int page, boolean isLoad) {
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("page",page+"");
        map.put("pagesize",pageSize+"");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getBalanceList(map, new MyCallBack<BalanceObj>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(BalanceObj obj) {
                tv_account_amount.setText(obj.getBalance()+"");
                if(!TextUtils.isEmpty(getSStr(tv_account_amount))){
                    if(getSStr(tv_account_amount).length()<=7){
                        tv_account_amount.setTextSize(18);
                    }else{
                        int size = 18 - (getSStr(tv_account_amount).length() - 7);
                        tv_account_amount.setTextSize(size);
                    }
                }

                if(isLoad){
                    pageNum++;
                    adapter.addList(obj.getBalance_detail_list(),true);
                }else{
                    pageNum=2;
                    adapter.setList(obj.getBalance_detail_list(),true);
                }
            }
        });
    }

    @Override
    protected void onViewClick(View v) {

    }

    @Override
    public void loadMore() {
        getData(pageNum,true);
    }

    @OnClick({R.id.tv_account_chongzhi, R.id.tv_account_tixian})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_account_chongzhi:
                STActivityForResult(AccountChongZhiActivity.class,1000);
                break;
            case R.id.tv_account_tixian:
                STActivityForResult(TiXianActivity.class,1000);
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 1000:
                    initData();
                    break;
            }
        }

    }
}
