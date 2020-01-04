package com.example.grocersapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.grocersapp.Response.OrderHistoryResponse;
import com.example.grocersapp.repositories.OrderHistoryRepository;

public class OrderHistoryViewModel extends ViewModel {
    public MutableLiveData<OrderHistoryResponse> mutableLiveData;
    private OrderHistoryRepository orderHistoryRepository;
    public void init(String  decryptedToken){
        if(orderHistoryRepository!=null){
            return;
        }
        orderHistoryRepository = OrderHistoryRepository.getInstance();
        mutableLiveData = orderHistoryRepository.orderHistoryResponseMutableLiveData(decryptedToken);
    }

    public LiveData<OrderHistoryResponse> orderHistory(){
        return mutableLiveData;
    }
}
