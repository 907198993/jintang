package com.sk.jintang.module.orderclass.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.customview.MyTextView;
import com.sk.jintang.Config;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseFragment;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.activity.LoginActivity;
import com.sk.jintang.module.orderclass.Constant;
import com.sk.jintang.module.orderclass.activity.GoodsDetailXianShiActivity;
import com.sk.jintang.module.orderclass.network.ApiRequest;
import com.sk.jintang.module.orderclass.network.response.XianShiQiangGouObj;
import com.sk.jintang.tools.AndroidUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/9/27.
 */

public class XianShiFragment extends BaseFragment implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_xian_shi_qiang)
    RecyclerView rv_xian_shi_qiang;

    LoadMoreAdapter adapter;
    @Override
    protected int getContentView() {
        return R.layout.frag_xianshi_qiang_gou;
    }
    public static XianShiFragment newInstance(int type) {
        return newInstance(type,0);
    }
    public static XianShiFragment newInstance(int type,int status) {
        Bundle args = new Bundle();
        args.putInt(Constant.goodsType, type);
        args.putInt(Constant.xianShiStatus, status);
        XianShiFragment fragment = new XianShiFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void initView() {
        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(1,false);
            }
        });
        adapter=new LoadMoreAdapter<XianShiQiangGouObj>(mContext,R.layout.item_xian_shi_qiang,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, XianShiQiangGouObj bean) {
                ImageView imageView = holder.getImageView(R.id.iv_xianshi_img);
                Glide.with(mContext).load(bean.getGoods_image()).error(R.color.c_press).into(imageView);
                TextView tv_xianshi_befor_price = holder.getTextView(R.id.tv_xianshi_befor_price);
                tv_xianshi_befor_price.setText("¥"+bean.getPrice());
                tv_xianshi_befor_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                tv_xianshi_befor_price.getPaint().setAntiAlias(true);

                holder.setText(R.id.tv_xianshi_title,bean.getGoods_name())
                .setText(R.id.tv_xianshi_price,"¥"+bean.getFlash_price());

                TextView tv_xianshi_percent = holder.getTextView(R.id.tv_xianshi_percent);
                TextView tv_xianshi_num = holder.getTextView(R.id.tv_xianshi_num);
                MyTextView tv_xianshi_qiang = (MyTextView) holder.getView(R.id.tv_xianshi_qiang);
                tv_xianshi_qiang.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        if(TextUtils.isEmpty(getUserId())){
                            STActivity(LoginActivity.class);
                        }else{
                            Map<String,String>map=new HashMap<String,String>();
                            map.put("user_id",getUserId());
                            map.put("goods_id",bean.getGoods_id()+"");
                            map.put("sign",GetSign.getSign(map));
                            ApiRequest.xianShiTiXing(map, new MyCallBack<BaseObj>(mContext) {
                                @Override
                                public void onSuccess(BaseObj obj) {
                                    showMsg(obj.getMsg());
                                    getData(1,false);
                                }
                            });
                        }
                    }
                });
                ProgressBar pb_xianshi = (ProgressBar) holder.getView(R.id.pb_xianshi);
                pb_xianshi.setMax(100);
                if(getArguments().getInt(Constant.xianShiStatus)==Constant.timeStatus_0){
                    tv_xianshi_num.setText("剩余"+bean.getSurplus()+"件");
                    if(bean.getIs_tixing()==0){
                        tv_xianshi_qiang.setText("提醒我");
                        tv_xianshi_qiang.setSolidColor(ContextCompat.getColor(mContext,R.color.green));
                    }else{
                        tv_xianshi_qiang.setText("已提醒");
                        tv_xianshi_qiang.setSolidColor(Color.parseColor("#c2c2c2"));
                    }
                    pb_xianshi.setProgress(0);
                    tv_xianshi_percent.setText("0%");
                }else if(getArguments().getInt(Constant.xianShiStatus)==Constant.timeStatus_1){

                    tv_xianshi_num.setText("已抢"+bean.getHas_been_robbed()+"件");
                    if(bean.getHas_been_robbed()>=bean.getSurplus()){
                        pb_xianshi.setProgress(100);

                        tv_xianshi_percent.setText("100%");

                        tv_xianshi_qiang.setText("已抢完");
                        tv_xianshi_qiang.setSolidColor(Color.parseColor("#c2c2c2"));
                        tv_xianshi_qiang.setOnClickListener(null);
                    }else{
//                        BigDecimal bd1=new BigDecimal(bean.getHas_been_robbed());
//                        BigDecimal bd2=new BigDecimal(bean.getSurplus());
//                        BigDecimal divide = bd1.divide(bd2).multiply(new BigDecimal(100));
                        double divide = AndroidUtils.chengFa(AndroidUtils.chuFa(bean.getHas_been_robbed(), bean.getSurplus(),2), 100);
                        tv_xianshi_percent.setText(divide+"%");
                        pb_xianshi.setProgress((int) divide);
                        tv_xianshi_qiang.setText("马上抢");
                        tv_xianshi_qiang.setSolidColor(ContextCompat.getColor(mContext,R.color.green));

                        tv_xianshi_qiang.setOnClickListener(new MyOnClickListener() {
                            @Override
                            protected void onNoDoubleClick(View view) {
                                if(bean.getHas_been_robbed()>=bean.getSurplus()){
                                    showMsg("该商品已被抢完");
                                    return;
                                }
                                Intent intent=new Intent();
                                intent.putExtra(Constant.IParam.goodsId,bean.getGoods_id()+"");
                                intent.setAction(Config.IParam.xianShiQiangGou);
                                STActivity(intent,GoodsDetailXianShiActivity.class);
                            }
                        });

                    }
                }else{
                    tv_xianshi_num.setText("已抢"+bean.getHas_been_robbed()+"件");
                    if(bean.getHas_been_robbed()>=bean.getSurplus()){
                        pb_xianshi.setProgress(100);

                        tv_xianshi_percent.setText("100%");

                        tv_xianshi_qiang.setText("已抢完");
                        tv_xianshi_qiang.setSolidColor(Color.parseColor("#c2c2c2"));
                        tv_xianshi_qiang.setOnClickListener(null);
                    }else{
//                        BigDecimal bd1=new BigDecimal(bean.getHas_been_robbed());
//                        BigDecimal bd2=new BigDecimal(bean.getSurplus());
//                        BigDecimal divide = bd1.divide(bd2).multiply(new BigDecimal(100));


                        double divide = AndroidUtils.chengFa(AndroidUtils.chuFa(bean.getHas_been_robbed(), bean.getSurplus(), 2), 100);

                        tv_xianshi_percent.setText(divide+"%");
                        pb_xianshi.setProgress((int) divide);

                        tv_xianshi_qiang.setText("已结束");
                        tv_xianshi_qiang.setSolidColor(Color.parseColor("#c2c2c2"));
                        tv_xianshi_qiang.setOnClickListener(null);
                    }
                }
                tv_xianshi_qiang.complete();

                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {

                        if(bean.getHas_been_robbed()>=bean.getSurplus()){
                            showMsg("该商品已被抢完");
                            return;
                        }
                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.goodsId,bean.getGoods_id()+"");
                        intent.setAction(Config.IParam.xianShiQiangGou);
                        STActivity(intent,GoodsDetailXianShiActivity.class);
                    }
                });
            }
        };
        adapter.setOnLoadMoreListener(this);
        rv_xian_shi_qiang.setLayoutManager(new LinearLayoutManager(mContext));
        rv_xian_shi_qiang.addItemDecoration(getItemDivider());
        rv_xian_shi_qiang.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        showProgress();
        getData(1,false);
    }

    @Override
    protected void initRxBus() {
        super.initRxBus();
        /*RxBus.getInstance().getEvent(RefreshEvent.class, new MySubscriber() {
            @Override
            public void onMyNext(Object o) {
                getData(1,false);
            }
        });*/
    }

    public void getData(int page, boolean isLoad) {
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id", getUserId()==null?"0":getUserId());
        map.put("type",getArguments().getInt(Constant.goodsType)+"");
        map.put("page", page+"");
        map.put("pagesize", pageSize+"");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.xianShiQiangGouList(map, new MyCallBack<List<XianShiQiangGouObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(List<XianShiQiangGouObj> list) {
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

    public void endTime() {
        getArguments().putInt(Constant.xianShiStatus, Constant.timeStatus_2);
        getData(1,false);
    }
}
