package com.example.buiderdream.news.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.example.buiderdream.news.R;
import com.example.buiderdream.news.adapter.NewsPagerAdapter;
import com.example.buiderdream.news.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/5.
 */

public class NewsFragment extends BaseFragment {
    private View newsFragmentView;
    private PagerSlidingTabStrip psts_tab;
    private ViewPager vp_fragment;
    private NewsPagerAdapter adapter;
    private Context context;
    private String [] titleArr ={"头条","新闻","财经","体育","娱乐","军事","教育","科技","NBA","股票","星座"};
    private List<String> titleList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (newsFragmentView==null){
            context = getActivity();
            newsFragmentView= inflater.inflate(R.layout.fragment_news,null);
            psts_tab = (PagerSlidingTabStrip) newsFragmentView.findViewById(R.id.psts_tab);
            vp_fragment = (ViewPager) newsFragmentView.findViewById(R.id.vp_fragment);
            adapter = new NewsPagerAdapter(getActivity().getSupportFragmentManager());
            initData();
            adapter.initData(titleList,fragmentList);
            vp_fragment.setAdapter(adapter);
            vp_fragment.setOffscreenPageLimit(6);
            psts_tab.setViewPager(vp_fragment);
        }
        return newsFragmentView;
    }

    private void initData() {
          titleList = new ArrayList<>();
          for (int i = 0 ; i < titleArr.length;i++){
              titleList.add(titleArr[i]);
          }
          fragmentList = new ArrayList<>();
        for (int i = 0 ; i < titleList.size();i++){
            NewsBrowserFragment nbf = new NewsBrowserFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type",i);
            nbf.setArguments(bundle);
            fragmentList.add(nbf);
        }
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        ViewGroup parent = (ViewGroup) newsFragmentView.getParent();
        if (parent!=null){
            parent.removeView(newsFragmentView);
        }
    }
}
