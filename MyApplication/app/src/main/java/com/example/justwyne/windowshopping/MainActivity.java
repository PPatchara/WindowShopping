package com.example.justwyne.windowshopping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import java.nio.channels.NotYetConnectedException;

public class MainActivity extends BaseActivity {
    private static final int REQ_CODE = 001;

    private int oldX = 0;
    private int oldY = 0;
    private int touchState = 0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                String json_data = String.format("{\"action\": \"pos\", \"delta_x\": %d, \"delta_y\":%d}", deltaX, deltaY);
                webSocket.send(json_data);
            } catch(NotYetConnectedException e) {
                Toast.makeText(this, "Disconnected!", Toast.LENGTH_LONG).show();
                e.printStackTrace();

            }
        }else if(webSocket != null && motionEvent.getAction() == MotionEvent.ACTION_UP && touchState == 1){

            try {
                String json_data = String.format("{\"action\": \"tap\", \"pos_x\": %d, \"pos_y\":%d}", current_x, current_y);
                webSocket.send(json_data);
            } catch(NotYetConnectedException e) {
                Toast.makeText(this, "Disconnected!", Toast.LENGTH_LONG).show();
                e.printStackTrace();

            }
            Log.d("DEBUG", "Tapped");
        }
        return true;
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
}
