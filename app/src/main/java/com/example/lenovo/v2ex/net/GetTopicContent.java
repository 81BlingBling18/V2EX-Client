package com.example.lenovo.v2ex.net;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.lenovo.v2ex.Global.V2EX;
import com.example.lenovo.v2ex.ItemClasses.ReplyItem;
import com.example.lenovo.v2ex.ItemClasses.TopicContent;
import com.example.lenovo.v2ex.Interface.Update;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lenovo on 2016/7/21.
 */
public class GetTopicContent {
    public static void GetContent(final String url, final Update update, final int pageNumber){


        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message message){
                       // ReplyItem item = (ReplyItem) message.obj;
//                        ArrayList<ReplyItem> list = (ArrayList<ReplyItem>) message.obj;
//                        for(ReplyItem item : list){
//                            update.update(item);
//                        }
                Log.d("holo", "begin to update");
                        update.update(message.obj);
            }
        };

        new Thread(new Runnable() {

            @Override
            public void run() {
                try{
                    ArrayList<ReplyItem> replyArrayList = new ArrayList<ReplyItem>();
                    Integer ii = 1;
                    for(int i = 0;i<pageNumber;i++){
                        String html = InternetUtils.getHttpContent(url + "?p=" + (i + 1));
                        Log.d("holo", html);
                        Document topicContentDoc = Jsoup.parse(html);
                        Log.d("holo", "get document");
                        //提取回复信息
                        Elements replies = topicContentDoc.getElementsByClass("cell");

                        replies.remove(0).text();
                        if(replies.size() != 0){
                            replies.remove(0);
                        }

                        String regex;
                        String target;
                        Pattern pattern;
                        Matcher matcher;

                        replies.add(topicContentDoc.getElementsByClass("inner").get(0));

                        for(Element e : replies){
                            String heart = "";
                            String client = "";
                            String comment;
                            String username = "";
                            String time = "";
                            ReplyItem reply = new ReplyItem();
                            replyArrayList.add(reply);
                            //获取图片
                            Elements elements1 = e.getElementsByClass("avatar");
                            for(Element e2 : elements1){
                                String content = e2.toString();
                                regex = "//cdn.v2ex.co/[a-z0-9/?&=;_.]{10,}\"";
                                pattern = Pattern.compile(regex);
                                matcher = pattern.matcher(content);
                                if(matcher.find()){
                                    String url = matcher.group();
                                    url = "http:" + url.substring(0,url.length()-1);
                                    //reply.setAvatar(Picasso.with(V2EX.getInstance().getApplicationContext()).load(url).get());
                                    reply.setUrl(url);
                                }
                            }

                            //获取其余部分

                            regex = "\\d [0-9a-zA-Z]{1,16}";
                            target = e.text();
                            pattern = Pattern.compile(regex);
                            matcher = pattern.matcher(target);
                            if(matcher.find()){
                                username = matcher.group();
                            }
                            username = username.substring(2,username.length());
                            reply.setUsername(username);
                            regex = "[0-9]{1,2} [0-9\\u4e00-\\u9fa5 ]+前";
                            pattern = Pattern.compile(regex);
                            matcher = pattern.matcher(target);
                            if(matcher.find()){
                                time = matcher.group();
                            }

                            reply.setTime(time);

                            regex = "[♥] [0-9]+";
                            pattern = Pattern.compile(regex);
                            matcher = pattern.matcher(target);
                            if(matcher.find()){
                                heart = matcher.group();
                            }
                            regex = "via Android";
                            pattern = Pattern.compile(regex);
                            matcher = pattern.matcher(target);
                            if(matcher.find()){
                                client = matcher.group();
                            }else{
                                regex = "via iPhone";
                                pattern = Pattern.compile(regex);
                                matcher = pattern.matcher(target);
                                if(matcher.find()){
                                    client = matcher.group();
                                }else{
                                    regex = "via iPad";
                                    pattern = Pattern.compile(regex);
                                    matcher = pattern.matcher(target);
                                    if(matcher.find()){
                                        client = matcher.group();
                                    }
                                }
                            }
                            if(!heart.equals("")){
                                heart += "111";
                            }
                            if (!client.equals("")){
                                client += "1";
                            }
                            comment = target.substring(9 +ii.toString().length() +  username.length() + time.length()
                                    + heart.length() + client.length(),target.length());
                            reply.setContent(comment);
                            reply.setFloor(ii + "楼");
                            Log.d("holo", ii + "  " + comment);

//                        if((i == replies.size())||(i>4 && i%5 == 0)){
//                            Message message = new Message();
//                            message.what = SUCCESS_MESSAGE;
//                            message.obj = replyArrayList;
//                            replyArrayList = new ArrayList<ReplyItem>();
//                            handler.sendMessage(message);
//
//                        }
                            ii++;
                        }
                    }
                    Log.d("holo", "begin to send message");
                    Message message = new Message();
                    message.obj = replyArrayList;
                    handler.sendMessage(message);

                }catch (Exception e){
                    e.printStackTrace();
                }finally {

                }
            }
        }).start();

    }
}
