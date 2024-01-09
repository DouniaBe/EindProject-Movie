// database/MyAppDatabase.java
package com.example.eindproject_movie.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.eindproject_movie.daos.UserDao;
import com.example.eindproject_movie.entities.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class MyAppDatabase extends RoomDatabase {

    public abstract UserDao userDao(); // UserDao is je interface met de database-operaties
}
