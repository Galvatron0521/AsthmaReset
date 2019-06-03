package com.shenkangyun.asthmaproject.Utils;

import com.shenkangyun.asthmaproject.BeanFolder.DiaryEntity;

import java.util.List;

public class SingleDiary {

    private static SingleDiary instance;
    private static List<DiaryEntity> viewList;

    private SingleDiary() {

    }

    public static SingleDiary getInstance() {
        if (instance == null) {
            instance = new SingleDiary();
        }
        return instance;
    }

    public static List<DiaryEntity> getViewList() {
        return viewList;
    }

    public static void setViewList(List<DiaryEntity> viewList) {
        SingleDiary.viewList = viewList;
    }
}
