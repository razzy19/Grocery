package com.example.grocersapp.Views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grocersapp.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import java.util.Timer;
import java.util.TimerTask;

import Utilities.CheckInternetConnection;

public class EnterContact extends AppCompatActivity {
    Button btnProceed;
    EditText txtContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_contact);

        btnProceed=findViewById(R.id.btn_proceed);
        txtContact=findViewById(R.id.editPhone);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //your method
                if(CheckInternetConnection.checkConnection(EnterContact.this))
                {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            new Timer().cancel();
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        }
                    });

                }
                if(!CheckInternetConnection.checkConnection(EnterContact.this))
                {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(EnterContact.this, "इंटरनेट कनेक्टिव्हिटी उपलब्ध नाही", Toast.LENGTH_SHORT).show();

                        }
                    });
                }


            }
        }, 0, 1000);


        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EnterContact.this,ChangePassword.class);
                intent.putExtra("contact",txtContact.getText().toString());
                startActivity(intent);
            }
        });
    }
}
