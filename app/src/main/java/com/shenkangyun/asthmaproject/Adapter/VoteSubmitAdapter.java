package com.shenkangyun.asthmaproject.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenkangyun.asthmaproject.Activity.QuestionnaireActivity;
import com.shenkangyun.asthmaproject.BeanFolder.VoteSubmitItem;
import com.shenkangyun.asthmaproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoteSubmitAdapter extends PagerAdapter {

    private QuestionnaireActivity mContext;
    // 传递过来的页面view的集合
    private List<View> viewItems;
    // 每个item的页面view
    private View convertView;
    // 传递过来的所有数据
    private ArrayList<VoteSubmitItem> dataItems;
    // 题目选项的adapter
    private VoteSubmitListAdapter listAdapter;
    //点击listview的item，score的集合
    private ArrayList<Integer> scoresItems;
    private Map<Integer, Integer> scoreMaps = new HashMap<Integer, Integer>();
    private int[] scoreArr;
    //判断有没选择某个页面的选项
    private Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();

    private OnClickCallBack onClickCallBack;

    public OnClickCallBack getOnClickCallBack() {
        return onClickCallBack;
    }

    public void setOnClickCallBack() {
        this.onClickCallBack = onClickCallBack;
    }

    public int s = 0;

    private String[] answer;


    public VoteSubmitAdapter(QuestionnaireActivity context, List<View> viewItems, ArrayList<VoteSubmitItem> dataItems, ArrayList<Integer> scoresItems, OnClickCallBack onClickCallBack) {
        mContext = context;
        this.viewItems = viewItems;
        this.dataItems = dataItems;
        this.scoresItems = scoresItems;
        scoreArr = new int[viewItems.size()];
        this.answer = new String[viewItems.size()];
        this.onClickCallBack = onClickCallBack;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewItems.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final ViewHolder holder = new ViewHolder();
        convertView = viewItems.get(position);
        holder.title = convertView.findViewById(R.id.vote_submit_title);
        holder.question = convertView.findViewById(R.id.vote_submit_question);
        holder.previousBtn = convertView.findViewById(R.id.vote_submit_linear_previous);
        holder.nextBtn = convertView.findViewById(R.id.vote_submit_linear_next);
        holder.nextText = convertView.findViewById(R.id.vote_submit_next_text);
        holder.nextImage = convertView.findViewById(R.id.vote_submit_next_image);

        holder.layoutA = convertView.findViewById(R.id.activity_prepare_test_layout_a);
        holder.layoutB = convertView.findViewById(R.id.activity_prepare_test_layout_b);
        holder.layoutC = convertView.findViewById(R.id.activity_prepare_test_layout_c);
        holder.layoutD = convertView.findViewById(R.id.activity_prepare_test_layout_d);
        holder.layoutE = convertView.findViewById(R.id.activity_prepare_test_layout_e);


        holder.ivA = convertView.findViewById(R.id.vote_submit_select_image_a);
        holder.ivB = convertView.findViewById(R.id.vote_submit_select_image_b);
        holder.ivC = convertView.findViewById(R.id.vote_submit_select_image_c);
        holder.ivD = convertView.findViewById(R.id.vote_submit_select_image_d);
        holder.ivE = convertView.findViewById(R.id.vote_submit_select_image_e);

        holder.ivA_ = convertView.findViewById(R.id.vote_submit_select_image_a_);
        holder.ivB_ = convertView.findViewById(R.id.vote_submit_select_image_b_);
        holder.ivC_ = convertView.findViewById(R.id.vote_submit_select_image_c_);
        holder.ivD_ = convertView.findViewById(R.id.vote_submit_select_image_d_);
        holder.ivE_ = convertView.findViewById(R.id.vote_submit_select_image_e_);

        holder.tvA = convertView.findViewById(R.id.vote_submit_select_text_a);
        holder.tvB = convertView.findViewById(R.id.vote_submit_select_text_b);
        holder.tvC = convertView.findViewById(R.id.vote_submit_select_text_c);
        holder.tvD = convertView.findViewById(R.id.vote_submit_select_text_d);
        holder.tvE = convertView.findViewById(R.id.vote_submit_select_text_e);

        holder.title.setText("<哮喘控制问卷>");

        holder.question.setText(dataItems.get(position).getVoteQuestion());
        holder.tvA.setText("A." + dataItems.get(position).getVoteAnswers().get(0));
        holder.tvB.setText("B." + dataItems.get(position).getVoteAnswers().get(1));
        holder.tvC.setText("C." + dataItems.get(position).getVoteAnswers().get(2));
        holder.tvD.setText("D." + dataItems.get(position).getVoteAnswers().get(3));
        holder.tvE.setText("E." + dataItems.get(position).getVoteAnswers().get(4));


        /**单选*/
        holder.layoutA.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                map.put(position, true);
                scoreMaps.put(position, 1);
                //answer[position] = dataItems.get(position).getVoteAnswers().get(0);
                answer[position] = String.valueOf(1);

                holder.ivA.setVisibility(View.GONE);
                holder.ivA_.setVisibility(View.VISIBLE);
                holder.tvA.setTextColor(mContext.getResources().getColor(R.color.theme_background));

                holder.ivB.setVisibility(View.VISIBLE);
                holder.ivB_.setVisibility(View.GONE);
                holder.tvB.setTextColor(mContext.getResources().getColor(R.color.practice_test_result_text));

                holder.ivC.setVisibility(View.VISIBLE);
                holder.ivC_.setVisibility(View.GONE);
                holder.tvC.setTextColor(mContext.getResources().getColor(R.color.practice_test_result_text));

                holder.ivD.setVisibility(View.VISIBLE);
                holder.ivD_.setVisibility(View.GONE);
                holder.tvD.setTextColor(mContext.getResources().getColor(R.color.practice_test_result_text));

                holder.ivE.setVisibility(View.VISIBLE);
                holder.ivE_.setVisibility(View.GONE);
                holder.tvE.setTextColor(mContext.getResources().getColor(R.color.practice_test_result_text));

                onClickCallBack.OnClick(position, scoreMaps, map, answer);
            }
        });
        holder.layoutB.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                map.put(position, true);
                scoreMaps.put(position, 2);
                //answer[position] = dataItems.get(position).getVoteAnswers().get(1);
                answer[position] = String.valueOf(2);
                holder.ivB.setVisibility(View.GONE);
                holder.ivB_.setVisibility(View.VISIBLE);
                holder.tvB.setTextColor(mContext.getResources().getColor(R.color.theme_background));

                holder.ivA.setVisibility(View.VISIBLE);
                holder.ivA_.setVisibility(View.GONE);
                holder.tvA.setTextColor(mContext.getResources().getColor(R.color.practice_test_result_text));

                holder.ivC.setVisibility(View.VISIBLE);
                holder.ivC_.setVisibility(View.GONE);
                holder.tvC.setTextColor(mContext.getResources().getColor(R.color.practice_test_result_text));

                holder.ivD.setVisibility(View.VISIBLE);
                holder.ivD_.setVisibility(View.GONE);
                holder.tvD.setTextColor(mContext.getResources().getColor(R.color.practice_test_result_text));

                holder.ivE.setVisibility(View.VISIBLE);
                holder.ivE_.setVisibility(View.GONE);
                holder.tvE.setTextColor(mContext.getResources().getColor(R.color.practice_test_result_text));

                onClickCallBack.OnClick(position, scoreMaps, map, answer);
            }
        });
        holder.layoutC.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                map.put(position, true);
                scoreMaps.put(position, 3);
                //answer[position] = dataItems.get(position).getVoteAnswers().get(2);
                answer[position] = String.valueOf(3);

                holder.ivC.setVisibility(View.GONE);
                holder.ivC_.setVisibility(View.VISIBLE);
                holder.tvC.setTextColor(mContext.getResources().getColor(R.color.theme_background));

                holder.ivA.setVisibility(View.VISIBLE);
                holder.ivA_.setVisibility(View.GONE);
                holder.tvA.setTextColor(mContext.getResources().getColor(R.color.practice_test_result_text));

                holder.ivB.setVisibility(View.VISIBLE);
                holder.ivB_.setVisibility(View.GONE);
                holder.tvB.setTextColor(mContext.getResources().getColor(R.color.practice_test_result_text));

                holder.ivD.setVisibility(View.VISIBLE);
                holder.ivD_.setVisibility(View.GONE);
                holder.tvD.setTextColor(mContext.getResources().getColor(R.color.practice_test_result_text));

                holder.ivE.setVisibility(View.VISIBLE);
                holder.ivE_.setVisibility(View.GONE);
                holder.tvE.setTextColor(mContext.getResources().getColor(R.color.practice_test_result_text));

                onClickCallBack.OnClick(position, scoreMaps, map, answer);
            }
        });
        holder.layoutD.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                map.put(position, true);
                scoreMaps.put(position, 4);
                //answer[position] = dataItems.get(position).getVoteAnswers().get(3);
                answer[position] = String.valueOf(4);

                holder.ivD.setVisibility(View.GONE);
                holder.ivD_.setVisibility(View.VISIBLE);
                holder.tvD.setTextColor(mContext.getResources().getColor(R.color.theme_background));

                holder.ivA.setVisibility(View.VISIBLE);
                holder.ivA_.setVisibility(View.GONE);
                holder.tvA.setTextColor(mContext.getResources().getColor(R.color.practice_test_result_text));

                holder.ivB.setVisibility(View.VISIBLE);
                holder.ivB_.setVisibility(View.GONE);
                holder.tvB.setTextColor(mContext.getResources().getColor(R.color.practice_test_result_text));

                holder.ivC.setVisibility(View.VISIBLE);
                holder.ivC_.setVisibility(View.GONE);
                holder.tvC.setTextColor(mContext.getResources().getColor(R.color.practice_test_result_text));

                holder.ivE.setVisibility(View.VISIBLE);
                holder.ivE_.setVisibility(View.GONE);
                holder.tvE.setTextColor(mContext.getResources().getColor(R.color.practice_test_result_text));

                onClickCallBack.OnClick(position, scoreMaps, map, answer);
            }
        });
        holder.layoutE.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                map.put(position, true);
                scoreMaps.put(position, 5);
                answer[position] = dataItems.get(position).getVoteAnswers().get(4);
                answer[position] = String.valueOf(5);

                holder.ivE.setVisibility(View.GONE);
                holder.ivE_.setVisibility(View.VISIBLE);
                holder.tvE.setTextColor(mContext.getResources().getColor(R.color.theme_background));

                holder.ivA.setVisibility(View.VISIBLE);
                holder.ivA_.setVisibility(View.GONE);
                holder.tvA.setTextColor(mContext.getResources().getColor(R.color.practice_test_result_text));

                holder.ivB.setVisibility(View.VISIBLE);
                holder.ivB_.setVisibility(View.GONE);
                holder.tvB.setTextColor(mContext.getResources().getColor(R.color.practice_test_result_text));

                holder.ivC.setVisibility(View.VISIBLE);
                holder.ivC_.setVisibility(View.GONE);
                holder.tvC.setTextColor(mContext.getResources().getColor(R.color.practice_test_result_text));

                holder.ivD.setVisibility(View.VISIBLE);
                holder.ivD_.setVisibility(View.GONE);
                holder.tvD.setTextColor(mContext.getResources().getColor(R.color.practice_test_result_text));

                onClickCallBack.OnClick(position, scoreMaps, map, answer);
            }
        });

        container.addView(viewItems.get(position));
        return viewItems.get(position);
    }

    @Override
    public int getCount() {
        if (viewItems == null)
            return 0;
        return viewItems.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    /**
     * 自定义类
     */
    class ViewHolder {
        TextView title;
        TextView question;
        LinearLayout previousBtn, nextBtn;
        TextView nextText;
        ImageView nextImage;

        //...
        LinearLayout layoutA;
        LinearLayout layoutB;
        LinearLayout layoutC;
        LinearLayout layoutD;
        LinearLayout layoutE;

        ImageView ivA;
        ImageView ivB;
        ImageView ivC;
        ImageView ivD;
        ImageView ivE;

        ImageView ivA_;
        ImageView ivB_;
        ImageView ivC_;
        ImageView ivD_;
        ImageView ivE_;

        TextView tvA;
        TextView tvB;
        TextView tvC;
        TextView tvD;
        TextView tvE;
    }

    public interface OnClickCallBack {
        void OnClick(int position, Map<Integer, Integer> dataMap, Map<Integer, Boolean> map, String[] answer);
    }

}
