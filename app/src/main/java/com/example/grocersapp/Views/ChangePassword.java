package com.example.grocersapp.Views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import com.example.grocersapp.Model.DefaultResponse;
import com.example.grocersapp.Model.User;
import com.example.grocersapp.R;
import com.example.grocersapp.api.Api;
import com.example.grocersapp.api.RetrofitClient;
import com.example.grocersapp.storage.SharedPrefManager;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import java.util.Timer;
import java.util.TimerTask;

import Utilities.CheckInternetConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChangePassword extends AppCompatActivity {

    EditText editTextCurrentPassword,editTextNewPassword;
    Context context;
    Button btnProceed;
    ProgressBar progressBar;
    SharedPrefManager sharedPrefManager;
    String decryptedToken,contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        editTextCurrentPassword = findViewById(R.id.editPass);
        editTextNewPassword = findViewById(R.id.editConfirmPass);
        btnProceed = findViewById(R.id.btn_proceed);
        progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        contact=getIntent().getStringExtra("contact");
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //your method
                if(CheckInternetConnection.checkConnection(ChangePassword.this))
                {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            new Timer().cancel();
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        }
                    });

                }
                if(!CheckInternetConnection.checkConnection(ChangePassword.this))
                {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(ChangePassword.this, "इंटरनेट कनेक्टिव्हिटी उपलब्ध नाही", Toast.LENGTH_SHORT).show();

                        }
                    });
                }


            }
        }, 0, 1000);

        editTextCurrentPassword .setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (editTextCurrentPassword .getRight() - editTextCurrentPassword .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        editTextCurrentPassword .setTransformationMethod(null) ;
                        return true;
                    }
                }
                editTextCurrentPassword .setTransformationMethod(new PasswordTransformationMethod());
                return false;
            }
        });

        editTextNewPassword .setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (editTextNewPassword .getRight() - editTextNewPassword .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        editTextNewPassword .setTransformationMethod(null) ;
                        return true;
                    }
                }
                editTextNewPassword .setTransformationMethod(new PasswordTransformationMethod());
                return false;
            }
        });




        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePassword();
            }
        });


    }

    private void updatePassword() {
        String currentpassword = editTextCurrentPassword.getText().toString().trim();
        String newpassword = editTextNewPassword.getText().toString().trim();

        if (currentpassword.isEmpty()) {
            editTextCurrentPassword.setError("पासवर्ड आवश्यक");
            editTextCurrentPassword.requestFocus();
            return;
        }

        if (newpassword.isEmpty()) {
            editTextNewPassword.setError("नवीन पासवर्ड टाका");
            editTextNewPassword.requestFocus();
            return;
        }
        if(!currentpassword.equals(newpassword))
        {
            editTextNewPassword.setError("पासवर्ड जुळत नाहीत");
            editTextNewPassword.requestFocus();
            return;
        }




        Retrofit retrofitClient =new RetrofitClient().getRetrofit();
        final Api api =retrofitClient.create(Api.class);
        Call<DefaultResponse> call = api.updatePassword(contact,editTextNewPassword.getText().toString());


        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {

                DefaultResponse d=response.body();
                if(d.getState().equals("success")){
                    progressBar.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(ChangePassword.this, Login.class);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(ChangePassword.this,d.getMsg(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(context,t.getMessage(), Toast.LENGTH_LONG).show();


            }
        });
    }
}
