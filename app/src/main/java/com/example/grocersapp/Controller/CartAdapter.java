package com.example.grocersapp.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocersapp.Model.CartItem;
import com.example.grocersapp.Model.ProductItem;
import com.example.grocersapp.Model.CartData;
import com.example.grocersapp.R;
import com.example.grocersapp.Views.Cart;
import com.example.grocersapp.database.DatabaseClient;
import com.example.grocersapp.storage.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartItemViewHolder> {
    private Context mctx;
    private List<CartData> cartItemList,list2;
    private String prc,prc2;
    private int c ;
    private int count = 1;
    private CartData cartItem,cartItem2;
    private int price=0;
    private int total = 0;
    ConstraintLayout layoutemptycart;
    RecyclerView recyclerView;
    Button button;
    SharedPrefManager sharedPrefManager;
    private int temp;

    public CartAdapter(Context mctx, List<CartData> cartItemList,ConstraintLayout constraintLayout,RecyclerView recyclerView,Button button) {
        this.mctx = mctx;
        this.cartItemList = cartItemList;
        this.layoutemptycart=constraintLayout;
        this.recyclerView=recyclerView;
        this.button=button;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
        return new CartAdapter.CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartItemViewHolder holder, final int position) {

            sharedPrefManager = new SharedPrefManager(mctx);
            cartItem = cartItemList.get(position);
            holder.txtName.setText(cartItem.getName());
            holder.txtQty.setText(cartItem.getQty());
            holder.txtPrice.setText(Integer.parseInt(cartItem.getQty())*Integer.parseInt(cartItem.getPrice())+"");


        total = total + Integer.parseInt(Integer.parseInt(cartItem.getQty())*Integer.parseInt(cartItem.getPrice())+"");
        Log.d("totallll", total+"");
        sharedPrefManager.saveTotal(total);
        holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

            CartData cartItem = cartItemList.get(holder.getAdapterPosition());
            Log.d("PS", position+"");
            Log.d("", (cartItemList.size() - 1)+"");
            cartItemList.remove(cartItem);
            notifyItemRemoved(position);
            deleteTask(cartItem);

            if(cartItemList.isEmpty()){
                Log.d("CARTADAPTER", "cart Empty");
                layoutemptycart.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                button.setEnabled(false);
                button.setTextColor(mctx.getResources().getColor(R.color.grey)); //TAKE DEFAULT COLOR
            }
            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = Integer.parseInt(cartItemList.get(position).getQty());
                getPrice(cartItemList.get(position).getId());
                cartItem2 = cartItemList.get(position);

                price = Integer.parseInt(cartItemList.get(position).getPrice());

                count++;
                updateTask(cartItem2);
                holder.txtCount.setText(count+"");
                total = total + Integer.parseInt(cartItemList.get(position).getPrice());
                sharedPrefManager.saveTotal(total);
                Log.d("totainsideplus", total+"");
                holder.txtPrice.setText((count*price)+"");


            }
        });

        holder.subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = Integer.parseInt(cartItemList.get(position).getQty());
                if (count == 1) {
                    count = 1;
                    return;
                }
                getPrice(cartItemList.get(position).getId());
                cartItem2 = cartItemList.get(position);

                price = Integer.parseInt(cartItemList.get(position).getPrice());

                count--;
                updateTask(cartItem2);
                holder.txtCount.setText(count + "");
                total = total - Integer.parseInt(cartItemList.get(position).getPrice());
                sharedPrefManager.saveTotal(total);
                Log.d("totainsidesubtract", total+"");

                holder.txtPrice.setText((count*price)+"");

            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public class CartItemViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtPrice, txtQty, txtOffer;
        final TextView txtCount;
        ImageView imageView;
        Button button, add, subtract;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.cart_item_name);
            txtPrice = itemView.findViewById(R.id.cart_item_price);
            txtQty=itemView.findViewById(R.id.txt_count);
            button = itemView.findViewById(R.id.btn_delete);
            add = itemView.findViewById(R.id.add);
            subtract = itemView.findViewById(R.id.subtract);
            txtCount = itemView.findViewById(R.id.txt_count);


        }
    }

    private void deleteTask(final CartData task) {
        class DeleteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(mctx).getAppDatabase()
                        .taskDao()
                        .delete(task);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
//                Toast.makeText(mctx, "Deleted", Toast.LENGTH_LONG).show();
            }
        }

        DeleteTask dt = new DeleteTask();
        dt.execute();
    }
        private void updateTask ( final CartData task){


            class UpdateTask extends AsyncTask<Void, Void, Void> {

                @Override
                protected Void doInBackground(Void... voids) {
                    task.setQty(count + "");
                    DatabaseClient.getInstance(mctx).getAppDatabase()
                            .taskDao()
                            .update(task);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
//                Toast.makeText(mctx, "Updated", Toast.LENGTH_LONG).show();
                }
            }

            UpdateTask ut = new UpdateTask();
            ut.execute();
        }
    private void getPrice(final int id) {

        class GetPrice extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                prc = DatabaseClient.getInstance(mctx).getAppDatabase()
                        .taskDao()
                        .getPrice(id);
                return prc;
            }

            @Override
            protected void onPostExecute(String aVoid) {
                temp=Integer.parseInt(aVoid);
                super.onPostExecute(aVoid);
            }
        }

        GetPrice ut = new GetPrice();
        ut.execute();
    }


}




