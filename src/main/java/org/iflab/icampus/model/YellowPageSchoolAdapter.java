package org.iflab.icampus.model;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.iflab.icampus.R;

import java.util.List;

public class YellowPageSchoolAdapter extends YellowPageAdapter {
    public YellowPageSchoolAdapter() {
        super();
    }

    public YellowPageSchoolAdapter(Context context, List<YelloPageList> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.yellowpage_item_adapter_school, null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textview_SchoolItem);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(getList().get(position).getName());
        return convertView;
    }

    class ViewHolder {
        private TextView textView;
    }
}
