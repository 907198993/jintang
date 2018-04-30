package com.sk.jintang.module.orderclass.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.customview.MyTextView;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseFragment;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.orderclass.activity.ImageDetailActivity;
import com.sk.jintang.module.orderclass.Constant;
import com.sk.jintang.module.orderclass.network.ApiRequest;
import com.sk.jintang.module.orderclass.network.response.GoodsEvaluateNumObj;
import com.sk.jintang.module.orderclass.network.response.GoodsEvaluateObj;
import com.sk.jintang.module.orderclass.network.response.TuanGouDetailObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/9/14.
 */

public class GoodsEvaluateTuanGouFragment extends BaseFragment implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_goods_evaluate)
    RecyclerView rv_goods_evaluate;

    LoadMoreAdapter adapter;
    @BindView(R.id.tv_goods_evaluate_all)
    MyTextView tv_goods_evaluate_all;
    @BindView(R.id.tv_goods_evaluate_good)
    MyTextView tv_goods_evaluate_good;
    @BindView(R.id.tv_goods_evaluate_middle)
    MyTextView tv_goods_evaluate_middle;
    @BindView(R.id.tv_goods_evaluate_bad)
    MyTextView tv_goods_evaluate_bad;
    private int type = 0;
    private List<ArrayList>imgItemList=new ArrayList<>();

    private MyTextView selectEvaluateView;
    @Override
    protected int getContentView() {
        return R.layout.frag_goods_evaluate;
    }

    public static GoodsEvaluateTuanGouFragment newInstance(TuanGouDetailObj obj) {
        Bundle args = new Bundle();
        args.putSerializable("obj", obj);
        GoodsEvaluateTuanGouFragment fragment = new GoodsEvaluateTuanGouFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {
        selectEvaluateView=tv_goods_evaluate_all;
        adapter = new LoadMoreAdapter<GoodsEvaluateObj>(mContext, R.layout.item_goods_evaluate, pageSize) {
            int imgWidth = (PhoneUtils.getScreenWidth(mContext) - PhoneUtils.dip2px(mContext, 40)) / 3;

            @Override
            public void bindData(LoadMoreViewHolder holder, int position, GoodsEvaluateObj bean) {
                holder.setText(R.id.tv_item_goods_evaluate_name, bean.getNickname())
                        .setText(R.id.tv_item_goods_evaluate_date, bean.getAdd_time())
                        .setText(R.id.tv_item_goods_evaluate_content, bean.getContent())
                        .setText(R.id.tv_item_goods_evaluate_guige, bean.getSpecifications());

                ImageView imageView = holder.getImageView(R.id.iv_item_goods_evaluate_img);
                Glide.with(mContext).load(bean.getPhoto()).error(R.color.c_press).into(imageView);
                LinearLayout ll_item_goods_evaluate = (LinearLayout) holder.getView(R.id.ll_item_goods_evaluate);
                ll_item_goods_evaluate.removeAllViews();
                ArrayList<String>photoList=new ArrayList<>();
                if (notEmpty(bean.getImg_list())) {
                    for (int i = 0; i < bean.getImg_list().size(); i++) {
                        String imgUrl = bean.getImg_list().get(i).getImg();
                        photoList.add(imgUrl);
                        ImageView evaluateImg = new ImageView(mContext);
                        evaluateImg.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                        evaluateImg.setBackgroundColor(ContextCompat.getColor(mContext,R.color.c_press));
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.width = imgWidth;
                        layoutParams.height = imgWidth;
                        layoutParams.setMargins(0, 0, PhoneUtils.dip2px(mContext, 10), 0);
                        evaluateImg.setLayoutParams(layoutParams);
                        Glide.with(mContext).load(bean.getImg_list().get(i).getImg()).error(R.color.c_press).into(evaluateImg);
                        int finalI = i;
                        evaluateImg.setOnClickListener(new MyOnClickListener() {
                            @Override
                            protected void onNoDoubleClick(View view) {
                                Intent intent=new Intent();
                                intent.putExtra(Constant.IParam.imgIndex, finalI);
                                intent.putExtra(Constant.IParam.imgEvaluate, bean.getContent());
                                intent.putStringArrayListExtra(Constant.IParam.imgList,imgItemList.get(position));
                                STActivity(intent,ImageDetailActivity.class);
                            }
                        });
                        ll_item_goods_evaluate.addView(evaluateImg);
                    }
                    imgItemList.add(photoList);
                }
            }
        };
        adapter.setOnLoadMoreListener(this);


        rv_goods_evaluate.setLayoutManager(new LinearLayoutManager(mContext));
        rv_goods_evaluate.addItemDecoration(getItemDivider());
        rv_goods_evaluate.setAdapter(adapter);

        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(1, false);
                getEvaluateNum();
            }
        });
    }

    @Override
    protected void initData() {
        showProgress();
        getData(1, false);
        getEvaluateNum();
    }

    private void getEvaluateNum() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("goods_id", getGoodsDetailObj().getGoods_id() + "");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getAllGoodsEvaluateNum(map, new MyCallBack<GoodsEvaluateNumObj>(mContext, pcfl, pl_load) {
            @Override
            public void onSuccess(GoodsEvaluateNumObj obj) {
                tv_goods_evaluate_good.setText("好评("+obj.getHaopin()+")");
                tv_goods_evaluate_middle.setText("中评("+obj.getZhongpin()+")");
                tv_goods_evaluate_bad.setText("差评("+obj.getChapin()+")");
            }
        });

    }

    public TuanGouDetailObj getGoodsDetailObj() {
        return (TuanGouDetailObj) getArguments().getSerializable("obj");
    }

    private void getData(int page, boolean isLoad) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("goods_id", getGoodsDetailObj().getGoods_id() + "");
        map.put("type", type + "");
        map.put("page", page + "");
        map.put("pagesize", pageSize + "");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getAllGoodsEvaluate(map, new MyCallBack<List<GoodsEvaluateObj>>(mContext, pcfl, pl_load) {
            @Override
            public void onSuccess(List<GoodsEvaluateObj> list) {
                if (isLoad) {
                    pageNum++;
                    adapter.addList(list, true);
                } else {
                    pageNum = 2;
                    adapter.setList(list, true);
                }
            }
        });

    }

    @OnClick({R.id.tv_goods_evaluate_all, R.id.tv_goods_evaluate_good, R.id.tv_goods_evaluate_middle, R.id.tv_goods_evaluate_bad})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_goods_evaluate_all:
                type=0;
                if(selectEvaluateView!=tv_goods_evaluate_all){
                    selectEvaluateView.setSolidColor(Color.parseColor("#ABE6D1"));
                    selectEvaluateView.complete();

                    selectEvaluateView=tv_goods_evaluate_all;
                    selectEvaluateView.setSolidColor(ContextCompat.getColor(mContext,R.color.green));
                    selectEvaluateView.complete();

                    showLoading();
                    getData(1, false);
                }
                break;
            case R.id.tv_goods_evaluate_good:
                type=1;

                if(selectEvaluateView!=tv_goods_evaluate_good){
                    selectEvaluateView.setSolidColor(Color.parseColor("#ABE6D1"));
                    selectEvaluateView.complete();

                    selectEvaluateView=tv_goods_evaluate_good;
                    selectEvaluateView.setSolidColor(ContextCompat.getColor(mContext,R.color.green));
                    selectEvaluateView.complete();

                    showLoading();
                    getData(1, false);
                }
                break;
            case R.id.tv_goods_evaluate_middle:
                type=2;
                if(selectEvaluateView!=tv_goods_evaluate_middle){
                    selectEvaluateView.setSolidColor(Color.parseColor("#ABE6D1"));
                    selectEvaluateView.complete();

                    selectEvaluateView=tv_goods_evaluate_middle;
                    selectEvaluateView.setSolidColor(ContextCompat.getColor(mContext,R.color.green));
                    selectEvaluateView.complete();

                    showLoading();
                    getData(1, false);
                }
                break;
            case R.id.tv_goods_evaluate_bad:
                type=3;
                if(selectEvaluateView!=tv_goods_evaluate_bad){
                    selectEvaluateView.setSolidColor(Color.parseColor("#ABE6D1"));
                    selectEvaluateView.complete();

                    selectEvaluateView=tv_goods_evaluate_bad;
                    selectEvaluateView.setSolidColor(ContextCompat.getColor(mContext,R.color.green));
                    selectEvaluateView.complete();

                    showLoading();
                    getData(1, false);
                }
                break;
        }
    }

    @Override
    public void loadMore() {
        getData(pageNum,true);
    }
}
