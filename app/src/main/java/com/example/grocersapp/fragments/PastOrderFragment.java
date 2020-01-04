package com.example.grocersapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.grocersapp.Controller.GridProductLayoutAdapter;
import com.example.grocersapp.Model.ProductItem;
import com.example.grocersapp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PastOrderFragment extends Fragment {

    GridView gridView;
    GridProductLayoutAdapter gridProductLayoutAdapter;
    private List<ProductItem> productList;
    Context context;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.offers_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadSearchView(view);


    }

    private void loadSearchView(View view) {

        gridView = view.findViewById(R.id.grid_product_view);
        productList = new ArrayList<>();
        productList.add(new ProductItem("", "सफरचंद", "Rs. 44"));
        productList.add(new ProductItem("", "बोर बॉन बिस्किट", "Rs. 25"));
        productList.add(new ProductItem("", "मॅगी", "Rs. 20"));
        productList.add(new ProductItem("", "सफरचंद", "Rs. 44"));

        gridProductLayoutAdapter = new GridProductLayoutAdapter(productList, context);
        gridView.setAdapter(gridProductLayoutAdapter);


//        Retrofit retrofitClient =new RetrofitClient().getRetrofit();
//        final Api api =retrofitClient.create(Api.class);
//        Call<List<ProductItem>> call = api.getProducts();
//
//        call.enqueue(new Callback<List<ProductItem>>() {
//            @Override
//            public void onResponse(Call<List<ProductItem>> call, Response<List<ProductItem>> response) {
//                productList = response.body();
//                Log.d("TAG","Response = "+productList);
//               gridProductLayoutAdapter.setProductList(productList);
//            }
//
//            @Override
//            public void onFailure(Call<List<ProductItem>> call, Throwable t) {
//                Log.d("TAG","Response = "+t.toString());
//            }
//        });
    }
}
