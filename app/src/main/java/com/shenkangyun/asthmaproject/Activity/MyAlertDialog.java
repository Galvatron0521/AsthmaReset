package com.shenkangyun.asthmaproject.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shenkangyun.asthmaproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAlertDialog extends Activity implements View.OnClickListener {

    @BindView(R.id.title)
    TextView mTextView;
    @BindView(R.id.btn_ok)
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        mButton.setOnClickListener(this);
    }

    private void initData() {
        String msg = getIntent().getStringExtra("msg");
        String title = getIntent().getStringExtra("title");
        if (msg != null)
            ((TextView) findViewById(R.id.alert_message)).setText(msg);
        if (title != null) {
            mTextView.setText(title);
        }
        mTextView.setTextColor(getResources().getColor(R.color.theme_background));
    }

    @Override
    public void onClick(View arg0) {
        Intent intent = new Intent();
        setResult(1, intent);
        finish();
    }
}
