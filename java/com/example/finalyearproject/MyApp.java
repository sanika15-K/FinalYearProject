package com.example.finalyearproject;

import android.app.Application;

import androidx.annotation.NonNull;

import com.google.firebase.database.*;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        listenForAnsweredQueries();
        listenForNewNotices();
    }

    private void listenForAnsweredQueries() {

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("queries");

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, String s) {

                QueryModel q = snapshot.getValue(QueryModel.class);

                if (q != null
                        && q.adminResponse != null
                        && !q.adminResponse.isEmpty()
                        && !q.notified) {

                    LocalNotificationHelper.show(
                            getApplicationContext(),
                            "Query Answered",
                            q.adminResponse
                    );

                    snapshot.getRef().child("notified").setValue(true);
                }
            }

            @Override public void onChildAdded(@NonNull DataSnapshot snapshot, String s) {}
            @Override public void onChildRemoved(@NonNull DataSnapshot snapshot) {}
            @Override public void onChildMoved(@NonNull DataSnapshot snapshot, String s) {}
            @Override public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void listenForNewNotices() {

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("notices");

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, String s) {

                NoticeModel m = snapshot.getValue(NoticeModel.class);
                if (m != null) {
                    LocalNotificationHelper.show(
                            getApplicationContext(),
                            "New Notice",
                            m.title
                    );
                }
            }

            @Override public void onChildChanged(@NonNull DataSnapshot snapshot, String s) {}
            @Override public void onChildRemoved(@NonNull DataSnapshot snapshot) {}
            @Override public void onChildMoved(@NonNull DataSnapshot snapshot, String s) {}
            @Override public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}
