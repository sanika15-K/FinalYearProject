//package com.example.finalyearproject;
//
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import android.os.Bundle;
//
//
//import com.google.firebase.database.*;
//import org.checkerframework.checker.nullness.qual.NonNull;
//
//
//import java.util.ArrayList;
//import java.util.Collections;
//
//
//public class AdminQueryActivity extends AppCompatActivity {
//
//
//    RecyclerView recyclerView;
//    DatabaseReference ref;
//    ArrayList<QueryModel> list = new ArrayList<>();
//    QueryAdapter adapter;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_admin_query);
//
//
//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//
//        adapter = new QueryAdapter(this, list);
//        recyclerView.setAdapter(adapter);
//
//
//        ref = FirebaseDatabase.getInstance().getReference("queries");
//
//
//        loadQueries();
//    }
//
//
//    private void loadQueries() {
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                list.clear();
//
//
//                for (DataSnapshot snap : snapshot.getChildren()) {
//                    QueryModel model = snap.getValue(QueryModel.class);
//                    if (model != null) list.add(model);
//                }
//
//
//                // Sort by priority first → then by timestamp
//                Collections.sort(list, (a, b) -> {
//                    if (a.priority != b.priority) {
//                        return Integer.compare(a.priority, b.priority);
//                    } else {
//                        return Long.compare(a.timestamp, b.timestamp);
//                    }
//                });
//
//
//                adapter.notifyDataSetChanged();
//            }
//
//
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) { }
//        });
//    }
//}


package com.example.finalyearproject;




import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;




import com.google.firebase.database.*;
import org.checkerframework.checker.nullness.qual.NonNull;




import java.util.ArrayList;
import java.util.Collections;




public class AdminQueryActivity extends AppCompatActivity {




    RecyclerView recyclerView;
    DatabaseReference ref;
    ArrayList<QueryModel> list = new ArrayList<>();
    QueryAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_query);




        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        adapter = new QueryAdapter(this, list);
        recyclerView.setAdapter(adapter);




        ref = FirebaseDatabase.getInstance().getReference("queries");




        loadQueries();
    }




    private void loadQueries() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();




                for (DataSnapshot snap : snapshot.getChildren()) {
                    QueryModel model = snap.getValue(QueryModel.class);
                    if (model != null) list.add(model);
                }




                // Sort by priority first → then by timestamp
                Collections.sort(list, (a, b) -> {
                    if (a.priority != b.priority) {
                        return Integer.compare(a.priority, b.priority);
                    } else {
                        return Long.compare(a.timestamp, b.timestamp);
                    }
                });




                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}
