package com.example.grocersapp.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.grocersapp.Model.LoginResponse;
import com.example.grocersapp.api.Api;
import com.example.grocersapp.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginRepository {
    private Retrofit retrofitClient;
    private static LoginRepository loginRepository;
    final Api api;

    public static LoginRepository getInstance()
    {
            if(loginRepository == null){
                loginRepository = new LoginRepository();
            }
            return loginRepository;
    }

    public LoginRepository(){
        retrofitClient = new RetrofitClient().getRetrofit();
        api = retrofitClient.create(Api.class);
    }

    public MutableLiveData<LoginResponse> login(String contact,String password) {
        final MutableLiveData<LoginResponse> loginResponseMutableLiveData = new MutableLiveData<>();
        api.userLogin(contact,password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    loginResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginResponseMutableLiveData.setValue(null);
            }
        });
        return loginResponseMutableLiveData;
    }
}
