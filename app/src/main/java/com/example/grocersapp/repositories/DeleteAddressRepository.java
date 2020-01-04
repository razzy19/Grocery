package com.example.grocersapp.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.grocersapp.Response.DeleteAddressResponse;
import com.example.grocersapp.api.Api;
import com.example.grocersapp.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DeleteAddressRepository {
    private static DeleteAddressRepository deleteAddressRepository;
    final Api api;
    private Retrofit retrofit;

    public static DeleteAddressRepository getInstance(){
        if(deleteAddressRepository == null) {
            deleteAddressRepository = new DeleteAddressRepository();
        }
        return deleteAddressRepository;
    }

    public DeleteAddressRepository(){
        retrofit = new RetrofitClient().getRetrofit();
        api = retrofit.create(Api.class);
    }

    public MutableLiveData<DeleteAddressResponse> deleteAddressResponseMutableLiveData(String decryptedToken,int id){
        final MutableLiveData<DeleteAddressResponse> deleteAddressResponseMutableLiveData = new MutableLiveData<>();
        api.deleteAddress(decryptedToken,id).enqueue(new Callback<DeleteAddressResponse>() {
            @Override
            public void onResponse(Call<DeleteAddressResponse> call, Response<DeleteAddressResponse> response) {
                if(response.isSuccessful()){
                    deleteAddressResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<DeleteAddressResponse> call, Throwable t) {
                deleteAddressResponseMutableLiveData.setValue(null);
            }
        });
        return  deleteAddressResponseMutableLiveData;
    }

}
