package com.example.lenovo.v2ex.net;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.lenovo.v2ex.Global.V2EX;
import com.example.lenovo.v2ex.ItemClasses.NodeIntroduce;
import com.example.lenovo.v2ex.R;
import com.example.lenovo.v2ex.Interface.Update;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/7/24.
 */
public class GetAllNodes {

    public static void getAllNodes(final Update update){

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message message){
//                ArrayList<NodeIntroduce> list = new ArrayList<>();
//                list.addAll((List<NodeIntroduce>)message.obj);
                update.update((ArrayList<NodeIntroduce>)message.obj);
                message.obj = null;
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = InternetUtils.getHttpContent(V2EX.getInstance().getString(R.string.all_nodes));
                Gson gson = new Gson();
                ArrayList<NodeIntroduce> list = gson.fromJson(data,new TypeToken<List<NodeIntroduce>>(){}.getType());
                Log.d("holo", list.size() + "");


                Message message = new Message();
                message.obj = list;
                handler.sendMessage(message);
            }
        }).start();
    }
}
