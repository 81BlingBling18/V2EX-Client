package com.example.lenovo.v2ex.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.v2ex.ItemClasses.NodeIntroduce;
import com.example.lenovo.v2ex.R;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/7/24.
 */
public class NodeAdapter extends RecyclerView.Adapter<NodeAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView header;
        TextView topics;
        TextView title;


        public ViewHolder(View view){
            super(view);
            header = (TextView) view.findViewById(R.id.header);
            topics = (TextView) view.findViewById(R.id.topics);
            title = (TextView) view.findViewById(R.id.title);
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
        viewHolder.topics.setText(nodeList.get(position).getTopics()+"");
        String header = nodeList.get(position).getHeader();
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
