package com.sk.jintang.module.my.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.view.MyDialog;
import com.sk.jintang.Config;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseFragment;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.response.MyCollectObj;
import com.sk.jintang.module.orderclass.Constant;
import com.sk.jintang.module.orderclass.activity.GoodsDetailActivity;
import com.sk.jintang.module.orderclass.activity.GoodsDetailTuanGouActivity;
import com.sk.jintang.module.orderclass.activity.GoodsDetailXianShiActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class MyCollectGoodsFragment extends BaseFragment  implements LoadMoreAdapter.OnLoadMoreListener{

    @BindView(R.id.rv_my_collect)
    RecyclerView rv_my_collect;

    LoadMoreAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.act_search_store_result;
    }

    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter<MyCollectObj>(mContext,R.layout.item_my_collect,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, MyCollectObj bean) {
               /* TextView tv_my_collect_yuanjia = holder.getTextView(R.id.tv_my_collect_yuanjia);
                tv_my_collect_yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                tv_my_collect_yuanjia.getPaint().setAntiAlias(true);
*/
                holder.setText(R.id.tv_my_collect_name,bean.getGoods_name())
                        .setText(R.id.tv_my_collect_price,"¥"+bean.getPrice());
                ImageView imageView = holder.getImageView(R.id.iv_my_collect);
                Glide.with(mContext).load(bean.getGoods_image()).error(R.color.c_press).into(imageView);

                TextView tv_my_collect_buy = holder.getTextView(R.id.tv_my_collect_buy);
                tv_my_collect_buy.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.goodsId,bean.getGoods_id()+"");
                        STActivity(intent,GoodsDetailActivity.class);
                    }
                });
                //code商品类别(0商品不存在 1普通商品 2限时抢购 3团购),staus商品状态(0商品不存在或者活动已结束 1商品存在活动没结束)
                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        //0商品不存在 1普通商品 2限时抢购 3团购),staus商品状态(0商品不存在或者活动已结束 1商品存在活动没结束
                        Intent intent=new Intent();
                        intent.putExtra(com.sk.jintang.module.orderclass.Constant.IParam.goodsId,bean.getGoods_id()+"");
                        if(bean.getCode()==0||bean.getCode()==1){
                            STActivity(intent,GoodsDetailActivity.class);
                        }else if(bean.getCode()==2){
                            intent.setAction(Config.IParam.xianShiQiangGou);
                            STActivity(intent,GoodsDetailXianShiActivity.class);
                        }else if(bean.getCode()==3){
                            STActivity(intent,GoodsDetailTuanGouActivity.class);
                        }
                    }
                });

                TextView tv_my_collect_uncollect = holder.getTextView(R.id.tv_my_collect_uncollect);
                tv_my_collect_uncollect.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
                        mDialog.setMessage("确定取消收藏吗?");
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
                                cancelCollect(position,bean);
                            }
                        });
                        mDialog.create().show();
                    }
                });
            }
        };
        adapter.setOnLoadMoreListener(this);
        rv_my_collect.setLayoutManager(new LinearLayoutManager(mContext));
        rv_my_collect.addItemDecoration(getItemDivider());
        rv_my_collect.setAdapter(adapter);

        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(1,false);
            }
        });

    }

    private void cancelCollect(int position,MyCollectObj bean) {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("goods_id",bean.getGoods_id()+"");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.cancelCollection(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                adapter.getList().remove(position);
                adapter.notifyDataSetChanged();
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
        ApiRequest.getMyCollectionList(map, new MyCallBack<List<MyCollectObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(List<MyCollectObj> list) {
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
