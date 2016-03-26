package com.shutup.rssformp4bar.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shutup on 16/3/6.
 */
public class RssUrl implements Constants{
    private String defaultRssUrl = "http://rss.sina.com.cn/tech/rollnews.xml";
    private static Map<Integer,String> rssUrlMap = null;
    static {
        rssUrlMap = new HashMap<>();
        rssUrlMap.put(Rss4Mp4Bar,"http://www.mp4ba.com/rss.php");
        rssUrlMap.put(Rss4SinaNews,"http://rss.sina.com.cn/tech/rollnews.xml");
    }
    public String getRssUrl() {
        if (rssUrl!=null && rssUrl.equalsIgnoreCase("")){
            return rssUrl;
        }else {
            return defaultRssUrl;
        }
    }
    public String getRssUrl(int type) {
        if (rssUrlMap.get(type) != null){
            return rssUrlMap.get(type);
        }else{
            return defaultRssUrl;
        }
    }

    public void setRssUrl(String rssUrl) {
        this.rssUrl = rssUrl;
    }

    String rssUrl;
}
