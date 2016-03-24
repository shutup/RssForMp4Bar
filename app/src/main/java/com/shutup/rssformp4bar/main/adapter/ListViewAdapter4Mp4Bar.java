package com.shutup.rssformp4bar.main.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import com.shutup.rssformp4bar.BuildConfig;
import com.shutup.rssformp4bar.R;
import com.shutup.rssformp4bar.rss_custom.RSSItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by shutup on 16/3/24.
 */
public class ListViewAdapter4Mp4Bar extends ListViewAdapter {

    private Context context;

    public ListViewAdapter4Mp4Bar(Context context, List<RSSItem> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    public String getTitle(RSSItem rssItem) {
        return rssItem.getTitle();
    }

    @Override
    public String getDescription(RSSItem rssItem ) {
        String rssItemDescription = rssItem.getDescription();
        return getDescriptionString(rssItemDescription);
    }

    @Override
    public Uri getImgUrl(RSSItem rssItem ) {
        String rssItemDescription = rssItem.getDescription();
        return parseImageUri(rssItemDescription);
    }

    @Override
    public String getPubDate(RSSItem rssItem ) {
        return parsePubDate(rssItem.getPubDate());
    }

    //parse the description
    private String getDescriptionString(String str) {
        Spanned htmlSpanned = Html.fromHtml(str);
        String htmlString = htmlSpanned.toString();
        String htmlStringArray[] = htmlString.split("\n");
        String description ="";
        for (int index = 0; index < htmlStringArray.length; index++) {
            String s = htmlStringArray[index];
            if (s.contains("剧情简介") || s.contains("剧情介绍") || s.contains("简　　介")){
                description = htmlStringArray[index+1];
                if (description.matches("")){
                    description = htmlStringArray[index+2];
                }
                break;
            }else {
                description = this.context.getResources().getString(R.string.item_no_description);
            }
        }
        return description;
    }

    //parse the image Uri
    private Uri parseImageUri(String imageUri) {
        String strings[] = imageUri.split("\n");
        for (String temp:strings) {
            if (temp.contains("<img")){
                imageUri = temp;
                break;
            }
        }
        int startIndex = imageUri.indexOf("src=\"");
        int lastIndex = imageUri.indexOf(".jpg");
        try {
            String result = imageUri.substring(startIndex + 5, lastIndex + 4);
            return Uri.parse(result);
        }catch (Exception e){
            if (BuildConfig.DEBUG) Log.d("ListViewAdapter", imageUri);
        }
        return null;
    }

    private String parsePubDate(Date pubDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return simpleDateFormat.format(pubDate);
    }
}
