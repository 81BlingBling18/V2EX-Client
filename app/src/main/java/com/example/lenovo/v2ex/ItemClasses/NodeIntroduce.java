package com.example.lenovo.v2ex.ItemClasses;

/**
 * Created by lenovo on 2016/7/24.
 */
public class NodeIntroduce {

    private int id;
    private String name;
    private String url;
    private String title;
    private String title_alternative;
    private int topics;
    private String header;
    private String footer;
    private int created;

    public NodeIntroduce(int created, String footer, String header, int id, String name, String title, String title_alternative, int topics, String url) {
        this.created = created;
        this.footer = footer;
        this.header = header;
        this.id = id;
        this.name = name;
        this.title = title;
        this.title_alternative = title_alternative;
        this.topics = topics;
        this.url = url;
    }

    public int getCreated() {
        return created;
    }

    public String getFooter() {
        return footer;
    }

    public String getHeader() {
        return header;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getTitle_alternative() {
        return title_alternative;
    }

    public int getTopics() {
        return topics;
    }

    public String getUrl() {
        return url;
    }
    public void setHeader(String header){
        this.header = header;
    }

}
