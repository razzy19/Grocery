package com.example.grocersapp.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocersapp.Controller.GridProductLayoutAdapter;
import com.example.grocersapp.Model.LoginResponse;
import com.example.grocersapp.Model.ProductItem;
import com.example.grocersapp.R;
import com.example.grocersapp.Views.Home;
import com.example.grocersapp.api.Api;
import com.example.grocersapp.api.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchFragment extends Fragment {
    GridView gridView;
    GridProductLayoutAdapter gridProductLayoutAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<ProductItem> productList;
    Context context;
    ProgressBar progressBar;
    TextView search;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_search_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadSearchView(view);
        progressBar = view.findViewById(R.id.progressBar);

    }


    private void loadSearchView(View view){
        gridView = view.findViewById(R.id.grid_product_view);
        productList=new ArrayList<>();
        productList.add(new ProductItem("","सफरचंद","Rs. 44"));
        productList.add(new ProductItem("","बोर बॉन बिस्किट","Rs. 25"));
        productList.add(new ProductItem("","मॅगी","Rs. 20"));
        productList.add(new ProductItem("","सफरचंद","Rs. 44"));
        productList.add(new ProductItem("","बोर बॉन बिस्किट","Rs. 25"));
        productList.add(new ProductItem("","मॅगी","Rs. 20"));
        productList.add(new ProductItem("","सफरचंद","Rs. 44"));
        gridProductLayoutAdapter = new GridProductLayoutAdapter(productList, context);
        gridView.setAdapter(gridProductLayoutAdapter);


    }
//    public void fetchContact(String key){
//
//        Retrofit retrofitClient =new RetrofitClient().getRetrofit();
//        final Api api =retrofitClient.create(Api.class);
//        Call<List<ProductItem>> call = api.getSearchProducts(key);
//
//
//        call.enqueue(new Callback<List<ProductItem>>() {
//            @Override
//            public void onResponse(Call<List<ProductItem>> call, Response<List<ProductItem>> response) {
//                progressBar.setVisibility(View.GONE);
//                productList = response.body();
//                gridProductLayoutAdapter = new GridProductLayoutAdapter(productList,context);
//                gridView.setAdapter(gridProductLayoutAdapter);
//               gridProductLayoutAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Call<List<ProductItem>> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
//                Toast.makeText(context, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        menu.clear();
//        inflater.inflate(R.menu.search, menu);
//        MenuItem item = menu.findItem(R.id.search);
//        SearchView searchView = new SearchView(((Home) context).getSupportActionBar().getThemedContext());
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                fetchContact(query);
//                return false;
//            }
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                fetchContact(newText);
//
//                return false;
//            }
//        });
//        searchView.setOnClickListener(new View.OnClickListener() {
//                                          @Override
//                                          public void onClick(View v) {
//
//                                          }
//                                      }
//        );
//    }

}