package com.edgar.rss_app2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebPage extends AppCompatActivity {

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
                super.onPageStarted(view, url, favicon);
                dialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(dialog!=null){
                    dialog.dismiss();
                }
            }
        });
        webView.loadUrl(imageUrl);
    }
}
