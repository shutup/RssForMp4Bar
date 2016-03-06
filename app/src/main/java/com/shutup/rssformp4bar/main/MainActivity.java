package com.shutup.rssformp4bar.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shutup.rssformp4bar.BuildConfig;
import com.shutup.rssformp4bar.R;
import com.shutup.rssformp4bar.common.RssUrl;

import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSItem;
import org.mcsoxford.rss.RSSReader;
import org.mcsoxford.rss.RSSReaderException;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.listView)
    ListView listView;
    private List<RSSItem> data;
    HandlerThread rssThread = null;
    Handler rssHandler = null;
    Handler mainHandler = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initEvents();

        rssHandler.post(new Runnable() {
            @Override
            public void run() {
                RSSReader rssReader = new RSSReader();
                try {
                    RSSFeed rssFeed = rssReader.load(new RssUrl().getRssUrl());
                    data = rssFeed.getItems();
                    mainHandler.sendEmptyMessage(1);
                } catch (RSSReaderException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initEvents() {
        //contact to the main thread
        mainHandler = new Handler(Looper.getMainLooper(),new RssCallBack());
        //get data in the rss thread in case stuck the main thread
        rssThread = new HandlerThread("rss");
        rssThread.start();
        //contact to the rss thread
        rssHandler = new Handler(rssThread.getLooper());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    class RssCallBack implements Handler.Callback {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                ListViewAdapter adapter = new ListViewAdapter(MainActivity.this,data);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                if (BuildConfig.DEBUG) Log.d("RssCallBack", "data changed");
            }
            return true;
        }
    }
}
