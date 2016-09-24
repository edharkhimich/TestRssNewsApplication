package com.edgar.rss_app2.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.edgar.rss_app2.extra.LanProgressDialog;
import com.edgar.rss_app2.adapter.MyAdapter;
import com.edgar.rss_app2.R;

public class WebPageActivity extends AppCompatActivity {

    LanProgressDialog dialog;
    String imageUrl;
    Intent intent;
    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_details);

        dialog = new LanProgressDialog(this);
        dialog.setCancelable(false);

        intent = getIntent();
        imageUrl = intent.getStringExtra(MyAdapter.KEY);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d(WebPageActivity.class.getSimpleName(), "Start load URL : " + url);
                super.onPageStarted(view, url, favicon);
                dialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d(WebPageActivity.class.getSimpleName(), "Finished URL : " + url);
                super.onPageFinished(view, url);
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        Log.d(WebPageActivity.class.getSimpleName(), "intent url: " + imageUrl);
        webView.loadUrl(imageUrl);
    }
}
