package com.example.lenovo.v2ex.net;

import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;

import com.example.lenovo.v2ex.Global.V2EX;
import com.example.lenovo.v2ex.ItemClasses.NodeIntroduce;
import com.example.lenovo.v2ex.R;
import com.example.lenovo.v2ex.Interface.Update;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by lenovo on 2016/7/24.
 */
public class GetAllNodes {

    public static void getAllNodes(final Update update){

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message message){

                update.update((ArrayList<NodeIntroduce>)message.obj);
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = InternetUtils.getHttpContent(V2EX.getInstance().getString(R.string.all_nodes));
                Gson gson = new Gson();
                ArrayList<NodeIntroduce> list = gson.fromJson(data,new TypeToken<ArrayList<NodeIntroduce>>(){}.getType());
                for(NodeIntroduce node : list){
                    String rawHeader = node.getHeader();
                    if(rawHeader != null){
                        String header = Html.fromHtml(rawHeader).toString();
                        node.setHeader(header);
                    }
                }
                Log.d("holo", "from get all nodes" + list.get(0).getName());
                Message message = new Message();
                message.obj = list;
                handler.sendMessage(message);
            }
        }).start();
    }
}
