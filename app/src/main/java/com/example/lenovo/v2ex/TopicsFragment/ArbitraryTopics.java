package com.example.lenovo.v2ex.TopicsFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.style.UpdateAppearance;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.v2ex.Base.BaseFragment;
import com.example.lenovo.v2ex.Global.V2EX;
import com.example.lenovo.v2ex.Interface.Update;
import com.example.lenovo.v2ex.R;
import com.example.lenovo.v2ex.net.GetFromAPI;
import com.example.lenovo.v2ex.net.GetTopics;

import org.jsoup.Connection;

/**
 * Created by lenovo on 2016/7/29.
 */
public class ArbitraryTopics extends BaseFragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    public ArbitraryTopics(){
    }
    @SuppressLint("ValidFragment")
    public ArbitraryTopics(int  type,String url,GetTopics abstractGetTopics){
        super();
        Log.d("holo", "init");
        super.init(type,url,abstractGetTopics);
    }
}
