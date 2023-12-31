// daos/UserDao.java
package com.example.eindproject_movie.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.eindproject_movie.entities.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM User WHERE email = :email")
    User getUserByEmail(String email);

    @Query("SELECT * FROM User")
    List<User> getAllUsers();

    @Query("SELECT * FROM User WHERE username = :username AND password = :password")
    User getUserByUsernameAndPassword(String username, String password);
    @Query("SELECT * FROM User WHERE username = :username OR email = :email LIMIT 1")
    User getUserByUsernameOrEmail(String username, String email);
}
