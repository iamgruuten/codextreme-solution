package com.example.appdevin.myapplication.Class;

public class user {

    public String uid;
    public String name;
    public int points;
    public String contact;

    public user() {
    }

    public user(String uid, String name, int points, String contact) {
        this.uid = uid;
        this.name = name;
        this.points = points;
        this.contact = contact;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
