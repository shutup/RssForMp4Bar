package com.shutup.rssformp4bar.main.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.shutup.rssformp4bar.BuildConfig;
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
        if (convertView != null) {
            viewHolder = new ViewHolder(convertView);
        } else {
            convertView = layoutInflater.inflate(R.layout.listview_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        RSSItem rssItem = data.get(position);
        String rssItemDescription = rssItem.getDescription();
        viewHolder.itemImg.setImageURI(parseImageUri(rssItemDescription));
        viewHolder.itemTitle.setText(rssItem.getTitle());
        viewHolder.itemTitle.setSelected(true);
        viewHolder.itemContent.setText(getDescriptionString(rssItemDescription));
        viewHolder.pubDate.setText(rssItem.getPubDate().toString());
        return convertView;
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
                description = "暂无剧情简介！";
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
}

