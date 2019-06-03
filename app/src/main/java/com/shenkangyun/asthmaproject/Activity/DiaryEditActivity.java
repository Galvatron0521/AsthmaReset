package com.shenkangyun.asthmaproject.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.shenkangyun.asthmaproject.BeanFolder.DiaryEntity;
import com.shenkangyun.asthmaproject.HttpFolder.DiaryModel;
import com.shenkangyun.asthmaproject.R;
import com.shenkangyun.asthmaproject.Utils.SingleDiary;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiaryEditActivity extends AppCompatActivity {

    @BindView(R.id.weekSelect_ID)
    TextView weekSelectID;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.add_diary)
    ImageView addDiary;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.floating_ID)
    FloatingActionButton floatingID;

    private List<DiaryEntity> viewList;
    public View contentView;
    public PopupWindow popupWindow;
    public int index1 = 0;
    private List<String> listYaoWuName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_edit);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.mBlue));
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        initView();
    }

    private void initView() {
        listYaoWuName = new ArrayList<>();
        listYaoWuName.add("硫酸沙丁胺醇气雾剂");
        listYaoWuName.add("吸入用异丙托溴铵溶液");
        listYaoWuName.add("塞托溴铵粉雾剂（含吸入器）");
        listYaoWuName.add("氨茶碱注射液");
        listYaoWuName.add("茶碱缓释胶囊Ⅱ");
        listYaoWuName.add("吸入用布地奈德混悬液");
        listYaoWuName.add("布地奈德福莫特罗粉吸入剂");
        listYaoWuName.add("沙美特罗替卡松粉吸入剂");
        listYaoWuName.add("孟鲁司特钠片");
        listYaoWuName.add("注射用甲泼尼龙琥珀酸钠");
        DiaryModel diaryModel = new DiaryModel();
    }

    @OnClick({R.id.add_diary, R.id.floating_ID})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_diary:
                if (viewList == null) {
                    Intent intent = new Intent(DiaryEditActivity.this, DiaryActivity.class);
                    intent.putExtra("week", 1);
                    SingleDiary.getInstance().setViewList(viewList);
                    startActivityForResult(intent, 0);
                } else {
                    Intent intent = new Intent(DiaryEditActivity.this, DiaryActivity.class);
                    intent.putExtra("week", viewList.size() + 1);
                    SingleDiary.getInstance().setViewList(viewList);
                    startActivityForResult(intent, 0);
                }
                break;
            case R.id.floating_ID:
                DiaryModel diaryModel = new DiaryModel();
                break;
        }
    }

    private void inputTitleDialog(final CallBackString callBackString) {

        final EditText inputServer = new EditText(this);
        inputServer.setFocusable(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name)).setIcon(
                R.drawable.icon_feedback).setView(inputServer).setNegativeButton("取消", null);
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
//                                 String inputName = inputServer.getText().toString();
                        callBackString.callBackString(inputServer.getText().toString());
                    }
                });
        builder.show();
    }

    PagerAdapter pagerAdapter = new PagerAdapter() {

        View pageView;

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {

            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {


            container.removeView((View) object);

        }

        @Override
        public int getItemPosition(Object object) {

            return super.getItemPosition(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            //return titleList.get(position);//直接用适配器来完成标题的显示，所以从上面可以看到，我们没有使用PagerTitleStrip。当然你可以使用。
            return "1111111";

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {


            pageView = initView(viewList.get(position));
            container.addView(pageView);

            return pageView;
        }

    };

    public void getInfo(List<DiaryEntity> diaryEntityList) {
        weekSelectID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initWeekPop(v, 100);
            }
        });

        List<DiaryEntity> list = diaryEntityList;
        viewList = diaryEntityList;
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(viewList.size());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                DiaryEntity diaryEntity = viewList.get(position);
                weekSelectID.setText("第" + diaryEntity.getWeek() + "周");
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public int dip2px(float dpValue) {
        final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void initWeekPop(View v, int flag) {
        if (popupWindow == null) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(this);
            contentView = mLayoutInflater.inflate(R.layout.week_popwindow_listview, null);
            ListView listView = contentView.findViewById(R.id.popWindow_list_id);
            listView.setAdapter(new listViewAdapter());
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    viewPager.setCurrentItem(position);
                    popupWindow.dismiss();
                    DiaryEntity diaryEntityPop = viewList.get(position);
                    weekSelectID.setText("第" + diaryEntityPop.getWeek() + "周");
                }
            });
            popupWindow = new PopupWindow(contentView, dip2px(100), 400);
        }

        ColorDrawable cd = new ColorDrawable(0x000000);
        popupWindow.setBackgroundDrawable(cd);

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(v, 0, 5);
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        popupWindow.update();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    public void initMedicinePop(View v, int flag, final CallBackString callBackString) {
        popupWindow = null;
        if (popupWindow == null) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(this);
            contentView = mLayoutInflater.inflate(R.layout.diary_medicine_name_pop, null);
            final RadioGroup radioGroup = contentView.findViewById(R.id.redioGroup_id);

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    Log.i("oo", group.getCheckedRadioButtonId() + "");
                    RadioButton radioButton = contentView.findViewById(group.getCheckedRadioButtonId());
                    index1 = Integer.parseInt(radioButton.getTag() + "");

                }
            });
            Button cancleButton = contentView.findViewById(R.id.cancel_ID);
            cancleButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
            Button sureButton = contentView.findViewById(R.id.confirm_ID);
            sureButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBackString.callBackString(listYaoWuName.get(index1));
                    popupWindow.dismiss();
                }
            });

            DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
            popupWindow = new PopupWindow(contentView, dm.widthPixels - 40, (dm.heightPixels - 20) * 3 / 9);
        }

        ColorDrawable cd = new ColorDrawable(0x000000);
        popupWindow.setBackgroundDrawable(cd);

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        //popupWindow.showAsDropDown(v, 0, 5);

        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        popupWindow.update();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    private class listViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.popwindow_item, null);

            }
            TextView textView = convertView.findViewById(R.id.listView_tv_ID);
            DiaryEntity diaryEntityPop = viewList.get(position);
            textView.setText("第" + diaryEntityPop.getWeek() + "周");
            return convertView;
        }
    }

    private class listPopViewAdater extends BaseAdapter {

        @Override
        public int getCount() {
            return listYaoWuName.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.diary_medicine_name_item, null);

            }
            TextView textView = convertView.findViewById(R.id.diary_medicine_name_item_Id);
            textView.setText(listYaoWuName.get(position));
            return convertView;
        }
    }


    private View initView(DiaryEntity diaryEntity) {

        final DiaryEntity diaryEnt = diaryEntity;
        LayoutInflater lf = getLayoutInflater().from(this);
        final View listView = lf.inflate(R.layout.activity_diary_add, null);

        final LinearLayout linearLayout = listView.findViewById(R.id.diary_top_id);
        int a = linearLayout.getChildCount();

        String[] itemNames = {getString(R.string.keSou), getString(R.string.ChuanXi),
                getString(R.string.qiJi), getString(R.string.xiongMen),
                getString(R.string.nightAsthma), getString(R.string.asthmaActivity)
        };

        for (int i = 2; i < linearLayout.getChildCount(); i++) {

            if (linearLayout.getChildAt(i) instanceof LinearLayout) {

                final DiaryEntity.SymptomArrayEntity.ItemsEntity itemsEntity = diaryEnt.getSymptomArray().get(0).getItems().get(i - 2);
                itemsEntity.setItemName(itemNames[i - 2]);
                itemsEntity.setType("checkbox");
                for (int k = 1; k < ((LinearLayout) linearLayout.getChildAt(i)).getChildCount(); k++) {

                    if (((LinearLayout) linearLayout.getChildAt(i)).getChildAt(k) instanceof ImageView) {
                        (((LinearLayout) linearLayout.getChildAt(i)).getChildAt(k)).setTag((k - 1) * 2);
                        if (itemsEntity.getSelectString() == null) {
                            itemsEntity.setSelectString("0,0,0,0,0,0,0");
                        }
                        String[] selectString = itemsEntity.getSelectString().split(",");
                        if (selectString[k - 1].equals("1")) {
                            (((LinearLayout) linearLayout.getChildAt(i)).getChildAt(k)).setSelected(true);
                            ((ImageView) ((LinearLayout) linearLayout.getChildAt(i)).getChildAt(k)).setImageResource(R.drawable.diary_duihao);

                        }

                        (((LinearLayout) linearLayout.getChildAt(i)).getChildAt(k)).setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                v.setSelected(!v.isSelected());
                                StringBuffer buffer = new StringBuffer(itemsEntity.getSelectString());
                                if (v.isSelected()) {
                                    System.out.println(buffer.toString());//输出123456
                                    int a = (int) v.getTag();
                                    buffer.replace(a, a + 1, "1");


                                    ((ImageView) v).setImageResource(R.drawable.diary_duihao);
                                    System.out.println(buffer.toString());//输出a23456
                                    itemsEntity.setSelectString(buffer.toString());
                                } else {
                                    int a = (int) v.getTag();
                                    buffer.replace(a, a + 1, "0");
                                    ((ImageView) v).setImageResource(0);
                                    System.out.println(buffer.toString());//输出a23456
                                    itemsEntity.setSelectString(buffer.toString());
                                }
                            }
                        });
                    }
                }

            }
        }

        LinearLayout linearLayoutMiddle = listView.findViewById(R.id.diary_middle);
        DiaryEntity.AddMedicineArrayEntity addMedicineArrayEntity = diaryEnt.getAddMedicineArray().get(0);
        DiaryEntity.AddMedicineArrayEntity.ItemsEntity middleItemsEntityTimes1 = addMedicineArrayEntity.getItems().get(0);
        if (addMedicineArrayEntity.getItems().size() > 1) {
            middleItemsEntityTimes1 = addMedicineArrayEntity.getItems().get(1);
        }

        final DiaryEntity.AddMedicineArrayEntity.ItemsEntity middleItemsEntityTimes = middleItemsEntityTimes1;
        if (middleItemsEntityTimes.getSelectString() == null || middleItemsEntityTimes.getSelectString().equals("")) {
            middleItemsEntityTimes.setSelectString(" , , , , , , ");
        }
        String[] slectString = middleItemsEntityTimes.getSelectString().split(",");
        int stringLength = 7 - slectString.length;
        ArrayList<String> listString = new ArrayList<String>();
        for (int i = 0; i < slectString.length; i++) {
            listString.add(slectString[i]);
        }

        for (int i = 0; i < stringLength; i++) {
            listString.add(" ");
        }


        for (int i = 1; i < linearLayoutMiddle.getChildCount(); i++) {

            if (linearLayoutMiddle.getChildAt(i) instanceof TextView) {
                (linearLayoutMiddle.getChildAt(i)).setTag(i - 1);
                ((TextView) linearLayoutMiddle.getChildAt(i)).setText(listString.get(i - 1) + "");
                (linearLayoutMiddle.getChildAt(i)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        final View textViewMiddle = v;
                        inputTitleDialog(new CallBackString() {
                            @Override
                            public void callBackString(String string) {
                                StringBuffer buffer = new StringBuffer(middleItemsEntityTimes.getSelectString());
                                String[] str = middleItemsEntityTimes.getSelectString().split(",");
                                str[(int) v.getTag()] = string;
                                String stringText = " ";
                                for (int k = 0; k < str.length; k++) {
                                    if (k == 0) {
                                        stringText = stringText + str[k];
                                    } else {
                                        stringText = stringText + "," + str[k];
                                    }
                                }
                                middleItemsEntityTimes.setSelectString(stringText);
                                ((TextView) textViewMiddle).setText(string);
                                int b = buffer.indexOf(",", 0);
                            }
                        });
                    }
                });

            }
        }

        final DiaryEntity.AddMedicineArrayEntity.ItemsEntity middleItemsTextViewEntityTimes = addMedicineArrayEntity.getItems().get(0);

        TextView middleTextView = listView.findViewById(R.id.diary_middle_textview);
        middleTextView.setText(middleItemsTextViewEntityTimes.getSelectString());
        middleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View textView = v;

                initMedicinePop(v, 1, new CallBackString() {
                    @Override
                    public void callBackString(String string) {
                        for (DiaryEntity.UseMedicineArrayEntity useMedicineArrayEntity : diaryEnt.getUseMedicineArray()) {
                            if (useMedicineArrayEntity.getItems().get(0).getItemName().equals(string)) {

                                Toast.makeText(getApplicationContext(), "已经添加该药物", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        ((TextView) textView).setText(string);
                        middleItemsTextViewEntityTimes.setSelectString(string);
                    }
                });

            }
        });

        for (int l = 0; l < diaryEnt.getUseMedicineArray().size(); l++) {

            final LinearLayout linearLayout1 = listView.findViewById(R.id.bottom_id);

            ViewGroup.LayoutParams lp1 = linearLayout.getLayoutParams();
            final View view = View.inflate(getApplicationContext(), R.layout.diary_item, null);

            linearLayout1.addView(view, lp1);
            final DiaryEntity.UseMedicineArrayEntity useMedicineArrayEntity = diaryEnt.getUseMedicineArray().get(l);
            ImageView deleteTextView = view.findViewById(R.id.delete_id);
            deleteTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    linearLayout1.removeView(view);
                    diaryEnt.getUseMedicineArray().remove(useMedicineArrayEntity);
                }
            });

            LinearLayout bottomLinearLayout = view.findViewById(R.id.diary_bottom_id);
            String[] bottomString = {getString(R.string.bottomEachDose), getString(R.string.bottomUseTimes)};

            for (int k = 1; k < bottomLinearLayout.getChildCount(); k++) {

                LinearLayout linearLayoutBottom;
                if (bottomLinearLayout.getChildAt(k) instanceof LinearLayout) {
                    linearLayoutBottom = (LinearLayout) bottomLinearLayout.getChildAt(k);
                } else {
                    continue;
                }
                final DiaryEntity.UseMedicineArrayEntity.ItemsEntity bottomiItemsEntity = useMedicineArrayEntity.getItems().get(k);
                if (bottomiItemsEntity.getSelectString() == null) {
                    bottomiItemsEntity.setSelectString(" , , , , , , , ");
                }
                String[] selectString = bottomiItemsEntity.getSelectString().split(",");
                int stringLength1 = 7 - selectString.length;
                ArrayList<String> listString1 = new ArrayList<String>();
                for (int i = 0; i < selectString.length; i++) {
                    listString1.add(selectString[i]);
                }

                for (int i = 0; i < stringLength1; i++) {
                    listString1.add(" ");
                }

                for (int i = 1; i < linearLayoutBottom.getChildCount(); i++) {

                    if (linearLayoutBottom.getChildAt(i) instanceof TextView) {

                        ((TextView) linearLayoutBottom.getChildAt(i)).setText(listString1.get(i - 1));
                        (linearLayoutBottom.getChildAt(i)).setTag(i - 1);
                        (linearLayoutBottom.getChildAt(i)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final View textViewBottom = v;
                                inputTitleDialog(new CallBackString() {
                                    @Override
                                    public void callBackString(String string) {

                                        StringBuffer buffer = new StringBuffer(bottomiItemsEntity.getSelectString());
                                        String[] str = bottomiItemsEntity.getSelectString().split(",");
                                        str[(int) textViewBottom.getTag()] = string;

                                        String stringText = " ";
                                        for (int n = 0; n < str.length; n++) {
                                            if (n == 0) {
                                                stringText = stringText + str[n];
                                            } else {
                                                stringText = stringText + "," + str[n];
                                            }
                                        }
                                        bottomiItemsEntity.setSelectString(stringText);
                                        ((TextView) textViewBottom).setText(string);
                                    }
                                });
                            }
                        });
                    }
                }
            }

            TextView bottomTextView = view.findViewById(R.id.diary_bottom_tv);
            final DiaryEntity.UseMedicineArrayEntity.ItemsEntity bottomiItemsTextViewEntity = useMedicineArrayEntity.getItems().get(0);
            bottomiItemsTextViewEntity.setItemName("药物名");
            bottomTextView.setText(bottomiItemsTextViewEntity.getSelectString());
            bottomTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final View bottomTextView = v;

                    initMedicinePop(v, 1, new CallBackString() {
                        @Override
                        public void callBackString(String string) {
                            if (string.trim().equals("")) {
                                bottomiItemsTextViewEntity.setSelectString("");
                                Toast.makeText(getApplicationContext(), "药物名为空", Toast.LENGTH_SHORT).show();

                            } else {
                                ((TextView) bottomTextView).setText(string);
                                bottomiItemsTextViewEntity.setSelectString(string);
                            }
                        }
                    });
                }
            });
        }


        TextView button = listView.findViewById(R.id.diary_add_id);
        if (diaryEnt.getUseMedicineArray() == null) {
            diaryEnt.setUseMedicineArray(new ArrayList<DiaryEntity.UseMedicineArrayEntity>());
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LinearLayout linearLayout = listView.findViewById(R.id.bottom_id);

                ViewGroup.LayoutParams lp1 = linearLayout.getLayoutParams();
                final View view = View.inflate(getApplicationContext(), R.layout.diary_item, null);

                linearLayout.addView(view, lp1);

                final DiaryEntity.UseMedicineArrayEntity useMedicineArrayEntity = new DiaryEntity.UseMedicineArrayEntity();
                useMedicineArrayEntity.setItems(new ArrayList<DiaryEntity.UseMedicineArrayEntity.ItemsEntity>());
                useMedicineArrayEntity.setSectionName(getString(R.string.sectionNameBottom));
                LinearLayout bottomLinearLayout = view.findViewById(R.id.diary_bottom_id);

                String[] bottomString = {getString(R.string.bottomEachDose), getString(R.string.bottomUseTimes)};
                for (int k = 1; k < bottomLinearLayout.getChildCount(); k++) {

                    final DiaryEntity.UseMedicineArrayEntity.ItemsEntity bottomiItemsEntity = new DiaryEntity.UseMedicineArrayEntity.ItemsEntity();
                    LinearLayout linearLayoutBottom;
                    if (bottomLinearLayout.getChildAt(k) instanceof LinearLayout) {
                        linearLayoutBottom = (LinearLayout) bottomLinearLayout.getChildAt(k);
                    } else {
                        continue;
                    }
                    useMedicineArrayEntity.getItems().add(bottomiItemsEntity);
                    bottomiItemsEntity.setSelectString(" ");
                    bottomiItemsEntity.setItemName(bottomString[k - 1]);

                    for (int i = 1; i < linearLayoutBottom.getChildCount(); i++) {
                        if (linearLayoutBottom.getChildAt(i) instanceof TextView) {

                            (linearLayoutBottom.getChildAt(i)).setTag(i - 1);
                            (linearLayoutBottom.getChildAt(i)).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final View textViewBottom = v;
                                    inputTitleDialog(new CallBackString() {
                                        @Override
                                        public void callBackString(String string) {

                                            StringBuffer buffer = new StringBuffer(bottomiItemsEntity.getSelectString());
                                            String[] str = bottomiItemsEntity.getSelectString().split(",");
                                            str[(int) textViewBottom.getTag()] = string;

                                            String stringText = " ";
                                            for (int n = 0; n < str.length; n++) {
                                                if (n == 0) {
                                                    stringText = stringText + str[n];
                                                } else {
                                                    stringText = stringText + "," + str[n];
                                                }
                                            }
                                            bottomiItemsEntity.setSelectString(stringText);
                                            ((TextView) textViewBottom).setText(string);
                                        }
                                    });
                                }
                            });
                        }

                        if ((i - 1) == 0) {
                            bottomiItemsEntity.setSelectString(bottomiItemsEntity.getSelectString() + " ");
                        } else {
                            bottomiItemsEntity.setSelectString(bottomiItemsEntity.getSelectString() + ", ");

                        }
                    }
                }

                ImageView deleteTextView = view.findViewById(R.id.delete_id);
                deleteTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        linearLayout.removeView(view);
                        diaryEnt.getUseMedicineArray().remove(useMedicineArrayEntity);
                    }
                });

                TextView bottomTextView = view.findViewById(R.id.diary_bottom_tv);

                final DiaryEntity.UseMedicineArrayEntity.ItemsEntity bottomiItemsTextViewEntity = new DiaryEntity.UseMedicineArrayEntity.ItemsEntity();
                useMedicineArrayEntity.getItems().add(bottomiItemsTextViewEntity);
                bottomiItemsTextViewEntity.setItemName("药物名");
                bottomiItemsTextViewEntity.setSelectString(" ");

                bottomTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final View bottomTextView = v;
                        initMedicinePop(v, 1, new CallBackString() {
                            @Override
                            public void callBackString(String string) {
                                if (string.trim().equals("")) {
                                    bottomiItemsTextViewEntity.setSelectString("");
                                    Toast.makeText(getApplicationContext(), "药物名为空", Toast.LENGTH_SHORT).show();

                                } else {
                                    ((TextView) bottomTextView).setText(string);
                                    bottomiItemsTextViewEntity.setSelectString(string);
                                }
                            }
                        });

                    }
                });
                diaryEnt.getUseMedicineArray().add(useMedicineArrayEntity);
            }
        });
        return listView;
    }

    private interface CallBackString {

        public void callBackString(String string);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        DiaryModel diaryModel = new DiaryModel();
    }

    @Override
    protected void onPause() {
        super.onPause();

        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        MobclickAgent.onResume(this);
    }
}
