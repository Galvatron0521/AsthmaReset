package com.shenkangyun.asthmaproject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.shenkangyun.asthmaproject.R;
import com.shenkangyun.asthmaproject.View.ProgressWebView;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseWebActivity extends AppCompatActivity {

    @BindView(R.id.toolBar_title)
    TextView toolBarTitle;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.webView)
    ProgressWebView webView;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_web);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.mBlue));
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            toolBarTitle.setText("哮喘管理");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        initDate();
        initView();
    }

    private void initDate() {
        Intent intent = getIntent();
        url = intent.getStringExtra("URL");
    }

    private void initView() {
        webView.loadUrl(url);
        //设置支持js代码
        webView.getSettings().setJavaScriptEnabled(true);
        //设置支持插件
        //webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        //设置允许访问文件数据
        webView.getSettings().setAllowFileAccess(true);
        //设置是否支持插件
        //webView.getSettings().setPluginsEnabled(true);
        //支持缩放
        webView.getSettings().setSupportZoom(true);
        //支持缓存
        webView.getSettings().setAppCacheEnabled(true);
        //设置缓存模式
        //webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //设置此属性，可任意比例缩放
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
    }

    private void callHiddenWebViewMethod(String name) {
        if (webView != null) {
            try {
                Method method = WebView.class.getMethod(name);
                method.invoke(webView);
            } catch (NoSuchMethodException e) {

            } catch (IllegalAccessException e) {

            } catch (InvocationTargetException e) {

            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.pauseTimers();

        if (isFinishing()) {
            webView.loadUrl("about:blank");
            setContentView(new FrameLayout(this));
        }
        callHiddenWebViewMethod("onPause");
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        callHiddenWebViewMethod("onResume");
        MobclickAgent.onResume(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
