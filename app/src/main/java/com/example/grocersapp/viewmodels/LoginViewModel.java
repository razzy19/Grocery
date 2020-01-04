package com.example.grocersapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.grocersapp.Model.LoginResponse;
import com.example.grocersapp.repositories.LoginRepository;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<LoginResponse> loginResponseMutableLiveData;
    private LoginRepository loginRepository;
    public void init(String contact,String password){
        if(loginResponseMutableLiveData!=null){
            return;
        }
        loginRepository = LoginRepository.getInstance();
        loginResponseMutableLiveData = loginRepository.login(contact,password);
    }

    public LiveData<LoginResponse> checkLogin(){
        return loginResponseMutableLiveData;
    }
}
