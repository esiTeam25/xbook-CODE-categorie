package com.example.xbookbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Signup extends AppCompatActivity {
TextView email  ;
TextView password  , name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        email = findViewById(R.id.emailid);
        password = findViewById(R.id.passwordid);
        name = findViewById(R.id.nameid);
        ProgressDialog wait = new ProgressDialog(this);
        wait.setTitle("wait");
        wait.setMessage("wait");

        findViewById(R.id.loginid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.signupid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wait.show();
              FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(email.getText().toString() , password.getText().toString() )
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                HashMap<String ,Object> data = new HashMap<>() ;
                                data.put("name",name.getText().toString() );
                                data.put("image" , "" );
                                data.put("books" , 0);
                                data.put("likes" , 0);
                                data.put("id" , FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                                data.put("location" , new LatLng(0,0));
                                FirebaseDatabase.getInstance().getReference().
                                        child("users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        FirstActivity.savedBooks.clear();
                                        wait.dismiss();
                                        startActivity(new Intent(Signup.this , MainActivity.class));
                                        finish();
                                    }
                                });


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Signup.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        wait.dismiss();
                    }
                });


              /*  FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(email.getText().toString() , password.getText().toString() ).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        HashMap<String ,Object> data = new HashMap<>() ;
                        data.put("name",name.getText().toString() );
                        data.put("image" , "" );
                        data.put("id" , FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                        data.put("latitude" , 0.0);
                        data.put("longitude" , 0.0);
                        FirebaseFirestore.getInstance().collection("users")
                                .document(FirebaseAuth.getInstance().getUid().toString())
                                .set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                startActivity(new Intent(Signup.this , MainActivity.class));
                                finish();
                                wait.dismiss();
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });*/






            }
        });

    }
}