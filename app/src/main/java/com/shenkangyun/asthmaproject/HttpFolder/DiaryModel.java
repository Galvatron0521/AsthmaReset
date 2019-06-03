package com.shenkangyun.asthmaproject.HttpFolder;

import android.content.Context;

import com.google.gson.Gson;
import com.shenkangyun.asthmaproject.Activity.DiaryEditActivity;
import com.shenkangyun.asthmaproject.BeanFolder.DiaryEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.internal.framed.Header;

public class DiaryModel {

    private List<DiaryEntity> listPatientBeans;
    private DiaryEditActivity patientActivity;
    private ResultDownCallBack resultDownCallBack;
    private ResultUploadCallBack resultUploadCallBack;

    public DiaryModel() {
    }

    public void downloadIPatientModels(Context context, final ResultDownCallBack resultDownCallBack) {
    }


    public void upLoadData(Context context, DiaryEntity diaryEntity, final ResultUploadCallBack resultUploadCallBack) {

        Gson gson = new Gson();
        List<DiaryEntity> list = new ArrayList<>();
        list.add(diaryEntity);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        Calendar cal = Calendar.getInstance();
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间

        cal.setTime(curDate);
        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        System.out.println("要计算日期为:" + sdf.format(cal.getTime())); //输出要计算日期
        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        String imptimeBegin = sdf.format(cal.getTime());
        System.out.println("所在周星期一的日期：" + imptimeBegin);

        String stringJson = gson.toJson(list);

    }

    public void updateData() {

        Gson gson = new Gson();
    }

    public interface ResultUploadCallBack {
        void onSuccess(int statusCode, Header[] headers, Object responseString);

        void onError(int statusCode, Header[] headers, Object responseString);

    }

    public interface ResultDownCallBack {
        void onDownSuccess(int statusCode, Header[] headers, Object responseString);

        void onDownError(int statusCode, Header[] headers, Object responseString);
    }
}
