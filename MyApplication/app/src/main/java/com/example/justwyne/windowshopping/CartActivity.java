package com.example.justwyne.windowshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.justwyne.windowshopping.models.Cart;
import com.example.justwyne.windowshopping.models.Color;
import com.example.justwyne.windowshopping.models.Product;
import com.example.justwyne.windowshopping.models.ProductOrder;

public class CartActivity extends AppCompatActivity {
    private Button btnCheckout;
    private LinearLayout linearLayoutOrder;
    private LinearLayout.LayoutParams paramsOrder;
    private LinearLayout.LayoutParams paramsBtnRemove;
    private TextView tvProductOrder;
    private TextView tvSubTotal;
    private TextView tvTotal;
    private Button btnRemove;
    private Cart cart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cart = Cart.getInstance();
        initInstances();
        renderProductOrder();

    }

    private void initInstances(){
        tvSubTotal = (TextView) findViewById(R.id.tvSubTotal);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
        linearLayoutOrder = (LinearLayout) findViewById(R.id.cartLayout);
        paramsOrder = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsBtnRemove = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        btnCheckout = (Button) findViewById(R.id.btnCheckout);
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntent();
            }
        });
    }

    private void renderProductOrder(){
        for (ProductOrder productOrder : cart.getProductList()) {

            final Product product = productOrder.getProduct();
            final Color color = productOrder.getColor();
            final String size = productOrder.getSize();
            final int quantity = productOrder.getQuantity();

            final LinearLayout orderLayout = new LinearLayout(this);
            orderLayout.setOrientation(LinearLayout.HORIZONTAL);
            final LinearLayout btnRemoveLayout = new LinearLayout(this);
            btnRemoveLayout.setOrientation(LinearLayout.HORIZONTAL);

            final View view = new View(this);

            tvProductOrder = new TextView(this);
            tvProductOrder.setText(String.format("Product ID: %s\nProduct: %s\nColor: %s\nSize: %s\nQuantity %s", product.getId(), product.getName(), color.getName(), size, quantity));
            tvProductOrder.setTextColor(android.graphics.Color.parseColor("#000000"));

            for(int index=0; index < cart.size(); index++)
            {

                btnRemove = new Button(this);
                btnRemove.setId(index+1);
                btnRemove.setText("remove");
                paramsBtnRemove.setMargins(0, 0, 20, 0);
                btnRemove.setLayoutParams(paramsBtnRemove);

                // Set click listener for button
                btnRemove.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        cart.remove(product,color,size);
                        linearLayoutOrder.removeView(orderLayout);
                        linearLayoutOrder.removeView(btnRemoveLayout);
                        linearLayoutOrder.removeView(view);
                        tvSubTotal.setText(String.format("$ %.2f", cart.getTotal()));
                        tvTotal.setText(String.format("$ %.2f", cart.getTotal()));
                    }
                });

            }
            view.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    5
            ));
            view.setBackgroundColor(android.graphics.Color.parseColor("#B3B3B3"));



            tvProductOrder.setLayoutParams(paramsOrder);
            btnRemoveLayout.addView(btnRemove);
            orderLayout.addView(tvProductOrder);
            linearLayoutOrder.addView(orderLayout);
            linearLayoutOrder.addView(btnRemoveLayout);
            linearLayoutOrder.addView(view);
        }
        tvSubTotal.setText(String.format("$ %.2f", cart.getTotal()));
        tvTotal.setText(String.format("$ %.2f", cart.getTotal()));
    }


    private void sendIntent(){
        Intent intent;
        intent = new Intent(this, ShippingAddressActivity.class);
        startActivity(intent);
    }
}
