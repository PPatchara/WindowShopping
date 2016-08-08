package com.example.justwyne.windowshopping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import org.java_websocket.WebSocketImpl;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;

import websocket.ControlWebSocket;

public class MainActivity extends Activity {
    private static final int REQ_CODE = 001;

    private String location = "ws://172.20.10.2:8887";
    private ControlWebSocket webSocket;
    private URI uri;

    private int oldX = 0;
    private int oldY = 0;
    private int touchState = 0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebSocketImpl.DEBUG = false;

        try {
            uri = new URI(location);
            webSocket = new ControlWebSocket(this, uri);
            Boolean bool = webSocket.connectBlocking();
            Toast.makeText(this, "You are connected to RemoteServer: " + uri.getRawPath(), Toast.LENGTH_SHORT);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent)
    {
        int current_x=(int) motionEvent.getX();
        int current_y=(int) motionEvent.getY();

        switch (motionEvent.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                Log.d("DEBUG", "On touch (down)" + String.valueOf(current_x) + ", " + String.valueOf(current_y));
                oldX = current_x;
                oldY = current_y;

                touchState = 1;

                break;
            case MotionEvent.ACTION_UP:
                Log.d("DEBUG", "On touch (up)" + String.valueOf(current_x) + ", " + String.valueOf(current_y));
                oldX = current_x;
                oldY = current_y;

                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("DEBUG", "On touch (move)" + String.valueOf(current_x) + ", " + String.valueOf(current_y));

                touchState = 0;

                break;
        }
        if( webSocket != null && motionEvent.getAction() != MotionEvent.ACTION_DOWN && motionEvent.getAction() != MotionEvent.ACTION_UP) {

            int deltaX = current_x - oldX;
            int deltaY = current_y - oldY;

            oldX = current_x;
            oldY = current_y;


            Log.d("DEBUG", "On touch (delta)" + String.valueOf(deltaX) + ", " + String.valueOf(deltaY));

            try {
                String data = String.format("pos,%d,%d", deltaX, deltaY);
                webSocket.send(data);
            } catch(NotYetConnectedException e) {
                Toast.makeText(this, "Disconnected!", Toast.LENGTH_LONG).show();
                e.printStackTrace();

            }
        }else if(webSocket != null && motionEvent.getAction() == MotionEvent.ACTION_UP && touchState == 1){

            try {
                String data = String.format("tap,%d,%d", current_x,current_y);
                webSocket.send(data);
            } catch(NotYetConnectedException e) {
                Toast.makeText(this, "Disconnected!", Toast.LENGTH_LONG).show();
                e.printStackTrace();

            }
            Log.d("DEBUG", "Tapped");
        }
        return true;
    }

    public void trigger() {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        startActivityForResult(intent, REQ_CODE);
        Log.d("MainActivity", "Trigger");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Cycle", "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Cycle", "onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Cycle", "onDestroy");
        webSocket.close();
    }
}
