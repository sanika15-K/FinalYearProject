//package com.example.finalyearproject;
//
//import android.app.AlertDialog;
//import android.app.DownloadManager;
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Environment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.firebase.firestore.FirebaseFirestore;
//
//import java.util.ArrayList;
//
//public class AdminDriveAdapter
//        extends RecyclerView.Adapter<AdminDriveAdapter.ViewHolder> {
//
//    ArrayList<DriveModel> list;
//    Context context;
//
//    public AdminDriveAdapter(ArrayList<DriveModel> list, Context context) {
//        this.list = list;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(context)
//                .inflate(R.layout.item_drive_admin, parent, false);
//        return new ViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {
//
//        DriveModel d = list.get(pos);
//
//        h.company.setText(d.companyName());
//        h.eligibility.setText("Eligibility: " + d.eligibility());
//
//        h.openForm.setOnClickListener(v -> openLink(d.formLink()));
//        h.viewFile.setOnClickListener(v -> openFile(d.getFileUrl()));
//
//        h.update.setOnClickListener(v -> {
//            Intent intent = new Intent(context, EditDriveActivity.class);
//
//            intent.putExtra("id", d.id());
//            intent.putExtra("company", d.companyName());
//            intent.putExtra("eligibility", d.eligibility());
//            intent.putExtra("formLink", d.formLink());
//
//            context.startActivity(intent);
//        });
//
//
//        h.delete.setOnClickListener(v -> {
//            new AlertDialog.Builder(context)
//                    .setTitle("Delete Drive")
//                    .setMessage("Are you sure?")
//                    .setPositiveButton("Delete", (x, y) ->
//                            FirebaseFirestore.getInstance()
//                                    .collection("drive")
//                                    .document(d.getId())
//                                    .delete())
//                    .setNegativeButton("Cancel", null)
//                    .show();
//        });
//    }
//
//    private void openLink(String link) {
//        if (!link.startsWith("http")) link = "https://" + link;
//        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
//    }
//
//
//
//    private void openFile(String url) { Intent i = new Intent(Intent.ACTION_VIEW); i.setDataAndType(Uri.parse(url), "*/*"); context.startActivity(i); }
//
//
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    static class ViewHolder extends RecyclerView.ViewHolder {
//
//        TextView company, eligibility, openForm, viewFile, update, delete;
//
//        ViewHolder(View v) {
//            super(v);
//            company = v.findViewById(R.id.txtCompany);
//            eligibility = v.findViewById(R.id.txtEligibility);
//            openForm = v.findViewById(R.id.txtOpenForm);
//            viewFile = v.findViewById(R.id.txtViewFile);
//            update = v.findViewById(R.id.txtUpdate);
//            delete = v.findViewById(R.id.txtDelete);
//        }
//    }
//}
package com.example.finalyearproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdminDriveAdapter extends RecyclerView.Adapter<AdminDriveAdapter.ViewHolder> {

    Context context;
    ArrayList<DriveModel> list;

    public AdminDriveAdapter(Context context, ArrayList<DriveModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_drive_admin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DriveModel model = list.get(position);

        holder.tvCompany.setText(model.companyName);
        holder.tvEligibility.setText(model.eligibility);
        holder.tvFormLink.setText(model.formLink);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCompany, tvEligibility, tvFormLink;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCompany = itemView.findViewById(R.id.tvCompany);
            tvEligibility = itemView.findViewById(R.id.tvEligibility);
            tvFormLink = itemView.findViewById(R.id.tvFormLink);
        }
    }
}
