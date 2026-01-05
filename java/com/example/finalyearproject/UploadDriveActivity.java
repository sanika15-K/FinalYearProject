////package com.example.finalyearproject;
////
////import android.content.Intent;
////import android.database.Cursor;
////import android.net.Uri;
////import android.os.Bundle;
////import android.provider.OpenableColumns;
////import android.util.Log;
////import android.widget.Button;
////import android.widget.EditText;
////import android.widget.Toast;
////
////import androidx.annotation.Nullable;
////import androidx.appcompat.app.AppCompatActivity;
////
////import com.cloudinary.utils.ObjectUtils;
////import com.google.firebase.database.DatabaseReference;
////import com.google.firebase.database.FirebaseDatabase;
////import com.google.firebase.firestore.FirebaseFirestore;
////
////import java.io.File;
////import java.io.FileOutputStream;
////import java.io.InputStream;
////import java.util.HashMap;
////import java.util.Map;
////
////public class UploadDriveActivity extends AppCompatActivity {
////
////    private static final int PICK_FILE_REQUEST = 101;
////    private Uri selectedFileUri;
////
////    private EditText etCompanyName, etEligibility, etFormLink;
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_upload_drive);
////
////        Log.d("DRIVE_TEST", "UploadDriveActivity OPENED");
////
////        etCompanyName = findViewById(R.id.etCompanyName);
////        etEligibility = findViewById(R.id.etEligibility);
////        etFormLink = findViewById(R.id.etFormLink);
////
////        Button btnSelectFile = findViewById(R.id.btnSelectFile);
////        Button btnUploadFile = findViewById(R.id.btnUploadFile);
////
////        btnSelectFile.setOnClickListener(v -> openFilePicker());
////
////        btnUploadFile.setOnClickListener(v -> {
////            Log.d("DRIVE_TEST", "UPLOAD BUTTON CLICKED");
////
////            if (selectedFileUri == null) {
////                Toast.makeText(this, "Please select a file", Toast.LENGTH_SHORT).show();
////                return;
////            }
////
////            if (etCompanyName.getText().toString().trim().isEmpty()) {
////                Toast.makeText(this, "Enter company name", Toast.LENGTH_SHORT).show();
////                return;
////            }
////
////            uploadAndSave(selectedFileUri);
////        });
////    }
////
////    private void openFilePicker() {
////        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
////        intent.setType("*/*");
////        intent.addCategory(Intent.CATEGORY_OPENABLE);
////        startActivityForResult(intent, PICK_FILE_REQUEST);
////    }
////
////    @Override
////    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null) {
////            selectedFileUri = data.getData();
////            Toast.makeText(this, "File selected", Toast.LENGTH_SHORT).show();
////        }
////    }
////
////    private void uploadAndSave(Uri uri) {
////        Log.d("DRIVE_TEST", "uploadAndSave() CALLED");
////
////        Toast.makeText(this, "Uploading...", Toast.LENGTH_SHORT).show();
////
////        new Thread(() -> {
////            try {
////                File file = createTempFile(uri);
////
////                Map uploadResult = CloudinaryUploadHelper
////                        .getCloudinary()
////                        .uploader()
////                        .upload(
////                                file,
////                                ObjectUtils.asMap(
////                                        "resource_type", "raw",
////                                        "folder", "tpomodule",
////                                        "use_filename", true,
////                                        "unique_filename", true
////                                )
////                        );
////
////                String fileUrl = uploadResult.get("secure_url").toString();
////
////                HashMap<String, Object> driveMap = new HashMap<>();
////                driveMap.put("companyName", etCompanyName.getText().toString().trim());
////                driveMap.put("eligibility", etEligibility.getText().toString().trim());
////                driveMap.put("formLink", etFormLink.getText().toString().trim());
////                driveMap.put("fileUrl", fileUrl);
////                driveMap.put("createdAt", System.currentTimeMillis());
////
////                FirebaseFirestore.getInstance()
////                        .collection("drive")
////                        .add(driveMap)
////                        .addOnSuccessListener(doc -> {
////                            sendNotificationToStudents(
////                                    etCompanyName.getText().toString().trim()
////                            );
////                            runOnUiThread(() ->
////                                    Toast.makeText(
////                                            this,
////                                            "Drive uploaded successfully",
////                                            Toast.LENGTH_SHORT
////                                    ).show()
////                            );
////                        })
////                        .addOnFailureListener(e ->
////                                runOnUiThread(() ->
////                                        Toast.makeText(
////                                                this,
////                                                "Firestore error: " + e.getMessage(),
////                                                Toast.LENGTH_LONG
////                                        ).show()
////                                )
////                        );
////
////            } catch (Exception e) {
////                runOnUiThread(() ->
////                        Toast.makeText(
////                                this,
////                                "Upload failed: " + e.getMessage(),
////                                Toast.LENGTH_LONG
////                        ).show()
////                );
////            }
////        }).start();
////    }
////
////    // 🔔 REALTIME NOTIFICATION (SAME STRUCTURE AS QUERY)
////    private void sendNotificationToStudents(String companyName) {
////
////        DatabaseReference ref = FirebaseDatabase.getInstance()
////                .getReference("notifications")
////                .child("students");
////
////        String id = ref.push().getKey();
////
////        HashMap<String, Object> map = new HashMap<>();
////        map.put("title", "New Drive Uploaded");
////        map.put("message", companyName + " drive is available");
////        map.put("type", "DRIVE");
////        map.put("timestamp", System.currentTimeMillis());
////
////        ref.child(id).setValue(map);
////    }
////
////    private File createTempFile(Uri uri) throws Exception {
////        String name = getFileName(uri);
////        File file = new File(getCacheDir(), name);
////
////        InputStream in = getContentResolver().openInputStream(uri);
////        FileOutputStream out = new FileOutputStream(file);
////
////        byte[] buffer = new byte[4096];
////        int read;
////        while ((read = in.read(buffer)) != -1) {
////            out.write(buffer, 0, read);
////        }
////
////        in.close();
////        out.close();
////        return file;
////    }
////
////    private String getFileName(Uri uri) {
////        String result = "file";
////        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
////        if (cursor != null && cursor.moveToFirst()) {
////            int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
////            if (index != -1) result = cursor.getString(index);
////            cursor.close();
////        }
////        return result;
////    }
////}
//
//
//package com.example.finalyearproject;
//
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.provider.OpenableColumns;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.app.NotificationCompat;
//import androidx.core.content.ContextCompat;
//
//import com.cloudinary.utils.ObjectUtils;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.util.HashMap;
//import java.util.Map;
//
//import android.Manifest;
//
//
//
//public class UploadDriveActivity extends AppCompatActivity {
//
//    private static final int PICK_FILE_REQUEST = 101;
//    private Uri selectedFileUri;
//
//    private EditText etCompanyName, etEligibility, etFormLink;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_upload_drive);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            if (ContextCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.POST_NOTIFICATIONS
//            ) != PackageManager.PERMISSION_GRANTED) {
//
//                ActivityCompat.requestPermissions(
//                        this,
//                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
//                        101
//                );
//            }
//        }
//
//
//        etCompanyName = findViewById(R.id.etCompanyName);
//        etEligibility = findViewById(R.id.etEligibility);
//        etFormLink = findViewById(R.id.etFormLink);
//
//        Button btnSelectFile = findViewById(R.id.btnSelectFile);
//        Button btnUploadFile = findViewById(R.id.btnUploadFile);
//
//        btnSelectFile.setOnClickListener(v -> openFilePicker());
//
//        btnUploadFile.setOnClickListener(v -> {
//            if (selectedFileUri == null) {
//                Toast.makeText(this, "Please select a file", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            if (etCompanyName.getText().toString().trim().isEmpty()) {
//                Toast.makeText(this, "Enter company name", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            uploadAndSave(selectedFileUri);
//        });
//        showNotification("");
//    }
//
//    private void openFilePicker() {
//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        intent.setType("*/*");
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        startActivityForResult(intent, PICK_FILE_REQUEST);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null) {
//            selectedFileUri = data.getData();
//            Toast.makeText(this, "File selected", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void uploadAndSave(Uri uri) {
//
//        Toast.makeText(this, "Uploading...", Toast.LENGTH_SHORT).show();
//
//        new Thread(() -> {
//            try {
//                File file = createTempFile(uri);
//
//                Map uploadResult = CloudinaryUploadHelper
//                        .getCloudinary()
//                        .uploader()
//                        .upload(file, ObjectUtils.asMap(
//                                "resource_type", "raw",
//                                "folder", "tpomodule"
//                        ));
//
//                String fileUrl = uploadResult.get("secure_url").toString();
//
//                HashMap<String, Object> driveMap = new HashMap<>();
//                driveMap.put("companyName", etCompanyName.getText().toString().trim());
//                driveMap.put("eligibility", etEligibility.getText().toString().trim());
//                driveMap.put("formLink", etFormLink.getText().toString().trim());
//                driveMap.put("fileUrl", fileUrl);
//                driveMap.put("createdAt", System.currentTimeMillis());
//
//                FirebaseFirestore.getInstance()
//                        .collection("drive")
//                        .add(driveMap)
//                        .addOnSuccessListener(doc -> {
//                            runOnUiThread(() -> {
//                                Toast.makeText(this,
//                                        "Drive uploaded successfully",
//                                        Toast.LENGTH_LONG).show();
//
//                                // 🔔 THIS IS THE FIX
//                                showNotification(
//                                        etCompanyName.getText().toString().trim()
//                                );
//                            });
//                        });
//
//            } catch (Exception e) {
//                runOnUiThread(() ->
//                        Toast.makeText(this,
//                                "Upload failed: " + e.getMessage(),
//                                Toast.LENGTH_LONG).show()
//                );
//            }
//        }).start();
//    }
//
//    // 🔔 LOCAL NOTIFICATION (WORKING)
//    private void showNotification(String companyName) {
//
//        String channelId = "drive_channel";
//
//        NotificationManager manager =
//                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(
//                    channelId,
//                    "Drive Notifications",
//                    NotificationManager.IMPORTANCE_HIGH
//            );
//            manager.createNotificationChannel(channel);
//        }
//
//        NotificationCompat.Builder builder =
//                new NotificationCompat.Builder(this, channelId)
//                        .setSmallIcon(R.drawable.ic_notification)
//                        .setContentTitle("New Drive ")
//                        .setContentText(companyName + " drive uploading")
//                        .setAutoCancel(true);
//
//        manager.notify((int) System.currentTimeMillis(), builder.build());
//    }
//
//    private File createTempFile(Uri uri) throws Exception {
//        String name = getFileName(uri);
//        File file = new File(getCacheDir(), name);
//
//        InputStream in = getContentResolver().openInputStream(uri);
//        FileOutputStream out = new FileOutputStream(file);
//
//        byte[] buffer = new byte[4096];
//        int read;
//        while ((read = in.read(buffer)) != -1) {
//            out.write(buffer, 0, read);
//        }
//
//        in.close();
//        out.close();
//        return file;
//    }
//
//    private String getFileName(Uri uri) {
//        String result = "file";
//        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//        if (cursor != null && cursor.moveToFirst()) {
//            int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
//            if (index != -1) result = cursor.getString(index);
//            cursor.close();
//        }
//        return result;
//    }
//}



//package com.example.finalyearproject;
//
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.provider.OpenableColumns;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.app.NotificationCompat;
//import androidx.core.content.ContextCompat;
//
//import com.cloudinary.utils.ObjectUtils;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.util.HashMap;
//import java.util.Map;
//
//import android.Manifest;
//
//public class UploadDriveActivity extends AppCompatActivity {
//
//    private static final int PICK_FILE_REQUEST = 101;
//    private Uri selectedFileUri;
//
//    private EditText etCompanyName, etEligibility, etFormLink;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_upload_drive);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            if (ContextCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.POST_NOTIFICATIONS
//            ) != PackageManager.PERMISSION_GRANTED) {
//
//                ActivityCompat.requestPermissions(
//                        this,
//                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
//                        101
//                );
//            }
//        }
//
//        etCompanyName = findViewById(R.id.etCompanyName);
//        etEligibility = findViewById(R.id.etEligibility);
//        etFormLink = findViewById(R.id.etFormLink);
//
//        Button btnSelectFile = findViewById(R.id.btnSelectFile);
//        Button btnUploadFile = findViewById(R.id.btnUploadFile);
//
//        btnSelectFile.setOnClickListener(v -> openFilePicker());
//
//        btnUploadFile.setOnClickListener(v -> {
//            if (selectedFileUri == null) {
//                Toast.makeText(this, "Please select a file", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            if (etCompanyName.getText().toString().trim().isEmpty()) {
//                Toast.makeText(this, "Enter company name", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            uploadAndSave(selectedFileUri);
//        });
//
//        // ❌ REMOVED notification from onCreate()
//    }
//
//    private void openFilePicker() {
//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        intent.setType("*/*");
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        startActivityForResult(intent, PICK_FILE_REQUEST);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null) {
//            selectedFileUri = data.getData();
//            Toast.makeText(this, "File selected", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void uploadAndSave(Uri uri) {
//
//        Toast.makeText(this, "Uploading...", Toast.LENGTH_SHORT).show();
//
//        new Thread(() -> {
//            try {
//                File file = createTempFile(uri);
//
//                Map uploadResult = CloudinaryUploadHelper
//                        .getCloudinary()
//                        .uploader()
//                        .upload(file, ObjectUtils.asMap(
//                                "resource_type", "raw",
//                                "folder", "tpomodule"
//                        ));
//
//                String fileUrl = uploadResult.get("secure_url").toString();
//
//                HashMap<String, Object> driveMap = new HashMap<>();
//                driveMap.put("companyName", etCompanyName.getText().toString().trim());
//                driveMap.put("eligibility", etEligibility.getText().toString().trim());
//                driveMap.put("formLink", etFormLink.getText().toString().trim());
//                driveMap.put("fileUrl", fileUrl);
//                driveMap.put("createdAt", System.currentTimeMillis());
//
//                FirebaseFirestore.getInstance()
//                        .collection("drive")
//                        .add(driveMap)
//                        .addOnSuccessListener(doc -> runOnUiThread(() -> {
//                            Toast.makeText(this,
//                                    "Drive uploaded successfully",
//                                    Toast.LENGTH_LONG).show();
//
//                            showNotification(
//                                    etCompanyName.getText().toString().trim()
//                            ); // ✅ CORRECT PLACE
//                        }));
//
//            } catch (Exception e) {
//                runOnUiThread(() ->
//                        Toast.makeText(this,
//                                "Upload failed: " + e.getMessage(),
//                                Toast.LENGTH_LONG).show()
//                );
//            }
//        }).start();
//    }
//
//    private void showNotification(String companyName) {
//
//        String channelId = "drive_channel";
//
//        NotificationManager manager =
//                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(
//                    channelId,
//                    "Drive Notifications",
//                    NotificationManager.IMPORTANCE_HIGH
//            );
//            manager.createNotificationChannel(channel);
//        }
//
//        NotificationCompat.Builder builder =
//                new NotificationCompat.Builder(this, channelId)
//                        .setSmallIcon(R.drawable.ic_notification)
//                        .setContentTitle("New Drive")
//                        .setContentText(companyName + " drive uploaded")
//                        .setAutoCancel(true);
//
//        manager.notify((int) System.currentTimeMillis(), builder.build());
//    }
//
//    private File createTempFile(Uri uri) throws Exception {
//        String name = getFileName(uri);
//        File file = new File(getCacheDir(), name);
//
//        InputStream in = getContentResolver().openInputStream(uri);
//        FileOutputStream out = new FileOutputStream(file);
//
//        byte[] buffer = new byte[4096];
//        int read;
//        while ((read = in.read(buffer)) != -1) {
//            out.write(buffer, 0, read);
//        }
//
//        in.close();
//        out.close();
//        return file;
//    }
//
//    private String getFileName(Uri uri) {
//        String result = "file";
//        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//        if (cursor != null && cursor.moveToFirst()) {
//            int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
//            if (index != -1) result = cursor.getString(index);
//            cursor.close();
//        }
//        return result;
//    }
//}

package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UploadDriveActivity extends AppCompatActivity {

    EditText companyNameEt, eligibilityEt, formLinkEt;
    Button btnChooseFile, btnUpload;
    Uri fileUri;

    DatabaseReference driveRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_drive);

        CloudinaryUploadHelper.init(this);

        companyNameEt = findViewById(R.id.etCompanyName);
        eligibilityEt = findViewById(R.id.etEligibility);
        formLinkEt = findViewById(R.id.etFormLink);
        btnChooseFile = findViewById(R.id.btnSelectFile);
        btnUpload = findViewById(R.id.btnUploadFile);

        // ✅ SINGLE SOURCE OF TRUTH
        driveRef = FirebaseDatabase.getInstance().getReference("drives");

        btnChooseFile.setOnClickListener(v -> chooseFile());
        btnUpload.setOnClickListener(v -> uploadDrive());
    }

    private void chooseFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            fileUri = data.getData();
            Toast.makeText(this, "File selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadDrive() {

        String company = companyNameEt.getText().toString().trim();
        String eligibility = eligibilityEt.getText().toString().trim();
        String formLink = formLinkEt.getText().toString().trim();

        if (company.isEmpty() || fileUri == null) {
            Toast.makeText(this, "Fill all details", Toast.LENGTH_SHORT).show();
            return;
        }

        String driveId = driveRef.push().getKey();

        MediaManager.get().upload(fileUri)
                .unsigned("android_upload")   // ✅ IMPORTANT
                .callback(new UploadCallback() {

                    @Override
                    public void onSuccess(String requestId, Map resultData) {

                        String fileUrl = resultData.get("secure_url").toString();

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("companyName", company);
                        map.put("eligibility", eligibility);
                        map.put("formLink", formLink);
                        map.put("fileUrl", fileUrl);
                        map.put("timestamp", System.currentTimeMillis());

                        driveRef.child(driveId).setValue(map)
                                .addOnSuccessListener(unused -> {
                                    Toast.makeText(UploadDriveActivity.this,
                                            "Drive uploaded successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                });
                    }

                    @Override
                    public void onError(String requestId, ErrorInfo error) {
                        Toast.makeText(UploadDriveActivity.this,
                                error.getDescription(), Toast.LENGTH_LONG).show();
                    }

                    @Override public void onStart(String requestId) {}
                    @Override public void onProgress(String requestId, long bytes, long totalBytes) {}
                    @Override public void onReschedule(String requestId, ErrorInfo error) {}
                }).dispatch();
    }
}

