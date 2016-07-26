package com.example.lenovo.v2ex.Base;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lenovo.v2ex.net.GetTopics;
import com.example.lenovo.v2ex.Database.DataBase;
import com.example.lenovo.v2ex.Global.V2EX;
import com.example.lenovo.v2ex.ItemClasses.TopicItem;
import com.example.lenovo.v2ex.R;
import com.example.lenovo.v2ex.Adapter.TopicItemAdapter;
import com.example.lenovo.v2ex.Interface.Update;
import com.example.lenovo.v2ex.net.InternetUtils;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/7/25.
 */
public class BaseFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,Update{
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    int type;
    int urlId;
    GetTopics abstractGetTopics;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_layout,container,false);
        swipeRefreshLayout =(SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getColor(android.R.color.holo_blue_bright));
        swipeRefreshLayout.setDistanceToTriggerSync(200);
        swipeRefreshLayout.setProgressBackgroundColor(android.R.color.white);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        recyclerView = (RecyclerView)v.findViewById(R.id.topic_list);

        if(V2EX.isInitialized(type)){
            ArrayList<TopicItem> list = DataBase.get(type);
            TopicItemAdapter adapter = new TopicItemAdapter(list);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }else{
            V2EX.Initialize(type);
            String url = getString(urlId);
            abstractGetTopics.get(url,this,type);
        }
        return v;
    }

    @Override
    public void onRefresh(){
        if(InternetUtils.accessible()){
            String url = getString(urlId);
            abstractGetTopics.get(url,this,type);
        }else {
            Toast.makeText(getActivity(), "网络连接失败...", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void update(Object obj){
        Log.d("holo","in the update method");
        ArrayList<TopicItem> list = (ArrayList<TopicItem>) obj;
        TopicItemAdapter adapter = new TopicItemAdapter(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(V2EX.getInstance().getApplicationContext()));
        swipeRefreshLayout.setRefreshing(false);
    }

    public void init(int type, int urlId,GetTopics abstractGetTopics) {
        this.type = type;
        this.urlId = urlId;
        this.abstractGetTopics = abstractGetTopics;
    }
}
