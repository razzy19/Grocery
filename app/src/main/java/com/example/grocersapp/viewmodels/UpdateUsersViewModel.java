package com.example.grocersapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.grocersapp.Response.UpdateUserResponse;
import com.example.grocersapp.repositories.UpdateUsersRepository;

public class UpdateUsersViewModel extends ViewModel {
    private MutableLiveData<UpdateUserResponse> mutableLiveData;
    private UpdateUsersRepository updateUsersRepository;
    public void init(String token,String name,String contact)
    {
        if (mutableLiveData != null){
            return;
        }
        updateUsersRepository = UpdateUsersRepository.getInstance();
        mutableLiveData = updateUsersRepository.updateUsers(token,name,contact);
    }

    public LiveData<UpdateUserResponse> update(){
        return mutableLiveData;
    }

}
