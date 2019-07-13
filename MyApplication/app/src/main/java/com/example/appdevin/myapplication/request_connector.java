package com.example.appdevin.myapplication;

import android.net.Uri;

public class request_connector {

    public String name;
    public String postalcode;
    public String phone_number;
    public String imageKey;

    public request_connector() {
    }

    public request_connector(String name, String postalcode, String phone_number, String imageKey) {
        this.name = name;
        this.postalcode = postalcode;
        this.phone_number = phone_number;
        this.imageKey = imageKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }
}
