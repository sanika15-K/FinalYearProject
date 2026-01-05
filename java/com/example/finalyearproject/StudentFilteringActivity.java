package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class StudentFilteringActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_filtering);

        TextView tv = findViewById(R.id.textTitle);
        tv.setText("Student Filter");
    }
}
