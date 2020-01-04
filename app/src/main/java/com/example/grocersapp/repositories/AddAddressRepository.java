package com.example.grocersapp.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.grocersapp.Response.AddAddressResponse;
import com.example.grocersapp.api.Api;
import com.example.grocersapp.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddAddressRepository {
    private static AddAddressRepository addAddressRepository;
    private Retrofit retrofit;
    final Api api;

    public static AddAddressRepository getInstance(){
        if(addAddressRepository == null){
            addAddressRepository = new AddAddressRepository();
        }
        return addAddressRepository;
    }

    public AddAddressRepository(){
        retrofit = new RetrofitClient().getRetrofit();
        api = retrofit.create(Api.class);
    }

    public MutableLiveData<AddAddressResponse> addAddress(String decryptedToken,String address,String address_type){
        final MutableLiveData<AddAddressResponse> addAddressResponseMutableLiveData = new MutableLiveData<>();
        api.addAddress(decryptedToken,address,address_type).enqueue(new Callback<AddAddressResponse>() {
            @Override
            public void onResponse(Call<AddAddressResponse> call, Response<AddAddressResponse> response) {
                if(response.isSuccessful()){
                    addAddressResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<AddAddressResponse> call, Throwable t) {
                addAddressResponseMutableLiveData.setValue(null);
            }
        });
        return addAddressResponseMutableLiveData;
    }
}
