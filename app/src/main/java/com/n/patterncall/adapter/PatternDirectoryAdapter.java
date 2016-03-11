package com.n.patterncall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.n.patterncall.R;
import com.n.patterncall.model.PatternDirectory;

import java.util.List;

/**
 * Created by N on 2016-03-08.
 */
public class PatternDirectoryAdapter extends ArrayAdapter<PatternDirectory> {

    public PatternDirectoryAdapter(Context context, int resource, List<PatternDirectory> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PatternDirectory patternDirectory = getItem(position);

        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_pattern_call_list, parent, false);

            viewHolder.pattern = (TextView) convertView.findViewById(R.id.pattern_call_tv_pattern);
            viewHolder.value = (TextView) convertView.findViewById(R.id.pattern_call_tv_value);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.pattern.setText("Key : " + patternDirectory.getPattern());
        viewHolder.value.setText("Data : " + patternDirectory.getRelatedValue());

        return convertView;
    }

    private static class ViewHolder {
        TextView pattern;
        TextView value;
    }
}
