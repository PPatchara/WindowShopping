package com.example.justwyne.windowshopping;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.java_websocket.WebSocketImpl;
import java.net.URI;
import java.net.URISyntaxException;


import websocket.ControlWebSocket;

/**
 * Created by Justwyne on 8/16/16 AD.
 */
public class BaseActivity extends AppCompatActivity{

    protected String location = "ws://10.200.249.83:8887"; //change IP when connect internet
    protected URI uri;
    protected ControlWebSocket webSocket;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Cycle", "onDestroy");
        webSocket.close();
    }

}
