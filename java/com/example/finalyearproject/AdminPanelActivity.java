package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminPanelActivity extends AppCompatActivity {

    private EditText titleInput, messageInput;
    private Button sendButton;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        titleInput = findViewById(R.id.editTitle);
        messageInput = findViewById(R.id.editMessage);
        sendButton = findViewById(R.id.buttonSend);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleInput.getText().toString().trim();
                String message = messageInput.getText().toString().trim();

                if (title.isEmpty() || message.isEmpty()) {
                    Toast.makeText(AdminPanelActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Call helper to send notification
                FCMHttpV1Sender.sendMessage("notification", title, message);

                Toast.makeText(AdminPanelActivity.this, "Notification Sent!", Toast.LENGTH_SHORT).show();
                titleInput.setText("");
                messageInput.setText("");
            }
        });
    }
}
