// RegisterActivity.java
package com.example.eindproject_movie;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.eindproject_movie.database.MyAppDatabase;
import com.example.eindproject_movie.entities.User;

public class RegisterActivity extends AppCompatActivity {
    private EditText userEdt, emailEdt, passEdt, confirmPassEdt;
    private Button registerBtn;
    private MyAppDatabase myAppDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

        myAppDatabase = Room.databaseBuilder(getApplicationContext(), MyAppDatabase.class, "my-database").build();
    }

    private void initView() {
        userEdt = findViewById(R.id.editTextText);
        emailEdt = findViewById(R.id.editTextTextEmailAddress);
        passEdt = findViewById(R.id.editTextTextPassword);
        confirmPassEdt = findViewById(R.id.editTextTextConfirmPassword);
        registerBtn = findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(v -> {
            String username = userEdt.getText().toString().trim();
            String email = emailEdt.getText().toString().trim();
            String password = passEdt.getText().toString().trim();
            String confirmPassword = confirmPassEdt.getText().toString().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Vul alle velden in", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(RegisterActivity.this, "Wachtwoorden komen niet overeen", Toast.LENGTH_SHORT).show();
            } else {
                new CheckUserExistsTask().execute(username, email, password);
            }
        });
    }

    private class CheckUserExistsTask extends AsyncTask<String, Void, User> {

        private String[] params; // Voeg deze lijn toe om de params bij te houden

        @Override
        protected User doInBackground(String... params) {
            this.params = params; // Sla de params op in de klassevariabele
            return myAppDatabase.userDao().getUserByUsernameOrEmail(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(User existingUser) {
            if (existingUser == null) {
                // Geen bestaande gebruiker gevonden, voeg de nieuwe gebruiker toe
                new RegisterTask().execute(params); // Gebruik de opgeslagen params
            } else {
                // Bestaande gebruiker gevonden met dezelfde gebruikersnaam of e-mail
                Toast.makeText(RegisterActivity.this, "Een gebruiker met dezelfde gebruikersnaam of e-mail bestaat al", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class RegisterTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            User newUser = new User();
            newUser.username = params[0];
            newUser.email = params[1];
            newUser.password = params[2];

            myAppDatabase.userDao().insertUser(newUser);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(RegisterActivity.this, "Registratie succesvol", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        }
    }
}

