package com.example.buiderdream.news.base;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.buiderdream.news.R;

public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener{

    public abstract void initView();
    public abstract void initData();
    public abstract void initListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initListener();
    }
}
