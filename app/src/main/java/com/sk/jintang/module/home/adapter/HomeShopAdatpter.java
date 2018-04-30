package com.sk.jintang.module.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.github.baseclass.utils.ActUtils;
import com.sk.jintang.R;
import com.sk.jintang.module.home.Constant;
import com.sk.jintang.module.home.network.response.SpecialRoadObj;
import com.sk.jintang.shop.ShopListsActivity;

/**
 * Created by Administrator on 2018/4/23.
 */

public class HomeShopAdatpter  extends BaseRecyclerAdapter<SpecialRoadObj> {

    public HomeShopAdatpter(Context mContext, int layoutId) {
        super(mContext, layoutId);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerViewHolder holder  = new RecyclerViewHolder(this.mContext, this.mInflater.inflate(R.layout.item_home_huodong_other, parent, false));
        return holder;

    }
    @Override
    public void bindData(RecyclerViewHolder holder, int i, SpecialRoadObj bean) {
        TextView textView = holder.getTextView(R.id.tv_home_huodong_other);
        ImageView imageView = holder.getImageView(R.id.iv_home_huodong_other);

            Glide.with(mContext).load(bean.getTypeImg()).error(R.color.background_f2).into(imageView);
            textView.setText(bean.getTypeName());
            holder.itemView.setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    Intent intent=new Intent();
                    intent.putExtra(Constant.IParam.specialStoreId,bean.getId()+"");
                    intent.putExtra(Constant.IParam.specialStoreTitle,bean.getTypeName()+"");
                    ActUtils.STActivity((Activity) mContext,intent, ShopListsActivity.class);
                }
            });
    }
    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }
}
