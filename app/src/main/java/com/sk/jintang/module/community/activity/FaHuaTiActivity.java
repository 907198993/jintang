package com.sk.jintang.module.community.activity;

import android.Manifest;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.rx.IOCallBack;
import com.github.baseclass.view.MyDialog;
import com.sendtion.xrichtext.RichTextEditor;
import com.sk.jintang.GetSign;
import com.sk.jintang.R;
import com.sk.jintang.base.BaseActivity;
import com.sk.jintang.base.BaseObj;
import com.sk.jintang.base.MyCallBack;
import com.sk.jintang.module.community.network.request.FaBuHuaTiItem;
import com.sk.jintang.module.community.network.response.MoreHuaTiObj;
import com.sk.jintang.module.my.network.ApiRequest;
import com.sk.jintang.module.my.network.request.UploadImgItem;
import com.sk.jintang.tools.BitmapUtils;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import top.zibin.luban.Luban;

/**
 * Created by administartor on 2017/9/4.
 */

public class FaHuaTiActivity extends BaseActivity {
    @BindView(R.id.et_new_content)
    RichTextEditor et_new_content;
    @BindView(R.id.et_fa_bu_title)
    EditText et_fa_bu_title;
    @BindView(R.id.tv_fa_bu_hua_ti)
    TextView tv_fa_bu_hua_ti;
    @BindView(R.id.tv_fa_bu_hua_ti_img)
    TextView tv_fa_bu_hua_ti_img;
    @BindView(R.id.iv_fa_bu_img)
    ImageView iv_fa_bu_img;
    private BottomSheetDialog selectPhotoDialog;

    private int selectImgType;//1为上传封面，2为上传内容图片

    private String fengMianImg="";
    @Override
    protected int getContentView() {
        setAppTitle("发布话题");
        setAppRightTitle("发布");
        return R.layout.act_fa_hua_ti;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.ll_fa_bu_add_img,R.id.app_right_tv,R.id.tv_fa_bu_hua_ti,R.id.tv_fa_bu_hua_ti_img,R.id.iv_fa_bu_img})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_fa_bu_hua_ti:
                getZhuTi();
                break;
            case R.id.tv_fa_bu_hua_ti_img:
            case R.id.iv_fa_bu_img:
                PhoneUtils.hiddenKeyBoard(mContext);
                selectImgType=1;
                showSelectPhotoDialog();
                break;
            case R.id.app_right_tv:
                if(TextUtils.isEmpty(getSStr(tv_fa_bu_hua_ti))){
                    showMsg("请选择所属话题");
                    return;
                }else if(TextUtils.isEmpty(getSStr(et_fa_bu_title))){
                    showMsg("请输入标题");
                    return;
                }else if(TextUtils.isEmpty(fengMianImg)){
                    showMsg("请上传封面");
                    return;
                }else if(TextUtils.isEmpty(getEditData())){
                    showMsg("请输入内容");
                    return;
                }
                Log.i("======","======"+getEditData());
                MyDialog.Builder mDialog=new MyDialog.Builder(mContext);
                mDialog.setMessage("是否确认发布?");
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
                        faBuHuaTi();
                    }
                });
                mDialog.create().show();
                break;
            case R.id.ll_fa_bu_add_img:
                PhoneUtils.hiddenKeyBoard(mContext);
                selectImgType=2;
                showSelectPhotoDialog();
            break;
        }
    }

    private void getZhuTi() {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("rnd",getRnd());
        map.put("sign", GetSign.getSign(map));
        com.sk.jintang.module.community.network.ApiRequest.getMoreHuaTi(map, new MyCallBack<List<MoreHuaTiObj>>(mContext,pcfl,pl_load) {
            @Override
            public void onSuccess(List<MoreHuaTiObj> list) {
                if(isEmpty(list)){
                    showMsg("暂无话题");
                    return;
                }else{
                    showHuaTiList(list);
                }
            }
        });

    }

    private void showHuaTiList(List<MoreHuaTiObj> list) {
        List<String>options1Items=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            options1Items.add(list.get(i).getTopic_name());
        }
        //条件选择器
        OptionsPickerView pvOptions = new  OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                MoreHuaTiObj moreHuaTiObj = list.get(options1);
                tv_fa_bu_hua_ti.setText(moreHuaTiObj.getTopic_name());
                tv_fa_bu_hua_ti.setTag(moreHuaTiObj.getTopic_id());
                Log.i("===",moreHuaTiObj.getTopic_id()+"=="+moreHuaTiObj.getTopic_name()+"==="+options1);
            }
        }).build();
        pvOptions.setPicker(options1Items, null, null);
        pvOptions.show();
    }

    private void faBuHuaTi() {
        showLoading();
        FaBuHuaTiItem item=new FaBuHuaTiItem();
        item.setContent(getEditData());
        item.setTitle(getSStr(et_fa_bu_title));
        item.setImg_url(fengMianImg);
        item.setUser_id(getUserId());
        Map<String,String>map=new HashMap<String,String>();
        map.put("topic_id",tv_fa_bu_hua_ti.getTag()+"");
        map.put("sign",GetSign.getSign(map));
        com.sk.jintang.module.community.network.ApiRequest.faBuHuaTi(map,item, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                setResult(RESULT_OK);
                finish();
            }
        });

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
    Uri cropUri;
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
                    File file = files.get(0);
                    imgSaveName=file.getAbsolutePath();
                    String imgStr = BitmapUtils.bitmapToString2(file);
                    subscriber.onNext(imgStr);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }


               /* int width = PhoneUtils.getScreenWidth(mContext);
                int height = PhoneUtils.getScreenHeight(mContext);
                Bitmap bitmap = ImageUtils.getSmallBitmap(imgSaveName, width, height);//压缩图片
                //bitmap = BitmapFactory.decodeFile(imagePath);
                imgSaveName = SDCardUtil.saveToSdCard(bitmap);*/
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
                /*String imgStr = BitmapUtils.bitmapToString(bitmap);
                subscriber.onNext(imgStr);
                subscriber.onCompleted();*/
            }
            @Override
            public void onMyNext(String baseImg) {
                UploadImgItem item=new UploadImgItem();
                item.setFile(baseImg);
                String rnd = getRnd();
                Map<String,String> map=new HashMap<String,String>();
                map.put("rnd",rnd);
                map.put("sign", GetSign.getSign(map));
                ApiRequest.uploadImg(map,item, new MyCallBack<BaseObj>(mContext) {


                    @Override
                    public void onSuccess(BaseObj obj) {
                       /* imgUrl = obj.getImg();
                        Glide.with(mContext).load(imgSaveName).error(R.drawable.people).into(civ_info_img);
                        updateInfoForImg();*/
                       if(selectImgType==1){
                           fengMianImg = obj.getImg();
                           Glide.with(mContext).load(obj.getImg()).error(R.color.c_press).into(iv_fa_bu_img);
                           iv_fa_bu_img.setVisibility(View.VISIBLE);
                           tv_fa_bu_hua_ti_img.setVisibility(View.GONE);
                       }else{
                           addImg(obj.getImg());
                       }
                    }
                });
            }
            @Override
            public void onMyError(Throwable e) {
                super.onMyError(e);
                dismissLoading();
                showToastS("图片插入失败");
            }
        });
    }

    private void addImg(String imgSaveName) {
        et_new_content.insertImage(imgSaveName, et_new_content.getMeasuredWidth());
        et_new_content.addEditTextAtIndex(et_new_content.getLastIndex(), " ");
    }
    private String getEditData() {
        List<RichTextEditor.EditData> editList = et_new_content.buildEditData();
        StringBuffer content = new StringBuffer();
        for (RichTextEditor.EditData itemData : editList) {
            if (itemData.inputStr != null) {
                content.append(itemData.inputStr);
                //Log.d("RichEditor", "commit inputStr=" + itemData.inputStr);
            } else if (itemData.imagePath != null) {
                content.append("<img src=\"").append(itemData.imagePath).append("\"/>");
                //Log.d("RichEditor", "commit imgePath=" + itemData.imagePath);
                //imageList.add(itemData.imagePath);
            }
        }
        return content.toString();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK){
            return;
        }
        switch (requestCode){
            case Crop.REQUEST_CROP :
                //imgSaveName
                imgSaveName=cropUri.getPath();
                uploadImg();
                break;
            case 2000:
                if(selectImgType==1){//封面裁剪
                    startCrop();
                }else{
                    uploadImg();
                }
                break;
            case 3000:
                Uri uri = data.getData();
                Cursor cursor = getContentResolver().query(uri, null, null, null,null);
                if (cursor != null && cursor.moveToFirst()) {
                    imgSaveName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                    if(selectImgType==1){//封面裁剪
                        startCrop();
                    }else{
                        uploadImg();
                    }
                }
                break;
        }
    }
    private void startCrop() {
        String cropPath = path + getPhotoFileName() + ".jpg";
        cropUri = Uri.fromFile(new File(cropPath));
        photoUri = Uri.fromFile(new File(imgSaveName));
        Crop.of(photoUri, cropUri).withAspect(12,7).start(mContext);
    }


}
