package com.example.lenovo.v2ex;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.v2ex.Adapter.NodeAdapter;
import com.example.lenovo.v2ex.Database.DataBase;
import com.example.lenovo.v2ex.Global.V2EX;
import com.example.lenovo.v2ex.Interface.Update;
import com.example.lenovo.v2ex.ItemClasses.NodeIntroduce;
import com.example.lenovo.v2ex.net.GetAllNodes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by lenovo on 2016/7/24.
 */
public class AllNodesActivity extends AppCompatActivity implements Update, SwipeRefreshLayout.OnRefreshListener{
    ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_nodes);
        getSupportActionBar().hide();
        //progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.all_nodes_swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(this.getResources().getColor(android.R.color.holo_blue_bright));
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setDistanceToTriggerSync(200);
        swipeRefreshLayout.setBackgroundColor(getResources().getColor(android.R.color.white));
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        if(V2EX.isInitialized(V2EX.ALL_NODES)){
            ArrayList<NodeIntroduce> nodeList = DataBase.get();
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.node_list);
            NodeAdapter adapter = new NodeAdapter(nodeList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        }else{
            GetAllNodes.getAllNodes(this);
        }
    }


    @Override
    public void onRefresh(){
        GetAllNodes.getAllNodes(this);
    }

    @Override
    public void update(Object object){
      //  progressBar.setVisibility(View.VISIBLE);
        ArrayList<NodeIntroduce> list = (ArrayList<NodeIntroduce>)object;

        Log.d("holo", list.get(0).getName() + "");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.node_list);
        NodeAdapter adapter = new NodeAdapter(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        DataBase.update(list);
        if(!V2EX.isInitialized(V2EX.ALL_NODES)){
            V2EX.initialize(V2EX.ALL_NODES);
        }
        swipeRefreshLayout.setRefreshing(false);
//        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
//        for(int i = 0;i<list.size();i+=2){
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            LinearLayout linerLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.node_introduce,null);
//            TextView titleL = (TextView) linerLayout.findViewById(R.id.title_l);;
//            TextView titleR = (TextView) linerLayout.findViewById(R.id.title_r);
//            TextView titleAlternativeR = (TextView) linerLayout.findViewById(R.id.title_alternative_r);
//            TextView titleAlternativeL = (TextView) linerLayout.findViewById(R.id.title_alternative_l);
//            titleL.setText(list.get(i).getTitle());
//            titleAlternativeL.setText(list.get(i).getTitle_alternative());
//            if(i+1 != list.size()){
//                titleR.setText(list.get(i + 1).getTitle());
//                titleAlternativeR.setText(list.get(i + 1).getTitle_alternative());
//            }
//            layout.addView(linerLayout,params);
//        }
//
//        if(!V2EX.isInitialized(V2EX.ALL_NODES)){
//            V2EX.initialize(V2EX.ALL_NODES);
//
//        }
//       // progressBar.setVisibility(View.GONE);
//
//        Log.d("holo", "finish update");
    }

//    private void add(LinearLayout layout,NodeIntroduce introduce){
//
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        RelativeLayout relativeLayout =(RelativeLayout) LayoutInflater.from(this).inflate(R.layout.node_introduce,null);
//
//        TextView title = (TextView) relativeLayout.findViewById(R.id.title);
//       // TextView header = (TextView) relativeLayout.findViewById(R.id.header);
//       // TextView topics = (TextView) relativeLayout.findViewById(R.id.topics);
//
//        title.setText(introduce.getTitle());
//        //header.setText(introduce.getHeader());
//       // topics.setText(introduce.getTopics() + "个主题");
//
//        layout.addView(relativeLayout,params);
//
//    }
}
