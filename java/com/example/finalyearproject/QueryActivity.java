package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class QueryActivity extends AppCompatActivity {

    Button btnAnsweredQueries, btnSubmitNewQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_query);

        btnAnsweredQueries = findViewById(R.id.btnAnsweredQueries);
        btnSubmitNewQuery = findViewById(R.id.btnSubmitNewQuery);

        // Open answered queries
        btnAnsweredQueries.setOnClickListener(v ->
                startActivity(new Intent(QueryActivity.this, AnsweredQueriesActivity.class)));

        // Open submit query page
        btnSubmitNewQuery.setOnClickListener(v ->
                startActivity(new Intent(QueryActivity.this, SubmitQueryActivity.class)));
    }
}
