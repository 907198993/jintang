package com.sk.jintang.module.my.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.community.Constant;
import com.sk.jintang.module.community.activity.TieZiDetailActivity;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.response.MySheQuObj;
import com.sk.jintang.tools.ImageSizeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by administartor on 2017/9/6.
 */

public class MySheQuActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_my_she_qu)
    RecyclerView rv_my_she_qu;

    LoadMoreAdapter adapter;
    @Override
    protected int getContentView() {
        setAppTitle("我的社区");
        return R.layout.act_my_she_qu;
        //123F025DBDF7C8523818C76AB768927822C9BFD6
    }

    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter<MySheQuObj>(mContext,R.layout.item_my_she_qu,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, MySheQuObj bean) {
                ImageView imageView=holder.getImageView(R.id.iv_my_she_qu);
                imageView.setLayoutParams(ImageSizeUtils.getImageSizeFrameLayoutParams(mContext));
                ImageView iv_my_she_qu_status=holder.getImageView(R.id.iv_my_she_qu_status);
                if(bean.getIs_autio()==1){
                    iv_my_she_qu_status.setImageResource(R.drawable.shenhe_1);
                }else if(bean.getIs_autio()==2){
                    iv_my_she_qu_status.setImageResource(R.drawable.shenhe_2);
                }else if(bean.getIs_autio()==3){
                    iv_my_she_qu_status.setImageResource(R.drawable.shenhe_3);
                }
                Glide.with(mContext).load(bean.getImg_url()).error(R.color.c_press).into(imageView);
                holder.setText(R.id.tv_my_she_qu_title,bean.getTitle())
                        .setText(R.id.tv_my_she_qu_content,bean.getContent())
                        .setText(R.id.tv_my_she_qu_look,bean.getPage_view()+"")
                        .setText(R.id.tv_my_she_qu_date,bean.getAdd_time());

                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.tieZiId,bean.getForum_id()+"");
                        STActivity(intent,TieZiDetailActivity.class);
                    }
                });
                /*iv_my_she_qu
tv_my_she_qu_title
tv_my_she_qu_content
tv_my_she_qu_look
tv_my_she_qu_date*/
            }
        };


        rv_my_she_qu.setLayoutManager(new LinearLayoutManager(mContext));
        rv_my_she_qu.setAdapter(adapter);
        adapter.setOnLoadMoreListener(this);

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
        ApiRequest.getMySheQu(map, new MyCallBack<List<MySheQuObj>>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(List<MySheQuObj> list) {
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
