package com.example.grocersapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.grocersapp.Response.UpdateAddressResponse;
import com.example.grocersapp.repositories.UpdateAddressRepository;

public class UpdateAddressViewModel extends ViewModel {
    private MutableLiveData<UpdateAddressResponse> mutableLiveData;
    private UpdateAddressRepository updateAddressRepository;

    public void init(String decryptedToken,String address,int id){
        if(updateAddressRepository!=null){
            return;
        }

        updateAddressRepository  = UpdateAddressRepository.getInstance();
        mutableLiveData = updateAddressRepository.updateAddressResponseMutableLiveData(decryptedToken,address,id);
    }

    public LiveData<UpdateAddressResponse> updateAddress(){
        return mutableLiveData;
    }
}
