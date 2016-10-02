package com.example.justwyne.windowshopping;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.justwyne.windowshopping.models.EventList;

import org.java_websocket.client.WebSocketClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends BaseActivity {
    private final String TAG = "MainActivity";
    private EventList eventList;

    String ipAddress;

    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener accelListener;
    private double gap;
    private String state = "TiltDown";

    private TextView name, description, place, date, time, price;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ipAddress = getIpAddress();
        System.out.println(ipAddress);
        acceleroListener();
        setupData();
        initInstance();
        renderEvent();

    }

    private void setupData(){
        eventList = EventList.getInstance();
        eventList.loadData(getResources().openRawResource(R.raw.event));
    }

    private void initInstance() {
        name = (TextView) findViewById(R.id.tvEventName);
        description = (TextView) findViewById(R.id.tvEventDescription);
        place = (TextView) findViewById(R.id.tvPlace);
        date = (TextView) findViewById(R.id.tvDate);
        time = (TextView) findViewById(R.id.tvTime);
        price = (TextView) findViewById(R.id.tvPrices);
    }

    private void renderEvent() {
//        try {
//            JSONObject productObject = getProduct();
//            product = productList.getProduct(productObject.getString("product_id"));
//            color = product.getColorByName(productObject.getString("color"));
//
//            tvName.setText(product.getName());
//            tvDetails.setText(product.getDetails());
//            tvPrice.setText("$ " + product.getPrice());
//
//            String imageName = color.getImageNameList().get(1);
//            int res = getResources().getIdentifier(imageName, "drawable", getPackageName());
//            ivImage.setImageResource(res);
//
//
//            tvDescription.setText(product.getDescription());
//            tvColor.setText(color.getName());
//
//            createColorChoices();
//            createSizeChoices();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
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
