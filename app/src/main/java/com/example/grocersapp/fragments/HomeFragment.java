package com.example.grocersapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocersapp.Controller.CategoryAdapter;
import com.example.grocersapp.Controller.GridProductLayoutAdapter;
import com.example.grocersapp.Controller.ManageAddressAdapter;
import com.example.grocersapp.Controller.ProductItemAdapter;
import com.example.grocersapp.Controller.ProductListAdapter;
import com.example.grocersapp.Model.CategoryModel;
import com.example.grocersapp.Model.LoginResponse;
import com.example.grocersapp.Model.ProductItem;
import com.example.grocersapp.R;
import com.example.grocersapp.Response.CategoriesResponse;
import com.example.grocersapp.Response.GetProductResponse;
import com.example.grocersapp.Views.Categories;
import com.example.grocersapp.Views.Home;
import com.example.grocersapp.Views.ManageAddress;
import com.example.grocersapp.Views.ProductList;
import com.example.grocersapp.api.Api;
import com.example.grocersapp.api.RetrofitC;
import com.example.grocersapp.api.RetrofitClient;
import com.example.grocersapp.storage.SharedPrefManager;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import Utilities.CheckInternetConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private RecyclerView categoryRecycle,recycledhayna,recyclecold;

    private CategoryAdapter categoryAdapter;
    private ArrayList<CategoriesResponse> categoryList;

    private ProductItemAdapter productItemAdapter;
    private List<ProductItem> productList;
    Context context;

    GridView gridView;
    GridProductLayoutAdapter gridProductLayoutAdapter;

    SharedPrefManager sharedPrefManager;
    private String decryptedToken;

    Button btnCat,btnOffer,btnOrders;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragment, container, false);
        ((Button) v.findViewById(R.id.btn_view_offers)).setOnClickListener(this);
        ((Button) v.findViewById(R.id.btn_view_orders)).setOnClickListener(this);

        return v;


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        categoryLoad(view);
        dhanyaItems(view);
        coldDrinksItems(view);

        btnCat=view.findViewById(R.id.btn_view_categories);
        btnCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), Categories.class);
                startActivity(intent);
            }
        });



    }

   private void categoryLoad(View view)
    {
        categoryRecycle = view.findViewById(R.id.recycler_category);
        categoryRecycle.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

//        categoryList = new ArrayList<CategoriesResponse.Categories>();
//        categoryList.add(new CategoryModel("https://www.heartfoundation.org.nz/media/images/nutrition/page-heros/are-whole-grains-good-for-you_737_553_c1.jpg", "Dhanya", 1));
//        categoryList.add(new CategoryModel("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/shopping-bag-full-of-fresh-vegetables-and-fruits-royalty-free-image-1128687123-1564523576.jpg?crop=0.669xw:1.00xh;0.300xw,0&resize=640:*", "Fruits", 2));
//        categoryList.add(new CategoryModel("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/shopping-bag-full-of-fresh-vegetables-and-fruits-royalty-free-image-1128687123-1564523576.jpg?crop=0.669xw:1.00xh;0.300xw,0&resize=640:*", "Vegetables", 3));
//        categoryList.add(new CategoryModel("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/shopping-bag-full-of-fresh-vegetables-and-fruits-royalty-free-image-1128687123-1564523576.jpg?crop=0.669xw:1.00xh;0.300xw,0&resize=640:*", "Vegetables", 3));
//        categoryAdapter = new CategoryAdapter(context, categoryList);
//        categoryRecycle.setAdapter(categoryAdapter);

        sharedPrefManager = new SharedPrefManager(this.getActivity());

        decryptedToken = sharedPrefManager.getToken();

        Retrofit retrofitClient =new RetrofitClient().getRetrofit();
        final Api api =retrofitClient.create(Api.class);
        Call<CategoriesResponse> call = api.getCategories(decryptedToken);


        call.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
//                if (response.isSuccessful() &&
//                        response.raw().networkResponse() != null
//
//                    ) {
//                    Log.e("Network", "response came from cache");
//                    // the response hasn't changed, so you do not need to do anything
//                    return;
//                }
                CategoriesResponse categoriesResponse = response.body();
                   categoryAdapter = new CategoryAdapter( getActivity(),categoriesResponse.getCategories());
                    categoryRecycle.setAdapter(categoryAdapter);
                Log.d("category", "in here");
                if (response.raw().cacheResponse() != null) {
                    Log.e("Network", "response came from cache");
                }

                if (response.raw().networkResponse() != null) {
                    Log.e("Network", "response came from server");
                }
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
            }
        });
}

    private void dhanyaItems(View view){
        recycledhayna = view.findViewById(R.id.recycle_dhanya);
        recycledhayna.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

//        productList=new ArrayList<>();
//
//        productList.add(new ProductItem("https://previews.123rf.com/images/utima/utima1311/utima131100146/24077398-orange-fruit-isolated-on-white-background.jpg","संत्र","35"));
//        productList.add(new ProductItem("https://5.imimg.com/data5/NR/UP/MY-2/123-jpg-500x500.jpg","बोर बॉन बिस्किट","25"));
//        productList.add(new ProductItem("https://images-na.ssl-images-amazon.com/images/I/812o4EQXPKL._SX569_.jpg","मॅगी","20"));
//        productList.add(new ProductItem("https://i5.walmartimages.ca/images/Enlarge/094/514/6000200094514.jpg","सफरचंद","44"));
//        productItemAdapter= new ProductItemAdapter(context, productList,this);
//        productRecycle.setAdapter(productItemAdapter);



        sharedPrefManager = new SharedPrefManager(getActivity());

        decryptedToken = sharedPrefManager.getToken();

        Retrofit retrofitClient =new RetrofitC(getActivity()).getRetrofit();
        final Api api =retrofitClient.create(Api.class);
        Call<GetProductResponse> call = api.getProducts(decryptedToken,1);


        call.enqueue(new Callback<GetProductResponse>() {
            @Override
            public void onResponse(Call<GetProductResponse> call, Response<GetProductResponse> response) {
                GetProductResponse getProductResponse = response.body();
                productItemAdapter = new ProductItemAdapter(getActivity(),getProductResponse.getGetProducts(),HomeFragment.this);
                recycledhayna.setAdapter(productItemAdapter);
            }

            @Override
            public void onFailure(Call<GetProductResponse> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
            }
        });


//        Retrofit retrofitClient =new RetrofitClient().getRetrofit();
//        final Api api =retrofitClient.create(Api.class);
//        Call<List<ProductItem>> call = api.getProducts();
//
//        call.enqueue(new Callback<List<ProductItem>>() {
//            @Override
//            public void onResponse(Call<List<ProductItem>> call, Response<List<ProductItem>> response) {
//                productList = response.body();
//                Log.d("TAG","Response = "+productList);
//                productItemAdapter.setProductList(productList);
//            }
//
//            @Override
//            public void onFailure(Call<List<ProductItem>> call, Throwable t) {
//                Log.d("TAG","Response = "+t.toString());
//            }
//        });
    }



    private void coldDrinksItems(View view){
        recyclecold = view.findViewById(R.id.recycle_cold_drinks);
        recyclecold.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

//        productList =new ArrayList<>();
//
//        productList.add(new ProductItem("https://previews.123rf.com/images/utima/utima1311/utima131100146/24077398-orange-fruit-isolated-on-white-background.jpg","संत्र","35"));
//        productList.add(new ProductItem("https://5.imimg.com/data5/NR/UP/MY-2/123-jpg-500x500.jpg","बोर बॉन बिस्किट","25"));
//        productList.add(new ProductItem("https://images-na.ssl-images-amazon.com/images/I/812o4EQXPKL._SX569_.jpg","मॅगी","20"));
//        productList.add(new ProductItem("https://i5.walmartimages.ca/images/Enlarge/094/514/6000200094514.jpg","सफरचंद","44"));
//        productItemAdapter= new ProductItemAdapter(context, productList,this);
//        productRecycle.setAdapter(productItemAdapter);

        sharedPrefManager = new SharedPrefManager(getActivity());

        decryptedToken = sharedPrefManager.getToken();

        Retrofit retrofitClient =new RetrofitC(getActivity()).getRetrofit();
        final Api api =retrofitClient.create(Api.class);
        Call<GetProductResponse> call = api.getProducts(decryptedToken,2);


        call.enqueue(new Callback<GetProductResponse>() {
            @Override
            public void onResponse(Call<GetProductResponse> call, Response<GetProductResponse> response) {
                GetProductResponse getProductResponse = response.body();
                productItemAdapter = new ProductItemAdapter(getActivity(),getProductResponse.getGetProducts(),HomeFragment.this);
                recyclecold.setAdapter(productItemAdapter);
            }

            @Override
            public void onFailure(Call<GetProductResponse> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
            }
        });




//
//        Retrofit retrofitClient =new RetrofitClient().getRetrofit();
//        final Api api =retrofitClient.create(Api.class);
//        Call<List<ProductItem>> call = api.getProducts();
//
//        call.enqueue(new Callback<List<ProductItem>>() {
//            @Override
//            public void onResponse(Call<List<ProductItem>> call, Response<List<ProductItem>> response) {
//                productList = response.body();
//                Log.d("TAG","Response = "+productList);
//                productItemAdapter.setProductList(productList);
//            }
//
//            @Override
//            public void onFailure(Call<List<ProductItem>> call, Throwable t) {
//                Log.d("TAG","Response = "+t.toString());
//            }
//        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_view_offers:
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                OfferFragment fragment =new OfferFragment();
                Bundle arguments = new Bundle();
                arguments.putInt("id",1);
                fragment.setArguments(arguments);
                ft.replace(R.id.content_home, fragment, "NewFragmentTag");
                ft.addToBackStack(null);
                ft.commit();
                break;
            case R.id.btn_view_orders:
                final FragmentTransaction ft1 = getFragmentManager().beginTransaction();
                OfferFragment fragment1 =new OfferFragment();
                Bundle arguments1 = new Bundle();
                arguments1.putInt("id",2);
                fragment1.setArguments(arguments1);
                ft1.replace(R.id.content_home, fragment1, "NewFragmentTag");
                ft1.addToBackStack(null);
                ft1.commit();
                break;
        }
    }
}


