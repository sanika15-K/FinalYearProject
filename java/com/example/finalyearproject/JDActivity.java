package com.example.finalyearproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JDActivity extends AppCompatActivity {

    TextView companyTitle;
    EditText jdText;
    Button saveJD;
    String companyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jd);

        companyName = getIntent().getStringExtra("companyName");

        companyTitle = findViewById(R.id.companyTitle);
        jdText = findViewById(R.id.jdText);
        saveJD = findViewById(R.id.saveJD);

        companyTitle.setText(companyName);

        saveJD.setOnClickListener(v -> saveDrive());
    }

    private void saveDrive() {

        String jd = jdText.getText().toString().trim();

        if (jd.isEmpty()) {
            jdText.setError("Enter Job Description");
            return;
        }

        long time = System.currentTimeMillis();

        // Save drive inside Firebase
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("company_drives");

        String id = ref.push().getKey();

        //DriveModel model = new DriveModel(companyName, jd, time);

        //ref.child(id).setValue(model);

        // ALSO store a notification for students
        sendNotificationToStudents(companyName, jd, time);

        Toast.makeText(this, "Drive Added & Notification Sent", Toast.LENGTH_LONG).show();
        finish();
    }

    private void sendNotificationToStudents(String company, String jd, long time) {

        DatabaseReference noticeRef = FirebaseDatabase.getInstance().getReference("notices");
        String id = noticeRef.push().getKey();

        NoticeModel model = new NoticeModel(
                "New Drive: " + company,
                jd,
                time
        );

        noticeRef.child(id).setValue(model);
    }
}
