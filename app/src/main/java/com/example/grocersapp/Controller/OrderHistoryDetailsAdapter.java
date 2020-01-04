package com.example.grocersapp.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocersapp.R;
import com.example.grocersapp.Response.OrderHistoryDetailsResponse;
import com.example.grocersapp.Response.OrderHistoryResponse;

import java.util.ArrayList;

public class OrderHistoryDetailsAdapter extends RecyclerView.Adapter<OrderHistoryDetailsAdapter.OrderHistoryViewHolder> {

    ArrayList<OrderHistoryDetailsResponse.OrderHistory.orders> orders;
    Context context;

    public OrderHistoryDetailsAdapter(ArrayList<OrderHistoryDetailsResponse.OrderHistory.orders> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderHistoryDetailsAdapter.OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderhistory_product_item, parent, false);
        return new OrderHistoryDetailsAdapter.OrderHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryDetailsAdapter.OrderHistoryViewHolder holder, int position) {

        final OrderHistoryDetailsResponse.OrderHistory.orders orderHistory=orders.get(position);

        holder.txtPrice.setText(orderHistory.getPrice());
        holder.txtName.setText(orderHistory.getName());
        holder.txtQty.setText(orderHistory.getQty());

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }


    public class OrderHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView txtName,txtQty,txtPrice;
        public OrderHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.txt_prod_name);
            txtQty=itemView.findViewById(R.id.txt_prod_qty);
            txtPrice=itemView.findViewById(R.id.txt_prod_unitprice);
        }
    }
}
