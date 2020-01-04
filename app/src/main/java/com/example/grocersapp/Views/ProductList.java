package com.example.grocersapp.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.grocersapp.Controller.GridCategoryAdapter;
import com.example.grocersapp.Controller.ProductListAdapter;
import com.example.grocersapp.R;
import com.example.grocersapp.Response.CategoriesResponse;
import com.example.grocersapp.Response.GetProductResponse;
import com.example.grocersapp.api.Api;
import com.example.grocersapp.api.RetrofitClient;
import com.example.grocersapp.storage.SharedPrefManager;

import java.util.Timer;
import java.util.TimerTask;

import Utilities.CheckInternetConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductList extends AppCompatActivity {
    RecyclerView recyclerView;
    ProductListAdapter productListAdapter;
    SharedPrefManager sharedPrefManager;
    String decryptedToken;
    int cat_id;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        recyclerView=findViewById(R.id.product_recycle);


        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //your method
                if(CheckInternetConnection.checkConnection(ProductList.this))
                {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            new Timer().cancel();
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        }
                    });

                }
                if(!CheckInternetConnection.checkConnection(ProductList.this))
                {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(ProductList.this, "इंटरनेट कनेक्टिव्हिटी उपलब्ध नाही", Toast.LENGTH_SHORT).show();

                        }
                    });
                }


            }
        }, 0, 1000);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        getSupportActionBar().setTitle("Items");

//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
        recyclerView.setLayoutManager( new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        cat_id=getIntent().getIntExtra("cat_id",0);

        sharedPrefManager = new SharedPrefManager(this);

        decryptedToken = sharedPrefManager.getToken();

        Retrofit retrofitClient =new RetrofitClient().getRetrofit();
        final Api api =retrofitClient.create(Api.class);
        Call<GetProductResponse> call = api.getProducts(decryptedToken,cat_id);


        call.enqueue(new Callback<GetProductResponse>() {
            @Override
            public void onResponse(Call<GetProductResponse> call, Response<GetProductResponse> response) {
                GetProductResponse getProductResponse = response.body();
                productListAdapter = new ProductListAdapter( getProductResponse.getGetProducts(),getApplicationContext());
                recyclerView.setAdapter(productListAdapter);
            }

            @Override
            public void onFailure(Call<GetProductResponse> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
            }
        });
    }
}
