package com.example.justwyne.windowshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.justwyne.windowshopping.adapter.EventListAdapter;
import com.example.justwyne.windowshopping.models.Event;
import com.example.justwyne.windowshopping.models.SavingList;

import java.util.ArrayList;

public class EventListActivity extends AppCompatActivity {
    private EventListAdapter adapter;
    private ListView listItemView;
    private SavingList savingList;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        setupData();
        setupView();
        setupEventListView();
    }

    private void setupData () {
        savingList = SavingList.getInstance();
    }

    private void setupView () {
        listItemView = (ListView) findViewById(R.id.lvEvent);
    }

    private void setupEventListView() {
        adapter = new EventListAdapter(EventListActivity.this, savingList.getEventList());
        listItemView.setAdapter(adapter);
        listItemView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event selectedEvent = (Event) listItemView.getItemAtPosition(position);
                String eventId = selectedEvent.getId();
                Toast.makeText(EventListActivity.this,"clicked" + eventId,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EventListActivity.this, EventDetailActivity.class);
                intent.putExtra("eventId",eventId);
                startActivity(intent);
            }
        });
    }
}
