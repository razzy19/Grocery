package com.example.grocersapp.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.grocersapp.Model.SignupResponse;
import com.example.grocersapp.api.Api;
import com.example.grocersapp.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignupRepository {
    private static SignupRepository signupRepository;
    private Retrofit retrofitClient;
    final Api api;

    public static SignupRepository getInstance(){
        if(signupRepository == null){
            signupRepository = new SignupRepository();
        }
        return signupRepository;
    }

    public SignupRepository(){
        retrofitClient = new RetrofitClient().getRetrofit();
        api = retrofitClient.create(Api.class);
    }

    public MutableLiveData<SignupResponse> signup(String address,String password,String fullname,String contact){
        final MutableLiveData<SignupResponse> signupResponseMutableLiveData = new MutableLiveData<>();
        api.createUser(address,password,fullname,contact).enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                if(response.isSuccessful()){
                    signupResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                signupResponseMutableLiveData.setValue(null);
            }
        });
        return signupResponseMutableLiveData;
    }

}
