package com.shutup.rssformp4bar.main.adapter;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.interfaces.DraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.shutup.rssformp4bar.BuildConfig;
import com.shutup.rssformp4bar.R;

import com.shutup.rssformp4bar.rss_custom.RSSItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by shutup on 16/3/6.
 */
public class ListViewAdapter extends BaseAdapter implements ListViewAdapterDataFormat{
    private List<RSSItem> data;
    private Context context;
    private LayoutInflater layoutInflater;

    public ListViewAdapter(Context context, List<RSSItem> data) {
        this.data = data;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView != null) {
            viewHolder = new ViewHolder(convertView);
        } else {
            convertView = layoutInflater.inflate(R.layout.listview_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        RSSItem rssItem = data.get(position);

        viewHolder.itemImg.setImageURI(getImgUrl(rssItem));
        VectorDrawableCompat vectorDrawableCompat = VectorDrawableCompat.create(context.getResources(), R.drawable.icon_sina, context.getTheme());
        viewHolder.itemImg.setImageDrawable(vectorDrawableCompat);
        viewHolder.itemTitle.setText(getTitle(rssItem));
        viewHolder.itemTitle.setSelected(true);
        viewHolder.itemContent.setText(getDescription(rssItem));
        viewHolder.pubDate.setText(getPubDate(rssItem));
        return convertView;
    }

    @Override
    public String getTitle(RSSItem rssItem) {
        return rssItem.getTitle();
    }

    @Override
    public String getDescription(RSSItem rssItem) {
        return rssItem.getDescription();
    }

    @Override
    public String getPubDate(RSSItem rssItem) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return simpleDateFormat.format(rssItem.getPubDate());
    }

    @Override
    public Uri getImgUrl(RSSItem rssItem) {
        return null;
    }


    static class ViewHolder {
        @Bind(R.id.item_img)
        SimpleDraweeView itemImg;
        @Bind(R.id.item_title)
        TextView itemTitle;
        @Bind(R.id.item_content)
        TextView itemContent;
        @Bind(R.id.pubDate)
        TextView pubDate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}

