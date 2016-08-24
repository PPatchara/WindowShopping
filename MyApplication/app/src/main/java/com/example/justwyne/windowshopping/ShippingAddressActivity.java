package com.example.justwyne.windowshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ShippingAddressActivity extends AppCompatActivity {
    private Button btnShipMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);

        initInstances();
    }

    private void initInstances(){
        btnShipMethod = (Button) findViewById(R.id.btnShipMethod);
        btnShipMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntent();
            }
        });
    }

    private void sendIntent(){
        Intent intent;
        intent = new Intent(ShippingAddressActivity.this, ShippingMethodActivity.class);
        startActivity(intent);
    }
}
