package com.sk.jintang.shop.detail;


import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sk.jintang.R;
import com.sk.jintang.shop.Views.AddWidget;
import com.sk.jintang.shop.bean.FoodBean;
import com.sk.jintang.shop.network.response.ShopDataObj;

import java.util.List;

class DetailAdapter extends BaseQuickAdapter<ShopDataObj.GoodsListBean, BaseViewHolder> {
	private AddWidget.OnAddClick onAddClick;

	DetailAdapter(@Nullable List<ShopDataObj.GoodsListBean> data, AddWidget.OnAddClick onAddClick) {
		super(R.layout.item_detail, data);
		this.onAddClick = onAddClick;
	}

	@Override
	protected void convert(BaseViewHolder helper, ShopDataObj.GoodsListBean item) {
		helper.setText(R.id.textView6, item.getGoods_name())
				.setText(R.id.textView7, item.getSales_volume())
				.setText(R.id.textView8, item.getStrPrice(mContext))
			;
		AddWidget addWidget = helper.getView(R.id.detail_addwidget);
		addWidget.setData(onAddClick,item);
	}
}
