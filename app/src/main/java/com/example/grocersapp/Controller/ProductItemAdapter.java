package com.example.grocersapp.Controller;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocersapp.Model.CartData;
import com.example.grocersapp.Model.CartItem;
import com.example.grocersapp.Model.CategoryModel;
import com.example.grocersapp.Model.ProductItem;
import com.example.grocersapp.Model.CartData;
import com.example.grocersapp.R;
import com.example.grocersapp.Response.GetProductResponse;
import com.example.grocersapp.Views.Cart;
import com.example.grocersapp.database.DatabaseClient;
import com.example.grocersapp.fragments.HomeFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class ProductItemAdapter extends RecyclerView.Adapter<ProductItemAdapter.ProductItemViewHolder> {
    Context mCtx;
    String prodName,prodPrice;
    CartData cartData;
    String dupliparam;
    List<CartData> taskList;
    List<CartData> taskList2;
    int item_id=0;
    public ProductItemAdapter(Context mCtx, List<GetProductResponse.GetProducts> productList, HomeFragment homeFragment) {
        this.mCtx = homeFragment.getActivity();
        this.productList = productList;
    }




    private List<GetProductResponse.GetProducts> productList;


    @NonNull
    @Override
    public ProductItemAdapter.ProductItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productitem_layout, parent, false);
        return new ProductItemAdapter.ProductItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductItemAdapter.ProductItemViewHolder holder, int position) {

        final GetProductResponse.GetProducts product = productList.get(position);

        holder.txtProdName.setText(product.getName());
        holder.txtProdPrice.setText(product.getPrice()+"");

        prodName = product.getName();
        prodPrice =  String.valueOf(product.getPrice());
        item_id=product.getItem_id();
        Log.d("itemid", String.valueOf(item_id));
        // Glide.with(mCtx).load(productList.get(position).getProdImage()).apply(RequestOptions.centerCropTransform()).into(holder.imageView);

        Picasso.get()
                .load(product.getImgurl())
                .fit()
                .into(holder.imageView);


        holder.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prodName = product.getName();
                prodPrice = String.valueOf(product.getPrice());
                item_id=product.getItem_id();
                dupliparam = prodName;
                checkDuplicateItem(dupliparam);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductItemViewHolder extends RecyclerView.ViewHolder {
        TextView txtProdName, txtProdPrice;
        ImageView imageView;
        Button btnSave;

        public ProductItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.prodImg);
            txtProdName = itemView.findViewById(R.id.txt_prodName);
            txtProdPrice = itemView.findViewById(R.id.txt_ProdPrice);
            btnSave = itemView.findViewById(R.id.btn_edit);


        }
    }

    private void saveTask() {

         class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task
                CartData task = new CartData();
                task.setName(prodName);
                task.setPrice(prodPrice);
                task.setItem_id(String.valueOf(item_id));
                Log.d("PRODPRICE", prodPrice);
                Log.d("aa", item_id+"");
                task.setQty("1");
                DatabaseClient.getInstance(mCtx).getAppDatabase()
                            .taskDao()
                            .insert(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(mCtx, "Added to cart", Toast.LENGTH_LONG).show();
            }
        }
         SaveTask s = new SaveTask();
         s.execute();


    }
    private void checkDuplicateItem(final String dupliparam2) {

        class CheckDuplicate extends AsyncTask<Void, Void, List<CartData>> {

            @Override
            protected List<CartData> doInBackground(Void... voids) {
                taskList = DatabaseClient
                        .getInstance(mCtx)
                        .getAppDatabase()
                        .taskDao()
                        .checkDuplicate(dupliparam2);

                return taskList;
            }

            @Override
            protected void onPostExecute(List<CartData> tasks) {
                super.onPostExecute(tasks);
                taskList = tasks;
                Log.d("SIZELIST", tasks.size()+"");
                if(taskList.size() == 0)
                {
                    saveTask();

                }
                else
                {
                    Toast.makeText(mCtx,"Product is already present in cart",Toast.LENGTH_LONG).show();
                }

            }
        }
        CheckDuplicate s = new CheckDuplicate();
        s.execute();

    }
}
