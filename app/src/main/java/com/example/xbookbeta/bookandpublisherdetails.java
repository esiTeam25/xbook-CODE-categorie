package com.example.xbookbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ortiz.touchview.TouchImageView;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class bookandpublisherdetails extends AppCompatActivity {
static  String bookimage ;//= null ;
    TouchImageView bookImage;
    String id ;
    static  String profileimage ;
    CircleImageView profileprofile ;
    public static String key ;//=null ;
    public static String name;// = null;
    TextView namee , desc ;
    public static ArrayList<String> likedBooks = new ArrayList<>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookandpublisherdetails);
        ProgressDialog wait = new ProgressDialog(bookandpublisherdetails.this);
        wait.setTitle("wait");
        wait.setMessage("wait");
        wait.show();
        bookImage = findViewById(R.id.bookImageId);
        profileprofile = findViewById(R.id.profileImageId);
        namee = findViewById(R.id.profileNameid);
        desc = findViewById(R.id.bookDescriptionId);
        findViewById(R.id.like).setVisibility(View.INVISIBLE);
        findViewById(R.id.locationid).setVisibility(View.INVISIBLE);
        findViewById(R.id.sendmessageid).setVisibility(View.INVISIBLE);
        findViewById(R.id.phonecallid).setVisibility(View.INVISIBLE);
        FirebaseFirestore.getInstance().collection("likes"+FirebaseAuth.getInstance().getUid().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(DocumentSnapshot ds :task.getResult().getDocuments()){
                    likedBooks.add(ds.getString("key")) ;
                }


























                FirebaseFirestore.getInstance().collection("books").document(key).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        id = task.getResult().getString("id");
                        String imagestr = task.getResult().getString("image");
                        mapactivity.point = new LatLng(  task.getResult().getDouble("lat") , task.getResult().getDouble("lng"));
                        mapactivity.name = task.getResult().getString("title") ;
                        desc.setText(task.getResult().getString("description"));
                        byte[] decodedString = Base64.decode(imagestr, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        bookImage.setImageBitmap(decodedByte);
                        if (!id.equals(FirebaseAuth.getInstance().getUid().toString()))  if(!likedBooks.contains(key)) findViewById(R.id.like).setVisibility(View.VISIBLE);
                        if (!id.equals(FirebaseAuth.getInstance().getUid().toString())) {
                            findViewById(R.id.locationid).setVisibility(View.VISIBLE);
                            findViewById(R.id.sendmessageid).setVisibility(View.VISIBLE);
                            findViewById(R.id.phonecallid).setVisibility(View.VISIBLE);
                        }

                        DatabaseReference account = FirebaseDatabase.getInstance().getReference().child("users")
                                .child(id);
                        account.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                namee.setText(snapshot.child("name").getValue().toString());
                                if ( !snapshot.child("image").getValue().toString().equals("" ) ) {
                                    byte[] decodedString = Base64.decode(snapshot.child("image").getValue().toString(), Base64.DEFAULT);
                                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                    profileprofile.setImageBitmap(decodedByte);
                                    wait.dismiss();

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });

                findViewById(R.id.sendmessageid).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(view.getContext(), chat.class);

                        i.putExtra("id", id);
                        i.putExtra("key" , key) ;
                        i.putExtra("x", "2");
                        startActivity(i);
                    }
                });


                findViewById(R.id.phonecallid).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:0558585327"));
                        startActivity(intent);
                    }
                });


                findViewById(R.id.locationid).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(getIntent().getExtras()!=null) {
                            startActivity(new Intent(bookandpublisherdetails.this, mapactivity.class).putExtra("id", getIntent().getExtras().getString("id")));
                        }else{
                            startActivity(new Intent(bookandpublisherdetails.this, mapactivity.class).putExtra("id",id));


                        }}
                });


                findViewById(R.id.like).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        findViewById(R.id.like).setVisibility(View.INVISIBLE);

                        HashMap<String, Object> dataaa = new HashMap<>();
                        dataaa.put("key" , key);
                        FirebaseFirestore.getInstance().collection("likes"+FirebaseAuth.getInstance().getUid()).add(dataaa).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {

                                FirebaseFirestore.getInstance().collection("books").document(key).update("likes" , FieldValue.increment(1)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        DatabaseReference account = FirebaseDatabase.getInstance().getReference().child("users")
                                                .child(id);
                                        account.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                long number = (long) snapshot.child("likes").getValue() ;
                                                snapshot.getRef().child("likes").setValue(number+1);
                                                likedBooks.add(key);
                                                Toast.makeText(view.getContext(), "liked", Toast.LENGTH_SHORT).show();

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                });


                            }
                        });

                    }
                });


                wait.dismiss();


            }
        });

profileprofile.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        otherprofile.id = id;
       startActivity(new Intent(bookandpublisherdetails.this, otherprofile.class));
    }
});
























































        }


}