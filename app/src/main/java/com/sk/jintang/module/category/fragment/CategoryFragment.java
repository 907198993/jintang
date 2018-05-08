package com.sk.jintang.module.category.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.androidtools.PhoneUtils;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseFragment;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.category.adapter.CategoryAdapter;
import com.sk.jintang.module.category.adapter.CategoryTwoAdapter;
import com.sk.jintang.module.category.network.ApiRequest;
import com.sk.jintang.module.category.network.response.CategoryObj;
import com.sk.jintang.module.community.Constant;
import com.sk.jintang.module.orderclass.activity.GoodsClassActivity;
import com.sk.jintang.tools.DividerGridItemDecoration;

import java.util.HashMap;
import java.util.Map;


import butterknife.BindView;
/**
 * Created by Administrator on 2018/3/31.
 */

public class CategoryFragment extends BaseFragment implements CategoryAdapter.OnItemClickListener,CategoryTwoAdapter.OnItem2ClickListener{


    @BindView(R.id.recyclerview_category)
    RecyclerView recyclerviewCategory;
    @BindView(R.id.recyclerview_wares)
    RecyclerView recyclerviewWares;

    @BindView(R.id.app_title)
    TextView appTitle;

    private CategoryAdapter categoryAdapter;
    //    private HomeAdapter homeAdapter;
    private CategoryTwoAdapter mCategoryTwodapter;
    @Override
    protected int getContentView() {

        return R.layout.category_fragment;
    }

    @Override
    protected void initView() {

        appTitle.setText("商品分类");
        categoryAdapter=new CategoryAdapter(mContext,0);
        recyclerviewCategory.setAdapter(categoryAdapter);
        recyclerviewCategory.setNestedScrollingEnabled(false);
        categoryAdapter.setOnItemClickListener(this);
        recyclerviewCategory.setLayoutManager(new GridLayoutManager(mContext, 1));
        recyclerviewCategory.addItemDecoration(new DividerGridItemDecoration(mContext, PhoneUtils.dip2px(mContext,1)));

        mCategoryTwodapter=new CategoryTwoAdapter(mContext,0);
        recyclerviewWares.setAdapter(mCategoryTwodapter);
        recyclerviewWares.setNestedScrollingEnabled(false);
        mCategoryTwodapter.setOnItem2ClickListener(this);
        recyclerviewWares.setLayoutManager(new GridLayoutManager(mContext, 3));
        recyclerviewWares.addItemDecoration(new DividerGridItemDecoration(mContext, PhoneUtils.dip2px(mContext,1)));

    }
    @Override
    protected void initData() {
        showProgress();
        getData();

    }

    @Override
    protected void onViewClick(View v) {

    }

    private void getData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("sign", "admin123");
        map.put("parentID", "0");
        ApiRequest.getGoodsCateyoryList(map, new MyCallBack<CategoryObj>(mContext, pcfl, pl_load) {
            @Override
            public void onSuccess(CategoryObj obj) {
//                showCategoryData(obj);

                categoryAdapter.setList(obj.getTypeList(),true);
                if(obj !=null && obj.getTypeList().size()>0)
//                    category_id = obj.getTypeList().get(0).getGoods_type_id();
                requestWares(obj.getTypeList().get(0).getGoods_type_id());

            }



        });

    }


    @Override
    public void onItemClickListener(int pos, CategoryObj.TypeListBean data, int i) {
        //拿适配器调用适配器内部自定义好的setThisPosition方法（参数写点击事件的参数的position）
        categoryAdapter.setThisPosition(pos);
        //嫑忘记刷新适配器
        categoryAdapter.notifyDataSetChanged();
        requestWares(data.getGoods_type_id());

    }



    private void requestWares(String data) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("sign", "admin123");
        map.put("parentID", data);
        ApiRequest.getGoodsCateyoryList(map, new MyCallBack<CategoryObj>(mContext, pcfl, pl_load) {
            @Override
            public void onSuccess(CategoryObj obj) {
//                showCategoryData(obj);
                  mCategoryTwodapter.setList(obj.getTypeList(),true);
            }
        });
    }
    @Override
    public void onItem2ClickListener(int pos, CategoryObj.TypeListBean data, int i) {
        Intent intent=new Intent();
        intent.putExtra(Constant.IParam.typeId,data.getGoods_type_id()+"");
        STActivity(intent,GoodsClassActivity.class);
    }
}
