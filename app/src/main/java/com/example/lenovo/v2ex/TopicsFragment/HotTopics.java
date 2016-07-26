package com.example.lenovo.v2ex.TopicsFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.v2ex.Base.BaseFragment;
import com.example.lenovo.v2ex.Global.V2EX;
import com.example.lenovo.v2ex.R;
import com.example.lenovo.v2ex.net.GetFromAPI;
import com.example.lenovo.v2ex.net.GetTopics;

/**
 * Created by lenovo on 2016/7/18.
 */
public class HotTopics extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.init(V2EX.HOT_TOPICS, R.string.hot_topics, new GetTopics(new GetFromAPI()));
        return super.onCreateView(inflater,container,savedInstanceState);
    }
}

