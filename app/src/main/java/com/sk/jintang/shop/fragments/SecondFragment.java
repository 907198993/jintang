package com.sk.jintang.shop.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.customview.MyImageView;
import com.shizhefei.fragment.LazyFragment;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseFragment;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.home.activity.MainActivity;
import com.sk.jintang.module.shoppingcart.network.ApiRequest;
import com.sk.jintang.module.shoppingcart.network.response.ShopIntroduceObj;
import com.sk.jintang.shop.MainShopActivity;
import com.sk.jintang.shop.Views.CustomLoadMoreView;
import com.sk.jintang.shop.adapters.CommentAdapter;
import com.sk.jintang.shop.bean.CommentBean;
import com.sk.jintang.shop.network.response.ShopDataObj;
import com.sk.jintang.shop.utils.BaseUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class SecondFragment extends BaseFragment {
	@BindView(R.id.civ_shop_img)
	CircleImageView civShopImg;
	@BindView(R.id.tv_shop_name)
	TextView tvShopName;
	@BindView(R.id.ll_my_info)
	LinearLayout llMyInfo;
	@BindView(R.id.shop_address)
	TextView shopAddress;
	@BindView(R.id.phone_text)
	TextView phoneText;
	@BindView(R.id.shop_phone)
	TextView shopPhone;
	@BindView(R.id.iv_goods_detail_kefu)
	MyImageView ivGoodsDetailKefu;
	@BindView(R.id.shop_name_text)
	TextView shopNameText;
	@BindView(R.id.shop_user_name)
	TextView shopUserName;
	@BindView(R.id.title_id)
	LinearLayout title_id;
	@BindView(R.id.linear_shop_title)
	LinearLayout linear_shop_title;

	private  String storeId;



	private void getData() {
		Map<String,String> map=new HashMap<String,String>();
		map.put("storeId",storeId);
		map.put("sign",  GetSign.getSign(map));
		ApiRequest.getShopIntroduce(map, new MyCallBack<ShopIntroduceObj>(getActivity()) {
			@Override
			public void onSuccess(ShopIntroduceObj obj) {
				Glide.with(getActivity()).load(obj.getStoreHeadImg()).error(R.color.c_press).into(civShopImg);
				title_id.setVisibility(View.GONE);
				linear_shop_title.setVisibility(View.GONE);
				tvShopName.setText(obj.getStoreName());
				shopAddress.setText(obj.getStoreAddress());
				shopPhone.setText(obj.getStoreTel());
				shopUserName.setText(obj.getStoreUserName());
			}
		});
	}

	@Override
	protected int getContentView() {
		return R.layout.shop_introduction;
	}

	@Override
	protected void initView() {
		Bundle bundle = getArguments();//从activity传过来的Bundle
		if(bundle!=null){
			storeId = bundle.getString("storeId");
		}


	}

	@Override
	protected void initData() {
		getData();
	}

	@OnClick({R.id.iv_goods_detail_phone})
	protected void onViewClick(View v) {
          switch (v.getId()){
			  case R.id.iv_goods_detail_phone :
			  String telStr=shopPhone.getText().toString();
			  Uri uri=Uri.parse("tel:"+telStr);
			  Intent it=new Intent();
			  it.setAction(Intent.ACTION_CALL);
			  it.setData(uri);
			  SecondFragment.this.startActivity(it);
			  	break;
		  }
	}
}
