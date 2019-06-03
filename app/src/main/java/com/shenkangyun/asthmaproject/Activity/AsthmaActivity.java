package com.shenkangyun.asthmaproject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.shenkangyun.asthmaproject.Adapter.MyViewPagerAdapter;
import com.shenkangyun.asthmaproject.R;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AsthmaActivity extends AppCompatActivity {

    @BindView(R.id.toolBar_title)
    TextView toolBarTitle;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.textNumber)
    TextView textNumber;

    private List<View> viewList;
    private List<Integer> lists;
    private MyViewPagerAdapter myViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asthma);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.mBlue));
        ButterKnife.bind(this);
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            toolBarTitle.setText("哮喘管理");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        initView();
        initData();
    }

    private void initView() {
        viewList = new ArrayList<>();
        lists = new ArrayList<>();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent.getStringExtra("info").equals("knowledge")) {
            lists.add(R.drawable.diary1);
            lists.add(R.drawable.diary2);
            lists.add(R.drawable.diary3);
            lists.add(R.drawable.diary4);
            lists.add(R.drawable.diary5);
            lists.add(R.drawable.diary6);
            lists.add(R.drawable.diary7);
            lists.add(R.drawable.diary8);
            lists.add(R.drawable.diary9);
            lists.add(R.drawable.diary10);

            for (int i = 0; i < 10; i++) {
                viewList.add(LayoutInflater.from(this).inflate(R.layout.pager_image, null));
            }
        } else if (intent.getStringExtra("info").equals("manage")) {
            lists.add(R.drawable.diary11);
            lists.add(R.drawable.diary12);

            for (int i = 0; i < 2; i++) {
                viewList.add(LayoutInflater.from(this).inflate(R.layout.pager_image, null));
            }
        } else if (intent.getStringExtra("info").equals("medicine")) {
            lists.add(R.drawable.diary13);
            lists.add(R.drawable.diary14);

            for (int i = 0; i < 2; i++) {
                viewList.add(LayoutInflater.from(this).inflate(R.layout.pager_image, null));
            }
        }

        // 1.设置幕后item的缓存数目
        viewpager.setOffscreenPageLimit(2);
        myViewPagerAdapter = new MyViewPagerAdapter(viewList, lists);
        viewpager.setAdapter(myViewPagerAdapter);
        textNumber.setText("1/" + String.valueOf(viewList.size()));

        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                textNumber.setText(String.valueOf(position + 1) + "/" + String.valueOf(viewList.size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
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