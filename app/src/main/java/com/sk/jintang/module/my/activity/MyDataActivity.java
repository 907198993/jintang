package com.sk.jintang.module.my.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.github.androidtools.DateUtils;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.SPUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.rx.IOCallBack;
import com.sk.jintang.Config;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.request.UploadImgItem;
import com.sk.jintang.module.orderclass.activity.AddressListActivity;
import com.sk.jintang.tools.BitmapUtils;
import com.sk.jintang.tools.ImageUtils;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;
import top.zibin.luban.Luban;

/**
 * Created by administartor on 2017/9/7.
 */

public class MyDataActivity extends BaseActivity {

    @BindView(R.id.civ_info_img)
    CircleImageView civ_info_img;
    @BindView(R.id.et_info_name)
    EditText et_info_name;


    @BindView(R.id.tv_info_sex)
    TextView tv_info_sex;
    @BindView(R.id.tv_info_birthday)
    TextView tv_info_birthday;

    private BottomSheetDialog sexDialog,selectPhotoDialog;
    private String sex="";
    private String birthday="";
    private String imgUrl="";
    @Override
    protected int getContentView() {
        setAppTitle("我的资料");
        return R.layout.act_my_data;
    }

    @Override
    protected void initView() {
        String userName = SPUtils.getPrefString(mContext, Config.nick_name, null);
        String birthday = SPUtils.getPrefString(mContext, Config.birthday, null);
        String avatar = SPUtils.getPrefString(mContext, Config.avatar, null);
        String sex = SPUtils.getPrefString(mContext, Config.sex, null);
        et_info_name.setText(userName);
        tv_info_birthday.setText(birthday);
        tv_info_sex.setText(sex);
        if (avatar != null) {
            Glide.with(mContext).load(avatar).error(R.drawable.people).into(civ_info_img);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }

    @OnClick({R.id.ll_info_img, R.id.ll_info_date, R.id.ll_info_sex, R.id.ll_info_updatePwd, R.id.ll_info_address, R.id.tv_info_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_info_img:
                PhoneUtils.hiddenKeyBoard(mContext);
                showSelectPhotoDialog();
                break;
            case R.id.ll_info_date:
                PhoneUtils.hiddenKeyBoard(mContext);
                TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        birthday= DateUtils.dateToString(date,"yyyy/MM/dd");
                        tv_info_birthday.setText(birthday);
                    }
                }).setRange(1950, Calendar.getInstance().get(Calendar.YEAR)).setType(new boolean[]{true, true, true, false, false, false}).build();
                pvTime.show();
                break;
            case R.id.ll_info_sex:
                selectSex();
                break;
            case R.id.ll_info_updatePwd:
                STActivity(UpdatePWDActivity.class);
                break;
            case R.id.ll_info_address:
                STActivity(AddressListActivity.class);
                break;
            case R.id.tv_info_commit:
                updateInfo();
                break;
        }
    }

    private void showSelectPhotoDialog() {
        if (selectPhotoDialog == null) {
            View sexView= LayoutInflater.from(mContext).inflate(R.layout.popu_select_photo,null);
            sexView.findViewById(R.id.tv_select_photo).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    selectPhotoDialog.dismiss();
                    selectPhoto();
                }
            });
            sexView.findViewById(R.id.tv_take_photo).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    selectPhotoDialog.dismiss();
                    takePhoto();
                }
            });
            sexView.findViewById(R.id.tv_photo_cancle).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    selectPhotoDialog.dismiss();
                }
            });
            selectPhotoDialog = new BottomSheetDialog(mContext);
            selectPhotoDialog.setCanceledOnTouchOutside(true);
            selectPhotoDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            selectPhotoDialog.setContentView(sexView);
        }
        selectPhotoDialog.show();
    }
    //选择相册
    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 3000);
    }
    private String path = Environment.getExternalStorageDirectory() +
            File.separator + Environment.DIRECTORY_DCIM + File.separator;
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return "IMG_" + dateFormat.format(date);
    }
    Uri photoUri;
    private String imgSaveName="";
    //拍照
    private void takePhoto() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.CAMERA}, 1);
        } else {
            String state = Environment.getExternalStorageState();
            if (state.equals(Environment.MEDIA_MOUNTED)) {
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdir();
                }
                String fileName = getPhotoFileName() + ".jpg";
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imgSaveName = path + fileName;
                photoUri = Uri.fromFile(new File(imgSaveName));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, 2000);
            }
        }
    }
    private void uploadImg() {
        showLoading();
        Log.i("========","========"+imgSaveName);
        RXStart(new IOCallBack<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String newPath= ImageUtils.filePath;
                ImageUtils.makeFolder(newPath);
                FileInputStream fis = null;
                try {
                    List<File> files = Luban.with(mContext).load(imgSaveName).get();
                    String imgStr = BitmapUtils.bitmapToString2(files.get(0));
                    subscriber.onNext(imgStr);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
                /*String newPath= ImageUtils.filePath;
                String name=ImageUtils.fileName;
                String smallBitmapPath = ImageUtils.getSmallBitmap(imgSaveName, newPath, name);
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(smallBitmapPath);
                    Bitmap bitmap  = BitmapFactory.decodeStream(fis);
                    String imgStr = BitmapUtils.bitmapToString(bitmap);
                    subscriber.onNext(imgStr);
                    subscriber.onCompleted();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }*/
            }
            @Override
            public void onMyNext(String baseImg) {
                UploadImgItem item=new UploadImgItem();
                item.setFile(baseImg);
                String rnd = getRnd();
                Map<String,String>map=new HashMap<String,String>();
                map.put("rnd",rnd);
                map.put("sign",GetSign.getSign(map));
                ApiRequest.uploadImg(map,item, new MyCallBack<BaseObj>(mContext,true) {
                    @Override
                    public void onSuccess(BaseObj obj) {
                        imgUrl = obj.getImg();
                        Glide.with(mContext).load(imgSaveName).error(R.drawable.people).into(civ_info_img);
                        updateInfoForImg();
                    }
                });
            }
            @Override
            public void onMyError(Throwable e) {
                super.onMyError(e);
                dismissLoading();
                showToastS("图片处理失败");
            }
        });
    }

    private void updateInfoForImg() {
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("avatar",imgUrl);
        map.put("sign",GetSign.getSign(map));
        ApiRequest.updateUserImg(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                if(!TextUtils.isEmpty(imgUrl)){
                    SPUtils.setPrefString(mContext,Config.avatar,imgUrl);
                }
            }
        });

    }

    private void selectSex() {
        if (sexDialog == null) {
            View sexView= LayoutInflater.from(mContext).inflate(R.layout.popu_sex,null);
            sexView.findViewById(R.id.tv_sex_nan).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    sexDialog.dismiss();
                    sex ="男";
                    tv_info_sex.setText(sex);
                }
            });
            sexView.findViewById(R.id.tv_sex_nv).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    sexDialog.dismiss();
                    sex ="女";
                    tv_info_sex.setText(sex);
                }
            });
            sexView.findViewById(R.id.tv_sex_cancle).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    sexDialog.dismiss();
                }
            });
            sexDialog = new BottomSheetDialog(mContext);
            sexDialog.setCanceledOnTouchOutside(true);
            sexDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            sexDialog.setContentView(sexView);
        }
        sexDialog.show();
    }

    private void updateInfo() {
        showLoading();
        String name = getSStr(et_info_name);
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("avatar",imgUrl);
        map.put("nickname",name);
        map.put("birthday",birthday);
        map.put("sex",sex);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.updateInfo(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                if(!TextUtils.isEmpty(imgUrl)){
                    SPUtils.setPrefString(mContext,Config.avatar,imgUrl);
                }
                if(!TextUtils.isEmpty(sex)){
                    SPUtils.setPrefString(mContext,Config.sex,sex);
                }
                if(!TextUtils.isEmpty(birthday)){
                    SPUtils.setPrefString(mContext,Config.birthday,birthday);
                }
                if(!TextUtils.isEmpty(name)){
                    SPUtils.setPrefString(mContext,Config.nick_name,name);
                }
                finish();
            }
        });

        /*addSubscription(ApiRequest.updateInfo(map).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                showMsg(obj.getMsg());
                if(!TextUtils.isEmpty(imgUrl)){
                    SPUtils.setPrefString(mContext,Config.avatar,imgUrl);
                }
                if(!TextUtils.isEmpty(sex)){
                    SPUtils.setPrefString(mContext,Config.sex,sex);
                }
                if(!TextUtils.isEmpty(birthday)){
                    SPUtils.setPrefString(mContext,Config.birthday,birthday);
                }
                if(!TextUtils.isEmpty(name)){
                    SPUtils.setPrefString(mContext,Config.nick_name,name);
                }
            }
        }));*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK){
            return;
        }
        switch (requestCode){
            case 2000:
                uploadImg();
                break;
            case 3000:
                Uri uri = data.getData();
                Cursor cursor = getContentResolver().query(uri, null, null, null,null);
                if (cursor != null && cursor.moveToFirst()) {
                    imgSaveName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                    uploadImg();
                }
                break;
        }
    }

}
