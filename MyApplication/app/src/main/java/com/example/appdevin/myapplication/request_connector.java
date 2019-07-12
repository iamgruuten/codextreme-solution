package com.example.appdevin.myapplication;

import android.net.Uri;

public class request_connector {

    public String name;
    public String postalcode;
    public int phone_number;
    Uri image_of_recycled_items;

    public String getName() {
        return name;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public int getPhone_number() {
        return phone_number;
    }

    public Uri getImage_of_recycled_items() {
        return image_of_recycled_items;
    }


}
