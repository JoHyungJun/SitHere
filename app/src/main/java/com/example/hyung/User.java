package com.example.hyung;

import android.widget.ImageView;

public class User {
    String userID;
    String userPassword;
    String userName;
    String userAge;
    String userStatus;

    /*
    ImageView userPermission;

    public ImageView getUserPermission() {
        return userPermission;
    }

    public void setImageView(ImageView userPermission) {
        this.userPermission = userPermission;
    }

     */

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public User(String userID, String userPassword, String userName, String userAge, String userStatus /*, ImageView userPermission*/) {
        this.userID = userID;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userAge = userAge;
        this.userStatus = userStatus;
        //this.userPermission = userPermission;
    }
}
