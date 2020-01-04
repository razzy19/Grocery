package com.example.grocersapp.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import Utilities.CheckInternetConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.grocersapp.Model.DefaultResponse;
import com.example.grocersapp.Model.SignupResponse;
import com.example.grocersapp.R;
import com.example.grocersapp.api.Api;
import com.example.grocersapp.api.RetrofitClient;
import com.example.grocersapp.storage.SharedPrefManager;
import com.example.grocersapp.viewmodels.SignupViewModel;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import java.util.Timer;
import java.util.TimerTask;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    SharedPrefManager sharedPrefManager;
    private EditText editTextName, editTextPassword, editTextPhone, editTextAddress;
    ProgressBar progressBar;
    private SignupViewModel signupViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        sharedPrefManager= new SharedPrefManager(SignUp.this);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //your method
                if(CheckInternetConnection.checkConnection(SignUp.this))
                {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            new Timer().cancel();
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        }
                    });

                }
                if(!CheckInternetConnection.checkConnection(SignUp.this))
                {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(SignUp.this, "इंटरनेट कनेक्टिव्हिटी उपलब्ध नाही", Toast.LENGTH_SHORT).show();

                        }
                    });
                }


            }
        }, 0, 1000);
        progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);

        editTextName = findViewById(R.id.editName);
        editTextPassword = findViewById(R.id.editPass);
        editTextPhone = findViewById(R.id.editPhone);
        editTextAddress= findViewById(R.id.editAddress);

        findViewById(R.id.btnSignUp).setOnClickListener(this);
        findViewById(R.id.txtSignIn).setOnClickListener(this);

        editTextPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (editTextPassword.getRight() - editTextPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        editTextPassword.setTransformationMethod(null) ;
                        return true;
                    }
                }
                editTextPassword.setTransformationMethod(new PasswordTransformationMethod());
                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void userSignUp() {
        String name = editTextName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();

        if (name.isEmpty()) {
            editTextName.setError("ईमेल आवश्यक आहे");
            editTextName.requestFocus();
            return;
        }

     /*   if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }*/

        if (password.isEmpty()) {
            editTextPassword.setError("पासवर्ड आवश्यक");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("पासवर्ड किमान 6 वर्ण लांब असावा");
            editTextPassword.requestFocus();
            return;
        }

        if (name.isEmpty()) {
            editTextName.setError("नाव आवश्यक");
            editTextName.requestFocus();
            return;
        }

        if (address.isEmpty()) {
            editTextAddress.setError("पत्ता आवश्यक");
            editTextAddress.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            editTextPhone.setError("फोन नंबर आवश्यक");
            editTextPhone.requestFocus();
            return;
        }
        if (phone.length()==9) {
            editTextPhone.setError("वैध क्रमांक प्रविष्ट करा");
            editTextPhone.requestFocus();
            return;
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//        public Api getApi() {
//            return retrofit.create(Api.class);
//        }
//        Call<DefaultResponse> call = RetrofitClient();
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofitClient =new RetrofitClient().getRetrofit();
        final Api api =retrofitClient.create(Api.class);
        Call<SignupResponse> call = api.createUser(address,password,name,phone);

        call.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {

                    SignupResponse dr = response.body();
                    if(dr.getState().equals("success"))
                    {
                        progressBar.setVisibility(View.GONE);
                        sharedPrefManager.saveAddress(address);
                        startActivity(new Intent(SignUp.this, Login.class));
                        Toast.makeText(SignUp.this, "यशस्वीरित्या साइन अप केले!", Toast.LENGTH_LONG).show();
                    }
                    else if(dr.getState().equals("fail"))
                    {
                        progressBar.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        Toast.makeText(SignUp.this, "वापरकर्ता आधीच अस्तित्वात आहे!", Toast.LENGTH_LONG).show();
                    }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {


                Toast.makeText(SignUp.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
//        signupViewModel = ViewModelProviders.of(this).get(SignupViewModel.class);
//        signupViewModel.init(address,password,name,phone);
//        signupViewModel.signupResponseLiveData().observe(this,signupResponse -> {
//            if(signupResponse.getState().equals("success")){
//                progressBar.setVisibility(View.GONE);
//                startActivity(new Intent(SignUp.this, Login.class));
//                Toast.makeText(SignUp.this, "Signed up successfully !", Toast.LENGTH_LONG).show();
//            }
//            else if(signupResponse.getState().equals("fail"))
//                    {
//                        progressBar.setVisibility(View.GONE);
//                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//                        Toast.makeText(SignUp.this, "User already exist !", Toast.LENGTH_LONG).show();
//                    }
//        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp:
                userSignUp();
                break;
            case R.id.txtSignIn:

                startActivity(new Intent(this, Login.class));

                break;
        }

    }
}
