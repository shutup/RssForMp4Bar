package com.shutup.rssformp4bar.rss_custom;

import android.util.Log;

import com.shutup.rssformp4bar.BuildConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shutup on 16/3/22.
 */
public class RSSHandler4SinaNews extends RSSHandler {


    /**
     * Instantiate a SAX handler which can parse a subset of RSS 2.0 feeds.
     *
     * @param config configuration for the initial capacities of collections
     */
    public RSSHandler4SinaNews(RSSConfig config) {
        super(config);
    }

    @Override
    public Setter SET_PUBDATE() {
        return new ContentSetter() {

            @Override
            public void set(String pubDate) {
                if (BuildConfig.DEBUG) Log.d("RSSHandler4SinaNews", pubDate);
                final java.util.Date date = parseDate(pubDate);
                if (item == null) {
                    feed.setPubDate(date);
                } else {
                    item.setPubDate(date);
                }
            }
        };
    }

    private Date parseDate(String pubDate)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z",java.util.Locale.ENGLISH);
        try {
            return simpleDateFormat.parse(pubDate);
        } catch (ParseException e) {
            throw new RSSFault(e);
        }
    }
}
