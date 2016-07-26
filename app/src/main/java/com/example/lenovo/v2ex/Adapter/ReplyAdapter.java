package com.example.lenovo.v2ex.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.v2ex.ItemClasses.ReplyItem;
import com.example.lenovo.v2ex.R;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/7/21.
 */
public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView avatar;
        TextView username;
        TextView time;
        TextView floor;
        TextView reply;

        public ViewHolder(View v){
            super(v);
            avatar = (ImageView)v.findViewById(R.id.avatar);
            username = (TextView) v.findViewById(R.id.username);
            time = (TextView)v.findViewById(R.id.time);
            floor = (TextView)v.findViewById(R.id.floor);
            reply = (TextView)v.findViewById(R.id.reply);
        }
    }

    ArrayList<ReplyItem> replyItems;

    public ReplyAdapter(ArrayList<ReplyItem> replyItems){
        this.replyItems = replyItems;
    }

    @Override
    public int getItemCount(){
        return replyItems.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reply,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ReplyItem item = replyItems.get(position);
        holder.floor.setText(item.getFloor());
        holder.username.setText(item.getUsername());
        holder.avatar.setImageBitmap(item.getAvatar());
        holder.reply.setText(item.getContent());
        holder.time.setText(item.getTime());
    }

}
