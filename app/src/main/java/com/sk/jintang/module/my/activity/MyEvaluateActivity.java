package com.sk.jintang.module.my.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.Constant;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.response.MyEvaluateObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/9/8.
 */

public class MyEvaluateActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_my_evaluate)
    RecyclerView rv_my_evaluate;

    LoadMoreAdapter adapter;
    @Override
    protected int getContentView() {
        setAppTitle("我的评价");
        return R.layout.act_my_evaluate;
    }

    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter<MyEvaluateObj>(mContext,R.layout.item_my_evaluate,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, MyEvaluateObj bean) {
                TextView tv_my_evaluate_content = holder.getTextView(R.id.tv_my_evaluate_content);
                if(TextUtils.isEmpty(bean.getContent())){
                    tv_my_evaluate_content.setText("暂无评价");
                }else{
                    tv_my_evaluate_content.setText(bean.getContent());
                }
                holder.setText(R.id.tv_my_evaluate_name,bean.getNickname())
                        .setText(R.id.tv_my_evaluate_date,bean.getAdd_time())
                        .setText(R.id.tv_my_evaluate_goods,bean.getGoods_name())
                        .setText(R.id.tv_my_evaluate_price,"¥"+bean.getPrice());
                ImageView imageView = holder.getImageView(R.id.iv_my_evaluate_head);
                ImageView iv_my_evaluate_img = holder.getImageView(R.id.iv_my_evaluate_img);

                Glide.with(mContext).load(bean.getGoods_img()).error(R.color.c_press).into(iv_my_evaluate_img);
                Glide.with(mContext).load(bean.getPhoto()).error(R.color.c_press).into(imageView);

                TextView tv_my_evaluate_again = holder.getTextView(R.id.tv_my_evaluate_again);
                tv_my_evaluate_again.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        String evaluateId=bean.getAppraise_id()+"";
                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.evaluateId,evaluateId);
                        STActivityForResult(intent,AgainEvaluateActivity.class,100);
                    }
                });

                LinearLayout ll_my_evaluate_again = (LinearLayout) holder.getView(R.id.ll_my_evaluate_again);
                int status = bean.getIs_after_review();
                if(status==1){
                    ll_my_evaluate_again.setVisibility(View.GONE);
                    holder.itemView.setOnClickListener(new MyOnClickListener() {
                        @Override
                        protected void onNoDoubleClick(View view) {
                            String evaluateId=bean.getAppraise_id()+"";
                            Intent intent=new Intent();
                            intent.putExtra(Constant.IParam.evaluateId,evaluateId);
                            intent.putExtra(Constant.IParam.lookEvaluate,true);
                            STActivity(intent,AgainEvaluateActivity.class);
                        }
                    });
                }else{
                    ll_my_evaluate_again.setVisibility(View.VISIBLE);
                    holder.itemView.setOnClickListener(new MyOnClickListener() {
                        @Override
                        protected void onNoDoubleClick(View view) {
                            String evaluateId=bean.getAppraise_id()+"";
                            Intent intent=new Intent();
                            intent.putExtra(Constant.IParam.evaluateId,evaluateId);
                            STActivityForResult(intent,AgainEvaluateActivity.class,100);
                        }
                    });
                }
            }
        };
        adapter.setOnLoadMoreListener(this);
        rv_my_evaluate.setLayoutManager(new LinearLayoutManager(mContext));
        rv_my_evaluate.addItemDecoration(getItemDivider(PhoneUtils.dip2px(mContext,10)));
        rv_my_evaluate.setAdapter(adapter);

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
        getData(1,false);
    }
    private void getData(int page, boolean isLoad) {
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("page",page+"");
        map.put("pagesize",pageSize+"");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getMyEvaluate(map, new MyCallBack<List<MyEvaluateObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(List<MyEvaluateObj> list) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 100:
                    initData();
                break;
            }
        }
    }
}
