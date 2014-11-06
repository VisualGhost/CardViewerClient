package com.cardviewer.client.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * ItemAdapter
 */
public class ItemAdapter extends BaseAdapter {

    private List<Item> mItems;
    private Context mContext;

    public ItemAdapter(final Context context) {
        mItems = new ArrayList<Item>();
        mContext = context;
    }

    public void add(Item item) {
        mItems.add(item);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(final int position) {
        return mItems.get(position);
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
        viewHolder.title.setText(mItems.get(position).getName());
        return rowView;
    }

    static class ViewHolder {
        TextView title;
    }
}
