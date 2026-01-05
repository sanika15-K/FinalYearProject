package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class AdminSendNotificationMenuActivity extends AppCompatActivity {

    Button btnSendNotice, btnSendCompanyNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notification_menu);

        btnSendNotice = findViewById(R.id.btnSendNotice);
        btnSendCompanyNotification = findViewById(R.id.btnSendCompanyNotification);

        btnSendNotice.setOnClickListener(v ->
                startActivity(new Intent(this, AdminNoticeActivity.class))
        );
    }
}
