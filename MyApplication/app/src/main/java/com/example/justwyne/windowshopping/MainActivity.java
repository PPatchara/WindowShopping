package com.example.justwyne.windowshopping;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justwyne.windowshopping.models.Event;
import com.example.justwyne.windowshopping.models.EventList;
import com.example.justwyne.windowshopping.models.SavingList;

public class MainActivity extends BaseActivity {
    private final String TAG = "MainActivity";
    private EventList eventList;
    private Event event;
    private SavingList savingList;

    String ipAddress;

    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener accelListener;
    private double gap;
    private String state = "TiltDown";

    private TextView tvName, tvDescription, tvPlace, tvDate, tvTime, tvPrice;
    private Button btnSave, btnEventList;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        acceleroListener();
        ipAddress = getIpAddress();
        System.out.println(ipAddress);
        setupData();
        initInstance();
        renderEvent();

    }

    private void setupData(){
        eventList = EventList.getInstance();
        eventList.loadData(getResources().openRawResource(R.raw.event));
    }

    private void initInstance() {
        savingList = SavingList.getInstance();
        tvName = (TextView) findViewById(R.id.tvEvent);
        tvDescription = (TextView) findViewById(R.id.tvEventDescription);
        tvPlace = (TextView) findViewById(R.id.tvPlace);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvPrice = (TextView) findViewById(R.id.tvPrices);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savingList.add(event);
                String id = savingList.getEvent(event).getId();
                Toast.makeText(MainActivity.this,id + "saved",Toast.LENGTH_SHORT).show();
            }
        });

        btnEventList = (Button) findViewById(R.id.btnEventList);
        btnEventList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (savingList.size() == 0) {
                    Intent intent = new Intent(MainActivity.this, EmptyActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(MainActivity.this, EventListActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void renderEvent() {
//            JSONObject productObject = getProduct();
            event = eventList.getEvent("#001");

            tvName.setText(event.getName());
            tvDescription.setText(event.getDescription());
            tvPlace.setText(event.getPlace());
            tvDate.setText(event.getDate());
            tvTime.setText(event.getTime());
            tvPrice.setText(event.getPrice());
    }

    private void acceleroListener() {
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        accelListener = new SensorEventListener() {
            public void onAccuracyChanged(Sensor sensor, int acc) { }

            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                gap = Math.abs(y - z);

                Log.d(TAG, (int) x + " " + (int) y + " " + (int) z + " " + (int)gap);

                if (gap > 8 && gap < 11) {
                    sendIntent();
                }
            }
        };
    }

    private void sendTilt(String state) {
            String json_data = String.format("{\"action\": \"%s\"}",state);
            Log.e(TAG, json_data);
            webSocket.send(json_data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Cycle", "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(accelListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

//    public void onStop() {
//        super.onStop();
//        sensorManager.unregisterListener(accelListener);
//    }

    private void sendIntent() {
        sensorManager.unregisterListener(accelListener);
        Intent intent = new Intent(MainActivity.this, TouchpadActivity.class);
        startActivity(intent);
        sendTilt(state);
    }

}
