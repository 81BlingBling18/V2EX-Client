package com.example.lenovo.v2ex.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Message;
import android.renderscript.ScriptGroup;
import android.util.Log;

import com.example.lenovo.v2ex.Global.V2EX;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lenovo on 2016/7/24.
 */
public class InternetUtils {

    public static boolean accessible(){
        ConnectivityManager connectivityManager = (ConnectivityManager) V2EX.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getActiveNetworkInfo() != null){
            return connectivityManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }

    public static String getHttpContent(String path){

        HttpURLConnection httpURLConnection = null;
        InputStream in = null;
        BufferedReader bufferedReader = null;
        String html = "";
        try{
            URL url = new URL(path);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(6*1000);
            httpURLConnection.setReadTimeout(6*1000);
            in = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(in));

            StringBuilder builder = new StringBuilder();
            String line = null;


            while((line = bufferedReader.readLine()) != null){
                builder.append(line);
            }
            if(in != null){
                in.close();
            }
            if (bufferedReader != null){
                bufferedReader.close();
            }
            html = builder.toString();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
        }
        return html;
    }
}
