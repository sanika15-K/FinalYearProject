//package com.example.finalyearproject;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.os.Bundle;
//
//import com.google.firebase.database.*;
//
//import java.util.ArrayList;
//
//public class AnsweredQueriesActivity extends AppCompatActivity {
//
//    RecyclerView recyclerView;
//    AnsweredQueryAdapter adapter;
//    ArrayList<QueryModel> list;
//    DatabaseReference ref;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_answered_queries);
//
//        recyclerView = findViewById(R.id.recyclerAnswered);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        list = new ArrayList<>();
//        adapter = new AnsweredQueryAdapter(list);
//        recyclerView.setAdapter(adapter);
//
//        ref = FirebaseDatabase.getInstance().getReference("queries");
//
////        ref.addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                list.clear();
////
////                for (DataSnapshot snap : snapshot.getChildren()) {
////                    QueryModel q = snap.getValue(QueryModel.class);
////                    if (q != null && q.adminResponse != null && !q.adminResponse.isEmpty()) {
////                        list.add(q);
////                    }
////
////                }
////
////                adapter.notifyDataSetChanged();
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError error) { }
////        });
//
//
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                list.clear();
//
//                for (DataSnapshot snap : snapshot.getChildren()) {
//                    QueryModel q = snap.getValue(QueryModel.class);
//
//                    if (q != null && q.adminResponse != null && !q.adminResponse.isEmpty()) {
//                        list.add(q);
//                    }
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) { }
//        });
//
//
//    }
//}




package com.example.finalyearproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.*;

import java.util.ArrayList;

public class AnsweredQueriesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AnsweredQueryAdapter adapter;
    ArrayList<QueryModel> list;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answered_queries);

        recyclerView = findViewById(R.id.recyclerAnswered);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new AnsweredQueryAdapter(list);
        recyclerView.setAdapter(adapter);

        ref = FirebaseDatabase.getInstance().getReference("queries");

        // 🔹 1. Load answered queries in list (NO notification here)
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();

                for (DataSnapshot snap : snapshot.getChildren()) {
                    QueryModel q = snap.getValue(QueryModel.class);

                    if (q != null
                            && q.adminResponse != null
                            && !q.adminResponse.isEmpty()) {

                        list.add(q);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        // 🔔 2. Popup ONLY when admin answers a query first time
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, String s) {

                QueryModel q = snapshot.getValue(QueryModel.class);

                if (q != null
                        && q.adminResponse != null
                        && !q.adminResponse.isEmpty()
                        && !q.notified) {

                    LocalNotificationHelper.show(
                            AnsweredQueriesActivity.this,
                            "Query Answered",
                            q.adminResponse
                    );

                    // mark as notified
                    snapshot.getRef().child("notified").setValue(true);
                }
            }

            @Override public void onChildAdded(@NonNull DataSnapshot snapshot, String s) {}
            @Override public void onChildRemoved(@NonNull DataSnapshot snapshot) {}
            @Override public void onChildMoved(@NonNull DataSnapshot snapshot, String s) {}
            @Override public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}

//package com.example.finalyearproject;
//
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//
//import android.os.Bundle;
//
//
//import com.google.firebase.database.*;
//
//
//import java.util.ArrayList;
//
//
//public class AnsweredQueriesActivity extends AppCompatActivity {
//
//
//    RecyclerView recyclerView;
//    AnsweredQueryAdapter adapter;
//    ArrayList<QueryModel> list;
//    DatabaseReference ref;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_answered_queries);
//
//
//        recyclerView = findViewById(R.id.recyclerAnswered);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//
//        list = new ArrayList<>();
//        adapter = new AnsweredQueryAdapter(list);
//        recyclerView.setAdapter(adapter);
//
//
//        ref = FirebaseDatabase.getInstance().getReference("queries");
//
//
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                list.clear();
//
//
//                for (DataSnapshot snap : snapshot.getChildren()) {
//                    QueryModel q = snap.getValue(QueryModel.class);
//                    if (q != null && q.adminResponse != null && !q.adminResponse.isEmpty()) {
//                        list.add(q);
//                    }
//
//
//                }
//
//
//                adapter.notifyDataSetChanged();
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) { }
//        });
//    }
//}
