package com.example.lenovo.v2ex.TopicsFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.v2ex.Base.BaseFragment;
import com.example.lenovo.v2ex.Global.V2EX;
import com.example.lenovo.v2ex.R;
import com.example.lenovo.v2ex.net.GetFromHtml;
import com.example.lenovo.v2ex.net.GetTopics;

/**
 * Created by lenovo on 2016/7/25.
 */
public class TechTopics extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,Bundle savedInstanceState){
        super.init(V2EX.TECH_TOPICS, R.string.tech_topics,new GetTopics(new GetFromHtml()));
        return super.onCreateView(inflater, parent, savedInstanceState);
    }
}
