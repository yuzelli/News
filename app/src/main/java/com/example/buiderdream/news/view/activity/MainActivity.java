package com.example.buiderdream.news.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.buiderdream.news.R;
import com.example.buiderdream.news.base.BaseActivity;
import com.example.buiderdream.news.view.fragment.AboutUSFragment;
import com.example.buiderdream.news.view.fragment.ApplicationFragment;
import com.example.buiderdream.news.view.fragment.NewCollectionFragment;
import com.example.buiderdream.news.view.fragment.NewsFragment;
import com.slidingmenu.lib.SlidingMenu;

public class MainActivity extends BaseActivity {
    private SlidingMenu slidingMenu;
    private ImageView img_slidingMenu;
    private ImageView imgR_head;
    private TextView tv_news;
    private TextView tv_newCollection;
    private TextView tv_application;
    private TextView tv_aboutUs;
    private TextView tv_title;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    //定义数组来存放user的Fragment界面
    private Fragment fragmentArray[] = {new NewsFragment(),new  NewCollectionFragment(),new  ApplicationFragment(),new AboutUSFragment()};
   private String titleArray[] = {"新闻资讯","新闻收藏","精彩应用","关于我们"};
    private int currentFragmentIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
    }

    //--------------------------------抽象方法--------------------------------------
    @Override
    public void initView() {
        img_slidingMenu = (ImageView) this.findViewById(R.id.img_slidingMenu);
        tv_title = (TextView) this.findViewById(R.id.tv_title);
        View leftView = getLayoutInflater().inflate(R.layout.slidingmenu_left, null);
        slidingMenu = new SlidingMenu(getApplicationContext());
        slidingMenu.setMode(SlidingMenu.LEFT);   //滑动模式
        //  slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);// 设置触摸屏幕的模式（全屏滑）
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);// 设置触摸屏幕的模式（不滑）
        slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
        slidingMenu.setShadowDrawable(R.color.colorAccent);

        // 设置滑动菜单视图的宽度
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);//绑定activity
        //为侧滑菜单设置布局
        slidingMenu.setMenu(leftView);
        imgR_head = (ImageView) leftView.findViewById(R.id.imgR_head);
        tv_news = (TextView) leftView.findViewById(R.id.tv_news);
        tv_newCollection = (TextView) leftView.findViewById(R.id.tv_newCollection);
        tv_application = (TextView) leftView.findViewById(R.id.tv_application);
        tv_aboutUs = (TextView) leftView.findViewById(R.id.tv_aboutUs);

        //     获取Fragment的管理类FragmentManager
        manager = getSupportFragmentManager();
//        开启事务
        transaction = manager.beginTransaction();
        transaction.add(R.id.frame_content,fragmentArray[0],fragmentArray[0].getClass().getName());
        transaction.commit();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        img_slidingMenu.setOnClickListener(this);
        imgR_head.setOnClickListener(this);
        tv_news.setOnClickListener(this);
        tv_newCollection.setOnClickListener(this);
        tv_application.setOnClickListener(this);
        tv_aboutUs.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_slidingMenu:
                if (slidingMenu.isMenuShowing()) {
                    slidingMenu.showContent();
                } else {
                    slidingMenu.showMenu();
                }
                break;
            case R.id.imgR_head:
                break;
            case R.id.tv_news:
                replaceFragment(0);
                break;
            case R.id.tv_newCollection:
                replaceFragment(1);
                break;
            case R.id.tv_application:
                replaceFragment(2);
                break;
            case R.id.tv_aboutUs:
                replaceFragment(3);
                break;
            default:
                break;
        }
    }

    /**
     * 替换fragment
     * @param i
     */
    private void replaceFragment(int i) {
        tv_title.setText(titleArray[i]);
        if (currentFragmentIndex==i){
            return;
        }
        //     获取Fragment的管理类FragmentManager
        manager = getSupportFragmentManager();
//        开启事务
        transaction = manager.beginTransaction();
//        替换
        transaction.replace(R.id.frame_content,fragmentArray[i],fragmentArray[i].getClass().getName());
//        提交
        transaction.commit();
    }
}
