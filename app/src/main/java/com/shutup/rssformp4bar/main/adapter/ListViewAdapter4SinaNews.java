package com.shutup.rssformp4bar.main.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.graphics.drawable.VectorDrawableCompat;

import com.shutup.rssformp4bar.R;
import com.shutup.rssformp4bar.rss_custom.RSSItem;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by shutup on 16/3/24.
 */
public class ListViewAdapter4SinaNews extends ListViewAdapter {
    public ListViewAdapter4SinaNews(Context context, List<RSSItem> data) {
        super(context, data);
    }

    @Override
    public String getTitle(RSSItem rssItem) {
        return rssItem.getTitle();
    }

    @Override
    public String getDescription(RSSItem rssItem) {
        return rssItem.getDescription().replaceAll("\\s","");
    }

    @Override
    public String getPubDate(RSSItem rssItem) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return simpleDateFormat.format(rssItem.getPubDate());
    }

    @Override
    public String getLink(RSSItem rssItem) {
        String temp = rssItem.getLink().toString();
        String result = temp.substring(temp.indexOf("=")+1);
        return result;
    }

    @Override
    public Uri getImgUrl(RSSItem rssItem) {
        return null;
    }

    @Override
    public Drawable getImageDrawable() {
        VectorDrawableCompat vectorDrawableCompat = VectorDrawableCompat.create(context.getResources(), R.drawable.icon_sina, context.getTheme());
        return vectorDrawableCompat;
    }
}
