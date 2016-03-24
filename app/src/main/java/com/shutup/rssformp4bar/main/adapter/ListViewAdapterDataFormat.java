package com.shutup.rssformp4bar.main.adapter;

import android.net.Uri;

import com.shutup.rssformp4bar.rss_custom.RSSItem;

/**
 * Created by shutup on 16/3/24.
 */
public interface ListViewAdapterDataFormat {
    String getTitle(RSSItem rssItem );
    String getDescription(RSSItem rssItem );
    String getPubDate(RSSItem rssItem );
    Uri getImgUrl(RSSItem rssItem );
}
