package com.sk.jintang.module.community.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.github.customview.MyTextView;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseFragment;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.community.Constant;
import com.sk.jintang.module.community.activity.FaTieActivity;
import com.sk.jintang.module.community.activity.HuaTiDetailActivity;
import com.sk.jintang.module.community.activity.MoreHuaTiListActivity;
import com.sk.jintang.module.community.activity.TieZiDetailActivity;
import com.sk.jintang.module.community.activity.TieZiSearchActivity;
import com.sk.jintang.module.community.network.ApiRequest;
import com.sk.jintang.module.community.network.response.SheQuHuaTiObj;
import com.sk.jintang.module.community.network.response.SheQuObj;
import com.sk.jintang.module.community.network.response.ZanObj;
import com.sk.jintang.module.my.activity.LoginActivity;
import com.sk.jintang.tools.DividerGridItemDecoration;
import com.sk.jintang.tools.ImageSizeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

import static android.app.Activity.RESULT_OK;

/**
 * Created by administartor on 2017/8/4.
 */

public class SheQuFragment extends BaseFragment implements LoadMoreAdapter.OnLoadMoreListener {
    @BindView(R.id.rv_shequ)
    RecyclerView rv_shequ;

    LoadMoreAdapter adapter;
    BaseRecyclerAdapter huaTiAdapter;
    @BindView(R.id.et_shequ_search)
    EditText et_shequ_search;
    @BindView(R.id.iv_shequ)
    ImageView iv_shequ;
    @BindView(R.id.rv_shequ_huati)
    RecyclerView rv_shequ_huati;
    @BindView(R.id.nsv)
    NestedScrollView nsv;

    @Override
    protected int getContentView() {
        return R.layout.frag_she_qu;
    }

    @Override
    protected void initView() {
        pageSize=7;
        adapter = new LoadMoreAdapter<SheQuObj>(mContext, R.layout.item_shequ, pageSize,nsv) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, SheQuObj bean) {
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
        rv_shequ.setLayoutManager(new LinearLayoutManager(mContext));
        rv_shequ.setNestedScrollingEnabled(false);
        rv_shequ.addItemDecoration(getItemDivider(PhoneUtils.dip2px(mContext, 10)));
        rv_shequ.setAdapter(adapter);


        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getData(1,false);
                getHuaTi();
            }
        });


        huaTiAdapter=new BaseRecyclerAdapter<SheQuHuaTiObj.TopicListBean>(mContext,R.layout.item_shequ_huati ) {
            @Override
            public void bindData(RecyclerViewHolder holder, int position, SheQuHuaTiObj.TopicListBean bean) {
                ImageView iv_shequ_huati = holder.getImageView(R.id.iv_shequ_huati);
                int itemViewType = getItemViewType(position);
                if(itemViewType==1){
                    iv_shequ_huati.setVisibility(View.VISIBLE);
                    holder.setText(R.id.tv_shequ_huati,"更多热门话题");
                    holder.itemView.setOnClickListener(new MyOnClickListener() {
                        @Override
                        protected void onNoDoubleClick(View view) {
                            STActivity(MoreHuaTiListActivity.class);
                        }
                    });
                }else{
                    iv_shequ_huati.setVisibility(View.GONE);
                    holder.setText(R.id.tv_shequ_huati,bean.getTopic_name());
                    holder.itemView.setOnClickListener(new MyOnClickListener() {
                        @Override
                        protected void onNoDoubleClick(View view) {
                            if (TextUtils.isEmpty(getUserId())) {
                                STActivity(LoginActivity.class);
                            }else{
                                Intent intent=new Intent();
                                intent.putExtra(Constant.IParam.topicId,bean.getTopic_id()+"");
                                intent.putExtra(Constant.IParam.topicTitle,bean.getTopic_name());
                                STActivity(intent,HuaTiDetailActivity.class);
                            }

                        }
                    });
                }
            }
            @Override
            public int getItemCount() {
                return mList==null?0:mList.size()+1;
            }

            @Override
            public int getItemViewType(int position) {
                if(position==mList.size()){
                    return 1;
                }else{
                    return 0;
                }

            }
        };
        rv_shequ_huati.setLayoutManager(new GridLayoutManager(mContext,2));
        rv_shequ_huati.addItemDecoration(new DividerGridItemDecoration(mContext,2));
        rv_shequ_huati.setAdapter(huaTiAdapter);



        et_shequ_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (TextUtils.isEmpty(et_shequ_search.getText().toString())) {
                        showMsg("请输入搜索内容");
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra(Constant.IParam.searchStr, et_shequ_search.getText().toString());
                        STActivityForResult(intent, TieZiSearchActivity.class,200);

                    }
                }
                return false;
            }
        });
    }
    @Override
    protected void initData() {
        showProgress();
        getData(1, false);
        getHuaTi();
    }

    private void getHuaTi() {
        Map<String,String>map=new HashMap<String,String>();
        map.put("rnd",getRnd());
        map.put("sign",GetSign.getSign(map));
        ApiRequest.getSheQuHuaTi(map, new MyCallBack<SheQuHuaTiObj>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(SheQuHuaTiObj obj) {
                iv_shequ.setLayoutParams(ImageSizeUtils.getImageSizeFrameLayoutParams(mContext));
                Glide.with(mContext).load(obj.getImg_url()).error(R.color.c_press).into(iv_shequ);
                huaTiAdapter.setList(obj.getTopic_list(),true);
            }
        });

    }


    private void getData(int page, boolean isLoad) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_id", getUserId() == null ? "0" : getUserId());
        map.put("page", page + "");
        map.put("pagesize", pageSize + "");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getSheQuList(map, new MyCallBack<List<SheQuObj>>(mContext, pcfl, pl_load) {
            @Override
            public void onSuccess(List<SheQuObj> list) {
                if (isLoad) {
                    pageNum++;
                    adapter.addList(list, true);
                } else {
                    pageNum = 2;
                    adapter.setList(list, true);
                }
            }
        });

    }



    @Override
    public void loadMore() {
        getData(pageNum, true);
    }

    @OnClick({R.id.iv_shequ_tiezi, R.id.ll_shequ_dpzr, R.id.ll_shequ_qgdp, R.id.ll_shequ_zyzp, R.id.ll_shequ_jsqz})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.iv_shequ_tiezi:
                if (TextUtils.isEmpty(getUserId())) {
                    STActivity(LoginActivity.class);
                    return;
                }
                STActivityForResult(FaTieActivity.class,100);
                break;
            case R.id.ll_shequ_dpzr:
                break;
            case R.id.ll_shequ_qgdp:
                break;
            case R.id.ll_shequ_zyzp:
                break;
            case R.id.ll_shequ_jsqz:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 100:
                    getData(1,false);
                break;
            }
        }
        if(requestCode==200){
            et_shequ_search.setText(null);
        }
    }
}
