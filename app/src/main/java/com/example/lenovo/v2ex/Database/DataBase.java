package com.example.lenovo.v2ex.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.lenovo.v2ex.Bitmap.BitmapHelper;
import com.example.lenovo.v2ex.Global.V2EX;
import com.example.lenovo.v2ex.ItemClasses.NodeIntroduce;
import com.example.lenovo.v2ex.ItemClasses.TopicItem;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/7/23.
 */
public class DataBase extends SQLiteOpenHelper {



    private static DataBase dh = new DataBase(V2EX.getInstance().getApplicationContext(),
            "data",1);
    private  static SQLiteDatabase db = dh.getWritableDatabase();

    Context mContext;

    public static final String CREATE_DATA = "create table topics(" +
            "id integer primary key autoincrement," +
            "category int," +
            "lastModified int," +
            "url text," +
            "created int," +
            "content text," +
            "username text," +
            "nodeName text," +
            "title text," +
            "replies int," +
            "avatar text)";

    public static final String CREATE_NODES_DATA = "create table nodes(" +
            "name text primary key," +
            "title text," +
            "topicNumber int," +
            "header text)";

    private DataBase(Context context, String name, int version){
        super(context,name,null,version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_DATA);
        db.execSQL(CREATE_NODES_DATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }

    public static void update(ArrayList<TopicItem> list,int category){

        delete(category);

            for(TopicItem item : list){
                ContentValues values = new ContentValues();
                String avatarName = BitmapHelper.output(item.getImage());
                values.put("category",category);
                values.put("username",item.getUsername());
                values.put("title",item.getTitle());
                values.put("replies", item.getReplies());
                values.put("nodeName", item.getNodeName());
                values.put("lastModified", item.getLastModified());
                values.put("avatar", avatarName);
                values.put("url", item.getUrl());
                values.put("created", item.getCreated());
                values.put("content",item.getContent());
                db.insert("topics",null,values);
                values.clear();
            }
    }
    public static void update(ArrayList<NodeIntroduce> list){
        delete();
        for(NodeIntroduce introduce: list){
            ContentValues values = new ContentValues();
            values.put("name", introduce.getName());
            values.put("title", introduce.getTitle());
            values.put("header",introduce.getHeader());
            values.put("topicNumber",introduce.getTopics());
            db.insert("nodes", null, values);
            values.clear();
        }
    }

    public static ArrayList<TopicItem> get(int category){
        ArrayList<TopicItem> list = new ArrayList<>();
        Cursor cursor = db.query("topics",null,"category == ?",new String[]{category + ""},null,null,null);
        if(cursor.moveToFirst()){
            do {
                String title = cursor.getString(cursor.getColumnIndex("title"));
                Integer replies = cursor.getInt(cursor.getColumnIndex("replies"));
                String username = cursor.getString(cursor.getColumnIndex("username"));
                String nodeName = cursor.getString(cursor.getColumnIndex("nodeName"));
                Integer lastModified = cursor.getInt(cursor.getColumnIndex("lastModified"));
                String avatar = cursor.getString(cursor.getColumnIndex("avatar"));
                String url = cursor.getString(cursor.getColumnIndex("url"));
                Integer created = cursor.getInt(cursor.getColumnIndex("created"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                Bitmap bitmap = BitmapHelper.get(avatar);
                TopicItem item = new TopicItem(title,username,nodeName,replies,lastModified,bitmap,url,created,content);
                list.add(item);
            }while(cursor.moveToNext());
        }
        return list;
    }

    public static String get(String name){
        Cursor cursor = db.query("nodes", null, "name == ?", new String[]{name}, null, null, null);
        String title = "";
        if(cursor.moveToFirst()){
            title = cursor.getString(cursor.getColumnIndex("title"));
        }
        return title;
    }

    public static ArrayList<NodeIntroduce>get(){
        ArrayList<NodeIntroduce> list = new ArrayList<>();
        Cursor cursor = db.query("nodes", null, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String header = cursor.getString(cursor.getColumnIndex("header"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int topicNumber = cursor.getInt(cursor.getColumnIndex("topicNumber"));
                NodeIntroduce node = new NodeIntroduce(0, null, header, 0, name, title, null, topicNumber, null);
                list.add(node);
            }while (cursor.moveToNext());
        }
        return list;
    }

    private static void delete(int category){
        Cursor cursor = db.query("topics",null,"category == ?",new String[]{category + ""},null,null,null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String picName = cursor.getString(cursor.getColumnIndex("avatar"));
                BitmapHelper.delete(picName);
                db.delete("topics","id == ?",new String[]{id + ""});
            }while (cursor.moveToNext());
        }
    }
    private static void delete(){
        db.delete("nodes", null, null);
    }
}
