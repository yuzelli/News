package com.example.buiderdream.news.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.buiderdream.news.R;
import com.example.buiderdream.news.base.BaseFragment;

/**
 * Created by Administrator on 2017/1/5.
 */

public class ApplicationFragment extends BaseFragment {
    private View aApplicationFragmentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (aApplicationFragmentView == null) {
            aApplicationFragmentView = inflater.inflate(R.layout.fragment_application, null);
        }

        return aApplicationFragmentView;
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        ViewGroup parent = (ViewGroup) aApplicationFragmentView.getParent();
        if (parent!=null){
            parent.removeView(aApplicationFragmentView);
        }
    }
}
