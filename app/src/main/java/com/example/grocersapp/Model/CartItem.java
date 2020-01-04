package com.example.grocersapp.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import java.io.Serializable;

@Entity
public class CartItem implements Serializable {

    @ColumnInfo(name = "name")
    private String name;

//    @ColumnInfo(name = "qty")
//    private String qty;

    @ColumnInfo(name = "price")
    private String price;

//    String img,qty,offer;

    public CartItem(String img, String name, String qty, String price, String offer) {
//        this.img = img;
        this.name = name;
//        this.qty = qty;
        this.price = price;
//        this.offer = offer;
    }

//    public String getImg() {
//        return img;
//    }

    public String getName() {
        return name;
    }

//    public String getQty() {
//        return qty;
//    }

    public String getPrice() {
        return price;
    }

//    public String getOffer() {
//        return offer;
//    }

//    public void setImg(String img) {
//        this.img = img;
//    }

    public void setName(String name) {
        this.name = name;
    }

//    public void setQty(String qty) {
//        this.qty = qty;
//    }

    public void setPrice(String price) {
        this.price = price;
    }

//    public void setOffer(String offer) {
//        this.offer = offer;
//    }
}
