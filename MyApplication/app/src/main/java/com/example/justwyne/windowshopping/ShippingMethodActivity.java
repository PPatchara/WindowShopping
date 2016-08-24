package com.example.justwyne.windowshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ShippingMethodActivity extends AppCompatActivity {
    private Button btnPaymentMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_method);

        initInstances();
    }

    private void initInstances(){
        btnPaymentMethod = (Button) findViewById(R.id.btnPaymentMethod);
        btnPaymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntent();
            }
        });
    }

    private void sendIntent(){
        Intent intent;
        intent = new Intent(this, PaymentMethodActivity.class);
        startActivity(intent);
    }
}
