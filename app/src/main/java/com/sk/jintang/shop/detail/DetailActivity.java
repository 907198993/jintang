package com.sk.jintang.shop.detail;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.customview.FlowLayout;
import com.github.customview.MyTextView;
import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.sk.jintang.R;
import com.sk.jintang.module.orderclass.network.request.ShoppingCartItem;
import com.sk.jintang.module.orderclass.network.response.GuiGeObj;
import com.sk.jintang.module.shoppingcart.activity.SureGoodsActivity;
import com.sk.jintang.shop.BaseActivity;
import com.sk.jintang.shop.MainShopActivity;
import com.sk.jintang.shop.Views.AddWidget;
import com.sk.jintang.shop.Views.ListContainer;
import com.sk.jintang.shop.Views.ShopCarView;
import com.sk.jintang.shop.adapters.CarAdapter;
import com.sk.jintang.shop.bean.FoodBean;
import com.sk.jintang.shop.network.response.ShopDataObj;
import com.sk.jintang.shop.utils.ViewUtils;

import static com.sk.jintang.shop.MainShopActivity.CLEARCAR_ACTION;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends BaseActivity implements AddWidget.OnAddClick {
	private ShopDataObj.GoodsListBean goodsListBean;
	private AddWidget detail_add;
	public BottomSheetBehavior behavior;
	private ShopCarView shopCarView;
	private CarAdapter carAdapter;
	private CoordinatorLayout detail_main;
	private DetailHeaderBehavior dhb;
	private View headerView;
	private int position = -1;
	private DetailAdapter dAdapter;
	private int startPs;
	private int shippingFree;
	private  TextView  car_limit;
	private MyTextView selectGuiGeView;



	@Override
	protected int getContentView() {
		return R.layout.act_main_shop_goods_detail;
	}

	@Override
	protected void initView() {
		goodsListBean = (ShopDataObj.GoodsListBean) getIntent().getSerializableExtra("food");
		startPs = getIntent().getIntExtra("startPs", -1);
		shippingFree = getIntent().getIntExtra("shippingFree", -1);
		position = getIntent().getIntExtra("position", -1);
		detail_main = (CoordinatorLayout) findViewById(R.id.detail_main);
		headerView = findViewById(R.id.headerview);
		CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) headerView.getLayoutParams();
		dhb = (DetailHeaderBehavior) lp.getBehavior();
		ImageView iv_detail = (ImageView) findViewById(R.id.iv_detail);
		Glide.with(mContext).load(goodsListBean.getGoods_image()).error(R.color.c_press).into(iv_detail);
		TextView toolbar_title = (TextView) findViewById(R.id.toolbar_title);
		toolbar_title.setText(goodsListBean.getGoods_name());
		TextView detail_name = (TextView) findViewById(R.id.detail_name);
		detail_name.setText(goodsListBean.getGoods_name());
		TextView detail_sale = (TextView) findViewById(R.id.detail_sale);
		detail_sale.setText("销量"+goodsListBean.getSales_volume());
		TextView detail_price = (TextView) findViewById(R.id.detail_price);
		detail_price.setText(goodsListBean.getStrPrice(mContext));
		car_limit = findViewById(R.id.car_limit);
		car_limit.setOnClickListener(this);
		detail_add = (AddWidget) findViewById(R.id.detail_add);
		detail_add.setData(this, goodsListBean);
		detail_add.setState(goodsListBean.getSelectCount());

//		FlowLayout fl_guige = findViewById(R.id.fl_guige);
//		addGuiGeView(iv_guige_img, tv_guige_price, tv_guige_kucun, fl_guige, goodsListBean.getSpecificationList());
//		initRecyclerView();
		initShopCar();
	}

	@Override
	protected void initData() {

	}

//	private void addGuiGeView(ImageView iv_guige_img, TextView tv_guige_price, TextView tv_guige_kucun, FlowLayout fl_guige, final List<ShopDataObj.GoodsListBean.SpecificationListBean> list) {
//		if (list.size()>1) {
//			for (int i = 0; i < list.size(); i++) {
//				MyTextView textView = new MyTextView(mContext);
////                textView.setWidth(PhoneUtils.dip2px(mContext, 97));
//				textView.setHeight(PhoneUtils.dip2px(mContext, 30));
//				textView.setGravity(Gravity.CENTER);
//				FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//				layoutParams.setMargins(0, PhoneUtils.dip2px(mContext, 10), PhoneUtils.dip2px(mContext, 10), 0);
//				textView.setLayoutParams(layoutParams);
//				textView.setPadding(PhoneUtils.dip2px(mContext,9),0,PhoneUtils.dip2px(mContext,9),0);
//				textView.setText(list.get(i).getSpecification());
//				textView.setTextColor(mContext.getResources().getColor(R.color.white));
//				textView.setTextSize(14);
//				textView.setRadius(PhoneUtils.dip2px(mContext, 4));
//				textView.setSolidColor(Color.parseColor("#e0e0e0"));
//				textView.complete();
//				textView.setTag(i);
//				textView.setOnClickListener(new MyOnClickListener() {
//					@Override
//					protected void onNoDoubleClick(View view) {
//						if (selectGuiGeView == null) {
//							selectGuiGeView = textView;
//							selectGuiGeView.setSolidColor(mContext.getResources().getColor(R.color.green));
//							selectGuiGeView.complete();
//
//							clickGuiGe();
//						} else if (selectGuiGeView != textView) {
//							selectGuiGeView.setSolidColor(Color.parseColor("#e0e0e0"));
//							selectGuiGeView.complete();
//
//							selectGuiGeView = textView;
//							selectGuiGeView.setSolidColor(mContext.getResources().getColor(R.color.green));
//							selectGuiGeView.complete();
//
//							clickGuiGe();
//						}
//					}
//
//					private void clickGuiGe() {
//						selectGuiGeObj = list.get((int) textView.getTag());
//						tv_guige_price.setText("¥" + list.get((int) textView.getTag()).getPrice());
//						tv_guige_kucun.setText("库存" + list.get((int) textView.getTag()).getStock() + "件");
//						Glide.with(mContext).load(list.get((int) textView.getTag()).getImages()).error(R.color.c_press).into(iv_guige_img);
//					}
//				});
//				if (i == 0) {//默认选择第一个
//					selectGuiGeView = textView;
//					selectGuiGeView.setSolidColor(mContext.getResources().getColor(R.color.green));
//					selectGuiGeView.complete();
//
//					selectGuiGeObj = list.get((int) textView.getTag());
//					tv_guige_price.setText("¥" + list.get((int) textView.getTag()).getPrice());
//					tv_guige_kucun.setText("库存" + list.get((int) textView.getTag()).getStock() + "件");
//					Glide.with(mContext).load(list.get((int) textView.getTag()).getImages()).error(R.color.c_press).into(iv_guige_img);
//				}
//				fl_guige.addView(textView);
//			}
//		}
//	}

//	private void initRecyclerView() {
//		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.detail_recyclerView);
//		recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
//		recyclerView.addItemDecoration(new SpaceItemDecoration());
//		((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
//		dAdapter = new DetailAdapter(ListContainer.commandList, this);
//		View header = View.inflate(mContext, R.layout.item_detail_header, null);
//		dAdapter.addHeaderView(header);
//		TextView footer = new TextView(mContext);
//		ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewUtils.dip2px(mContext, 100));
//		footer.setText("已经没有更多了");
//		footer.setTextSize(12);
//		footer.setTextColor(ContextCompat.getColor(mContext, R.color.detail_hint));
//		footer.setGravity(Gravity.CENTER_HORIZONTAL);
//		footer.setPadding(0, 30, 0, 0);
//		footer.setLayoutParams(lp);
//		dAdapter.addFooterView(footer);
//		dAdapter.bindToRecyclerView(recyclerView);
//	}


	private void initShopCar() {
		shopCarView = (ShopCarView) findViewById(R.id.car_mainfl);
		final View blackView = findViewById(R.id.blackview);
		behavior = BottomSheetBehavior.from(findViewById(R.id.car_container));
		behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
			@Override
			public void onStateChanged(@NonNull View bottomSheet, int newState) {
				shopCarView.sheetScrolling = false;
				dhb.setDragable(false);
				if (newState == BottomSheetBehavior.STATE_COLLAPSED || newState == BottomSheetBehavior.STATE_HIDDEN) {
					blackView.setVisibility(View.GONE);
					dhb.setDragable(true);
				}
			}

			@Override
			public void onSlide(@NonNull View bottomSheet, float slideOffset) {
				shopCarView.sheetScrolling = true;
				blackView.setVisibility(View.VISIBLE);
				ViewCompat.setAlpha(blackView, slideOffset);
			}
		});
		blackView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
				return true;
			}
		});
		shopCarView.setBehavior(behavior);
		RecyclerView carRecView = (RecyclerView) findViewById(R.id.car_recyclerview);
		carRecView.setLayoutManager(new LinearLayoutManager(mContext));
		((DefaultItemAnimator) carRecView.getItemAnimator()).setSupportsChangeAnimations(false);
		ArrayList<ShopDataObj.GoodsListBean> flist = new ArrayList<ShopDataObj.GoodsListBean>();
		flist.addAll(MainShopActivity.carAdapter.getData());
		carAdapter = new CarAdapter(flist, this);
		carAdapter.bindToRecyclerView(carRecView);
//		handleCommand(goodsListBean);
		shopCarView.post(new Runnable() {
			@Override
			public void run() {
				dealCar(goodsListBean);
			}
		});
	}

	@Override
	public void onAddClick(View view, ShopDataObj.GoodsListBean fb) {
		dealCar(fb);
		ViewUtils.addTvAnim(view, shopCarView.carLoc, mContext, detail_main);
		if (!dhb.canDrag) {
			ViewAnimator.animate(headerView).alpha(1, -4).duration(410).onStop(new AnimationListener.Stop() {
				@Override
				public void onStop() {
					finish();
				}
			}).start();
		}
	}

	@Override
	public void onSubClick(ShopDataObj.GoodsListBean fb) {
		dealCar(fb);
	}


//	private void dealCar(ShopDataObj.GoodsListBean foodBean) {
//		BigDecimal amount = new BigDecimal(0.0);
//		int total = 0;
//		boolean hasFood = false;
//		if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED || foodBean.getGoodsId() == this.goodsListBean.getGoodsId() && foodBean.getSelectCount() !=
//				this.goodsListBean.getSelectCount()) {
//			this.goodsListBean = foodBean;
//			detail_add.expendAdd(foodBean.getSelectCount());
//			handleCommand(foodBean);
//		}
//		List<ShopDataObj.GoodsListBean> flist = carAdapter.getData();
//		int p = -1;
//		for (int i = 0; i < flist.size(); i++) {
//			ShopDataObj.GoodsListBean fb = flist.get(i);
//			total += fb.getSelectCount();
//			amount = amount.add(fb.getPrice().multiply(BigDecimal.valueOf(fb.getSelectCount())));
//			if (fb.getGoodsId() == foodBean.getGoodsId()) {
//				hasFood = true;
//				if (foodBean.getSelectCount() == 0) {
//					p = i;
//				} else {
//					carAdapter.setData(i, foodBean);
//				}
//			}
//		}
//		if (p >= 0) {
//			carAdapter.remove(p);
//		} else if (!hasFood && foodBean.getSelectCount() > 0) {
//			carAdapter.addData(foodBean);
//			amount = amount.add(foodBean.getPrice().multiply(BigDecimal.valueOf(foodBean.getSelectCount())));
//			total += foodBean.getSelectCount();
//		}
//		shopCarView.showBadge(total);
//		shopCarView.updateAmount(amount,startPs);
//		Intent intent = new Intent(MainShopActivity.CAR_ACTION);
//		if (foodBean.getGoodsId() == this.goodsListBean.getGoodsId()) {
//			intent.putExtra("position", position);
//		}
//		intent.putExtra("foodbean",(ShopDataObj.GoodsListBean)  foodBean);
//		sendBroadcast(intent);
//	}
	private void dealCar(ShopDataObj.GoodsListBean foodBean) {
		BigDecimal amount = new BigDecimal(0.0);
		int total = 0;
		boolean hasFood = false;
		if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED || foodBean.getGoodsId() == this.goodsListBean.getGoodsId() && foodBean.getSelectCount() !=
				this.goodsListBean.getSelectCount()) {
			this.goodsListBean = foodBean;
			detail_add.expendAdd(foodBean.getSelectCount());
//			handleCommand(foodBean);
		}
		List<ShopDataObj.GoodsListBean> flist = carAdapter.getData();
		int p = -1;
		for (int i = 0; i < flist.size(); i++) {
			ShopDataObj.GoodsListBean fb = flist.get(i);
			total += fb.getSelectCount();
			amount = amount.add(fb.getPrice().multiply(BigDecimal.valueOf(fb.getSelectCount())));
			if (fb.getGoodsId().equals(foodBean.getGoodsId())) {
				hasFood = true;
				if (foodBean.getSelectCount() == 0) {
					p = i;
				} else {
					carAdapter.setData(i, foodBean);
				}
			}
		}
		if (p >= 0) {
			carAdapter.remove(p);
		} else if (!hasFood && foodBean.getSelectCount() > 0) {
			carAdapter.addData(foodBean);
			amount = amount.add(foodBean.getPrice().multiply(BigDecimal.valueOf(foodBean.getSelectCount())));
			total += foodBean.getSelectCount();
		}
		shopCarView.showBadge(total);
		shopCarView.updateAmount(amount,startPs,shippingFree);
		Intent intent = new Intent(MainShopActivity.CAR_ACTION);
		if (foodBean.getGoodsId() == this.goodsListBean.getGoodsId()) {
			intent.putExtra("position", position);
		}
		intent.putExtra("foodbean", foodBean);
		sendBroadcast(intent);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.car_limit:
				Intent intent;
				if (carAdapter.getData().size() == 0) {
					return;
				} else {
					ShoppingCartItem item = new ShoppingCartItem();
					List<ShopDataObj.GoodsListBean> flist = carAdapter.getData();
					List<ShoppingCartItem.BodyBean> body = new ArrayList<>();
					ShoppingCartItem.BodyBean bodyBean;
					for (int i = 0; i < flist.size(); i++) {
						bodyBean = new ShoppingCartItem.BodyBean();
						bodyBean.setShopping_cart_id(0);
						bodyBean.setGoods_id(flist.get(i).getGoodsId());
						bodyBean.setNumber(flist.get(i).getSelectCount());
						bodyBean.setSpecification_id(flist.get(i).getSpecificationList().get(0).getId());
						body.add(bodyBean);
					}
					item.setBody(body);
					intent = new Intent();
					intent.putExtra(com.sk.jintang.module.shoppingcart.Constant.IParam.specialShoppingGoods, item);
					intent.putExtra(com.sk.jintang.module.orderclass.Constant.IParam.is_special_buy_now, true);
					STActivity(intent, SureGoodsActivity.class);
				}
				break;
		}
	}

	@Override
	public void again() {

	}

	private class SpaceItemDecoration extends RecyclerView.ItemDecoration {

		private int space;

		SpaceItemDecoration() {
			this.space = ViewUtils.dip2px(mContext, 2);
		}

		@Override
		public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
			outRect.left = space;
			outRect.top = space;
			outRect.right = space;
			if (parent.getChildLayoutPosition(view) % 2 == 0) {
				outRect.left = 0;
			}
		}

	}

//	private void handleCommand(ShopDataObj.GoodsListBean foodBean) {
//		for (int i = 0; i < dAdapter.getData().size(); i++) {
//			ShopDataObj.GoodsListBean fb = dAdapter.getItem(i);
//			if (fb.getGoodsId() == foodBean.getGoodsId() && fb.getSelectCount() != foodBean.getSelectCount()) {
//				fb.setSelectCount(foodBean.getSelectCount());
//				dAdapter.setData(i, fb);
//				dAdapter.notifyItemChanged(i);
//				break;
//			}
//		}
//	}

	public void clearCar(View view) {
		ViewUtils.showClearCar(mContext, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				List<ShopDataObj.GoodsListBean> flist = carAdapter.getData();
				for (int i = 0; i < flist.size(); i++) {
					ShopDataObj.GoodsListBean fb = flist.get(i);
					fb.setSelectCount(0);
				}
				carAdapter.setNewData(new ArrayList<ShopDataObj.GoodsListBean>());
				shopCarView.showBadge(0);
				shopCarView.updateAmount(new BigDecimal(0.0),startPs,shippingFree);
				sendBroadcast(new Intent(CLEARCAR_ACTION));
			}
		});
	}
}
