package com.example.justwyne.windowshopping;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.view.GestureDetectorCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class TouchpadActivity extends BaseActivity implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    private final String TAG = "TouchpadActivity";

    private GestureDetectorCompat mDetector;
    private final int MIN_SWIPE_X = 100;
    public static final int MIN_SWIPE_TIME = 500;

    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener accelListener;
    private double gap;
    private boolean tiltDown = true;
    private String state = "TiltUp";

//    private int oldX = 0;
//    private int oldY = 0;
//    private int touchState = 0;
//
//    float initialX, initialY;
//    long startTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touchpad);
        mDetector = new GestureDetectorCompat(this, this);
        mDetector.setOnDoubleTapListener(this);
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

                Log.d(TAG, (int) x + " " + (int) y + " " + (int) z + " " + (int) gap);

                if ((int) gap > -4 && (int) gap < 4) {
                    sendIntent();
                }

//                if (Math.abs(y) > Math.abs(x)) {
//                    if (y < 3) {
//                        Log.e(TAG, "Down");
//                    }
//                    if (y > 4) {
//                        Log.e(TAG, "Up");
//                        tiltDown = false;
//                        isTiltDown();
//                    }
//                }
//                if (-4 < gap && gap < -1 ) {
//                    tiltDown = false;
//                    isTiltDown();
//                }

            }
        };
    }

//    public void isTiltDown() {
//        if (!tiltDown){
//            sendIntent();
//        }
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event) {
//        Log.e(TAG,"onDown: " + event.toString());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        if (Math.abs(velocityX) > MIN_SWIPE_TIME) {
            if (Math.abs(event1.getX() - event2.getX()) > MIN_SWIPE_X) {
                sendSwipe(event1.getX(), event2.getX());
            }
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
//        Log.e(TAG, "onLongPress: " + event.toString());
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
//        sendMouseMove(distanceX, distanceY);
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
//        Log.e(TAG, "onShowPress: " + event.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
//        Log.e(TAG, "onSingleTapUp: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
//        Log.e(TAG, "onDoubleTap: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
//        Log.e(TAG, "onDoubleTapEvent: " + event.toString());
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
//        sendMouseTapped(event.getX(), event.getY());
        return true;
    }

    private void sendMouseMove(float deltaX, float deltaY) {
        String json_data = String.format("{\"action\": \"pos\", \"delta_x\": %f, \"delta_y\": %f, \"ip\": %s}", deltaX, deltaY);
        Log.e(TAG, json_data);
//        webSocket.send(json_data);
    }

    private void sendMouseTapped(float current_x, float current_y) {
        String json_data = String.format("{\"action\": \"tap\", \"pos_x\": %f, \"pos_y\":%f}", current_x, current_y);
        Log.e(TAG, json_data);
//        webSocket.send(json_data);
    }

    private void sendTilt(String state) {
        String json_data = String.format("{\"action\": \"%s\"}",state);
        Log.e(TAG, json_data);
            webSocket.send(json_data);
    }

    private void sendSwipe(float initialX, float finalX) {
        if (initialX < finalX) {
            String json_data = String.format("{\"action\": \"forward_swipe\"}");
            Log.e(TAG, json_data);
            webSocket.send(json_data);
        } else if (initialX > finalX) {
            String json_data = String.format("{\"action\": \"backward_swipe\"}");
            Log.e(TAG, json_data);
            webSocket.send(json_data);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Cycle", "onPause");
        sensorManager.unregisterListener(accelListener);
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
        Log.d(TAG, "Finish");
        sensorManager.unregisterListener(accelListener);
//        finish();
        Intent intent = new Intent(TouchpadActivity.this, MainActivity.class);
        startActivity(intent);
        sendTilt(state);
    }
}
