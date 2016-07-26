package com.example.lenovo.v2ex.net;

import android.os.Handler;
import android.os.Message;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lenovo on 2016/7/21.
 */
public class GetTopicContent {
    public static void GetContent(final String url, final Update update){
        final TopicContent topicContent = new TopicContent();
        final int SUCCESS_MESSAGE = 1;

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message message){
                switch(message.what){
                    case SUCCESS_MESSAGE:
                        ReplyItem item = (ReplyItem) message.obj;
                        update.update(item);
                        break;
                    default:
                        break;
                }
            }
        };

        new Thread(new Runnable() {

            HttpURLConnection httpURLConnection = null;
            InputStream in = null;
            BufferedReader bufferedReader = null;
            @Override
            public void run() {
                try{
                    String html = InternetUtils.getHttpContent(url);
                    Document topicContentDoc = Jsoup.parse(html);
                    //提取回复信息
                    Elements replies = topicContentDoc.getElementsByClass("cell");

                    topicContent.setContent(replies.remove(0).text());
                    if(replies.size() != 0){
                        replies.remove(0);
                    }

                    String regex;
                    String target;
                    Pattern pattern;
                    Matcher matcher;

                    replies.add(topicContentDoc.getElementsByClass("inner").get(0));
                    ArrayList<ReplyItem> replyArrayList = new ArrayList<ReplyItem>();
                    Integer i = 1;
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
                                    reply.setAvatar(Picasso.with(V2EX.getInstance().getApplicationContext()).load(url).get());
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
                        comment = target.substring(9 +i.toString().length() +  username.length() + time.length()
                         + heart.length() + client.length(),target.length());
                        reply.setContent(comment);
                        reply.setFloor(i + "楼");

                        i++;
                        Message message = new Message();
                        message.what = SUCCESS_MESSAGE;
                        message.obj = reply;
                        handler.sendMessage(message);
                    }
                    topicContent.setReplyList(replyArrayList);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {

                }
            }
        }).start();

    }
}
