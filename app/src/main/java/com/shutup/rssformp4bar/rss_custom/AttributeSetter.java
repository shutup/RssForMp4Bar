package com.shutup.rssformp4bar.rss_custom;

/**
 * Created by shutup on 16/3/24.
 */
public interface AttributeSetter extends Setter{
    /**
     * Set the XML attributes.
     */
    void set(org.xml.sax.Attributes attributes);
}
