package com.baseinfotech.juscep.model;

public class User {
    private String name;
    private String mobileNumber;
    private UserType userType;

    public User() {

    }

    public User(String name, String mobileNumber, UserType userType) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.userType = userType;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
