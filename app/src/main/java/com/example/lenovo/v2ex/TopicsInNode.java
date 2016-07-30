package com.example.lenovo.v2ex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.lenovo.v2ex.Global.V2EX;
import com.example.lenovo.v2ex.TopicsFragment.ArbitraryTopics;
import com.example.lenovo.v2ex.net.GetFromAPI;
import com.example.lenovo.v2ex.net.GetTopics;


/**
 * Created by lenovo on 2016/7/29.
 */
public class TopicsInNode extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topics_in_node);
        ArbitraryTopics arbitraryTopics = (ArbitraryTopics) getSupportFragmentManager().
                findFragmentById(R.id.arbitrary_fragment);
        Intent intent = getIntent();
        String name = intent.getStringExtra("nodeName");
        arbitraryTopics.init(V2EX.ARBITRARY
                        ,"https://www.v2ex.com/api/topics/show.json?node_name=" + name
                        ,new GetTopics(new GetFromAPI()));
        
               // BaseFragment fragment = new BaseFragment();
//                Intent intent = getIntent();
//                String name = intent.getStringExtra("nodeName");
//                ArbitraryTopics fragment = new ArbitraryTopics(V2EX.ARBITRARY
//                        ,"https://www.v2ex.com/api/topics/show.json?node_name=" + name
//                        ,new GetTopics(new GetFromAPI()));
                //fragment.init();
//                Fragment fragment = new TestFragment();
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                transaction.add(R.layout.topics_in_node,fragment);
//                transaction.commit();
//                Log.d("holo", "commit transaction");-

    }
}
