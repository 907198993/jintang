package com.sk.jintang.module.community.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import com.github.baseclass.rx.RxBus;
import com.github.customview.MyEditText;
import com.github.customview.MyImageView;
import com.github.customview.MyLinearLayout;
import com.github.customview.MyTextView;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseFragment;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.community.event.CloseEvaluateEvent;
import com.sk.jintang.module.community.network.ApiRequest;
import com.sk.jintang.module.community.network.response.TieZiDetailObj;
import com.sk.jintang.module.community.network.response.ZanObj;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/13.
 */

public class TieZiAllEvaluateDetailFragment extends BaseFragment implements LoadMoreAdapter.OnLoadMoreListener {


    @BindView(R.id.iv_tie_zi_all_evaluate_close)
    ImageView iv_tie_zi_all_evaluate_close;
    @BindView(R.id.tv_all_evaluate_num)
    TextView tv_all_evaluate_num;
    @BindView(R.id.iv_tie_zi_detail_user)
    MyImageView iv_tie_zi_detail_user;
    @BindView(R.id.tv_tie_zi_detail_name)
    TextView tv_tie_zi_detail_name;
    @BindView(R.id.tv_tie_zi_detail_content)
    TextView tv_tie_zi_detail_content;
    @BindView(R.id.tv_tie_zi_huifu_time)
    TextView tv_tie_zi_huifu_time;
    @BindView(R.id.iv_tie_zi_detail_zan_evaluate)
    ImageView iv_tie_zi_detail_zan_evaluate;
    @BindView(R.id.tv_tie_zi_detail_zan_num)
    TextView tv_tie_zi_detail_num;
    @BindView(R.id.ll_tie_zi_detail_zan_evaluate)
    LinearLayout ll_tie_zi_detail_zan_evaluate;
    @BindView(R.id.rv_tie_zi_all_evaluate)
    RecyclerView rv_tie_zi_all_evaluate;
    @BindView(R.id.et_tie_zi_evaluate)
    MyEditText et_tie_zi_evaluate;
    @BindView(R.id.iv_tie_zi_zan)
    ImageView iv_tie_zi_zan;
    @BindView(R.id.iv_tie_zi_share)
    ImageView iv_tie_zi_share;
    @BindView(R.id.ll_tie_zi_detail_bottom)
    MyLinearLayout ll_tie_zi_detail_bottom;

    LoadMoreAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.frag_tie_zi_evaluate;
    }

    public static TieZiAllEvaluateDetailFragment newInstance(String id) {

        Bundle args = new Bundle();
        args.putString("id", id);

        TieZiAllEvaluateDetailFragment fragment = new TieZiAllEvaluateDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {
        adapter=new LoadMoreAdapter<TieZiDetailObj.CommentListBean>(mContext,R.layout._item_,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, TieZiDetailObj.CommentListBean bean) {
                ImageView imageView = holder.getImageView(R.id.iv_tie_zi_detail_user);
                Glide.with(mContext).load(bean.getPhoto()).error(R.drawable.people).into(imageView);
                holder.setText(R.id.tv_tie_zi_detail_name,bean.getNickname())
                        .setText(R.id.tv_tie_zi_detail_name,bean.getNickname())
                        .setText(R.id.tv_tie_zi_detail_zan_num,bean.getThumbup_count()+"")
                        .setText(R.id.tv_tie_zi_huifu_time,bean.getFormatTime()+"")
                        .setText(R.id.tv_tie_zi_detail_content,bean.getContent());
                MyTextView tv_tie_zi_huifu = (MyTextView) holder.getTextView(R.id.tv_tie_zi_huifu);
                if(bean.getReply_count()>0){
                    tv_tie_zi_huifu.setText(bean.getReply_count()+"回复");
                    tv_tie_zi_huifu.setSolidColor(Color.parseColor("#F3F3F3"));
                    tv_tie_zi_huifu.setPadding(PhoneUtils.dip2px(mContext,6),PhoneUtils.dip2px(mContext,2),PhoneUtils.dip2px(mContext,6),PhoneUtils.dip2px(mContext,2));
                    tv_tie_zi_huifu.setRadius(PhoneUtils.dip2px(mContext,10));
                    tv_tie_zi_huifu.complete();
                }else{
                    tv_tie_zi_huifu.setText("回复");
                    tv_tie_zi_huifu.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
                }
                tv_tie_zi_huifu.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        showMsg("弹窗");
                    }
                });
                TextView tv_tie_zi_all_huifu = holder.getTextView(R.id.tv_tie_zi_all_huifu);
                tv_tie_zi_all_huifu.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        showMsg("查看所有回复");
                    }
                });
                if(bean.getReply_count()>3){
                    tv_tie_zi_all_huifu.setVisibility(View.VISIBLE);
                    tv_tie_zi_all_huifu.setText("查看全部"+bean.getReply_count()+"回复");
                }else{
                    tv_tie_zi_all_huifu.setVisibility(View.GONE);
                }
                LinearLayout ll_tie_zi_detail_zan_evaluate = (LinearLayout) holder.getView(R.id.ll_tie_zi_detail_zan_evaluate);
                ImageView iv_tie_zi_detail_zan_evaluate = holder.getImageView(R.id.iv_tie_zi_detail_zan_evaluate);
                if(bean.getIs_thumbupX()==1){
                    iv_tie_zi_detail_zan_evaluate.setImageResource(R.drawable.shequ15);
                }else{
                    iv_tie_zi_detail_zan_evaluate.setImageResource(R.drawable.shequ16);
                }
                ll_tie_zi_detail_zan_evaluate.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        zanEvaluate(position,bean.getComments_id());
                    }
                });
                LoadMoreAdapter evaluateAdapter=new LoadMoreAdapter<TieZiDetailObj.CommentListBean.ReplyListBean>(mContext,R.layout.item_tie_zi_detail_evaluate,pageSize) {
                    @Override
                    public void bindData(LoadMoreViewHolder childHolder, int childPosition, TieZiDetailObj.CommentListBean.ReplyListBean item) {
                        String content="";
                        if(TextUtils.isEmpty(item.getNickname_to())){
                            content="<font color='#6F85A8'>"+item.getNickname()+":</font>"+item.getContent();
                        }else{

                            content="<font color='#6F85A8'>"+item.getNickname()+"回复"+item.getNickname_to()+"</font>"+item.getContent();
                        }

                        TextView tv_tie_zi_detail_evaluate_user1 = childHolder.getTextView(R.id.tv_tie_zi_detail_evaluate_user1);
                        tv_tie_zi_detail_evaluate_user1.setText(Html.fromHtml(content));

                    }
                };

                evaluateAdapter.setList(bean.getReply_list());

                RecyclerView rv_tie_zi_detail_evaluate = (RecyclerView) holder.getView(R.id.rv_tie_zi_detail_evaluate);

                rv_tie_zi_detail_evaluate.setAdapter(evaluateAdapter);
                rv_tie_zi_detail_evaluate.setNestedScrollingEnabled(false);
                rv_tie_zi_detail_evaluate.setLayoutManager(new LinearLayoutManager(mContext));
            }
            private void zanEvaluate(int position,int id) {
                showLoading();
                Map<String,String>map=new HashMap<String,String>();
                map.put("user_id",getUserId());
                map.put("forum_comment_id",id+"");
                map.put("type","2");
                map.put("sign",GetSign.getSign(map));
                ApiRequest.dianZan(map, new MyCallBack<ZanObj>(mContext) {
                    @Override
                    public void onSuccess(ZanObj obj) {
                        int thumbup_count = getList().get(position).getThumbup_count();
                        if(obj.getIs_thumbup()==1){
                            thumbup_count++;
                            getList().get(position).setThumbup_count(obj.getThumbup_count());
                            getList().get(position).setIs_thumbupX(1);
                        }else {
                            thumbup_count--;
                            getList().get(position).setThumbup_count(obj.getThumbup_count());
                            getList().get(position).setIs_thumbupX(0);
                        }
                        notifyDataSetChanged();
                    }
                });

            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_tie_zi_all_evaluate.setLayoutManager(new LinearLayoutManager(mContext));
        rv_tie_zi_all_evaluate.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        showProgress();
        getData(1, false);
    }

    private void getData(int page, boolean isLoad) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("comment_id", getArguments().getString("id"));
        map.put("user_id", getUserId());
        map.put("page", page + "");
        map.put("pagesize", pageSize + "");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getAllEvaluateDetail(map, new MyCallBack<TieZiDetailObj.CommentListBean>(mContext) {
            @Override
            public void onSuccess(TieZiDetailObj.CommentListBean obj) {
                setEvaluateData(obj);
                if(isLoad){
                    pageNum++;
                    adapter.addList(obj.getReply_list(),true);
                }else{
                    pageNum=2;
                    adapter.setList(obj.getReply_list(),true);
                }
            }

            private void setEvaluateData(TieZiDetailObj.CommentListBean bean) {
                 Glide.with(mContext).load(bean.getPhoto()).error(R.drawable.people).into(iv_tie_zi_detail_user);

                 tv_tie_zi_detail_name.setText(bean.getNickname());
                 tv_tie_zi_detail_num.setText(bean.getThumbup_count()+"条回复");
                 tv_tie_zi_huifu_time.setText(bean.getFormatTime()+"");
                 tv_tie_zi_detail_content.setText(bean.getContent());

                if(bean.getIs_thumbupX()==1){
                    iv_tie_zi_detail_zan_evaluate.setImageResource(R.drawable.shequ15);
                    iv_tie_zi_detail_zan_evaluate.setTag(1);
                }else{
                    iv_tie_zi_detail_zan_evaluate.setImageResource(R.drawable.shequ16);
                    iv_tie_zi_detail_zan_evaluate.setTag(0);
                }


                ll_tie_zi_detail_zan_evaluate.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        zanEvaluate(bean.getComments_id());
                    }
                });
            }
            private void zanEvaluate(int id) {
                showLoading();
                Map<String,String>map=new HashMap<String,String>();
                map.put("user_id",getUserId());
                map.put("forum_comment_id",id+"");
                map.put("type","2");
                map.put("sign",GetSign.getSign(map));
                ApiRequest.dianZan(map, new MyCallBack<BaseObj>(mContext) {
                    @Override
                    public void onSuccess(BaseObj obj) {
                        showMsg(obj.getMsg());
                        int tag = (int) iv_tie_zi_detail_zan_evaluate.getTag();
                        if(tag==1){
                            iv_tie_zi_detail_zan_evaluate.setImageResource(R.drawable.shequ16);
                            iv_tie_zi_detail_zan_evaluate.setTag(0);
                        }else{
                            iv_tie_zi_detail_zan_evaluate.setImageResource(R.drawable.shequ15);
                            iv_tie_zi_detail_zan_evaluate.setTag(1);
                        }
                    }
                });

            }
        });

    }

    @OnClick({R.id.iv_tie_zi_all_evaluate_close})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.iv_tie_zi_all_evaluate_close:
                RxBus.getInstance().post(new CloseEvaluateEvent());
            break;
        }
    }

    @Override
    public void loadMore() {
        getData(pageNum, true);
    }
}
