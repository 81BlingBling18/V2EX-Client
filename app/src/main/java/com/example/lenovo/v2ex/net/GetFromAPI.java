package com.example.lenovo.v2ex.net;

import android.graphics.Bitmap;

import com.example.lenovo.v2ex.Interface.AbstractGetter;
import com.example.lenovo.v2ex.Bitmap.BitmapHelper;
import com.example.lenovo.v2ex.ItemClasses.Topic;
import com.example.lenovo.v2ex.ItemClasses.TopicItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/7/25.
 */
public class GetFromAPI implements AbstractGetter {

    public ArrayList<TopicItem> get(String data){

        ArrayList<TopicItem> hotTopicList = null;
        HttpURLConnection connection = null;

        try{
            Gson gson = new Gson();
            List<Topic> dataList= gson.fromJson(data,new TypeToken<List<Topic>>(){}.getType());
            hotTopicList = new ArrayList<>();
            for(Topic topic : dataList){
                String username = topic.getMember().getUsername();
                String nodeName = topic.getNode().getName();
                String title = topic.getTitle();
                String topicUrl = topic.getUrl();
                int replies = topic.getReplies();
                int lastModified = topic.getLast_modified();
                int created = topic.getCreated();
                String content = topic.getContent();

                String url = "http:"+topic.getMember().getAvatar_large();
                Bitmap bitmap = BitmapHelper.getByUrl(url);
                hotTopicList.add(new TopicItem(title,username,nodeName,replies,lastModified,bitmap,topicUrl,created,content));
        }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(connection != null){
                connection.disconnect();
            }
        }
        return hotTopicList;
    }
}
