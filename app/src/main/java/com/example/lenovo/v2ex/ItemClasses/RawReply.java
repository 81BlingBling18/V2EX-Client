package com.example.lenovo.v2ex.ItemClasses;

/**
 * Created by lenovo on 2016/7/26.
 */
public class RawReply {
    private int id;
    private int thanks;
    private String content;
    private String content_rendered;
    private Member member;
    private int created;
    private int last_modified;



    public Member getMember(){
        return member;
    }
    public int getLast_modified(){
        return last_modified;
    }
    public String getContent_rendered(){
        return content_rendered;
    }
}
