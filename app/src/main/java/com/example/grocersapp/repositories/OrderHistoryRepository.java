package com.example.grocersapp.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.grocersapp.Response.OrderHistoryResponse;
import com.example.grocersapp.api.Api;
import com.example.grocersapp.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrderHistoryRepository {
    private static OrderHistoryRepository orderHistoryRepository;
    final Api api;
    private Retrofit retrofit;

    public static OrderHistoryRepository getInstance(){
        if(orderHistoryRepository == null){
            orderHistoryRepository = new OrderHistoryRepository();
        }
        return orderHistoryRepository;
    }

    public OrderHistoryRepository(){
        retrofit = new RetrofitClient().getRetrofit();
        api = retrofit.create(Api.class);
    }

    public MutableLiveData<OrderHistoryResponse> orderHistoryResponseMutableLiveData(String decryptedToken){
        final MutableLiveData<OrderHistoryResponse> mutableLiveData = new MutableLiveData<>();
        api.getOrdersHistory(decryptedToken).enqueue(new Callback<OrderHistoryResponse>() {
            @Override
            public void onResponse(Call<OrderHistoryResponse> call, Response<OrderHistoryResponse> response) {
                if(response.isSuccessful()){
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<OrderHistoryResponse> call, Throwable t) {
                mutableLiveData.setValue(null);
            }
        });
        return mutableLiveData;
    }
}
