package com.example.finalyearproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AnsweredQueryAdapter extends RecyclerView.Adapter<AnsweredQueryAdapter.Holder> {

    private final ArrayList<QueryModel> list;

    public AnsweredQueryAdapter(ArrayList<QueryModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.answered_query_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        QueryModel q = list.get(position);
        holder.txtQ.setText(q.queryText == null ? "" : q.queryText);
        holder.txtA.setText(q.adminResponse == null || q.adminResponse.isEmpty() ? "No Answer" : q.adminResponse);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView txtQ, txtA;
        Holder(@NonNull View itemView) {
            super(itemView);
            txtQ = itemView.findViewById(R.id.txtQ);
            txtA = itemView.findViewById(R.id.txtA);
        }
    }
}
