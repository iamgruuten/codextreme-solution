package com.example.appdevin.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class show_request extends AppCompatActivity {

    public String TAG = "Request";

    //User come to view request
    ImageView back;
    public static DatabaseReference mPostReference;
    RecyclerView recyclerView;
    List<request_connector> data; //details needed for each card

    DatabaseReference  Dref= FirebaseDatabase.getInstance().getReference("Quests");

    StorageReference storageRef = FirebaseStorage.getInstance().getReference("Quest_Photo");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static request_connector request;
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

        final ArrayList<request_connector> requets = new ArrayList<>();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    request = ds.getValue(request_connector.class);
                    requets.add(request);
                }

                // Create adapter passing in the sample user data
                QuestsAdapter adapter = new QuestsAdapter(requets);
                // Attach the adapter to the recyclerview to populate items
                recyclerView.setAdapter(adapter);
                // Set layout manager to position the items
                recyclerView.setLayoutManager(new LinearLayoutManager(show_request.this));
                // That's all!


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        Dref.addValueEventListener(valueEventListener);


    }


    public class QuestsAdapter extends RecyclerView.Adapter<show_request.QuestsAdapter.ViewHolder>{
        private ArrayList<request_connector> mRequests;
        public QuestsAdapter(ArrayList<request_connector> requets) {mRequests = requets; }

        @NonNull
        @Override
        public QuestsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View contactView = inflater.inflate(R.layout.request_cardview, parent, false);

            // Return a new holder instance
            ViewHolder viewHolder = new ViewHolder(contactView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull final QuestsAdapter.ViewHolder holder, int position) {

            final request_connector request_connector = mRequests.get(position);

            TextView txtName = holder.txtName;
            txtName.setText(request_connector.getName());
            holder.txtDes.setText(request_connector.getDescription());

            storageRef.child(request_connector.imageKey).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(show_request.this).load(uri.toString()).into(holder.imgRecycle);

                    Log.i(TAG, "onSuccess: "+request_connector.getName());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "onFailure: "+request_connector.getName()+e.getLocalizedMessage() );

                }
            });


        }

        @Override
        public int getItemCount() {
            return mRequests.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            public TextView txtName,txtDes;
            public ImageView imgRecycle;


            public ViewHolder(View itemView) {

                super(itemView);

                txtName = itemView.findViewById(R.id.leaderboard_name);
                txtDes =itemView.findViewById(R.id.description_show);
                imgRecycle = itemView.findViewById(R.id.picture_recycle);



            }
        }
    }
}
