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
    public static final int MIN_SWIPE_TIME = 160;

    private int oldX = 0;
    private int oldY = 0;
    private int touchState = 0;

    float initialX, initialY;
    long startTime = 0;
    String TAG = "onTouchEvent";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public boolean onTouchEvent(MotionEvent motionEvent)
    {
        int current_x=(int) motionEvent.getX();
        int current_y=(int) motionEvent.getY();
        int action = motionEvent.getActionMasked();

        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                Log.d("DEBUG", "On touch (down)" + String.valueOf(current_x) + ", " + String.valueOf(current_y));

                if (startTime == 0){
                    startTime = System.currentTimeMillis();
                }

                initialX = current_x;
                initialY = current_y;
                touchState = 1;

                break;
            case MotionEvent.ACTION_UP:
                float finalX = motionEvent.getX();
                long endTime = (System.currentTimeMillis() - startTime);
                Log.d("DEBUG", "On touch (up)" + String.valueOf(current_x) + ", " + String.valueOf(current_y) + " : " + endTime);

                if (endTime < MIN_SWIPE_TIME && touchState == 0) {
                    if (initialX < finalX) {
                        Log.d("On touch", "Left to Right swipe performed: Forward");
                        sendSwipe(initialX,finalX);
                    }
                    else if (initialX > finalX) {
                        Log.d("On touch", "Right to Left swipe performed: Backward");
                        sendSwipe(initialX,finalX);
                    }
                }
                else if (oldX == current_x && oldY == current_y && touchState == 1) {
                    sendMouseTapped();
                }
                startTime = 0;

                break;
            case MotionEvent.ACTION_MOVE:
                if (System.currentTimeMillis() - startTime >= MIN_SWIPE_TIME) {
                    Log.d("DEBUG", "On touch (move)" + String.valueOf(current_x) + ", " + String.valueOf(current_y));

                    int deltaX = current_x - oldX;
                    int deltaY = current_y - oldY;

                    sendMouseMove(deltaX, deltaY);
                }
                touchState = 0;
                break;
        }
//        if( webSocket != null && motionEvent.getAction() != MotionEvent.ACTION_DOWN && motionEvent.getAction() != MotionEvent.ACTION_UP) {

//            int deltaX = current_x - oldX;
//            int deltaY = current_y - oldY;
//
//            oldX = current_x;
//            oldY = current_y;

//        } else
//        if(webSocket != null && motionEvent.getAction() == MotionEvent.ACTION_UP && touchState == 1) {
//            try {
//                String json_data = String.format("{\"action\": \"tap\", \"pos_x\": %d, \"pos_y\":%d}", current_x, current_y);
//                webSocket.send(json_data);
//            } catch(NotYetConnectedException e) {
//                Toast.makeText(this, "Disconnected!", Toast.LENGTH_LONG).show();
//                e.printStackTrace();
//
//            }
//            Log.d("DEBUG", "Tapped");
//        }

        oldX = current_x;
        oldY = current_y;
        return true;
    }

    private void sendMouseMove(int deltaX, int deltaY) {
        String json_data = String.format("{\"action\": \"pos\", \"delta_x\": %d, \"delta_y\":%d}", deltaX, deltaY);
        webSocket.send(json_data);
    }

    private void sendMouseTapped() {
        Log.d("On touch (tap)", "Tapped");
        String json_data = String.format("{\"action\": \"tap\"}");
        webSocket.send(json_data);
    }

    private void sendSwipe(float initialX,float finalX){
        if (initialX < finalX){
            String json_data = String.format("{\"action\": \"forward swipe\"}");
            webSocket.send(json_data);
        }else if (initialX > finalX){
            String json_data = String.format("{\"action\": \"backward swipe\"}");
            webSocket.send(json_data);
        }
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
