package com.sk.jintang.tools;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;
import com.sk.jintang.R;

/**
 * Created by Administrator on 2017/3/9.
 */

public class GlideLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).
                load(path).
                error(R.color.c_press).
                into(imageView);
    }
}
