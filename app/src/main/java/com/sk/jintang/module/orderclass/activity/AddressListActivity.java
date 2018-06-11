package com.sk.jintang.module.orderclass.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.github.androidtools.PhoneUtils;
import com.github.androidtools.SPUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.adapter.LoadMoreAdapter;
import com.github.baseclass.adapter.LoadMoreViewHolder;
import com.github.baseclass.view.MyDialog;
import com.sk.jintang.Config;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.response.AddressObj;
import com.sk.jintang.module.orderclass.Constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/9/2.
 */

public class AddressListActivity extends BaseActivity implements LoadMoreAdapter.OnLoadMoreListener{
    @BindView(R.id.rv_address_list)
    RecyclerView rv_address_list;


    LoadMoreAdapter adapter;
    private boolean isSelectForOrder;
    @Override
    protected int getContentView() {
        setAppTitle("管理收货地址");
        return R.layout.act_address_list;
    }

    @Override
    protected void initView() {
        isSelectForOrder=getIntent().getBooleanExtra(Config.IParam.selectAddress,false);
        adapter=new LoadMoreAdapter<AddressObj>(mContext,R.layout.item_address_list,pageSize) {
            @Override
            public void bindData(LoadMoreViewHolder holder, int position, AddressObj bean) {
                holder.setText(R.id.tv_address_name,bean.getRecipient())
                        .setText(R.id.tv_address_content,bean.getShipping_address()+" "+bean.getShipping_address_details())
                        .setText(R.id.tv_address_phone,bean.getPhone());
                CheckBox cb_address= (CheckBox) holder.getView(R.id.cb_address);
                if(bean.getIs_default()==1){
                    cb_address.setChecked(true);
                }else{
                    cb_address.setChecked(false);
                }
                cb_address.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if(!cb_address.isChecked()){
                            setDefaultAddress(bean.getId()+"");
                        }
                        return true;
                    }

                    private void setDefaultAddress(String addressId) {
                        showLoading();
                        Map<String,String>map=new HashMap<String,String>();
                        map.put("address_id",addressId);
                        map.put("userid",getUserId());
                        map.put("sign",GetSign.getSign(map));
                        ApiRequest.setDefaultAddress(map, new MyCallBack<BaseObj>(mContext) {
                            @Override
                            public void onSuccess(BaseObj obj) {
                                showMsg(obj.getMsg());
                                SPUtils.setPrefString(mContext, Config.default_address,addressId);
                                getData();
                            }
                        });

                    }
                });

                TextView tv_address_edit=  holder.getTextView(R.id.tv_address_edit);
                TextView tv_address_delete=  holder.getTextView(R.id.tv_address_delete);
                tv_address_edit.setOnClickListener(new MyOnClickListener() {//编辑
                    @Override
                    protected void onNoDoubleClick(View view) {
                        Intent intent=new Intent();
                        intent.putExtra(Constant.IParam.editAddress,true);
                        intent.putExtra(Constant.IParam.Address,bean);
                        STActivityForResult(intent,AddAddressActivity.class,101);
                    }
                });
                tv_address_delete.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
                        mDialog.setMessage("是否确认删除当前地址?");
                        mDialog.setNegativeButton(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        mDialog.setPositiveButton(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                deleteAddress(bean);
                            }
                        });
                        mDialog.create().show();
                    }
                });
                if(isSelectForOrder){
                    holder.itemView.setOnClickListener(new MyOnClickListener() {
                        @Override
                        protected void onNoDoubleClick(View view) {
                            Intent intent=new Intent();
                            intent.putExtra(Config.IParam.address,bean);
                            setResult(RESULT_OK,intent);
                            finish();
                        }
                    });
                }
            }
        };
        adapter.setOnLoadMoreListener(this);

        rv_address_list.setLayoutManager(new LinearLayoutManager(mContext));
        rv_address_list.addItemDecoration(getItemDivider(PhoneUtils.dip2px(mContext,5)));
        rv_address_list.setAdapter(adapter);

    }

    private void deleteAddress(AddressObj bean) {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("address_id",bean.getId()+"");
        map.put("sign",GetSign.getSign(map));
        ApiRequest.deleteAddress(map, new MyCallBack<BaseObj>(mContext,true) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                getData();
            }
        });
        
    }

    @Override
    protected void initData() {
        showProgress();
        getData();
    }

    private void getData() {
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getAddress(map, new MyCallBack<List<AddressObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(List<AddressObj> list) {
                adapter.setList(list,true);
                if(list.size()==1){
                    SPUtils.setPrefString(mContext, Config.default_address,list.get(0).getId()+"");
                }
            }
        });

    }

    @OnClick({R.id.tv_address_add})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_address_add:
                STActivityForResult(AddAddressActivity.class,100);
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 100:
                case 101:
                    getData();
                break;
            }
        }
    }

    @Override
    public void loadMore() {
    }
}
