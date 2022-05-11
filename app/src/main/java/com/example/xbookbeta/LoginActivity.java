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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    TextView email ;
    TextView password ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.emailid);
        password = findViewById(R.id.passwordid);
        ProgressDialog wait = new ProgressDialog(this);
        wait.setTitle("wait");
        wait.setMessage("wait");
        findViewById(R.id.signupid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this , Signup.class));
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
    }
}