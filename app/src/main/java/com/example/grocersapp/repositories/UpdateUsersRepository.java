package com.example.grocersapp.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.grocersapp.Response.CategoriesResponse;
import com.example.grocersapp.Response.UpdateUserResponse;
import com.example.grocersapp.api.Api;
import com.example.grocersapp.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdateUsersRepository {
    private static UpdateUsersRepository updateUsersRepository;
    private Retrofit retrofitClient;
    final Api api;
    public static UpdateUsersRepository getInstance(){
        if(updateUsersRepository == null){
            updateUsersRepository = new UpdateUsersRepository();
        }
        return updateUsersRepository;
    }
    public UpdateUsersRepository(){
        retrofitClient =new RetrofitClient().getRetrofit();
        api =retrofitClient.create(Api.class);
    }

    public MutableLiveData<UpdateUserResponse> updateUsers(String decryptedToken,String name,String contact){
        final MutableLiveData<UpdateUserResponse> updateU = new MutableLiveData<>();
        api.updateUser(decryptedToken,name,contact).enqueue(new Callback<UpdateUserResponse>() {
            @Override
            public void onResponse(Call<UpdateUserResponse> call, Response<UpdateUserResponse> response) {
                if(response.isSuccessful())
                {
                    updateU.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<UpdateUserResponse> call, Throwable t) {
                updateU.setValue(null);
            }
        });
        return updateU;
    }
}
