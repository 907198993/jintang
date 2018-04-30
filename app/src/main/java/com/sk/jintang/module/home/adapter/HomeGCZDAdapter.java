package com.sk.jintang.module.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.SPUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.github.baseclass.utils.ActUtils;
import com.sk.jintang.Config;
import com.sk.jintang.R;
import com.sk.jintang.module.home.Constant;
import com.sk.jintang.module.home.activity.GongChangActivity;
import com.sk.jintang.module.home.activity.GoodsForGongChangActivity;
import com.sk.jintang.module.home.network.response.HomeButtomObj;
import com.sk.jintang.module.my.activity.LoginActivity;

/**
 * Created by administartor on 2017/9/12.
 */

public class HomeGCZDAdapter extends BaseRecyclerAdapter<HomeButtomObj.FactoryListBean> {

    public HomeGCZDAdapter(Context ctx, int layoutId) {
        super(ctx, layoutId);
    }
    public void bindData(RecyclerViewHolder holder, int i, HomeButtomObj.FactoryListBean bean) {
        LinearLayout ll_gc_view = (LinearLayout) holder.getView(R.id.ll_gc_view);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.width= (PhoneUtils.getScreenWidth(mContext)-4)/3;
        layoutParams.height= (PhoneUtils.getScreenWidth(mContext)-4)/3;
        ll_gc_view.setLayoutParams(layoutParams);
        TextView tv_home_gc = holder.getTextView(R.id.tv_home_gc);
        ImageView imageView = holder.getImageView(R.id.iv_home_gc);
        if(i==mList.size()){
            imageView.setVisibility(View.GONE);
            tv_home_gc.setText("更多>>");
            tv_home_gc.setTextColor(mContext.getResources().getColor(R.color.green));
            holder.itemView.setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    ActUtils.STActivity((Activity) mContext, GongChangActivity.class);
                }
            });
        }else{
            imageView.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(bean.getFactory_image()).error(R.color.c_press).into(imageView);
            tv_home_gc.setText(bean.getFactory_name());
            tv_home_gc.setTextColor(mContext.getResources().getColor(R.color.gray_33));
            holder.itemView.setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    if (TextUtils.isEmpty(SPUtils.getPrefString(mContext, Config.user_id, null))) {
                        ActUtils.STActivity((Activity) mContext,LoginActivity.class);
                        return;
                    }
                    Intent intent=new Intent();
                    intent.putExtra(Constant.IParam.gongChangId,bean.getFactory_id()+"");
                    ActUtils.STActivity((Activity) mContext,intent, GoodsForGongChangActivity.class);
                }
            });
        }
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerViewHolder holder  = new RecyclerViewHolder(this.mContext, this.mInflater.inflate(R.layout.item_home_gczd, parent, false));
        return holder;
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size()+1;
    }
}
