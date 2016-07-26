package com.example.lenovo.v2ex.ItemClasses;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by lenovo on 2016/7/21.
 */
public class ReplyItem implements Parcelable{
    Bitmap avatar;
    String time;
    String content;
    String username;
    String floor;


    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public String getContent() {
        return content;
    }

    public String getFloor() {
        return floor;
    }

    public String getTime() {
        return time;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest,int flags){
        dest.writeValue(avatar);
        dest.writeString(time);
        dest.writeString(content);
        dest.writeString(username);
        dest.writeString(floor);
    }

    public static Parcelable.Creator<ReplyItem> CREATOR = new Parcelable.Creator<ReplyItem>(){
        @Override
        public ReplyItem createFromParcel(Parcel parcel){
            ReplyItem item = new ReplyItem();
            item.setAvatar((Bitmap) parcel.readValue(Bitmap.class.getClassLoader()));
            item.setTime(parcel.readString());
            item.setContent(parcel.readString());
            item.setUsername(parcel.readString());
            item.setFloor(parcel.readString());
            return item;
        }

        @Override
        public ReplyItem[] newArray(int i){
            return new ReplyItem[i];
        }
    };
}



