// MainActivity.java
package com.example.eindproject_movie;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.eindproject_movie.database.MyAppDatabase;
import com.example.eindproject_movie.entities.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MyAppDatabase myAppDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantieer de Room-database
        myAppDatabase = Room.databaseBuilder(getApplicationContext(), MyAppDatabase.class, "my-database").build();

        // Voorbeeld van het uitvoeren van een databasebewerking op de achtergrond
        new GetAllUsersTask().execute();
    }

    private class GetAllUsersTask extends AsyncTask<Void, Void, List<User>> {

        @Override
        protected List<User> doInBackground(Void... voids) {
            return myAppDatabase.userDao().getAllUsers();
        }

        @Override
        protected void onPostExecute(List<User> users) {
            // Voorbeeld: log alle gebruikersgegevens in de logcat
            for (User user : users) {
                Log.d("User", "ID: " + user.id + ", Username: " + user.username + ", Email: " + user.email);
            }
        }
    }
}

