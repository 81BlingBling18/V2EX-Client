package com.example.lenovo.v2ex.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.v2ex.Database.DataBase;
import com.example.lenovo.v2ex.Global.V2EX;
import com.example.lenovo.v2ex.ItemClasses.TopicItem;
import com.example.lenovo.v2ex.R;
import com.example.lenovo.v2ex.TopicContentActivity;

import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2016/7/19.
 */
public class TopicItemAdapter extends RecyclerView.Adapter<TopicItemAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView title;
        TextView replies;
        TextView username;
        TextView nodeName;
        TextView lastModified;
        ImageView avatar;
        int position;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            replies = (TextView) v.findViewById(R.id.replies);
            username = (TextView) v.findViewById(R.id.username);
            nodeName = (TextView) v.findViewById(R.id.nodeName);
            lastModified = (TextView) v.findViewById(R.id.lastModified);
            avatar = (ImageView) v.findViewById(R.id.avatar);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(V2EX.getInstance().getApplicationContext(),TopicContentActivity.class);
                    intent.putExtra("topicItem",topicList.get(position));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    V2EX.getInstance().getApplicationContext().startActivity(intent);
                }
            });
        }
    }

    List<TopicItem> topicList;

    public TopicItemAdapter(List<TopicItem> list){
        topicList = list;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.username.setText(topicList.get(position).getUsername());
        String node = DataBase.get(topicList.get(position).getNodeName());
        holder.nodeName.setText(node);
        holder.title.setText(topicList.get(position).getTitle());
        holder.replies.setText(topicList.get(position).getReplies().toString() + "个回复");
        holder.avatar.setImageBitmap(topicList.get(position).getImage());
        holder.position = position;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("MM-dd hh:mm:ss");
        long number = topicList.get(position).getLastModified();
        String time = format.format(new Date(number * 1000L));
        holder.lastModified.setText(DateUtils.getRelativeDateTimeString(V2EX.getInstance().getApplicationContext(), number * 1000, DateUtils.MINUTE_IN_MILLIS, 0, DateUtils.FORMAT_ABBREV_TIME));

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic,parent,false);
        TopicItemAdapter.ViewHolder viewHolder = new TopicItemAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

}
