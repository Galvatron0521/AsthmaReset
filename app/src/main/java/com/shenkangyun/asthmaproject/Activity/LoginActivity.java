package com.shenkangyun.asthmaproject.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.shenkangyun.asthmaproject.R;
import com.shenkangyun.asthmaproject.Utils.ProgressUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.cuowutishi)
    TextView tiShiTextView;
    @BindView(R.id.account_et)
    EditText accountEdit;
    @BindView(R.id.password_iv)
    ImageView passwordIv;
    @BindView(R.id.password_et)
    EditText passwordEdit;
    @BindView(R.id.forget_pssward)
    TextView registerButton;
    @BindView(R.id.btn_login)
    Button login;

    private SharedPreferences shared;
    private SharedPreferences.Editor editor;
    private ImageView visible;
    private AlertDialog dialog;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        StatusBarUtil.setTranslucent(LoginActivity.this, 55);
        activity = this;
        initView();
        initListener();
        shared = getSharedPreferences("users", MODE_PRIVATE);
        editor = shared.edit();

    }

    /**
     * 初始化
     */
    private void initView() {
    }

    /**
     * 各种监听
     */
    private void initListener() {
        login.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        accountEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tiShiTextView.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tiShiTextView.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //登陆
            case R.id.btn_login:
                login();
                break;

            case R.id.forget_pssward:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            default:

                break;
        }
    }

    /**
     * 登陆
     */
    private void login() {
        loginSuccess();
        // 将信息存入到users里面
        String user_name = accountEdit.getText().toString();
        String user_pwd = passwordEdit.getText().toString();

//        if (TextUtils.isEmpty(user_name)) {
//
//            Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_LONG).show();
//            return;
//        }
//        if (TextUtils.isEmpty(user_pwd)) {
//
//            Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_LONG).show();
//            return;
//        }
//
    }

    /**
     * 登陆成功
     */
    public void loginSuccess() {
        ProgressUtil.getInstance().showSuccessProgress(activity, "登录成功");
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
//        saveUserName();
//        passwordEdit.setText("");
    }

    /**
     * 保存登陆的患者
     */
    private void saveUserName() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("data", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", accountEdit.getText().toString());
        editor.commit();
    }

    /**
     * 密码可见
     */
    public void visible(View view) {
        passwordEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        passwordEdit.postInvalidate();
        visible.setVisibility(View.VISIBLE);

    }

    /**
     * 密码不可见
     */
    public void invisible(View view) {
        passwordEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
        passwordEdit.postInvalidate();
        visible.setVisibility(View.GONE);
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