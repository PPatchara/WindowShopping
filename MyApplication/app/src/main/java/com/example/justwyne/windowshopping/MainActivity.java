package com.example.justwyne.windowshopping;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends BaseActivity {
    private final String TAG = "MainActivity";
    private static final int REQ_CODE = 001;
    public static final int MIN_SWIPE_TIME = 350;

    private int touchState = 0;
    float initialX, initialY;
    long startTime = 0;
    String ipAddress;

    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener accelListener;
    private double gap;
    private boolean tiltUp = true;
    private String state = "TiltUp";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ipAddress = getIpAddress();
        System.out.println(ipAddress);
        acceleroListener();
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

                if (gap > 4 && gap < 11) {
                    sendIntent();
                }
//                    if (y > 4) {
//                        Log.e(TAG, "Up");
//                        tiltUp = false;
//                    }
//                if (-6 > gap && gap > -10 ) {
//                    tiltUp = false;
//                    isTiltUp();
//                }

            }
        };
    }

    private void sendTilt(String state) {
            String json_data = String.format("{\"action\": \"%s\"}",state);
            Log.e(TAG, json_data);
            webSocket.send(json_data);
    }

//    public String getIpAddress() {
//        //Display the IP address, obtained from function getIPAddress, using textview
//        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
//        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//        int ip = wifiInfo.getIpAddress();
//        return String.format("%d.%d.%d.%d", (ip & 0xff), (ip >> 8 & 0xff), (ip >> 16 & 0xff), (ip >> 24 & 0xff));
//    }

//    public boolean onTouchEvent(MotionEvent motionEvent)
//    {
//        int current_x=(int) motionEvent.getX();
//        int current_y=(int) motionEvent.getY();
//        int action = motionEvent.getActionMasked();
//
//        switch (action)
//        {
//            case MotionEvent.ACTION_DOWN:
//                Log.d("DEBUG", "On touch (down)" + String.valueOf(current_x) + ", " + String.valueOf(current_y));
//
//                if (startTime == 0){
//                    startTime = System.currentTimeMillis();
//                }
//
//                initialX = current_x;
//                initialY = current_y;
//                touchState = 1;
//
//                break;
//            case MotionEvent.ACTION_UP:
//                float finalX = motionEvent.getX();
//                long endTime = (System.currentTimeMillis() - startTime);
//                Log.d("DEBUG", "On touch (up)" + String.valueOf(current_x) + ", " + String.valueOf(current_y) + " : " + endTime);
//
//                if (endTime < MIN_SWIPE_TIME && touchState == 0) {
//                    sendSwipe(initialX,finalX);
//                }
//                else if (oldX == current_x && oldY == current_y && touchState == 1) {
//                    sendMouseTapped();
//                }
//                startTime = 0;
//
//                break;
//            case MotionEvent.ACTION_MOVE:
//                if (System.currentTimeMillis() - startTime >= MIN_SWIPE_TIME) {
//                    Log.d("DEBUG", "On touch (move)" + String.valueOf(current_x) + ", " + String.valueOf(current_y));
//
//                    int deltaX = current_x - oldX;
//                    int deltaY = current_y - oldY;
//
//                    sendMouseMove(deltaX, deltaY);
//                }
//                touchState = 0;
//                break;
//        }
//
//        oldX = current_x;
//        oldY = current_y;
//        return true;
//    }
//
//    private void sendMouseMove(int deltaX, int deltaY) {
//        String json_data = String.format("{\"action\": \"pos\", \"delta_x\": %d, \"delta_y\": %d, \"ip\": %s}", deltaX, deltaY, ipAddress);
//        webSocket.send(json_data);
//    }
//
//    private void sendMouseTapped() {
//        Log.d("On touch (tap)", "Tapped");
//        String json_data = String.format("{\"action\": \"tap\", \"ip\": %s}", ipAddress);
//        webSocket.send(json_data);
//    }
//
//    private void sendSwipe(float initialX,float finalX){
//        if (initialX < finalX){
//            String json_data = String.format("{\"action\": \"forward_swipe\", \"ip\": %s}", ipAddress);
//            webSocket.send(json_data);
//        }else if (initialX > finalX){
//            String json_data = String.format("{\"action\": \"backward_swipe\", \"ip\": %s}", ipAddress);
//            webSocket.send(json_data);
//        }
//    }


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

    private void sendIntent() {
        Log.d(TAG, "send intent New activity");
        sensorManager.unregisterListener(accelListener);
        Intent intent = new Intent(MainActivity.this, TouchpadActivity.class);
        startActivity(intent);
        sendTilt(state);
    }

}
