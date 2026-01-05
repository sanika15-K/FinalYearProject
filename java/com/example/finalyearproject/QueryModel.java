//package com.example.finalyearproject;
//
//public class QueryModel {
//
//    public String queryId;
//    public String queryText;
//    public String adminResponse;
//    public int priority;
//    public long timestamp;
//
//    // 🔴 REQUIRED empty constructor
//    public QueryModel() { }
//
//    public QueryModel(String queryId, String queryText,
//                      String adminResponse, int priority, long timestamp) {
//        this.queryId = queryId;
//        this.queryText = queryText;
//        this.adminResponse = adminResponse;
//        this.priority = priority;
//        this.timestamp = timestamp;
//    }
//}


package com.example.finalyearproject;

public class QueryModel {

    public String queryId;
    public String queryText;
    public String adminResponse;
    public int priority;
    public long timestamp;

    // 🔔 IMPORTANT: to avoid multiple notifications
    public boolean notified;

    // Required empty constructor
    public QueryModel() { }

    public QueryModel(String queryId, String queryText,
                      String adminResponse, int priority, long timestamp) {
        this.queryId = queryId;
        this.queryText = queryText;
        this.adminResponse = adminResponse;
        this.priority = priority;
        this.timestamp = timestamp;
        this.notified = false;
    }
}
