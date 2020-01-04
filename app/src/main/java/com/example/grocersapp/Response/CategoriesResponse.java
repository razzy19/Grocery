package com.example.grocersapp.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CategoriesResponse {
    @SerializedName("state")
    String state;

    @SerializedName("data")
    ArrayList<Categories> categories ;

    public CategoriesResponse(String state, ArrayList<Categories> categories) {
        this.state = state;
        this.categories = categories;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ArrayList<Categories> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Categories> categories) {
        this.categories = categories;
    }

    public static class Categories{
        @SerializedName("id")
        int id;

        @SerializedName("img_url")
        String img;

        @SerializedName("name")
        String name;


        public Categories(int id, String img, String name) {
            this.id = id;
            this.img = img;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }



}
