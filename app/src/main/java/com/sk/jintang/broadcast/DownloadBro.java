package com.sk.jintang.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.sk.jintang.module.home.Constant;

import java.io.File;

/**
 * Created by Administrator on 2017/12/23.
 */

public class DownloadBro extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        String action = intent.getAction(); //动作
        Log.i("===","action==="+action);
        if (action.equals("download")) {
            Log.i("===","action===download");
            String path = intent.getStringExtra(Constant.IParam.path);
            installApp(context,new File(path));
        }
    }
    public static void installApp(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
