package com.example.grocersapp.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import Utilities.CheckInternetConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.Toast;

import com.example.grocersapp.Controller.CategoryAdapter;
import com.example.grocersapp.Controller.GridCategoryAdapter;
import com.example.grocersapp.Model.CategoryModel;
import com.example.grocersapp.R;
import com.example.grocersapp.Response.CategoriesResponse;
import com.example.grocersapp.api.Api;
import com.example.grocersapp.api.RetrofitClient;
import com.example.grocersapp.fragments.HomeFragment;
import com.example.grocersapp.fragments.OfferFragment;
import com.example.grocersapp.fragments.SearchFragment;
import com.example.grocersapp.fragments.SettingsFragment;
import com.example.grocersapp.storage.SharedPrefManager;
import com.example.grocersapp.viewmodels.CategoriesViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Categories extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    GridCategoryAdapter gridCategoryAdapter;
    GridView categoryRecycle;
    Context context;
    CategoriesViewModel categoriesViewModel;
    SharedPrefManager sharedPrefManager;
    private String decryptedToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("कॅटेगरीज");

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //your method
                if(CheckInternetConnection.checkConnection(Categories.this))
                {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            new Timer().cancel();
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        }
                    });

                }
                if(!CheckInternetConnection.checkConnection(Categories.this))
                {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(Categories.this, "इंटरनेट कनेक्टिव्हिटी उपलब्ध नाही ", Toast.LENGTH_SHORT).show();

                        }
                    });
                }


            }
        }, 0, 1000);


        categoryLoad();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
   }

    private void displayFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.categories_layout, fragment)
                .commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = null;
        int id = menuItem.getItemId();
        if(id == R.id.menu_home)
        {
            fragment = new HomeFragment();

        }else if(id == R.id.menu_account)
        {
            fragment = new SettingsFragment();

        }

        if(fragment != null){
            displayFragment(fragment);
        }

        return false;

    }

    private void categoryLoad()
    {
        categoryRecycle =findViewById(R.id.grid_product_view);

//        sharedPrefManager = new SharedPrefManager(this);
//
//        decryptedToken = sharedPrefManager.getToken();
//
//        Retrofit retrofitClient =new RetrofitClient().getRetrofit();
//        final Api api =retrofitClient.create(Api.class);
//        Call<CategoriesResponse> call = api.getCategories(decryptedToken);
//
//
//        call.enqueue(new Callback<CategoriesResponse>() {
//            @Override
//            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
//                CategoriesResponse categoriesResponse = response.body();
//                gridCategoryAdapter = new GridCategoryAdapter( categoriesResponse.getCategories(),getApplicationContext());
//                categoryRecycle.setAdapter(gridCategoryAdapter);
//            }
//
//            @Override
//            public void onFailure(Call<CategoriesResponse> call, Throwable t) {
//                Log.d("TAG","Response = "+t.toString());
//            }
//        });
        sharedPrefManager = new SharedPrefManager(this);

        decryptedToken = sharedPrefManager.getToken();
        categoriesViewModel = ViewModelProviders.of(this).get(CategoriesViewModel.class);
        categoriesViewModel.init(decryptedToken);
        categoriesViewModel.getCategoriesRepository().observe(this,categoriesResponse -> {
            gridCategoryAdapter = new GridCategoryAdapter( categoriesResponse.getCategories(),getApplicationContext());
                categoryRecycle.setAdapter(gridCategoryAdapter);
        });
    }

//    private void categoryLoad()
//    {
//        gridView = findViewById(R.id.grid_product_view);
//        categoryList = new ArrayList<CategoryModel>();
//        categoryList.add(new CategoryModel("https://www.heartfoundation.org.nz/media/images/nutrition/page-heros/are-whole-grains-good-for-you_737_553_c1.jpg", "Dhanya", 1));
//        categoryList.add(new CategoryModel("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/shopping-bag-full-of-fresh-vegetables-and-fruits-royalty-free-image-1128687123-1564523576.jpg?crop=0.669xw:1.00xh;0.300xw,0&resize=640:*", "Fruits", 2));
//        categoryList.add(new CategoryModel("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/shopping-bag-full-of-fresh-vegetables-and-fruits-royalty-free-image-1128687123-1564523576.jpg?crop=0.669xw:1.00xh;0.300xw,0&resize=640:*", "Vegetables", 3));
//        categoryList.add(new CategoryModel("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/shopping-bag-full-of-fresh-vegetables-and-fruits-royalty-free-image-1128687123-1564523576.jpg?crop=0.669xw:1.00xh;0.300xw,0&resize=640:*", "Vegetables", 3));
//        categoryList.add(new CategoryModel("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/shopping-bag-full-of-fresh-vegetables-and-fruits-royalty-free-image-1128687123-1564523576.jpg?crop=0.669xw:1.00xh;0.300xw,0&resize=640:*", "Vegetables", 3));
//        categoryList.add(new CategoryModel("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/shopping-bag-full-of-fresh-vegetables-and-fruits-royalty-free-image-1128687123-1564523576.jpg?crop=0.669xw:1.00xh;0.300xw,0&resize=640:*", "Vegetables", 3));
//        categoryList.add(new CategoryModel("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/shopping-bag-full-of-fresh-vegetables-and-fruits-royalty-free-image-1128687123-1564523576.jpg?crop=0.669xw:1.00xh;0.300xw,0&resize=640:*", "Vegetables", 3));
//        categoryList.add(new CategoryModel("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/shopping-bag-full-of-fresh-vegetables-and-fruits-royalty-free-image-1128687123-1564523576.jpg?crop=0.669xw:1.00xh;0.300xw,0&resize=640:*", "Vegetables", 3));
//        categoryList.add(new CategoryModel("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/shopping-bag-full-of-fresh-vegetables-and-fruits-royalty-free-image-1128687123-1564523576.jpg?crop=0.669xw:1.00xh;0.300xw,0&resize=640:*", "Vegetables", 3));
//        categoryList.add(new CategoryModel("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/shopping-bag-full-of-fresh-vegetables-and-fruits-royalty-free-image-1128687123-1564523576.jpg?crop=0.669xw:1.00xh;0.300xw,0&resize=640:*", "Vegetables", 3));
//        categoryList.add(new CategoryModel("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/shopping-bag-full-of-fresh-vegetables-and-fruits-royalty-free-image-1128687123-1564523576.jpg?crop=0.669xw:1.00xh;0.300xw,0&resize=640:*", "Vegetables", 3));
//        categoryList.add(new CategoryModel("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/shopping-bag-full-of-fresh-vegetables-and-fruits-royalty-free-image-1128687123-1564523576.jpg?crop=0.669xw:1.00xh;0.300xw,0&resize=640:*", "Vegetables", 3));
//        categoryList.add(new CategoryModel("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/shopping-bag-full-of-fresh-vegetables-and-fruits-royalty-free-image-1128687123-1564523576.jpg?crop=0.669xw:1.00xh;0.300xw,0&resize=640:*", "Vegetables", 3));
//        categoryList.add(new CategoryModel("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/shopping-bag-full-of-fresh-vegetables-and-fruits-royalty-free-image-1128687123-1564523576.jpg?crop=0.669xw:1.00xh;0.300xw,0&resize=640:*", "Vegetables", 3));
//        categoryList.add(new CategoryModel("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/shopping-bag-full-of-fresh-vegetables-and-fruits-royalty-free-image-1128687123-1564523576.jpg?crop=0.669xw:1.00xh;0.300xw,0&resize=640:*", "Vegetables", 3));
//        categoryList.add(new CategoryModel("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/shopping-bag-full-of-fresh-vegetables-and-fruits-royalty-free-image-1128687123-1564523576.jpg?crop=0.669xw:1.00xh;0.300xw,0&resize=640:*", "Vegetables", 3));
//        categoryList.add(new CategoryModel("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/shopping-bag-full-of-fresh-vegetables-and-fruits-royalty-free-image-1128687123-1564523576.jpg?crop=0.669xw:1.00xh;0.300xw,0&resize=640:*", "Vegetables", 3));
//        categoryList.add(new CategoryModel("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/shopping-bag-full-of-fresh-vegetables-and-fruits-royalty-free-image-1128687123-1564523576.jpg?crop=0.669xw:1.00xh;0.300xw,0&resize=640:*", "Vegetables", 3));
//
//        gridCategoryAdapter = new GridCategoryAdapter(categoryList,context);
//        gridView.setAdapter(gridCategoryAdapter);
//
////        Retrofit retrofitClient =new RetrofitClient().getRetrofit();
////        final Api api =retrofitClient.create(Api.class);
////        Call<List<CategoryModel>> call = api.getCategories();
////
////        call.enqueue(new Callback<List<CategoryModel>>() {
////            @Override
////            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
////                categoryList = response.body();
////                Log.d("TAG","Response = "+categoryList);
////                gridCategoryAdapter.setProductList(categoryList);
////            }
////
////            @Override
////            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {
////                Log.d("TAG","Response = "+t.toString());
////            }
////        });
//    }
}
