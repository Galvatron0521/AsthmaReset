package com.shenkangyun.asthmaproject.Fragment;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shenkangyun.asthmaproject.Activity.AsthmaActivity;
import com.shenkangyun.asthmaproject.Activity.BaseWebActivity;
import com.shenkangyun.asthmaproject.Activity.ClockIndexActivity;
import com.shenkangyun.asthmaproject.Activity.DiaryActivity;
import com.shenkangyun.asthmaproject.Activity.DiaryEditActivity;
import com.shenkangyun.asthmaproject.Activity.QuestionnaireActivity;
import com.shenkangyun.asthmaproject.Activity.WeatherActivity;
import com.shenkangyun.asthmaproject.Adapter.MyAdapter;
import com.shenkangyun.asthmaproject.BeanFolder.Subject;
import com.shenkangyun.asthmaproject.R;
import com.shenkangyun.asthmaproject.Utils.GlideImageLoader;
import com.shenkangyun.asthmaproject.Utils.OnRecyclerItemClickListener;
import com.shenkangyun.asthmaproject.View.DividerGridItemDecoration;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {
    @BindView(R.id.carousel)
    Banner carousel;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    private String[] titles = {"服药提醒", "问卷", "天气", "哮喘知识", "哮喘日记", "更多"};
    private int[] imageIds = {R.drawable.clock, R.drawable.questionnaire, R.drawable.weather,
            R.drawable.asthma_knowledge, R.drawable.diary, R.drawable.home_add};
    private Integer[] imageUrl = {R.drawable.a, R.drawable.b,
            R.drawable.c, R.drawable.d};
    private List<Subject> datas = new ArrayList<>();
    private ItemTouchHelper mItemTouchHelper;
    private MyAdapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, view);
        initImgData();
        initView();
        initData();
        return view;
    }

    private void initImgData() {
        List<Integer> imgUrls = Arrays.asList(imageUrl);
        carousel.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        carousel.setImageLoader(new GlideImageLoader());
        //设置图片集合
        carousel.setImages(imgUrls);
        //设置banner动画效果
        carousel.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        carousel.isAutoPlay(true);
        //设置轮播时间
        carousel.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        carousel.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        carousel.start();
        initBannerClick();
    }

    private void initBannerClick() {
        carousel.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                switch (position) {
                    case 0:
                        String urlFirst = "https://mp.weixin.qq.com/s?__biz=MzAxMjMxMzAzOQ==&mid=203135820&idx=1&sn=6a4baedd7b1c345060337aacf14ce024&scene=1&srcid=12218ohGI6PKIYvVSoAeCcrg&key=710a5d99946419d9035331685f71cf09335e57ea9af1bb723e426e36a4388b9e354ba4362aa0117357aa1f1b6bcb49b7&ascene=0&uin=MTI5NTY5NjgxNQ%3D%3D&devicetype=iMac+MacBookPro12%2C1+OSX+OSX+10.11.1+build(15B42)&version=11020201&pass_ticket=tICNpVzk0wxPZxBukQTs81kDBq5sDBIdFIqTdwDVA7E6NrjCfGH4LlxUUnoZEKwH";
                        Intent intentA = new Intent(getActivity(), BaseWebActivity.class);
                        intentA.putExtra("URL", urlFirst);
                        getActivity().startActivity(intentA);
                        break;
                    case 1:
                        Intent intentB = new Intent(getActivity(), AsthmaActivity.class);
                        intentB.putExtra("info", "medicine");
                        getActivity().startActivity(intentB);
                        break;
                    case 2:
                        Intent intentC = new Intent(getActivity(), AsthmaActivity.class);
                        intentC.putExtra("info", "manage");
                        getActivity().startActivity(intentC);
                        break;
                    case 3:
                        String urlLast = "https://mp.weixin.qq.com/s?__biz=MzA5MjUxOTgyMA==&mid=203270042&idx=1&sn=74dbe163876f747a034d30766ba528ff&scene=1&srcid=04065Lj0XcydkIsIMMwhSNLZ&key=710a5d99946419d94047138e5fd5c4ece3202817b40bcfb5d793fdfd49264d368869dd8e108eb2afeea67abc34157b67&ascene=0&uin=MTI5NTY5NjgxNQ%3D%3D&devicetype=iMac+MacBookPro12%2C1+OSX+OSX+10.11.1+build(15B42)&version=11020201&pass_ticket=tICNpVzk0wxPZxBukQTs81kDBq5sDBIdFIqTdwDVA7E6NrjCfGH4LlxUUnoZEKwH";
                        Intent intentD = new Intent(getActivity(), BaseWebActivity.class);
                        intentD.putExtra("URL", urlLast);
                        getActivity().startActivity(intentD);
                        break;
                }
            }
        });
    }


    private void initData() {
        //初始化data
        for (int i = 0; i < titles.length; i++) {
            datas.add(new Subject(titles[i], imageIds[i]));
        }
    }

    private void initView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.addItemDecoration(new DividerGridItemDecoration(getContext()));
        myAdapter = new MyAdapter(datas, getContext());
        recyclerView.setAdapter(myAdapter);
        recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {

                if (datas.get(vh.getLayoutPosition()).getTitle().equals("服药提醒")) {
                    Intent intent = new Intent(getContext(), ClockIndexActivity.class);
                    startActivity(intent);
                }
                if (datas.get(vh.getLayoutPosition()).getTitle().equals("问卷")) {
                    Intent intent = new Intent(getContext(), QuestionnaireActivity.class);
                    startActivity(intent);
                }
                if (datas.get(vh.getLayoutPosition()).getTitle().equals("天气")) {
                    Intent intent = new Intent(getContext(), WeatherActivity.class);
                    startActivity(intent);
                }
                if (datas.get(vh.getLayoutPosition()).getTitle().equals("哮喘知识")) {
                    Intent intent = new Intent(getActivity(), AsthmaActivity.class);
                    intent.putExtra("info", "knowledge");
                    startActivity(intent);
                }
                if (datas.get(vh.getLayoutPosition()).getTitle().equals("哮喘日记")) {
                    Intent intent = new Intent(getActivity(), DiaryEditActivity.class);
                    intent.putExtra("info", "knowledge");
                    startActivity(intent);
                }
                if (datas.get(vh.getLayoutPosition()).getTitle().equals("更多")) {
                    Toast.makeText(getActivity(), "添加更多，敬请期待", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                //判断被拖拽的是否是前两个，如果不是则执行拖拽
                mItemTouchHelper.startDrag(vh);
                //获取系统震动服务
                Vibrator vib = (Vibrator) getActivity().getSystemService(Service.VIBRATOR_SERVICE);//震动70毫秒
                vib.vibrate(70);
            }
        });

        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {

            /**
             * 是否处理滑动事件 以及拖拽和滑动的方向 如果是列表类型的RecyclerView的只存在UP和DOWN，如果是网格类RecyclerView则还应该多有LEFT和RIGHT
             *
             * @param recyclerView
             * @param viewHolder
             * @return
             */
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    final int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                } else {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    final int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                }
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //得到当拖拽的viewHolder的Position
                int fromPosition = viewHolder.getAdapterPosition();
                //拿到当前拖拽到的item的viewHolder
                int toPosition = target.getAdapterPosition();
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(datas, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(datas, i, i - 1);
                    }
                }
                myAdapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }

            /**
             * 重写拖拽可用
             *
             * @return
             */
            @Override
            public boolean isLongPressDragEnabled() {
                return false;
            }

            /**
             * 长按选中Item的时候开始调用
             *
             * @param viewHolder
             * @param actionState
             */
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
                }
                super.onSelectedChanged(viewHolder, actionState);
            }

            /**
             * 手指松开的时候还原
             *
             * @param recyclerView
             * @param viewHolder
             */
            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundColor(0);
            }
        });
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }
}