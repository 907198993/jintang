package com.sk.jintang.module.community.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.SPUtils;
import com.github.androidtools.ToastUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.view.Loading;
import com.github.customview.MyEditText;
import com.github.customview.MyImageView;
import com.github.customview.MyLinearLayout;
import com.sk.jintang.Config;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.community.network.ApiRequest;
import com.sk.jintang.module.community.network.response.TieZiAllEvaluateObj;
import com.sk.jintang.module.community.network.response.ZanObj;
import com.sk.jintang.module.my.network.response.FenXiangObj;
import com.sk.jintang.view.ProgressLayout;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * Created by Administrator on 2017/11/13.
 */

public class AllEvaluateFragment extends BottomSheetDialogFragment implements LoadMoreAdapter.OnLoadMoreListener,ProgressLayout.OnAgainInter{
    protected int pageNum=2;
    protected int pageSize=20;
    protected PtrClassicFrameLayout pcfl;
    protected Activity mContext;
    protected ProgressLayout pl_load;
    protected Unbinder mUnBind;
    private BottomSheetBehavior mBehavior;
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        mContext=getActivity();
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.frag_tie_zi_evaluate, null);
        mUnBind = ButterKnife.bind(this, view);

        dialog.setContentView(view);


        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        initView();
        initData();
        return dialog;
    }


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
    TextView tv_tie_zi_detail_zan_num;
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


    public static AllEvaluateFragment newInstance(String id) {

        Bundle args = new Bundle();
        args.putString("id", id);

        AllEvaluateFragment fragment = new AllEvaluateFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("===","==onViewCreated=");
        initView();
        initData();
    }

    private void initView() {
        adapter=new LoadMoreAdapter<TieZiAllEvaluateObj.ReplyListBean>(mContext,R.layout.item_tie_zi_all_evaluate,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, TieZiAllEvaluateObj.ReplyListBean bean) {
                String content="";
                if(TextUtils.isEmpty(bean.getNickname_to())){
                    content="<font color='#6F85A8'>"+bean.getNickname()+"</font>";
                }else{
                    content="<font color='#6F85A8'>"+bean.getNickname()+"</font><font color='#848484'> 回复 </font>"+"<font color='#6F85A8'>"+bean.getNickname_to()+"</font>";
                }

                TextView tv_all_evaluate_name = holder.getTextView(R.id.tv_all_evaluate_name);
                tv_all_evaluate_name.setText(Html.fromHtml(content));

                ImageView imageView = holder.getImageView(R.id.iv_all_evaluate_img);
                Glide.with(mContext).load(bean.getPhoto()).error(R.drawable.people).into(imageView);
                holder.setText(R.id.tv_all_evaluate_content,bean.getContent())
                        .setText(R.id.tv_tie_zi_detail_zan_num,bean.getThumbup_count()+"")
                        .setText(R.id.tv_tie_zi_huifu_time,bean.getFormatTime()+"") ;

                TextView tv_tie_zi_huifu_huifu = holder.getTextView(R.id.tv_tie_zi_huifu_huifu);
                tv_tie_zi_huifu_huifu.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        et_tie_zi_evaluate.requestFocus();
                        PhoneUtils.showKeyBoard(mContext,et_tie_zi_evaluate);
                        et_tie_zi_evaluate.setHint("回复 "+bean.getNickname());
                        et_tie_zi_evaluate.setTag(bean.getReply_id()+"");
                    }
                });

                LinearLayout ll_tie_zi_detail_zan_evaluate = (LinearLayout) holder.getView(R.id.ll_tie_zi_detail_zan_evaluate);
                ll_tie_zi_detail_zan_evaluate.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        zanSecondEvaluate(position,bean.getReply_id());
                    }
                });

                ImageView iv_tie_zi_detail_zan_evaluate = holder.getImageView(R.id.iv_tie_zi_detail_zan_evaluate);
                if(bean.getIs_thumbupX()==1){
                    iv_tie_zi_detail_zan_evaluate.setImageResource(R.drawable.zan_select);
                    iv_tie_zi_detail_zan_evaluate.setTag(1);
                }else{
                    iv_tie_zi_detail_zan_evaluate.setImageResource(R.drawable.zan_normal);
                    iv_tie_zi_detail_zan_evaluate.setTag(0);
                }
            }
            private void zanSecondEvaluate(int position,int id) {
                showLoading();
                Map<String,String> map=new HashMap<String,String>();
                map.put("user_id",getUserId());
                map.put("forum_comment_id",id+"");
                map.put("type","3");//3：点赞帖子评论的评论
                map.put("sign", GetSign.getSign(map));
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


        et_tie_zi_evaluate.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEND){
                    if (TextUtils.isEmpty(et_tie_zi_evaluate.getText().toString())){
                        showMsg("评论不能为空");
                    }else{
                        PhoneUtils.hiddenKeyBoard(mContext,et_tie_zi_evaluate);
                        huiFu();
                    }
                }
                return false;
            }
        });

    }

    private void huiFu() {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        if(et_tie_zi_evaluate.getTag()==null){
            map.put("forum_comment_id",evaluateDetail.getComments_id()+"");
            map.put("type","2");
            map.put("user_id",getUserId());
            map.put("content",et_tie_zi_evaluate.getText().toString());
            map.put("sign",GetSign.getSign(map));
            ApiRequest.huiFuEvaluate(map, new MyCallBack<BaseObj>(mContext,true) {
                @Override
                public void onSuccess(BaseObj obj) {
                    et_tie_zi_evaluate.setText(null);
                    showMsg(obj.getMsg());
                    getData(1,false);
                }
            });
        }else{
            map.put("comment_id",evaluateDetail.getComments_id()+"");
            map.put("reply_id",et_tie_zi_evaluate.getTag().toString());
            map.put("user_id",getUserId());
            map.put("content",et_tie_zi_evaluate.getText().toString());
            map.put("sign",GetSign.getSign(map));
            ApiRequest.huiFuSecondEvaluate(map, new MyCallBack<BaseObj>(mContext,true) {
                @Override
                public void onSuccess(BaseObj obj) {
                    et_tie_zi_evaluate.setText(null);
                    showMsg(obj.getMsg());
                    getData(1,false);
                }
            });
        }
    }

    private void initData() {
        showProgress();
        getData(1, false);
    }
    @Override
    public void onStart()
    {
        super.onStart();
        //默认全屏展开
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    public static AllEvaluateFragment newInstance() {

        Bundle args = new Bundle();

        AllEvaluateFragment fragment = new AllEvaluateFragment();
        fragment.setArguments(args);
        return fragment;
    }
    private TieZiAllEvaluateObj evaluateDetail;
    private void getData(int page, boolean isLoad) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("comment_id", getArguments().getString("id"));
        map.put("user_id", getUserId());
        map.put("page", page + "");
        map.put("pagesize", pageSize + "");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getAllEvaluateDetail(map, new MyCallBack<TieZiAllEvaluateObj>(mContext) {
            @Override
            public void onSuccess(TieZiAllEvaluateObj obj) {
                evaluateDetail = obj;
                setEvaluateData(obj);
                if(isLoad){
                    pageNum++;
                    adapter.addList(obj.getReply_list(),true);
                }else{
                    pageNum=2;
                    adapter.setList(obj.getReply_list(),true);
                }
            }

            private void setEvaluateData(TieZiAllEvaluateObj bean) {
                Glide.with(getActivity()).load(bean.getPhoto()).error(R.drawable.people).into(iv_tie_zi_detail_user);
                tv_tie_zi_detail_name.setText(bean.getNickname());
                tv_tie_zi_detail_zan_num.setText(bean.getThumbup_count()+"");
                tv_all_evaluate_num.setText(bean.getReply_count()+"条回复");
                tv_tie_zi_huifu_time.setText(bean.getFormatTime()+"");
                tv_tie_zi_detail_content.setText(bean.getContent());

                if(bean.getIs_thumbup()==1){
                    iv_tie_zi_detail_zan_evaluate.setImageResource(R.drawable.zan_select);
                    iv_tie_zi_detail_zan_evaluate.setTag(1);
                    iv_tie_zi_zan.setImageResource(R.drawable.zan_select);
                }else{
                    iv_tie_zi_detail_zan_evaluate.setImageResource(R.drawable.zan_normal);
                    iv_tie_zi_detail_zan_evaluate.setTag(0);
                    iv_tie_zi_zan.setImageResource(R.drawable.zan_normal);
                }


                ll_tie_zi_detail_zan_evaluate.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        zanEvaluate(bean.getComments_id());
                    }
                });
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
        ApiRequest.dianZan(map, new MyCallBack<ZanObj>(mContext) {
            @Override
            public void onSuccess(ZanObj obj) {
                showMsg(obj.getMsg());
                int tag = (int) iv_tie_zi_detail_zan_evaluate.getTag();
                int count = Integer.parseInt(tv_tie_zi_detail_zan_num.getText().toString());
                if(tag==1){
                    iv_tie_zi_detail_zan_evaluate.setImageResource(R.drawable.zan_normal);
                    iv_tie_zi_detail_zan_evaluate.setTag(0);
                    iv_tie_zi_zan.setImageResource(R.drawable.zan_normal);
                    iv_tie_zi_zan.setTag(0);
                    count--;
                    tv_tie_zi_detail_zan_num.setText(obj.getThumbup_count()+"");
                }else{
                    iv_tie_zi_detail_zan_evaluate.setImageResource(R.drawable.zan_select);
                    iv_tie_zi_detail_zan_evaluate.setTag(1);
                    iv_tie_zi_zan.setImageResource(R.drawable.zan_select);
                    iv_tie_zi_zan.setTag(1);
                    count++;
                    tv_tie_zi_detail_zan_num.setText(obj.getThumbup_count()+"");
                }
            }
        });

    }
    @OnClick({R.id.iv_tie_zi_all_evaluate_close,R.id.iv_tie_zi_zan,R.id.iv_tie_zi_share})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.iv_tie_zi_all_evaluate_close:
                 dismiss();
                break;
            case R.id.iv_tie_zi_zan:
                zanEvaluate(evaluateDetail.getComments_id());
                break;
            case R.id.iv_tie_zi_share:
                showFenXiang("0");
                break;
        }
    }

    @Override
    public void loadMore() {
        getData(pageNum, true);
    }

    @Override
    public void again() {
        initData();
    }


    public void showProgress(){
        if (pl_load != null) {
            pl_load.showProgress();
        }
    }
    public void onDestroy() {
        super.onDestroy();
        mUnBind.unbind();
    }
    public void showLoading() {
        Loading.show(this.getActivity());
    }
    protected String getUserId(){
        return SPUtils.getPrefString(mContext, Config.user_id,null);
    }
    public void showMsg(String msg) {
        ToastUtils.showToast(this.getActivity(), msg);
    }
    /*****************************************************************第三方分享********************************************************************************/
    protected void fenXiang(SHARE_MEDIA shareMedia, String  fenXiangId) {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("goods_id",fenXiangId);
        map.put("sign",GetSign.getSign(map));
        com.sk.jintang.module.my.network.ApiRequest.fenXiang(map, new MyCallBack<FenXiangObj>(mContext,true) {
            @Override
            public void onSuccess(FenXiangObj obj) {
                UMWeb web = new UMWeb(obj.getShare_link());
                UMImage image=new UMImage(mContext,R.drawable.app_default);
                web.setTitle(obj.getTitle());//标题
                web.setThumb(image);  //缩略图
                web.setDescription(obj.getContent());//描述
                new ShareAction(mContext)
                        .setPlatform(shareMedia)//传入平台
                        .withMedia(web)
//                      .withText(getSStr(tv_fenxiao_detail_code))//分享内容
                        .setCallback(getListener())
                        .share();
            }
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                dismissLoading();
            }
        });
    }
    protected UMShareListener getListener() {
        return new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                dismissLoading();
                Log.i("============","============onStart");
            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                Log.i("============","============onResult");
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                showMsg(throwable.getMessage());
                Log.i("============","============onError");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                Log.i("============","============onCancel");
            }
        };
    }
    BottomSheetDialog fenXiangDialog;
    public void showFenXiang(String fenXiangId){
        if (fenXiangDialog == null) {
            View sexView= LayoutInflater.from(mContext).inflate(R.layout.popu_fen_xiang,null);
            /*iv_yaoqing_wx
iv_yaoqing_friend
iv_yaoqing_qq
iv_yaoqing_qzone
iv_yaoqing_sina*/
            sexView.findViewById(R.id.iv_yaoqing_wx).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    if (!UMShareAPI.get(mContext).isInstall(mContext, SHARE_MEDIA.WEIXIN)) {
                        showMsg("请安装微信之后再试");
                        return;
                    }
                    fenXiang(SHARE_MEDIA.WEIXIN,fenXiangId);
                    fenXiangDialog.dismiss();

                }
            });
            sexView.findViewById(R.id.iv_yaoqing_friend).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    if (!UMShareAPI.get(mContext).isInstall(mContext, SHARE_MEDIA.WEIXIN)) {
                        showMsg("请安装微信之后再试");
                        return;
                    }
                    fenXiang(SHARE_MEDIA.WEIXIN_CIRCLE,fenXiangId);
                    fenXiangDialog.dismiss();

                }
            });
            sexView.findViewById(R.id.iv_yaoqing_qq).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    if (!UMShareAPI.get(mContext).isInstall(mContext, SHARE_MEDIA.QQ)) {
                        showMsg("请安装QQ之后再试");
                        return;
                    }
                    fenXiang(SHARE_MEDIA.QQ,fenXiangId);
                    fenXiangDialog.dismiss();
                }
            });
            sexView.findViewById(R.id.iv_yaoqing_qzone).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    if (!UMShareAPI.get(mContext).isInstall(mContext, SHARE_MEDIA.QQ)) {
                        showMsg("请安装QQ之后再试");
                        return;
                    }
                    fenXiang(SHARE_MEDIA.QZONE,fenXiangId);
                    fenXiangDialog.dismiss();
                }
            });
            /*sexView.findViewById(R.id.iv_yaoqing_sina).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    showMsg("正在开发中");
                    fenXiangDialog.dismiss();
                }
            });*/
            sexView.findViewById(R.id.tv_fenxiang_cancle).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    fenXiangDialog.dismiss();
                }
            });
            fenXiangDialog=new BottomSheetDialog(mContext);
            fenXiangDialog.setCanceledOnTouchOutside(true);
            fenXiangDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            fenXiangDialog.setContentView(sexView);
        }
        fenXiangDialog.show();
    }
    protected void dismissLoading() {
        Loading.dismissLoading();
    }
}
