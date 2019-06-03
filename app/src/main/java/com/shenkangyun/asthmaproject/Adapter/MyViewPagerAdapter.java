package com.shenkangyun.asthmaproject.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shenkangyun.asthmaproject.R;

import java.util.List;


public class MyViewPagerAdapter extends PagerAdapter {

    private List<View> mListViews;
    private List<Integer> lists;


    public MyViewPagerAdapter(List<View> mListViews, List<Integer> lists) {
        this.mListViews = mListViews;//构造方法，参数是我们的页卡，这样比较方便。
        this.lists = lists;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object); // 将view 类型 的object熊容器中移除,根据key
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡
        container.addView(mListViews.get(position), 0);//添加页卡
        View view = mListViews.get(position);
        ImageView imageView = view.findViewById(R.id.imageview);
        imageView.setImageResource(lists.get(position));

        return mListViews.get(position);
    }

    @Override
    public int getCount() {
        return mListViews.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }
}
