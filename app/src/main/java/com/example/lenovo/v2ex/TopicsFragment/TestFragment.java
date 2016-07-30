package com.example.lenovo.v2ex.TopicsFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.v2ex.R;

/**
 * Created by lenovo on 2016/7/29.
 */
public class TestFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,Bundle savedInstanceState){
        return inflater.inflate(R.layout.test_layout, parent,false);
    }
}
