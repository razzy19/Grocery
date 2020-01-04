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

import com.example.grocersapp.Model.CategoryModel;
import com.example.grocersapp.Model.ProductItem;
import com.example.grocersapp.R;
import com.example.grocersapp.Response.CategoriesResponse;
import com.example.grocersapp.Views.ProductList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GridCategoryAdapter extends BaseAdapter {

    private ArrayList<CategoriesResponse.Categories> productItemList;
    private Context mContext;

    public GridCategoryAdapter(ArrayList<CategoriesResponse.Categories> productItemList, Context context) {
        this.productItemList = productItemList;
        mContext=context;
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
    public View getView(final int i, final View itemView, final ViewGroup parent) {
        View view;
        if(itemView==null)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categoryitem_layout,null);
            view.setBackgroundColor(Color.parseColor("#ffffff"));

            TextView  textViewName;
            ImageView imageView;

            CategoriesResponse.Categories categoryModel=productItemList.get(i);
            textViewName = view.findViewById(R.id.cat_name);
            imageView=view.findViewById(R.id.cat_image);

            textViewName.setText(productItemList.get(i).getName());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext, ProductList.class);
                    intent.putExtra("cat_id",productItemList.get(i).getId());
                    parent.getContext().startActivity(intent);

                }
            });

            Picasso.get()
                    .load(categoryModel.getImg())
                    .fit()
                    .into(imageView);


        }else{
            view=itemView;
        }


        return view;
    }
}
