package com.example.grocersapp.Controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.grocersapp.Model.CategoryModel;
import com.example.grocersapp.R;
import com.example.grocersapp.Response.CategoriesResponse;
import com.example.grocersapp.Views.Home;
import com.example.grocersapp.Views.ProductList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context mCtx;
    private ArrayList<CategoriesResponse.Categories> categoryList;


    public CategoryAdapter(Context mCtx, ArrayList<CategoriesResponse.Categories> categoryList) {
        this.mCtx = mCtx;
        this.categoryList = categoryList;
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categoryitem_layout, parent, false);
        return new CategoryViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        final CategoriesResponse.Categories user = categoryList.get(position);

        holder.textViewName.setText(user.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(mCtx, ProductList.class);
                intent.putExtra("cat_id",user.getId());
                mCtx.startActivity(intent);
            }
        });

//TODO : Code for caching images using Picasso

//        OkHttpClient okHttpClient = new OkHttpClient();
//        okHttpClient.networkInterceptors().add(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Response originalResponse = chain.proceed(chain.request());
//                return originalResponse.newBuilder().header("Cache-Control", "max-age=" + (60 * 60 * 24 * 365)).build();
//            }
//        });
//
//        okHttpClient.setCache(new Cache(mainActivity.getCacheDir(), Integer.MAX_VALUE));
//        OkHttpDownloader okHttpDownloader = new OkHttpDownloader(okHttpClient);
//        Picasso picasso = new Picasso.Builder(mainActivity).downloader(okHttpDownloader).build();
//        picasso.load(imageURL).into(viewHolder.image);
        Picasso.get()
                .load(user.getImg())
                .fit()
                .into(holder.imageView);




    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView  textViewName;
        ImageView imageView;

        public CategoryViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.cat_image);
            textViewName = itemView.findViewById(R.id.cat_name);
        }

    }
}
