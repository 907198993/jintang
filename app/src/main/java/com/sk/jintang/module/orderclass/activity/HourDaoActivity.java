package com.sk.jintang.module.orderclass.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.SPUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.customview.FlowLayout;
import com.github.customview.MyTextView;
import com.sk.jintang.Config;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.activity.LoginActivity;
import com.sk.jintang.module.orderclass.Constant;
import com.sk.jintang.module.orderclass.network.ApiRequest;
import com.sk.jintang.module.orderclass.network.response.GuiGeObj;
import com.sk.jintang.module.orderclass.network.response.HourDaoObj;
import com.sk.jintang.tools.DividerGridItemDecoration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/9/1.
 */

public class HourDaoActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener {

    LoadMoreAdapter adapter;
    @BindView(R.id.tv_hour_dao_city)
    TextView tv_hour_dao_city;
    @BindView(R.id.tv_hour_dao_song)
    TextView tv_hour_dao_song;
    @BindView(R.id.tv_hour_dao_shuoming1)
    TextView tv_hour_dao_shuoming1;
    @BindView(R.id.tv_hour_dao_shuoming2)
    TextView tv_hour_dao_shuoming2;
    @BindView(R.id.rv_hour_dao)
    RecyclerView rv_hour_dao;

    @BindView(R.id.rl_hour_dao_shoppingcart)
    RelativeLayout rl_hour_dao_shoppingcart;

    @BindView(R.id.tv_hour_dao_shoppingcart_num)
    TextView tv_hour_dao_shoppingcart_num;

    private int screenWidth;
    private String address="上海市";

    @Override
    protected int getContentView() {
        setAppTitle("24小时到");
        address = SPUtils.getPrefString(mContext, Config.city, "上海市");
        setAppRightTitle(address);
        return R.layout.act_hour_dao;
    }

    @Override
    protected void initView() {
        tv_hour_dao_city.setText(address);
        screenWidth = PhoneUtils.getScreenWidth(mContext);
        int imgWidth = (screenWidth - 2) / 2 - PhoneUtils.dip2px(mContext, 20);
        adapter = new LoadMoreAdapter<HourDaoObj.ListBean>(mContext, R.layout.item_goods, pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, HourDaoObj.ListBean bean) {
                TextView tv_goods_yuanjia = holder.getTextView(R.id.tv_goods_yuanjia);
                if(bean.getOriginal_price()<=0||bean.getOriginal_price()==bean.getPrice()){
                    tv_goods_yuanjia.setVisibility(View.INVISIBLE);
                }else{
                    tv_goods_yuanjia.setText("¥"+bean.getOriginal_price());
                    tv_goods_yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    tv_goods_yuanjia.getPaint().setAntiAlias(true);
                    tv_goods_yuanjia.setVisibility(View.VISIBLE);
                }
                ImageView iv_goods_add_shopping = holder.getImageView(R.id.iv_goods_add_shopping);
                iv_goods_add_shopping.setVisibility(View.VISIBLE);
                iv_goods_add_shopping.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        if (TextUtils.isEmpty(getUserId())) {
                            STActivity(LoginActivity.class);
                            return;
                        }
                        goodsId=bean.getGoods_id()+"";
                        guiGeImg=bean.getGoods_image();
                        getGuiGeData(0,bean.getGoods_id()+"");
                    }
                });

                View tv_goods_baoyou = holder.getView(R.id.tv_goods_baoyou);
                tv_goods_baoyou.setVisibility(bean.getBaoyou()==1?View.VISIBLE:View.GONE);
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

                        Intent intent = new Intent();
                        intent.putExtra(Constant.IParam.goodsId, bean.getGoods_id() + "");
                        intent.putExtra(Constant.IParam.hourDao,true);
                        STActivity(intent, GoodsDetailActivity.class);
                    }
                });
            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_hour_dao.setLayoutManager(new GridLayoutManager(mContext, 2));
        rv_hour_dao.addItemDecoration(new DividerGridItemDecoration(mContext, PhoneUtils.dip2px(mContext, 5)));
        rv_hour_dao.setAdapter(adapter);


        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(1,false);
            }
        });
    }

    @Override
    protected void initData() {
        showProgress();
        getShoppingCartNum();
        getData(1,false);
    }
    @Override
    protected void myReStart() {
        super.myReStart();
        getShoppingCartNum();
    }

    private void getData(int page, boolean isLoad) {
        Map<String,String> map=new HashMap<String,String>();
        map.put("address", address);
        map.put("page",page+"");
        map.put("pagesize",pageSize+"");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getHourDaoList(map, new MyCallBack<HourDaoObj>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(HourDaoObj obj) {
                tv_hour_dao_city.setText(obj.getDelivery_station_name());
                tv_hour_dao_song.setText("("+obj.getDelivery_station_price()+"起送)");
                tv_hour_dao_shuoming1.setText(obj.getDelivery_notice_top());
                tv_hour_dao_shuoming2.setText(obj.getDelivery_notice_down());
                if(isLoad){
                    pageNum++;
                    adapter.addList(obj.getList(),true);
                }else{
                    pageNum=2;
                    adapter.setList(obj.getList(),true);
                }
            }
        });

    }

    @OnClick({R.id.app_right_tv,R.id.iv_hour_dao,R.id.rl_hour_dao_shoppingcart})
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.app_right_tv:
                STActivityForResult(SelectCityActivity.class, 100);
                break;
            case R.id.iv_hour_dao:
                showFenXiang("");
                break;
            case R.id.rl_hour_dao_shoppingcart:
                if (TextUtils.isEmpty(SPUtils.getPrefString(mContext, Config.user_id, null))) {
                    STActivity(LoginActivity.class);
                    return;
                }
                STActivity(ShoppingCartActivity.class);
                break;
        }
    }
    private void getShoppingCartNum() {
        if (TextUtils.isEmpty(getUserId())) {
            tv_hour_dao_shoppingcart_num.setVisibility(View.INVISIBLE);
            return;
        }
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("sign",GetSign.getSign(map));
        ApiRequest.getShoppingCartNum(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                tv_hour_dao_shoppingcart_num.setText(obj.getShoppingCartCount()+"");
                tv_hour_dao_shoppingcart_num.setVisibility(View.VISIBLE);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 100:
                    String city = data.getStringExtra(Constant.IParam.city);
                    setAppRightTitle(city);
                    showLoading();
                    address=city;
                    getData(1,false);
                    break;
            }
        }
    }

    @Override
    public void loadMore() {
        getData(pageNum,true);
    }

    private GuiGeObj selectGuiGeObj;
    private String guiGeImg;
    private String goodsId;
    //0加入购物车，1立即购买,-1查看商品规格，不显示确认按钮
    private void getGuiGeData(int type,String goodsId) {
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
//                        buyGoods();
                    }

                }else{
                    showGuiGe(list,type);
                }
            }
        });

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
                getShoppingCartNum();
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent(Config.Bro.addShoppingCart));
                showMsg(obj.getMsg());
                if (shareDialog != null) {
                    shareDialog.dismiss();
                }
            }
        });
    }

    private Dialog shareDialog;
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

        if(type==-1){
            tv_guige_addshoppingcart.setVisibility(View.VISIBLE);
            tv_guige_buy.setVisibility(View.VISIBLE);
            tv_guige_sure.setVisibility(View.GONE);
        }else{
            tv_guige_addshoppingcart.setVisibility(View.GONE);
            tv_guige_buy.setVisibility(View.GONE);
            tv_guige_sure.setVisibility(View.VISIBLE);
        }

        tv_guige_sure.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                if (noCanBuy(tv_guige_num)) {
                    return;
                }
                if(type==0){//加入购物车
                    addShoppingCart();
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
    private int guiGeNum = 1;//记录购买数量
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
    private MyTextView selectGuiGeView;
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
}
