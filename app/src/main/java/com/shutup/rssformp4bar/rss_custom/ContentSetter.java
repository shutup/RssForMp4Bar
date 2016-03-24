package com.shutup.rssformp4bar.rss_custom;

/**
 * Created by shutup on 16/3/24.
 */
public interface ContentSetter extends Setter {
    /**
     * Set the field of an object which represents an RSS element.
     */
    void set(String value);
}
