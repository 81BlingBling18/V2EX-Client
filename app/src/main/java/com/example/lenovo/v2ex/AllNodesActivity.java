package com.example.lenovo.v2ex;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.v2ex.Interface.Update;
import com.example.lenovo.v2ex.ItemClasses.NodeIntroduce;
import com.example.lenovo.v2ex.net.GetAllNodes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/7/24.
 */
public class AllNodesActivity extends AppCompatActivity implements Update {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_nodes);
        getSupportActionBar().hide();
        GetAllNodes.getAllNodes(this);
    }

    @Override
    public void update(Object object){
        ArrayList<NodeIntroduce> list = new ArrayList<>();
        list.addAll((List<NodeIntroduce>)object);

//
//        RecyclerView leftView = (RecyclerView) findViewById(R.id.left_node_list);
//        RecyclerView rightView = (RecyclerView) findViewById(R.id.right_node_list);

        LinearLayout leftLayout = (LinearLayout) findViewById(R.id.left_list);
        LinearLayout rightLayout = (LinearLayout) findViewById(R.id.right_list);

        ArrayList<NodeIntroduce> leftList =  new ArrayList<>();
        leftList.addAll(list.subList(0,list.size()/2));
        ArrayList<NodeIntroduce> rightList =new ArrayList<>();
        rightList.addAll(list.subList(list.size()/2,list.size()));
        list = null;
        object =null;


        for(NodeIntroduce introduce : rightList){
            add(rightLayout, introduce);
        }
        for(NodeIntroduce introduce : leftList){
            add(leftLayout, introduce);
        }
//        for (NodeIntroduce nodeIntroduce : rightList){
//            Log.d("holo",nodeIntroduce.getHeader() + "   " + nodeIntroduce.getTitle() + "   " + nodeIntroduce.getTopics());
//            Log.d("holo","-----------------------------------");
//        }




//        for (NodeIntroduce nodeIntroduce : leftList){
//            Log.d("holo",nodeIntroduce.getHeader());
//            Log.d("holo",nodeIntroduce.getTitle());
//            Log.d("holo",nodeIntroduce.getTopics() + "");
//            Log.d("holo","-----------------------------------");
//        }


//        NodeAdapter leftAdapter = new NodeAdapter(leftList);
//        NodeAdapter rightAdapter = new NodeAdapter(rightList);
//
//        leftView.setAdapter(leftAdapter);
//        rightView.setAdapter(rightAdapter);
//
//        leftView.setLayoutManager(new LinearLayoutManager(this));
//        rightView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void add(LinearLayout layout,NodeIntroduce introduce){

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout relativeLayout =(RelativeLayout) LayoutInflater.from(this).inflate(R.layout.node_introduce,null);

        TextView title = (TextView) relativeLayout.findViewById(R.id.title);
        TextView header = (TextView) relativeLayout.findViewById(R.id.header);
        TextView topics = (TextView) relativeLayout.findViewById(R.id.topics);

        title.setText(introduce.getTitle());
        header.setText(introduce.getHeader());
        topics.setText(introduce.getTopics() + "个主题");

        layout.addView(relativeLayout,params);

    }
}
