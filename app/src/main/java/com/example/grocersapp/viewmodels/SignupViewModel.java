package com.example.grocersapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.grocersapp.Model.SignupResponse;
import com.example.grocersapp.repositories.SignupRepository;

public class SignupViewModel extends ViewModel {
    private MutableLiveData<SignupResponse> mutableLiveData;
    private SignupRepository signupRepository;

    public void init(String address,String password,String name,String contact){
        if(signupRepository!=null){
            return;
        }
        signupRepository = SignupRepository.getInstance();
        mutableLiveData = signupRepository.signup(address, password, name, contact);
    }
    public LiveData<SignupResponse> signupResponseLiveData(){
        return mutableLiveData;
    }
}
