package com.sk.jintang.module.orderclass.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.CommonTabLayout;
import com.github.androidtools.PhoneUtils;
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
import com.sk.jintang.module.orderclass.fragment.GoodsDetailTuanGouFragment;
import com.sk.jintang.module.orderclass.fragment.GoodsEvaluateTuanGouFragment;
import com.sk.jintang.module.orderclass.network.ApiRequest;
import com.sk.jintang.module.orderclass.network.response.GuiGeObj;
import com.sk.jintang.module.orderclass.network.response.TuanGouDetailObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/9/2.
 */

public class GoodsEvaluateTuanGouActivity extends BaseActivity {

    @BindView(R.id.ctl_goods_evaluate)
    CommonTabLayout ctl_goods_evaluate;
    @BindView(R.id.iv_goods_evaluate_detail_collect)
    ImageView iv_goods_evaluate_detail_collect;

    @BindView(R.id.fl_goods_detail)
    FrameLayout fl_goods_detail;

    @BindView(R.id.tv_evaluate_tuan_gou_detail_no_tuan)
    TextView tv_evaluate_tuan_gou_detail_no_tuan;

    @BindView(R.id.tv_evaluate_tuan_gou_detail_tuan)
    TextView tv_evaluate_tuan_gou_detail_tuan;
    private TuanGouDetailObj goodsDetailObj;

    private GoodsEvaluateTuanGouFragment evaluateFragment;
    private GoodsDetailTuanGouFragment detailFragment;
    private boolean isCollect,isChangeCollect;



    private Dialog shareDialog;
    private String guiGeImg;
    private GuiGeObj selectGuiGeObj;
    private MyTextView selectGuiGeView;
    private int guiGeNum = 1;//记录购买数量

    @Override
    protected int getContentView() {
        setAppRightImg(R.drawable.share);
        return R.layout.act_goods_evaluate_tuan_gou;
    }

    @Override
    protected void initView() {
        goodsDetailObj = (TuanGouDetailObj) getIntent().getSerializableExtra(Constant.IParam.goods);
        tv_evaluate_tuan_gou_detail_no_tuan.setText("¥"+goodsDetailObj.getPrice());
        tv_evaluate_tuan_gou_detail_tuan.setText("¥"+goodsDetailObj.getGroup_price());
        isCollect =  getIntent().getBooleanExtra(Constant.IParam.isCollect,false);
        if (isCollect) {
            iv_goods_evaluate_detail_collect.setImageResource(R.drawable.order3_select);
        } else {
            iv_goods_evaluate_detail_collect.setImageResource(R.drawable.order3);
        }
        evaluateFragment = GoodsEvaluateTuanGouFragment.newInstance(goodsDetailObj);
        getSupportFragmentManager().beginTransaction().add(R.id.fl_goods_detail, evaluateFragment).commitAllowingStateLoss();

        /*ArrayList<CustomTabEntity> list = new ArrayList<>();
        list.add(new TabEntity("评价"));
        list.add(new TabEntity("详情"));
        ctl_goods_evaluate.setTabData(list);
        ctl_goods_evaluate.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switch (position) {
                    case 0:
                        showFragment(evaluateFragment);
                        hideFragment(detailFragment);
                        break;
                    case 1:
                        if (detailFragment == null) {
                            detailFragment = GoodsDetailTuanGouFragment.newInstance(goodsDetailObj);
                            getSupportFragmentManager().beginTransaction().add(R.id.fl_goods_detail, detailFragment).commitAllowingStateLoss();
                        } else {
                            showFragment(detailFragment);
                        }
                        hideFragment(evaluateFragment);
                        break;
                }
            }

            @Override
            public void onTabReselect(int position) {
            }
        });*/
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.app_right_iv,R.id.ll_evaluate_tuan_gou_detail_no_tuan,R.id.ll_evaluate_tuan_gou_detail_tuan,R.id.tv_goods_evaluate_name,R.id.ll_goods_evaluate_detail_kefu, R.id.ll_goods_evaluate_detail_collect, R.id.ll_goods_evaluate_detail_addshoppingcart, R.id.ll_goods_evaluate_detail_buy})
    public void onViewClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.app_right_iv:
                showFenXiang(goodsDetailObj.getGoods_id()+"");
            break;
            case R.id.ll_evaluate_tuan_gou_detail_tuan:
                intent=new Intent();
                intent.putExtra(Constant.IParam.tuanType,Constant.IParam.canTuan);
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.ll_evaluate_tuan_gou_detail_no_tuan:
                intent=new Intent();
                intent.putExtra(Constant.IParam.tuanType,Constant.IParam.noTuan);
                setResult(RESULT_OK,intent);
                finish();
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
            case R.id.ll_goods_evaluate_detail_buy:
                if (TextUtils.isEmpty(getUserId())) {
                    STActivity(LoginActivity.class);
                    return;
                }
                getGuiGeData();
                break;
            case R.id.tv_goods_evaluate_name:
                finish();
                break;
            case R.id.tv_guige_addshoppingcart:
                if (TextUtils.isEmpty(getUserId())) {
                    STActivity(LoginActivity.class);
                    return;
                }
                addShoppingCart();
                break;
            case R.id.tv_guige_buy:
                if (TextUtils.isEmpty(getUserId())) {
                    STActivity(LoginActivity.class);
                    return;
                }
                buyGoods();
                break;
        }
    }
    private void addShoppingCart() {
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
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent(Config.Bro.addShoppingCart));
                showMsg(obj.getMsg());
            }
        });
    }

    private void buyGoods() {
        showLoading();
    }
    private void getGuiGeData() {
        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put("goods_id", goodsDetailObj.getGoods_id()+"");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getGoodsGuiGe(map, new MyCallBack<List<GuiGeObj>>(mContext) {
            @Override
            public void onSuccess(List<GuiGeObj> list) {
                showGuiGe(list);
            }
        });

    }

    private void showGuiGe(List<GuiGeObj> list) {
        shareDialog = new Dialog(mContext);//,R.style.dialogAnimation
        Window win = shareDialog.getWindow();
        win.setGravity(Gravity.CENTER);
        win.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        win.getDecorView().setPadding(0, 0, 0, 0);
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

        View guiGeView = LayoutInflater.from(mContext).inflate(R.layout.popu_xian_shi_goods_guige_select, null);

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
        TextView tv_guige_num = guiGeView.findViewById(R.id.tv_guige_num);
        TextView tv_guige_addshoppingcart = guiGeView.findViewById(R.id.tv_guige_addshoppingcart);
        TextView tv_guige_buy = guiGeView.findViewById(R.id.tv_guige_buy);
        tv_guige_addshoppingcart.setOnClickListener(this);
        tv_guige_buy.setOnClickListener(this);
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
    private View.OnClickListener getListener(int flag, TextView tv_guige_num) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == 0) {//减法
                    if (guiGeNum > 1) {
                        guiGeNum--;
                        tv_guige_num.setText(guiGeNum + "");
                    }
                } else {//加法
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
                textView.setWidth(PhoneUtils.dip2px(mContext, 97));
                textView.setHeight(PhoneUtils.dip2px(mContext, 30));
                textView.setGravity(Gravity.CENTER);
                FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, PhoneUtils.dip2px(mContext, 10), PhoneUtils.dip2px(mContext, 10), 0);
                textView.setLayoutParams(layoutParams);
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
