package com.sk.jintang.module.orderclass.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.orderclass.network.ApiRequest;
import com.sk.jintang.module.orderclass.network.response.GetVouchersObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/9/2.
 */

public class GetVouchersActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_get_vouchers)
    RecyclerView rv_get_vouchers;

    LoadMoreAdapter adapter;
    @Override
    protected int getContentView() {
        setAppTitle("领券中心");
        return R.layout.act_get_vouchers;
    }

    @Override
    protected void initView() {
        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData();
            }
        });
        adapter=new LoadMoreAdapter<GetVouchersObj>(mContext,R.layout.item_get_vouchers,0) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, GetVouchersObj bean) {
                LinearLayout ll_vouchers_gb = (LinearLayout) holder.getView(R.id.ll_vouchers_gb);
                holder.setText(R.id.tv_vouchers_money,bean.getFace_value()+"")
                        .setText(R.id.tv_vouchers_title,bean.getTitle())
                        .setText(R.id.tv_vouchers_available,"满"+bean.getAvailable()+"元可用")
                        .setText(R.id.tv_vouchers_date,bean.getDeadline());

                ImageView iv_vouchers_type = holder.getImageView(R.id.iv_vouchers_type);
                TextView tv_vouchers_get = holder.getTextView(R.id.tv_vouchers_get);
                if(bean.getStatus()==0){//未领取
                    ll_vouchers_gb.setBackgroundResource(R.drawable.order11);
                    tv_vouchers_get.setVisibility(View.VISIBLE);
                    iv_vouchers_type.setVisibility(View.GONE);
                }else{
                    ll_vouchers_gb.setBackgroundResource(R.drawable.order10);
                    tv_vouchers_get.setVisibility(View.GONE);
                    iv_vouchers_type.setVisibility(View.VISIBLE);
                }
                holder.getTextView(R.id.tv_vouchers_get).setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        showLoading();
                        getVouchers(bean.getId());
                    }
                });
            }
        };

        rv_get_vouchers.setLayoutManager(new LinearLayoutManager(mContext));
        rv_get_vouchers.addItemDecoration(getItemDivider(PhoneUtils.dip2px(mContext,10)));
        rv_get_vouchers.setAdapter(adapter);
    }

    private void getVouchers(int id) {
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("coupons_id",id+"");
        map.put("sign",GetSign.getSign(map));
        ApiRequest.getVouchers(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                getData();
            }
        });

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
        ApiRequest.getVouchersList(map, new MyCallBack<List<GetVouchersObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(List<GetVouchersObj> list) {
                adapter.setList(list,true);
            }
        });

    }

    @Override
    protected void onViewClick(View v) {

    }

    @Override
    public void loadMore() {

    }
}
