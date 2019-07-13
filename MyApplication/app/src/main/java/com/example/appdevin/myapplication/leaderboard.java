package com.example.appdevin.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appdevin.myapplication.Class.user;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class leaderboard extends AppCompatActivity {

    DatabaseReference Dref= FirebaseDatabase.getInstance().getReference("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        final RecyclerView rvHelpRecycler = findViewById(R.id.leaderboard_recyclerview);


        final ArrayList<user> users= new ArrayList<>();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    user User = ds.getValue(user.class);
                    users.add(User);
                }

                for (int i =0; i < users.size(); i++){
                    for (int a = 0; a < users.size(); a++) {
                        if (users.get(i).getPoints() > users.get(a).getPoints()) {
                            user tempValue = users.get(a);
                            users.set(a, users.get(i));
                            users.set(i, tempValue);
                        }
                    }
                }


                // Create adapter passing in the sample user data
                leaderboardAdapter adapter = new leaderboardAdapter(users);
                // Attach the adapter to the recyclerview to populate items
                rvHelpRecycler.setAdapter(adapter);
                // Set layout manager to position the items
                rvHelpRecycler.setLayoutManager(new LinearLayoutManager(leaderboard.this));
                // That's all!

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        Dref.addValueEventListener(valueEventListener);


    }


    public class leaderboardAdapter extends RecyclerView.Adapter<leaderboard.leaderboardAdapter.ViewHolder>{
        private ArrayList<user> mRequests;

        public leaderboardAdapter(ArrayList<user> requets) {mRequests = requets; }

        @NonNull
        @Override
        public leaderboard.leaderboardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View contactView = inflater.inflate(R.layout.leaderboard_cardview, parent, false);

            // Return a new holder instance
            leaderboard.leaderboardAdapter.ViewHolder viewHolder = new leaderboard.leaderboardAdapter.ViewHolder(contactView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            user Users = mRequests.get(position);

            holder.txtName.setText(Users.getName());
            holder.txtScore.setText("Points: " + String.valueOf(Users.getPoints()));

            if(position > 2){
                holder.Crown.setImageDrawable(null);
            }

            if(position == 0){
                holder.mCardView.setCardBackgroundColor(getResources().getColor(R.color.ShinyGold));
            }
            if(position == 1){
                holder.mCardView.setCardBackgroundColor(getResources().getColor(R.color.Sliver));
            }
            if(position == 2){
                holder.mCardView.setCardBackgroundColor(getResources().getColor(R.color.Bronze));
            }




        }



        @Override
        public int getItemCount() {
            return mRequests.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            public TextView txtName,txtScore;
            public ImageView Crown;
            public CardView mCardView;


            public ViewHolder(View itemView) {

                super(itemView);

                txtName = itemView.findViewById(R.id.leaderboard_name);
                txtScore =itemView.findViewById(R.id.textViewScore);
                Crown = itemView.findViewById(R.id.crown);
                mCardView = itemView.findViewById(R.id.DesCardView);




            }
        }
    }
}
