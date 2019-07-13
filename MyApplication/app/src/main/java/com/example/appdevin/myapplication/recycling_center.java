package com.example.appdevin.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.appdevin.myapplication.Class.Reward;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.MapFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class recycling_center extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private Button acheievment_page;
    private Button request_page;
    private Button request_page_view;
    private TextView points;
    ImageView qr, redeem;

    ArrayList<recycleCenterData> list;

    private String TAG = "Recycling ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycling_center);

        //Obtain user points
        points = (TextView) findViewById(R.id.score_board);
        Log.i(TAG, "onCreate: "+Login.User.getPoints());
        points.setText(String.valueOf(Login.User.getPoints()));

        //qr
        qr = findViewById(R.id.qr_code);
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(recycling_center.this, BarcodeCaptureActivity.class);
                startActivity(intent);
                Toast.makeText(recycling_center.this, "Redeem score", Toast.LENGTH_SHORT).show();
            }
        });

        //redeem
        redeem = findViewById(R.id.trophy);
        redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(recycling_center.this, Reward.class);
                startActivity(intent);
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //To set the orientation to portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        recycleCenterData recycleCenterData = new recycleCenterData();
       list = recycleCenterData.list();


        acheievment_page = findViewById(R.id.achievement_page);
        request_page = findViewById(R.id.help_view);
        request_page_view = findViewById(R.id.lend_a_hand);

        request_page_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent req_view = new Intent(recycling_center.this, show_request.class);
                startActivity(req_view);
            }
        });

        acheievment_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent achm = new Intent(recycling_center.this, leaderboard.class);
                startActivity(achm);
            }
        });

        request_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent req = new Intent(recycling_center.this, request_activity.class);
                startActivity(req);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Toast.makeText(this, "Map ready", Toast.LENGTH_SHORT).show();
        // Add a marker in Sydney and move the camera

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.setBuildingsEnabled(false);

        MyLocationIndicate();



    }

    /*--------------------------Blue Dot------------------------*/
    //To indicate the current location
    private void MyLocationIndicate() {

        //Checking for Permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //This a Api calling to activate the blue dot
            mMap.setMyLocationEnabled(true);
            for (recycleCenterData rc:list) {
                marker(rc.getLat(), rc.getLng());
            }
        } else {
            // Show rationale and request permission.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    /*-----------------------------Add marker----------------------------*/
    private void marker(Double lat, Double lon){
        LatLng latLng = new LatLng(lon, lat);
        mMap.addMarker(new MarkerOptions().position(latLng).title("Recycling Center")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        Toast.makeText(this, latLng.toString(), Toast.LENGTH_SHORT).show();

        // Creating a marker
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting the position for the marker
        markerOptions.position(latLng);

        // Setting the title for the marker.
        // This will be displayed on taping the marker
        markerOptions.title(latLng.latitude + " : " + latLng.longitude);

        // Animating to the touched position
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        // Placing a marker on the touched position
        mMap.addMarker(markerOptions);

    }

}
