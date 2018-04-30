package com.sk.jintang.module.my.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.github.androidtools.PhoneUtils;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseFragment;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.adapter.VouchersAdapter;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.response.VouchersObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/8/8.
 */

public class VouchersFragment extends BaseFragment implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_vouchers)
    RecyclerView rv_vouchers;

    VouchersAdapter adapter;
    private String vouchersType;
    private boolean selectVoucher;

    @Override
    protected int getContentView() {
        return R.layout.frag_vouchers;
    }
    @Override
    protected void initView() {
        vouchersType=getArguments().getString("type");
        adapter=new VouchersAdapter(mContext, R.layout.item_my_vouchers,pageSize);
        adapter.setOnLoadMoreListener(this);
        adapter.setType(vouchersType);
        adapter.setClickListener(new LoadMoreAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                if(selectVoucher){
//                    VouchersNumObj vouchersObj = adapter.getList().get(i);
//                    RxBus.getInstance().post(new VoucherEvent(vouchersObj));
                }
            }
        });
        rv_vouchers.setLayoutManager(new LinearLayoutManager(mContext));
        rv_vouchers.addItemDecoration(getItemDivider(PhoneUtils.dip2px(mContext,5)));
        rv_vouchers.setAdapter(adapter);
        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(1,false);
            }
        });
    }

    public static VouchersFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString("type", type);
        VouchersFragment fragment = new VouchersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        showProgress();
        getData(1,false);
    }
    private void getData(int page, boolean isLoad) {
        Log.i("==","=="+getArguments().getString("type"));
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("type",getArguments().getString("type"));
        map.put("page",page+"");
        map.put("pagesize",pageSize+"");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getVouchersList(map, new MyCallBack<List<VouchersObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(List<VouchersObj> list) {
                if(isLoad){
                    pageNum++;
                    adapter.addList(list,true);
                }else{
                    pageNum=2;
                    adapter.setList(list,true);
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

    public void setSelectVoucher(boolean selectVoucher) {
        this.selectVoucher=selectVoucher;
    }
}
