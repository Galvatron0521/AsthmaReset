package com.shenkangyun.asthmaproject.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.jaeger.library.StatusBarUtil;
import com.shenkangyun.asthmaproject.R;
import com.umeng.analytics.MobclickAgent;

public class WelcomeActivity extends AppCompatActivity {

    private Animation alphaAnimation;
    private ImageView welcomeImg;
    private GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        StatusBarUtil.setTranslucent(WelcomeActivity.this, 55);
        initView();
        detector = new GestureDetector(this, new MyOnGestureListener());
    }

    private void initView() {
        welcomeImg =  findViewById(R.id.logo_image);
        alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.welcome_alpha);
        //启动fill保持
        alphaAnimation.setFillEnabled(true);
        //设置动画的最后一帧是保持在view上
        alphaAnimation.setFillAfter(true);
        //welcomeImg.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new MyAnimationListener());
        welcomeImg.startAnimation(alphaAnimation);
    }

    private class MyAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            SharedPreferences shared = getSharedPreferences("users", MODE_PRIVATE);
            String user_name = shared.getString("user_name", "");
            String user_pwd = shared.getString("user_pwd", "");
            Intent intent = null;
            if (user_name != "" && user_pwd != "") {
                intent = new Intent(WelcomeActivity.this, MainActivity.class);
            } else {
                intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            }
            startActivity(intent);
            finish();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.detector.onTouchEvent(event);
    }


    private class MyOnGestureListener implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            if (e1.getY() - e2.getY() > 120) {
                welcomeImg.setVisibility(View.INVISIBLE);
            }
            return true;
        }
    }

    /**
     * 返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //在欢迎界面屏蔽BACK键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return false;
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

}