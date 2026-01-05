//package com.example.finalyearproject;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class EditDriveActivity extends AppCompatActivity {
//
//    private EditText etCompanyName, etEligibility, etFormLink;
//    private Button btnUpdateDrive;
//
//    private String driveId;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_drive);
//
//        // 🔗 Bind views
//        etCompanyName = findViewById(R.id.etCompanyName);
//        etEligibility = findViewById(R.id.etEligibility);
//        etFormLink = findViewById(R.id.etFormLink);
//        btnUpdateDrive = findViewById(R.id.btnUpdateDrive);
//
//        // 📦 Get intent data (MUST match adapter keys)
//        driveId = getIntent().getStringExtra("id");
//        String company = getIntent().getStringExtra("company");
//        String eligibility = getIntent().getStringExtra("eligibility");
//        String formLink = getIntent().getStringExtra("formLink");
//
//        Log.d("EDIT_DRIVE", "Drive ID: " + driveId);
//
//        // ❌ Safety check
//        if (driveId == null) {
//            Toast.makeText(this, "Drive ID missing", Toast.LENGTH_SHORT).show();
//            finish();
//            return;
//        }
//
//        // ✍️ Set existing data
//        etCompanyName.setText(company);
//        etEligibility.setText(eligibility);
//        etFormLink.setText(formLink);
//
//        // 🔄 Update button
//        btnUpdateDrive.setOnClickListener(v -> updateDrive());
//
//        showDriveUpdatedNotification();
//
//    }
//
//    private void updateDrive() {
//
//        String updatedCompany = etCompanyName.getText().toString().trim();
//        String updatedEligibility = etEligibility.getText().toString().trim();
//        String updatedFormLink = etFormLink.getText().toString().trim();
//
//        // ❌ Validation
//        if (updatedCompany.isEmpty()) {
//            etCompanyName.setError("Required");
//            return;
//        }
//
//        Map<String, Object> updateMap = new HashMap<>();
//        updateMap.put("companyName", updatedCompany);
//        updateMap.put("eligibility", updatedEligibility);
//        updateMap.put("formLink", updatedFormLink);
//
////        FirebaseFirestore.getInstance()
////                .collection("drive")          // ✅ SAME collection
////                .document(driveId)            // ✅ SAME document
////                .update(updateMap)             // ✅ UPDATE (not add)
////                .addOnSuccessListener(unused -> {
////                    Toast.makeText(this, "Drive updated successfully", Toast.LENGTH_SHORT).show();
////                    finish();
////                })
////                .addOnFailureListener(e -> {
////                    Log.e("EDIT_DRIVE", "Update failed", e);
////                    Toast.makeText(this, "Update failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
////                });
//        FirebaseFirestore.getInstance()
//                .collection("drive")
//                .document(driveId)
//                .update(updateMap)
//                .addOnSuccessListener(unused -> {
//
//                    showDriveUpdatedNotification(); // 🔔 MUST BE HERE
//
//                    Toast.makeText(this, "Drive updated", Toast.LENGTH_SHORT).show();
//                    finish();
//                })
//
//                .addOnFailureListener(e ->
//                        Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
//                );
//
//
//    }
//    private void showDriveUpdatedNotification() {
//
//        String channelId = "drive_update_channel";
//
//        android.app.NotificationManager notificationManager =
//                (android.app.NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            android.app.NotificationChannel channel =
//                    new android.app.NotificationChannel(
//                            channelId,
//                            "Drive Updates",
//                            android.app.NotificationManager.IMPORTANCE_HIGH
//                    );
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        androidx.core.app.NotificationCompat.Builder builder =
//                new androidx.core.app.NotificationCompat.Builder(this, channelId)
//                        .setSmallIcon(android.R.drawable.ic_dialog_info) // ✅ SAFE ICON
//                        .setContentTitle("Drive Updated")
//                        .setContentText("Drive details have been updated")
//                        .setPriority(androidx.core.app.NotificationCompat.PRIORITY_HIGH)
//                        .setDefaults(android.app.Notification.DEFAULT_ALL)
//                        .setAutoCancel(true);
//
//        notificationManager.notify(
//                (int) System.currentTimeMillis(),
//                builder.build()
//        );
//    }
//
//
//
//}

package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class EditDriveActivity extends AppCompatActivity {

    EditText etCompany, etEligibility, etFormLink;
    Button btnUpdate;

    DatabaseReference driveRef;
    String driveId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_drive);

        etCompany = findViewById(R.id.etCompanyName);
        etEligibility = findViewById(R.id.etEligibility);
        etFormLink = findViewById(R.id.etFormLink);
        btnUpdate = findViewById(R.id.btnUpdateDrive);

        driveId = getIntent().getStringExtra("driveId");
        driveRef = FirebaseDatabase.getInstance().getReference("drive");

        btnUpdate.setOnClickListener(v -> updateDrive());
    }

    private void updateDrive() {

        String company = etCompany.getText().toString().trim();
        String eligibility = etEligibility.getText().toString().trim();
        String formLink = etFormLink.getText().toString().trim();

        if (company.isEmpty()) {
            Toast.makeText(this, "Company name required", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("companyName", company);
        map.put("eligibility", eligibility);
        map.put("formLink", formLink);

        driveRef.child(driveId).updateChildren(map)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Drive updated", Toast.LENGTH_SHORT).show();
                    finish();
                });
    }
}
