package com.sk.jintang.module.community.activity;

import android.graphics.Color;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.rx.MySubscriber;
import com.github.customview.MyTextView;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.BaseDividerListItem;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.community.Constant;
import com.sk.jintang.module.community.event.CloseEvaluateEvent;
import com.sk.jintang.module.community.fragment.AllEvaluateFragment;
import com.sk.jintang.module.community.network.ApiRequest;
import com.sk.jintang.module.community.network.response.TieZiDetailObj;
import com.sk.jintang.module.community.network.response.ZanObj;
import com.sk.jintang.tools.ImageSizeUtils;
import com.sk.jintang.tools.RichEditor;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/9/4.
 */

public class TieZiDetailActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener {
    @BindView(R.id.rv_tie_zi_detail)
    RecyclerView rv_tie_zi_detail;
//    @BindView(R.id.wv_tie_zi_detail)
//    WebView wv_tie_zi_detail;
    @BindView(R.id.re_tie_zi_detail)
    RichEditor re_tie_zi_detail;

    LoadMoreAdapter adapter;
    @BindView(R.id.iv_tie_zi_detail)
    ImageView iv_tie_zi_detail;
    @BindView(R.id.tv_tie_zi_detail_title)
    TextView tv_tie_zi_detail_title;
    @BindView(R.id.tv_tie_zi_detail_name)
    TextView tv_tie_zi_detail_name;
    @BindView(R.id.tv_tie_zi_detail_time)
    TextView tv_tie_zi_detail_time;
    @BindView(R.id.tv_tie_zi_detail_zan_num)
    TextView tv_tie_zi_detail_num;
    @BindView(R.id.tv_tie_zi_zan_num)
    TextView tv_tie_zi_zan_num;
    @BindView(R.id.iv_tie_zi_detail_name)
    ImageView iv_tie_zi_detail_name;
    @BindView(R.id.iv_tie_zi_zan)
    ImageView iv_tie_zi_zan;
    @BindView(R.id.et_tie_zi_evaluate)
    EditText et_tie_zi_evaluate;
    @BindView(R.id.ll_tie_content)
    LinearLayout ll_tie_content;
    @BindView(R.id.nsv)
    NestedScrollView nsv;
    private String tieZiId;

    private BaseDividerListItem itemDivider;
    private BottomSheetDialog sheetDialog;
    private TieZiDetailObj tieZiDetailObj;

    @Override
    protected int getContentView() {
        return R.layout.act_tie_zi_detail;
    }

    @Override
    protected void initRxBus() {
        super.initRxBus();
        getRxBusEvent(CloseEvaluateEvent.class, new MySubscriber() {
            @Override
            public void onMyNext(Object o) {
                sheetDialog.dismiss();
            }
        });
    }

    @Override
    protected void initView() {
        itemDivider = getItemDivider(PhoneUtils.dip2px(mContext, 5), R.color.transparent);
        tieZiId = getIntent().getStringExtra(Constant.IParam.tieZiId);
        adapter = new LoadMoreAdapter<TieZiDetailObj.CommentListBean>(mContext, R.layout.item_tie_zi_detail, pageSize, nsv) {

            @Override
            public void bindData(LoadMoreViewHolder holder, int position, TieZiDetailObj.CommentListBean bean) {
                holder.itemView.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        showAllEvaluate(bean);
                    }
                });
                ImageView imageView = holder.getImageView(R.id.iv_tie_zi_detail_user);
                Glide.with(mContext).load(bean.getPhoto()).error(R.drawable.people).into(imageView);
                holder.setText(R.id.tv_tie_zi_detail_name, bean.getNickname())
                        .setText(R.id.tv_tie_zi_detail_name, bean.getNickname())
                        .setText(R.id.tv_tie_zi_detail_zan_num, bean.getThumbup_count() + "")
                        .setText(R.id.tv_tie_zi_huifu_time, bean.getFormatTime() + "")
                        .setText(R.id.tv_tie_zi_detail_content, bean.getContent());
                MyTextView tv_tie_zi_huifu = (MyTextView) holder.getTextView(R.id.tv_tie_zi_huifu);
                if (bean.getReply_count() > 0) {
                    tv_tie_zi_huifu.setText(bean.getReply_count() + "回复");
                    tv_tie_zi_huifu.setSolidColor(Color.parseColor("#F3F3F3"));
                    tv_tie_zi_huifu.setPadding(PhoneUtils.dip2px(mContext, 6), PhoneUtils.dip2px(mContext, 2), PhoneUtils.dip2px(mContext, 6), PhoneUtils.dip2px(mContext, 2));
                    tv_tie_zi_huifu.setRadius(PhoneUtils.dip2px(mContext, 10));
                    tv_tie_zi_huifu.complete();
                } else {
                    tv_tie_zi_huifu.setText("回复");
                    tv_tie_zi_huifu.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
                }
                /*tv_tie_zi_huifu.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        showMsg("弹窗");
                    }
                });*/
                TextView tv_tie_zi_all_huifu = holder.getTextView(R.id.tv_tie_zi_all_huifu);
               /* tv_tie_zi_all_huifu.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        showMsg("查看所有回复");
                    }
                });*/
                if (bean.getReply_count() > 3) {
                    tv_tie_zi_all_huifu.setVisibility(View.VISIBLE);
                    tv_tie_zi_all_huifu.setText("查看全部" + bean.getReply_count() + "回复");
                } else {
                    tv_tie_zi_all_huifu.setVisibility(View.GONE);
                }
                LinearLayout ll_tie_zi_detail_zan_evaluate = (LinearLayout) holder.getView(R.id.ll_tie_zi_detail_zan_evaluate);
                ImageView iv_tie_zi_detail_zan_evaluate = holder.getImageView(R.id.iv_tie_zi_detail_zan_evaluate);
                if (bean.getIs_thumbupX() == 1) {
                    iv_tie_zi_detail_zan_evaluate.setImageResource(R.drawable.shequ15);
                } else {
                    iv_tie_zi_detail_zan_evaluate.setImageResource(R.drawable.shequ16);
                }
                ll_tie_zi_detail_zan_evaluate.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        zanEvaluate(position, bean.getComments_id());
                    }
                });
                LoadMoreAdapter evaluateAdapter = new LoadMoreAdapter<TieZiDetailObj.CommentListBean.ReplyListBean>(mContext, R.layout.item_tie_zi_detail_evaluate, pageSize) {
                    @Override
                    public void bindData(LoadMoreViewHolder childHolder, int childPosition, TieZiDetailObj.CommentListBean.ReplyListBean item) {
                        childHolder.itemView.setOnClickListener(new MyOnClickListener() {
                            @Override
                            protected void onNoDoubleClick(View view) {
                                showAllEvaluate(bean);
                            }
                        });
/*iv_tie_zi_detail_evaluate_user
tv_tie_zi_detail_evaluate_user1
tv_tie_zi_detail_evaluate_user2
tv_tie_zi_detail_evaluate_content*/
//                        ImageView imageView = childHolder.getImageView(R.id.iv_tie_zi_detail_evaluate_user);
//                        Glide.with(mContext).load(item.getPhoto()).error(R.drawable.people).into(imageView);
                       /* childHolder.setText(R.id.tv_tie_zi_detail_evaluate_user1,item.getNickname())
                                .setText(R.id.tv_tie_zi_detail_evaluate_user2,item.getNickname_to())
                                .setText(R.id.tv_tie_zi_detail_evaluate_content,item.getContent());*/
                        String content = "";
                        if (TextUtils.isEmpty(item.getNickname_to())) {
                            content = "<font color='#6F85A8'>" + item.getNickname() + ":</font>" + item.getContent();
                        } else {

                            content = "<font color='#6F85A8'>" + item.getNickname() + " 回复 " + item.getNickname_to() + "</font>" + item.getContent();
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

            private void showAllEvaluate(TieZiDetailObj.CommentListBean obj) {
                AllEvaluateFragment.newInstance(obj.getComments_id() + "").show(getSupportFragmentManager(), "dialog");
            }

            private void zanEvaluate(int position, int id) {
                showLoading();
                Map<String, String> map = new HashMap<String, String>();
                map.put("user_id", getUserId());
                map.put("forum_comment_id", id + "");
                map.put("type", "2");
                map.put("sign", GetSign.getSign(map));
                ApiRequest.dianZan(map, new MyCallBack<ZanObj>(mContext) {
                    @Override
                    public void onSuccess(ZanObj obj) {
                        int thumbup_count = getList().get(position).getThumbup_count();
                        if (obj.getIs_thumbup() == 1) {
                            thumbup_count++;
                            getList().get(position).setThumbup_count(obj.getThumbup_count());
                            getList().get(position).setIs_thumbupX(1);
                        } else {
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

        rv_tie_zi_detail.setNestedScrollingEnabled(false);
        rv_tie_zi_detail.setLayoutManager(new LinearLayoutManager(mContext));
        rv_tie_zi_detail.setAdapter(adapter);


        et_tie_zi_evaluate.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    if (TextUtils.isEmpty(et_tie_zi_evaluate.getText().toString())) {
                        showMsg("评论不能为空");
                    } else {
                        huiFu();
                    }
                }
                return false;
            }
        });

    }

    private void huiFu() {
        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put("forum_comment_id", tieZiDetailObj.getForum_id() + "");
        map.put("type", "1");
        map.put("user_id", getUserId());
        map.put("content", et_tie_zi_evaluate.getText().toString());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.huiFuEvaluate(map, new MyCallBack<BaseObj>(mContext, true) {
            @Override
            public void onSuccess(BaseObj obj) {
                et_tie_zi_evaluate.setText(null);
                showMsg(obj.getMsg());
                getData(1, false);
            }
        });
    }


    @Override
    protected void initData() {
        showProgress();
        getData(1, false);
    }




    private void getData(int page, boolean isLoad) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("page", page + "");
        map.put("pagesize", pageSize + "");
        map.put("forum_id", tieZiId);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.tieZiDetail(map, new MyCallBack<TieZiDetailObj>(mContext, pl_load) {
            @Override
            public void onSuccess(TieZiDetailObj obj) {
                tieZiDetailObj = obj;
                if (obj.getIs_thumbup() == 1) {
                    iv_tie_zi_zan.setImageResource(R.drawable.zan_select);
                    iv_tie_zi_zan.setTag(1);
                } else {
                    iv_tie_zi_zan.setImageResource(R.drawable.zan_normal);
                    iv_tie_zi_zan.setTag(0);
                }
                if (isLoad) {
                    pageNum++;
                    adapter.addList(obj.getComment_list(), true);
                } else {
                    pageNum = 2;
                    adapter.setList(obj.getComment_list(), true);

                    setAppTitle(obj.getTitle());
//                    initWebViewForContent(wv_tie_zi_detail,obj.getContent());

                    re_tie_zi_detail.setHtml(getNewContent(obj.getContent()));
                    re_tie_zi_detail.setInputEnabled(false);
                    Log.i("===", "getContent===" + obj.getContent());
                    iv_tie_zi_detail.setLayoutParams(ImageSizeUtils.getImageSizeLayoutParams(mContext));

                    Glide.with(mContext).load(obj.getImg_url()).error(R.color.c_press).into(iv_tie_zi_detail);
                    Glide.with(mContext).load(obj.getPhoto()).error(R.drawable.people).into(iv_tie_zi_detail_name);

                    tv_tie_zi_detail_title.setText(obj.getTitle());
                    tv_tie_zi_detail_name.setText(obj.getNickname());
                    tv_tie_zi_detail_time.setText(obj.getAdd_time());
                    tv_tie_zi_detail_num.setText("全部评论(" + obj.getComment_count() + ")");
                    tv_tie_zi_zan_num.setText(obj.getThumbup_count() + "");
                }
            }
        });

    }

    private void getEvaluate(int page, boolean isLoad) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("page", page + "");
        map.put("pagesize", pageSize + "");
        map.put("forum_id", tieZiId);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.tieZiDetail(map, new MyCallBack<TieZiDetailObj>(mContext, pl_load) {
            @Override
            public void onSuccess(TieZiDetailObj obj) {
                if (isLoad) {
                    pageNum++;
                    adapter.addList(obj.getComment_list(), true);
                } else {
                    pageNum = 2;
                    adapter.setList(obj.getComment_list(), true);
                }
            }
        });

    }

    @OnClick({R.id.iv_tie_zi_share, R.id.iv_tie_zi_zan})
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.iv_tie_zi_share:
                showFenXiang("0");
                break;
            case R.id.iv_tie_zi_zan:
                zanTieZi();
                break;
        }
    }

    private void zanTieZi() {
        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId());
        map.put("forum_comment_id", tieZiDetailObj.getForum_id() + "");
        map.put("type", "1");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.dianZan(map, new MyCallBack<ZanObj>(mContext) {
            @Override
            public void onSuccess(ZanObj obj) {
                int tag = (int) iv_tie_zi_zan.getTag();
                int count = Integer.parseInt(tv_tie_zi_zan_num.getText().toString());
                if (tag == 1) {
                    iv_tie_zi_zan.setImageResource(R.drawable.zan_normal);
                    iv_tie_zi_zan.setTag(0);
                    count--;
                    tv_tie_zi_zan_num.setText(obj.getThumbup_count() + "");
                } else {
                    iv_tie_zi_zan.setImageResource(R.drawable.zan_select);
                    iv_tie_zi_zan.setTag(1);
                    count++;
                    tv_tie_zi_zan_num.setText(obj.getThumbup_count() + "");
                }
            }
        });
    }

    @Override
    public void loadMore() {
        getData(pageNum, true);
    }
}
