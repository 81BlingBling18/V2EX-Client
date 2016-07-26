package com.example.lenovo.v2ex.net;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.lenovo.v2ex.Adapter.TopicFragmentPagerAdapter;
import com.example.lenovo.v2ex.Interface.AbstractGetter;
import com.example.lenovo.v2ex.Bitmap.BitmapHelper;
import com.example.lenovo.v2ex.ItemClasses.Topic;
import com.example.lenovo.v2ex.ItemClasses.TopicFromSingleTopic;
import com.example.lenovo.v2ex.ItemClasses.TopicItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lenovo on 2016/7/25.
 */
public class GetFromHtml implements AbstractGetter {

    public ArrayList<TopicItem> get(String data){

        String regex = "/t/[0-9]{6}";

        Gson  gson = new Gson();
        ArrayList<TopicItem> list = new ArrayList<>();

        Document document = Jsoup.parse(data);
        Elements elements = document.getElementsByClass("item_title");
        for(Element e :elements){

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(e.toString());
            String temp = "";
            if(matcher.find()){
                temp = matcher.group();
            }
            Integer id = new Integer(temp.substring(3, temp.length()));
            String topicData = InternetUtils.getHttpContent("http://www.v2ex.com/api/topics/show.json?id=" + id);

            List<TopicFromSingleTopic> tempList = gson.fromJson(topicData, new TypeToken<List<TopicFromSingleTopic>>(){}.getType());
            TopicFromSingleTopic topic = tempList.get(0);

            Bitmap avatar = BitmapHelper.getByUrl("http:" + topic.getNode().getAvatar_large());
            Log.d("cell",topic.getNode().getAvatar_large());
            TopicItem item = new TopicItem();
            item.setTitle(topic.getTitle());
            item.setReplies(topic.getReplies());
            item.setUsername(topic.getMember().getUsername());
            item.setNodeName(topic.getNode().getName());
            item.setLastModified(topic.getLast_modified());
            item.setImage(avatar);
            item.setUrl(topic.getUrl());
            item.setCreated(topic.getCreated());
            item.setContent(topic.getContent());
            list.add(item);
        }
        Log.d("cell", list.size() + "");
        return list;
    }
}
