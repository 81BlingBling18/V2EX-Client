package com.example.lenovo.v2ex.net;

import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.lenovo.v2ex.Interface.AbstractGetter;
import com.example.lenovo.v2ex.Database.DataBase;
import com.example.lenovo.v2ex.Global.V2EX;
import com.example.lenovo.v2ex.ItemClasses.TopicItem;
import com.example.lenovo.v2ex.Interface.Update;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/7/25.
 */
public  class GetTopics {

    AbstractGetter getter;

    public GetTopics(AbstractGetter getter){
        this.getter = getter;
    }

    public  void get(final String urlOfTopics, final Update update, final int type){

        final int SHOW_RESPONSE = 0;
        final String hotURL = "https://www.v2ex.com/api/topics/hot.json";
        final String newURL = "https://www.v2ex.com/api/topics/latest.json";
        final String techURL = "https://www.v2ex.com/?tab=tech";


        final Handler handler = new Handler(){
            public void handleMessage(Message msg){
                switch (msg.what){
                    case SHOW_RESPONSE:
                        final ArrayList<TopicItem> list = (ArrayList<TopicItem>)msg.obj;
                        DataBase.update(list,type);
                        for(TopicItem item:list){
                            Log.d("holo",1 +"   "+ item.getNodeName());
                            Log.d("holo",2 +"   "+ item.getTitle());
                            Log.d("holo",3 +"   "+ item.getUsername());
                            Log.d("holo",4 +"   "+ item.getLastModified() + "");
                            Log.d("holo",5 +"   "+ item.getReplies() + "");
                            Log.d("holo",6 +"   "+ item.getContent().toString());
                            Log.d("holo",6 +"   "+ item.getCreated());
                            Log.d("holo","----------------------------------------------");
                        }
                        update.update(list);
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String data = InternetUtils.getHttpContent(urlOfTopics);
                    ArrayList<TopicItem> topicList = getter.get(data);
                    Message message = new Message();
                    message.what = SHOW_RESPONSE;
                    message.obj = topicList;
                    handler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
