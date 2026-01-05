package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        findViewById(R.id.studentButton).setOnClickListener(v -> {
            //startActivity(new Intent(this, SignupActivity.class));
            startActivity(new Intent(this, SignupActivity.class));
        });

        findViewById(R.id.adminButton).setOnClickListener(v -> {
            //startActivity(new Intent(this, AdminSignupActivity.class));
            startActivity(new Intent(this, AdminSignupActivity.class));
        });

    }
}
