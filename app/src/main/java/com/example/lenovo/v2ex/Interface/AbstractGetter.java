package com.example.lenovo.v2ex.Interface;

import com.example.lenovo.v2ex.ItemClasses.Topic;
import com.example.lenovo.v2ex.ItemClasses.TopicItem;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/7/25.
 */
public interface AbstractGetter {

    public ArrayList<TopicItem> get(String data);
}
