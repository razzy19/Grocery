package com.example.grocersapp.api;


import com.example.grocersapp.Model.AddressModel;
import com.example.grocersapp.Model.CartItem;
import com.example.grocersapp.Model.CategoryModel;
import com.example.grocersapp.Model.DefaultResponse;
import com.example.grocersapp.Model.LoginResponse;
import com.example.grocersapp.Model.ProductItem;
import com.example.grocersapp.Model.SignupResponse;
import com.example.grocersapp.Model.UsersResponse;
import com.example.grocersapp.Response.AddAddressResponse;
import com.example.grocersapp.Response.AddOrderResponse;
import com.example.grocersapp.Response.AddressResponse;
import com.example.grocersapp.Response.CategoriesResponse;
import com.example.grocersapp.Response.DeleteAddressResponse;
import com.example.grocersapp.Response.GetProductResponse;
import com.example.grocersapp.Response.OrderHistoryDetailsResponse;
import com.example.grocersapp.Response.OrderHistoryResponse;
import com.example.grocersapp.Response.UpdateAddressResponse;
import com.example.grocersapp.Response.UpdateUserResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {


    @FormUrlEncoded
    @POST("users/signup/")
    Call<SignupResponse> createUser(
            @Field("address") String address,
            @Field("password") String password,
            @Field("fullname") String name,
            @Field("contact") String phone
    );

    @FormUrlEncoded
    @POST("users/login/")
    Call<LoginResponse> userLogin(
            @Field("contact") String email,
            @Field("password") String password
    );

    @GET("allusers")
    Call<UsersResponse> getUsers();

    @POST("users/get_address/")
    Call<AddressResponse> getAddress(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("users/add_address/")
    Call<AddAddressResponse> addAddress(@Header("Authorization") String token, @Field("address") String address, @Field("address_type") String address_type);

    @FormUrlEncoded
    @POST("users/delete_address/")
    Call<DeleteAddressResponse> deleteAddress(@Header("Authorization") String token,@Field("id") int id);

    @FormUrlEncoded
    @POST("orders/add_order/")
    Call<AddOrderResponse> addOrder(@Header("Authorization") String token,@Field("scheduled_delivery_time") String delivery_time,@Field("scheduled_delivery_date") String delivery_date,@Field("order_data") String order_data,@Field("delivery_address") String delivery_address,@Field("total") String total);

    @FormUrlEncoded
    @POST("users/update_address/")
    Call<UpdateAddressResponse> updateAddress(@Header("Authorization") String token,@Field("address") String address, @Field("id") int id);

    @FormUrlEncoded
    @POST("orders/order_history_details/")
    Call<OrderHistoryDetailsResponse> getOrdersHistoryDetails(@Header("Authorization") String token, @Field("id") int id);



    @POST("orders/orders_history/")
    Call<OrderHistoryResponse> getOrdersHistory(@Header("Authorization") String token);



    @POST("items/get_categories/")
    Call<CategoriesResponse> getCategories(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("items/get_items_user/")
    Call<GetProductResponse> getProducts(@Header("Authorization") String token,@Field("cat_id") int id);

    @GET("cartItems")
    Call<CartItem> getCartItems();

    @FormUrlEncoded
    @POST("items/get_items_user/")
    Call<GetProductResponse> getGrainProducts(@Header("Authorization") String token,@Field("cat_id") int id);

    @FormUrlEncoded
    @POST("items/get_items_user/")
    Call<GetProductResponse> getColdDrinksProducts(@Header("Authorization") String token,@Field("cat_id") int id);

    @GET("categories")
    Call<List<ProductItem>> getProducts();

    @FormUrlEncoded
    @PUT("updateuser/{id}")
    Call<LoginResponse> updateUser(
            @Path("id") int id,
            @Field("name") String name,
            @Field("phone") String phone
    );

    @FormUrlEncoded
    @POST("users/change_pwd/")
    Call<DefaultResponse> updatePassword(
            @Field("contact") String contact,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("users/update_users/")
    Call<UpdateUserResponse> updateUser(@Header("Authorization") String token,@Field("fullname") String name,@Field("contact") String contact);

    @GET("items/get_categories/")
    Observable<CategoriesResponse> getCategories2(@Header("Authorization") String token);
}
