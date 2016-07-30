package com.example.lenovo.v2ex.Global;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.example.lenovo.v2ex.R;

/**
 * Created by lenovo on 2016/7/19.
 */
public class V2EX extends Application {
    private static V2EX v2EX;

    public static final int HOT_TOPICS = 0;
    public static final int NEW_TOPICS = 1;
    public static final int TECH_TOPICS = 2;
    public static final int ALL_NODES = 3;
    public static final int ARBITRARY = 4;

    @Override
    public void onCreate(){
        super.onCreate();
        v2EX = this;
    }

    public static V2EX getInstance(){
        return v2EX;
    }

    public static void initialize(int topicType){
        SharedPreferences sharedPreferences = V2EX.getInstance().getApplicationContext().getSharedPreferences(V2EX.getInstance().getApplicationContext().getString(R.string.isInitialized)
        ,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (topicType){
            case HOT_TOPICS:
                editor.putBoolean("hotTopics",true);
                break;
            case NEW_TOPICS:
                editor.putBoolean("newTopics", true);
                break;
            case TECH_TOPICS:
                editor.putBoolean("techTopics", true);
                break;
            case ALL_NODES:
                editor.putBoolean("allNodes",true);
                break;
            default:
                break;
        }
        editor.commit();
    }
    public static boolean isInitialized(int topicType){
        SharedPreferences sharedPreferences = V2EX.getInstance().getApplicationContext().getSharedPreferences(V2EX.getInstance().getApplicationContext().getString(R.string.isInitialized)
                ,MODE_PRIVATE);
        switch (topicType){
            case HOT_TOPICS:
                return sharedPreferences.getBoolean("hotTopics", false);
            case NEW_TOPICS:
                return sharedPreferences.getBoolean("newTopics",false);
            case TECH_TOPICS:
                return sharedPreferences.getBoolean("techTopics",false);
            case ALL_NODES:
                return sharedPreferences.getBoolean("allNodes", false);
            default:
                return false;
        }
    }
}
