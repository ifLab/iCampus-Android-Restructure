package org.iflab.icampus.School;

/**
 * 适配器类
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.iflab.icampus.R;

import java.util.List;

public class SchoolAdapter extends BaseAdapter {
    private Context context;
    private List<School> list;

    public SchoolAdapter() {/*构造函数*/
        super();
    }//空的构造函数

    public SchoolAdapter(Context context, List<School> list) {//传入一个context和list
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        ViewHolder viewHolder;
        if (arg1 == null) {
            viewHolder = new ViewHolder();
            arg1 = LayoutInflater.from(context).inflate(R.layout.item_school, null);
            viewHolder.textView = (TextView) arg1.findViewById(R.id.schoolItem);
            arg1.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) arg1.getTag();
        }
        viewHolder.textView.setText(list.get(arg0).getIntroName());
        return arg1;
    }

    class ViewHolder {
        private TextView textView;
    }
}
