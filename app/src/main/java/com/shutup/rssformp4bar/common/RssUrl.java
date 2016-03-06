package com.shutup.rssformp4bar.common;

/**
 * Created by shutup on 16/3/6.
 */
public class RssUrl {
    private String defaultRssUrl = "http://www.mp4ba.com/rss.php";

    public String getRssUrl() {
        if (rssUrl!=null && rssUrl.equalsIgnoreCase("")){
            return rssUrl;
        }else {
            return defaultRssUrl;
        }
    }

    public void setRssUrl(String rssUrl) {
        this.rssUrl = rssUrl;
    }

    String rssUrl;
}
