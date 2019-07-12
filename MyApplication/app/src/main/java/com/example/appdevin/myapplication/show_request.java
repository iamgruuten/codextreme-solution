package com.example.appdevin.myapplication;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class show_request extends AppCompatActivity {
//User come to view request
    ImageView back;
    public static DatabaseReference mPostReference;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_request);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //GET DATA OF REQUEST


        public void GetData()
        {
            recyclerView.setAdapter(null);
            // Ensure that there is connectivity
            try {
                // Gets Firebase data
                mPostReference = FirebaseDatabase.getInstance().getReference("Accidents");
                mPostReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            for (DataSnapshot PS : postSnapshot.getChildren()) {
                                GetAccidentData data = PS.getValue(GetAccidentData.class);
                                Login.GAD.add(data);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            catch (Exception e)
            {
                Toast.makeText(AccidentDetails.this, "Connectivity error", Toast.LENGTH_SHORT);
                Log.d("Refresh", e.getMessage());
            }
            // Does not appear immediately, so it requires a delay
            countDownTimer = new CountDownTimer(3000, 1) {
                @Override
                public void onTick(long millisUntilFinished) {
                    swipeRefreshLayout.setRefreshing(true);
                    // If data is loaded
                    if(Login.GAD.size() != 0) {
                        // Set data on adapter
                        Sort(ascend, distanceS, dateS);
                        swipeRefreshLayout.setRefreshing(false);
                        countDownTimer.cancel();
                    }
                }
                @Override
                public void onFinish() {
                    Toast.makeText(AccidentDetails.this, "Data did not load on time", Toast.LENGTH_LONG);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }.start();
        }


    }
}
