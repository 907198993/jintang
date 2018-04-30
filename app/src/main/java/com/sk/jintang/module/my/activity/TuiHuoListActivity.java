package com.sk.jintang.module.my.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.github.baseclass.view.MyDialog;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.Constant;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.response.TuiHuanHuoObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/9/6.
 */

public class TuiHuoListActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_tui_huo)
    RecyclerView rv_tui_huo;

    LoadMoreAdapter adapter;
    @Override
    protected int getContentView() {
        setAppTitle("我的退换货 ");
        return R.layout.act_tui_huo_list;
    }

    @Override
    protected void initView() {
        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(1,false);
            }
        });
        adapter=new LoadMoreAdapter<TuiHuanHuoObj>(mContext,R.layout.item_tui_huo,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, TuiHuanHuoObj bean) {
                RecyclerView rv_item_tui_huo = (RecyclerView) holder.getView(R.id.rv_item_tui_huo);
                holder.setText(R.id.tv_tuihuo_no,bean.getOrder_no())
                        .setText(R.id.tv_tuihuo_total_num,"共"+bean.getGoods_list_count()+"件商品")
                .setText(R.id.tv_tuihuo_total_price,"合计: ¥"+bean.getCombined());
                TextView tv_tuihuo_status = holder.getTextView(R.id.tv_tuihuo_status);
                TextView tv_tuihuo_cancel = holder.getTextView(R.id.tv_tuihuo_cancel);
                tv_tuihuo_cancel.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
                        mDialog.setMessage("是否确认取消?");
                        mDialog.setNegativeButton(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        mDialog.setPositiveButton(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                cancelTuiHuo(bean);
                            }
                        });
                        mDialog.create().show();
                    }
                });
                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.tuiHuoDetail,bean.getOrder_no());
                        STActivity(intent,TuiHuoDetailActivity.class);
                    }
                });
                View view = holder.getView(R.id.ll_tui_huan_huo);
                int refundStatus = bean.getRefund_status();
                switch (refundStatus){
                    case 1://退款中
                        tv_tuihuo_status.setText("退款中");
                        view.setVisibility(View.VISIBLE);
                    break;
                    case 2://退款成功
                        tv_tuihuo_status.setText("退款成功");
                        view.setVisibility(View.GONE);
                    break;
                    case 3://退款失败
                        tv_tuihuo_status.setText("退款失败");
                        view.setVisibility(View.VISIBLE);
                    break;
                }

                rv_item_tui_huo.setLayoutManager(new LinearLayoutManager(mContext));
                rv_item_tui_huo.addItemDecoration(getItemDivider());
                BaseRecyclerAdapter itemAdapter=new BaseRecyclerAdapter<TuiHuanHuoObj.GoodsListBean>(mContext,R.layout.item_tui_huo_goods) {
                    @Override
                    public void bindData(RecyclerViewHolder itemHolder, int i, TuiHuanHuoObj.GoodsListBean item) {
                        ImageView imageView = itemHolder.getImageView(R.id.iv_tuihuo);
                        Glide.with(mContext).load(item.getGoods_images()).error(R.color.c_press).into(imageView);
                        itemHolder.setText(R.id.tv_tuihuo_name,item.getGoods_name())
                                .setText(R.id.tv_tuihuo_guige,item.getGoods_specification())
                                .setText(R.id.tv_tuihuo_price,"¥"+item.getGoods_unitprice())
                                .setText(R.id.tv_tuihuo_num,"x"+item.getGoods_number());


                        itemHolder.itemView.setOnClickListener(new MyOnClickListener() {
                            @Override
                            protected void onNoDoubleClick(View view) {
                                Intent intent=new Intent();
                                intent.putExtra(Constant.IParam.tuiHuoDetail,bean.getOrder_no());
                                STActivity(intent,TuiHuoDetailActivity.class);
                            }
                        });
                    }
                };
                itemAdapter.setList(bean.getGoods_list());
                rv_item_tui_huo.setAdapter(itemAdapter);
            }
        };
        adapter.setOnLoadMoreListener(this);
        rv_tui_huo.setLayoutManager(new LinearLayoutManager(mContext));
        rv_tui_huo.addItemDecoration(getItemDivider(PhoneUtils.dip2px(mContext,10)));
        rv_tui_huo.setAdapter(adapter);
    }



    private void cancelTuiHuo(TuiHuanHuoObj bean) {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("order_no",bean.getOrder_no());
        map.put("sign",GetSign.getSign(map));
        ApiRequest.cancelTuiHuanHuo(map, new MyCallBack<BaseObj>(mContext,true) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                getData(1,false);
            }
        });

    }

    @Override
    protected void initData() {
        showProgress();
        getData(1,false);

    }

    private void getData(int page, boolean isLoad) {
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("page",page+"");
        map.put("pagesize",pageSize+"");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getTuiHuanHuoList(map, new MyCallBack<List<TuiHuanHuoObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(List<TuiHuanHuoObj> list) {
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
}
