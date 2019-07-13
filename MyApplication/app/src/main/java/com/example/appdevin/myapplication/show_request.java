package com.example.appdevin.myapplication;

import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class show_request extends AppCompatActivity {
    //User come to view request
    ImageView back;
    public static DatabaseReference mPostReference;
    RecyclerView recyclerView;
    List<request_connector> data; //details needed for each card

    DatabaseReference  Dref= FirebaseDatabase.getInstance().getReference("Report Accident");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_request);
        recyclerView = findViewById(R.id.help_recycler);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //GET DATA OF REQUEST

        // Lookup the recyclerview in activity layout
        RecyclerView rvHelpRecycler = findViewById(R.id.help_recycler);

        


        // Create adapter passing in the sample user data
         QuestsAdapter adapter = new QuestsAdapter(contacts);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        // That's all!
        
    }


    public class QuestsAdapter extends RecyclerView.Adapter<QuestsAdapter.ViewHolder>{

        @NonNull
        @Override
        public QuestsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull QuestsAdapter.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        public class ViewHolder extends RecyclerView.ViewHolder{


            public ViewHolder(View itemView) {
                super(itemView);


            }
        }
    }
}
