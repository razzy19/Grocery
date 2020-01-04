package com.example.grocersapp.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.grocersapp.Response.UpdateAddressResponse;
import com.example.grocersapp.api.Api;
import com.example.grocersapp.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdateAddressRepository {
    private static UpdateAddressRepository updateAddressRepository;
    final Api api;
    private Retrofit retrofitClient;

    public static UpdateAddressRepository getInstance(){
        if(updateAddressRepository == null){
            updateAddressRepository = new UpdateAddressRepository();
        }
        return updateAddressRepository;
    }

    public UpdateAddressRepository(){
        retrofitClient = new RetrofitClient().getRetrofit();
        api = retrofitClient.create(Api.class);
    }

    public MutableLiveData<UpdateAddressResponse> updateAddressResponseMutableLiveData(String decryptedToken,String address,int id){
        final MutableLiveData<UpdateAddressResponse> updateAddressResponseMutableLiveData = new MutableLiveData<>();
        api.updateAddress(decryptedToken, address, id).enqueue(new Callback<UpdateAddressResponse>() {
            @Override
            public void onResponse(Call<UpdateAddressResponse> call, Response<UpdateAddressResponse> response) {
                if(response.isSuccessful()){
                    updateAddressResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<UpdateAddressResponse> call, Throwable t) {
                updateAddressResponseMutableLiveData.setValue(null);
            }
        });
        return updateAddressResponseMutableLiveData;
    }
}
