package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLoginActivity extends AppCompatActivity {

    EditText adminUsername, adminPassword;
    Button adminLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        adminUsername = findViewById(R.id.admin_username);
        adminPassword = findViewById(R.id.admin_password);
        adminLoginBtn = findViewById(R.id.admin_login_button);

        adminLoginBtn.setOnClickListener(v -> loginAdmin());
    }

    private void loginAdmin() {

        String username = adminUsername.getText().toString().trim().toLowerCase();
        String password = adminPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference ref =
                FirebaseDatabase.getInstance().getReference("admins").child(username);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!snapshot.exists()) {
                    adminUsername.setError("Admin not found");
                    return;
                }

                String dbHashedPassword =
                        snapshot.child("password").getValue(String.class);

                // ✅ HASH INPUT PASSWORD BEFORE COMPARISON
                String inputHashed =
                        PasswordUtil.hashPassword(password);

                if (inputHashed.equals(dbHashedPassword)) {
                    startActivity(new Intent(
                            AdminLoginActivity.this,
                            AdminDashboardActivity.class
                    ));
                    finish();
                } else {
                    adminPassword.setError("Invalid password");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminLoginActivity.this,
                        "Login failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
