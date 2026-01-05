package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    TextView name, email, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.profileName);
        email = findViewById(R.id.profileEmail);
        username = findViewById(R.id.profileUsername);

        // Get data from intent
        String nameUser = getIntent().getStringExtra("name");
        String emailUser = getIntent().getStringExtra("email");
        String usernameUser = getIntent().getStringExtra("username");

        // Set data to TextViews
        name.setText(nameUser);
        email.setText(emailUser);
        username.setText(usernameUser);
    }
}