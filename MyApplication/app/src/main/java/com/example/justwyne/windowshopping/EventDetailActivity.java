package com.example.justwyne.windowshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justwyne.windowshopping.models.Event;
import com.example.justwyne.windowshopping.models.SavingList;

import java.util.ArrayList;

public class EventDetailActivity extends AppCompatActivity {
    private SavingList savingList;
    private Event event;
    private TextView tvName, tvDescription, tvPlace, tvDate, tvTime, tvPrice;
    String eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        Intent intent = getIntent();
        eventId = intent.getStringExtra("eventId");
        Log.d("eventId",eventId);

        setupData();
        initInstance();
        renderEvent();
    }

    private void setupData() {
        savingList = SavingList.getInstance();
    }

    private void initInstance() {
        tvName = (TextView) findViewById(R.id.tvEvent);
        tvDescription = (TextView) findViewById(R.id.tvEventDescription);
        tvPlace = (TextView) findViewById(R.id.tvPlace);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvPrice = (TextView) findViewById(R.id.tvPrices);

    }

    private void renderEvent() {
        event = savingList.getEvent(eventId);
        tvName.setText(event.getName());
        tvDescription.setText(event.getDescription());
        tvPlace.setText(event.getPlace());
        tvDate.setText(event.getDate());
        tvTime.setText(event.getTime());
        tvPrice.setText(event.getPrice());
    }
}
