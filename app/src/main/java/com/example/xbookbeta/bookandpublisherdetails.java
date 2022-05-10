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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ortiz.touchview.TouchImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class bookandpublisherdetails extends AppCompatActivity {
static  String bookimage ;//= null ;
    TouchImageView bookImage;
    String id ;
    static  String profileimage ;
    CircleImageView profileprofile ;
    public static String key ;//=null ;
    public static String name;// = null;
    TextView namee ;

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

        if (bookimage!=null && name != null) {






            DatabaseReference account = FirebaseDatabase.getInstance().getReference().child("users")
                    .child(getIntent().getExtras().getString("id"));
            account.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    namee.setText(snapshot.child("name").getValue().toString());
                    String profileimagefor = snapshot.child("image").getValue().toString();
                    if ( !snapshot.child("image").getValue().toString().equals("" ) ) {
                        byte[] decodedString = Base64.decode(snapshot.child("image").getValue().toString(), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        profileprofile.setImageBitmap(decodedByte);
                        //   holder.profilename.setText(snapshot.child("name").getValue().toString());
wait.dismiss();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });





























            if (getIntent().getExtras().getString("id").equals(FirebaseAuth.getInstance().getUid().toString())) {
                findViewById(R.id.sendmessageid).setVisibility(View.INVISIBLE);
                findViewById(R.id.locationid).setVisibility(View.INVISIBLE);
                findViewById(R.id.phonecallid).setVisibility(View.INVISIBLE);
            }
            byte[] decodedString2 = Base64.decode(bookimage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString2, 0, decodedString2.length);
            bookImage.setImageBitmap(decodedByte) ;

            findViewById(R.id.sendmessageid).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(), chat.class);
                    if(getIntent().getExtras()!=null){
                        i.putExtra("id", getIntent().getExtras().getString("id"));}
                    else{i.putExtra("id", id);}
                    i.putExtra("key" , key) ;
                    i.putExtra("x", "2");
                    startActivity(i);
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
            findViewById(R.id.phonecallid).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:0558585327"));
                    startActivity(intent);
                }
            });
        }






















        else {



















            findViewById(R.id.locationid).setVisibility(View.INVISIBLE);

            FirebaseFirestore.getInstance().collection("books").document(key).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    id = task.getResult().getString("id");
                    String imagestr = task.getResult().getString("image");

                        byte[] decodedString = Base64.decode(imagestr, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        bookImage.setImageBitmap(decodedByte);

                        if (id.equals(FirebaseAuth.getInstance().getUid().toString())) {
                            findViewById(R.id.sendmessageid).setVisibility(View.INVISIBLE);
                            findViewById(R.id.phonecallid).setVisibility(View.INVISIBLE);
                        }

                    DatabaseReference account = FirebaseDatabase.getInstance().getReference().child("users")
                            .child(id);
                    account.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            namee.setText(snapshot.child("name").getValue().toString());
                            String profileimagefor = snapshot.child("image").getValue().toString();
                            if ( !snapshot.child("image").getValue().toString().equals("" ) ) {
                                byte[] decodedString = Base64.decode(snapshot.child("image").getValue().toString(), Base64.DEFAULT);
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                profileprofile.setImageBitmap(decodedByte);
                             //   holder.profilename.setText(snapshot.child("name").getValue().toString());
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










































        }





        }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bookimage=null ;
    name = null;
        id =null;
        profileimage=null ;
        profileprofile=null ;
        key =null;

    }
}