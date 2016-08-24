package com.example.justwyne.windowshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ConfirmOrderActivity extends AppCompatActivity {
    private Button btnOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        initInstances();
    }

    private void initInstances(){
        btnOrder = (Button) findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntent();
            }
        });
    }

    private void sendIntent(){
        Intent intent;
        intent = new Intent(this, OrderCompleteActivity.class);
        startActivity(intent);
    }
}
