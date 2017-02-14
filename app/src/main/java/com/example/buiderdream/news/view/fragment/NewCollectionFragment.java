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

public class NewCollectionFragment extends BaseFragment {
    private View newCollectionFragmentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (newCollectionFragmentView == null) {
            newCollectionFragmentView = inflater.inflate(R.layout.fragment_newcollection, null);
        }

        return newCollectionFragmentView;
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        ViewGroup parent = (ViewGroup) newCollectionFragmentView.getParent();
        if (parent!=null){
            parent.removeView(newCollectionFragmentView);
        }
    }
}
