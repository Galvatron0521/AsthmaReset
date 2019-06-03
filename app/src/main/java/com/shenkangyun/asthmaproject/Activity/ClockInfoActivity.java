package com.shenkangyun.asthmaproject.Activity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;

import com.jaeger.library.StatusBarUtil;
import com.shenkangyun.asthmaproject.Adapter.ClockInfoRingAdapter;
import com.shenkangyun.asthmaproject.DBFolder.ClockDBUtil;
import com.shenkangyun.asthmaproject.HttpFolder.ClockModel;
import com.shenkangyun.asthmaproject.R;
import com.shenkangyun.asthmaproject.Service.AlarmReceiver;
import com.shenkangyun.asthmaproject.Utils.ClockGetNextAlarm;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClockInfoActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.toolBar_title)
    TextView toolBarTitle;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    //是否为新建闹钟
    private boolean isAdd;
    private Intent intent;
    //时钟
    private TimePicker timePicker;
    //日历
    private Calendar calendar;
    //闹钟管理器
    private AlarmManager alarmManager;
    //保存/删除
    private Button button;
    //更新闹铃按钮
    private Button mButtonUpdate;
    //小时
    private int mHourOfDay;
    //分钟
    private int mMinute;
    //重复
    private CheckBox cb_sun, cb_mon, cb_tues, cb_wed, cb_thurs, cb_fri, cb_sat;
    //
    private List<CheckBox> mListCheckBox;
    //标签
    private TextView mTag;
    //间隔
    private TextView mSpace;
    //铃音
    private TextView mRing;
    //闹钟实例
    private ClockModel model;
    //当前闹铃请求码最大值
    private int requestCode;
    //标签的dialog
    private AlertDialog mTagDialog;
    //标签布局
    private LinearLayout mTagLinearLayout;
    //间隔时间的popupwindow
    private PopupWindow mSpacePopupWindow;
    //间隔时间布局
    private LinearLayout mSpaceLinearLayout;
    //铃声dialog
    private AlertDialog mRingDialog;
    //铃声布局
    private LinearLayout mRingLinearLayout;
    //铃声列表加载器
    private ClockInfoRingAdapter mRingAdapter;
    //开关
    public static final String KEY_ON = "0";
    public static final String KEY_OFF = "1";
    //不同闹铃间的标识
    private int uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_info);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.mBlue));
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            toolBarTitle.setText("提醒设置");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        requestCode = mGetRequestCode();
        myGetIntent();
        initView();

    }

    /**
     * 拿到当前存储的所有_id的最大值+1后作为下个闹铃的请求码
     */
    private int mGetRequestCode() {
        int uuid = 0;
        ClockDBUtil clockDBUtil = new ClockDBUtil(getApplicationContext());
        clockDBUtil.openDataBase();
        uuid = clockDBUtil.getLastId() + 1;
        clockDBUtil.closeDataBase();

        return uuid;
    }

    private void myGetIntent() {
        isAdd = true;
        intent = getIntent();
        isAdd = intent.getBooleanExtra("isAdd", true);
        if (isAdd == false) {
            model = intent.getParcelableExtra("clockInstance");
        }

    }

    private void initView() {

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        calendar = Calendar.getInstance();
        timePicker = findViewById(R.id.timePiker);
        timePicker.setIs24HourView(true);

        cb_sun = findViewById(R.id.sun);
        cb_mon = findViewById(R.id.mon);
        cb_tues = findViewById(R.id.tues);
        cb_wed = findViewById(R.id.wed);
        cb_thurs = findViewById(R.id.thurs);
        cb_fri = findViewById(R.id.fri);
        cb_sat = findViewById(R.id.sat);
        mListCheckBox = new ArrayList<>();
        mListCheckBox.add(cb_sun);
        mListCheckBox.add(cb_mon);
        mListCheckBox.add(cb_tues);
        mListCheckBox.add(cb_wed);
        mListCheckBox.add(cb_thurs);
        mListCheckBox.add(cb_fri);
        mListCheckBox.add(cb_sat);

        mTag = findViewById(R.id.mtag);
        mSpace = findViewById(R.id.mspace);
        mRing = findViewById(R.id.morning);
        button = findViewById(R.id.clock_bt);
        mTagLinearLayout = findViewById(R.id.tag_ll);
        mRingLinearLayout = findViewById(R.id.ring_ll);
        mSpaceLinearLayout = findViewById(R.id.space_ll);
        mButtonUpdate = findViewById(R.id.clock_bt_update);
        mTagLinearLayout.setOnClickListener(this);
        mRingLinearLayout.setOnClickListener(this);
        mSpaceLinearLayout.setOnClickListener(this);
        if (isAdd == true) {
            //保存
            button.setText(getString(R.string.clock_save));
        } else {
            //删除
            button.setText(getString(R.string.clock_delete));
            button.setBackgroundResource(R.color.red);
            mButtonUpdate.setVisibility(View.VISIBLE);
            setClockToView();
        }
    }

    /**
     * 如果是编辑，则把传过来的实例属性赋值给View
     */
    private void setClockToView() {
        timePicker.setCurrentHour(Integer.parseInt(model.getTime().substring(0, 2)));
        timePicker.setCurrentMinute(Integer.parseInt(model.getTime().substring(3, 5)));

        for (int j = 0; j < model.getRepeat().split(",").length; j++) {
            for (int i = 0; i < mListCheckBox.size(); i++) {
                if (mListCheckBox.get(i).getTag().equals(model.getRepeat().split(",")[j])) {
                    mListCheckBox.get(i).setChecked(true);
                }
            }
        }
        mTag.setText(model.getTag());
        mSpace.setText(setSpaceText(model.getSpace()));
        mSpace.setTag(model.getSpace());
        mRing.setText(model.getRing());

    }

    /**
     * 间隔显示的文字
     *
     * @param space
     * @return
     */
    private String setSpaceText(String space) {
        if (space.equals("0")) {
            space = getResources().getString(R.string.space1);
        } else if (space.equals("3")) {
            space = getResources().getString(R.string.space2);
        } else if (space.equals("6")) {
            space = getResources().getString(R.string.space3);
        } else if (space.equals("12")) {
            space = getResources().getString(R.string.space4);
        }
        return space;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.clock_bt:
                if (isAdd) {
                    //保存闹铃
                    getClockInstance();
                    setAlarm(requestCode);
                    saveClockToData(model);

                    this.finish();
                } else {
                    //删除闹铃
                    ClockDBUtil clockDBUtil = new ClockDBUtil(this);
                    clockDBUtil.openDataBase();
                    int result = clockDBUtil.deleteOneClock(model.getId());
                    clockDBUtil.closeDataBase();
                    if (result == 1) {
                        cancelAlarm(Integer.parseInt(model.getUuid()));
                        ClockInfoActivity.this.finish();
                    }
                }
                break;
            case R.id.clock_bt_update:
                //更新闹铃
                int id = model.getId();
                uuid = Integer.parseInt(model.getUuid());

                cancelAlarm(uuid);
                getClockInstance();//此处如果不先赋值下，下面设置闹铃时的重复日期就会是上次的重复日期
                setAlarm(uuid);
                this.finish();

                ClockDBUtil clockDBUtil = new ClockDBUtil(this);
                clockDBUtil.openDataBase();
                clockDBUtil.updateClock(getClockInstance(), id + "");
                clockDBUtil.closeDataBase();

                break;

            case R.id.tag_ll:
                //标签
                LayoutInflater inflater = LayoutInflater.from(this);
                View view = inflater.inflate(R.layout.tag_dialog_layout, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                final EditText mTagTv = view.findViewById(R.id.tag_et);
                TextView mCancel = view.findViewById(R.id.cancel_tv);
                TextView mOk = view.findViewById(R.id.ok_tv);

                builder.setCancelable(false);
                mTagDialog = builder.show();
                mTagDialog.getWindow().setContentView(view);
                mTagDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

                mCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mTagDialog.dismiss();
                    }
                });
                mOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mTag.setText(mTagTv.getText());
                        mTagDialog.dismiss();
                    }
                });
                break;
            case R.id.space_ll:
                //间隔时间
                LayoutInflater inflater2 = LayoutInflater.from(this);
                View view2 = inflater2.inflate(R.layout.space_popupwindow_layout, null);

                final TextView mSpaceTextView1 = view2.findViewById(R.id.space_tv_1);
                final TextView mSpaceTextView2 = view2.findViewById(R.id.space_tv_2);
                final TextView mSpaceTextView3 = view2.findViewById(R.id.space_tv_3);
                final TextView mSpaceTextView4 = view2.findViewById(R.id.space_tv_4);

                mSpaceTextView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSpace.setText(mSpaceTextView1.getText());
                        mSpace.setTag(mSpaceTextView1.getTag());
                        mSpacePopupWindow.dismiss();
                    }
                });
                mSpaceTextView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSpace.setText(mSpaceTextView2.getText());
                        mSpace.setTag(mSpaceTextView2.getTag());
                        mSpacePopupWindow.dismiss();
                    }
                });
                mSpaceTextView3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSpace.setText(mSpaceTextView3.getText());
                        mSpace.setTag(mSpaceTextView3.getTag());
                        mSpacePopupWindow.dismiss();
                    }
                });
                mSpaceTextView4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSpace.setText(mSpaceTextView4.getText());
                        mSpace.setTag(mSpaceTextView4.getTag());
                        mSpacePopupWindow.dismiss();
                    }
                });

                mSpacePopupWindow = new PopupWindow(this);
                //设置宽高
                mSpacePopupWindow.setWidth((getWindowManager()
                        .getDefaultDisplay().getWidth() - Dp2Px(this, 15 * 2f)) / 2);
                mSpacePopupWindow.setHeight(Dp2Px(this, 48 * 4f));
                //填充
                mSpacePopupWindow.setContentView(view2);
                //设置背景透明
                mSpacePopupWindow.setBackgroundDrawable(new ColorDrawable(0));
                //外部可控
                mSpacePopupWindow.setOutsideTouchable(true);
                //加了下面这行，onItemClick才好用
                mSpacePopupWindow.setFocusable(true);
                //在控件之上
                int[] location = new int[2];
                mSpaceLinearLayout.getLocationOnScreen(location);
                mSpacePopupWindow.showAtLocation(mSpaceLinearLayout,
                        Gravity.NO_GRAVITY, location[0] + mSpacePopupWindow.getWidth(),
                        location[1] - mSpacePopupWindow.getHeight());

                break;
            case R.id.ring_ll:
                //铃声
                LayoutInflater inflater3 = LayoutInflater.from(this);
                View view3 = inflater3.inflate(R.layout.ring_dialog_layout, null);
                AlertDialog.Builder builder3 = new AlertDialog.Builder(this);

                ListView mRingListView = view3.findViewById(R.id.ring_list_view);
                final List<String> mListRing = new ArrayList<>();

                mListRing.add(getResources().getString(R.string.ring1));
                mListRing.add(getResources().getString(R.string.ring2));
                mListRing.add(getResources().getString(R.string.ring3));
                mListRing.add(getResources().getString(R.string.ring4));
                mListRing.add(getResources().getString(R.string.ring5));
                mListRing.add(getResources().getString(R.string.ring6));
                mListRing.add(getResources().getString(R.string.ring7));
                mListRing.add(getResources().getString(R.string.ring8));
                mListRing.add(getResources().getString(R.string.ring9));
                mListRing.add(getResources().getString(R.string.ring10));

                mRingAdapter = new ClockInfoRingAdapter(this, mListRing);
                mRingListView.setAdapter(mRingAdapter);
                mRingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mRing.setText(mListRing.get(position));
                        mRingDialog.dismiss();
                    }
                });

                mRingDialog = builder3.show();
                mRingDialog.getWindow().setContentView(view3);
                mRingDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                break;
        }
    }

    /**
     * dp 转化px
     *
     * @param context
     * @param dp
     * @return
     */
    public int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 删除闹铃
     *
     * @param uuid
     */
    public void cancelAlarm(int uuid) {
        Intent intent = new Intent(ClockInfoActivity.this,
                AlarmReceiver.class);
        intent.putExtra("uuid", uuid);
        PendingIntent pendingIntent = PendingIntent
                .getBroadcast(ClockInfoActivity.this, uuid,
                        intent, 0);
        alarmManager.cancel(pendingIntent);
    }

    /**
     * 设置闹铃
     *
     * @param uuid
     */
    public void setAlarm(int uuid) {
        // 建立Intent和PendingIntent来调用闹钟管理器
        Intent intent = new Intent(ClockInfoActivity.this, AlarmReceiver.class);
        intent.setAction(AlarmReceiver.ALARM_ACTION);
        intent.putExtra("uuid", uuid);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ClockInfoActivity.this, uuid, intent, 0);
        String flag = KEY_OFF;
        //看是否有间隔时间
        if (!model.getSpace().equals("0")) {//有间隔时间
            //计算今晚凌晨的时间
            long nextDayTime = ClockGetNextAlarm.getTonightLastTime(System.currentTimeMillis());
            //第一次响铃时间的毫秒
            long nextCurrentTimeMillis = ClockGetNextAlarm.getFirstAlarmTime(System.currentTimeMillis(),
                    model.getTime(), model.getSpace());
            if (nextCurrentTimeMillis <= nextDayTime) {//第二次响铃小于凌晨时间（在当天）
                //闹铃响
                alarmManager.set(AlarmManager.RTC_WAKEUP, nextCurrentTimeMillis, pendingIntent);
                ClockGetNextAlarm.L("info下次闹铃时间是", nextCurrentTimeMillis);
                flag = KEY_ON;
            } else {//第二次响铃大于凌晨时间（不在当天，明天）
                //每周可以重复响铃的次数
                int counts = 7 * 24 / (Integer.parseInt(model.getSpace()));
                for (int i = 0; i < counts; i++) {
                    //下次响铃时间的毫秒
                    if (i == 0) {
                        //已经是下次响铃时间了
                        nextCurrentTimeMillis += 0;
                    } else {
                        nextCurrentTimeMillis += Integer.parseInt(model.getSpace()) * AlarmManager.INTERVAL_HOUR;
                    }
                    //下次响铃时间所在的日期是否包含在重复的日期里
                    boolean bl = ClockGetNextAlarm.isContainsInRepeat(nextCurrentTimeMillis, model.getRepeat());
                    if (bl) {//包含
                        //设置响铃时间为此时的时间，并且跳出循环
                        alarmManager.set(AlarmManager.RTC_WAKEUP, nextCurrentTimeMillis, pendingIntent);
                        ClockGetNextAlarm.L("info下次闹铃时间是", nextCurrentTimeMillis);
                        flag = KEY_ON;
                        break;
                    } else {//不包含
                        //继续循环
                        flag = KEY_OFF;
                    }
                }
            }
        } else {//没有间隔时间

            // 设置闹钟
            calendar.setTimeInMillis(ClockGetNextAlarm.getNextAlarmTime(
                    ClockGetNextAlarm.DATE_MODE_WEEK,
                    model.getRepeat(),
                    model.getTime()));
            if (calendar.getTimeInMillis() != 0) {//!=0 说明有重复日期
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                ClockGetNextAlarm.L("info下次闹铃时间是", calendar.getTimeInMillis());
                flag = KEY_ON;
            } else {//=0 说明没有重复日期
                // 设置日历的时间，主要是让日历的年月日和当前同步
                calendar.setTimeInMillis(System.currentTimeMillis());
                // 设置日历的小时和分钟
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                // 将秒和毫秒设置为0
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                if (calendar.getTimeInMillis() > System.currentTimeMillis()) {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    ClockGetNextAlarm.L("info下次闹铃时间是", calendar.getTimeInMillis());
                    flag = KEY_ON;
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP,
                            calendar.getTimeInMillis() + AlarmManager.INTERVAL_DAY,
                            pendingIntent);
                    ClockGetNextAlarm.L("info下次闹铃时间是", calendar.getTimeInMillis() + AlarmManager.INTERVAL_DAY);
                    flag = KEY_ON;
                }
            }
        }
        model.setmSwitch(flag);
    }

    /**
     * 给闹钟的各个属性赋值,用以保存
     *
     * @return
     */
    private ClockModel getClockInstance() {

        if (isAdd) {
            model = new ClockModel();
            model.setUuid(requestCode + "");
            model.setmSwitch(KEY_ON);
        } else {
            model.setUuid(uuid + "");
        }
        model.setTime(intToStr(timePicker.getCurrentHour()) + ":" + intToStr(timePicker.getCurrentMinute()));
        model.setRepeat(mGetTag(cb_sun) + "," +
                mGetTag(cb_mon) + "," +
                mGetTag(cb_tues) + "," +
                mGetTag(cb_wed) + "," +
                mGetTag(cb_thurs) + "," +
                mGetTag(cb_fri) + "," +
                mGetTag(cb_sat));
        model.setTag(mTag.getText().toString());
        model.setSpace(mSpace.getTag().toString());
        model.setRing(mRing.getText().toString());
        return model;
    }

    /**
     * CheckBox是否选中，重复日期选中了哪天
     *
     * @param cb
     * @return
     */
    private String mGetTag(CheckBox cb) {

        String tag;
        if (cb.isChecked() == false) {
            tag = "7";
        } else {
            tag = cb.getTag().toString();
        }
        return tag;
    }

    /**
     * 时间补全（2:7--->02:07）
     *
     * @param ii
     * @return
     */
    private String intToStr(int ii) {
        String i = ii + "";
        String s = "0";
        if (i.length() == 1) {
            s += i;
        } else {
            s = i;
        }
        return s;
    }

    /**
     * 保存闹铃
     */
    private void saveClockToData(ClockModel model) {
        ClockDBUtil clockDBUtil = new ClockDBUtil(getApplicationContext());
        clockDBUtil.openDataBase();
        clockDBUtil.addClock(model);
        clockDBUtil.closeDataBase();
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