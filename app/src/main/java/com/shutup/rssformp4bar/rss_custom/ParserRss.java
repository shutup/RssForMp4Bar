package com.shutup.rssformp4bar.rss_custom;

/**
 * Created by shutup on 16/3/24.
 */
public interface ParserRss {
    //将属性的解析接口以函数的形式提供
    Setter SET_TITLE();
    Setter SET_DESCRIPTION();
    Setter SET_CONTENT();
    Setter SET_LINK();
    Setter SET_PUBDATE();
    Setter SET_LAST_BUILD_DATE();
    Setter SET_TTL();
    Setter ADD_CATEGORY();
    Setter ADD_MEDIA_THUMBNAIL();
    Setter SET_ENCLOSURE();

}
