package com.example.grocersapp.api;

import android.content.Context;
import android.util.Base64;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

private static Retrofit retrofit = null;
   private static Interceptor onlineInterceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response response = chain.proceed(chain.request());
            int maxAge = 60; // read from cache for 60 seconds even if there is internet connection
            return response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Pragma")
                    .build();
        }
    };

    public RetrofitClient()
    {
//        int cacheSize = 10 * 1024 * 1024; // 10 MB
//        Cache cache = new Cache(context.getCacheDir(), cacheSize);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
//                .cache(cache)
//                .addNetworkInterceptor(onlineInterceptor).build();



        retrofit = new Retrofit.Builder()
                .baseUrl("https://grocerr.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

        public Retrofit getRetrofit() {
            return retrofit;
        }



    }




