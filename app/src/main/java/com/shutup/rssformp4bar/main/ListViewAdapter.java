package com.shutup.rssformp4bar.main;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shutup.rssformp4bar.R;

import org.mcsoxford.rss.RSSItem;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by shutup on 16/3/6.
 */
public class ListViewAdapter extends BaseAdapter {
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
        if (convertView != null){
            viewHolder = new ViewHolder(convertView);
        }else{
            convertView = layoutInflater.inflate(R.layout.listview_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        RSSItem rssItem = data.get(position);
        viewHolder.itemTitle.setText(rssItem.getTitle());
        viewHolder.itemContent.setText(rssItem.getContent());
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.item_img)
        ImageView itemImg;
        @Bind(R.id.item_title)
        TextView itemTitle;
        @Bind(R.id.item_content)
        TextView itemContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
