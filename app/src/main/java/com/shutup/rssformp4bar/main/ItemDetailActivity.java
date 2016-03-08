package com.shutup.rssformp4bar.main;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.shutup.rssformp4bar.R;
import com.shutup.rssformp4bar.base.BaseActivity;
import com.shutup.rssformp4bar.common.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ItemDetailActivity extends BaseActivity implements Constants{

    @Bind(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);
        processExtra();
    }

    private void processExtra() {
        Intent intent = getIntent();
        if (intent != null){
            String itemUrl = intent.getStringExtra(Constants.IntentIdentify);
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.loadUrl(itemUrl);
        }
    }

}
