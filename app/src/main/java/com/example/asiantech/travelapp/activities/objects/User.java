package com.example.asiantech.travelapp.activities.objects;

/**
 * Created by phuong on 09/04/2017.
 */

public class User {
    private String chatWith;
    private String id;
    private String name;
    private String pass;
    private String phoneNumber;

    public User(String id, String name, String pass, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.pass = pass;
        this.phoneNumber = phoneNumber;
    }

    public User() {
    }

    public User(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User(String chatWith, String id, String name, String pass, String phoneNumber) {
        this.chatWith = chatWith;
        this.id = id;
        this.name = name;
        this.pass = pass;
        this.phoneNumber = phoneNumber;
    }

    public String getChatWith() {
        return chatWith;
    }

    public void setChatWith(String chatWith) {
        this.chatWith = chatWith;
    }
}
