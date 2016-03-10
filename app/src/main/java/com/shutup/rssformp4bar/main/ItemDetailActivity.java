package com.shutup.rssformp4bar.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.shutup.rssformp4bar.R;
import com.shutup.rssformp4bar.base.BaseActivity;
import com.shutup.rssformp4bar.common.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ItemDetailActivity extends BaseActivity implements Constants {

    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);
        processToolBar();


        processExtra();
    }

    private void processToolBar() {
        toolbar.setNavigationIcon(R.drawable.back);
        // App Logo
        toolbar.setLogo(R.mipmap.ic_launcher);
// Title
        toolbar.setTitle("My Title");
// Sub Title
        toolbar.setSubtitle("Sub title");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void processExtra() {
        Intent intent = getIntent();
        if (intent != null) {
            String itemUrl = intent.getStringExtra(Constants.IntentIdentify);
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.loadUrl(itemUrl);
        }
    }

}
