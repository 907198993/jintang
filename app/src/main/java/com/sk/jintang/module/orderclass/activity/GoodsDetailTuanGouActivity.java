package com.sk.jintang.module.orderclass.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.PhoneUtils;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.customview.MyTextView;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.activity.LoginActivity;
import com.sk.jintang.module.orderclass.Constant;
import com.sk.jintang.module.orderclass.adapter.GoodsDetailImgAdapter;
import com.sk.jintang.module.orderclass.network.ApiRequest;
import com.sk.jintang.module.orderclass.network.response.TuanGouDetailObj;
import com.sk.jintang.module.shoppingcart.activity.SureGoodsActivity;
import com.sk.jintang.module.shoppingcart.network.response.ShoppingCartObj;
import com.sk.jintang.tools.DateUtils;
import com.sk.jintang.tools.GlideLoader;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/9/2.
 */

public class GoodsDetailTuanGouActivity extends BaseActivity {

    @BindView(R.id.bn_tuan_gou_detail)
    Banner bn_tuan_gou_detail;
    @BindView(R.id.tv_tuan_gou_detail_title)
    TextView tv_tuan_gou_detail_title;
    @BindView(R.id.tv_tuan_gou_detail_price)
    TextView tv_tuan_gou_detail_price;
    @BindView(R.id.tv_tuan_gou_detail_before_price)
    TextView tv_tuan_gou_detail_before_price;
    @BindView(R.id.tv_tuan_gou_detail_num)
    MyTextView tv_tuan_gou_detail_num;
    @BindView(R.id.tv_tuan_gou_detail_buy_num)
    TextView tv_tuan_gou_detail_buy_num;
    @BindView(R.id.tv_tuan_gou_detail_need_num)
    TextView tv_tuan_gou_detail_need_num;
    @BindView(R.id.tv_tuan_gou_detail_end_time)
    TextView tv_tuan_gou_detail_end_time;
    @BindView(R.id.tv_tuan_gou_detail_evaluate_num)
    TextView tv_tuan_gou_detail_evaluate_num;
    @BindView(R.id.rv_tuan_gou_detail_evaluate)
    RecyclerView rv_tuan_gou_detail_evaluate;
    @BindView(R.id.ll_tuan_gou_detail_all_pj)
    LinearLayout ll_tuan_gou_detail_all_pj;
    @BindView(R.id.tv_tuan_gou_detail_no_tuan)
    TextView tv_tuan_gou_detail_no_tuan;
    @BindView(R.id.tv_tuan_gou_detail_tuan)
    TextView tv_tuan_gou_detail_tuan;
    @BindView(R.id.tv_tuan_gou_detail_kuaidi)
    TextView tv_tuan_gou_detail_kuaidi;
    @BindView(R.id.rv_tuan_gou_img_detail)
    RecyclerView rv_tuan_gou_img_detail;


    private ArrayList<String> bannerList;
    private String goodsId;

    private long jinEndTime;

    LoadMoreAdapter adapter;

    GoodsDetailImgAdapter imgAdapter;
    private int guiGeId;
    private TuanGouDetailObj tuanGouDetailObj;

    @Override
    protected int getContentView() {
        setAppTitle("团购详情");
        return R.layout.act_tuan_gou_detail;
    }

    @Override
    protected void initView() {
        goodsId = getIntent().getStringExtra(Constant.IParam.goodsId);

        adapter=new LoadMoreAdapter<TuanGouDetailObj.PingjiaListBean>(mContext,R.layout.item_goods_detail_evaluate,0) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, TuanGouDetailObj.PingjiaListBean bean) {
                ImageView imageView = holder.getImageView(R.id.iv_goods_detail_evaluate);
                Glide.with(mContext).load(bean.getPhoto()).error(R.drawable.people).into(imageView);
                holder.setText(R.id.tv_goods_detail_evaluate_name,bean.getNickname())
                        .setText(R.id.tv_goods_detail_evaluate,bean.getContent())
                        .setText(R.id.tv_goods_detail_evaluate_date,bean.getAdd_time());
            }
        };

        rv_tuan_gou_detail_evaluate.setNestedScrollingEnabled(false);
        rv_tuan_gou_detail_evaluate.setLayoutManager(new LinearLayoutManager(mContext));
        rv_tuan_gou_detail_evaluate.addItemDecoration(getItemDivider());
        rv_tuan_gou_detail_evaluate.setAdapter(adapter);

        imgAdapter=new GoodsDetailImgAdapter(mContext,R.layout._item_);

        rv_tuan_gou_img_detail.setNestedScrollingEnabled(false);
        rv_tuan_gou_img_detail.setLayoutManager(new LinearLayoutManager(mContext));
        rv_tuan_gou_img_detail.setAdapter(imgAdapter);

    }

    @Override
    protected void initData() {
        showProgress();
        getData();
    }

    private void getData() {
        Map<String,String> map=new HashMap<String,String>();
        map.put("goods_id",goodsId);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getTuanGouDetail(map, new MyCallBack<TuanGouDetailObj>(mContext,pl_load) {
            @Override
            public void onSuccess(TuanGouDetailObj obj) {
                tuanGouDetailObj = obj;
                guiGeId=obj.getSpecification_id();
                setBanner(obj);
                adapter.setList(obj.getPingjia_list(),true);
                if(notEmpty(obj.getPingjia_list())){
                    ll_tuan_gou_detail_all_pj.setVisibility(View.VISIBLE);
                }else{
                    ll_tuan_gou_detail_all_pj.setVisibility(View.GONE);
                }
                imgAdapter.setList(obj.getGoods_details(),true);

                if(obj.getCourier()==0){
                    tv_tuan_gou_detail_kuaidi.setText("包邮");
                }else{
                    tv_tuan_gou_detail_kuaidi.setText("快递费"+obj.getCourier());
                }
                tv_tuan_gou_detail_title.setText(obj.getGoods_name());
                tv_tuan_gou_detail_price.setText(""+obj.getGroup_price());
                tv_tuan_gou_detail_tuan.setText("¥"+obj.getGroup_price());
                tv_tuan_gou_detail_before_price.setText("¥"+obj.getPrice());
                tv_tuan_gou_detail_no_tuan.setText("¥"+obj.getPrice());
                tv_tuan_gou_detail_before_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                tv_tuan_gou_detail_before_price.getPaint().setAntiAlias(true);
                tv_tuan_gou_detail_num.setText(obj.getGroup_num_people()+"人团");
                tv_tuan_gou_detail_buy_num.setText("已有"+obj.getNumber_tuxedo()+"人参与");
                tv_tuan_gou_detail_need_num.setText((obj.getGroup_num_people()-obj.getNumber_tuxedo())+"");

                tv_tuan_gou_detail_evaluate_num.setText("评价("+obj.getPingjia_num()+")");

                jinEndTime=obj.getEnd_time();
                daoJiShi();
                ///////////////////////////////////////////////////////////////////
            }
        });
    }
    private void setBanner(TuanGouDetailObj obj) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.width= PhoneUtils.getScreenWidth(mContext);
        layoutParams.height=PhoneUtils.getScreenWidth(mContext);
        bn_tuan_gou_detail.setLayoutParams(layoutParams);
        bannerList = new ArrayList<String>();
        for (int i = 0; i < obj.getImg_list().size(); i++) {
            bannerList.add(obj.getImg_list().get(i).getGoods_image());
        }
        bn_tuan_gou_detail.setImages(bannerList);
        bn_tuan_gou_detail.setImageLoader(new GlideLoader());
        bn_tuan_gou_detail.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent=new Intent();
                intent.putStringArrayListExtra(Constant.IParam.imgList,bannerList);
                intent.putExtra(com.sk.jintang.module.my.Constant.IParam.imgIndex,position);
                STActivity(intent,ImageDetailActivity.class);
            }
        });
        bn_tuan_gou_detail.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (bn_tuan_gou_detail != null && bannerList != null) {
            bn_tuan_gou_detail.stopAutoPlay();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (bn_tuan_gou_detail != null && bannerList != null) {
            bn_tuan_gou_detail.startAutoPlay();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(task!=null){
            task.cancel();
        }
        if(timer!=null){
            timer.cancel();
        }
        task=null;
        timer=null;
    }
    private Timer timer;
    private TimerTask task;
    private void daoJiShi() {
        timer = new Timer(true);
        task = new TimerTask() {
            public void run() {
                if (new Date().getTime() > jinEndTime) {
                    new Handler(getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            tv_tuan_gou_detail_end_time.setText("已结束");
                        }
                    });
                } else {
                    new Handler(getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            tv_tuan_gou_detail_end_time.setText( DateUtils.getXCTime(new Date().getTime(), jinEndTime,true));
                        }
                    });
                }

            }
        };

        timer.schedule(task, 0, 1000);
    }

    @OnClick({R.id.ll_goods_detail_question,R.id.tv_tuan_gou_goods_detail_lookevaluate, R.id.ll_tuan_gou_detail_no_tuan, R.id.ll_tuan_gou_detail_tuan})
    public void onViewClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_goods_detail_question:
                intent=new Intent();
                intent.putExtra(com.sk.jintang.module.my.Constant.IParam.goodsId,goodsId);
                STActivity(intent,GoodsQuestionActivity.class);
                break;
            case R.id.tv_tuan_gou_goods_detail_lookevaluate:
                  intent=new Intent();
                intent.putExtra(Constant.IParam.goods,tuanGouDetailObj);
                intent.putExtra(Constant.IParam.isCollect,false);
                STActivityForResult(intent,GoodsEvaluateTuanGouActivity.class,100);
                break;
            case R.id.ll_tuan_gou_detail_no_tuan:
                if (TextUtils.isEmpty(getUserId())) {
                    STActivity(LoginActivity.class);
                    return;
                }
                noTuan();
                break;
            case R.id.ll_tuan_gou_detail_tuan:
                if (new Date().getTime() > jinEndTime) {
                    showMsg("团购已结束无法参团");
                    return;
                }
                if (TextUtils.isEmpty(getUserId())) {
                    STActivity(LoginActivity.class);
                    return;
                }
                canTuan();
                break;
        }
    }

    private void noTuan() {
        if (TextUtils.isEmpty(getUserId())) {
            STActivity(LoginActivity.class);
            return;
        }
        Intent intent;ArrayList<ShoppingCartObj> list=new ArrayList<>();
        ShoppingCartObj obj =new ShoppingCartObj();
        obj.setId(0);
        obj.setNumber(1);
        obj.setSpecification_id(guiGeId);
        obj.setGoods_id((goodsId));
        list.add(obj);
        intent=new Intent();
        intent.putParcelableArrayListExtra(com.sk.jintang.module.shoppingcart.Constant.IParam.shoppingGoods,list);
        STActivity(intent,SureGoodsActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 100:
                    String type = data.getStringExtra(Constant.IParam.tuanType);
                    if(Constant.IParam.noTuan.equals(type)){
                        noTuan();
                    }else{
                        if (new Date().getTime() > jinEndTime) {
                            showMsg("团购已结束无法参团");
                            return;
                        }
                        canTuan();
                    }
                    break;
            }
        }
    }

    private void canTuan() {
        if (TextUtils.isEmpty(getUserId())) {
            STActivity(LoginActivity.class);
            return;
        }
        ArrayList<ShoppingCartObj>list=new ArrayList<>();
        ShoppingCartObj obj =new ShoppingCartObj();
        obj.setId(0);
        obj.setNumber(1);
        obj.setSpecification_id(guiGeId);
        obj.setGoods_id((goodsId));
        list.add(obj);
        Intent intent=new Intent();
        intent.setAction(com.sk.jintang.module.shoppingcart.Constant.IParam.tuanGou);
        intent.putExtra(com.sk.jintang.module.shoppingcart.Constant.IParam.goodsId,goodsId+"");
        intent.putExtra(com.sk.jintang.module.shoppingcart.Constant.IParam.guiGeId,guiGeId+"");
        intent.putParcelableArrayListExtra(com.sk.jintang.module.shoppingcart.Constant.IParam.shoppingGoods,list);
        STActivity(intent,SureGoodsActivity.class);
    }
}
