package com.example.justwyne.windowshopping;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.justwyne.windowshopping.adapter.EventListAdapter;
import com.example.justwyne.windowshopping.models.SavingList;

public class EventListActivity extends AppCompatActivity {
    private EventListAdapter adapter;
    private ListView mListView;
    private SavingList savingList;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        initeData();
        setupView();
        setupEventListView();
    }

    private void initeData () {
        savingList = SavingList.getInstance();
    }

    private void setupView () {
        mListView = (ListView) findViewById(R.id.lvEvent);
    }

    private void setupEventListView() {
        adapter = new EventListAdapter(EventListActivity.this, savingList.getEventList());
        mListView.setAdapter(adapter);
        
    }
}
