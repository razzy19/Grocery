package com.example.grocersapp.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetProductResponse {
    @SerializedName("state")
    String state;

    @SerializedName("data")
    ArrayList<GetProducts> getProducts;

    public GetProductResponse(String state, ArrayList<GetProducts> getProducts) {
        this.state = state;
        this.getProducts = getProducts;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ArrayList<GetProducts> getGetProducts() {
        return getProducts;
    }

    public void setGetProducts(ArrayList<GetProducts> getProducts) {
        this.getProducts = getProducts;
    }

    public static class GetProducts{

        @SerializedName("id")
        int item_id;

        @SerializedName("name")
        String name;

        @SerializedName("rate")
        int price;

        @SerializedName("img_url")
        String imgurl;

        public GetProducts(String name, int price,int item_id) {
            this.name = name;
            this.price = price;
            this.item_id=item_id;
        }

        public int getItem_id() {
            return item_id;
        }

        public void setItem_id(int item_id) {
            this.item_id = item_id;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }

}
