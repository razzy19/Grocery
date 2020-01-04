package com.example.grocersapp.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocersapp.Controller.OrderHistoryAdapter;
import com.example.grocersapp.Controller.OrderHistoryDetailsAdapter;
import com.example.grocersapp.R;
import com.example.grocersapp.Response.OrderHistoryDetailsResponse;
import com.example.grocersapp.Response.OrderHistoryResponse;
import com.example.grocersapp.api.Api;
import com.example.grocersapp.api.RetrofitClient;
import com.example.grocersapp.storage.SharedPrefManager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import Utilities.CheckInternetConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrderHistory extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;
    String decryptedToken;
    Context context;
    TextView txtStatus,txtDate,txtPaymentMode,txtTotal,txtProdCount,txtOTP;
    int orderid;

    RecyclerView recyclerView;
    OrderHistoryDetailsAdapter orderHistoryDetailsAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //your method
                if(CheckInternetConnection.checkConnection(OrderHistory.this))
                {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            new Timer().cancel();
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        }
                    });

                }
                if(!CheckInternetConnection.checkConnection(OrderHistory.this))
                {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(OrderHistory.this, "इंटरनेट कनेक्टिव्हिटी उपलब्ध नाही", Toast.LENGTH_SHORT).show();

                        }
                    });
                }


            }
        }, 0, 1000);

        txtDate=findViewById(R.id.txt_delivery_date);
        txtStatus=findViewById(R.id.txt_order_status);
        txtPaymentMode=findViewById(R.id.txt_payment_mode);
        txtTotal=findViewById(R.id.txt_total_amount);
        txtProdCount=findViewById(R.id.txt_product_count);
        txtOTP=findViewById(R.id.txt_otp);
        orderid=Integer.parseInt(getIntent().getStringExtra("order_id"));
        sharedPrefManager = new SharedPrefManager(this);
        decryptedToken = sharedPrefManager.getToken();
        recyclerView=findViewById(R.id.recycle_old_items);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Retrofit retrofitClient = new RetrofitClient().getRetrofit();
        final Api api = retrofitClient.create(Api.class);
        Call<OrderHistoryDetailsResponse> call = api.getOrdersHistoryDetails(decryptedToken,orderid);



        call.enqueue(new Callback<OrderHistoryDetailsResponse>() {
            @Override
            public void onResponse(Call<OrderHistoryDetailsResponse> call, Response<OrderHistoryDetailsResponse> response) {
                OrderHistoryDetailsResponse orderHistoryDetailsResponse = response.body();
                ArrayList<OrderHistoryDetailsResponse.OrderHistory> orderHistory = orderHistoryDetailsResponse.getOrderHistory();

                if (orderHistoryDetailsResponse.getStatus().equals("success")) {
                    Log.d("historydetails", orderHistoryDetailsResponse.getStatus());


//                    txtStatus.setText(orderHistory.getOrderstatus());
                    for (OrderHistoryDetailsResponse.OrderHistory o: orderHistory
                         ) {
                        Log.d("date", o.getOrders()+"");
                        txtDate.setText(o.getDate());
                        txtOTP.setText(o.getOtp());
                        txtTotal.setText(o.getAmount());
                        txtPaymentMode.setText(o.getPaymentmode());
                        txtStatus.setText("Order "+o.getOrderstatus());
                        orderHistoryDetailsAdapter= new OrderHistoryDetailsAdapter(o.getOrders(),context);
                        txtProdCount.setText(orderHistoryDetailsAdapter.getItemCount()+"");
                        recyclerView.setAdapter(orderHistoryDetailsAdapter);

                        if(o.getOrderstatus().equals("Completed")){
                            txtOTP.setVisibility(View.INVISIBLE);
                        }

                    }

//                    txtDate.setText(orderHistory.getDate());

//                    orderHistoryDetailsAdapter= new OrderHistoryDetailsAdapter(orderHistory.getOrders(),context);
//                    recyclerView.setAdapter(orderHistoryDetailsAdapter);


                } else {
                    Log.d("historydetails", orderHistoryDetailsResponse.getStatus());

                }
            }

            @Override
            public void onFailure(Call<OrderHistoryDetailsResponse> call, Throwable t) {

                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
