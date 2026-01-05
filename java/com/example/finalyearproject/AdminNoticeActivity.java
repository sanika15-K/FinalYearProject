package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.auth.oauth2.GoogleCredentials;

import org.json.JSONObject;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;

public class AdminNoticeActivity extends AppCompatActivity {

    EditText titleInput, messageInput;
    Button sendButton;
    DatabaseReference noticesRef;

    // 🔹 Replace with your actual Firebase project ID
    private static final String PROJECT_ID =
            "finalyearproject-f7105"; // e.g. mycollegeapp-12345

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notice);

        titleInput = findViewById(R.id.noticeTitle);
        messageInput = findViewById(R.id.noticeMessage);
        sendButton = findViewById(R.id.sendNoticeButton);
        noticesRef = FirebaseDatabase.getInstance().getReference("notices");

        sendButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString().trim();
            String message = messageInput.getText().toString().trim();

            if (title.isEmpty() || message.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save notice to Realtime Database
            String id = noticesRef.push().getKey();
            HashMap<String, Object> data = new HashMap<>();
            data.put("title", title);
            data.put("message", message);
            data.put("timestamp", System.currentTimeMillis());
            noticesRef.child(id).setValue(data);

            // Send notification via FCM v1 API
            new Thread(() -> sendPushNotificationV1(title, message)).start();
        });
    }

    private void sendPushNotificationV1(String title, String message) {
        try {
            // ✅ Load the service account JSON from assets
            InputStream serviceAccount = getAssets().open("service-account.json");

            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount)
                    .createScoped(Collections.singletonList("https://www.googleapis.com/auth/firebase.messaging"));
            credentials.refreshIfExpired();

            String token = credentials.getAccessToken().getTokenValue();

            // ✅ FCM v1 endpoint
            URL url = new URL("https://fcm.googleapis.com/v1/projects/" + PROJECT_ID + "/messages:send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setRequestProperty("Content-Type", "application/json; UTF-8");
            conn.setDoOutput(true);

            // ✅ Create notification body
            JSONObject notification = new JSONObject();
            notification.put("title", title);
            notification.put("body", message);

            JSONObject messageObj = new JSONObject();
            messageObj.put("topic", "college_updates"); // topic subscribed by students
            messageObj.put("notification", notification);

            JSONObject payload = new JSONObject();
            payload.put("message", messageObj);

            // ✅ Send payload
            OutputStream os = conn.getOutputStream();
            os.write(payload.toString().getBytes("UTF-8"));
            os.flush();
            os.close();

            int responseCode = conn.getResponseCode();

            runOnUiThread(() -> Toast.makeText(AdminNoticeActivity.this,
                    responseCode == 200 ? "✅ Notice sent successfully!" : "❌ FCM Error: " + responseCode,
                    Toast.LENGTH_LONG).show());

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(() -> Toast.makeText(AdminNoticeActivity.this,
                    "Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
        }
    }
}


