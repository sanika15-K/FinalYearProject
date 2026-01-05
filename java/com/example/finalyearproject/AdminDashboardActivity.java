package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

public class AdminDashboardActivity extends AppCompatActivity {

    LinearLayout btnPostDrive, btnViewDrive, btnQuery, btnNotice, btnAnalytics, btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        btnPostDrive = findViewById(R.id.btnPostDrive);
        btnViewDrive = findViewById(R.id.btnViewDrive);
        btnQuery = findViewById(R.id.btnQuery);
        btnNotice = findViewById(R.id.btnNotice);
//        btnAnalytics = findViewById(R.id.btnAnalytics);
//        btnSettings = findViewById(R.id.btnSettings);

        btnPostDrive.setOnClickListener(v ->
                startActivity(new Intent(this, UploadDriveActivity.class)));

        btnViewDrive.setOnClickListener(v ->
                startActivity(new Intent(this, AdminViewDrivesActivity.class)));

        btnQuery.setOnClickListener(v ->
                startActivity(new Intent(this, AdminQueryActivity.class)));

        btnNotice.setOnClickListener(v ->
                startActivity(new Intent(this, AdminSendNotificationMenuActivity.class)));

//        btnAnalytics.setOnClickListener(v ->
//                startActivity(new Intent(this, AnalyticsActivity.class)));
//
//        btnSettings.setOnClickListener(v ->
//                startActivity(new Intent(this, SettingsActivity.class)));
    }
}
