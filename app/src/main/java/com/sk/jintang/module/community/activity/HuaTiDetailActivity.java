package com.sk.jintang.module.community.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.community.Constant;
import com.sk.jintang.module.community.network.ApiRequest;
import com.sk.jintang.module.community.network.response.HuaTiObj;
import com.sk.jintang.module.my.activity.LoginActivity;
import com.sk.jintang.tools.ImageSizeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/9/4.
 */

public class HuaTiDetailActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_tiezi_for_hua_ti)
    RecyclerView rv_tiezi_for_hua_ti;
    private String topicId;

    LoadMoreAdapter adapter;
    @Override
    protected int getContentView() {
        return R.layout.act_hua_ti_detail;
    }

    @Override
    protected void initView() {
        topicId = getIntent().getStringExtra(Constant.IParam.topicId);
        String topicTitle = getIntent().getStringExtra(Constant.IParam.topicTitle);
        setAppTitle(topicTitle);

        adapter=new LoadMoreAdapter<HuaTiObj>(mContext,R.layout.item_tie_zi_for_hua_ti_,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, HuaTiObj bean) {
                ImageView imageView = holder.getImageView(R.id.iv_tiezi_huati);
                imageView.setLayoutParams(ImageSizeUtils.getImageSizeLayoutParams(mContext));
                Glide.with(mContext).load(bean.getImg_url()).error(R.color.c_press).into(imageView);

                holder.setText(R.id.tv_tiezi_huati_title,bean.getTitle())
                        .setText(R.id.tv_tiezi_huati_content,bean.getContent())
                        .setText(R.id.tv_tiezi_huati_date,bean.getAdd_time())
                        .setText(R.id.tv_tiezi_huati_look,bean.getPage_view()+"")
                        .setText(R.id.tv_tiezi_huati_msg,bean.getComment_count()+"")
                        .setText(R.id.tv_tiezi_huati_zan,bean.getThumbup_count()+"");

                ImageView iv_huati_zan = holder.getImageView(R.id.iv_huati_zan);
                if(bean.getIs_thumbup()==1){
                    iv_huati_zan.setImageResource(R.drawable.shequ15);
                }else{
                    iv_huati_zan.setImageResource(R.drawable.shequ16);
                }

                View ll_hua_ti_detail = holder.getView(R.id.ll_hua_ti_detail);
                ll_hua_ti_detail.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        zanHuaTi(position,bean.getForum_id());
                    }
                });
                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        if(TextUtils.isEmpty(getUserId())){
                            STActivity(LoginActivity.class);
                            return;
                        }
                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.tieZiId,bean.getForum_id()+"");
                        STActivity(intent,TieZiDetailActivity.class);
                    }
                });

            }
            private void zanHuaTi(int position,int id) {
                showLoading();
                Map<String,String>map=new HashMap<String,String>();
                map.put("user_id",getUserId());
                map.put("forum_comment_id",id+"");
                map.put("type","1");
                map.put("sign",GetSign.getSign(map));
                ApiRequest.dianZan(map, new MyCallBack<BaseObj>(mContext) {
                    @Override
                    public void onSuccess(BaseObj obj) {
                        int thumbup_count = getList().get(position).getThumbup_count();
                        if(obj.getIs_thumbup()==1){
                            thumbup_count++;
                            getList().get(position).setThumbup_count(thumbup_count);
                            getList().get(position).setIs_thumbup(1);
                        }else {
                            thumbup_count--;
                            getList().get(position).setThumbup_count(thumbup_count);
                            getList().get(position).setIs_thumbup(0);
                        }
                        notifyDataSetChanged();
                    }
                });

            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_tiezi_for_hua_ti.setLayoutManager(new LinearLayoutManager(mContext));
        rv_tiezi_for_hua_ti.addItemDecoration(getItemDivider());
        rv_tiezi_for_hua_ti.setAdapter(adapter);
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

    private void getData(int page,boolean isLoad) {
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("topic_id",topicId);
        map.put("page",page+"");
        map.put("pagesize",pageSize+"");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getTieZiForHuaTi(map, new MyCallBack<List<HuaTiObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(List<HuaTiObj> list) {
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
}
