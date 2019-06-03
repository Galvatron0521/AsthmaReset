package com.shenkangyun.asthmaproject.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shenkangyun.asthmaproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonCenterFragment extends Fragment {
    @BindView(R.id.img_head)
    CircleImageView imgHead;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personcenter, null);
        ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    private void initView() {

    }

    private void initData() {

    }

    @OnClick(R.id.btn_exit)
    public void onViewClicked() {
        getActivity().finish();
    }
}
