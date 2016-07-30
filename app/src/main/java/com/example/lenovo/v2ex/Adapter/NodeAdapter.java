package com.example.lenovo.v2ex.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.v2ex.Global.V2EX;
import com.example.lenovo.v2ex.ItemClasses.NodeIntroduce;
import com.example.lenovo.v2ex.R;
import com.example.lenovo.v2ex.TopicsInNode;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/7/24.
 */
public class NodeAdapter extends RecyclerView.Adapter<NodeAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView header;
        TextView topicNumber;
        TextView title;
        int position;

        public ViewHolder(View view){
            super(view);
            header = (TextView) view.findViewById(R.id.header);
            topicNumber = (TextView) view.findViewById(R.id.topic_number);
            title = (TextView) view.findViewById(R.id.title);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(V2EX.getInstance().getApplicationContext(), TopicsInNode.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("nodeName", nodeList.get(position).getName());
                    Log.d("holo", "url" + nodeList.get(position).getName());
                    V2EX.getInstance().getApplicationContext().startActivity(intent);
                }
            });
        }
    }

    ArrayList<NodeIntroduce> nodeList;


    public NodeAdapter(ArrayList<NodeIntroduce> list){
        nodeList = list;
    }

    @Override
    public int getItemCount(){
        return nodeList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder,int position){
        viewHolder.title.setText(nodeList.get(position).getTitle());
        viewHolder.topicNumber.setText(nodeList.get(position).getTopics()+"个主题");
        String header = nodeList.get(position).getHeader();
        viewHolder.position = position;
        if(header != null){
            viewHolder.header.setText(nodeList.get(position).getHeader());
        }else{
            viewHolder.header.setText("");
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int type){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.node_introduce, parent, false);
        return new ViewHolder(v);
    }
}




