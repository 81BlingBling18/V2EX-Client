package com.example.lenovo.v2ex.ItemClasses;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutCompat;

import java.io.Serializable;

/**
 * Created by lenovo on 2016/7/18.
 */
public class TopicItem implements Parcelable{
    private String title;
    private Integer replies;
    private String username;
    private String nodeName;
    private Integer lastModified;
    private Bitmap image;
    private String url;
    private Integer created;
    private String content;

    public TopicItem(String title,String username,String nodeName,int replies,int lastModified,Bitmap image,String url,int created,String content){
        this.title = title;
        this.username = username;
        this.nodeName = nodeName;
        this.replies = replies;
        this.lastModified = lastModified;
        this.image = image;
        this.url = url;
        this.created = created;
        this.content = content;
    }
    public TopicItem(){
    }

    @Override
    public String toString(){
        return "title" + title + "username" + username + "nodeName" + nodeName + "replies" + replies
                 + "lastModified" + lastModified + "image" + image + "url" + url  + content;

//        + "created" + created + "content" + content
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setLastModified(Integer lastModified) {
        this.lastModified = lastModified;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getLastModified() {
        return lastModified;
    }

    public void setLastModified(int lastModified) {
        this.lastModified = lastModified;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Integer getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public Bitmap getImage(){
        return image;
    }
    public String getUrl(){
        return url;
    }
    public Integer getCreated(){
        return created;
    }
    public void setCreated(Integer integer){
        this.created = integer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(title);
        dest.writeString(username);
        dest.writeString(nodeName);
        dest.writeString(url);
        dest.writeInt(lastModified);
        dest.writeInt(replies);
        dest.writeValue(image);
        dest.writeInt(created);
        dest.writeString(content);
    }

    public static final Parcelable.Creator<TopicItem> CREATOR = new Parcelable.Creator<TopicItem>() {
        @Override
        public TopicItem createFromParcel(Parcel source) {
            TopicItem item = new TopicItem();
            item.setTitle(source.readString());
            item.setUsername(source.readString());
            item.setNodeName(source.readString());
            item.setUrl(source.readString());
            item.setLastModified(source.readInt());
            item.setReplies(source.readInt());
            item.setImage((Bitmap) source.readValue(Bitmap.class.getClassLoader()));
            item.setCreated(source.readInt());
            item.setContent(source.readString());
            return item;
        }

        @Override
        public TopicItem[] newArray(int size) {
            return new TopicItem[size];
        }

    };

}
