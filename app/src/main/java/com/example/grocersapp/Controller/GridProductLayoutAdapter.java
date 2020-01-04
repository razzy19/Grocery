package com.example.grocersapp.Controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.grocersapp.Model.ProductItem;
import com.example.grocersapp.R;


import java.util.List;

public class GridProductLayoutAdapter extends BaseAdapter {
    private List<ProductItem> productItemList;
    private Context mContext;

    public GridProductLayoutAdapter(List<ProductItem> productItemList, Context context) {
        this.productItemList = productItemList;
        mContext=context;
    }

    public void setProductList(List<ProductItem> categoryList) {
        this.productItemList = categoryList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return productItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View convertview,final ViewGroup parent) {
        View view;
        if(convertview==null)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productitem_layout,null);
            view.setBackgroundColor(Color.parseColor("#ffffff"));

            TextView txtProdName,txtProdPrice,txtProdOffer;
            ImageView imageView;

            imageView = view.findViewById(R.id.prodImg);
            txtProdName = view.findViewById(R.id.txt_prodName);
            txtProdPrice = view.findViewById(R.id.txt_ProdPrice);


            txtProdName.setText(productItemList.get(i).getProductName());
            txtProdPrice.setText(productItemList.get(i).getProductPrice());

        }
        else{
            view=convertview;
        }


        return view;
    }
}
