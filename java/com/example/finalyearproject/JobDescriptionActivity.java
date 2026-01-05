package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;

public class JobDescriptionActivity extends AppCompatActivity {

    TextView txtCompany, txtDesc, txtFile;
    Button btnOpenFile;

    String fileUrl, fileType, fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_description);

        txtCompany = findViewById(R.id.txtCompany);
        txtDesc = findViewById(R.id.txtDesc);
        txtFile = findViewById(R.id.txtFile);
        btnOpenFile = findViewById(R.id.btnOpenFile);

        Intent i = getIntent();
        txtCompany.setText(i.getStringExtra("companyName"));
        txtDesc.setText(i.getStringExtra("textDescription"));

        fileUrl = i.getStringExtra("fileUrl");
        fileName = i.getStringExtra("fileName");
        fileType = i.getStringExtra("fileType");

        if (fileUrl == null || fileUrl.isEmpty()) {
            txtFile.setText("No file attached");
            btnOpenFile.setEnabled(false);
        } else {
            txtFile.setText(fileName);
            btnOpenFile.setOnClickListener(v -> openFile());
        }
    }

    private void openFile() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(fileUrl));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }
}
