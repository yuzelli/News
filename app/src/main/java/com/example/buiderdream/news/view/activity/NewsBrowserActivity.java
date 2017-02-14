package com.example.buiderdream.news.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buiderdream.news.R;
import com.example.buiderdream.news.base.BaseActivity;
import com.example.buiderdream.news.bean.New;

public class NewsBrowserActivity extends BaseActivity {
    private New.ResultBean.ListBean news;
    private ImageView img_back;
    private ImageView img_shared;

    private TextView tv_shared;
    private TextView tv_edit;
    private TextView tv_collection;
    private TextView tv_newTitle;
    private WebView web_content;

    private PopupWindow mPopWindow ;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_news_browser);
        news = (New.ResultBean.ListBean) getIntent().getSerializableExtra("news");
        super.onCreate(savedInstanceState);
        context = this;
    }
    private void showPopupWindow() {
        View contentView = LayoutInflater.from(NewsBrowserActivity.this).inflate(R.layout.popwindew_view,null);
        mPopWindow = new PopupWindow(contentView);
        mPopWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setContentView(contentView);//设置包含视图
        tv_shared = (TextView) contentView.findViewById(R.id.tv_shared);
        tv_edit = (TextView) contentView.findViewById(R.id.tv_edit);
        tv_collection = (TextView) contentView.findViewById(R.id.tv_collection);
        tv_shared.setOnClickListener(this);
        tv_edit.setOnClickListener(this);
        tv_collection.setOnClickListener(this);
        View rootview = LayoutInflater.from(NewsBrowserActivity.this).inflate(R.layout.activity_main, null);
        // 控制popupwindow点击屏幕其他地方消失
        mPopWindow.setBackgroundDrawable(this.getResources().getDrawable(
                R.drawable.background));
        mPopWindow.setOutsideTouchable(true);// 触摸popupwindow外部，popupwindow消失。这个要求你的popupwindow要有背景图片才可以成功，如上
        mPopWindow.setAnimationStyle(R.style.contextMenuAnim);//设置动画
        mPopWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);//设置模式，和Activity的一样，覆盖，调整大小。
        mPopWindow.showAsDropDown(img_shared);
    }

    public static void actionStart(Context context, New.ResultBean.ListBean news) {
        Intent intent = new Intent(context, NewsBrowserActivity.class);
        intent.putExtra("news", news);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        img_back = (ImageView) this.findViewById(R.id.img_back);
        img_shared = (ImageView) this.findViewById(R.id.img_shared);

        tv_newTitle = (TextView) this.findViewById(R.id.tv_newTitle);
        web_content = (WebView) this.findViewById(R.id.web_content);
    }

    @Override
    public void initData() {
        tv_newTitle.setText(news.getTitle());
        web_content.loadUrl(news.getWeburl());
        web_content.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        //启用支持javascript
        WebSettings settings = web_content.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    @Override
    public void initListener() {
        img_back.setOnClickListener(this);
        img_shared.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_back:
                finish();
                 break;
            case R.id.img_shared:
                showPopupWindow();
                break;
            case R.id.tv_shared:
                Toast.makeText(context,"分享",Toast.LENGTH_SHORT).show();
                mPopWindow.dismiss();
                break;
            case R.id.tv_edit:
                Toast.makeText(context,"评论",Toast.LENGTH_SHORT).show();
                mPopWindow.dismiss();
                break;
            case R.id.tv_collection:
                Toast.makeText(context,"收藏",Toast.LENGTH_SHORT).show();
                mPopWindow.dismiss();
                break;
            default:
                break;
        }

    }
}

