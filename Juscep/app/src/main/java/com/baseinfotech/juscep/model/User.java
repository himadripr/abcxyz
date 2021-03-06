package com.baseinfotech.juscep.model;

public class User {
    private String id;
    private String mobileNumber;
    private UserType userType;

    public User() {

    }

    public User(String id, String mobileNumber, UserType userType) {
        this.id = id;
        this.mobileNumber = mobileNumber;
        this.userType = userType;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
