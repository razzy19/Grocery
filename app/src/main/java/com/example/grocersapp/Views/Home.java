package com.example.grocersapp.Views;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocersapp.R;
import com.example.grocersapp.fragments.HomeFragment;
import com.example.grocersapp.fragments.NotifyFragment;
import com.example.grocersapp.fragments.OfferFragment;
import com.example.grocersapp.fragments.SearchFragment;
import com.example.grocersapp.fragments.SettingsFragment;
import com.example.grocersapp.storage.SharedPrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Utilities.CheckInternetConnection;

public class Home extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener,BottomNavigationView.OnNavigationItemSelectedListener{
    private Toolbar toolbar;
    TextView txtName;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        sharedPrefManager = new SharedPrefManager(this);




        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //your method
                if(CheckInternetConnection.checkConnection(Home.this))
                {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            new Timer().cancel();
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        }
                    });

                }
                if(!CheckInternetConnection.checkConnection(Home.this))
                {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(Home.this, "इंटरनेट कनेक्टिव्हिटी उपलब्ध नाही ", Toast.LENGTH_SHORT).show();

                        }
                    });
                }


            }
        }, 0, 1000);






//        PermissionListener permissionlistener = new PermissionListener() {
//
//            @Override
//            public void onPermissionGranted() {
//                Toast.makeText(Home.this, "Permission Granted", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
//                Toast.makeText(Home.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
//            }
//
//
//        };
//        TedPermission.with(this)
//                .setPermissionListener(permissionlistener)
//                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
//                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .check();

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView botnavigationView = findViewById(R.id.bottom_nav);
        botnavigationView.setOnNavigationItemSelectedListener(this);


       displayFragment(new HomeFragment());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Fragment fragment = null;
        int id= item.getItemId();


        if(id == R.id.main_shopping){
            Intent intent=new Intent(getApplicationContext(),Cart.class);
            startActivity(intent);
        }

        if(fragment != null){
            displayFragment(fragment);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        txtName=findViewById(R.id.txtName);
        txtName.setText(sharedPrefManager.getEncryptedName());
        Fragment fragment = null;
        int id = item.getItemId();

        //Navigation drawer events
        if (id == R.id.nav_manage) {
            Intent intent = new Intent(Home.this, ManageAddress.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_ord_history) {

            Intent intent = new Intent(Home.this, OrderHistoryList.class);
            startActivity(intent);

        }else if (id == R.id.nav_logout) {
                sharedPrefManager.clear();
                SharedPrefManager.getInstance(Home.this).clear();
                Intent intent = new Intent(Home.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

        }


        //Bottom navigation events
        else if(id == R.id.menu_home)
        {
            fragment = new HomeFragment();

        }else if(id == R.id.menu_account)
        {
            fragment = new SettingsFragment();

        }

        if(fragment != null){
            displayFragment(fragment);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;

    }

    private void displayFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_home, fragment)
                .commit();
    }




}
