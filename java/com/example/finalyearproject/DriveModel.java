package com.example.finalyearproject;

public class DriveModel {

    public String id;
    public String companyName;
    public String eligibility;
    public String formLink;
    public String fileUrl;
    public long timestamp;

    // Required empty constructor for Firebase
    public DriveModel() {
    }

    public DriveModel(String id, String companyName, String eligibility,
                      String formLink, String fileUrl, long timestamp) {
        this.id = id;
        this.companyName = companyName;
        this.eligibility = eligibility;
        this.formLink = formLink;
        this.fileUrl = fileUrl;
        this.timestamp = timestamp;
    }
}
