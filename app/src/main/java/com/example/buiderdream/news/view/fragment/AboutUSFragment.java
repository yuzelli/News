package com.example.buiderdream.news.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.buiderdream.news.R;
import com.example.buiderdream.news.base.BaseFragment;

/**
 * Created by Administrator on 2017/1/5.
 */

public class AboutUSFragment extends BaseFragment {
    private View aboutUSFragmentView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (aboutUSFragmentView == null) {
            aboutUSFragmentView = inflater.inflate(R.layout.fragment_aboutus, null);
        }
        return aboutUSFragmentView;
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        ViewGroup parent = (ViewGroup) aboutUSFragmentView.getParent();
        if (parent != null) {
            parent.removeView(aboutUSFragmentView);
        }
    }
}
