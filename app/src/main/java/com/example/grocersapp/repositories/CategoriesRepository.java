package com.example.grocersapp.repositories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.grocersapp.Response.CategoriesResponse;
import com.example.grocersapp.api.Api;
import com.example.grocersapp.api.RetrofitClient;
import com.example.grocersapp.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CategoriesRepository {

    private static CategoriesRepository categoriesRepository;
    Context context;
    private Retrofit retrofitClient;
    final Api api;
    public static CategoriesRepository getInstance(){
        if(categoriesRepository == null)
        {
            categoriesRepository = new CategoriesRepository();
        }
        return categoriesRepository;
    }

    public CategoriesRepository(){
        retrofitClient =new RetrofitClient().getRetrofit();
        api =retrofitClient.create(Api.class);

    }

    public MutableLiveData<CategoriesResponse> getCategories(String decryptedToken)
    {
        final MutableLiveData<CategoriesResponse> categoriesData = new MutableLiveData<>();
        api.getCategories(decryptedToken).enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                if(response.isSuccessful())
                {
                    categoriesData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable t) {
                categoriesData.setValue(null);
            }
        });
        return categoriesData;
    }

}
