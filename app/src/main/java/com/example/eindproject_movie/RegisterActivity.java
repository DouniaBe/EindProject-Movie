package com.example.eindproject_movie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private EditText userEdt, emailEdt, passEdt, confirmPassEdt;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
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
                // Voer hier je registratielogica in
                // Bijvoorbeeld: sla de gebruikersnaam, e-mail en wachtwoord op in een database of een ander opslagmechanisme
                Toast.makeText(RegisterActivity.this, "Registratie succesvol", Toast.LENGTH_SHORT).show();

                // Na registratie kun je de gebruiker doorsturen naar de inlogactiviteit of een andere activiteit
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }
}