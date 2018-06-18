package com.stokey.androidunittest.model;

/**
 * Created by stokey on 2018/6/17.
 */

public class User {
    private long userId;
    private String userName;
    private String phone;
    private boolean sex;

    public  User(long userId, String userName, String phone, boolean sex) {
        this.userId = userId;
        this.userName = userName;
        this.phone = phone;

        this.sex = sex;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{userId:\'"
                + userId +"\',userName:\'"
                + userName +"\',phone:\'"
                + phone +"\',sex:\'"
                + (sex ? "man" : "woman") +"\'}";
    }
}
