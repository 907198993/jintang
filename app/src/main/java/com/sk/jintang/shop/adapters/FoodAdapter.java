package com.sk.jintang.shop.adapters;


import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.customview.MyTextView;
import com.sk.jintang.R;
import com.sk.jintang.shop.Views.AddWidget;
import com.sk.jintang.shop.bean.FoodBean;
import com.sk.jintang.shop.network.response.ShopDataObj;


import java.util.List;


//右侧的列表
public class FoodAdapter extends BaseQuickAdapter<ShopDataObj.GoodsListBean, BaseViewHolder> {
	public static final int FIRST_STICKY_VIEW = 1;
	public static final int HAS_STICKY_VIEW = 2;
	public static final int NONE_STICKY_VIEW = 3;
	private List<ShopDataObj.GoodsListBean> flist;
	private AddWidget.OnAddClick onAddClick;

	public FoodAdapter(@Nullable List<ShopDataObj.GoodsListBean> data, AddWidget.OnAddClick onAddClick) {
		super(R.layout.item_food, data);
		flist = data;
		this.onAddClick = onAddClick;
	}

	@Override
	protected void convert(BaseViewHolder helper, ShopDataObj.GoodsListBean item) {
		helper.setText(R.id.tv_name, item.getGoods_name())
				.setText(R.id.tv_price,item.getStrPrice(mContext))
				.setText(R.id.tv_sale, "销量"+item.getSales_volume()+"")
//				.setImageResource(R.id.iv_food, item.getGoods_image()).addOnClickListener(R.id.addwidget)
				.addOnClickListener(R.id.iv_food)
//				.addOnClickListener(R.id.my_guige)
				;
		ImageView imageView = helper.getView(R.id.iv_food);
//		MyTextView myTextView =  helper.getView(R.id.my_guige);
//        if(item.getSpecificationList().size()>1){
//			myTextView.setVisibility(View.VISIBLE);
//		}else{
//			myTextView.setVisibility(View.GONE);
//		}
		Glide.with(mContext).load(item.getGoods_image()).error(R.color.c_press).into(imageView);

		AddWidget addWidget = helper.getView(R.id.addwidget);
//		addWidget.setData(this, helper.getAdapterPosition(), onAddClick);
		addWidget.setData(onAddClick,item);

		if (helper.getAdapterPosition() == 0) {
			helper.setVisible(R.id.stick_header, true)
					.setText(R.id.tv_header, item.getTypeName())
					.setTag(R.id.food_main, FIRST_STICKY_VIEW);
		} else {
			if (!TextUtils.equals(item.getTypeName(), flist.get(helper.getAdapterPosition() - 1).getTypeName())) {
				helper.setVisible(R.id.stick_header, true)
						.setText(R.id.tv_header, item.getTypeName())
						.setTag(R.id.food_main, HAS_STICKY_VIEW);
			} else {
				helper.setVisible(R.id.stick_header, false)
						.setTag(R.id.food_main, NONE_STICKY_VIEW);
			}
		}
		helper.getConvertView().setContentDescription(item.getTypeName());
	}

}
