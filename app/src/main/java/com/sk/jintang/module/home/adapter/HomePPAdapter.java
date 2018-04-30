package com.sk.jintang.module.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.SPUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.github.baseclass.utils.ActUtils;
import com.sk.jintang.Config;
import com.sk.jintang.R;
import com.sk.jintang.module.home.Constant;
import com.sk.jintang.module.home.activity.GoodsForPinPaiActivity;
import com.sk.jintang.module.home.activity.PinPaiActivity;
import com.sk.jintang.module.home.network.response.HomeButtomObj;
import com.sk.jintang.module.my.activity.LoginActivity;

/**
 * Created by administartor on 2017/9/12.
 */

public class HomePPAdapter extends BaseRecyclerAdapter<HomeButtomObj.BrandListBean> {


    public HomePPAdapter(Context mContext, int layoutId) {
        super(mContext, layoutId);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerViewHolder holder = new RecyclerViewHolder(this.mContext, this.mInflater.inflate(R.layout.item_home_pp, parent, false));
        return holder;
    }
    @Override
    public void bindData(RecyclerViewHolder holder, int i, HomeButtomObj.BrandListBean bean) {
        ImageView iv_home_pp = holder.getImageView(R.id.iv_home_pp);
        TextView tv_home_pp_more = holder.getTextView(R.id.tv_home_pp_more);
        if(i==mList.size()){
            iv_home_pp.setVisibility(View.GONE);
            tv_home_pp_more.setVisibility(View.VISIBLE);
            holder.itemView.setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    ActUtils.STActivity((Activity) mContext, PinPaiActivity.class);
                }
            });
        }else{
            iv_home_pp.setVisibility(View.VISIBLE);
            tv_home_pp_more.setVisibility(View.GONE);
            Glide.with(mContext).load(bean.getBrand_img()).error(R.color.white).into(iv_home_pp);
            holder.itemView.setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    if (TextUtils.isEmpty(SPUtils.getPrefString(mContext, Config.user_id, null))) {
                        ActUtils.STActivity((Activity) mContext,LoginActivity.class);
                        return;
                    }
                    Intent intent=new Intent();
                    intent.putExtra(Constant.IParam.pinPaiId,bean.getBrand_id()+"");
                    ActUtils.STActivity((Activity) mContext,intent, GoodsForPinPaiActivity.class);
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return mList==null?0:mList.size()+1;
    }
}
