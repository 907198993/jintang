package com.sk.jintang.shop.adapters;


import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sk.jintang.R;
import com.sk.jintang.shop.Views.AddWidget;
import com.sk.jintang.shop.bean.FoodBean;
import com.sk.jintang.shop.network.response.ShopDataObj;


import java.math.BigDecimal;
import java.util.List;

public class CarAdapter extends BaseQuickAdapter<ShopDataObj.GoodsListBean, BaseViewHolder> {
	private AddWidget.OnAddClick onAddClick;

	public CarAdapter(@Nullable List<ShopDataObj.GoodsListBean> data, AddWidget.OnAddClick onAddClick) {
		super(R.layout.item_car, data);
		this.onAddClick = onAddClick;
	}

	@Override
	protected void convert(BaseViewHolder helper, ShopDataObj.GoodsListBean item) {
		helper.setText(R.id.car_name, item.getGoods_name())
				.setText(R.id.car_price, item.getStrPrice(mContext, item.getPrice().multiply(BigDecimal.valueOf(item.getSelectCount()))))
		;
		AddWidget addWidget = helper.getView(R.id.car_addwidget);
//		addWidget.setData(this, helper.getAdapterPosition(), onAddClick);
		addWidget.setData(onAddClick,item);
	}
}
