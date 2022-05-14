package com.example.xbookbeta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class otherprofile extends AppCompatActivity {
    private CircleImageView prflimg;
    private TextView prflname , likes  , bookss;
    RecyclerView rv ;
    public  static  String id ;
    Boolean end = false ;
    profilebooksadapter rva =  new profilebooksadapter(books) ;
    Boolean isloading = false ;
    // String key = null ;
    DocumentSnapshot key = null ;
    public static ArrayList<onebook> books = new ArrayList<>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otherprofile);
        prflimg = findViewById(R.id.profileImageId);
        prflname = findViewById(R.id.profileNameId);
        likes = findViewById(R.id.likesid);
        bookss = findViewById(R.id.booksid);

        ProgressDialog wait = new ProgressDialog(otherprofile.this);
        wait.setTitle("wait");
        wait.setMessage("wait");
        wait.show();


        FirebaseDatabase.getInstance().getReference().child("users").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long num =(long) snapshot.child("books").getValue();
                if (num == 0 ) findViewById(R.id.yourbooksbuttonid).setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference account = FirebaseDatabase.getInstance().getReference().child("users").child( id);
        account.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                prflname.setText(snapshot.child("name").getValue().toString());
                likes.setText("likes : "+snapshot.child("likes").getValue().toString());
                bookss.setText("books :"+ snapshot.child("books").getValue().toString());
                long num =(long) snapshot.child("books").getValue();
                if (num == 0 ) findViewById(R.id.yourbooksbuttonid).setVisibility(View.INVISIBLE);
                if ( !snapshot.child("image").getValue().toString().equals("" ) ) {
                    byte[] decodedString = Base64.decode(snapshot.child("image").getValue().toString(), Base64.DEFAULT);
                    prflname.setText(snapshot.child("name").getValue().toString());
                    prflimg.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
                    wait.dismiss();
                }

                wait.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        findViewById(R.id.yourbooksbuttonid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otherbooks.id = id ;
                startActivity(new Intent( otherprofile.this , otherbooks.class));
            }
        });









        rv = findViewById(R.id.rvid);
        rv.setAdapter(rva);
        rv.setHasFixedSize(true);
        LinearLayoutManager lm = new LinearLayoutManager(otherprofile.this, LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(lm);
        books.clear();

        addelements();


    }



    public void addelements(){





        FirebaseFirestore.getInstance().collection("books").whereEqualTo("id" , id).limit(4).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType() == DocumentChange.Type.ADDED){
                        books.add(
                                new
                                        onebook(  dc.getDocument().getString("image") ,dc.getDocument().getString("title") , dc.getDocument().getString("categorie") , dc.getDocument().getDouble("latitude")  , dc.getDocument().getDouble("longitude"))) ;
                    }



                }
                findViewById(R.id.prgrsbrid).setVisibility(View.INVISIBLE);
                rva.notifyDataSetChanged();
                rva.notifyItemRangeInserted(books.size() , books.size());



                //  rv.smoothScrollToPosition(0);
            }

        });


    }


}