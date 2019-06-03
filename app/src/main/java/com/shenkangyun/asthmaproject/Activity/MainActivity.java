package com.shenkangyun.asthmaproject.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jaeger.library.StatusBarUtil;
import com.shenkangyun.asthmaproject.Fragment.HomeFragment;
import com.shenkangyun.asthmaproject.Fragment.PersonCenterFragment;
import com.shenkangyun.asthmaproject.R;
import com.shenkangyun.asthmaproject.Utils.DateUtil;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_personal)
    RadioButton rbPersonal;
    @BindView(R.id.rg_all)
    RadioGroup rgAll;

    private String pm25;
    private String date;

    private FragmentManager fragmentManager;
    private Fragment homeFragment, personalFragment, replaceFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.mBlue));
        ButterKnife.bind(this);
        initView();
        initFragment();
    }

    private void initView() {
        //传送pm2.5
        date = DateUtil.getDateFormat(new Date());
        homeFragment = new HomeFragment();
        personalFragment = new PersonCenterFragment();
    }

    /**
     * 设置默认的显示界面
     */
    private void initFragment() {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.id_content, homeFragment).commit();
        replaceFragment = homeFragment;
    }

    @OnClick({R.id.rb_home, R.id.rb_personal})
    public void onViewClicked(View view) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.rb_home:
                replaceFragment(homeFragment, fragmentTransaction);
                break;
            case R.id.rb_personal:
                replaceFragment(personalFragment, fragmentTransaction);
                break;
        }
    }

    public void replaceFragment(Fragment showFragment, FragmentTransaction fragmentTransaction) {
        if (showFragment.isAdded()) {
            fragmentTransaction.hide(replaceFragment).show(showFragment).commit();
        } else {
            fragmentTransaction.hide(replaceFragment).add(R.id.id_content, showFragment).commit();
        }
        replaceFragment = showFragment;
    }
}