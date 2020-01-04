package com.example.grocersapp.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import Utilities.CheckInternetConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.grocersapp.Model.LoginResponse;
import com.example.grocersapp.R;
import com.example.grocersapp.api.Api;
import com.example.grocersapp.api.RetrofitClient;
import com.example.grocersapp.repositories.LoginRepository;
import com.example.grocersapp.storage.SharedPrefManager;
import com.example.grocersapp.viewmodels.LoginViewModel;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import java.util.Timer;
import java.util.TimerTask;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private LoginViewModel loginViewModel;
    ProgressBar progressBar;
    private EditText editTextName;
    private EditText editTextPassword;
    private TextView txtForgotPassword;
    SharedPrefManager sharedPrefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPrefManager= new SharedPrefManager(Login.this);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //your method
                if(CheckInternetConnection.checkConnection(Login.this))
                {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            new Timer().cancel();
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        }
                    });

                }
                if(!CheckInternetConnection.checkConnection(Login.this))
                {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(Login.this, "इंटरनेट कनेक्टिव्हिटी उपलब्ध नाही", Toast.LENGTH_SHORT).show();

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

        txtForgotPassword = findViewById(R.id.txtForgotPass);
        findViewById(R.id.btnLogin).setOnClickListener(this);
        findViewById(R.id.txtSignIn).setOnClickListener(this);
        findViewById(R.id.txtForgotPass).setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

//        if (!sharedPrefManager.isLoggedIn()) {
//            Log.d("login", "in here");
//            Intent intent = new Intent(this, Home.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        }
//        else {
//            Intent intent = new Intent(this, Login.class);
//            startActivity(intent);
//        }
    }

    private void userLogin(){

        String contact = editTextName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (contact.isEmpty()) {
            editTextName.setError("ईमेल आवश्यक आहे");
            editTextName.requestFocus();
            return;
        }

//        if (!Patterns.EMAIL_ADDRESS.matcher(contact).matches()) {
//            editTextName.setError("Enter a valid email");
//            editTextName.requestFocus();
//            return;
//        }

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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofitClient =new RetrofitClient().getRetrofit();
        final Api api =retrofitClient.create(Api.class);
        Call<LoginResponse> call = api.userLogin(contact,password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                Log.d("loginresponse", "in here ");
                if (loginResponse.getState().equals("success")) {
                    progressBar.setVisibility(View.GONE);
                    Log.d("TOKEN", loginResponse.getToken());
                sharedPrefManager.saveToken(loginResponse.getToken());
                sharedPrefManager.saveUserData(loginResponse.getName(),loginResponse.getContact());
//                    SharedPrefManager.getInstance(Login.this)
//                            .saveUser(loginResponse.getUser());
                    Intent intent = new Intent(Login.this, Home.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);


                } else {
                    progressBar.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Toast.makeText(Login.this, "अवैध प्रमाणपत्रे!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                    Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
//        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
//        loginViewModel.init(contact,password);
//        loginViewModel.checkLogin().observe(this,loginResponse -> {
//            if(loginResponse.getState().equals("success")){
//                progressBar.setVisibility(View.GONE);
//                sharedPrefManager.saveToken(loginResponse.getToken());
//                sharedPrefManager.saveUserData(loginResponse.getName(),loginResponse.getContact());
//                Intent intent = new Intent(Login.this, Home.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//
//            }
//            else {
//                    progressBar.setVisibility(View.GONE);
//                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//                    Toast.makeText(Login.this, "Invalid credentials !", Toast.LENGTH_LONG).show();
//                }
//
//        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        new Timer().cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Timer().cancel();
    }

    @Override
    protected void onPause() {
        super.onPause();
        new Timer().cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new Timer().cancel();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
               userLogin();
                break;
            case R.id.txtSignIn:
                startActivity(new Intent(this, SignUp.class));
                break;
            case R.id.txtForgotPass:
                startActivity(new Intent(this, EnterContact.class));
                break;
        }

    }
}
