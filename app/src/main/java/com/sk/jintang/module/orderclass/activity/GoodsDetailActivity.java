package com.sk.jintang.module.orderclass.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.SPUtils;
import com.github.androidtools.StatusBarUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.customview.FlowLayout;
import com.github.customview.MyTextView;
import com.hyphenate.easeui.EaseConstant;
import com.sk.jintang.Config;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.chat.ChatActivity;
import com.sk.jintang.chat.ConversationActivity;
import com.sk.jintang.module.my.activity.LoginActivity;
import com.sk.jintang.module.orderclass.Constant;
import com.sk.jintang.module.orderclass.adapter.GoodsDetailImgAdapter;
import com.sk.jintang.module.orderclass.bean.TabEntity;
import com.sk.jintang.module.orderclass.network.ApiRequest;
import com.sk.jintang.module.orderclass.network.response.GoodsDetailObj;
import com.sk.jintang.module.orderclass.network.response.GoodsParamsObj;
import com.sk.jintang.module.orderclass.network.response.GuiGeObj;
import com.sk.jintang.module.shoppingcart.activity.ShopActivity;
import com.sk.jintang.module.shoppingcart.activity.SureGoodsActivity;
import com.sk.jintang.module.shoppingcart.network.response.ShoppingCartObj;
import com.sk.jintang.tools.DividerGridItemDecoration;
import com.sk.jintang.tools.GlideLoader;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/9/1.
 */

public class GoodsDetailActivity extends BaseActivity {
    @BindView(R.id.nsv_goods_detail)
    NestedScrollView nsv_goods_detail;
    @BindView(R.id.rv_goods_detail)
    RecyclerView rv_goods_detail;
    //顶部栏
    @BindView(R.id.ll_order_detail_top)
    LinearLayout ll_order_detail_top;

    @BindView(R.id.ll_goods_detail_all_pj)
    LinearLayout ll_goods_detail_all_pj;

    @BindView(R.id.ll_goods_detail_pj)
    LinearLayout ll_goods_detail_pj;

    @BindView(R.id.linear_shop)
    LinearLayout linearShop;

    @BindView(R.id.ll_goods_detail_xiangqing)
    LinearLayout ll_goods_detail_xiangqing;
    @BindView(R.id.ll_goods_detail_tuijian)
    LinearLayout ll_goods_detail_tuijian;
    @BindView(R.id.tv_goods_detail_price)
    TextView tv_goods_detail_price;
    @BindView(R.id.tv_goods_detail_maxprice)
    TextView tv_goods_detail_maxprice;
    @BindView(R.id.tv_goods_detail_yuanjia)
    TextView tv_goods_detail_yuanjia;

    @BindView(R.id.rv_goods_detail_evaluate)
    RecyclerView rv_goods_detail_evaluate;

    @BindView(R.id.rv_goods_detail_img)
    RecyclerView rv_goods_detail_img;

    GoodsDetailImgAdapter imgAdapter;

    @BindView(R.id.bn_goods_detail)
    Banner bn_goods_detail;

    LoadMoreAdapter adapter,evaluateAdapter;
    @BindView(R.id.tv_goods_detail_shoppingcart)
    TextView tv_goods_detail_shoppingcart;
    @BindView(R.id.tv_goods_detail_buy)
    TextView tv_goods_detail_buy;
    @BindView(R.id.tv_goods_detail_name)
    TextView tv_goods_detail_name;
    @BindView(R.id.tv_goods_detail_kuaidi)
    TextView tv_goods_detail_kuaidi;
    @BindView(R.id.tv_goods_detail_yuexiao)
    TextView tv_goods_detail_yuexiao;
    @BindView(R.id.tv_goods_detail_address)
    TextView tv_goods_detail_address;
    @BindView(R.id.tv_goods_detail_ysd)
    TextView tv_goods_detail_ysd;
    @BindView(R.id.tv_goods_detail_guige)
    TextView tv_goods_detail_guige;
    @BindView(R.id.tv_goods_detail_evaluate_num)
    TextView tv_goods_detail_evaluate_num;
    @BindView(R.id.iv_goods_detail_collect)
    ImageView iv_goods_detail_collect;
    @BindView(R.id.ctl_goods_detail)
    CommonTabLayout ctl_goods_detail;
    private String goodsId;
    private ArrayList<String> bannerList;
    private MyTextView selectGuiGeView;
    //    private BottomSheetDialog guiGeDialog;
    private Dialog shareDialog;
    private GuiGeObj selectGuiGeObj;
    private boolean isCollect;
    private GoodsDetailObj goodsDetailObj;
    private BottomSheetDialog goodsParamsDialog;
    private String intentAction;
    private String HxUserId;
    private boolean isHourDao =true;
    private int storeId;

    @Override
    protected int getContentView() {
        return R.layout.shop_goods_detail;
    }

    @Override
    protected void initView() {
        setTabTitle();
        int screenWidth = PhoneUtils.getScreenWidth(mContext);
        ll_order_detail_top.getBackground().mutate().setAlpha(0);
        ll_order_detail_top.setPadding(0, StatusBarUtils.getStatusBarHeight(mContext), 0, 0);
        nsv_goods_detail.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY >= 0 && scrollY <= screenWidth) {
                    double alpha = (double) scrollY / screenWidth;
                    ll_order_detail_top.getBackground().mutate().setAlpha((int) (alpha * 255));
                } else {
                    ll_order_detail_top.getBackground().mutate().setAlpha(255);
                }
                if(scrollY >= 0 && scrollY <= screenWidth/2){ //不显示title
                    ctl_goods_detail.setVisibility(View.INVISIBLE);
                }else{//显示title
                    ctl_goods_detail.setVisibility(View.VISIBLE);
                    if(keJian(ll_goods_detail_pj) ){
                        ctl_goods_detail.setCurrentTab(1);
                    }else if(keJian(rv_goods_detail_img)&&keJian(ll_goods_detail_tuijian)==false){
                        ctl_goods_detail.setCurrentTab(2);
                    }else if(keJian(rv_goods_detail)||keJian(ll_goods_detail_tuijian)){
                        ctl_goods_detail.setCurrentTab(3);
                    }
                }

            }
        });

        intentAction = getIntent().getAction();
        goodsId = getIntent().getStringExtra(Constant.IParam.goodsId);
//        isHourDao = getIntent().getBooleanExtra(Constant.IParam.hourDao,false);

        if(!isHourDao){
//            tv_goods_detail_shoppingcart.setVisibility(View.GONE);
//            tv_goods_detail_buy.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT,4));
        }
       /* if(Config.IParam.xianShiQiangGou.equals(intentAction)){
            ll_goods_detail_xian_shi.setVisibility(View.VISIBLE);
            tv_goods_detail_shoppingcart.setVisibility(View.INVISIBLE);
        }else{
            ll_goods_detail_xian_shi.setVisibility(View.GONE);
            tv_goods_detail_shoppingcart.setVisibility(View.VISIBLE);
        }*/

        adapter = new LoadMoreAdapter<GoodsDetailObj.TuijianListBean>(mContext, R.layout.item_goods, pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, GoodsDetailObj.TuijianListBean bean) {
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
                layoutParams.weight = imgWidth;
                layoutParams.height = imgWidth;
                iv_goods_img.setLayoutParams(layoutParams);
                Glide.with(mContext).load(bean.getGoods_image()).error(R.color.c_press).into(iv_goods_img);

                ImageView iv_goods_share = holder.getImageView(R.id.iv_goods_share);
                iv_goods_share.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        showFenXiang(bean.getGoods_id()+"");
                    }
                });
                holder.setText(R.id.tv_goods_name, bean.getGoods_name())
                        .setText(R.id.tv_goods_address, bean.getAddresss())
                        .setText(R.id.tv_goods_price, "" + bean.getPrice())
                        .setText(R.id.tv_goods_goumai, bean.getSales_volume() + "人购买");
                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.goodsId,bean.getGoods_id()+"");
                        STActivity(intent,GoodsDetailActivity.class);
                    }
                });
            }
        };
        rv_goods_detail.setLayoutManager(new GridLayoutManager(mContext, 2));
        rv_goods_detail.setNestedScrollingEnabled(false);
        rv_goods_detail.addItemDecoration(new DividerGridItemDecoration(mContext, 2));
        rv_goods_detail.setAdapter(adapter);


        imgAdapter=new GoodsDetailImgAdapter(mContext,R.layout._item_);
        rv_goods_detail_img.setNestedScrollingEnabled(false);
        rv_goods_detail_img.setLayoutManager(new LinearLayoutManager(mContext));
        rv_goods_detail_img.setAdapter(imgAdapter);

        evaluateAdapter=new LoadMoreAdapter<GoodsDetailObj.PingjiaListBean>(mContext,R.layout.item_goods_detail_evaluate,0) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, GoodsDetailObj.PingjiaListBean bean) {
                ImageView imageView = holder.getImageView(R.id.iv_goods_detail_evaluate);
                Glide.with(mContext).load(bean.getPhoto()).error(R.drawable.people).into(imageView);
                holder.setText(R.id.tv_goods_detail_evaluate_name,bean.getNickname())
                        .setText(R.id.tv_goods_detail_evaluate,bean.getContent())
                        .setText(R.id.tv_goods_detail_evaluate_date,bean.getAdd_time());
            }
        };

        rv_goods_detail_evaluate.setAdapter(evaluateAdapter);
        rv_goods_detail_evaluate.setNestedScrollingEnabled(false);
        rv_goods_detail_evaluate.setLayoutManager(new LinearLayoutManager(mContext));
        rv_goods_detail_evaluate.addItemDecoration(getItemDivider());
    }

    private void setTabTitle() {
        ArrayList<CustomTabEntity> list = new ArrayList<>();
        list.add(new TabEntity("商品"));
        list.add(new TabEntity("评价"));
        list.add(new TabEntity("详情"));
        list.add(new TabEntity("推荐"));
        ctl_goods_detail.setTabData(list);
        ctl_goods_detail.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switch (position) {
                    case 0:
                        nsv_goods_detail.smoothScrollTo(0,0);
                        break;
                    case 1:
                        nsv_goods_detail.smoothScrollTo(0,ll_goods_detail_pj.getTop()-PhoneUtils.dip2px(mContext,getResources().getDimension(R.dimen.app_title_height)));
                        break;
                    case 2:
                        nsv_goods_detail.smoothScrollTo(0,ll_goods_detail_xiangqing.getTop()-PhoneUtils.dip2px(mContext,getResources().getDimension(R.dimen.app_title_height)));
                        break;
                    case 3:
                        nsv_goods_detail.smoothScrollTo(0,ll_goods_detail_tuijian.getTop()-PhoneUtils.dip2px(mContext,getResources().getDimension(R.dimen.app_title_height)));
                        break;
                }
            }
            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    @Override
    protected void initData() {
        showProgress();
//        getShoppingCartNum();
        getData();
    }

    private void getData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id",getUserId()==null?"0":getUserId());
        map.put("goods_id", goodsId + "");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getGoodsDetail(map, new MyCallBack<GoodsDetailObj>(mContext, pcfl, pl_load) {
            @Override
            public void onSuccess(GoodsDetailObj obj) {
                storeId = obj.getStore_id();
                goodsDetailObj = obj;
                guiGeImg = obj.getGoods_image();
                setBanner(obj);
                setGoodsData(obj);
                HxUserId = obj.getHxUserId();
                imgAdapter.setList(obj.getGoods_details(),true);
            }
        });

    }

    private void setGoodsData(GoodsDetailObj obj) {
        isCollect = obj.getIs_collect() == 1;
        if (isCollect) {
            iv_goods_detail_collect.setImageResource(R.mipmap.collected);
        } else {
            iv_goods_detail_collect.setImageResource(R.mipmap.collect);
        }
        adapter.setList(obj.getTuijian_list(), true);
        tv_goods_detail_price.setText(obj.getPrice()+"");
        if(obj.getPrice()==obj.getMax_price()){
            tv_goods_detail_maxprice.setVisibility(View.GONE);
        }else{
            tv_goods_detail_maxprice.setVisibility(View.VISIBLE);
            tv_goods_detail_maxprice.setText("~"+obj.getMax_price());
        }

        if(obj.getOriginal_price()<=0||obj.getOriginal_price()==obj.getPrice()){
            tv_goods_detail_yuanjia.setVisibility(View.INVISIBLE);
        }else{
            tv_goods_detail_yuanjia.setText("¥"+obj.getOriginal_price());
            tv_goods_detail_yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tv_goods_detail_yuanjia.getPaint().setAntiAlias(true);
            tv_goods_detail_yuanjia.setVisibility(View.VISIBLE);
        }



        tv_goods_detail_name.setText(obj.getGoods_name());
        tv_goods_detail_kuaidi.setText(obj.getCourier() + "");
        tv_goods_detail_yuexiao.setText("月销" + obj.getSales_volume() + "笔");
        tv_goods_detail_address.setText(obj.getAddresss());
        tv_goods_detail_ysd.setText("购买可获得" + obj.getKeeping_bean() + "个养生豆");
        tv_goods_detail_evaluate_num.setText("评价(" + obj.getPingjia_num() + ")");


        evaluateAdapter.setList(obj.getPingjia_list(),true);
        if(notEmpty(obj.getPingjia_list())){
            ll_goods_detail_all_pj.setVisibility(View.VISIBLE);
        }else{
            ll_goods_detail_all_pj.setVisibility(View.GONE);
        }
        /*if (obj.getPingjia_list() != null && obj.getPingjia_list().size() > 0) {
            Glide.with(mContext).load(obj.getPingjia_list().get(0).getPhoto()).error(R.drawable.people).into(iv_goods_detail_evaluate0);
            tv_goods_detail_evaluate_name0.setText(obj.getPingjia_list().get(0).getNickname());
            tv_goods_detail_evaluate0.setText(obj.getPingjia_list().get(0).getContent());
            tv_goods_detail_evaluate_date0.setText(obj.getPingjia_list().get(0).getAdd_time());
        }
        if (obj.getPingjia_list() != null && obj.getPingjia_list().size() > 1) {
            tv_goods_detail_evaluate_name1.setText(obj.getPingjia_list().get(1).getNickname());
            tv_goods_detail_evaluate1.setText(obj.getPingjia_list().get(1).getContent());
            tv_goods_detail_evaluate_date1.setText(obj.getPingjia_list().get(1).getAdd_time());
            Glide.with(mContext).load(obj.getPingjia_list().get(1).getPhoto()).error(R.drawable.people).into(iv_goods_detail_evaluate1);
        }*/
    }

    private void setBanner(GoodsDetailObj obj) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.width=PhoneUtils.getScreenWidth(mContext);
        layoutParams.height=PhoneUtils.getScreenWidth(mContext);
        bn_goods_detail.setLayoutParams(layoutParams);
        bannerList = new ArrayList<String>();
        for (int i = 0; i < obj.getImg_list().size(); i++) {
            bannerList.add(obj.getImg_list().get(i).getGoods_image());
        }
        bn_goods_detail.setImages(bannerList);
        bn_goods_detail.setImageLoader(new GlideLoader());
        bn_goods_detail.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent=new Intent();
                intent.putStringArrayListExtra(Constant.IParam.imgList,bannerList);
                intent.putExtra(com.sk.jintang.module.my.Constant.IParam.imgIndex,position);
//                图片详情
                STActivity(intent,ImageDetailActivity.class);
            }
        });
        bn_goods_detail.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (bn_goods_detail != null && bannerList != null) {
            bn_goods_detail.stopAutoPlay();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (bn_goods_detail != null && bannerList != null) {
            bn_goods_detail.startAutoPlay();
        }
    }

    @Override
    protected void myReStart() {
        super.myReStart();
//        getShoppingCartNum();
    }

    @OnClick({R.id.ll_goods_detail_question,R.id.fl_goods_detail_shopping_cart,R.id.ll_goods_detail_params,R.id.tv_goods_detail_lookevaluate,R.id.ll_goods_detail_guige, R.id.iv_goods_detail_back, R.id.iv_goods_detail_share, R.id.ll_goods_detail_kefu, R.id.ll_goods_detail_collect, R.id.tv_goods_detail_shoppingcart, R.id.tv_goods_detail_buy,R.id.linear_shop})
    public void onViewClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_goods_detail_question:
                intent=new Intent();
                intent.putExtra(com.sk.jintang.module.my.Constant.IParam.goodsId,goodsId);
                STActivity(intent,GoodsQuestionActivity.class);
                break;
            case R.id.tv_goods_detail_lookevaluate://全部评价
                intent=new Intent();
                intent.putExtra(Constant.IParam.goods,goodsDetailObj);
                intent.putExtra(Constant.IParam.isCollect,isCollect);
                intent.putExtra(Constant.IParam.hourDao,isHourDao);
                STActivityForResult(intent,GoodsEvaluateActivity.class,100);
                break;
            case R.id.fl_goods_detail_shopping_cart:
                if (TextUtils.isEmpty(SPUtils.getPrefString(mContext, Config.user_id, null))) {
                    STActivity(LoginActivity.class);
                    return;
                }
                STActivity(ShoppingCartActivity.class);
                break;
            case R.id.iv_goods_detail_back:
                finish();
                break;
            case R.id.iv_goods_detail_share:
                showFenXiang(goodsDetailObj.getGoods_id()+"");
                break;
            case R.id.ll_goods_detail_kefu:
                if (TextUtils.isEmpty(getUserId())) {
                    STActivity(LoginActivity.class);
                    return;
                }
                goHX(1,HxUserId);
                break;
            case R.id.ll_goods_detail_collect:
                if (TextUtils.isEmpty(getUserId())) {
                    STActivity(LoginActivity.class);
                    return;
                }
                collectGoods();
                break;
            case R.id.linear_shop:  //去店铺
                intent=new Intent();
                intent.putExtra(com.sk.jintang.module.my.Constant.IParam.storeId,storeId+"");
                STActivity(intent,ShopActivity.class);
                break;

            case R.id.ll_goods_detail_guige:
                getGuiGeData(-1);
                break;
            case R.id.tv_goods_detail_shoppingcart:
                if (TextUtils.isEmpty(getUserId())) {
                    STActivity(LoginActivity.class);
                    return;
                }
                getGuiGeData(0);
                break;
            case R.id.tv_goods_detail_buy:
                if (TextUtils.isEmpty(getUserId())) {
                    STActivity(LoginActivity.class);
                    return;
                }
                getGuiGeData(1);
                break;
            case R.id.ll_goods_detail_params://商品参数
                getGoodsParams();
            break;
        }
    }

    private void getGoodsParams() {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("goods_id",goodsId+"");
        map.put("sign",GetSign.getSign(map));
        ApiRequest.getGoodsParams(map, new MyCallBack<List<GoodsParamsObj>>(mContext) {
            @Override
            public void onSuccess(List<GoodsParamsObj> list) {
                setGoodsParams(list);
            }
        });

    }

    private void setGoodsParams(List<GoodsParamsObj> list) {
        goodsParamsDialog=new BottomSheetDialog(mContext);
        goodsParamsDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        View goodsParamsView = LayoutInflater.from(mContext).inflate(R.layout.popu_goods_params, null);
        ImageView iv_goods_param_cancle = goodsParamsView.findViewById(R.id.iv_goods_param_cancle);
        RecyclerView rv_goods_param = goodsParamsView.findViewById(R.id.rv_goods_param);
        LoadMoreAdapter adapter=new LoadMoreAdapter<GoodsParamsObj>(mContext,R.layout.item_goods_params,0) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int i, GoodsParamsObj bean) {
                holder.setText(R.id.tv_goods_param_name,bean.getParameter_name()+"  ")
                        .setText(R.id.tv_goods_param_content,bean.getParameter_value());
            }
        };
        adapter.setList(list);
        rv_goods_param.setLayoutManager(new LinearLayoutManager(mContext));
        rv_goods_param.setAdapter(adapter);
        iv_goods_param_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goodsParamsDialog.dismiss();
            }
        });
        goodsParamsDialog.setContentView(goodsParamsView);
        goodsParamsDialog.show();
    }

    private void addShoppingCart() {
        if (TextUtils.isEmpty(SPUtils.getPrefString(mContext, Config.user_id, null))) {
            STActivity(LoginActivity.class);
            return;
        }
//        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("goods_id", goodsId);
        map.put("number", guiGeNum+"");
        map.put("specification_id", selectGuiGeObj.getId()+"");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.addShoppingCart(map, new MyCallBack<BaseObj>(mContext,true) {
            @Override
            public void onSuccess(BaseObj obj) {
//                getShoppingCartNum();
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent(Config.Bro.addShoppingCart));
                showMsg(obj.getMsg());
                if (shareDialog != null) {
                    shareDialog.dismiss();
                }
            }
        });
    }

//    private void getShoppingCartNum() {
//        if (TextUtils.isEmpty(getUserId())) {
//            tv_goods_detail_shopping_cart_num.setVisibility(View.INVISIBLE);
//            return;
//        }
//        Map<String,String>map=new HashMap<String,String>();
//        map.put("user_id",getUserId());
//        map.put("sign",GetSign.getSign(map));
//        ApiRequest.getShoppingCartNum(map, new MyCallBack<BaseObj>(mContext) {
//            @Override
//            public void onSuccess(BaseObj obj) {
//                tv_goods_detail_shopping_cart_num.setText(obj.getShoppingCartCount()+"");
//                tv_goods_detail_shopping_cart_num.setVisibility(View.VISIBLE);
//            }
//        });
//    }

    private void buyGoods() {
        ArrayList<ShoppingCartObj>list=new ArrayList<>();
        ShoppingCartObj obj =new ShoppingCartObj();
        obj.setId(0);//不清空购物车标识
        obj.setNumber(guiGeNum);
        obj.setSpecification_id(selectGuiGeObj.getId());
        obj.setGoods_id(goodsId);
        list.add(obj);
        Intent intent=new Intent();
        intent.putExtra(Constant.IParam.hourDao,isHourDao);
        intent.putExtra(Constant.IParam.guiGeId,selectGuiGeObj.getId()+"");
        intent.putExtra(Constant.IParam.goodsId,goodsId+"");
        intent.putExtra(Constant.IParam.goodsNum,guiGeNum+"");
        intent.putParcelableArrayListExtra(com.sk.jintang.module.shoppingcart.Constant.IParam.shoppingGoods,list);
        intent.putExtra(com.sk.jintang.module.orderclass.Constant.IParam.is_buy_now,"1");
            STActivity(intent,SureGoodsActivity.class);
        if(shareDialog!=null){
            shareDialog.dismiss();
        }
    }

    private void collectGoods() {

        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("goods_id", goodsId);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.collectGoods(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
//                showMsg(obj.getMsg());
                isCollect = !isCollect;
                if (isCollect) {
                    iv_goods_detail_collect.setImageResource(R.mipmap.collected);
                } else {
                    iv_goods_detail_collect.setImageResource(R.mipmap.collect);
                }
            }
        });
    }

    //0加入购物车，1立即购买,-1查看商品规格，不显示确认按钮
    private void getGuiGeData(int type) {
        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put("goods_id", goodsId);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getGoodsGuiGe(map, new MyCallBack<List<GuiGeObj>>(mContext) {
            @Override
            public void onSuccess(List<GuiGeObj> list) {
                if(type==-1){
                    showGuiGe(list,type);
                    return;
                }
                //如果规格只有一个就直接加入购物车或者购买
                if(notEmpty(list)&&list.size()==1){
                    selectGuiGeObj=list.get(0);
                    if(type==0){//加入购物车
                        addShoppingCart();
                    }else{//立即购买
                        buyGoods();
                    }

                }else{
                    showGuiGe(list,type);
                }
            }
        });

    }

    private String guiGeImg;
    private int guiGeNum = 1;//记录购买数量

    private void showGuiGe(List<GuiGeObj> list,int type) {

        shareDialog = new Dialog(mContext);//,R.style.dialogAnimation
        Window win = shareDialog.getWindow();
        win.setGravity(Gravity.CENTER);
        win.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        win.getDecorView().setPadding(0,0,0,PhoneUtils.getNavigationBarHeight(mContext));

        win.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        win.setAttributes(lp);

        Context context = shareDialog.getContext();
        int divierId = context.getResources().getIdentifier("android:id/titleDivider", null, null);
        View divider = shareDialog.findViewById(divierId);
        if (divider != null) {
            divider.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
        }

        View guiGeView = LayoutInflater.from(mContext).inflate(R.layout.popu_goods_guige_select, null);

        NestedScrollView nsv_goods_detail_guige = guiGeView.findViewById(R.id.nsv_goods_detail_guige);
        win.getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect= new Rect();
                win.getDecorView().getWindowVisibleDisplayFrame(rect);
                int screenHeight = win.getDecorView().getRootView().getHeight();
                int heightDifferent = screenHeight - rect.bottom- PhoneUtils.getNavigationBarHeight(mContext);
                win.getDecorView().setPadding(0, 0, 0, heightDifferent+PhoneUtils.getNavigationBarHeight(mContext));
                nsv_goods_detail_guige.fullScroll(View.FOCUS_DOWN);
            }
        });
        ImageView iv_guige_img = guiGeView.findViewById(R.id.iv_guige_img);
        if (!TextUtils.isEmpty(guiGeImg)) {
            Glide.with(mContext).load(guiGeImg).error(R.color.c_press).into(iv_guige_img);
        }
        TextView tv_guige_price = guiGeView.findViewById(R.id.tv_guige_price);
        TextView tv_guige_kucun = guiGeView.findViewById(R.id.tv_guige_kucun);
        ImageView iv_guige_cancle = guiGeView.findViewById(R.id.iv_guige_cancle);
        LinearLayout ll_guige_bg = guiGeView.findViewById(R.id.ll_guige_bg);
        FlowLayout fl_guige = guiGeView.findViewById(R.id.fl_guige);
        addGuiGeView(iv_guige_img, tv_guige_price, tv_guige_kucun, fl_guige, list);
        ImageView iv_guige_jian = guiGeView.findViewById(R.id.iv_guige_jian);
        ImageView iv_guige_jia = guiGeView.findViewById(R.id.iv_guige_jia);
        EditText tv_guige_num = guiGeView.findViewById(R.id.tv_guige_num);
        tv_guige_num.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                iv_guige_cancle.clearFocus();
                iv_guige_cancle.setFocusableInTouchMode(false);
                tv_guige_num.requestFocus();
                return false;
            }
        });

        TextView tv_guige_addshoppingcart = guiGeView.findViewById(R.id.tv_guige_addshoppingcart);
        TextView tv_guige_buy = guiGeView.findViewById(R.id.tv_guige_buy);
        TextView tv_guige_sure = guiGeView.findViewById(R.id.tv_guige_sure);
        tv_guige_addshoppingcart.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                if (TextUtils.isEmpty(getUserId())) {
                    STActivity(LoginActivity.class);
                    return;
                }
                if (noCanBuy(tv_guige_num)) {
                    return;
                }
                addShoppingCart();
            }
        });
        tv_guige_buy.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                if (TextUtils.isEmpty(getUserId())) {
                    STActivity(LoginActivity.class);
                    return;
                }
                if (noCanBuy(tv_guige_num)) {
                    return;
                }
                buyGoods();
            }
        });
        if(type==-1){
            tv_guige_addshoppingcart.setVisibility(View.VISIBLE);
            tv_guige_buy.setVisibility(View.VISIBLE);
            tv_guige_sure.setVisibility(View.GONE);
        }else{
            tv_guige_addshoppingcart.setVisibility(View.GONE);
            tv_guige_buy.setVisibility(View.GONE);
            tv_guige_sure.setVisibility(View.VISIBLE);
        }
        if(!isHourDao){
            tv_guige_addshoppingcart.setVisibility(View.GONE);
        }
        tv_guige_sure.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                if (noCanBuy(tv_guige_num)) {
                    return;
                }
                if(type==0){//加入购物车
                    addShoppingCart();
                }else{//立即购买
                    shareDialog.dismiss();
                    buyGoods();
                }
            }
        });
        tv_guige_num.setText(guiGeNum + "");
        ll_guige_bg.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                shareDialog.dismiss();
            }
        });
        iv_guige_cancle.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                shareDialog.dismiss();
            }
        });
        iv_guige_jian.setOnClickListener(getListener(0, tv_guige_num));
        iv_guige_jia.setOnClickListener(getListener(1, tv_guige_num));
        shareDialog.setContentView(guiGeView);

        shareDialog.show();

    }

    private boolean noCanBuy(EditText tv_guige_num) {
        if(TextUtils.isEmpty(getSStr(tv_guige_num).trim())||"0".equals(getSStr(tv_guige_num).trim())||Integer.parseInt(getSStr(tv_guige_num))<=0){
            showMsg("请输入购买量");
            return true;
        }else if(Integer.parseInt(getSStr(tv_guige_num))> selectGuiGeObj.getStock()){//没等号
            tv_guige_num.setText(selectGuiGeObj.getStock() + "");
            showMsg("购买量不能超过库存");
            guiGeNum=selectGuiGeObj.getStock();
            return true;
        }
        guiGeNum=Integer.parseInt(getSStr(tv_guige_num));
        return false;
    }

    @NonNull
    private View.OnClickListener getListener(int flag, EditText tv_guige_num) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (flag == 0) {//减法
                    if(noCanBuy(tv_guige_num)){
                        return;
                    }
                    if (guiGeNum > 1) {
                        guiGeNum--;
                        tv_guige_num.setText(guiGeNum + "");
                    }
                } else {//加法
                    if(TextUtils.isEmpty(getSStr(tv_guige_num).trim())||"0".equals(getSStr(tv_guige_num).trim())||Integer.parseInt(getSStr(tv_guige_num))<=0){
                        showMsg("请输入购买量");
                        return;
                    }else if(Integer.parseInt(getSStr(tv_guige_num))>= selectGuiGeObj.getStock()){
                        tv_guige_num.setText(selectGuiGeObj.getStock() + "");
                        showMsg("购买量不能超过库存");
                        guiGeNum=selectGuiGeObj.getStock();
                        return;
                    }
                    guiGeNum=Integer.parseInt(getSStr(tv_guige_num));
                    if (guiGeNum >= selectGuiGeObj.getStock()) {
                        showMsg("购买量不能超过库存");
                    } else {
                        guiGeNum++;
                        tv_guige_num.setText(guiGeNum + "");
                    }
                }
            }
        };
    }

    private void addGuiGeView(ImageView iv_guige_img, TextView tv_guige_price, TextView tv_guige_kucun, FlowLayout fl_guige, final List<GuiGeObj> list) {
        fl_guige.removeAllViews();
        if (notEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                MyTextView textView = new MyTextView(mContext);
//                textView.setWidth(PhoneUtils.dip2px(mContext, 97));
                textView.setHeight(PhoneUtils.dip2px(mContext, 30));
                textView.setGravity(Gravity.CENTER);
                FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, PhoneUtils.dip2px(mContext, 10), PhoneUtils.dip2px(mContext, 10), 0);
                textView.setLayoutParams(layoutParams);
                textView.setPadding(PhoneUtils.dip2px(mContext,9),0,PhoneUtils.dip2px(mContext,9),0);
                textView.setText(list.get(i).getSpecification());
                textView.setTextColor(mContext.getResources().getColor(R.color.white));
                textView.setTextSize(14);
                textView.setRadius(PhoneUtils.dip2px(mContext, 4));
                textView.setSolidColor(Color.parseColor("#e0e0e0"));
                textView.complete();
                textView.setTag(i);
                textView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        if (selectGuiGeView == null) {
                            selectGuiGeView = textView;
                            selectGuiGeView.setSolidColor(mContext.getResources().getColor(R.color.green));
                            selectGuiGeView.complete();

                            clickGuiGe();
                        } else if (selectGuiGeView != textView) {
                            selectGuiGeView.setSolidColor(Color.parseColor("#e0e0e0"));
                            selectGuiGeView.complete();

                            selectGuiGeView = textView;
                            selectGuiGeView.setSolidColor(mContext.getResources().getColor(R.color.green));
                            selectGuiGeView.complete();

                            clickGuiGe();
                        }
                    }

                    private void clickGuiGe() {
                        selectGuiGeObj = list.get((int) textView.getTag());
                        tv_guige_price.setText("¥" + list.get((int) textView.getTag()).getPrice());
                        tv_guige_kucun.setText("库存" + list.get((int) textView.getTag()).getStock() + "件");
                        Glide.with(mContext).load(list.get((int) textView.getTag()).getImages()).error(R.color.c_press).into(iv_guige_img);
                    }
                });
                if (i == 0) {//默认选择第一个
                    selectGuiGeView = textView;
                    selectGuiGeView.setSolidColor(mContext.getResources().getColor(R.color.green));
                    selectGuiGeView.complete();

                    selectGuiGeObj = list.get((int) textView.getTag());
                    tv_guige_price.setText("¥" + list.get((int) textView.getTag()).getPrice());
                    tv_guige_kucun.setText("库存" + list.get((int) textView.getTag()).getStock() + "件");
                    Glide.with(mContext).load(list.get((int) textView.getTag()).getImages()).error(R.color.c_press).into(iv_guige_img);
                }
                fl_guige.addView(textView);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 100:
                    isCollect = data.getBooleanExtra(Constant.IParam.isCollect, false);
                    if (isCollect) {
                        iv_goods_detail_collect.setImageResource(R.mipmap.collected);
                    } else {
                        iv_goods_detail_collect.setImageResource(R.mipmap.collect);
                    }
                    break;
            }
        }
    }
}
