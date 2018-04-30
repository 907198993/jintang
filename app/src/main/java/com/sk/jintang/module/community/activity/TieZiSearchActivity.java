package com.sk.jintang.module.community.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.customview.MyTextView;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.community.Constant;
import com.sk.jintang.module.community.network.ApiRequest;
import com.sk.jintang.module.community.network.response.TieZiSearchObj;
import com.sk.jintang.module.community.network.response.ZanObj;
import com.sk.jintang.module.my.activity.LoginActivity;
import com.sk.jintang.tools.ImageSizeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2017/11/15.
 */

public class TieZiSearchActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener{

    @BindView(R.id.rv_shequ_search)
    RecyclerView rv_shequ_search;

    LoadMoreAdapter adapter;
    private String searchStr ="";

    @Override
    protected int getContentView() {
        setAppTitle("帖子");
        return R.layout.act_tie_zi_search;
    }

    @Override
    protected void initView() {
        searchStr = getIntent().getStringExtra(Constant.IParam.searchStr);

        adapter=new LoadMoreAdapter<TieZiSearchObj>(mContext,R.layout.item_shequ,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, TieZiSearchObj bean) {
                ImageView iv_tie_zi_zan = holder.getImageView(R.id.iv_tie_zi_zan);
                if(bean.getIs_thumbup()==1){
                    iv_tie_zi_zan.setImageResource(R.drawable.shequ15);
                }else{
                    iv_tie_zi_zan.setImageResource(R.drawable.shequ16);
                }
                MyTextView tv_shequ_flag = (MyTextView) holder.getView(R.id.tv_shequ_flag);
                if (bean.getIs_hot() == 1) {
                    tv_shequ_flag.setText("精华帖");
                    tv_shequ_flag.setVisibility(View.VISIBLE);
                    tv_shequ_flag.setSolidColor(Color.parseColor("#ff9700"));
                } else if (bean.getIs_hot() == 2) {
                    tv_shequ_flag.setText("热门帖");
                    tv_shequ_flag.setVisibility(View.VISIBLE);
                    tv_shequ_flag.setSolidColor(Color.parseColor("#fe3728"));
                    tv_shequ_flag.complete();
                } else {
                    tv_shequ_flag.setVisibility(View.GONE);
                }

                holder.setText(R.id.tv_shequ_title, bean.getTitle())
                        .setText(R.id.tv_shequ_content, bean.getContent())
                        .setText(R.id.tv_shequ_date, bean.getAdd_time())
                        .setText(R.id.tv_shequ_msg, bean.getComment_count() + "")
                        .setText(R.id.tv_shequ_zan, bean.getThumbup_count() + "")
                        .setText(R.id.tv_shequ_look, bean.getPage_view() + "");

                ImageView imageView = holder.getImageView(R.id.iv_shequ_img);
                imageView.setLayoutParams(ImageSizeUtils.getImageSizeFrameLayoutParams(mContext));
                Glide.with(mContext).load(bean.getImg_url()).error(R.color.c_press).into(imageView);

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
                LinearLayout ll_shequ_dian_zan = (LinearLayout) holder.getView(R.id.ll_shequ_dian_zan);
                ll_shequ_dian_zan.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        if(TextUtils.isEmpty(getUserId())){
                            STActivity(LoginActivity.class);
                            return;
                        }
                        dianZanTieZi(position,bean.getForum_id());
                    }
                    private void dianZanTieZi(int position,int id) {
                        showLoading();
                        Map<String,String>map=new HashMap<String,String>();
                        map.put("user_id",getUserId());
                        map.put("forum_comment_id",id+"");
                        map.put("type","1");
                        map.put("sign",GetSign.getSign(map));
                        ApiRequest.dianZan(map, new MyCallBack<ZanObj>(mContext) {
                            @Override
                            public void onSuccess(ZanObj obj) {
                                int thumbup_count = getList().get(position).getThumbup_count();
                                if(obj.getIs_thumbup()==1){
                                    thumbup_count++;
                                    getList().get(position).setThumbup_count(obj.getThumbup_count());
                                    getList().get(position).setIs_thumbup(1);
                                }else {
                                    thumbup_count--;
                                    getList().get(position).setThumbup_count(obj.getThumbup_count());
                                    getList().get(position).setIs_thumbup(0);
                                }
                                notifyDataSetChanged();
                            }
                        });
                    }
                });
            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_shequ_search.setLayoutManager(new LinearLayoutManager(mContext));
        rv_shequ_search.addItemDecoration(getItemDivider(PhoneUtils.dip2px(mContext, 10)));
        rv_shequ_search.setAdapter(adapter);


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
        map.put("search_term", searchStr);
        map.put("page",page+"");
        map.put("pagesize",pageSize+"");
        map.put("user_id",getUserId()==null?"0":getUserId());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.tieZiSearch(map, new MyCallBack<List<TieZiSearchObj>>(mContext,pl_load,pcfl) {
            @Override
            public void onSuccess(List<TieZiSearchObj> list) {
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
