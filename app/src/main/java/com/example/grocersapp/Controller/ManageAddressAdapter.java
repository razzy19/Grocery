package com.example.grocersapp.Controller;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocersapp.Model.AddressModel;
import com.example.grocersapp.R;
import com.example.grocersapp.Response.AddressResponse;
import com.example.grocersapp.Response.DeleteAddressResponse;
import com.example.grocersapp.Response.UpdateAddressResponse;
import com.example.grocersapp.Views.ManageAddress;
import com.example.grocersapp.api.Api;
import com.example.grocersapp.api.RetrofitClient;
import com.example.grocersapp.storage.SharedPrefManager;
import com.example.grocersapp.viewmodels.DeleteAddressViewModel;
import com.example.grocersapp.viewmodels.UpdateAddressViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ManageAddressAdapter extends RecyclerView.Adapter<ManageAddressAdapter.AddressViewHolder>  {

    private ArrayList<AddressResponse.Addresses> addressModelList;
    private Context mctx;
    int address_id,address2;
    String decryptedToken;
    SharedPrefManager  sharedPrefManager;
    ManageAddressAdapter.AddressViewHolder holder2;
    private UpdateAddressViewModel updateAddressViewModel;
    private Dialog dialog;
    private DeleteAddressViewModel deleteAddressViewModel;
    private ManageAddress manageAddress;
    public ManageAddressAdapter(ArrayList<AddressResponse.Addresses> addressModelList, Context context) {
        this.addressModelList = addressModelList;
        this.mctx=context;
    }

    @NonNull
    @Override
    public ManageAddressAdapter.AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item2, parent, false);
        return new ManageAddressAdapter.AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ManageAddressAdapter.AddressViewHolder holder, final int position) {

        final AddressResponse.Addresses addressResponse= addressModelList.get(position);

        holder.txtAddresstype.setText(addressResponse.getAddressType());
        holder.txtAddress.setText(addressResponse.getAddress());

        Log.d("address_id", address_id+"");
        address_id=addressResponse.getId();
        sharedPrefManager=new SharedPrefManager(mctx);
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView txtAddressUpdate;
                Button btnUpdate;
                Log.d("Edit", "onClick: ");
                dialog = new Dialog(mctx); // Context, this, etc.
                dialog.setContentView(R.layout.layout_edit_address);
                dialog.show();
                address_id=addressResponse.getId();

                Log.d("IDADDRESS", address_id+"");
                txtAddressUpdate=dialog.findViewById(R.id.edit_address);
                btnUpdate=dialog.findViewById(R.id.btn_update);
                txtAddressUpdate.setText(holder.txtAddress.getText());
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder2 = holder;
                        updateAddress(address_id,txtAddressUpdate.getText().toString());
                        Log.d("position", holder.getAdapterPosition()+"");



                    }
                });


            }
        });

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("remove", "onClick: "+address_id);
                deleteAddress(address_id);
                addressModelList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());

            }
        });

    }

    @Override
    public int getItemCount() {
        return addressModelList.size();
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder {
        TextView txtAddress,txtAddresstype;
        ImageView btnEdit,btnRemove;
        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAddress= itemView.findViewById(R.id.txtAddress);
            txtAddresstype= itemView.findViewById(R.id.txt_address_type);

            btnEdit=itemView.findViewById(R.id.img_edit);
            btnRemove=itemView.findViewById(R.id.img_remove);
        }
    }


    public void updateAddress(int id,String address){
        Retrofit retrofitClient = new RetrofitClient().getRetrofit();
        final Api api = retrofitClient.create(Api.class);
        decryptedToken = sharedPrefManager.getToken();
        Call<UpdateAddressResponse> call = api.updateAddress(decryptedToken,address,id);

        call.enqueue(new Callback<UpdateAddressResponse>() {
            @Override
            public void onResponse(Call<UpdateAddressResponse> call, Response<UpdateAddressResponse> response) {
                UpdateAddressResponse updateAddressResponse = response.body();
                Log.d("inupdate", "onResponse: ");
                if (updateAddressResponse.getState().equals(" success")) {
//                  progressBar.setVisibility(View.GONE);

                    notifyItemChanged(holder2.getAdapterPosition());
                    ((ManageAddress)mctx).loadAddress();
                    dialog.dismiss();

                    Toast.makeText(mctx, "Address Updated Successfully", Toast.LENGTH_LONG).show();

                } else {
//                progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<UpdateAddressResponse> call, Throwable t) {

                Toast.makeText(mctx, t.getMessage(), Toast.LENGTH_SHORT).show();

            }

        });

//            updateAddressViewModel = ViewModelProviders.of((FragmentActivity) mctx).get(UpdateAddressViewModel.class);
//            updateAddressViewModel.init(decryptedToken,address,id);
//            updateAddressViewModel.updateAddress().observe((FragmentActivity)mctx,updateAddressResponse -> {
//                if(updateAddressResponse.getState().equals(" success")){
//
//                    Toast.makeText(mctx, "Address Updated Successfully", Toast.LENGTH_LONG).show();
//                }
//                else{
//                    Toast.makeText(mctx, "Cannot update address !", Toast.LENGTH_LONG).show();
//                }
//            });

    }

    public void deleteAddress(int id){
        Retrofit retrofitClient = new RetrofitClient().getRetrofit();
        final Api api = retrofitClient.create(Api.class);
        decryptedToken = sharedPrefManager.getToken();
        Call<DeleteAddressResponse> call = api.deleteAddress(decryptedToken,id);

        call.enqueue(new Callback<DeleteAddressResponse>() {
            @Override
            public void onResponse(Call<DeleteAddressResponse> call, Response<DeleteAddressResponse> response) {
                DeleteAddressResponse deleteAddressResponse = response.body();
                if (deleteAddressResponse.getState().equals("success")) {
//                   progressBar.setVisibility(View.GONE);
                    Toast.makeText(mctx, deleteAddressResponse.getMsg(), Toast.LENGTH_SHORT).show();


                } else {
//                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<DeleteAddressResponse> call, Throwable t) {

                Toast.makeText(mctx, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

//        deleteAddressViewModel = ViewModelProviders.of((FragmentActivity)mctx).get(DeleteAddressViewModel.class);
//        deleteAddressViewModel.init(decryptedToken,id);
//        deleteAddressViewModel.deleteAddress().observe((FragmentActivity)mctx,deleteAddressResponse -> {
//            if(deleteAddressResponse.getState().equals("success")){
//                Toast.makeText(mctx, deleteAddressResponse.getMsg(), Toast.LENGTH_SHORT).show();
//            }
//            else{
//                Toast.makeText(mctx, deleteAddressResponse.getMsg(), Toast.LENGTH_SHORT).show();
//            }
//        });

    }


}
