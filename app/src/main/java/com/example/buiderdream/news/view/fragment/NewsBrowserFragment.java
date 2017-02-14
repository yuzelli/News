package com.example.buiderdream.news.view.fragment;

import android.content.Context;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buiderdream.news.R;
import com.example.buiderdream.news.base.BaseFragment;
import com.example.buiderdream.news.bean.New;
import com.example.buiderdream.news.constants.ConstantUtils;
import com.example.buiderdream.news.utils.CommonAdapter;
import com.example.buiderdream.news.utils.OkHttpClientManager;
import com.example.buiderdream.news.utils.ViewHolder;
import com.example.buiderdream.news.view.activity.NewsBrowserActivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by Administrator on 2017/1/6.
 */

public class NewsBrowserFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,AdapterView.OnItemClickListener {
    private View newsBrowserFragment;
    private ListView lv_news;
    private View bottomView;
    private String [] titleArr ={"头条","新闻","财经","体育","娱乐","军事","教育","科技","NBA","股票","星座"};
    private String title;
    private SwipeRefreshLayout sRef_content;

    private Context context;
    private List<New.ResultBean.ListBean>  newList;
    private CommonAdapter<New.ResultBean.ListBean> adapter;
    private boolean bottomViewClick = true;  //底部加载更多是否可以点击
    private NewsBrowserHandler handler;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (newsBrowserFragment==null){
            context = getActivity();
            handler = new NewsBrowserHandler();
            newsBrowserFragment = inflater.inflate(R.layout.fragment_newbrowser,null);
            lv_news = (ListView) newsBrowserFragment.findViewById(R.id.lv_news);
            sRef_content = (SwipeRefreshLayout) newsBrowserFragment.findViewById(R.id.sRef_content);
            sRef_content.setOnRefreshListener(this);
            sRef_content.setColorSchemeColors
                    (getResources().getColor(android.R.color.holo_blue_bright),
                            getResources().getColor(android.R.color.holo_green_light),
                            getResources().getColor(android.R.color.holo_orange_light),
                            getResources().getColor(android.R.color.holo_red_light));

            title = titleArr[getArguments().getInt("type")];
            doRequestData(page);
            newList = new ArrayList<>();
            //给listView添加底部视图
            bottomView = getActivity().getLayoutInflater().inflate(R.layout.view_listview_bottom, null);
            TextView tv_more = (TextView) bottomView.findViewById(R.id.tv_more);
            tv_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bottomViewClick){
                        doRequestData(page);
                        bottomViewClick = false;
                        Toast.makeText(context,"bottom",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            lv_news.addFooterView(bottomView);
        }
        return newsBrowserFragment;
    }

    private void doRequestData(int start) {
        OkHttpClientManager manager = OkHttpClientManager.getInstance();
        Map<String,String> map =new HashMap<>();
        map.put("channel",title);
        map.put("num","15");
        map.put("start",page*15+"");
        map.put("appkey",ConstantUtils.JISHU_APPKEY);
        String  url = OkHttpClientManager.attachHttpGetParams(ConstantUtils.JISHU_NEW_NETWORK_ADDRESS,map);
        manager.getAsync(url, new OkHttpClientManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Toast.makeText(context, "加载网路数据失败！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson = new Gson();
                New news =gson.fromJson(result,New.class);
                newList.addAll(news.getResult().getList());
                handler.sendEmptyMessage(ConstantUtils.NEWS_BROWERRSER_GET_DATA);
            }
        });
    }

    @Override
    public void onDestroyView() {
        ViewGroup parent = (ViewGroup) newsBrowserFragment.getParent();

        if (parent!=null){
            parent.removeView(newsBrowserFragment);
        }
        super.onDestroyView();
    }

    @Override
    public void onRefresh() {
        doRequestData(page);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        NewsBrowserActivity.actionStart(context,newList.get(i));
    }

    class NewsBrowserHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ConstantUtils.NEWS_BROWERRSER_GET_DATA:
                    bottomViewClick = true;
                    page = page+1;
                    initListView();
                    break;
                default:
                    break;
            }
        }
    }

    private void initListView() {
        adapter = new CommonAdapter<New.ResultBean.ListBean>(context,newList,R.layout.item_news_content) {
            @Override
            public void convert(ViewHolder helper, New.ResultBean.ListBean item) {
                helper.setImageByUrl(R.id.img_newPic,item.getPic());
                helper.setText(R.id.tv_title,item.getTitle());
                helper.setText(R.id.tv_author,item.getSrc());
                helper.setText(R.id.tv_time,item.getTime());
            }
        };
        lv_news.setAdapter(adapter);
        lv_news.setOnItemClickListener(this);
    }
}
