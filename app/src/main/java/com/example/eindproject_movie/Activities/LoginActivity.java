// LoginActivity.java
package com.example.eindproject_movie.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.eindproject_movie.R;
import com.example.eindproject_movie.database.MyAppDatabase;
import com.example.eindproject_movie.entities.User;

public class LoginActivity extends AppCompatActivity {
    private EditText userEdt, passEdt;
    private Button loginBtn;
    private TextView linkRegister;
    private MyAppDatabase myAppDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        myAppDatabase = Room.databaseBuilder(getApplicationContext(), MyAppDatabase.class, "my-database").build();

        linkRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void initView() {
        userEdt = findViewById(R.id.editTextText);
        passEdt = findViewById(R.id.editTextPassword);
        loginBtn = findViewById(R.id.loginBtn);
        linkRegister = findViewById(R.id.linkRegister);

        loginBtn.setOnClickListener(v -> {
            String username = userEdt.getText().toString().trim();
            String password = passEdt.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Ontbrekende gegevens", Toast.LENGTH_SHORT).show();
            } else {
                // Voer inlogtaak uit
                new LoginTask(username, password).execute();
            }
        });
    }

    private class LoginTask extends AsyncTask<Void, Void, User> {

        private String username;
        private String password;

        LoginTask(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected User doInBackground(Void... voids) {
            return myAppDatabase.userDao().getUserByUsernameAndPassword(username, password);
        }

        @Override
        protected void onPostExecute(User user) {
            if (user != null) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            } else {
                Toast.makeText(LoginActivity.this, "Jouw gebruikersnaam of wachtwoord zijn niet correct", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
