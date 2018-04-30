package com.sk.jintang.module.shoppingcart.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.BaseRecyclerAdapter;
import com.github.baseclass.adapter.RecyclerViewHolder;
import com.github.baseclass.utils.ActUtils;
import com.sk.jintang.R;
import com.sk.jintang.module.shoppingcart.activity.ShopGoodsCategoryListActivity;
import com.sk.jintang.module.shoppingcart.network.response.ShopGoodsTypeObj;


public class ShopGoodsCategoryAdapter extends BaseRecyclerAdapter<ShopGoodsTypeObj.TypeListBean> {
  private  String storeId;
    public ShopGoodsCategoryAdapter(Context mContext, int layoutId ,String storeId) {
        super(mContext, layoutId);
        this.storeId = storeId;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerViewHolder holder  = new RecyclerViewHolder(this.mContext, this.mInflater.inflate(R.layout.shop_goods_category_item, parent, false));
        return holder;

    }
    @Override
    public void bindData(RecyclerViewHolder holder, int i, ShopGoodsTypeObj.TypeListBean bean) {
             TextView textView = holder.getTextView(R.id.tv_home_huodong_other);
             textView.setText(bean.getTypeName());
             holder.itemView.setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    Intent intent=new Intent();
                    intent.putExtra(com.sk.jintang.module.my.Constant.IParam.typeId,bean.getTypeId());
                    intent.putExtra(com.sk.jintang.module.my.Constant.IParam.storeId,storeId);
                    intent.putExtra(com.sk.jintang.module.my.Constant.IParam.typeName,bean.getTypeName());
                    ActUtils.STActivity((Activity) mContext,intent, ShopGoodsCategoryListActivity.class);
//                    RxBus.getInstance().post(new MoreCategoryEvent(bean.getTypeId(),bean.getTypeName()));
                }
            });

    }
    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }
}
