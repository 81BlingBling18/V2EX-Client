package com.example.lenovo.v2ex;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.v2ex.Adapter.ReplyAdapter;
import com.example.lenovo.v2ex.Bitmap.BitmapHelper;
import com.example.lenovo.v2ex.Database.DataBase;
import com.example.lenovo.v2ex.Global.V2EX;
import com.example.lenovo.v2ex.Interface.Update;
import com.example.lenovo.v2ex.ItemClasses.ReplyItem;
import com.example.lenovo.v2ex.ItemClasses.TopicItem;
import com.example.lenovo.v2ex.net.GetTopicContent;

import org.markdown4j.Markdown4jProcessor;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by lenovo on 2016/7/22.
 */
public class TopicContentActivity extends AppCompatActivity implements Update {
    RecyclerView replyList;
    ImageView bitmap;
    TextView username;
    TextView time;
    TextView replyNumber;
    TextView nodeName;
    TextView title;
    TextView content;
    View header;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_content);


        //layout = (LinearLayout) findViewById(R.id.replyLayout);
        replyList = (RecyclerView) findViewById(R.id.replies);

        final TopicItem topicItem = getIntent().getParcelableExtra("topicItem");

        //设置header
        header= LayoutInflater.from(this).inflate(R.layout.content_header,null);
        bitmap = (ImageView)header.findViewById(R.id.avatar);
        username = (TextView) header.findViewById(R.id.username);
        time = (TextView) header.findViewById(R.id.time);
        replyNumber = (TextView) header.findViewById(R.id.reply_number);
        nodeName = (TextView) header.findViewById(R.id.node_name);
        title = (TextView) header.findViewById(R.id.title);
        content = (TextView) header.findViewById(R.id.content);

        username.setText(topicItem.getUsername());
        time.setText(DateUtils.getRelativeDateTimeString(this, topicItem.getCreated() * 1000L, DateUtils.MINUTE_IN_MILLIS, 0, DateUtils.FORMAT_ABBREV_TIME));
        replyNumber.setText(" "+topicItem.getReplies().toString() + "个回复");
        String node = DataBase.get(topicItem.getNodeName());
        nodeName.setText(node);
        title.setText(topicItem.getTitle());
        bitmap.setImageBitmap(topicItem.getImage());
        DecodeMarkDown.decode(content,topicItem.getContent(), getResources().getDisplayMetrics().widthPixels);

        int pageNumber = topicItem.getReplies() / 100;
        int i = topicItem.getReplies() %100;
        if(topicItem.getReplies() %100 >1){
            pageNumber++;
        }
        Log.d("holo", topicItem.getUrl());
        GetTopicContent.GetContent(topicItem.getUrl(),this,pageNumber);
    }

    @Override
    public void update(Object it){
        //ReplyItem item = (ReplyItem) it;
        ArrayList<ReplyItem> list = (ArrayList<ReplyItem>) it;
        ReplyAdapter replyAdapter = new ReplyAdapter(list);
        replyAdapter.setHeader(header);
        replyList.setLayoutManager(new LinearLayoutManager(this));
        replyList.setAdapter(replyAdapter);

//        for(ReplyItem item: list){
//            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(TopicContentActivity.this)
//                    .inflate(R.layout.reply, null);
//            ImageView avatar = (ImageView) linearLayout.findViewById(R.id.avatar);
//            TextView username = (TextView) linearLayout.findViewById(R.id.username);
//            TextView time = (TextView) linearLayout.findViewById(R.id.time);
//            TextView reply = (TextView) linearLayout.findViewById(R.id.reply);
//            TextView floor = (TextView) linearLayout.findViewById(R.id.floor);
//            avatar.setImageBitmap(item.getAvatar());
//            username.setText(item.getUsername());
//            time.setText(item.getTime());
//            reply.setText(item.getContent());
//            floor.setText(item.getFloor());
//
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);
//            layout.addView(linearLayout, layoutParams);
//        }
    }

}
