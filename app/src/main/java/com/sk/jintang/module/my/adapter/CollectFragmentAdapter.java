package com.sk.jintang.module.my.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by administartor on 2017/8/2.
 */

public class CollectFragmentAdapter extends FragmentStatePagerAdapter {
    String[]title={"店铺\n(-)","宝贝\n(-)"};
    List<Fragment> list;

    public void setList(List<Fragment> list) {
        this.list = list;
    }

    public CollectFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
//        return super.getPageTitle(position);
    }
}
