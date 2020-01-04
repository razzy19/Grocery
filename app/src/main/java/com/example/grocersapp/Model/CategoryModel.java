package com.example.grocersapp.Model;

public class CategoryModel {
    private String categoryimage;
    private String categoryname;
    private long catID;


    public CategoryModel(String categoryimage, String categoryname, long catID) {
        this.categoryimage = categoryimage;
        this.categoryname = categoryname;
        this.catID = catID;
    }

    public String getCategoryimage() {
        return categoryimage;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public long getCatID() {
        return catID;
    }

    public void setCategoryimage(String categoryimage) {
        this.categoryimage = categoryimage;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public void setCatID(long catID) {
        this.catID = catID;
    }
}
