package com.example.grocersapp.Controller;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Delete;

import com.example.grocersapp.Model.AddressModel;
import com.example.grocersapp.Model.CartData;
import com.example.grocersapp.R;
import com.example.grocersapp.Response.AddressResponse;
import com.example.grocersapp.Views.OrderDetails;
import com.example.grocersapp.interfaces.OnOrderDetailRecyclerViewItemClickListener;
import com.example.grocersapp.storage.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class AddressAdapter extends  RecyclerView.Adapter<AddressAdapter.AddressViewMHolder> {
    private ArrayList<AddressResponse.Addresses> addressModelList;
    private Context context;
    private Dialog dialog;
    SharedPrefManager sharedPrefManager;
    public AddressAdapter(ArrayList<AddressResponse.Addresses> addressModelList, Context context, Dialog dialog) {
        this.addressModelList = addressModelList;
        this.context=context;
        this.dialog=dialog;

    }

    @NonNull
    @Override
    public AddressAdapter.AddressViewMHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item, parent, false);
        return new AddressAdapter.AddressViewMHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AddressAdapter.AddressViewMHolder holder, int position) {

        final AddressResponse.Addresses addressModel= addressModelList.get(position);

        holder.txtAddress.setText(addressModel.getAddress());
        holder.txtaddresstype.setText(addressModel.getAddressType());
        sharedPrefManager =new SharedPrefManager(dialog.getContext());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("address1",holder.txtAddress.getText().toString()+"");
                sharedPrefManager.saveAddress(holder.txtAddress.getText().toString());
//                textView.setText(addressModelList.get(holder.getAdapterPosition()).getAddress()+"");
                dialog.dismiss();
            }
        });

    }

    @Override
    public int getItemCount() {
        return addressModelList.size();
    }

    public class AddressViewMHolder extends RecyclerView.ViewHolder {
        TextView txtAddress,txtaddresstype;
        public AddressViewMHolder(@NonNull View itemView) {
            super(itemView);
            txtAddress= itemView.findViewById(R.id.txt_address);
            txtaddresstype=itemView.findViewById(R.id.txt_address_type);

        }
    }
}
