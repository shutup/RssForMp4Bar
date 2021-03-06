package com.shutup.rssformp4bar.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.shutup.rssformp4bar.BuildConfig;
import com.shutup.rssformp4bar.R;
import com.shutup.rssformp4bar.base.BaseActivity;
import com.shutup.rssformp4bar.common.Constants;
import com.shutup.rssformp4bar.common.RssUrl;
import com.shutup.rssformp4bar.main.adapter.ListViewAdapter;
import com.shutup.rssformp4bar.main.adapter.ListViewAdapter4Mp4Bar;
import com.shutup.rssformp4bar.main.adapter.ListViewAdapter4SinaNews;
import com.shutup.rssformp4bar.rss_custom.RSSConfig;
import com.shutup.rssformp4bar.rss_custom.RSSHandler4Mp4Bar;
import com.shutup.rssformp4bar.rss_custom.RSSHandler4SinaNews;
import com.shutup.rssformp4bar.rss_custom.RSSParser;
import com.shutup.rssformp4bar.rss_custom.RSSParserCustom;
import com.shutup.rssformp4bar.setting.SettingActivity;

import com.shutup.rssformp4bar.rss_custom.RSSFeed;
import com.shutup.rssformp4bar.rss_custom.RSSItem;
import com.shutup.rssformp4bar.rss_custom.RSSReader;

import org.apache.http.impl.client.DefaultHttpClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements Constants {

    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private List<RSSItem> data;
    HandlerThread rssThread = null;
    Handler rssHandler = null;
    Handler mainHandler = null;
    Map<Integer,ListViewAdapter> adapterMap = null;
    private int currenttype = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        processToolBar();
        initListAdapter();
        initEvents();
    }

    private void initListAdapter() {
        adapterMap = new HashMap<>();
        adapterMap.put(Rss4Mp4Bar,new ListViewAdapter4Mp4Bar(this,data));
        adapterMap.put(Rss4SinaNews,new ListViewAdapter4SinaNews(this,data));

    }

    private void processToolBar() {
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_setting:
                        rssHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                fetchRssItems(Rss4Mp4Bar);
                            }
                        });

//                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                        break;
                }
                return true;
            }
        });
    }


    private void initEvents() {

        //contact to the main thread
        mainHandler = new Handler(Looper.getMainLooper(), new RssCallBack());
        //get data in the rss thread in case stuck the main thread
        rssThread = new HandlerThread("rss");
        rssThread.start();
        //contact to the rss thread
        rssHandler = new Handler(rssThread.getLooper());

        rssHandler.post(new Runnable() {
            @Override
            public void run() {
                fetchRssItems(Rss4SinaNews);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RSSItem rssItem = data.get(position);
                Intent intent = new Intent(MainActivity.this, ItemDetailActivity.class);
                intent.putExtra(Constants.IntentIdentify, adapterMap.get(currenttype).getLink(rssItem));
                startActivity(intent);
            }
        });
    }

    //the handler callback
    class RssCallBack implements Handler.Callback {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == Rss4Mp4Bar) {
                ListViewAdapter adapter = new ListViewAdapter4Mp4Bar(MainActivity.this, data);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                if (BuildConfig.DEBUG) Log.d("RssCallBack", "ListViewAdapter4Mp4Bar data changed");
            }else if (msg.what == Rss4SinaNews) {
                ListViewAdapter adapter = new ListViewAdapter4SinaNews(MainActivity.this, data);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                if (BuildConfig.DEBUG) Log.d("RssCallBack", "ListViewAdapter4SinaNews data changed");
            }
            return true;
        }
    }

    private void fetchRssItems(int type){
        currenttype = type;
        RSSReader rssReader = null;
        if (type == Rss4Mp4Bar){
            rssReader = new RSSReader(new DefaultHttpClient(), new RSSParserCustom(new RSSHandler4Mp4Bar(new RSSConfig())));
        }else if (type == Rss4SinaNews){
            rssReader = new RSSReader(new DefaultHttpClient(), new RSSParserCustom(new RSSHandler4SinaNews(new RSSConfig())));
        }
        try {
            RSSFeed rssFeed = rssReader.load(new RssUrl().getRssUrl(type));
            data = rssFeed.getItems();
            mainHandler.sendEmptyMessage(type);
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "e:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
