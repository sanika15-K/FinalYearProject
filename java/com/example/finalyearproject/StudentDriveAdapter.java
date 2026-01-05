//package com.example.finalyearproject;
//
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
//import java.util.ArrayList;
//
//public class StudentDriveAdapter
//        extends RecyclerView.Adapter<StudentDriveAdapter.ViewHolder> {
//
//    ArrayList<DriveModel> list;
//    Context context;
//
//    public StudentDriveAdapter(ArrayList<DriveModel> list, Context context) {
//        this.list = list;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(context)
//                .inflate(R.layout.item_drive_student, parent, false);
//        return new ViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {
//
//        DriveModel d = list.get(pos);
//
//        h.company.setText(d.getCompanyName());
//        h.eligibility.setText("Eligibility: " + d.getEligibility());
//
//        h.formLink.setOnClickListener(v -> openLink(d.getFormLink()));
//        h.viewFile.setOnClickListener(v -> openFile(d.getFileUrl()));
//    }
//
//    private void openLink(String link) {
//        if (!link.startsWith("http")) link = "https://" + link;
//        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
//    }
//
////    private void openFile(String url) {
////        try {
////            Uri uri = Uri.parse(url);
////
////            String fileName = uri.getLastPathSegment();
////            if (fileName.contains("?")) {
////                fileName = fileName.substring(0, fileName.indexOf("?"));
////            }
////
////            DownloadManager.Request request =
////                    new DownloadManager.Request(uri);
////
////            request.setTitle(fileName);
////            request.setDescription("Downloading file...");
////            request.setNotificationVisibility(
////                    DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
////            );
////
////            request.setAllowedOverMetered(true);
////            request.setAllowedOverRoaming(true);
////
////            request.setDestinationInExternalPublicDir(
////                    Environment.DIRECTORY_DOWNLOADS,
////                    fileName
////            );
////
////            DownloadManager manager =
////                    (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
////
////            manager.enqueue(request);
////
////            Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show();
////
////        } catch (Exception e) {
////            Toast.makeText(context, "Download failed", Toast.LENGTH_LONG).show();
////            e.printStackTrace();
////        }
////    }
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
//        TextView company, eligibility, formLink, viewFile;
//
//        ViewHolder(View v) {
//            super(v);
//            company = v.findViewById(R.id.txtCompany);
//            eligibility = v.findViewById(R.id.txtEligibility);
//            formLink = v.findViewById(R.id.txtFormLink);
//            viewFile = v.findViewById(R.id.txtViewFile);
//        }
//    }
//}
package com.example.finalyearproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentDriveAdapter extends RecyclerView.Adapter<StudentDriveAdapter.ViewHolder> {

    Context context;
    ArrayList<DriveModel> list;

    public StudentDriveAdapter(Context context, ArrayList<DriveModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_drive_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DriveModel model = list.get(position);

        holder.tvCompany.setText(model.companyName);
        holder.tvEligibility.setText(model.eligibility);
        holder.tvFormLink.setText("Open File");

        holder.tvFormLink.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(model.fileUrl));
            context.startActivity(intent);
        });
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
