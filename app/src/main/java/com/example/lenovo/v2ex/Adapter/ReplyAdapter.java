package com.example.lenovo.v2ex.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.v2ex.DecodeMarkDown;
import com.example.lenovo.v2ex.Global.V2EX;
import com.example.lenovo.v2ex.ItemClasses.ReplyItem;
import com.example.lenovo.v2ex.R;
import com.squareup.picasso.Picasso;

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
            if(v == mHeader){
                return;
            }
            avatar = (ImageView)v.findViewById(R.id.avatar);
            username = (TextView) v.findViewById(R.id.username);
            time = (TextView)v.findViewById(R.id.time);
            floor = (TextView)v.findViewById(R.id.floor);
            reply = (TextView)v.findViewById(R.id.reply);
        }
    }

    ArrayList<ReplyItem> replyItems;
    View mHeader;
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    public ReplyAdapter(ArrayList<ReplyItem> replyItems){
        this.replyItems = replyItems;
    }

    public void setHeader(View v){
        mHeader = v;
        notifyItemInserted(0);
    }

    @Override
    public int getItemCount(){
        if(mHeader == null){
            return replyItems.size();
        }else{
         return replyItems.size() + 1;
        }
    }

    @Override
    public int getItemViewType(int position){
        if(mHeader == null){
            return TYPE_NORMAL;
        }
        if(position == 0){
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeader != null&& viewType == TYPE_HEADER){
            return new ViewHolder(mHeader);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reply,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_HEADER){
            return;
        }
        position--;
        ReplyItem item = replyItems.get(position);
        holder.floor.setText(item.getFloor());
        holder.username.setText(item.getUsername());
        DecodeMarkDown.decode(holder.reply,item.getContent(), V2EX.getInstance().getResources().getDisplayMetrics().widthPixels * 7 / 10);
        holder.time.setText(item.getTime());
        ImageView imageView = holder.avatar;
        String url = replyItems.get(position).getUrl();
        holder.avatar.setImageBitmap(item.getAvatar());
        Picasso.with(V2EX.getInstance()
                .getApplicationContext())
                .load(url)
                .error(android.R.color.holo_blue_bright)
                .tag(position)
                .into(imageView);

    }
}
