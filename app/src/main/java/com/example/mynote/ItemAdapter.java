package com.example.mynote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends BaseAdapter {
    private Context context;
    private List<item_class> itemList;

    public ItemAdapter(Context context, List<item_class> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        }

        item_class item = itemList.get(position);

        TextView title = convertView.findViewById(R.id.item_title);
        TextView message = convertView.findViewById(R.id.item_msg);
        TextView time = convertView.findViewById(R.id.item_currenttime);

        title.setText(item.title);
        message.setText(item.msg);
        time.setText(item.time);

        return convertView;
    }
}

