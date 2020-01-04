package com.example.grocersapp.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.grocersapp.Response.CategoriesResponse;
import com.example.grocersapp.Views.Categories;
import com.example.grocersapp.Views.Home;
import com.example.grocersapp.repositories.CategoriesRepository;
import com.example.grocersapp.storage.SharedPrefManager;

public class CategoriesViewModel extends ViewModel {
    private MutableLiveData<CategoriesResponse> mutableLiveData;
    private CategoriesRepository categoriesRepository;

    public void init(String decryptedToken)
    {
        if (mutableLiveData != null){
            return;
        }
        categoriesRepository = CategoriesRepository.getInstance();
        mutableLiveData = categoriesRepository.getCategories(decryptedToken);
    }
    public LiveData<CategoriesResponse> getCategoriesRepository(){
        return mutableLiveData;
    }

}
