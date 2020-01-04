package com.example.grocersapp.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocersapp.Controller.AddressAdapter;
import com.example.grocersapp.Controller.ManageAddressAdapter;
import com.example.grocersapp.Controller.ProductItemAdapter;
import com.example.grocersapp.Model.AddressModel;
import com.example.grocersapp.Model.CartData;
import com.example.grocersapp.Model.DefaultResponse;
import com.example.grocersapp.R;
import com.example.grocersapp.Response.AddOrderResponse;
import com.example.grocersapp.api.Api;
import com.example.grocersapp.api.RetrofitClient;
import com.example.grocersapp.database.DatabaseClient;
import com.example.grocersapp.storage.SharedPrefManager;
import com.example.grocersapp.viewmodels.GetAddressesViewModel;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Utilities.CheckInternetConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrderDetails extends AppCompatActivity {
        ImageButton btnAdd,btnSelect;
        RecyclerView recyclerView;
        AddressAdapter addressAdapter;
        String date;
        String time;
        String jsondata;
        String address,selected_address;
        TextView txt_total;
        ArrayList<AddressModel> addressModelList;
        Context context,context2;
        List<String> addresslist;
        private Cart c;
    List<CartData> taskList;
    private Spinner spinner,timespinner;
        private GetAddressesViewModel getAddressesViewModel;
        private Button addbtn,btnProceed;
        private ArrayAdapter<String> adapter;
        private String fulladdress;
        private TextView txtFlat,txtBuilding,txtArea,txtPincode;
        private TextView txtaddress,txtDateTime,txtName,txtContact;
        int total=0;
        private DatePickerDialog.OnDateSetListener mDateSetListener;
        private ImageView imgclose;
        SharedPrefManager sharedPrefManager;
        String decryptedToken;
        private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //your method
                if(CheckInternetConnection.checkConnection(OrderDetails.this))
                {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            new Timer().cancel();
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        }
                    });

                }
                if(!CheckInternetConnection.checkConnection(OrderDetails.this))
                {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(OrderDetails.this, "इंटरनेट कनेक्टिव्हिटी उपलब्ध नाही", Toast.LENGTH_SHORT).show();

                        }
                    });
                }


            }
        }, 0, 1000);
        btnSelect=findViewById(R.id.btn_select_address);
        btnAdd=findViewById(R.id.btn_addAddress);
        txtaddress=findViewById(R.id.txtAddress);
        txtDateTime=findViewById(R.id.txtDateTime);
        timespinner=findViewById(R.id.spinner_time);
        txt_total = findViewById(R.id.textView20);
        btnProceed=findViewById(R.id.btnProceed);
        jsondata = getIntent().getStringExtra("orderdata");
        sharedPrefManager = new SharedPrefManager(OrderDetails.this);
        total = sharedPrefManager.getTotal();
        txt_total.setText("₹ "+String.valueOf(total));
        progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        txtName=findViewById(R.id.txt_cust_name);
        txtContact=findViewById(R.id.txt_cust_contact);
        progressBar.setIndeterminateDrawable(doubleBounce);
        CartApplication cartApplication = (CartApplication)getApplication();
        c = cartApplication.cart;
        txtName.setText(sharedPrefManager.getEncryptedName());
        txtContact.setText(sharedPrefManager.getEncryptedContact());
        content();


        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txtDateTime.getText().toString().isEmpty()) {
                    txtDateTime.setError("Select Date");
                    txtDateTime.requestFocus();
                    return;
                }
                else {

                }
                if(total<500){
                    Toast.makeText(getApplicationContext(),"किमान 500 ची ऑर्डर आवश्यक",Toast.LENGTH_SHORT).show();
                    txt_total.requestFocus();
                    return;

                }

                if(txtaddress.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"कृपया पत्ता निवडा",Toast.LENGTH_SHORT).show();
                    txtaddress.requestFocus();
                    return;
                }

                Retrofit retrofitClient =new RetrofitClient().getRetrofit();
                final Api api =retrofitClient.create(Api.class);
                decryptedToken = sharedPrefManager.getToken();

                Call<AddOrderResponse> call = api.addOrder(decryptedToken,date,time,jsondata,address,String.valueOf(total));

                progressBar.setVisibility(View.VISIBLE);
                call.enqueue(new Callback<AddOrderResponse>() {
                    @Override
                    public void onResponse(Call<AddOrderResponse> call, Response<AddOrderResponse> response) {

                        AddOrderResponse d=response.body();
                        if(d.getState().equals("success")){
                            progressBar.setVisibility(View.INVISIBLE);
                            Log.d("orderresponse", d.getState()+"");
                            Log.d("jsondata", jsondata);

                            sharedPrefManager.saveOtp(d.getOtp());
                            Toast.makeText(OrderDetails.this,"ऑर्डर यशस्वीरित्या दिली",Toast.LENGTH_SHORT).show();
                            final Dialog dialog = new Dialog(OrderDetails.this); // Context, this, etc.
                            dialog.setContentView(R.layout.order_done_layout);
                            dialog.show();

                            c.deleteTask();


                        }
                        else
                        {
                            Toast.makeText(OrderDetails.this,d.getMsg(),Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<AddOrderResponse> call, Throwable t) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(context,t.getMessage(), Toast.LENGTH_LONG).show();


                    }
                });
            }
        });




        //new AddressAdapter(txtaddress);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(OrderDetails.this); // Context, this, etc.
                dialog.setContentView(R.layout.layout_add_address);
                dialog.show();
                spinner=dialog.findViewById(R.id.spinner_addresstype);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(adapterView.getContext(),"Selected"+adapterView.getItemAtPosition(i).toString(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                txtArea=dialog.findViewById(R.id.editArea);
                txtBuilding=dialog.findViewById(R.id.editBuilding);
                txtPincode=dialog.findViewById(R.id.Pincode);
                txtFlat=dialog.findViewById(R.id.editFlatNo);

                addbtn=dialog.findViewById(R.id.btn_add_address);
                addbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String flat=txtFlat.getText().toString();
                        String building=txtBuilding.getText().toString();
                        String area=txtArea.getText().toString();
                        String pincode=txtPincode.getText().toString();

                        if (flat.isEmpty()) {
                            txtFlat.setError("Flat no is required");
                            txtFlat.requestFocus();
                            return;
                        }
                        if (building.isEmpty()) {
                            txtBuilding.setError("Building no is required");
                            txtBuilding.requestFocus();
                            return;
                        }
                        if (area.isEmpty()) {
                            txtArea.setError("Area/City is required");
                            txtArea.requestFocus();
                            return;
                        }
                        if (pincode.isEmpty()) {
                            txtPincode.setError("Pincode is required");
                            txtPincode.requestFocus();
                            return;
                        }
                        fulladdress=txtFlat.getText().toString()+" "+txtBuilding.getText().toString()+" "+txtArea.getText().toString()+" "+txtPincode.getText().toString();
                    }
                });


            }
        });




        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(OrderDetails.this); // Context, this, etc.
                dialog.setContentView(R.layout.layout_select_address);
                recyclerView=dialog.findViewById(R.id.recycle_address);
                recyclerView.setLayoutManager(new LinearLayoutManager(OrderDetails.this, LinearLayoutManager.VERTICAL, false));

//                addressModelList =new ArrayList<>();
//                addressModelList.add(new AddressModel("E-820 Marigold ,Bavdhan ,Pune,E-820 Marigold ,Bavdhan ,Pune","Home"));
//                addressModelList.add(new AddressModel("Rh-1,k-34,Sector-7,Vashi,Navi Mumbai 4007031230123011","Office"));
                decryptedToken = sharedPrefManager.getToken();

                getAddressesViewModel = ViewModelProviders.of(OrderDetails.this).get(GetAddressesViewModel.class);
                getAddressesViewModel.init(decryptedToken);
                getAddressesViewModel.getAd().observe(OrderDetails.this, getAddress ->{
                    if(getAddress.getState().equals("success")){
                        addressAdapter = new AddressAdapter(getAddress.getAddresses(), OrderDetails.this,dialog);
                        recyclerView.setAdapter(addressAdapter);
                    }
                    else{

                    }

                });



//                addressAdapter= new AddressAdapter(addressModelList,context,dialog);
//                recyclerView.setAdapter(addressAdapter);

                dialog.show();
            }
        });


        txtDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog = new DatePickerDialog(
                        OrderDetails.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                date = month + "/" + day + "/" + year;
                txtDateTime.setText(date);
            }
        };

       timespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                time = adapterView.getItemAtPosition(i).toString();
//                Toast.makeText(adapterView.getContext(),"Selected"+adapterView.getItemAtPosition(i).toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
    public void content(){
        sharedPrefManager=new SharedPrefManager(this);
        address = sharedPrefManager.retrieveAddress();
//        if(address==null)
//        {
//            txtaddress.setText("None");
//        }else{
//
//            txtaddress.setText(address);
//        }
        txtaddress.setText(address);
        selected_address = address;
        refresh(1000);
    }
    public void refresh(int miliseconds){

        final Handler handler=new Handler();

        final Runnable runnable =new Runnable() {
            @Override
            public void run() {
                content();
            }
        };
        handler.postDelayed(runnable,miliseconds);
    }



}
