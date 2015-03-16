package org.iflab.icampus.model;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.iflab.icampus.R;

import java.util.List;

public class YellowPagePersonAdapter extends YellowPageAdapter {
    public YellowPagePersonAdapter() {
        super();
    }

    public YellowPagePersonAdapter(Context context, List<YelloPageList> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.yellowpage_item_adapter_person, null);
            viewHolder.telName = (TextView) convertView.findViewById(R.id.textview_TelName);
            viewHolder.telNum = (TextView) convertView.findViewById(R.id.textview_TelNum);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.telName.setText(getList().get(position).getName());
        viewHolder.telNum.setText(getList().get(position).getTelnum());
        return convertView;
    }

    class ViewHolder {
        TextView telName;
        TextView telNum;
    }
}
