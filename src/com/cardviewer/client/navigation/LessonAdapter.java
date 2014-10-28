package com.cardviewer.client.navigation;

import android.content.Context;
import android.content.pm.ProviderInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * LessonAdapter
 */
public class LessonAdapter extends BaseAdapter {

    private List<ProviderInfo> mProviderInfos;
    private Context mContext;

    LessonAdapter(final Context context) {
        mProviderInfos = new ArrayList<ProviderInfo>();
        mContext = context;
    }

    public void add(ProviderInfo providerInfo) {
        mProviderInfos.add(providerInfo);
    }

    @Override
    public int getCount() {
        return mProviderInfos.size();
    }

    @Override
    public ProviderInfo getItem(final int position) {
        return mProviderInfos.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder;
        if (rowView == null || rowView.getTag() == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            viewHolder = new ViewHolder();
            if (rowView != null) {
                viewHolder.title = (TextView) rowView.findViewById(android.R.id.text1);
                rowView.setTag(viewHolder);
            }
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }
        viewHolder.title.setText(mProviderInfos.get(position).authority);
        return rowView;
    }

    static class ViewHolder {
        TextView title;
    }
}
