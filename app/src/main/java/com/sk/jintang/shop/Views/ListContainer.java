package com.sk.jintang.shop.Views;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.sk.jintang.R;
import com.sk.jintang.shop.adapters.FoodAdapter;
import com.sk.jintang.shop.adapters.TypeAdapter;
import com.sk.jintang.shop.bean.FoodBean;
import com.sk.jintang.shop.bean.TypeBean;
import com.sk.jintang.shop.detail.DetailActivity;
import com.sk.jintang.shop.network.response.ShopDataObj;
import com.sk.jintang.shop.utils.BaseUtils;
import com.sk.jintang.shop.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

public class ListContainer extends LinearLayout {

	public TypeAdapter typeAdapter;
	private LinearLayoutManager linearLayoutManager;
//	private List<FoodBean> foodBeanList;
	private boolean move;
	private int index;
	private Context mContext;
	public FoodAdapter foodAdapter;
	public static List<ShopDataObj.GoodsListBean> commandList = new ArrayList<>();
	private TextView tvStickyHeaderView;
	private View stickView;
	private List<String> shoppeGoodsType= new ArrayList<String>();
	private List<ShopDataObj.GoodsListBean> foodBeanList= new ArrayList<ShopDataObj.GoodsListBean>();
	RecyclerView recyclerView1;
	RecyclerView recyclerView2;
	private  int startPs;//起送价格
	private  int shippingFree;
	public ListContainer(Context context) {
		super(context);

	}


	public ListContainer(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		inflate(mContext, R.layout.view_listcontainer, this);
		recyclerView1 = findViewById(R.id.recycler1);
		recyclerView2 = findViewById(R.id.recycler2);

	}

	public void loadData(ShopDataObj list){
		startPs = list.getStartPs();
		shippingFree = list.getIsFreePs();
		//左侧数据
		for (int i = 0;i<list.getStoreTypeList().size();i++){
			shoppeGoodsType.add(list.getStoreTypeList().get(i).getTypeName());
		}
		//右侧数据
		for (int i = 0;i<list.getGoodsList().size();i++){
			ShopDataObj.GoodsListBean goodsListBean = new ShopDataObj.GoodsListBean();
			goodsListBean.setGoods_image(list.getGoodsList().get(i).getGoods_image());
			goodsListBean.setTypeName(list.getGoodsList().get(i).getTypeName());
			goodsListBean.setGoods_name(list.getGoodsList().get(i).getGoods_name());
			goodsListBean.setPrice(list.getGoodsList().get(i).getPrice());
			goodsListBean.setSales_volume(list.getGoodsList().get(i).getSales_volume());
			goodsListBean.setGoodsId(list.getGoodsList().get(i).getGoodsId());
			goodsListBean.setSpecificationList(list.getGoodsList().get(i).getSpecificationList());
			foodBeanList.add(goodsListBean);
		}




		recyclerView1.setLayoutManager(new LinearLayoutManager(mContext));
		typeAdapter = new TypeAdapter(shoppeGoodsType);
		View view = new View(mContext);
		view.setMinimumHeight(ViewUtils.dip2px(mContext, 50));
		typeAdapter.addFooterView(view);
		typeAdapter.bindToRecyclerView(recyclerView1);
		recyclerView1.addItemDecoration(new SimpleDividerDecoration(mContext));
		((DefaultItemAnimator) recyclerView1.getItemAnimator()).setSupportsChangeAnimations(false);
		//左侧列表
		recyclerView1.addOnItemTouchListener(new OnItemClickListener() {
			@Override
			public void onSimpleItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
				if (recyclerView2.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
					typeAdapter.fromClick = true;
					typeAdapter.setChecked(i);
					String type = view.getTag().toString();
					for (int ii = 0; ii < foodBeanList.size(); ii++) {
						ShopDataObj.GoodsListBean typeBean = foodBeanList.get(ii);
						if (typeBean.getTypeName().equals(type)) {
							index = ii;
							moveToPosition(index);//移动
							break;
						}
					}
				}
			}
		});

		linearLayoutManager = new LinearLayoutManager(mContext);
		recyclerView2.setLayoutManager(linearLayoutManager);
		((DefaultItemAnimator) recyclerView2.getItemAnimator()).setSupportsChangeAnimations(false);
//		foodBeanList = BaseUtils.getDatas(mContext);
//		commandList = BaseUtils.getDetails(foodBeanList);
		//
		recyclerView2.addOnItemTouchListener(new OnItemClickListener() {
			@Override
			public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
				super.onItemChildClick(adapter, view, position);
				if (view.getId() == R.id.iv_food) {
					Intent intent = new Intent(mContext, DetailActivity.class);
					intent.putExtra("food", (ShopDataObj.GoodsListBean) adapter.getData().get(position));
					intent.putExtra("startPs", startPs);
					intent.putExtra("shippingFree", shippingFree);
					intent.putExtra("position", position);
					mContext.startActivity(intent);
					((Activity) mContext).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				}
			}

			@Override
			public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

			}
		});

	}
	private void moveToPosition(int n) {
		//先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
		int firstItem = linearLayoutManager.findFirstVisibleItemPosition();
		int lastItem = linearLayoutManager.findLastVisibleItemPosition();
		//然后区分情况
		if (n <= firstItem) {
			//当要置顶的项在当前显示的第一个项的前面时
			recyclerView2.scrollToPosition(n);
		} else if (n <= lastItem) {
			//当要置顶的项已经在屏幕上显示时
			int top = recyclerView2.getChildAt(n - firstItem).getTop();
			recyclerView2.scrollBy(0, top);
		} else {
			//当要置顶的项在当前显示的最后一项的后面时
			recyclerView2.scrollToPosition(n);
			//这里这个变量是用在RecyclerView滚动监听里面的
			move = true;
		}
	}


	public void setAddClick(AddWidget.OnAddClick onAddClick) {
		foodAdapter = new FoodAdapter(foodBeanList, onAddClick);
		View view = new View(mContext);
		view.setMinimumHeight(ViewUtils.dip2px(mContext, 50));
		foodAdapter.addFooterView(view);
		foodAdapter.bindToRecyclerView(recyclerView2);
		stickView = findViewById(R.id.stick_header);
		tvStickyHeaderView = findViewById(R.id.tv_header); //右边的标题
		tvStickyHeaderView.setText("类别0");
        recyclerView2.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                typeAdapter.fromClick = false;
                return false;
            }
        });
		recyclerView2.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				if (move) {
					move = false;
					//获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
					int n = index - linearLayoutManager.findFirstVisibleItemPosition();
					if (0 <= n && n < recyclerView.getChildCount()) {
						//获取要置顶的项顶部离RecyclerView顶部的距离
						int top = recyclerView.getChildAt(n).getTop();
						//最后的移动
						recyclerView.smoothScrollBy(0, top);
					}
				} else {
					View stickyInfoView = recyclerView.findChildViewUnder(stickView.getMeasuredWidth() / 2, 5);
					if (stickyInfoView != null && stickyInfoView.getContentDescription() != null) {
						tvStickyHeaderView.setText(String.valueOf(stickyInfoView.getContentDescription()));
						typeAdapter.setType(String.valueOf(stickyInfoView.getContentDescription()));
					}

					View transInfoView = recyclerView.findChildViewUnder(stickView.getMeasuredWidth() / 2, stickView.getMeasuredHeight
							() + 1);
					if (transInfoView != null && transInfoView.getTag() != null) {
						int transViewStatus = (int) transInfoView.getTag();
						int dealtY = transInfoView.getTop() - stickView.getMeasuredHeight();
						if (transViewStatus == FoodAdapter.HAS_STICKY_VIEW) {
							if (transInfoView.getTop() > 0) {
								stickView.setTranslationY(dealtY);
							} else {
								stickView.setTranslationY(0);
							}
						} else if (transViewStatus == FoodAdapter.NONE_STICKY_VIEW) {
							stickView.setTranslationY(0);
						}
					}
				}
			}
		});
	}
}
