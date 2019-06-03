package com.shenkangyun.asthmaproject.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.shenkangyun.asthmaproject.BeanFolder.DiaryEntity;
import com.shenkangyun.asthmaproject.HttpFolder.DiaryModel;
import com.shenkangyun.asthmaproject.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiaryActivity extends AppCompatActivity {

    @BindView(R.id.toolBar_title)
    TextView toolBarTitle;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.diary_add_back)
    ImageView diaryAddBack;
    @BindView(R.id.diary_top_id)
    LinearLayout diaryTopId;
    @BindView(R.id.diary_middle_id)
    TextView diaryMiddleId;
    @BindView(R.id.diary_middle_textview)
    TextView diaryMiddleTextview;
    @BindView(R.id.diary_middle)
    LinearLayout diaryMiddle;
    @BindView(R.id.bottom_title_id)
    TextView bottomTitleId;
    @BindView(R.id.diary_add_id)
    ImageView diaryAddId;
    @BindView(R.id.bottom_id)
    LinearLayout bottomId;
    @BindView(R.id.floating_id)
    FloatingActionButton floatingId;

    private int index1;
    public View contentView;
    public PopupWindow popupWindow;
    private List<DiaryEntity> viewList;
    private List<String> listYaoWuName;
    private DiaryEntity diaryEnt;
    private DiaryEntity.SymptomArrayEntity symptomArrayEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.mBlue));
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
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

        diaryEnt = new DiaryEntity();
        Intent intent = getIntent();
        diaryEnt.setWeek(String.valueOf(intent.getIntExtra("week", 0)));
        diaryEnt.setSymptomArray(new ArrayList<DiaryEntity.SymptomArrayEntity>());
        symptomArrayEntity = new DiaryEntity.SymptomArrayEntity();
        symptomArrayEntity.setSectionName(getString(R.string.sectionNameTop));
        symptomArrayEntity.setItems(new ArrayList<DiaryEntity.SymptomArrayEntity.ItemsEntity>());
    }

    private void initView() {
        LinearLayout linearLayout = findViewById(R.id.diary_add).findViewById(R.id.diary_top_id);
        int a = linearLayout.getChildCount();
        String[] itemNames = {getString(R.string.keSou), getString(R.string.ChuanXi),
                getString(R.string.qiJi), getString(R.string.xiongMen),
                getString(R.string.nightAsthma), getString(R.string.asthmaActivity)
        };

        for (int i = 2; i < linearLayout.getChildCount(); i++) {

            if (linearLayout.getChildAt(i) instanceof LinearLayout) {
                final DiaryEntity.SymptomArrayEntity.ItemsEntity itemsEntity = new DiaryEntity.SymptomArrayEntity.ItemsEntity();
                itemsEntity.setSelectString("");
                itemsEntity.setItemName(itemNames[i - 2]);
                itemsEntity.setType("checkbox");
                for (int k = 1; k < ((LinearLayout) linearLayout.getChildAt(i)).getChildCount(); k++) {

                    if (((LinearLayout) linearLayout.getChildAt(i)).getChildAt(k) instanceof ImageView) {
                        (((LinearLayout) linearLayout.getChildAt(i)).getChildAt(k)).setTag((k - 1) * 2);
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
                    if ((k - 1) == 0) {
                        itemsEntity.setSelectString(itemsEntity.getSelectString() + "0");
                    } else {
                        itemsEntity.setSelectString(itemsEntity.getSelectString() + ",0");
                    }
                }
                symptomArrayEntity.getItems().add(itemsEntity);
            }
        }

        diaryEnt.getSymptomArray().add(symptomArrayEntity);
        LinearLayout linearLayoutMiddle = findViewById(R.id.diary_add).findViewById(R.id.diary_middle);

        diaryEnt.setAddMedicineArray(new ArrayList<DiaryEntity.AddMedicineArrayEntity>());
        DiaryEntity.AddMedicineArrayEntity addMedicineArrayEntity = new DiaryEntity.AddMedicineArrayEntity();
        addMedicineArrayEntity.setSectionName(getString(R.string.sectionNameMiddle));
        addMedicineArrayEntity.setItems(new ArrayList<DiaryEntity.AddMedicineArrayEntity.ItemsEntity>());

        final DiaryEntity.AddMedicineArrayEntity.ItemsEntity middleItemsEntityTimes = new DiaryEntity.AddMedicineArrayEntity.ItemsEntity();
        middleItemsEntityTimes.setSelectString("");
        middleItemsEntityTimes.setItemName(getString(R.string.useTimes));
        addMedicineArrayEntity.getItems().add(middleItemsEntityTimes);

        diaryEnt.getAddMedicineArray().add(addMedicineArrayEntity);
        for (int i = 0; i < linearLayoutMiddle.getChildCount(); i++) {

            if (linearLayoutMiddle.getChildAt(i) instanceof TextView) {
                (linearLayoutMiddle.getChildAt(i)).setTag(i - 1);
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

                                String stringText = "";
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

            if (i == 0) {
                middleItemsEntityTimes.setSelectString(middleItemsEntityTimes.getSelectString() + " ");
            } else {
                middleItemsEntityTimes.setSelectString(middleItemsEntityTimes.getSelectString() + " ,");

            }
        }

        final DiaryEntity.AddMedicineArrayEntity.ItemsEntity middleItemsTextViewEntityTimes = new DiaryEntity.AddMedicineArrayEntity.ItemsEntity();
        TextView middleTextView = findViewById(R.id.diary_add).findViewById(R.id.diary_middle_textview);
        middleItemsTextViewEntityTimes.setItemName("药物名");
        middleItemsEntityTimes.setType("text");

        //添加药物名   因症状加重加吸的药物
        diaryEnt.getAddMedicineArray().get(0).getItems().add(middleItemsTextViewEntityTimes);
        middleItemsTextViewEntityTimes.setSelectString(" ");
        middleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View textView = v;

                initPopupView1(v, 1, new CallBackString() {
                    @Override
                    public void callBackString(String string) {
                        if (string.trim().equals("")) {
                            middleItemsTextViewEntityTimes.setSelectString(" ");
                            Toast.makeText(getApplicationContext(), "药物名为空", Toast.LENGTH_SHORT).show();

                        } else {
                            ((TextView) textView).setText(string);
                            middleItemsTextViewEntityTimes.setSelectString(string);
                        }
                    }
                });
            }
        });

        diaryEnt.setUseMedicineArray(new ArrayList<DiaryEntity.UseMedicineArrayEntity>());
        ImageView button = findViewById(R.id.diary_add_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LinearLayout linearLayout = findViewById(R.id.diary_add).findViewById(R.id.bottom_id);

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
                    bottomiItemsEntity.setSelectString("");
                    bottomiItemsEntity.setItemName(bottomString[k - 1]);

                    for (int i = 0; i < linearLayoutBottom.getChildCount(); i++) {
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

                                            String stringText = "";
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

                        initPopupView1(v, 1, new CallBackString() {
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

        final FloatingActionButton floatingActionButton = findViewById(R.id.floating_id);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.INVISIBLE);
                DiaryEntity diaryEntity = diaryEnt;
                DiaryModel diaryModel = new DiaryModel();
                closeActivity();
            }
        });

        ImageView backButton = findViewById(R.id.diary_add_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initPopupView1(View v, int flag, final CallBackString callBackString) {

        popupWindow = null;
        if (popupWindow == null) {

            LayoutInflater mLayoutInflater = LayoutInflater.from(this);
            contentView = mLayoutInflater.inflate(R.layout.diary_medicine_name_pop, null);
            final RadioGroup radioGroup = contentView.findViewById(R.id.redioGroup_id);

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton radioButton = contentView.findViewById(group.getCheckedRadioButtonId());
                    index1 = Integer.parseInt(radioButton.getTag() + "");
                }
            });


            Button cancelButton = contentView.findViewById(R.id.cancel_ID);
            cancelButton.setOnClickListener(new View.OnClickListener() {

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
            popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        }

        ColorDrawable cd = new ColorDrawable(0x000000);
        popupWindow.setBackgroundDrawable(cd);

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(toolBar);
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

    public int dip2px(float dpValue) {
        final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void closeActivity() {
        Intent intent = new Intent();
        intent.putExtra("result", "返回值");

        this.setResult(RESULT_OK, intent); // 设置结果数据
        this.finish(); // 关闭Activity
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
                        callBackString.callBackString(inputServer.getText().toString());
                    }
                });
        builder.show();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // requestCode用于区分业务
        // resultCode用于区分某种业务的执行情况
        if (1 == requestCode && RESULT_OK == resultCode) {
            String result = data.getStringExtra("result");
            Toast.makeText(this.getBaseContext(), result, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this.getBaseContext(), "无返回值", Toast.LENGTH_SHORT).show();
        }
    }

    private interface CallBackString {

        public void callBackString(String string);

    }

}
