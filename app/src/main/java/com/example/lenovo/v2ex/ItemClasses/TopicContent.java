package com.example.lenovo.v2ex.ItemClasses;

import android.graphics.Bitmap;
import android.media.audiofx.BassBoost;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by lenovo on 2016/7/21.
 */
public class TopicContent implements Parcelable{

    String title;
    String content;
    String username;
    String time;
    String replyNumber;
    String nodeName;
    Bitmap avatar;
    ArrayList<ReplyItem> replyList;

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public void setReplyNumber(String replyNumber) {
        this.replyNumber = replyNumber;
    }

    public void setReplyList(ArrayList<ReplyItem> replyList) {
        this.replyList = replyList;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public String getContent() {
        return content;
    }

    public String getNodeName() {
        return nodeName;
    }

    public String getReplyNumber() {
        return replyNumber;
    }

    public ArrayList<ReplyItem> getReplyList() {
        return replyList;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getUsername() {
        return username;
    }


    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(username);
        dest.writeString(time);
        dest.writeString(replyNumber);
        dest.writeString(nodeName);
        dest.writeValue(avatar);
        dest.writeList(replyList);
    }

    public static final Parcelable.Creator<TopicContent> CREATOR = new Parcelable.Creator<TopicContent>(){
        @Override
        public TopicContent createFromParcel(Parcel parcel){
            TopicContent topicContent = new TopicContent();
            topicContent.setTitle(parcel.readString());
            topicContent.setContent(parcel.readString());
            topicContent.setUsername(parcel.readString());
            topicContent.setTime(parcel.readString());
            topicContent.setReplyNumber(parcel.readString());
            topicContent.setNodeName(parcel.readString());
            topicContent.setAvatar((Bitmap) parcel.readValue(Bitmap.class.getClassLoader()));
            topicContent.setReplyList(parcel.readArrayList(getClass().getClassLoader()));
            return topicContent;
        }

        @Override
        public TopicContent[] newArray(int i){
            return new TopicContent[i];
        }

    };
}
