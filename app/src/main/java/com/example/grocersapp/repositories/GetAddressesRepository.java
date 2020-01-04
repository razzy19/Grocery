package com.example.grocersapp.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.grocersapp.Response.AddressResponse;
import com.example.grocersapp.Response.CategoriesResponse;
import com.example.grocersapp.api.Api;
import com.example.grocersapp.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetAddressesRepository {

    private static GetAddressesRepository getAddressesRepository;
    private Retrofit retrofitClient;
    final Api api;
    public static GetAddressesRepository getInstance(){
        if(getAddressesRepository == null){
            getAddressesRepository = new GetAddressesRepository();
        }
        return getAddressesRepository;
    }
    public GetAddressesRepository(){
        retrofitClient =new RetrofitClient().getRetrofit();
        api =retrofitClient.create(Api.class);
    }
    public MutableLiveData<AddressResponse> getAddresses(String decryptedToken){
        final MutableLiveData<AddressResponse> addressData = new MutableLiveData<>();
        api.getAddress(decryptedToken).enqueue(new Callback<AddressResponse>() {
            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                if(response.isSuccessful()){
                    addressData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<AddressResponse> call, Throwable t) {
                addressData.setValue(null);
            }
        });
        return addressData;
    }
}
