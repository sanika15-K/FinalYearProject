//package com.example.finalyearproject;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//public class SignupActivity extends AppCompatActivity {
//
//    EditText signupName, signupEmail, signupUsername, signupPassword;
//    Button signupButton;
//    TextView loginRedirectText;
//
//    DatabaseReference reference;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_signup);
//
//        signupName = findViewById(R.id.signup_name);
//        signupEmail = findViewById(R.id.signup_email);
//        signupUsername = findViewById(R.id.signup_username);
//        signupPassword = findViewById(R.id.signup_password);
//        signupButton = findViewById(R.id.signup_button);
//        loginRedirectText = findViewById(R.id.loginRedirectText);
//
//        reference = FirebaseDatabase.getInstance().getReference("users");
//
//        loginRedirectText.setOnClickListener(v ->
//                startActivity(new Intent(
//                        SignupActivity.this,
//                        StudentLoginActivity.class)));
//
//        signupButton.setOnClickListener(v -> {
//
//            String name = signupName.getText().toString().trim();
//            String email = signupEmail.getText().toString().trim();
//            String username = signupUsername.getText().toString().trim();
//            String password = signupPassword.getText().toString().trim();
//
//            if (name.isEmpty() || email.isEmpty()
//                    || username.isEmpty() || password.isEmpty()) {
//                Toast.makeText(this,
//                        "Fill all fields",
//                        Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            // 🔐 EMAIL DOMAIN CHECK
//            if (!email.endsWith("@cumminscollege.in")) {
//                signupEmail.setError(
//                        "Use college email (@cumminscollege.in)");
//                return;
//            }
//
//            String hashedPassword = PasswordUtil.hashPassword(password);
//
//            HelperClass helperClass =
//                    new HelperClass(name, email, username, hashedPassword);
//
//            reference.child(username)
//                    .addListenerForSingleValueEvent(
//                            new ValueEventListener() {
//                                @Override
//                                public void onDataChange(
//                                        @NonNull DataSnapshot snapshot) {
//
//                                    if (snapshot.exists()) {
//                                        signupUsername.setError(
//                                                "Username already exists");
//                                    } else {
//                                        reference.child(username)
//                                                .setValue(helperClass);
//
//                                        Toast.makeText(
//                                                SignupActivity.this,
//                                                "Signup successful! Please login",
//                                                Toast.LENGTH_SHORT).show();
//
//                                        startActivity(new Intent(
//                                                SignupActivity.this,
//                                                StudentLoginActivity.class));
//                                        finish();
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(
//                                        @NonNull DatabaseError error) {}
//                            });
//        });
//    }
//}


package com.example.finalyearproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.*;

public class SignupActivity extends AppCompatActivity {

    EditText signupName, signupEmail, signupUsername,
            signupPassword, signupUEC, signupBranch;
    Button signupButton;
    TextView loginRedirectText;

    DatabaseReference allowedRef, usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        signupUsername = findViewById(R.id.signup_username);
        signupPassword = findViewById(R.id.signup_password);
        signupUEC = findViewById(R.id.signup_uec);
        signupBranch = findViewById(R.id.signup_branch);

        signupButton = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        allowedRef = FirebaseDatabase.getInstance().getReference("allowed_users");
        usersRef = FirebaseDatabase.getInstance().getReference("users");

        loginRedirectText.setOnClickListener(v ->
                startActivity(new Intent(
                        SignupActivity.this,
                        StudentLoginActivity.class)));

        signupButton.setOnClickListener(v -> registerUser());
    }

    private boolean isValidPassword(String password) {

        if (password.length() < 8)
            return false;

        boolean hasUppercase = false;
        boolean hasSymbol = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSymbol = true;
            }
        }

        return hasUppercase && hasSymbol;
    }


    private void registerUser() {

        String name = signupName.getText().toString().trim();
        String email = signupEmail.getText().toString().trim();
        String username = signupUsername.getText().toString().trim();
        String password = signupPassword.getText().toString().trim();
        String uec = signupUEC.getText().toString().trim();
        String branch = signupBranch.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || username.isEmpty()
                || password.isEmpty() || uec.isEmpty() || branch.isEmpty()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!email.endsWith("@cumminscollege.in")) {
            signupEmail.setError("Use college email");
            return;
        }

        // 🔍 CHECK FROM allowed_users
        allowedRef.child(uec)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (!snapshot.exists()) {
                            Toast.makeText(SignupActivity.this,
                                    "You are not an allowed user",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String dbEmail = snapshot.child("email")
                                .getValue(String.class);
                        String dbBranch = snapshot.child("branch")
                                .getValue(String.class);

                        if (!email.equals(dbEmail)) {
                            Toast.makeText(SignupActivity.this,
                                    "Email does not match records",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (!branch.equalsIgnoreCase(dbBranch)) {
                            Toast.makeText(SignupActivity.this,
                                    "Branch does not match records",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // ✅ VERIFIED → CHECK USERNAME
                        usersRef.child(username)
                                .addListenerForSingleValueEvent(
                                        new ValueEventListener() {
                                            @Override
                                            public void onDataChange(
                                                    @NonNull DataSnapshot snap) {

                                                if (snap.exists()) {
                                                    signupUsername.setError(
                                                            "Username already exists");
                                                    return;
                                                }


                                                if (!isValidPassword(password)) {
                                                    signupPassword.setError(
                                                            "Password must be 8+ chars, 1 capital & 1 symbol");
                                                    return;
                                                }
                                                String hashedPassword =
                                                        PasswordUtil.hashPassword(password);


                                                HelperClass user =
                                                        new HelperClass(
                                                                name,
                                                                email,
                                                                username,
                                                                hashedPassword,
                                                                uec,
                                                                branch
                                                        );

                                                usersRef.child(username)
                                                        .setValue(user);

                                                Toast.makeText(
                                                        SignupActivity.this,
                                                        "Signup successful",
                                                        Toast.LENGTH_SHORT).show();

                                                startActivity(new Intent(
                                                        SignupActivity.this,
                                                        StudentLoginActivity.class));
                                                finish();
                                            }

                                            @Override
                                            public void onCancelled(
                                                    @NonNull DatabaseError error) {}
                                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
    }
}
