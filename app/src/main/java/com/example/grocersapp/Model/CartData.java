package com.example.grocersapp.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CartData {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name ="item_id")
    private String item_id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "qty")
    private String qty;

    @ColumnInfo(name = "price")
    private String price;


    /*
     * Getters and Setters
     * */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
