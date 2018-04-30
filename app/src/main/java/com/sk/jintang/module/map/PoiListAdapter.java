package com.sk.jintang.module.map;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.sk.jintang.R;

import java.util.List;

/**
 * Author：JunWu
 * Date：2017/3/1
 * Version:v1.0.0
 * class describe:
 */

public class PoiListAdapter extends BaseAdapter {
    private Context context;
    private List<PoiInfo> pois;
    private LinearLayout linearLayout;

    PoiListAdapter(Context context, List<PoiInfo> pois) {
        this.context = context;
        this.pois = pois;
    }

    @Override
    public int getCount() {
        return pois.size();
    }

    @Override
    public Object getItem(int position) {
        return pois.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PoiListAdapter.ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.pois_list_item, null);
            holder = new PoiListAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (PoiListAdapter.ViewHolder) convertView.getTag();
        }
        PoiInfo poiInfo = pois.get(position);
        if (position == 0) {
            holder.iv_gps_icon.setVisibility(View.VISIBLE);
            holder.tv_pois_name.setTextColor(Color.parseColor("#D13600"));
            holder.tv_pois_address.setTextColor(Color.parseColor("#D13600"));
            holder.tv_pois_name.setText("[当前位置]"+poiInfo.name);
            holder.tv_pois_address.setText(poiInfo.address);
        }else{
            holder.iv_gps_icon.setVisibility(View.INVISIBLE);
            holder.tv_pois_name.setTextColor(Color.parseColor("#4A4A4A"));
            holder.tv_pois_address.setTextColor(Color.parseColor("#ffa9a9a9"));
            holder.tv_pois_name.setText(poiInfo.name);
            holder.tv_pois_address.setText(poiInfo.address);
        }

        return convertView;
    }

    class ViewHolder {
        ImageView iv_gps_icon;
        TextView tv_pois_name;
        TextView tv_pois_address;

        ViewHolder(View view) {
            tv_pois_name = (TextView) view.findViewById(R.id.tv_pois_name);
            tv_pois_address = (TextView) view.findViewById(R.id.tv_pois_address);
            iv_gps_icon = (ImageView) view.findViewById(R.id.iv_gps_icon);
        }
    }
}
