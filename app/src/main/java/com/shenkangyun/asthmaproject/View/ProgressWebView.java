package com.shenkangyun.asthmaproject.View;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.shenkangyun.asthmaproject.R;


/**
 * @Description: 带进度条的WebView
 * @author rilintech
 */ 
@SuppressWarnings("deprecation")
public class ProgressWebView extends WebView {

	private ProgressBar progressbar;
	public ProgressWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		//为WebView设置进度条
		progressbar = new ProgressBar(context, null,
				android.R.attr.progressBarStyleHorizontal);
		progressbar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				10, 0, 0));
		
		Drawable drawable = context.getResources().getDrawable(R.drawable.progress_bar_states);
		progressbar.setProgressDrawable(drawable);
		addView(progressbar);
		 //为WebView设置WebViewClient处理某些操作 
		setWebViewClient(new WebViewClient());
		setWebChromeClient(new WebChromeClient());
		getSettings().setJavaScriptEnabled(true);
		//是否支持缩放
		getSettings().setSupportZoom(true);
		getSettings().setBuiltInZoomControls(true);
        //设置webview自适应屏幕大小
		getSettings().setUseWideViewPort(true);
		getSettings().setLoadWithOverviewMode(true);
	}

	public class WebChromeClient extends android.webkit.WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			if (newProgress == 100) {
				progressbar.setVisibility(GONE);
			} else {
				if (progressbar.getVisibility() == GONE)
					progressbar.setVisibility(VISIBLE);
				progressbar.setProgress(newProgress);
			}
			super.onProgressChanged(view, newProgress);
		}

	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
		lp.x = l;
		lp.y = t;
		progressbar.setLayoutParams(lp);
		super.onScrollChanged(l, t, oldl, oldt);
	}
}