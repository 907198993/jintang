package com.sk.jintang.module.orderclass.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.SPUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.rx.MySubscriber;
import com.github.baseclass.rx.RxBus;
import com.github.customview.MyCheckBox;
import com.sk.jintang.Config;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.broadcast.AddShoppingCartBro;
import com.sk.jintang.module.home.activity.MainActivity;
import com.sk.jintang.module.my.activity.LoginActivity;
import com.sk.jintang.module.shoppingcart.Constant;
import com.sk.jintang.module.shoppingcart.activity.SureGoodsActivity;
import com.sk.jintang.module.shoppingcart.adapter.ShoppingCartAdapter;
import com.sk.jintang.module.shoppingcart.event.TotalPriceEvent;
import com.sk.jintang.module.shoppingcart.network.ApiRequest;
import com.sk.jintang.module.shoppingcart.network.response.AllShoppingCartObj;
import com.sk.jintang.module.shoppingcart.network.response.ShoppingCartObj;
import com.sk.jintang.tools.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/9/2.
 */

public class ShoppingCartActivity extends BaseActivity {
    @BindView(R.id.nsv)
    NestedScrollView nsv;
    @BindView(R.id.rv_shopping_cart)
    RecyclerView rv_shopping_cart;

    @BindView(R.id.rv_shopping_tuijian)
    RecyclerView rv_shopping_tuijian;

    @BindView(R.id.ll_shopping_cart_empty)
    LinearLayout ll_shopping_cart_empty;

    @BindView(R.id.app_title)
    TextView app_title;
    @BindView(R.id.app_right_tv)
    TextView app_right_tv;

    @BindView(R.id.cb_shopingcart_select)
    MyCheckBox cb_shopingcart_select;

    @BindView(R.id.tv_shopingcart_total)
    TextView tv_shopingcart_total;

    @BindView(R.id.tv_shopingcart_jiesuan)
    TextView tv_shopingcart_jiesuan;

    ShoppingCartAdapter adapter;

    protected LoadMoreAdapter tuiJianAdapter;

    private AddShoppingCartBro addShoppingCartBro;
    private LocalBroadcastManager localBroadcastManager;
    private List<AllShoppingCartObj.TuijianBean> tuiJianList;

    @Override
    protected int getContentView() {
        setAppTitle("购物车");
        return R.layout.act_shopping_cart;
    }

    @Override
    protected void initView() {
        app_title.setText("购物车");
        app_right_tv.setText("编辑");

        adapter = new ShoppingCartAdapter(mContext, R.layout.item_shopping_cart);
        rv_shopping_cart.setLayoutManager(new LinearLayoutManager(mContext));
        rv_shopping_cart.addItemDecoration(new DividerGridItemDecoration(mContext, PhoneUtils.dip2px(mContext, 5)));
        rv_shopping_cart.setAdapter(adapter);

        cb_shopingcart_select.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (notEmpty(adapter.getList())) {
                    return false;
                } else {
                    showMsg("暂无商品可选");
                    return true;
                }
            }
        });
    }
    @Override
    protected void initRxBus() {
        super.initRxBus();
        RxBus.getInstance().getEvent(TotalPriceEvent.class, new MySubscriber<TotalPriceEvent>() {
            @Override
            public void onMyNext(TotalPriceEvent event) {
                if (notEmpty(adapter.getList())) {
                    app_title.setText("购物车(" + adapter.getList().size() + ")");

                    shoppingIsEmpty(false);
                } else {
                    app_title.setText("购物车(0)");

                    shoppingIsEmpty(true);
                }
                tv_shopingcart_total.setText(event.totalPrice);
                cb_shopingcart_select.setChecked(event.isSelectAll);
            }
        });
    }
    @Override
    protected void initData() {
        showProgress();
        getData();
    }

    private void getData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getShoppingCart(map, new MyCallBack<AllShoppingCartObj>(mContext, pcfl, pl_load) {
            @Override
            public void onSuccess(AllShoppingCartObj obj) {
                tuiJianList = obj.getTuijian();
                if (obj.getStatus() == 1) {//购物车有数据
                    List<ShoppingCartObj> list = obj.getShopping_cart();
                    adapter.setList(list, true);
//                    cb_shopingcart_select.performClick();
                    if (notEmpty(list)) {
                        app_title.setText("购物车(" + list.size() + ")");
                    }
                    shoppingIsEmpty(false);
                    adapter.setSelectAll(true);
                } else {
                    app_title.setText("购物车");
                    shoppingIsEmpty(true);
                }

            }
        });

    }

    public void shoppingIsEmpty(boolean flag){
        if(flag){
            ll_shopping_cart_empty.setVisibility(View.VISIBLE);
            pl_load.setVisibility(View.GONE);
            setTuiJian();
        }else{
            ll_shopping_cart_empty.setVisibility(View.GONE);
            pl_load.setVisibility(View.VISIBLE);
        }
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                nsv.scrollTo(0,0);
            }
        });
    }

    private int screenWidth;
    public void setTuiJian(){
        screenWidth = PhoneUtils.getScreenWidth(mContext);
        tuiJianAdapter = new LoadMoreAdapter<AllShoppingCartObj.TuijianBean>(mContext, R.layout.item_goods, pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, AllShoppingCartObj.TuijianBean bean) {
                TextView tv_goods_yuanjia = holder.getTextView(R.id.tv_goods_yuanjia);
                if(bean.getOriginal_price()<=0||bean.getOriginal_price()==bean.getPrice()){
                    tv_goods_yuanjia.setVisibility(View.INVISIBLE);
                }else{
                    tv_goods_yuanjia.setText("¥"+bean.getOriginal_price());
                    tv_goods_yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    tv_goods_yuanjia.getPaint().setAntiAlias(true);
                    tv_goods_yuanjia.setVisibility(View.VISIBLE);
                }

                View tv_goods_baoyou = holder.getView(R.id.tv_goods_baoyou);
                tv_goods_baoyou.setVisibility(bean.getBaoyou()==1?View.VISIBLE:View.GONE);
                int imgWidth = (screenWidth - 2) / 2 - PhoneUtils.dip2px(mContext, 20);
                ImageView iv_goods_img = holder.getImageView(R.id.iv_goods_img);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.weight=imgWidth;
                layoutParams.height=imgWidth;
                iv_goods_img.setLayoutParams(layoutParams);
                Glide.with(mContext).load(bean.getGoods_image()).error(R.color.c_press).into(iv_goods_img);

                ImageView iv_goods_share = holder.getImageView(R.id.iv_goods_share);
                iv_goods_share.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        showFenXiang(bean.getGoods_id()+"");
                    }
                });
                holder.setText(R.id.tv_goods_name,bean.getGoods_name())
                        .setText(R.id.tv_goods_address,bean.getAddresss())
                        .setText(R.id.tv_goods_price,""+bean.getPrice())
                        .setText(R.id.tv_goods_goumai,bean.getSales_volume()+"人购买");

                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        if (TextUtils.isEmpty(SPUtils.getPrefString(mContext, Config.user_id, null))) {
                            STActivity(LoginActivity.class);
                            return;
                        }
                        Intent intent = new Intent();
//                        intent.setFlags()
                        intent.putExtra(com.sk.jintang.module.orderclass.Constant.IParam.goodsId, bean.getGoods_id() + "");
                        STActivity(intent, GoodsDetailActivity.class);
                    }
                });
            }
        };
        tuiJianAdapter.setList(tuiJianList);
        rv_shopping_tuijian.setLayoutManager(new GridLayoutManager(mContext, 2));
        rv_shopping_tuijian.setNestedScrollingEnabled(false);
        rv_shopping_tuijian.setAdapter(tuiJianAdapter);
        rv_shopping_tuijian.addItemDecoration(new DividerGridItemDecoration(mContext,2));


    }
    @OnClick({R.id.tv_shopingcart_jiesuan, R.id.app_right_tv, R.id.cb_shopingcart_select,R.id.tv_shopping_qianggou, R.id.tv_shopping_looklook})
    protected void onViewClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_shopping_qianggou:
                STActivity(XianShiQiangActivity.class);
                break;
            case R.id.tv_shopping_looklook:
                intent=new Intent(Config.useVoucher);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                STActivity(intent,MainActivity.class);
                break;
            case R.id.app_right_tv:
                if (isEmpty(adapter.getList())) {
                    showMsg("暂无商品编辑");
                    return;
                }
                adapter.setEdit(!adapter.isEdit());
                if (adapter.isEdit()) {
                    app_right_tv.setText("完成");
                } else {
                    app_right_tv.setText("编辑");
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.cb_shopingcart_select:
                if (cb_shopingcart_select.isChecked()) {
                    adapter.setSelectAll(true);
                } else {
                    tv_shopingcart_total.setText("¥0.0");
                    adapter.setSelectAll(false);
                }
                break;
            case R.id.tv_shopingcart_jiesuan:
                if (isEmpty(adapter.getList())) {
                    showMsg("暂无商品结算");
                    return;
                }
                ArrayList<ShoppingCartObj> list = new ArrayList<>();
                for (int i = 0; i < adapter.getList().size(); i++) {
                    ShoppingCartObj obj = adapter.getList().get(i);
                    if (obj.isSelect()) {
                        list.add(obj);
                    }
                }
                if (list == null || list.size() == 0) {
                    showMsg("请选择商品");
                    return;
                }
                intent = new Intent();
                intent.putParcelableArrayListExtra(Constant.IParam.shoppingGoods, list);
                intent.putExtra(com.sk.jintang.module.orderclass.Constant.IParam.hourDao,true);
                STActivity(intent, SureGoodsActivity.class);
                break;
        }
    }

}
