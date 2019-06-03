package com.shenkangyun.asthmaproject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.shenkangyun.asthmaproject.Adapter.ClockIndexListViewAdapter;
import com.shenkangyun.asthmaproject.DBFolder.ClockDBUtil;
import com.shenkangyun.asthmaproject.HttpFolder.ClockModel;
import com.shenkangyun.asthmaproject.R;
import com.shenkangyun.asthmaproject.View.MyAnalogClock;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClockIndexActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolBar_title)
    TextView toolBarTitle;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.clock)
    MyAnalogClock clock;

    //添加闹铃
    private TextView mAddClock;
    //所有闹铃列表
    private ListView mListView;
    //所有闹铃集合
    private List<ClockModel> mListClock;
    //时间集合
    private List<String> mListTime;
    //标签集合
    private List<String> mListTag;
    //开关集合
    private List<String> mListSwitch;
    //mListClock加载器
    private ClockIndexListViewAdapter adapter;
    //记录当前的ListView的位置
    private int CURRENT_LISTVIEW_ITEM_POSITION = 0;
    //日期
    private TextView tv_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_index);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.mBlue));
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            toolBarTitle.setText("服药提醒");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        getAllClock();
        initView();


    }

    private void initView() {
        tv_date = findViewById(R.id.tv_time);
        mAddClock = findViewById(R.id.add_clock);
        mAddClock.setOnClickListener(this);
        mListView = findViewById(R.id.clock_listView);
        adapter = new ClockIndexListViewAdapter(this,
                mListTime,
                mListTag,
                mListSwitch);
        mListView.setAdapter(adapter);
        getSysDate();
    }

    /**
     * 获取所有的闹铃,并拿到时间/标签/开关的集合
     */
    private void getAllClock() {
        ClockDBUtil clockDBUtil = new ClockDBUtil(getApplicationContext());
        clockDBUtil.openDataBase();
        mListClock = clockDBUtil.getAllClock();
        clockDBUtil.closeDataBase();
        mListTime = new ArrayList<>();
        mListTag = new ArrayList<>();
        mListSwitch = new ArrayList<>();

        for (ClockModel info : mListClock) {
            mListTime.add(info.getTime());
            mListTag.add(info.getTag());
            mListSwitch.add(info.getmSwitch());
        }
    }

    /**
     * 添加闹钟
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_clock:
                Intent intent = new Intent(this, ClockInfoActivity.class);
                intent.putExtra("isAdd", true);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllClock();
        adapter = new ClockIndexListViewAdapter(this,
                mListTime,
                mListTag,
                mListSwitch);
        mListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //回到原来的位置
        mListView.setSelection(CURRENT_LISTVIEW_ITEM_POSITION);
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        //得到当前ListView可见部分的第一个位置
        CURRENT_LISTVIEW_ITEM_POSITION = mListView.getFirstVisiblePosition();
        MobclickAgent.onPause(this);
    }

    private void getSysDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String date = format.format(new Date());
        tv_date.setText(date);
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