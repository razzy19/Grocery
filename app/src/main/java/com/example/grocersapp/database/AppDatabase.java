package com.example.grocersapp.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.grocersapp.Model.CartData;

    @Database(entities = {CartData.class}, version = 19)
    public abstract class AppDatabase extends RoomDatabase {
        public abstract CartDao taskDao();
    }

