package com.example.grocersapp.database;




import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.grocersapp.Model.CartData;

import java.util.List;

@Dao
    public interface CartDao {
        @Query("SELECT * FROM cartdata")
        List<CartData> getAll();

        @Query("SELECT price FROM cartdata where id =:id")
        String getPrice(int id);

        @Insert
        void insert(CartData cartData);

        @Delete
        void delete(CartData cartData);

        @Query("delete from cartdata")
        void deleteAll();

        @Update
        void update(CartData cartData);

        @Query("SELECT * from cartdata where name =:name")
        List<CartData> checkDuplicate(String name);


    }

