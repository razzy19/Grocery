package com.example.grocersapp.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocersapp.Controller.ManageAddressAdapter;
import com.example.grocersapp.Model.AddressModel;
import com.example.grocersapp.Model.LoginResponse;
import com.example.grocersapp.Model.SignupResponse;
import com.example.grocersapp.R;
import com.example.grocersapp.Response.AddAddressResponse;
import com.example.grocersapp.Response.AddressResponse;
import com.example.grocersapp.api.Api;
import com.example.grocersapp.api.RetrofitClient;
import com.example.grocersapp.interfaces.AddAddressDialogCallback;
import com.example.grocersapp.storage.SharedPrefManager;
import com.example.grocersapp.viewmodels.AddAddressViewModel;
import com.example.grocersapp.viewmodels.GetAddressesViewModel;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import Utilities.CheckInternetConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ManageAddress extends AppCompatActivity {
    Button addAddress;
    RecyclerView recyclerView;
    ManageAddressAdapter manageAddressAdapter;
    ArrayList<AddressModel> addressModelList;
    private Context context;
    private Spinner spinner;
    AddAddressDialogCallback addAddressDialogCallback;
    private GetAddressesViewModel getAddressesViewModel;
    ProgressBar progressBar;
    SharedPrefManager sharedPrefManager;
    String fulladdress;
    private TextView txtFlat,txtBuilding,txtArea,txtPincode;
    private Dialog dialog;
    private AddAddressViewModel addAddressViewModel;
    private String decryptedToken,address_type;
    private int address_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_address);
        addAddress = findViewById(R.id.btn_addAddress);
        recyclerView=findViewById(R.id.recycle_address);
        sharedPrefManager = new SharedPrefManager(ManageAddress.this);
        progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        decryptedToken = sharedPrefManager.getToken();
        dialog = new Dialog(ManageAddress.this); // Context, this, etc.
        recyclerView.setLayoutManager(new LinearLayoutManager(ManageAddress.this, LinearLayoutManager.VERTICAL, false));

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //your method
                if(CheckInternetConnection.checkConnection(ManageAddress.this))
                {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            new Timer().cancel();
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        }
                    });

                }
                if(!CheckInternetConnection.checkConnection(ManageAddress.this))
                {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(ManageAddress.this, "इंटरनेट कनेक्टिव्हिटी उपलब्ध नाही", Toast.LENGTH_SHORT).show();

                        }
                    });
                }


            }
        }, 0, 1000);
        loadAddress();
//        getAddressesViewModel = ViewModelProviders.of(this).get(GetAddressesViewModel.class);
//        getAddressesViewModel.init(decryptedToken);
//        getAddressesViewModel.getAd().observe(this, getAddress ->{
//            if(getAddress.getState().equals("success")){
//                manageAddressAdapter = new ManageAddressAdapter(getAddress.getAddresses(), ManageAddress.this);
//                recyclerView.setAdapter(manageAddressAdapter);
//            }
//            else{
//
//            }
//
//        });



        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btnAddAddress;

                dialog.setContentView(R.layout.layout_add_address);
//                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                dialog.show();
                txtArea = dialog.findViewById(R.id.editArea);
                txtBuilding = dialog.findViewById(R.id.editBuilding);
                txtPincode = dialog.findViewById(R.id.Pincode);
                txtFlat = dialog.findViewById(R.id.editFlatNo);
                spinner = dialog.findViewById(R.id.spinner_addresstype);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(adapterView.getContext(), "Selected" + adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
                        address_type = adapterView.getItemAtPosition(i).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


                btnAddAddress = dialog.findViewById(R.id.btn_add_address);

                btnAddAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String flat=txtFlat.getText().toString();
                        String building=txtBuilding.getText().toString();
                        String area=txtArea.getText().toString();
                        String pincode=txtPincode.getText().toString();

                        if (flat.isEmpty()) {
                            txtFlat.setError("फ्लॅट नंबर आवश्यक आहे");
                            txtFlat.requestFocus();
                            return;
                        }
                        if (building.isEmpty()) {
                            txtBuilding.setError("इमारत क्रमांक आवश्यक आहे");
                            txtBuilding.requestFocus();
                            return;
                        }
                        if (area.isEmpty()) {
                            txtArea.setError("क्षेत्र / शहर आवश्यक आहे");
                            txtArea.requestFocus();
                            return;
                        }
                        if (pincode.isEmpty()) {
                            txtPincode.setError("पिनकोड आवश्यक आहे");
                            txtPincode.requestFocus();
                            return;
                        }
                        fulladdress = txtFlat.getText().toString() + " " + txtBuilding.getText().toString() + " " + txtArea.getText().toString() + " " + txtPincode.getText().toString();
                        decryptedToken = sharedPrefManager.getToken();

                        progressBar.setVisibility(View.VISIBLE);
                        Retrofit retrofitClient =new RetrofitClient().getRetrofit();
                        final Api api =retrofitClient.create(Api.class);
                        Call<AddAddressResponse> call = api.addAddress(decryptedToken,fulladdress,address_type);

                        call.enqueue(new Callback<AddAddressResponse>() {
                            @Override
                            public void onResponse(Call<AddAddressResponse> call, Response<AddAddressResponse> response) {

                                AddAddressResponse dr = response.body();
                                if(dr.getState().equals("success"))
                                {
                                    loadAddress();
                                    progressBar.setVisibility(View.GONE);
                                    dialog.dismiss();
                                    Toast.makeText(ManageAddress.this, dr.getMsg(), Toast.LENGTH_LONG).show();
                                }
                                else if(dr.getState().equals("fail"))
                                {

                                    progressBar.setVisibility(View.GONE);
//                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    Toast.makeText(ManageAddress.this, dr.getMsg(), Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<AddAddressResponse> call, Throwable t) {


                                Toast.makeText(ManageAddress.this, t.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });
//                        addAddressViewModel = ViewModelProviders.of(ManageAddress.this).get(AddAddressViewModel.class);
//                        addAddressViewModel.init(decryptedToken,fulladdress,address_type);
//                        addAddressViewModel.addAddressResponseLiveData().observe(ManageAddress.this,addAddressResponse -> {
//                            if(addAddressResponse.getState().equals("success")){
//                                progressBar.setVisibility(View.GONE);
//                                dialog.dismiss();
//                                loadAddress();
//                                Toast.makeText(ManageAddress.this, addAddressResponse.getMsg(), Toast.LENGTH_LONG).show();
//                            }
//                            else{
//                                progressBar.setVisibility(View.GONE);
//
//                                Toast.makeText(ManageAddress.this, addAddressResponse.getMsg(), Toast.LENGTH_LONG).show();
//                            }
//                        });
                    }
                });





            }
        });

    }

    public void loadAddress(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Retrofit retrofitClient = new RetrofitClient().getRetrofit();
                final Api api = retrofitClient.create(Api.class);
                Call<AddressResponse> call = api.getAddress(decryptedToken);

                call.enqueue(new Callback<AddressResponse>() {
                    @Override
                    public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                        AddressResponse addressResponse = response.body();
                        if (addressResponse.getState().equals("success")) {
//                            progressBar.setVisibility(View.GONE);

                            manageAddressAdapter = new ManageAddressAdapter(addressResponse.getAddresses(), ManageAddress.this);
                            recyclerView.setAdapter(manageAddressAdapter);
                            manageAddressAdapter.notifyDataSetChanged();


                        } else {
//                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<AddressResponse> call, Throwable t) {

                        Toast.makeText(ManageAddress.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
    }




}


