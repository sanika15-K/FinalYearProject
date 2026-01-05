//package com.example.finalyearproject;
//
//public class HelperClass {
//
//    String name, email, username, password;
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public HelperClass(String name, String email, String username, String password) {
//        this.name = name;
//        this.email = email;
//        this.username = username;
//        this.password = password;
//    }
//
//    public HelperClass() {
//    }
//}


package com.example.finalyearproject;

public class HelperClass {

    public String name, email, username,
            password, uec, branch;

    public HelperClass() {
    }

    public HelperClass(String name, String email,
                      String username, String password,
                      String uec, String branch) {

        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.uec = uec;
        this.branch = branch;
    }
}
