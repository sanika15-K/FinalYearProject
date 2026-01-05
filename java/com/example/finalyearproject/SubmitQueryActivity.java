package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SubmitQueryActivity extends AppCompatActivity {

    EditText editQuery;
    Button btnSubmit;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_query);

        editQuery = findViewById(R.id.editQuery);
        btnSubmit = findViewById(R.id.btnSubmitQuery);

        ref = FirebaseDatabase.getInstance().getReference("queries");

        btnSubmit.setOnClickListener(v -> submitQuery());
    }

    private void submitQuery() {
        String text = editQuery.getText().toString().trim();

        if (text.isEmpty()) {
            editQuery.setError("Enter your query");
            return;
        }

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("queries");
        String id = ref.push().getKey();

        QueryModel q = new QueryModel(
                id,
                text,       // ✅ CORRECT VARIABLE
                "",         // adminResponse
                0,          // default priority (low)
                System.currentTimeMillis()
        );

        ref.child(id).setValue(q);

        Toast.makeText(this, "Query Submitted", Toast.LENGTH_SHORT).show();
        editQuery.setText("");
    }


}
