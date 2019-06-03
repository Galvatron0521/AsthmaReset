package com.shenkangyun.asthmaproject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.shenkangyun.asthmaproject.Adapter.VoteSubmitAdapter;
import com.shenkangyun.asthmaproject.BeanFolder.Survey;
import com.shenkangyun.asthmaproject.BeanFolder.VoteSubmitItem;
import com.shenkangyun.asthmaproject.R;
import com.shenkangyun.asthmaproject.Utils.DataLoader;
import com.shenkangyun.asthmaproject.Utils.DateUtil;
import com.shenkangyun.asthmaproject.Utils.ViewPagerScroller;
import com.shenkangyun.asthmaproject.View.VoteSubmitViewPager;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionnaireActivity extends AppCompatActivity {

    @BindView(R.id.toolBar_title)
    TextView toolBarTitle;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.vote_submit_viewpager)
    VoteSubmitViewPager voteSubmitViewpager;

    private VoteSubmitAdapter pagerAdapter;
    private List<View> viewItems = new ArrayList<>();
    private ArrayList<VoteSubmitItem> dataItems = new ArrayList<>();
    private ArrayList<Integer> scoresItems = new ArrayList<>();

    private int s = 0;
    private int[] scoreArr;
    private Survey survey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
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
        toolBarTitle.setText("问卷调查");
        survey = new Survey();
    }

    private void initData() {
        dataItems = new DataLoader(this).getTestData();
        for (int i = 0; i < dataItems.size(); i++) {
            viewItems.add(getLayoutInflater().inflate(R.layout.vote_submit_viewpager_item, null));
        }
        scoreArr = new int[viewItems.size()];
        pagerAdapter = new VoteSubmitAdapter(this, viewItems, dataItems, scoresItems, new VoteSubmitAdapter.OnClickCallBack() {
            @Override
            public void OnClick(int position, Map<Integer, Integer> scoreMaps, Map<Integer, Boolean> map, String[] answer) {
                if (position < viewItems.size() - 1) {
                    setCurrentView(position + 1);
                }
                if (position + 1 == viewItems.size()) {
                    //预留 没有点击的问题，返回该问题界面
                    for (int i = 0; i < 5; i++) {
                        if (!map.containsKey(i)) {
                            Toast.makeText(QuestionnaireActivity.this, "请选择第" + i + 1 + "问题", Toast.LENGTH_SHORT).show();
                            voteSubmitViewpager.setCurrentItem(i);
                            return;
                        }
                    }
                    if (scoreMaps.size() >= 5) {
                        scoreArr[4] = scoreMaps.get(position);
                    }
                    Toast.makeText(QuestionnaireActivity.this, "感谢您完成问卷调查!", Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < scoreArr.length; i++) {
                        setCurrentScore(scoreArr[i]);
                    }
                    s = 0;
                    for (int i = 0; i < scoresItems.size(); i++) {
                        s = s + scoresItems.get(i);
                    }
                    String string = "";

                    for (int k = 0; k < answer.length; k++) {
                        if (k == 0) {
                            string = answer[k];
                        } else if (k != answer.length - 1) {
                            string = string + "," + answer[k];
                        } else {
                            string = string + "," + answer[k];
                        }
                    }
                    survey.setAnswer(string);
                    survey.setDate(DateUtil.getDateFormat(new Date()));
                    survey.setScore(String.valueOf(s));

                    if (scoreMaps.size() >= 5) {
                        if (s == 25) {
                            scoresItems.clear();
                            Intent intent = new Intent(QuestionnaireActivity.this, MyAlertDialog.class);
                            intent.putExtra("msg", "评估分数：" + s + "\n" + "评估结果：控制良好 在过去4周内，您的哮喘已得到完全控制。您没有哮喘症状，您的生活也不受哮喘所限制。");
                            intent.putExtra("title", "ACT问卷得分");
                            startActivityForResult(intent, 0);

                        } else if (20 <= s && s < 25) {
                            scoresItems.clear();
                            Intent intent = new Intent(QuestionnaireActivity.this, MyAlertDialog.class);
                            intent.putExtra("msg", "评估分数：" + s + "\n" + "评估结果：基本控制 在过去4周内，您的哮喘已得到良好控制，但还没有完全控制。您的医生也许可以帮助您得到完全控制。");
                            intent.putExtra("title", "ACT问卷得分");
                            startActivityForResult(intent, 0);
                        } else if (s < 20) {
                            scoresItems.clear();
                            Intent intent = new Intent(QuestionnaireActivity.this, MyAlertDialog.class);
                            intent.putExtra("msg", "评估分数：" + s + "\n" + "评估结果：未得到控制 在过去4周内，您的哮喘可能没有得到控制。您的医生可以帮您制定一个哮喘管理计划帮助您改善哮喘控制。");
                            intent.putExtra("title", "ACT问卷得分");
                            startActivityForResult(intent, 0);
                        }
                    }
                } else {
                    if (position + 1 == viewItems.size() - 5) {
                        if (true) {
                            if (!map.containsKey(position)) {
                                Toast.makeText(QuestionnaireActivity.this, "请选择选项", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    } else if (position + 1 == viewItems.size() - 4) {
                        if (true) {
                            if (!map.containsKey(position)) {
                                Toast.makeText(QuestionnaireActivity.this, "请选择选项", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        if (scoreMaps.size() >= 1) {
                            scoreArr[0] = scoreMaps.get(position);
                        }
                    } else if (position + 1 == viewItems.size() - 3) {
                        if (true) {
                            if (!map.containsKey(position)) {
                                Toast.makeText(QuestionnaireActivity.this, "请选择选项", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        if (scoreMaps.size() >= 2) {
                            scoreArr[1] = scoreMaps.get(position);
                        }
                    } else if (position + 1 == viewItems.size() - 2) {
                        if (true) {
                            if (!map.containsKey(position)) {
                                Toast.makeText(QuestionnaireActivity.this, "请选择选项", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        if (scoreMaps.size() >= 3) {
                            scoreArr[2] = scoreMaps.get(position);
                        }
                    } else if (position + 1 == viewItems.size() - 1) {
                        if (true) {
                            if (!map.containsKey(position)) {
                                Toast.makeText(QuestionnaireActivity.this, "请选择选项", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        if (scoreMaps.size() >= 4) {
                            scoreArr[3] = scoreMaps.get(position);
                        }
                    }
                }
            }
        });
        voteSubmitViewpager.setAdapter(pagerAdapter);
        voteSubmitViewpager.getParent().requestDisallowInterceptTouchEvent(false);
        //initViewPagerScroll();
        voteSubmitViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             * 页面切换后调用
             * position  新的页面位置
             */
            @Override
            public void onPageSelected(int position) {
                voteSubmitViewpager.setCurrentItem(position);

            }

            /**
             * 页面正在滑动的时候，回调
             */
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            /**
             * 当页面状态发生变化的时候，回调
             */
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 1) {
            finish();
        }
    }

    /**
     * 设置ViewPager的滑动速度
     */
    private void initViewPagerScroll() {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            ViewPagerScroller scroller = new ViewPagerScroller(voteSubmitViewpager.getContext());
            mScroller.set(voteSubmitViewpager, scroller);
        } catch (NoSuchFieldException e) {

        } catch (IllegalArgumentException e) {

        } catch (IllegalAccessException e) {

        }
    }

    /**
     * @param index 根据索引值切换页面
     */
    public void setCurrentView(int index) {
        voteSubmitViewpager.setCurrentItem(index);

    }

    public void setCurrentScore(Integer score) {
        scoresItems.add(score);
    }


    public void onClick(View view) {
        this.finish();
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
