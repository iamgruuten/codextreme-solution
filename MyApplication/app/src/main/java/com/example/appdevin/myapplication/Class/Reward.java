package com.example.appdevin.myapplication.Class;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appdevin.myapplication.Login;
import com.example.appdevin.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class Reward extends AppCompatActivity {

    int points = 0;
    CardView bk,bt,mc,kfc;
    ImageView back;
    TextView score;
    ArrayList<Integer> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        list.add(1000);
        list.add(500);
        list.add(200);
        list.add(300);

        score = findViewById(R.id.Points);
        back = findViewById(R.id.back);
        bk = findViewById(R.id.bk);
        bt = findViewById(R.id.bbt);
        mc = findViewById(R.id.mc);
        kfc = findViewById(R.id.kfc);

        score.setText(String.valueOf(Login.User.getPoints()));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        bk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                points = list.get(Integer.parseInt(v.getContentDescription().toString()));
                updatescore(points);
            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                points = list.get(Integer.parseInt(v.getContentDescription().toString()));
                updatescore(points);


            }
        });

        mc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                points = list.get(Integer.parseInt(v.getContentDescription().toString()));
                updatescore(points);


            }
        });

        kfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                points = list.get(Integer.parseInt(v.getContentDescription().toString()));
                updatescore(points);

            }
        });





    }
    public void updatescore(int score){
        Login.User.setPoints(score);
    }
}
