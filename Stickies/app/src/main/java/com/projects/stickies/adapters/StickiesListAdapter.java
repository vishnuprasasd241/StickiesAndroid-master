package com.projects.stickies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.projects.stickies.R;
import com.projects.stickies.models.Stickies;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by admin on 21/07/14.
 */
public class StickiesListAdapter extends BaseAdapter {

    ArrayList<Stickies> list;
    Context context;

    public StickiesListAdapter(ArrayList<Stickies> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setList(ArrayList<Stickies> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        RelativeLayout listItemView = (RelativeLayout) view;

        if (listItemView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listItemView = (RelativeLayout)inflater.inflate(R.layout.activity_list_item, viewGroup, false);
        }

        Stickies stickies = list.get(i);
        TextView textViewNotes = (TextView) listItemView.findViewById(R.id.textViewNotes);
        textViewNotes.setText(stickies.getText());
        TextView textViewTime = (TextView) listItemView.findViewById(R.id.textViewTime);
        textViewTime.setText(new SimpleDateFormat("dd MMM yyyy hh:mm:ss").format(stickies.getTime()));
        return listItemView;
    }
}
