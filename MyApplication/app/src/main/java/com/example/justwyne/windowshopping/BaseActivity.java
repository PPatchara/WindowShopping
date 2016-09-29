package com.example.justwyne.windowshopping;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.java_websocket.WebSocketImpl;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;


import websocket.ControlWebSocket;

/**
 * Created by Justwyne on 8/16/16 AD.
 */
public class BaseActivity extends AppCompatActivity{

    protected String location = "ws://10.217.26.58:8887"; //change IP when connect internet
    protected URI uri;
    protected ControlWebSocket webSocket;

//    private SensorManager sensorManager;
//    private Sensor sensor;
//    private SensorEventListener accelListener;
//    private double gap;
//    private boolean tiltUp = true;
//    String TAG = "Tilt";

    public BaseActivity() {
        super();
        WebSocketImpl.DEBUG = false;

        try {
            uri = new URI(location);
            webSocket = new ControlWebSocket(this, uri);
            Boolean bool = webSocket.connectBlocking();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getIpAddress() {
        //Display the IP address, obtained from function getIPAddress, using textview
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return String.format("%d.%d.%d.%d", (ip & 0xff), (ip >> 8 & 0xff), (ip >> 16 & 0xff), (ip >> 24 & 0xff));
    }

//    public boolean acceleroListener() {
//        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
//        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        accelListener = new SensorEventListener() {
//            public void onAccuracyChanged(Sensor sensor, int acc) { }
//
//            public void onSensorChanged(SensorEvent event) {
//                float x = event.values[0];
//                float y = event.values[1];
//                float z = event.values[2];
//                gap = y-x;
//
//                if (Math.abs(y) > Math.abs(x)) {
//                    if (y < 3) {
//                        Log.e(TAG, "Down");
//                        tiltUp = false;
//                    }
//                    if (y > 4) {
//                        Log.e(TAG, "Up");
//                        tiltUp = true;
//                    }
//                }
////                if (-6 > gap && gap > -10 ) {
////                    tiltUp = false;
////                    isTiltUp();
////                }
//
//            }
//        };
//        return tiltUp;
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Cycle", "onDestroy");
        webSocket.close();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        sensorManager.registerListener(accelListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
//    }
//
//    public void onStop() {
//        super.onStop();
//        sensorManager.unregisterListener(accelListener);
//    }

}
