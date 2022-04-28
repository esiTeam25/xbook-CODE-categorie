package com.example.xbookbeta;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class userhistorybooks extends AppCompatActivity {
    RecyclerView rv ;
    Boolean end = false ;
    userbookshistoryadapter rva =  new userbookshistoryadapter(books); ;
    Boolean isloading = false ;
    // String key = null ;
    ProgressBar prgrsbr;
    DocumentSnapshot key = null ;
    public static ArrayList<onebook> books = new ArrayList<>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhistorybooks);
        rv = findViewById(R.id.recyclerView);
        prgrsbr = findViewById(R.id.prgrsbrid);
        rv.setAdapter(rva);
        rv.setHasFixedSize(true);
        LinearLayoutManager lm = new LinearLayoutManager(userhistorybooks.this) ;
        // lm.setReverseLayout(true);
        rv.setLayoutManager(lm);
        books.clear();

        addelements();

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager =  (LinearLayoutManager)  rv.getLayoutManager();
                int items = linearLayoutManager.getItemCount() ;
                int lastitem = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                if(items <lastitem+3 ){
                    if (!isloading){

                        isloading=true ;
                        addelements();



                    }

                }
            }
        });























    }















    public void addelements(){
        prgrsbr.setVisibility(View.VISIBLE);





        get().addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (!isloading) {books.clear();}
                for (DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType() == DocumentChange.Type.ADDED){
                        books.add(
                                new
                                        onebook(  dc.getDocument().getString("image") ,dc.getDocument().getString("title") , dc.getDocument().getString("categorie") , dc.getDocument().getDouble("latitude")  , dc.getDocument().getDouble("longitude"))) ;
                        key =  dc.getDocument();
                    }



                }
                rva.notifyDataSetChanged();
                rva.notifyItemRangeInserted(books.size() , books.size());
                if(!isloading){
                    rv.smoothScrollToPosition(0);}
                isloading=false ;
                prgrsbr.setVisibility(View.INVISIBLE);

                //  rv.smoothScrollToPosition(0);
            }

        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                prgrsbr.setVisibility(View.INVISIBLE);            }
        }, 1500);


    }













    public Query get(){
        if (key== null ) {

            return FirebaseFirestore.getInstance().collection("books").whereEqualTo("id" , FirebaseAuth.getInstance().getCurrentUser().getUid()).limit(5) ;
        }
        else {
            return FirebaseFirestore.getInstance().collection("books").whereEqualTo("id" , FirebaseAuth.getInstance().getCurrentUser().getUid()).startAfter(key).limit(5);
        }
    }












}