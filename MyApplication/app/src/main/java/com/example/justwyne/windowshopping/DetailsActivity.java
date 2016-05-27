package com.example.justwyne.windowshopping;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        Log.d("Intent", "Details");

//        switchMode();
    }

//    public void switchMode(View view){
//        Intent intent = getIntent();
//        setResult(RESULT_OK,intent);
//        finish();
//    }
}
