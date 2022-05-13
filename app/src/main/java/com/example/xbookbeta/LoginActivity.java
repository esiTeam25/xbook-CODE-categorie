package com.example.xbookbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    EditText email , semail ;
    EditText password , spassword , sname ;
    ViewFlipper vflipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.emailid);
        semail = findViewById(R.id.signemailid);

        password = findViewById(R.id.passwordid);
        spassword = findViewById(R.id.signpasswordid);
       sname = findViewById(R.id.signnameid);
        ProgressDialog wait = new ProgressDialog(this);
        wait.setTitle("wait");
        wait.setMessage("wait");
        vflipper = findViewById(R.id.viewFlipper);
        vflipper.setInAnimation(this, android.R.anim.slide_in_left);

        findViewById(R.id.movetosignupid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(LoginActivity.this , Signup.class));
                email.setText("");
                password.setText("");
            vflipper.showNext();
            }
        });

    findViewById(R.id.movetologinid).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            semail.setText("");
            spassword.setText("");
            sname.setText("");
            vflipper.showPrevious();

        }
    });











        findViewById(R.id.loginid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wait.show();
                FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                FirebaseFirestore.getInstance().collection(FirebaseAuth.getInstance().getUid().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        for(DocumentSnapshot ds :task.getResult().getDocuments()){
                                            FirstActivity.savedBooks.add(ds.getString("key")) ;
                                        }
                                        wait.dismiss();
                                        startActivity(new Intent(LoginActivity.this , MainActivity.class));
                                        finish();
                                    }
                                });


                            }

                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(LoginActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        wait.dismiss();
                    }
                });

            }
        });

















        findViewById(R.id.signupid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wait.show();
                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(semail.getText().toString() , spassword.getText().toString() )
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                HashMap<String ,Object> data = new HashMap<>() ;
                                data.put("name",sname.getText().toString() );
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
                                        startActivity(new Intent(LoginActivity.this , MainActivity.class));
                                        finish();
                                    }
                                });


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        wait.dismiss();
                    }
                });






            }
        });













    }
}