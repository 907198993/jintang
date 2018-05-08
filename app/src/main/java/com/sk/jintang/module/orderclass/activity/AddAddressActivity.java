package com.sk.jintang.module.orderclass.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.github.androidtools.PhoneUtils;
import com.github.baseclass.rx.IOCallBack;
import com.github.baseclass.view.pickerview.OptionsPopupWindow;
import com.github.customview.MyEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.map.MapActivity;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.response.AddressObj;
import com.sk.jintang.module.orderclass.Constant;
import com.sk.jintang.module.orderclass.bean.CityBean;
import com.sk.jintang.tools.StreamUtils;
import com.suke.widget.SwitchButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by administartor on 2017/9/2.
 */

public class AddAddressActivity extends BaseActivity {
    @BindView(R.id.et_editaddress_name)
    MyEditText et_editaddress_name;
    @BindView(R.id.et_editaddress_phone)
    MyEditText et_editaddress_phone;
    @BindView(R.id.et_editaddress_door)
    MyEditText et_editaddress_door;  //门牌号
    @BindView(R.id.tv_editaddress_area)
    TextView tv_editaddress_area;
    @BindView(R.id.et_editaddress_detail)
    TextView et_editaddress_detail;
    @BindView(R.id.sb_address_default)
    SwitchButton sb_address_default;

    private boolean isEdit;
    private AddressObj addressObj;
    String longitude;//经度
    String latitude;//纬度

    private OptionsPopupWindow pwOptions;
    private ArrayList<String> options1Items;
    private ArrayList<ArrayList<String>> options2Items;
    private ArrayList<ArrayList<ArrayList<String>>> options3Items;
    private boolean isAddSuccess;
    private String selectProvinceName;
    private String selectCityName;
    private String selectZoneName;

    @Override
    protected int getContentView() {
        return R.layout.act_add_address;
    }

    @Override
    protected void initView() {
        isEdit = getIntent().getBooleanExtra(Constant.IParam.editAddress,false);
        if(isEdit){
            setAppTitle("编辑地址");
            addressObj = (AddressObj) getIntent().getSerializableExtra(Constant.IParam.Address);
            setAddress(addressObj);

            selectProvinceName=addressObj.getProvince();
            selectCityName=addressObj.getCity();
            selectZoneName=addressObj.getZone();

        }else{
            setAppTitle("添加新地址");
        }

    }

    private void setAddress(AddressObj addressObj) {
        et_editaddress_name.setText(addressObj.getRecipient());
        et_editaddress_phone.setText(addressObj.getPhone());
        tv_editaddress_area.setText(addressObj.getShipping_address());
        et_editaddress_detail.setText(addressObj.getShipping_address_details());
        et_editaddress_door.setText(addressObj.getLDH());
        sb_address_default.setChecked(addressObj.getIs_default()==1?true:false);
        String [] strings = addressObj.getCoord().split(",");
        longitude = strings[0];
        latitude = strings[1];
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_editaddress_area,R.id.tv_editaddress_commit,R.id.et_editaddress_detail})
    public void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_editaddress_area:
                selectArea();
            break;
            case R.id.et_editaddress_detail:
                Intent intent=new Intent();
                STActivityForResult(intent,MapActivity.class,103);
            break;
            case R.id.tv_editaddress_commit:
                String name = getSStr(et_editaddress_name);
                String phone = getSStr(et_editaddress_phone);
                String area = getSStr(tv_editaddress_area);
                String detail = getSStr(et_editaddress_detail);
                String door = getSStr(et_editaddress_door);

                if(TextUtils.isEmpty(name)){
                    showMsg("收货人不能为空");
                    return;
                }else if(TextUtils.isEmpty(phone)){
                    showMsg("联系电话不能为空");
                    return;
                }else if(!GetSign.isMobile(phone)){
                    showMsg("联系电话格式不正确");
                    return;
                }else if(TextUtils.isEmpty(area)){
                    showMsg("所在地区不能为空");
                    return;
                }else if(TextUtils.isEmpty(detail)){
                    showMsg("详细地址不能为空");
                    return;
                }else if(TextUtils.isEmpty(door)){
                    showMsg("门牌号");
                    return;
                }
                if(isEdit){
                    editAddress(name,phone,area,detail,door);
                }else{
                    addAddress(name,phone,area,detail,door);
                }
                break;
        }
    }

    private void addAddress(String name, String phone, String area, String detail,String door) {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("recipient",name);
        map.put("phone",phone);
        map.put("shipping_address",area);
        map.put("province",selectProvinceName);
        map.put("city",selectCityName);
        map.put("zone",selectZoneName);
        map.put("shipping_address_detail",detail);
        map.put("LDH",door);
        map.put("coord",longitude+","+latitude);
        map.put("is_default",sb_address_default.isChecked()?"1":"0");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.addAddress(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                isAddSuccess=true;
                et_editaddress_name.setText(null);
                et_editaddress_phone.setText(null);
                tv_editaddress_area.setText(null);
                et_editaddress_detail.setText(null);
                sb_address_default.setChecked(false);
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private void editAddress(String name, String phone, String area, String detail,String door) {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("address_id",addressObj.getId()+"");
        map.put("recipient",name);
        map.put("phone",phone);
        map.put("shipping_address",area);
        map.put("province",selectProvinceName);
        map.put("city",selectCityName);
        map.put("zone",selectZoneName);
        map.put("shipping_address_detail",detail);
        map.put("LDH",door);
        map.put("coord",longitude+","+latitude);
        map.put("is_default",sb_address_default.isChecked()?"1":"0");
        map.put("sign", GetSign.getSign(map));
        ApiRequest.editAddress(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                setResult(RESULT_OK);
                finish();
            }
        });
    }


    private void selectArea() {
        PhoneUtils.hiddenKeyBoard(mContext);
        showLoading();
        RXStart(new IOCallBack<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                initAddressDialog();
                subscriber.onNext(null);
                subscriber.onCompleted();
            }
            @Override
            public void onMyNext(String s) {
                dismissLoading();
                pwOptions = new OptionsPopupWindow(mContext, "选择地区");
                // 三级联动效果
                pwOptions.setPicker(options1Items, options2Items, options3Items, true);
                // 设置默认选中的三级项目
                pwOptions.setSelectOptions(0, 0, 0);
                // 监听确定选择按钮
                pwOptions.setOnoptionsSelectListener((options1, option2, options3) -> {
                    // 返回的分别是三个级别的选中位置
                    selectProvinceName = options1Items.get(options1);
                    selectCityName = options2Items.get(options1).get(option2);
                    selectZoneName = options3Items.get(options1).get(option2).get(options3);
                    String area = selectProvinceName + selectCityName + selectZoneName;
                    tv_editaddress_area.setText(area);
                });
                pwOptions.showAtLocation(tv_editaddress_area, Gravity.BOTTOM,0,PhoneUtils.getNavigationBarHeight(mContext));
            }
        });
    }
    private void initAddressDialog() {
        options1Items = new ArrayList<String>();
        options2Items = new ArrayList<ArrayList<String>>();
        options3Items = new ArrayList<ArrayList<ArrayList<String>>>();
        String areaJson = StreamUtils.get(this, R.raw.area);
        province(areaJson);
        // 地址选择器
    }
    private void province(String strJson) {
        List<CityBean> cityBean = new Gson().fromJson(strJson,
                new TypeToken<List<CityBean>>() {
                }.getType());
        ArrayList<String> item2;
        ArrayList<ArrayList<String>> item3;
        for (int i = 0; i < cityBean.size(); i++) {
            CityBean city=cityBean.get(i);
            options1Items.add(city.getTitle());
            item2=new ArrayList<>();
            item3=new ArrayList<ArrayList<String>>();
            for (int j = 0; j < city.getClass_list().size(); j++) {
                CityBean citySecond=city.getClass_list().get(j);
                item2.add(citySecond.getTitle());
                ArrayList<String> lastItem = new ArrayList<String>();
                for (int k = 0; k < citySecond.getClass_list().size(); k++) {
                    CityBean cityThird=citySecond.getClass_list().get(k);
                    lastItem.add(cityThird.getTitle());
                }
                item3.add(lastItem);
            }
            options2Items.add(item2);
            options3Items.add(item3);
        }
    }
    @Override
    public void onBackPressed() {
        if(pwOptions!=null&&pwOptions.isShowing()){
            pwOptions.dismiss();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 103:
                    String result_value = data.getStringExtra("address");
                    et_editaddress_detail.setText(result_value);
                    longitude = data.getStringExtra("longitude");
                    latitude =  data.getStringExtra("latitude");
                    break;
            }
        }
    }


}
