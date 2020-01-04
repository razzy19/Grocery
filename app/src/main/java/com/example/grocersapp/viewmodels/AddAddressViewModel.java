package com.example.grocersapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.grocersapp.Response.AddAddressResponse;
import com.example.grocersapp.repositories.AddAddressRepository;

public class AddAddressViewModel extends ViewModel {
    private AddAddressRepository addAddressRepository;
    private MutableLiveData<AddAddressResponse> mutableLiveData;

    public void init(String decryptedToken,String address,String address_type){
        if(addAddressRepository!=null){
            return;
        }
        addAddressRepository = AddAddressRepository.getInstance();
        mutableLiveData = addAddressRepository.addAddress(decryptedToken,address,address_type);
    }
    public LiveData<AddAddressResponse> addAddressResponseLiveData(){
        return mutableLiveData;
    }
}
