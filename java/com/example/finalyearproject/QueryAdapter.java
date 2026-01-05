package com.example.finalyearproject;

import android.app.AlertDialog;
import android.content.Context;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class QueryAdapter extends RecyclerView.Adapter<QueryAdapter.ViewHolder> {

    Context context;
    ArrayList<QueryModel> list;

    public QueryAdapter(Context context, ArrayList<QueryModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_query, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {
        QueryModel m = list.get(pos);

        h.txtQuery.setText(m.queryText);
        h.txtPriority.setText("Priority: " + m.priority);
        if (m.adminResponse == null || m.adminResponse.isEmpty()) {
            h.txtResponse.setText("No Reply");
        } else {
            h.txtResponse.setText(m.adminResponse);
        }


        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("queries")
                .child(m.queryId);

        h.btnHigh.setOnClickListener(v -> ref.child("priority").setValue(1));
        h.btnMedium.setOnClickListener(v -> ref.child("priority").setValue(2));
        h.btnLow.setOnClickListener(v -> ref.child("priority").setValue(3));

        h.btnAnswer.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View dialog = LayoutInflater.from(context).inflate(R.layout.dialog_answer, null);

            EditText edt = dialog.findViewById(R.id.editAnswer);
            Button send = dialog.findViewById(R.id.btnSendAnswer);

            builder.setView(dialog);
            AlertDialog alert = builder.create();

            send.setOnClickListener(v2 -> {
                String ans = edt.getText().toString();
                if (!ans.isEmpty()) {


                    FirebaseDatabase.getInstance()
                            .getReference("notifications")
                            .child("students")
                            .push()
                            .setValue(new NoticeModel(
                                    "Query Answered",
                                    "Admin replied to your query",
                                    System.currentTimeMillis()
                            ));
                    ref.child("adminResponse").setValue(ans);
                    alert.dismiss();
                }
            });

            alert.show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtQuery, txtPriority, txtResponse;
        Button btnHigh, btnMedium, btnLow, btnAnswer;

        ViewHolder(View v) {
            super(v);

            txtQuery = v.findViewById(R.id.txtQuery);
            txtPriority = v.findViewById(R.id.txtPriority);
            txtResponse = v.findViewById(R.id.txtResponse);
            btnHigh = v.findViewById(R.id.btnHigh);
            btnMedium = v.findViewById(R.id.btnMedium);
            btnLow = v.findViewById(R.id.btnLow);
            btnAnswer = v.findViewById(R.id.btnAnswer);
        }
    }
}
