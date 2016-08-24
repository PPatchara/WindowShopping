package com.example.justwyne.windowshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PaymentMethodActivity extends AppCompatActivity {
    private Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        initInstances();
    }

    private void initInstances(){
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntent();
            }
        });
    }

    private void sendIntent(){
        Intent intent;
        intent = new Intent(this, ConfirmOrderActivity.class);
        startActivity(intent);
    }
}
