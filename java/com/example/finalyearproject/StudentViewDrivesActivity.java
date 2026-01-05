//package com.example.finalyearproject;
//
//import android.os.Bundle;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.Spinner;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//
//public class StudentViewDrivesActivity extends AppCompatActivity {
//
//    RecyclerView recyclerView;
//    Spinner spinnerMonth, spinnerYear;
//    Button btnFilter;
//
//    FirebaseFirestore firestore;
//    ArrayList<DriveModel> allDrives = new ArrayList<>();
//    ArrayList<DriveModel> filteredDrives = new ArrayList<>();
//
//    StudentDriveAdapter adapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_drives);
//
//        recyclerView = findViewById(R.id.recyclerViewDrives);
//        spinnerMonth = findViewById(R.id.spinnerMonth);
//        spinnerYear = findViewById(R.id.spinnerYear);
//        btnFilter = findViewById(R.id.btnFilter);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        firestore = FirebaseFirestore.getInstance();
//        adapter = new StudentDriveAdapter(filteredDrives, this);
//        recyclerView.setAdapter(adapter);
//
//        setupSpinners();
//        loadAllDrives();
//
//        btnFilter.setOnClickListener(v -> applyFilter());
//    }
//
//    private void setupSpinners() {
//
//        String[] months = {
//                "January","February","March","April","May","June",
//                "July","August","September","October","November","December"
//        };
//
//        ArrayAdapter<String> monthAdapter =
//                new ArrayAdapter<>(this,
//                        android.R.layout.simple_spinner_item, months);
//        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerMonth.setAdapter(monthAdapter);
//
//        ArrayList<String> years = new ArrayList<>();
//        for (int i = 2020; i <= 2026; i++) {
//            years.add(String.valueOf(i));
//        }
//
//        ArrayAdapter<String> yearAdapter =
//                new ArrayAdapter<>(this,
//                        android.R.layout.simple_spinner_item, years);
//        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerYear.setAdapter(yearAdapter);
//    }
//
//    private void loadAllDrives() {
//        firestore.collection("drive")
//                .addSnapshotListener((value, error) -> {
//                    if (value == null) return;
//
//                    allDrives.clear();
//                    for (QueryDocumentSnapshot doc : value) {
//                        DriveModel model = doc.toObject(DriveModel.class);
//                        allDrives.add(model);
//                    }
//                    applyFilter();
//                });
//    }
//
//    private void applyFilter() {
//
//        filteredDrives.clear();
//
//        int selectedMonth = spinnerMonth.getSelectedItemPosition();
//        int selectedYear = Integer.parseInt(spinnerYear.getSelectedItem().toString());
//
//        for (DriveModel d : allDrives) {
//
//            Calendar cal = Calendar.getInstance();
//            cal.setTimeInMillis(d.getCreatedAt());
//
//            if (cal.get(Calendar.MONTH) == selectedMonth &&
//                    cal.get(Calendar.YEAR) == selectedYear) {
//                filteredDrives.add(d);
//            }
//        }
//
//        adapter.notifyDataSetChanged();
//    }
//}


package com.example.finalyearproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class StudentViewDrivesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<DriveModel> list;
    StudentDriveAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_drive_student);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new StudentDriveAdapter(this, list);
        recyclerView.setAdapter(adapter);

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("drive");

        ref.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    DriveModel model = ds.getValue(DriveModel.class);
                    if (model != null) {
                        list.add(model);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(com.google.firebase.database.DatabaseError error) {
            }
        });
    }
}
