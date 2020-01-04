package com.example.grocersapp.Controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocersapp.R;
import com.example.grocersapp.Response.OrderHistoryResponse;
import com.example.grocersapp.Views.OrderHistory;
import com.example.grocersapp.Views.ProductList;

import java.util.ArrayList;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {

    ArrayList<OrderHistoryResponse.OrderHistory> orderHistoryList;
    Context context;

    public OrderHistoryAdapter(ArrayList<OrderHistoryResponse.OrderHistory> orderHistoryList, Context context) {
        this.orderHistoryList = orderHistoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_item, parent, false);
        return new OrderHistoryAdapter.OrderHistoryViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryViewHolder holder, int position) {
        final OrderHistoryResponse.OrderHistory orderHistory=orderHistoryList.get(position);

        holder.txtOrderId.setText(orderHistory.getOrderid());
        holder.txtOrderDate.setText(orderHistory.getDate());
        holder.txtOrderPaymentMode.setText(orderHistory.getPaymentmode());
        holder.txtOrderAmount.setText(orderHistory.getAmount());
        holder.txtStatus.setText(orderHistory.getOrderstatus());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, OrderHistory.class);
                intent.putExtra("order_id",orderHistory.getOrderid());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return orderHistoryList.size();
    }

    public class OrderHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView txtOrderId,txtOrderAmount,txtOrderDate,txtOrderPaymentMode,txtStatus;
        public OrderHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderId=itemView.findViewById(R.id.txt_order_id);
            txtOrderAmount=itemView.findViewById(R.id.txt_paid_amount);
            txtOrderDate=itemView.findViewById(R.id.txt_delivered_date);
            txtOrderPaymentMode=itemView.findViewById(R.id.txt_payment_mode);
            txtStatus=itemView.findViewById(R.id.txt_order_status);



        }
    }
}
