package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import org.checkerframework.checker.nullness.qual.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.messaging.FirebaseMessaging;


public class StudentDashboardActivity extends AppCompatActivity {

    LinearLayout btnDrives, btnQuery, btnNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        FirebaseMessaging.getInstance()
                .subscribeToTopic("college_updates");


        btnDrives = findViewById(R.id.btnDrives);
        btnQuery = findViewById(R.id.btnQuery);
        btnNotifications = findViewById(R.id.btnNotifications);

        btnDrives.setOnClickListener(v -> {
            startActivity(new Intent(this, StudentViewDrivesActivity.class));
        });

        btnQuery.setOnClickListener(v -> {
            startActivity(new Intent(this, QueryActivity.class));
        });

        btnNotifications.setOnClickListener(v -> {
            startActivity(new Intent(this, NotificationActivity.class));
        });
    }

}
