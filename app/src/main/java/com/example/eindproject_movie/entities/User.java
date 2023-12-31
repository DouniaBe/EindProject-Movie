// entities/User.java
package com.example.eindproject_movie.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String username;

    public String password;

    public String confirmPassword; // Dit veld is voor het herhaalwachtwoord

    public String email;
}
