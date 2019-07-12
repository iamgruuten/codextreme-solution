package com.example.appdevin.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appdevin.myapplication.Class.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    private Button loginbtn,create;
    private EditText nme, pass, contact;
    private String nmee , passw, strcontact;
    private CheckBox rmbMe;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private user mUser;
    DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginbtn = findViewById(R.id.loginbtn);
        nme = findViewById(R.id.txt_username);
        pass = findViewById(R.id.txt_password);
        contact = findViewById(R.id.txt_number);

        create = findViewById(R.id.btnCreate);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nmee = nme.getText().toString();
                passw = pass.getText().toString();
                strcontact = contact.getText().toString();

                mAuth.signInWithEmailAndPassword(nmee,passw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            mUser = new user(mAuth.getCurrentUser().getUid(),"John",0,strcontact);

                            //Set data
                            mDatabaseReference.setValue(mUser);

                            Intent intent = new Intent(Login.this, recycling_center.class);
                            startActivity(intent);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        rmbMe = findViewById(R.id.Remember_Me);

        rmbMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                nme.setText("jeya@gmail.com");
                pass.setText("12345678");
            }
        });


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),recycling_center.class));
                finish();
            }
        });
    }
}
