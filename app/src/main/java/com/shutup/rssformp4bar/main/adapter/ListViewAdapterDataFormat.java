package com.shutup.rssformp4bar.main.adapter;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.shutup.rssformp4bar.rss_custom.RSSItem;

/**
 * Created by shutup on 16/3/24.
 */
public interface ListViewAdapterDataFormat {
    String getTitle(RSSItem rssItem );
    String getDescription(RSSItem rssItem );
    String getPubDate(RSSItem rssItem );
    String getLink(RSSItem rssItem);

    Uri getImgUrl(RSSItem rssItem );
    Drawable getImageDrawable();
}
