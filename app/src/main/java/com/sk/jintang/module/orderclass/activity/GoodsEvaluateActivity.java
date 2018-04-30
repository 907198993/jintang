package com.sk.jintang.module.orderclass.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.CommonTabLayout;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.SPUtils;
import com.github.androidtools.inter.MyOnClickListener;
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
import com.sk.jintang.module.orderclass.fragment.GoodsDetailFragment;
import com.sk.jintang.module.orderclass.fragment.GoodsEvaluateFragment;
import com.sk.jintang.module.orderclass.fragment.GoodsTuiJianFragment;
import com.sk.jintang.module.orderclass.network.ApiRequest;
import com.sk.jintang.module.orderclass.network.response.GoodsDetailObj;
import com.sk.jintang.module.orderclass.network.response.GuiGeObj;
import com.sk.jintang.module.shoppingcart.activity.SureGoodsActivity;
import com.sk.jintang.module.shoppingcart.network.response.ShoppingCartObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/9/2.
 */

public class GoodsEvaluateActivity extends BaseActivity {

    @BindView(R.id.ctl_goods_evaluate)
    CommonTabLayout ctl_goods_evaluate;
    @BindView(R.id.iv_goods_evaluate_detail_collect)
    ImageView iv_goods_evaluate_detail_collect;

    @BindView(R.id.ll_goods_evaluate_detail_addshoppingcart)
    TextView ll_goods_evaluate_detail_addshoppingcart;
    @BindView(R.id.ll_goods_evaluate_detail_buy)
    TextView ll_goods_evaluate_detail_buy;
    @BindView(R.id.tv_goods_detail_shopping_cart_num)
    TextView tv_goods_detail_shopping_cart_num;

    @BindView(R.id.fl_goods_detail)
    FrameLayout fl_goods_detail;
    private GoodsDetailObj goodsDetailObj;

    private GoodsEvaluateFragment evaluateFragment;
    private GoodsDetailFragment detailFragment;
    private GoodsTuiJianFragment tuiJianFragment;
    private boolean isCollect,isChangeCollect;



    private Dialog goodsDialog;
    private String guiGeImg;
    private GuiGeObj selectGuiGeObj;
    private MyTextView selectGuiGeView;
    private int guiGeNum = 1;//记录购买数量
    private boolean isHourDao;

    @Override
    protected int getContentView() {
        setAppRightImg(R.drawable.share);
        return R.layout.act_goods_evaluate;
    }

    @Override
    protected void initView() {
        goodsDetailObj = (GoodsDetailObj) getIntent().getSerializableExtra(Constant.IParam.goods);
        isCollect =  getIntent().getBooleanExtra(Constant.IParam.isCollect,false);
        isHourDao =  getIntent().getBooleanExtra(Constant.IParam.hourDao,false);
        if(!isHourDao){
            ll_goods_evaluate_detail_addshoppingcart.setVisibility(View.GONE);
            ll_goods_evaluate_detail_buy.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT,4));
        }
        if (isCollect) {
            iv_goods_evaluate_detail_collect.setImageResource(R.drawable.order3_select);
        } else {
            iv_goods_evaluate_detail_collect.setImageResource(R.drawable.order3);
        }
        evaluateFragment = GoodsEvaluateFragment.newInstance(goodsDetailObj);
        getSupportFragmentManager().beginTransaction().add(R.id.fl_goods_detail, evaluateFragment).commitAllowingStateLoss();

    }

    @Override
    protected void initData() {

        getShoppingCartNum();
    }

    @Override
    protected void myReStart() {
        super.myReStart();
        getShoppingCartNum();
    }

    private void getShoppingCartNum() {
        if (TextUtils.isEmpty(getUserId())) {
            tv_goods_detail_shopping_cart_num.setVisibility(View.INVISIBLE);
            return;
        }
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("sign",GetSign.getSign(map));
        ApiRequest.getShoppingCartNum(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                tv_goods_detail_shopping_cart_num.setText(obj.getShoppingCartCount()+"");
                tv_goods_detail_shopping_cart_num.setVisibility(View.VISIBLE);
            }
        });
    }

    @OnClick({R.id.fl_goods_detail_shopping_cart,R.id.app_right_iv,R.id.tv_goods_evaluate_name,R.id.ll_goods_evaluate_detail_kefu, R.id.ll_goods_evaluate_detail_collect, R.id.ll_goods_evaluate_detail_addshoppingcart, R.id.ll_goods_evaluate_detail_buy})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.app_right_iv:
                showFenXiang(goodsDetailObj.getGoods_id()+"");
                break;
            case R.id.fl_goods_detail_shopping_cart:
                if (TextUtils.isEmpty(SPUtils.getPrefString(mContext, Config.user_id, null))) {
                    STActivity(LoginActivity.class);
                    return;
                }
                /*Intent shoppingIntent=new Intent(Constant.IParam.goShoppingCart);
                shoppingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                STActivity(shoppingIntent,ConversationActivity.class);*/
                STActivity(ShoppingCartActivity.class);
                break;
            case R.id.ll_goods_evaluate_detail_kefu:
                goHX();
                break;
            case R.id.ll_goods_evaluate_detail_collect:
                if (TextUtils.isEmpty(getUserId())) {
                    STActivity(LoginActivity.class);
                    return;
                }
                collectGoods();
                break;
            case R.id.ll_goods_evaluate_detail_addshoppingcart:
                if (TextUtils.isEmpty(getUserId())) {
                    STActivity(LoginActivity.class);
                    return;
                }
                getGuiGeData(0);
                break;
            case R.id.ll_goods_evaluate_detail_buy:
                if (TextUtils.isEmpty(getUserId())) {
                    STActivity(LoginActivity.class);
                    return;
                }
                getGuiGeData(1);
                break;
            case R.id.tv_goods_evaluate_name:
                finish();
                break;
            case R.id.tv_guige_addshoppingcart:
                addShoppingCart();
                break;
            case R.id.tv_guige_buy:
                buyGoods();
                break;
        }
    }
    private void addShoppingCart() {
        if (TextUtils.isEmpty(SPUtils.getPrefString(mContext, Config.user_id, null))) {
            STActivity(LoginActivity.class);
            return;
        }
        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("goods_id",goodsDetailObj.getGoods_id()+"" );
        map.put("number", guiGeNum+"");
        map.put("specification_id", selectGuiGeObj.getId()+"");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.addShoppingCart(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                if(goodsDialog!=null){
                    goodsDialog.dismiss();
                }
                getShoppingCartNum();
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent(Config.Bro.addShoppingCart));
                showMsg(obj.getMsg());
            }
        });
    }

    private void buyGoods() {
        ArrayList<ShoppingCartObj>list=new ArrayList<>();
        ShoppingCartObj obj =new ShoppingCartObj();
        obj.setId(0);
        obj.setNumber(guiGeNum);
        obj.setSpecification_id(selectGuiGeObj.getId());
        obj.setGoods_id((goodsDetailObj.getGoods_id()+""));
        list.add(obj);
        Intent intent=new Intent();
        intent.putExtra(Constant.IParam.hourDao,isHourDao);
        intent.putParcelableArrayListExtra(com.sk.jintang.module.shoppingcart.Constant.IParam.shoppingGoods,list);
            STActivity(intent,SureGoodsActivity.class);
    }

    //0加入购物车，1立即购买
    private void getGuiGeData(int type) {
        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put("goods_id", goodsDetailObj.getGoods_id()+"");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getGoodsGuiGe(map, new MyCallBack<List<GuiGeObj>>(mContext) {
            @Override
            public void onSuccess(List<GuiGeObj> list) {
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

    private void showGuiGe(List<GuiGeObj> list,int type) {
        goodsDialog = new Dialog(mContext);//,R.style.dialogAnimation
        Window win = goodsDialog.getWindow();
        win.setGravity(Gravity.CENTER);
        win.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        win.getDecorView().setPadding(0, 0, 0, 0);
        win.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        win.setAttributes(lp);

        Context context = goodsDialog.getContext();
        int divierId = context.getResources().getIdentifier("android:id/titleDivider", null, null);
        View divider = goodsDialog.findViewById(divierId);
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
                    goodsDialog.dismiss();
                    buyGoods();
                }
            }
        });
        tv_guige_num.setText(guiGeNum + "");
        ll_guige_bg.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                goodsDialog.dismiss();
            }
        });
        iv_guige_cancle.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                goodsDialog.dismiss();
            }
        });
        iv_guige_jian.setOnClickListener(getListener(0, tv_guige_num));
        iv_guige_jia.setOnClickListener(getListener(1, tv_guige_num));
        goodsDialog.setContentView(guiGeView);

        goodsDialog.show();
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
    private void collectGoods() {
        if (TextUtils.isEmpty(SPUtils.getPrefString(mContext, Config.user_id, null))) {
            STActivity(LoginActivity.class);
            return;
        }
        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("goods_id", goodsDetailObj.getGoods_id()+"");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.collectGoods(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                isChangeCollect=true;
//                showMsg(obj.getMsg());
                isCollect = !isCollect;
                if (isCollect) {
                    iv_goods_evaluate_detail_collect.setImageResource(R.drawable.order3_select);
                } else {
                    iv_goods_evaluate_detail_collect.setImageResource(R.drawable.order3);
                }
            }
        });
    }

    @Override
    public void finish() {
        if(isChangeCollect){
            Intent intent=new Intent();
            intent.putExtra(Constant.IParam.isCollect,isCollect);
            setResult(RESULT_OK,intent);
        }
        super.finish();
    }
}
