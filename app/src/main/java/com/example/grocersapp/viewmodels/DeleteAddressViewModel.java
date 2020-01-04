package com.example.grocersapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.grocersapp.Response.DeleteAddressResponse;
import com.example.grocersapp.repositories.DeleteAddressRepository;

public class DeleteAddressViewModel extends ViewModel {
    private DeleteAddressRepository deleteAddressRepository;
    private MutableLiveData<DeleteAddressResponse> mutableLiveData;

    public void init(String decryptedToken,int id){
        if(deleteAddressRepository != null){
            return;
        }
        deleteAddressRepository = DeleteAddressRepository.getInstance();
        mutableLiveData = deleteAddressRepository.deleteAddressResponseMutableLiveData(decryptedToken,id);
    }

    public LiveData<DeleteAddressResponse> deleteAddress(){
        return mutableLiveData;
    }
}
