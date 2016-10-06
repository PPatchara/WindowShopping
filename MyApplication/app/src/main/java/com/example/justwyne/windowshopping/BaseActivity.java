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

    protected String location = "ws://10.200.231.40:8887"; //change IP when connect internet
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Cycle", "onDestroy");
        webSocket.close();
    }

}
