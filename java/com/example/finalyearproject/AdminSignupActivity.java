//package com.example.finalyearproject;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.InputType;
//import android.view.MotionEvent;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class AdminSignupActivity extends AppCompatActivity {
//
//    EditText emailET, usernameET, passwordET;
//    Button signupBtn;
//    TextView loginRedirectText;
//
//    private boolean isPasswordVisible = false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_admin_signup);
//
//        emailET = findViewById(R.id.admin_email);
//        usernameET = findViewById(R.id.admin_username);
//        passwordET = findViewById(R.id.admin_password);
//        signupBtn = findViewById(R.id.admin_signup_btn);
//        loginRedirectText = findViewById(R.id.loginRedirectText);
//
//        enablePasswordToggle(passwordET);
//
//        signupBtn.setOnClickListener(v -> adminSignup());
//
//        // ✅ Already user → go to Login
//        loginRedirectText.setOnClickListener(v -> {
//            startActivity(new Intent(this, AdminLoginActivity.class));
//        });
//    }
//
//    // ===================== ADMIN SIGNUP =====================
//    private void adminSignup() {
//
//        String email = emailET.getText().toString().trim().toLowerCase();
//        String username = usernameET.getText().toString().trim().toLowerCase();
//        String password = passwordET.getText().toString().trim();
//
//        if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
//            toast("Please fill all details");
//            return;
//        }
//
//        if (!email.endsWith("@cumminscollege.in")) {
//           emailET.setError(
//                    "Only college email allowed");
//            return;
//        }
//
//        if (!isValidPassword(password)) {
//            toast("Password must be 8+ chars with Upper, Lower, Digit & Special");
//            return;
//        }
//
//        DatabaseReference adminsRef =
//                FirebaseDatabase.getInstance().getReference("admins");
//
//        adminsRef.child(username)
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snap) {
//
////                        if (snap.exists()) {
////                            toast("Admin already exists. Please login.");
////                            return;
////                        }
//                        toast("Admin created successfully. Please login.");
//
//                        Intent intent = new Intent(AdminSignupActivity.this, AdminLoginActivity.class);
//                        startActivity(intent);
//                        finish();
//
//
//
//
//                        String hashedPassword =
//                                PasswordUtil.hashPassword(password);
//
//                        Map<String, Object> adminMap = new HashMap<>();
//                        adminMap.put("email", email);
//                        adminMap.put("username", username);
//                        adminMap.put("password", hashedPassword);
//
//                        adminsRef.child(username).setValue(adminMap);
//
//                        toast("Admin created successfully. Please login.");
//                        finish();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        toast("Signup failed. Try again.");
//                    }
//                });
//    }
//
//    // ===================== PASSWORD VALIDATION =====================
//    private boolean isValidPassword(String password) {
//
//        if (password.length() < 8) return false;
//
//        boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false;
//
//        for (char c : password.toCharArray()) {
//            if (Character.isUpperCase(c)) hasUpper = true;
//            else if (Character.isLowerCase(c)) hasLower = true;
//            else if (Character.isDigit(c)) hasDigit = true;
//            else hasSpecial = true;
//        }
//        return hasUpper && hasLower && hasDigit && hasSpecial;
//    }
//
//    // ===================== PASSWORD EYE TOGGLE =====================
//    private void enablePasswordToggle(EditText passwordET) {
//
//        passwordET.setCompoundDrawablesWithIntrinsicBounds(
//                0, 0, R.drawable.ic_eye, 0);
//
//        passwordET.setOnTouchListener((v, event) -> {
//
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//
//                if (passwordET.getCompoundDrawables()[2] == null) return false;
//
//                int drawableWidth =
//                        passwordET.getCompoundDrawables()[2].getIntrinsicWidth();
//
//                if (event.getRawX() >= (passwordET.getRight() - drawableWidth)) {
//
//                    if (isPasswordVisible) {
//                        passwordET.setInputType(
//                                InputType.TYPE_CLASS_TEXT |
//                                        InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                        passwordET.setCompoundDrawablesWithIntrinsicBounds(
//                                0, 0, R.drawable.ic_eye, 0);
//                    } else {
//                        passwordET.setInputType(
//                                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//                        passwordET.setCompoundDrawablesWithIntrinsicBounds(
//                                0, 0, R.drawable.ic_eye_off, 0);
//                    }
//
//                    passwordET.setSelection(passwordET.length());
//                    isPasswordVisible = !isPasswordVisible;
//                    return true;
//                }
//            }
//            return false;
//        });
//    }
//
//    private void toast(String msg) {
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
//    }
//}
//
//
////package com.example.finalyearproject;
////
////import android.content.Intent;
////import android.os.Bundle;
////import android.widget.Button;
////import android.widget.EditText;
////import android.widget.Toast;
////
////import androidx.appcompat.app.AppCompatActivity;
////
////import com.google.firebase.database.DatabaseReference;
////import com.google.firebase.database.FirebaseDatabase;
////
////public class AdminSignupActivity extends AppCompatActivity {
////
////    EditText adminName, adminEmail, adminUsername, adminPassword;
////    Button adminSignupBtn;
////
////    DatabaseReference reference;
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_admin_signup);
////
////
////        adminEmail = findViewById(R.id.admin_email);
////        adminUsername = findViewById(R.id.admin_username);
////        adminPassword = findViewById(R.id.admin_password);
////        adminSignupBtn = findViewById(R.id.admin_signup_btn);
////
////        reference = FirebaseDatabase.getInstance().getReference("admins");
////
////        adminSignupBtn.setOnClickListener(v -> {
////
////            String name = adminName.getText().toString().trim();
////            String email = adminEmail.getText().toString().trim();
////            String username = adminUsername.getText().toString().trim();
////            String password = adminPassword.getText().toString().trim();
////
////            if (name.isEmpty() || email.isEmpty()
////                    || username.isEmpty() || password.isEmpty()) {
////                Toast.makeText(this,
////                        "Fill all fields",
////                        Toast.LENGTH_SHORT).show();
////                return;
////            }
////
////            // 🔐 EMAIL DOMAIN CHECK
////            if (!email.endsWith("@cumminscollege.in")) {
////                adminEmail.setError(
////                        "Only college email allowed");
////                return;
////            }
////
////            String hashedPassword = PasswordUtil.hashPassword(password);
////
////            HelperClass admin =
////                    new HelperClass(
////                            name, email, username, hashedPassword);
////
////            reference.child(username).setValue(admin);
////
////            Toast.makeText(this,
////                    "Admin signup successful",
////                    Toast.LENGTH_SHORT).show();
////
////            startActivity(new Intent(
////                    AdminSignupActivity.this,
////                    AdminLoginActivity.class));
////            finish();
////        });
////    }
////}



package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.widget.*;

import com.google.firebase.database.*;

import java.util.HashMap;
import java.util.Map;

public class AdminSignupActivity extends AppCompatActivity {

    EditText emailET, usernameET, passwordET;
    Button signupBtn;
    TextView loginRedirectText;

    private boolean isPasswordVisible = false;

    DatabaseReference allowedAdminsRef, adminsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signup);

        emailET = findViewById(R.id.admin_email);
        usernameET = findViewById(R.id.admin_username);
        passwordET = findViewById(R.id.admin_password);
        signupBtn = findViewById(R.id.admin_signup_btn);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        enablePasswordToggle(passwordET);

        allowedAdminsRef =
                FirebaseDatabase.getInstance().getReference("allowed_admins");

        adminsRef =
                FirebaseDatabase.getInstance().getReference("admins");

        signupBtn.setOnClickListener(v -> adminSignup());

        loginRedirectText.setOnClickListener(v ->
                startActivity(new Intent(this, AdminLoginActivity.class)));
    }

    // ===================== ADMIN SIGNUP =====================
    private void adminSignup() {

        String email = emailET.getText().toString().trim().toLowerCase();
        String username = usernameET.getText().toString().trim().toLowerCase();
        String password = passwordET.getText().toString().trim();

        if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            toast("Please fill all details");
            return;
        }

        if (!email.endsWith("@cumminscollege.in")) {
            emailET.setError("Only college email allowed");
            return;
        }

        if (!isValidPassword(password)) {
            toast("Password must be 8+ chars with Upper, Lower, Digit & Special");
            return;
        }

        // 🔍 CHECK FROM allowed_admins
        allowedAdminsRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        boolean isAllowed = false;
                        String role = "";

                        for (DataSnapshot adminSnap : snapshot.getChildren()) {

                            String dbEmail =
                                    adminSnap.child("email")
                                            .getValue(String.class);

                            if (email.equalsIgnoreCase(dbEmail)) {
                                isAllowed = true;
                                role = adminSnap.child("role")
                                        .getValue(String.class);
                                break;
                            }
                        }

                        if (!isAllowed) {
                            toast("You are not an authorized admin");
                            return;
                        }

                        // 🔒 CHECK USERNAME EXISTS
                        String finalRole = role;
                        adminsRef.child(username)
                                .addListenerForSingleValueEvent(
                                        new ValueEventListener() {
                                            @Override
                                            public void onDataChange(
                                                    @NonNull DataSnapshot snap) {

                                                if (snap.exists()) {
                                                    toast("Admin already exists");
                                                    return;
                                                }

                                                String hashedPassword =
                                                        PasswordUtil.hashPassword(password);

                                                Map<String, Object> adminMap =
                                                        new HashMap<>();

                                                adminMap.put("email", email);
                                                adminMap.put("username", username);
                                                adminMap.put("password", hashedPassword);
                                                adminMap.put("role", finalRole);

                                                adminsRef.child(username)
                                                        .setValue(adminMap);

                                                toast("Admin created successfully");

                                                startActivity(new Intent(
                                                        AdminSignupActivity.this,
                                                        AdminLoginActivity.class));
                                                finish();
                                            }

                                            @Override
                                            public void onCancelled(
                                                    @NonNull DatabaseError error) {
                                                toast("Signup failed");
                                            }
                                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        toast("Signup failed");
                    }
                });
    }

    // ===================== PASSWORD VALIDATION =====================
    private boolean isValidPassword(String password) {

        if (password.length() < 8) return false;

        boolean hasUpper = false, hasLower = false,
                hasDigit = false, hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else hasSpecial = true;
        }
        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

    // ===================== PASSWORD EYE TOGGLE =====================
    private void enablePasswordToggle(EditText passwordET) {

        passwordET.setCompoundDrawablesWithIntrinsicBounds(
                0, 0, R.drawable.ic_eye, 0);

        passwordET.setOnTouchListener((v, event) -> {

            if (event.getAction() == MotionEvent.ACTION_UP) {

                int drawableWidth =
                        passwordET.getCompoundDrawables()[2].getIntrinsicWidth();

                if (event.getRawX() >=
                        (passwordET.getRight() - drawableWidth)) {

                    if (isPasswordVisible) {
                        passwordET.setInputType(
                                InputType.TYPE_CLASS_TEXT |
                                        InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        passwordET.setCompoundDrawablesWithIntrinsicBounds(
                                0, 0, R.drawable.ic_eye, 0);
                    } else {
                        passwordET.setInputType(
                                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        passwordET.setCompoundDrawablesWithIntrinsicBounds(
                                0, 0, R.drawable.ic_eye_off, 0);
                    }

                    passwordET.setSelection(passwordET.length());
                    isPasswordVisible = !isPasswordVisible;
                    return true;
                }
            }
            return false;
        });
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
