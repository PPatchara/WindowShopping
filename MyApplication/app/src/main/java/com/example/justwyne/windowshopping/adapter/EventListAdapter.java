package com.example.justwyne.windowshopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justwyne.windowshopping.EventListActivity;
import com.example.justwyne.windowshopping.R;
import com.example.justwyne.windowshopping.models.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justwyne on 10/3/2016 AD.
 */

public class EventListAdapter extends BaseAdapter {
    private Context context;
    private List<Event> eventList;

    public EventListAdapter(Context context, List<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @Override
    public int getCount() {
        return eventList.size();
    }

    @Override
    public Object getItem(int i) {
        return eventList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null)
            view = mInflater.inflate(R.layout.list_events_layout, parent, false);

        final Event event = eventList.get(position);

        final TextView eventName = (TextView)view.findViewById(R.id.tvEventName);
        eventName.setText(event.getName());

        final TextView subCategory = (TextView)view.findViewById(R.id.tvEventSubCategory);
        subCategory.setText(event.getSubcategory());

        return view;
    }
}
