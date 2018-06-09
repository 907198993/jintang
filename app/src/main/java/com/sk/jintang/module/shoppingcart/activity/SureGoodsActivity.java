package com.sk.jintang.module.shoppingcart.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.SPUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.adapter.ViewHolder;
import com.github.baseclass.rx.IOCallBack;
import com.sk.jintang.Config;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.broadcast.PaySuccessBro;
import com.sk.jintang.module.my.network.response.AddressObj;
import com.sk.jintang.module.orderclass.activity.AddressListActivity;
import com.sk.jintang.module.orderclass.activity.FaPiaoInfoActivity;
import com.sk.jintang.module.orderclass.activity.YouHuiQuanActivity;
import com.sk.jintang.module.orderclass.bean.OrderBean;
import com.sk.jintang.module.orderclass.network.ApiRequest;
import com.sk.jintang.module.orderclass.network.request.ShoppingCartItem;
import com.sk.jintang.module.orderclass.network.response.CommitGoodsObj;
import com.sk.jintang.module.shoppingcart.Constant;
import com.sk.jintang.module.shoppingcart.adapter.SureGoodsListAdapter;
import com.sk.jintang.module.shoppingcart.network.response.Remark;
import com.sk.jintang.module.shoppingcart.network.response.ShoppingCartObj;
import com.sk.jintang.module.shoppingcart.network.response.StoreOrderItem;
import com.sk.jintang.module.shoppingcart.network.response.SureGoodsItem;
import com.sk.jintang.module.shoppingcart.network.response.SureOrderObj;
import com.sk.jintang.tools.AndroidUtils;
import com.sk.jintang.tools.DividerGridItemDecoration;
import com.sk.jintang.tools.alipay.AliPay;
import com.sk.jintang.tools.alipay.OrderInfoUtil2_0;
import com.sk.jintang.tools.alipay.PayResult;
import com.sk.jintang.wxapi.PayResultActivity;
import com.sk.jintang.wxapi.WXPay;
import com.suke.widget.SwitchButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;


public class SureGoodsActivity extends BaseActivity   {
    @BindView(R.id.rv_sure_order)
    RecyclerView rv_sure_order;

    LoadMoreAdapter adapter;
    @BindView(R.id.tv_sure_order_name)
    TextView tv_sure_order_name;
    @BindView(R.id.tv_sure_order_phone)
    TextView tv_sure_order_phone;
    @BindView(R.id.tv_sure_order_address)
    TextView tv_sure_order_address;
//    @BindView(R.id.tv_sure_order_num)
//    TextView tv_sure_order_num;
//    @BindView(R.id.tv_sure_order_xiaoji)
//    TextView tv_sure_order_xiaoji;
    @BindView(R.id.tv_sure_order_fapiao)
    TextView tv_sure_order_fapiao;
//    @BindView(R.id.et_sure_order_liuyan)
//    EditText et_sure_order_liuyan;
    @BindView(R.id.tv_sure_order_vouchers)
    TextView tv_sure_order_vouchers;
    @BindView(R.id.tv_sure_order_ysd)
    TextView tv_sure_order_ysd;
    @BindView(R.id.sb_sure_order)
    SwitchButton sb_sure_order;
//    @BindView(R.id.cb_sure_huodao)
//    CheckBox cb_sure_huodao;
    @BindView(R.id.tv_sure_order_heji)
    TextView tv_sure_order_heji;
    @BindView(R.id.ll_sure_order_select_address)
    LinearLayout ll_sure_order_select_address;
//    @BindView(R.id.ll_huodao)
//    LinearLayout ll_huodao;
    @BindView(R.id.ll_sure_order_address)
    LinearLayout ll_sure_order_address;
    @BindView(R.id.ll_sure_order_youhui)
    LinearLayout ll_sure_order_youhui;
    @BindView(R.id.ll_sure_order_ysd)
    LinearLayout ll_sure_order_ysd;
    private ShoppingCartItem item;
    private String faPiaoType="";
    private String faPiaoHead="";
    private String faPiaoName="";
    private String faPiaoCode="";
    private int youHuiMoney=0;
    private int addressId;
    private int youHuiQuanId=0;
    private int ysd=0;
    double orderTotalMoney;
    private PaySuccessBro paySuccessBro;
    private String actionStr,goodsId,guiGeId,goodsNum;
    private double youFei;//邮费
    private double beanProportion=100;
    private String youHuiNum;
    private boolean isHourDao;
    private boolean isSpecialBuy;//特殊通道
    private String isBuyNow;//1或0  1为立即购买，0 为购物车购买
    private double minMoney;
    private String cityStr="";

    private String  addressID;//默认地址ID
    private  List<SureOrderObj.OrderGoodsListBean> orderGoodsListBean;
    ArrayList<ShoppingCartObj> shoppingCartObjList;
    private  ShoppingCartItem  specialGoodsItem;

    List remarkList = new ArrayList();
    Remark remark  ;//留

    @Override
    protected int getContentView() {
        setAppTitle("确认订单");
        return R.layout.act_sure_goods;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        remarkList.clear();
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(paySuccessBro);
    }


    public void saveEditData(int position, String str,int storeId) {
        Log.d("SureGoodsActivity",str+"----"+position);

    }


    @Override
    protected void initView() {

        paySuccessBro=new PaySuccessBro(new PaySuccessBro.PaySuccessBroInter() {
            @Override
            public void paySuccessBro() {
                finish();
            }
        });
        LocalBroadcastManager.getInstance(mContext).registerReceiver(paySuccessBro,new IntentFilter(Config.Bro.paySuccessBro));

        shoppingCartObjList = getIntent().getParcelableArrayListExtra(Constant.IParam.shoppingGoods);
        actionStr=getIntent().getAction();
        isHourDao = getIntent().getBooleanExtra(com.sk.jintang.module.orderclass.Constant.IParam.hourDao, false);
        isBuyNow = getIntent().getStringExtra(com.sk.jintang.module.orderclass.Constant.IParam.is_buy_now);

        //特殊通道购买的商品列表
        specialGoodsItem   = (ShoppingCartItem) getIntent().getSerializableExtra(Constant.IParam.specialShoppingGoods);
        isSpecialBuy = getIntent().getBooleanExtra(com.sk.jintang.module.orderclass.Constant.IParam.is_special_buy_now, false);
        Log.i("===","==="+isHourDao);
//        ll_huodao.setVisibility(View.GONE);
        ll_sure_order_ysd.setVisibility(View.VISIBLE);

        if(Constant.IParam.tuanGou.equals(actionStr)){//团购下单
             goodsId=getIntent().getStringExtra(Constant.IParam.goodsId);
             guiGeId=getIntent().getStringExtra(Constant.IParam.guiGeId);
            ll_sure_order_youhui.setVisibility(View.GONE);
        }else if(Constant.IParam.xianShi.equals(actionStr)){//限时抢购下单

            goodsId=getIntent().getStringExtra(Constant.IParam.goodsId);
            goodsNum=getIntent().getStringExtra(Constant.IParam.goodsNum);
            guiGeId=getIntent().getStringExtra(Constant.IParam.guiGeId);
            ll_sure_order_youhui.setVisibility(View.GONE);
        }else{
            ll_sure_order_youhui.setVisibility(View.VISIBLE);
            if(isHourDao){//24小时到
                guiGeId=getIntent().getStringExtra(Constant.IParam.guiGeId);
                goodsId=getIntent().getStringExtra(Constant.IParam.goodsId);
                goodsNum=getIntent().getStringExtra(Constant.IParam.goodsNum);
//                ll_huodao.setVisibility(View.VISIBLE);
                ll_sure_order_ysd.setVisibility(View.GONE);
            }
        }


        adapter = new LoadMoreAdapter<SureOrderObj.OrderGoodsListBean>(mContext, R.layout.item_sure_goods,0) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, SureOrderObj.OrderGoodsListBean bean) {


//                View ll_change_num = holder.getView(R.id.ll_change_num);
//                if(Constant.IParam.tuanGou.equals(actionStr)){
//                    ll_change_num.setVisibility(View.INVISIBLE);
//                }else if(Constant.IParam.xianShi.equals(actionStr)){
//                    ll_change_num.setVisibility(View.INVISIBLE);
//                }else{
//                    ll_change_num.setVisibility(View.VISIBLE);
//                }
                ImageView imageView = holder.getImageView(R.id.iv_item_sure_shop_image);
                Glide.with(mContext).load(bean.getStore().getStoreImg()).error(R.color.c_press).into(imageView);
                holder.setText(R.id.tv_item_sure_order_shop_title,bean.getStore().getStoreName())
                      .setText(R.id.tv_sure_order_shop_goods_num,"共"+bean.getCount()+"件商品")
                        .setText(R.id.tv_sure_order_xiaoji,"¥"+bean.getXiaoji())
                        .setText(R.id.tv_sure_order_kuaidi,"快递¥"+bean.getYunfei());


                EditText et_sure_order_liuyan = (EditText) holder.getView(R.id.et_sure_order_liuyan);
                et_sure_order_liuyan.setTag(position);//存tag值


                et_sure_order_liuyan.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        Log.d("SureGoodsActivity","bean.getStore().getStoreID()"+bean.getStore().getStoreID());
//                        ((SureGoodsActivity)mContext).saveEditData(position, s.toString(),bean.getStore().getStoreID());



                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(s!=null){
                            int position = (int) et_sure_order_liuyan.getTag();//取tag值
                            ((SureGoodsActivity)mContext).saveEditData(position, s.toString(),bean.getStore().getStoreID());
                            bean.setLiuyan(s.toString());

                        }
                    }
                });

              //商家下面的商品订单列表
                SureGoodsListAdapter sureGoodsListAdapter = new SureGoodsListAdapter(mContext,0);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                RecyclerView c = (RecyclerView) holder.getView(R.id.rv_sure_order_goodslist);
                c.setLayoutManager(new GridLayoutManager(mContext, 1));
                c.addItemDecoration(new DividerGridItemDecoration(mContext, PhoneUtils.dip2px(mContext,4),R.color.white));
                c.setAdapter(sureGoodsListAdapter);
                c.setVisibility(View.VISIBLE);
                sureGoodsListAdapter.setList(bean.getGoodsList(),true);
                holder.getView(R.id.linear_shop_title).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent();
                        intent.putExtra(com.sk.jintang.module.my.Constant.IParam.storeId,bean.getStore().getStoreID()+"");
                        STActivity(intent,ShopActivity.class);
                    }
                });
//
//                mCategoryTwodapter=new CategoryTwoAdapter(mContext,0);
//                recyclerviewWares.setAdapter(mCategoryTwodapter);
//                recyclerviewWares.setNestedScrollingEnabled(false);
//                mCategoryTwodapter.setOnItem2ClickListener(this);
//                recyclerviewWares.setLayoutManager(new GridLayoutManager(mContext, 3));
//                recyclerviewWares.addItemDecoration(new DividerGridItemDecoration(mContext, PhoneUtils.dip2px(mContext,1)));


//
//                holder.setText(R.id.tv_item_sure_order_title,bean.getGoods_name())
//                        .setText(R.id.tv_sure_order_num,bean.getNumber()+"")
//                        .setText(R.id.tv_item_sure_order_guige,bean.getSpecification())
//                        .setText(R.id.tv_item_sure_order_price,"¥"+bean.getPrice());
//
//                ImageView iv_sure_order_jian = holder.getImageView(R.id.iv_sure_order_jian);
//                iv_sure_order_jian.setOnClickListener(new MyOnClickListener() {
//                    @Override
//                    protected void onNoDoubleClick(View view) {
//                        if (bean.getNumber() > 1) {
////                            guiGeNum--;
//                            int number = bean.getNumber();
//                            number--;
//                            getList().get(position).setNumber(number);
//                            notifyDataSetChanged();
//                            jiSuanNumAndPrice();
//                        }
//                    }
//                });
//                ImageView iv_sure_order_jia = holder.getImageView(R.id.iv_sure_order_jia);
//                iv_sure_order_jia.setOnClickListener(new MyOnClickListener() {
//                    @Override
//                    protected void onNoDoubleClick(View view) {
//                        if (bean.getNumber()  >= bean.getStock()) {
//                            showMsg("购买量不能超过库存");
//                        } else {
//                            int number = bean.getNumber();
//                            number++;
//                            getList().get(position).setNumber(number);
//                            notifyDataSetChanged();
//                            jiSuanNumAndPrice();
//                        }
//                    }
//                });
            }
        };

        rv_sure_order.setLayoutManager(new LinearLayoutManager(mContext));
        rv_sure_order.setNestedScrollingEnabled(false);
//        rv_sure_order.addItemDecoration(getItemDivider());
        rv_sure_order.addItemDecoration(new DividerGridItemDecoration(mContext, PhoneUtils.dip2px(mContext, 5)));
        rv_sure_order.setAdapter(adapter);

        sb_sure_order.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if(isChecked){
                    if(ysd>0){
                        double totalMoney = AndroidUtils.jianFa(orderTotalMoney, AndroidUtils.chuFa(ysd, beanProportion, 2));
//                        youHuiMoney
                        double result = AndroidUtils.jianFa(totalMoney, youHuiMoney);
                        tv_sure_order_heji.setText("¥"+result);
                    }
                }else{
                    //
                    double result = AndroidUtils.jianFa(orderTotalMoney, youHuiMoney);
                    tv_sure_order_heji.setText("¥"+result);
                }
            }
        });
//        cb_sure_huodao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if (isChecked) {
//                    MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
//                    mDialog.setMessage("1.满"+minMoney+"元满足配送条件\n2.当天上午十二点以后下单才能享受货到付款服务\n3.目前支持以下城市货到付款:"+cityStr);
//                    mDialog.setPositiveButton(new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//                    mDialog.create().show();
//                }
//                if(sb_sure_order.isChecked()){
//                    if(ysd>0){
//                        double totalMoney = AndroidUtils.jianFa(orderTotalMoney, AndroidUtils.chuFa(ysd, beanProportion, 2));
//                        double result = AndroidUtils.jianFa(totalMoney, youHuiMoney);
//                        tv_sure_order_heji.setText("¥"+result);
//                    }
//                }else{
//                    double result = AndroidUtils.jianFa(orderTotalMoney, youHuiMoney);
//                    tv_sure_order_heji.setText("¥"+result);
//                }
//            }
//        });

    }

//    private void jiSuanNumAndPrice() {
//        double totalPrice=0;
//        for (int i = 0; i < adapter.getList().size(); i++) {
//            SureOrderObj.GoodsListBean bean = (SureOrderObj.GoodsListBean) adapter.getList().get(i);
//            double price = bean.getPrice();
//            int number = bean.getNumber();
//            totalPrice=AndroidUtils.jiaFa(totalPrice,AndroidUtils.chengFa(price,number));
//        }
//        tv_sure_order_xiaoji.setText("¥"+totalPrice);
//        orderTotalMoney=totalPrice;
//        orderTotalMoney=AndroidUtils.jiaFa(orderTotalMoney,youFei);
//        orderTotalMoney=AndroidUtils.jianFa(orderTotalMoney,youHuiMoney);
//        if(sb_sure_order.isChecked()){
//            if(ysd>0){
//                double totalMoney = AndroidUtils.jianFa(orderTotalMoney, AndroidUtils.chuFa(ysd, beanProportion, 2));
//                tv_sure_order_heji.setText("¥"+totalMoney);
//            }
//        }else{
//            tv_sure_order_heji.setText("¥"+orderTotalMoney);
//        }
//    }



    @Override
    protected void initData() {
        addressID = SPUtils.getPrefString(mContext, Config.default_address,"0");
        showProgress();
        getData();
    }



    private void getData() {
        if(isHourDao){
            item = new ShoppingCartItem();
            List<ShoppingCartItem.BodyBean> body = new ArrayList<>();
            ShoppingCartItem.BodyBean bodyBean;
            for (int i = 0; i < shoppingCartObjList.size(); i++) {
                ShoppingCartObj obj = shoppingCartObjList.get(i);
                bodyBean = new ShoppingCartItem.BodyBean();
                bodyBean.setShopping_cart_id(obj.getId());
                bodyBean.setGoods_id(obj.getGoods_id());
                bodyBean.setSpecification_id(obj.getSpecification_id());
                bodyBean.setNumber(obj.getNumber());
                body.add(bodyBean);
            }
            item.setBody(body);

            Map<String,String>map=new HashMap<String,String>();
            map.put("user_id", getUserId());
            map.put("is_byNow",isBuyNow);
            map.put("addressID",addressID);
            map.put("sign",  GetSign.getSign(map));

            ApiRequest.hourDaoSureOrder(map,item, new MyCallBack<SureOrderObj>(mContext,pcfl,pl_load) {
                @Override
                public void onSuccess(SureOrderObj obj) {
//                    if(notEmpty(obj.getCity_list().getCity_list())){
//                        for (int i = 0; i < obj.getCity_list().getCity_list().size(); i++) {
//                            cityStr = cityStr+obj.getCity_list().getCity_list().get(i)+",";
//                        }
//                        cityStr=cityStr.substring(0,cityStr.length()-1);
//                    }
//                    minMoney = obj.getCity_list().getMin_money();
                    setData(obj);
                    orderGoodsListBean= obj.getOrderGoods_list();

                }
            });
        }else if(isSpecialBuy){
            Map<String,String>map=new HashMap<String,String>();
            map.put("user_id", getUserId());
            map.put("sign", GetSign.getSign(map));
            ApiRequest.specialSureOrder(map,specialGoodsItem, new MyCallBack<SureOrderObj>(mContext,pcfl,pl_load) {
                @Override
                public void onSuccess(SureOrderObj obj) {
//                    if(notEmpty(obj.getCity_list().getCity_list())){
//                        for (int i = 0; i < obj.getCity_list().getCity_list().size(); i++) {
//                            cityStr = cityStr+obj.getCity_list().getCity_list().get(i)+",";
//                        }
//                        cityStr=cityStr.substring(0,cityStr.length()-1);
//                    }
//                    minMoney = obj.getCity_list().getMin_money();
                    setData(obj);
                    orderGoodsListBean= obj.getOrderGoods_list();
                }
            });
        }else{
            if(Constant.IParam.tuanGou.equals(actionStr)){//团购下单
                Map<String, String> map = new HashMap<String, String>();
                map.put("goods_id", goodsId);
                map.put("specification_id", guiGeId);
                map.put("user_id", getUserId());
                map.put("sign", GetSign.getSign(map));
                ApiRequest.tuanGouSureOrder(map, new MyCallBack<SureOrderObj>(mContext, pcfl, pl_load) {
                    @Override
                    public void onSuccess(SureOrderObj obj) {
                        setData(obj);
                    }
                });
            }else if(Constant.IParam.xianShi.equals(actionStr)){//限时抢购下单
//            xianShiSureOrder();
                Map<String, String> map = new HashMap<String, String>();
                map.put("goods_id", goodsId);
                map.put("specification_id", guiGeId==null?"0":guiGeId);
                map.put("number",goodsNum==null?"1":goodsNum);
                map.put("user_id", getUserId());
                map.put("sign", GetSign.getSign(map));
                ApiRequest.xianShiSureOrder(map, new MyCallBack<SureOrderObj>(mContext, pcfl, pl_load) {
                    @Override
                    public void onSuccess(SureOrderObj obj) {
                        setData(obj);
                    }
                });
            }else{//普通商品下单
                Map<String, String> map = new HashMap<String, String>();
                map.put("user_id", getUserId());
                map.put("sign", GetSign.getSign(map));
                map.put("is_byNow",isBuyNow);
                ApiRequest.hourDaoSureOrder(map, item, new MyCallBack<SureOrderObj>(mContext, pcfl, pl_load) {
                    @Override
                    public void onSuccess(SureOrderObj obj) {
                        setData(obj);
                        orderGoodsListBean= obj.getOrderGoods_list();
                    }
                });
            }
        }
    }
    public void setYouHuiNum(String num){
        if(TextUtils.isEmpty(num)){
            tv_sure_order_vouchers.setText(youHuiNum+"张可用");
        }else{
            tv_sure_order_vouchers.setText(num+"张可用");
            youHuiNum=num;
        }
    }
    private void setData(SureOrderObj obj) {
        youHuiNum = obj.getYouhui_num()+"";
        setYouHuiNum(youHuiNum);
        beanProportion = obj.getKeeping_bean_proportion();//1元多少养生豆

        if(((obj.getAddress_list().toString().length()<1))){
            ll_sure_order_select_address.setVisibility(View.GONE);
            ll_sure_order_address.setVisibility(View.VISIBLE);
            SureOrderObj.AddressListBean addressListBean = (SureOrderObj.AddressListBean) obj.getAddress_list();
            addressId=addressListBean.getId();

            tv_sure_order_name.setText(addressListBean.getRecipient());
            tv_sure_order_phone.setText(addressListBean.getPhone());
            tv_sure_order_address.setText("收货地址: "+addressListBean.getShipping_address()+""+addressListBean.getShipping_address_details());
        }else{
            ll_sure_order_select_address.setVisibility(View.VISIBLE);
            ll_sure_order_address.setVisibility(View.GONE);
        }
//        tv_sure_order_num.setText("共"+obj.getGong()+"件商品");
//        tv_sure_order_xiaoji.setText("¥"+obj.getSubtotal());
        tv_sure_order_heji.setText("¥"+obj.getTotal_up());
        orderTotalMoney = obj.getTotal_up();
//        youFei = obj.getCourier();
//        tv_sure_order_kuaidi.setText("快递¥"+ youFei);
        tv_sure_order_ysd.setText(obj.getKeeping_bean()+"");
        ysd=obj.getKeeping_bean();

       adapter.setList(obj.getOrderGoods_list(),true);


    }


    @OnClick({R.id.ll_sure_order_select_address,R.id.ll_sure_order_address, R.id.tv_sure_order_commit,R.id.tv_sure_order_fapiao,R.id.tv_sure_order_vouchers})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_sure_order_select_address:
            case R.id.ll_sure_order_address:
                Intent addressIntent=new Intent();
                addressIntent.putExtra(Config.IParam.selectAddress,true);
                STActivityForResult(addressIntent,AddressListActivity.class,300);
                break;
            case R.id.tv_sure_order_commit:
//                if(cb_sure_huodao.isChecked()){
//                    MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
//                    mDialog.setMessage("是否提交货到付款订单?");
//                    mDialog.setNegativeButton(new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//                    mDialog.setPositiveButton(new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
////                            commitHourDaoGoods(R.id.rb_pay_online);
//                        }
//                    });
//                    mDialog.create().show();
//                }else{
                    selectPay(getSStr(tv_sure_order_heji));
//                }
                break;
            case R.id.tv_sure_order_fapiao:
                Intent faPiaoIntent=new Intent();
                faPiaoIntent.putExtra(com.sk.jintang.module.orderclass.Constant.IParam.faPiaoType,faPiaoType);
                faPiaoIntent.putExtra(com.sk.jintang.module.orderclass.Constant.IParam.faPiaoHead,faPiaoHead);
                faPiaoIntent.putExtra(com.sk.jintang.module.orderclass.Constant.IParam.faPiaoName,faPiaoName);
                faPiaoIntent.putExtra(com.sk.jintang.module.orderclass.Constant.IParam.faPiaoCode,faPiaoCode);

                STActivityForResult(faPiaoIntent,FaPiaoInfoActivity.class,100);
                break;
            case R.id.tv_sure_order_vouchers:
                Intent intent=new Intent();
                intent.putExtra(com.sk.jintang.module.orderclass.Constant.IParam.orderMoney,orderTotalMoney);
                STActivityForResult(intent,YouHuiQuanActivity.class,200);
                break;
        }
    }

//    private void commitXianShiGoods(int checkId) {
//        showLoading();
//        SureOrderObj.GoodsListBean bean= (SureOrderObj.GoodsListBean) adapter.getList().get(0);
//        Map<String,String>map=new HashMap<String,String>();
//        map.put("goods_id",goodsId);
//        map.put("specification_id",guiGeId);
//        map.put("user_id",getUserId());
//        map.put("number",bean.getNumber()+"");
//        map.put("addres_id",addressId+"");
//        map.put("invoice_type",faPiaoType);
//        map.put("invoice_code",faPiaoHead);
//        map.put("invoice_name",faPiaoName);
//        map.put("invoice_tax_number",faPiaoCode);
//        map.put("buyer_msg",getSStr(et_sure_order_liuyan));
//        map.put("coupon_id",youHuiQuanId+"");
//        map.put("keeping_bean",sb_sure_order.isChecked()?ysd+"":"0");
//        map.put("sign",GetSign.getSign(map));
//        ApiRequest.commitXianShiSureOrder(map, new MyCallBack<CommitGoodsObj>(mContext) {
//            @Override
//            public void onSuccess(CommitGoodsObj obj) {
//                String orderNo = obj.getOrder_no();
//                double total = obj.getCombined();
//
//                startPay(checkId,orderNo,total);
//            }
//        });
//    }

//    private void commitTuanGouGoods(int checkId) {
//        showLoading();
//        SureOrderObj.GoodsListBean bean= (SureOrderObj.GoodsListBean) adapter.getList().get(0);
//        Map<String,String>map=new HashMap<String,String>();
//        map.put("goods_id",goodsId);
//        map.put("specification_id",guiGeId);
//        map.put("user_id",getUserId());
//        map.put("number",bean.getNumber()+"");
//        map.put("addres_id",addressId+"");
//        map.put("invoice_type",faPiaoType);
//        map.put("invoice_code",faPiaoHead);
//        map.put("invoice_name",faPiaoName);
//        map.put("invoice_tax_number",faPiaoCode);
//        map.put("buyer_msg",getSStr(et_sure_order_liuyan));
//        map.put("coupon_id",youHuiQuanId+"");
//        map.put("keeping_bean",sb_sure_order.isChecked()?ysd+"":"0");
//        map.put("sign",GetSign.getSign(map));
//        ApiRequest.commitTuanGouGoods(map, new MyCallBack<CommitGoodsObj>(mContext) {
//            @Override
//            public void onSuccess(CommitGoodsObj obj) {
//
//                String orderNo = obj.getOrder_no();
//                double total = obj.getCombined();
//
//                startPay(checkId,orderNo,total);
//            }
//        });
//    }

    private void startPay(int checkId,String orderNo, double totalPrice) {
        switch (checkId) {
            case R.id.rb_pay_zhifubao:
                zhiFuBaoPay(  orderNo,  totalPrice);
                break;
            case R.id.rb_pay_weixin:
                OrderBean orderBean =new OrderBean();
                orderBean.body="爱尚养御订单";
                orderBean.nonceStr=getRnd();
                orderBean.out_trade_no=orderNo;
//                        orderBean.totalFee=AndroidUtils.mul(1,1);
                orderBean.totalFee=AndroidUtils.mul(totalPrice,100);
                orderBean.IP= AndroidUtils.getIP(mContext);
                weiXinPay(orderBean);
                break;
            case R.id.rb_pay_online:
                onLinePay(orderNo,totalPrice);
                break;
        }
    }

//    private void commitGoods(int checkId) {
//        showLoading();
//        item = new ShoppingCartItem();
//        List<ShoppingCartItem.BodyBean> body = new ArrayList<>();
//        ShoppingCartItem.BodyBean bodyBean;
//        for (int i = 0; i < adapter.getList().size(); i++) {
//            SureOrderObj.GoodsListBean obj = (SureOrderObj.GoodsListBean) adapter.getList().get(i);
//            bodyBean = new ShoppingCartItem.BodyBean();
//            bodyBean.setShopping_cart_id(obj.getShopping_cart_id());
//            bodyBean.setGoods_id(obj.getGoods_id());
//            bodyBean.setSpecification_id(obj.getSpecification_id());
//            bodyBean.setNumber(obj.getNumber());
//            body.add(bodyBean);
//        }
//        item.setBody(body);
//        Map<String,String>map=new HashMap<String,String>();
//        map.put("user_id",getUserId());
//        map.put("addres_id",addressId+"");
//        map.put("invoice_type",faPiaoType);
//        map.put("invoice_code",faPiaoHead);
//        map.put("invoice_name",faPiaoName);
//        map.put("invoice_tax_number",faPiaoCode);
//        map.put("buyer_msg",getSStr(et_sure_order_liuyan));
//        map.put("coupon_id",youHuiQuanId+"");
//        map.put("keeping_bean",sb_sure_order.isChecked()?ysd+"":"0");
//        map.put("sign",GetSign.getSign(map));
//        ApiRequest.commitGoods(map,item, new MyCallBack<CommitGoodsObj>(mContext) {
//            @Override
//            public void onSuccess(CommitGoodsObj obj) {
//                Intent intent = new Intent();
//                intent.setAction(Config.Bro.addShoppingCart);
//                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
//                String orderNo = obj.getOrder_no();
//                double total = obj.getCombined();
//
//                startPay(checkId,orderNo,total);
//            }
//        });
//
//    }

    private void selectPay( String totalPrice) {
        BottomSheetDialog payDialog = new BottomSheetDialog(mContext);
        payDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        payDialog.setCancelable(false);
        payDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(payDialog.isShowing()&&keyCode== KeyEvent.KEYCODE_BACK){
                    payDialog.dismiss();
                    return true;
                }
                return false;
            }
        });
        payDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
//                finish();
            }
        });
        View payView = LayoutInflater.from(mContext).inflate(R.layout.popu_select_pay, null);
        ImageView iv_pay_cancle = payView.findViewById(R.id.iv_pay_cancle);
        iv_pay_cancle.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                payDialog.dismiss();
            }
        });
        TextView tv_pay_total = payView.findViewById(R.id.tv_pay_total);
        tv_pay_total.setText(totalPrice);
        RadioGroup rg_select_pay = payView.findViewById(R.id.rg_select_pay);
        TextView tv_pay_commit = payView.findViewById(R.id.tv_pay_commit);
        tv_pay_commit.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {


                int checkedRadioButtonId = rg_select_pay.getCheckedRadioButtonId();
                if(isHourDao){//小时到
                  commitHourDaoGoods(checkedRadioButtonId);
                }else if(isSpecialBuy){
                    commitHourDaoGoods(checkedRadioButtonId);
                }else{
                    if(Constant.IParam.tuanGou.equals(actionStr)){//团购下单
//                        commitTuanGouGoods(checkedRadioButtonId);
                    }else if(Constant.IParam.xianShi.equals(actionStr)){//限时抢购下单
//                        commitXianShiGoods(checkedRadioButtonId);
                    }else{//普通商品提交订单
                        commitHourDaoGoods(checkedRadioButtonId);
                    }
                }


                payDialog.dismiss();
            }


        });
        payDialog.setContentView(payView);
        payDialog.show();
    }

    private void commitHourDaoGoods(int checkId) {
        remarkList.clear();
        StoreOrderItem storeOrderItem = new StoreOrderItem();
        StoreOrderItem.StoreOrder  storeOrder = new StoreOrderItem.StoreOrder();
        List<SureGoodsItem> goods_list = new ArrayList<>();
        SureGoodsItem sureGoodsItem ;




        for (int a = 0; a < orderGoodsListBean.size(); a++) {
            remark = new Remark();
            remark.setRemark(orderGoodsListBean.get(a).getLiuyan());
            remark.setStoreID(orderGoodsListBean.get(a).getStore().getStoreID());
            remarkList.add(a,remark);
            storeOrder.setRemarkList(remarkList);//存储用户留言
            //订单商铺
            SureOrderObj.OrderGoodsListBean obj = orderGoodsListBean.get(a);
             for(int j = 0; j < obj.getGoodsList().size();j++){//
                 sureGoodsItem = new SureGoodsItem();
                 SureOrderObj.OrderGoodsListBean.GoodsListBean goodsListBean = obj.getGoodsList().get(j);
                 sureGoodsItem.setGoods_id(goodsListBean.getGoods_id());
                 sureGoodsItem.setShopping_cart_id(goodsListBean.getShopping_cart_id());
                 sureGoodsItem.setSpecification_id(goodsListBean.getSpecification_id());
                 sureGoodsItem.setNumber(goodsListBean.getNumber());
                 goods_list.add(sureGoodsItem);
             }

        }
        storeOrder.setGoods_list(goods_list);
        storeOrderItem.setBody(storeOrder);

        showLoading();
//        SureOrderObj.GoodsListBean bean= (SureOrderObj.GoodsListBean) adapter.getList().get(0);
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("addres_id",addressId+"");
        map.put("invoice_type",faPiaoType);
        map.put("invoice_code",faPiaoHead);
        map.put("invoice_name",faPiaoName);
        map.put("invoice_tax_number",faPiaoCode);
        map.put("coupon_id",youHuiQuanId+"");
        map.put("keeping_bean",sb_sure_order.isChecked()?ysd+"":"0");

        if(isHourDao){
            map.put("is_byNow", isBuyNow);
        }else{
            map.put("is_byNow", "1");
        }
        map.put("sign",GetSign.getSign(map));
        ApiRequest.commitHourDaoGoods(map,storeOrderItem, new MyCallBack<CommitGoodsObj>(mContext) {
            @Override
            public void onSuccess(CommitGoodsObj obj) {
                if(obj.getIs_huodao()==0){
                    String orderNo = obj.getOrder_no();
                    double total = obj.getCombined();
                    startPay(checkId,orderNo,total);
                }else{
                    showMsg(obj.getMsg());
                    finish();
                }
            }
        });
    }

    private void onLinePay(String orderNo, double totalPrice) {
//        Intent intent=new Intent();
//        intent.putExtra(Constant.IParam.orderNo,orderNo);
//        STActivity(intent,OfflinePayActivity.class);
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("order_no",orderNo);
        map.put("money",totalPrice+"");
        map.put("sign",GetSign.getSign(map));
        ApiRequest.yuEPay(map, new MyCallBack<BaseObj>(mContext) {

            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                Intent intent = new Intent();
                intent.setAction("alipay");
                intent.putExtra(Config.IParam.alipay, true);
                intent.putExtra(Config.IParam.result, 0);
                STActivity(intent, PayResultActivity.class);
                finish();
            }
        });

    }

    private void zhiFuBaoPay(String orderNo,double totalPrice) {
        double total;
//        if(BuildConfig.DEBUG){
//            total=0.01;
//        }else{
             total =  totalPrice;
//        }
//        double total =  totalPrice;
//        double total=0.01;
        AliPay bean = new AliPay();
        bean.setTotal_amount(total );
        bean.setOut_trade_no(orderNo);
        bean.setSubject(orderNo + "订单交易");
        bean.setBody("爱尚养御订单");
        String notifyUrl = SPUtils.getPrefString(mContext, Config.payType_ZFB, null);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(bean, notifyUrl);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String sign = OrderInfoUtil2_0.getSign(params, Config.zhifubao_rsa2, true);
        final String orderInfo = orderParam + "&" + sign;
        RXStart(new IOCallBack<Map>() {
            @Override
            public void call(Subscriber<? super Map> subscriber) {
                PayTask payTask = new PayTask(mContext);
                Map<String, String> result = payTask.payV2(orderInfo, true);
                Log.i("msp=====", result.toString());
                subscriber.onNext(result);
                subscriber.onCompleted();
            }

            @Override
            public void onMyNext(Map map) {

                PayResult payResult = new PayResult(map);
                /**
                 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                Log.i("==========", "1==========" + resultInfo);
                String resultStatus = payResult.getResultStatus();
                Log.i("==========", "2==========" + resultStatus);
                // 判断resultStatus 为9000则代表支付成功
                if (TextUtils.equals(resultStatus, "9000")) {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setAction("alipay");
                    intent.putExtra(Config.IParam.alipay, true);
                    intent.putExtra(Config.IParam.result, 0);
                    STActivity(intent, PayResultActivity.class);
                    finish();
                } else if (TextUtils.equals(resultStatus, "6001")) {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    Toast.makeText(mContext, "支付取消请前往订单列表查看当前订单", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setAction("alipay");
                    intent.putExtra(Config.IParam.alipay, true);
                    intent.putExtra(Config.IParam.result, -2);
                    STActivity(intent, PayResultActivity.class);
                    finish();
                    /*Intent intent=new Intent();
                    intent.setAction("alipay");
                    intent.putExtra(Config.IParam.alipay,false);
                    STActivity(intent, PaySuccessActivity.class);*/
                } else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    Toast.makeText(mContext, "支付失败请前往订单列表查看当前订单", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setAction("alipay");
                    intent.putExtra(Config.IParam.alipay, true);
                    intent.putExtra(Config.IParam.result, -1);
                    STActivity(intent, PayResultActivity.class);
                    finish();
                    /*Intent intent = new Intent();
                    intent.setAction("alipay");
                    intent.putExtra(Config.IParam.alipay, false);
                    intent.putExtra(Config.IParam.result, -1);
                    STActivity(intent, PayResultActivity.class);*/
                }
            }
        });
    }
    private void weiXinPay(OrderBean orderBean) {
        new WXPay(mContext).pay(orderBean);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 100:
                    faPiaoType = data.getStringExtra(com.sk.jintang.module.orderclass.Constant.IParam.faPiaoType);
                    faPiaoHead = data.getStringExtra(com.sk.jintang.module.orderclass.Constant.IParam.faPiaoHead);
                    faPiaoName = "";
                    faPiaoCode = "";
                    if("单位".equals(faPiaoHead)){
                        faPiaoCode =data.getStringExtra(com.sk.jintang.module.orderclass.Constant.IParam.faPiaoCode);
                    }
                    faPiaoName =data.getStringExtra(com.sk.jintang.module.orderclass.Constant.IParam.faPiaoName);
                    tv_sure_order_fapiao.setText(faPiaoType+"—"+faPiaoHead);
                break;
                case 200:
                    youHuiQuanId = data.getIntExtra(com.sk.jintang.module.orderclass.Constant.IParam.youHuiId,0);
                    youHuiMoney = data.getIntExtra(com.sk.jintang.module.orderclass.Constant.IParam.youHuiMoney,0);
                    if(youHuiQuanId==0){
                    }else{
                        tv_sure_order_vouchers.setText("¥"+youHuiMoney);
                    }
                    double subtract = AndroidUtils.jianFa(orderTotalMoney, youHuiMoney);
                    if(sb_sure_order.isChecked()){
                         subtract = AndroidUtils.jianFa(subtract, AndroidUtils.chuFa(ysd, beanProportion, 2));
                    }
                    tv_sure_order_heji.setText("¥"+subtract);
                    break;
                case 300:

                        ll_sure_order_select_address.setVisibility(View.GONE);
                        ll_sure_order_address.setVisibility(View.VISIBLE);


                    AddressObj addressObj = (AddressObj) data.getSerializableExtra(Config.IParam.address);
                    addressId=addressObj.getId();
                    tv_sure_order_name.setText(addressObj.getRecipient());
                    tv_sure_order_phone.setText(addressObj.getPhone());
                    tv_sure_order_address.setText("收货地址: "+addressObj.getShipping_address()+""+addressObj.getShipping_address_details());
                    break;
            }
        }
        if(requestCode==200){
            String youHuiNum = SPUtils.getPrefString(mContext, com.sk.jintang.module.orderclass.Constant.IParam.youHuiNum, null);
            if(youHuiQuanId==0){
                setYouHuiNum(youHuiNum);
            }
        }
    }
}
