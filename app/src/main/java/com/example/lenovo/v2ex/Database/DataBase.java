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

    private DataBase(Context context, String name, int version){
        super(context,name,null,version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        Log.d("holo","create database success");
        db.execSQL(CREATE_DATA);
        Log.d("holo","create database success");
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
            }
    }

    public static ArrayList<TopicItem> get(int category){
        ArrayList<TopicItem> list = new ArrayList<>();
        Cursor cursor = db.query("topics",null,"category == ?",new String[]{category + ""},null,null,null);
        Log.d("holo","cursor" + cursor.toString());
        if(cursor.moveToFirst()){
            do {
                String title = cursor.getString(cursor.getColumnIndex("title"));
                Integer replies = cursor.getInt(cursor.getColumnIndex("replies"));
                String username = cursor.getString(cursor.getColumnIndex("username"));
                String nodeName = cursor.getString(cursor.getColumnIndex("nodeName"));
                Integer lastModified = cursor.getInt(cursor.getColumnIndex("lastModified"));
                String avatar = cursor.getString(cursor.getColumnIndex("avatar"));
                Log.d("holo",avatar);
                String url = cursor.getString(cursor.getColumnIndex("url"));
                Integer created = cursor.getInt(cursor.getColumnIndex("created"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                Log.d("holo","avatar" + avatar);
                Bitmap bitmap = BitmapHelper.get(avatar);
                TopicItem item = new TopicItem(title,username,nodeName,replies,lastModified,bitmap,url,created,content);
                list.add(item);
            }while(cursor.moveToNext());
        }
        return list;
    }

    private static void delete(int category){
        Log.d("holo", "delete");
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
}
