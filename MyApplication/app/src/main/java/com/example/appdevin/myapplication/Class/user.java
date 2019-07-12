package com.example.appdevin.myapplication.Class;

public class user {

    public String UID;
    public String Name;
    public int points;
    public String Contact;

    public user(String UID, String name, int points, String contact) {
        this.UID = UID;
        Name = name;
        this.points = points;
        Contact = contact;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }
}
