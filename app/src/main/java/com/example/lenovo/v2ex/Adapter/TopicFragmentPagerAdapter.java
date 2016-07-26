package com.example.lenovo.v2ex.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/7/18.
 */
public class TopicFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;
    private String[] tabs = new String[]{"最热","最新","技术"} ;

    public TopicFragmentPagerAdapter(FragmentManager fm,ArrayList<Fragment> list){
        super(fm);
        fragments = list;
    }

    @Override
    public Fragment getItem(int location){
        return fragments.get(location);
    }

    @Override
    public int getCount(){
        return fragments.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
