package com.sk.jintang.shop.fragments;



import android.os.Bundle;
import android.view.View;

import com.shizhefei.fragment.LazyFragment;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseFragment;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.home.network.response.SpecialStoreObj;
import com.sk.jintang.shop.MainShopActivity;
import com.sk.jintang.shop.Views.ListContainer;
import com.sk.jintang.shop.adapters.FoodAdapter;
import com.sk.jintang.shop.adapters.TypeAdapter;
import com.sk.jintang.shop.network.ApiRequest;
import com.sk.jintang.shop.network.response.ShopDataObj;

import java.util.HashMap;
import java.util.Map;



public class FirstFragment extends LazyFragment {


	private ListContainer listContainer;

	private  String storeId;
	@Override
	protected void onCreateViewLazy(Bundle savedInstanceState) {
		super.onCreateViewLazy(savedInstanceState);
		setContentView(R.layout.fragment_first);
		listContainer = (ListContainer) findViewById(R.id.listcontainer);
		Bundle bundle = getArguments();//从activity传过来的Bundle
		if(bundle!=null){
			storeId = (bundle.getString("storeId"));
		}
		getData();
	}

	public FoodAdapter getFoodAdapter() {
		return listContainer.foodAdapter;
	}

	public TypeAdapter getTypeAdapter() {
		return listContainer.typeAdapter;
	}

	private void getData() {
		Map<String,String> map=new HashMap<String,String>();
		map.put("storeId",storeId);
		map.put("sign", GetSign.getSign(map));
		ApiRequest.getSpecialStoreGoodsList(map, new MyCallBack<ShopDataObj>(getActivity()) {
			@Override
			public void onSuccess(ShopDataObj list) {
				listContainer.loadData(list);
				listContainer.setAddClick((MainShopActivity) getActivity());
			}
		});
	}

}
