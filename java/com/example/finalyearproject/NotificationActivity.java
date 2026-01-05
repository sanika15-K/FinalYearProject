package com.example.finalyearproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.*;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    NoticeAdapter adapter;
    ArrayList<NoticeModel> noticeList;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerView = findViewById(R.id.noticeRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        noticeList = new ArrayList<>();
        adapter = new NoticeAdapter(noticeList);
        recyclerView.setAdapter(adapter);

        ref = FirebaseDatabase.getInstance().getReference("notices");

        // 🔹 1. Load notices into RecyclerView (NO notification here)
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                noticeList.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    NoticeModel m = ds.getValue(NoticeModel.class);
                    if (m != null) {
                        noticeList.add(m);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        // 🔔 2. Popup ONLY for new notice
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {

                NoticeModel m = snapshot.getValue(NoticeModel.class);
//                if (m != null) {
//                    LocalNotificationHelper.show(
//                            NotificationActivity.this,
//                            "New Notice",
//                            m.title   // or m.message
//                    );
//                }
            }

            @Override public void onChildChanged(@NonNull DataSnapshot snapshot, String s) {}
            @Override public void onChildRemoved(@NonNull DataSnapshot snapshot) {}
            @Override public void onChildMoved(@NonNull DataSnapshot snapshot, String s) {}
            @Override public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}
