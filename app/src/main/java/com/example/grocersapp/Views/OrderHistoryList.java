package com.example.grocersapp.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.grocersapp.Controller.OrderHistoryAdapter;
import com.example.grocersapp.R;
import com.example.grocersapp.storage.SharedPrefManager;
import com.example.grocersapp.viewmodels.OrderHistoryViewModel;

import java.util.Timer;
import java.util.TimerTask;

import Utilities.CheckInternetConnection;

public class OrderHistoryList extends AppCompatActivity {
    RecyclerView recyclerView;
    private String decryptedToken;
    SharedPrefManager sharedPrefManager;
    private OrderHistoryViewModel orderHistoryViewModel;
    private OrderHistoryAdapter orderHistoryAdapter;
    Context context;
    ConstraintLayout constraintLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_list);
        constraintLayout=findViewById(R.id.layout_nodata);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //your method
                if(CheckInternetConnection.checkConnection(OrderHistoryList.this))
                {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            new Timer().cancel();
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        }
                    });

                }
                if(!CheckInternetConnection.checkConnection(OrderHistoryList.this))
                {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(OrderHistoryList.this, "इंटरनेट कनेक्टिव्हिटी उपलब्ध नाही", Toast.LENGTH_SHORT).show();

                        }
                    });
                }


            }
        }, 0, 1000);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Order History");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Home.class));
                OrderHistoryList.this.finish();
            }
        });

        recyclerView=findViewById(R.id.recycle_order_history);
        sharedPrefManager = new SharedPrefManager(OrderHistoryList.this);
        decryptedToken = sharedPrefManager.getToken();

        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        orderHistoryViewModel = ViewModelProviders.of(OrderHistoryList.this).get(OrderHistoryViewModel.class);
        orderHistoryViewModel.init(decryptedToken);
        orderHistoryViewModel.orderHistory().observe(OrderHistoryList.this,orderHistoryResponse -> {
            if(orderHistoryResponse.getStatus().equals("success")){
                orderHistoryAdapter = new OrderHistoryAdapter(orderHistoryResponse.getOrderHistory(), OrderHistoryList.this);
                recyclerView.setAdapter(orderHistoryAdapter);

                if(orderHistoryAdapter.getItemCount()==0){
                    constraintLayout.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                }

            }
        });
    }
}
