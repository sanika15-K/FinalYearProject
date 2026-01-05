package com.example.finalyearproject;

import android.util.Log;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FCMNotificationSender {

    // 🔴 PUT YOUR SERVER KEY HERE
    private static final String SERVER_KEY = "AAAA....";

    public static void send(String title, String body) {
        new Thread(() -> {
            try {
                URL url = new URL("https://fcm.googleapis.com/fcm/send");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Authorization", "key=" + SERVER_KEY);
                conn.setRequestProperty("Content-Type", "application/json");

                JSONObject json = new JSONObject();
                json.put("to", "/topics/students");

                JSONObject notification = new JSONObject();
                notification.put("title", title);
                notification.put("body", body);

                json.put("notification", notification);

                OutputStream os = conn.getOutputStream();
                os.write(json.toString().getBytes("UTF-8"));
                os.close();

                conn.getInputStream(); // trigger request
            } catch (Exception e) {
                Log.e("FCM", "Error: " + e.getMessage());
            }
        }).start();
    }
}
