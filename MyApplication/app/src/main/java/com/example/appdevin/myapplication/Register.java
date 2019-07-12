package com.example.appdevin.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private EditText editTextEmail,editTextPassword,editTextConfirm;
    private FirebaseAuth mAuth;
    private Button register;
    private String TAG = "Register.class";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Edittext
        editTextEmail = findViewById(R.id.txt_username);
        editTextPassword = findViewById(R.id.txt_password);
        editTextConfirm = findViewById(R.id.txt_confirm);

        //Buttons
        register = findViewById(R.id.btnRegister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regUser();
            }
        });


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");





    }


    private void regUser() {

        String email = editTextEmail.getText().toString().trim();
        String pass = editTextPassword.getText().toString().trim();
        String confirm = editTextConfirm.getText().toString().trim();

        Log.i(TAG,  String.format("reguser has Email: %s, Password: %s and confirmed Password %s",email,pass,confirm));

        if (TextUtils.isEmpty(email)){
            Toast.makeText(Register.this, "Please fill up the empty fields", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(pass)) {
            Toast.makeText(Register.this, "Please fill up the empty fields", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(confirm)) {
            Toast.makeText(Register.this, "Please fill up the empty fields", Toast.LENGTH_SHORT).show();
            return;
        } else if (!confirm.equals(pass)) {
            Toast.makeText(Register.this, "Password Not Same", Toast.LENGTH_SHORT).show();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "onComplete: You have registered successfully");
//                            Intent intent = new Intent(Register.this, MainActivity.class);
//                            startActivity(intent);
//                            finish();
                        } else {
                            Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_SHORT).show();

                        }
                    }

                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: "+"Registration Failed"+e.getLocalizedMessage());
            }
        });

    }


}
