package com.example.grocersapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.grocersapp.Response.AddressResponse;
import com.example.grocersapp.repositories.GetAddressesRepository;

public class GetAddressesViewModel extends ViewModel {
    private MutableLiveData<AddressResponse> mutableLiveData;
    private GetAddressesRepository getAddressesRepository;

    public void init(String decryptedToken){
        if(mutableLiveData != null){
            return;
        }
        getAddressesRepository = GetAddressesRepository.getInstance();
        mutableLiveData = getAddressesRepository.getAddresses(decryptedToken);
    }
    public LiveData<AddressResponse> getAd(){
        return mutableLiveData;
    }
}
