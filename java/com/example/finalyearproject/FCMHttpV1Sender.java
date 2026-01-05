package com.example.finalyearproject;

import org.json.JSONObject;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class FCMHttpV1Sender {

    // 🔹 Replace this with your Project ID (from Firebase Settings)
    private static final String PROJECT_ID = "finalyearproject-12345";

    // 🔹 Replace with your Service Account access token (from your server or via Firebase Admin SDK)
    private static final String ACCESS_TOKEN = "YOUR_ACCESS_TOKEN";

    public static void sendMessage(String title, String body, String token) {
        try {
            URL url = new URL("https://fcm.googleapis.com/v1/projects/" + PROJECT_ID + "/messages:send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + ACCESS_TOKEN);
            conn.setRequestProperty("Content-Type", "application/json; UTF-8");
            conn.setDoOutput(true);

            // 🔹 Notification payload
            JSONObject message = new JSONObject();
            JSONObject notification = new JSONObject();
            notification.put("title", title);
            notification.put("body", body);

            JSONObject messageObj = new JSONObject();
            messageObj.put("token", token);
            messageObj.put("notification", notification);

            message.put("message", messageObj);

            OutputStream os = conn.getOutputStream();
            os.write(message.toString().getBytes(StandardCharsets.UTF_8));
            os.close();

            int responseCode = conn.getResponseCode();
            System.out.println("FCM Response Code: " + responseCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
