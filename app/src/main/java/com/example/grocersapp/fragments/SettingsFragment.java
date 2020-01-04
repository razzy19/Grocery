package com.example.grocersapp.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.grocersapp.Controller.ManageAddressAdapter;
import com.example.grocersapp.Controller.OrderHistoryAdapter;
import com.example.grocersapp.Model.DefaultResponse;
import com.example.grocersapp.Model.LoginResponse;
import com.example.grocersapp.Model.User;
import com.example.grocersapp.R;
import com.example.grocersapp.Response.AddressResponse;
import com.example.grocersapp.Response.OrderHistoryResponse;
import com.example.grocersapp.Views.Cart;
import com.example.grocersapp.Views.EnterContact;
import com.example.grocersapp.Views.Home;
import com.example.grocersapp.Views.Login;
import com.example.grocersapp.Views.ManageAddress;
import com.example.grocersapp.Views.ProductList;
import com.example.grocersapp.api.Api;
import com.example.grocersapp.api.RetrofitClient;
import com.example.grocersapp.storage.SharedPrefManager;
import com.example.grocersapp.viewmodels.OrderHistoryViewModel;
import com.example.grocersapp.viewmodels.UpdateUsersViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Timer;
import java.util.TimerTask;

import Utilities.CheckInternetConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SettingsFragment extends Fragment implements View.OnClickListener {

    private EditText editTextName, editTextPhone;
    private OrderHistoryAdapter orderHistoryAdapter;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private String decryptedToken;
    private OrderHistoryViewModel orderHistoryViewModel;
    SharedPrefManager sharedPrefManager;
    private UpdateUsersViewModel updateUsersViewModel;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_fragment, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        editTextName = view.findViewById(R.id.editTextName);
        editTextPhone = view.findViewById(R.id.editTextPhone);
//        recyclerView=view.findViewById(R.id.recycle_order_history);
        sharedPrefManager = new SharedPrefManager(getActivity());
        decryptedToken = sharedPrefManager.getToken();
        editTextName.setText(sharedPrefManager.getEncryptedName());
        editTextPhone.setText(sharedPrefManager.getEncryptedContact());



//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//        Retrofit retrofitClient = new RetrofitClient().getRetrofit();
//        final Api api = retrofitClient.create(Api.class);
//        Call<OrderHistoryResponse> call = api.getOrdersHistory(decryptedToken);
//
//        call.enqueue(new Callback<OrderHistoryResponse>() {
//            @Override
//            public void onResponse(Call<OrderHistoryResponse> call, Response<OrderHistoryResponse> response) {
//                OrderHistoryResponse orderHistoryResponse = response.body();
//                if (orderHistoryResponse.getStatus().equals("success")) {
////                    progressBar.setVisibility(View.GONE);
//                    orderHistoryAdapter = new OrderHistoryAdapter(orderHistoryResponse.getOrderHistory(), getContext());
//                    recyclerView.setAdapter(orderHistoryAdapter);
//
//
//                } else {
////                    progressBar.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<OrderHistoryResponse> call, Throwable t) {
//
//                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        orderHistoryViewModel = ViewModelProviders.of(getActivity()).get(OrderHistoryViewModel.class);
//        orderHistoryViewModel.init(decryptedToken);
//        orderHistoryViewModel.orderHistory().observe(getActivity(),orderHistoryResponse -> {
//            if(orderHistoryResponse.getStatus().equals("success")){
//                orderHistoryAdapter = new OrderHistoryAdapter(orderHistoryResponse.getOrderHistory(), getContext());
//                recyclerView.setAdapter(orderHistoryAdapter);
//
//            }
//        });
        view.findViewById(R.id.buttonSave).setOnClickListener(this);
    }

    private void updateProfile() {
        String name = editTextName.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();

        if (name.isEmpty()) {
            editTextName.setError("Email is required");
            editTextName.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            editTextPhone.setError("Name required");
            editTextPhone.requestFocus();
            return;
        }


        updateUsersViewModel = ViewModelProviders.of(getActivity()).get(UpdateUsersViewModel.class);
        updateUsersViewModel.init(decryptedToken,name,phone);
        updateUsersViewModel.update().observe(this,updateUserResponse -> {
            if(updateUserResponse.getState().equals("success"))
            {
                sharedPrefManager.saveUserData(editTextName.getText().toString(),editTextPhone.getText().toString());
                Toast.makeText(getActivity(),"माहिती यशस्वीरित्या अद्यतनित केली!",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getActivity(),"कृपया पुन्हा प्रयत्न करा !",Toast.LENGTH_LONG).show();
            }
        });
//        User user = SharedPrefManager.getInstance(getActivity()).getUser();

//        Retrofit retrofitClient =new RetrofitClient().getRetrofit();
//        final Api api =retrofitClient.create(Api.class);
//        Call<LoginResponse> call = api.updateUser(  user.getId(), name, phone);
//
//        call.enqueue(new Callback<LoginResponse>() {
//            @Override
//            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//
//                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
//
//                if (!response.body().isError()) {
//                    SharedPrefManager.getInstance(getActivity()).saveUser(response.body().getUser());
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//
//            }
//        });
    }

//    private void updatePassword() {
//        String currentpassword = editTextCurrentPassword.getText().toString().trim();
//        String newpassword = editTextNewPassword.getText().toString().trim();
//
//        if (currentpassword.isEmpty()) {
//            editTextCurrentPassword.setError("Password required");
//            editTextCurrentPassword.requestFocus();
//            return;
//        }
//
//        if (newpassword.isEmpty()) {
//            editTextNewPassword.setError("Enter new password");
//            editTextNewPassword.requestFocus();
//            return;
//        }
//
//
//        User user = SharedPrefManager.getInstance(getActivity()).getUser();
//
//        Retrofit retrofitClient =new RetrofitClient().getRetrofit();
//        final Api api =retrofitClient.create(Api.class);
//        Call<DefaultResponse> call = api.updatePassword(currentpassword, newpassword, user.getEmail());
//
//
//        call.enqueue(new Callback<DefaultResponse>() {
//            @Override
//            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
//
//                Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onFailure(Call<DefaultResponse> call, Throwable t) {
//
//            }
//        });
//    }

    private void logout() {
        SharedPrefManager.getInstance(getActivity()).clear();
        Intent intent = new Intent(getActivity(), Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

//    private void deleteUser() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Are you sure?");
//        builder.setMessage("This action is irreversible...");
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                User user = SharedPrefManager.getInstance(getActivity()).getUser();
//
//                Retrofit retrofitClient =new RetrofitClient().getRetrofit();
//                final Api api =retrofitClient.create(Api.class);
//                Call<DefaultResponse> call = api.deleteUser(user.getId());
//
//                call.enqueue(new Callback<DefaultResponse>() {
//                    @Override
//                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
//
//                        if (!response.body().isErr()) {
//                            SharedPrefManager.getInstance(getActivity()).clear();
//                            SharedPrefManager.getInstance(getActivity()).clear();
//                            Intent intent = new Intent(getActivity(), Home.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);
//                        }
//
//                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onFailure(Call<DefaultResponse> call, Throwable t) {
//
//                    }
//                });
//
//            }
//        });
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//
//        AlertDialog ad = builder.create();
//        ad.show();
//    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSave:
             updateProfile();
                break;


        }
    }



}
