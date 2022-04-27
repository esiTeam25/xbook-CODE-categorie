package com.example.xbookbeta;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.GridView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class search extends AppCompatActivity {
    Boolean isloading = false ;
    private GridView gridView ;
    public static ArrayList<onebook> books = new ArrayList<>() ;
    DocumentSnapshot key = null ;
    Boolean _areLecturesLoaded =false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);



        CourseGVAdapter gridadptr = new CourseGVAdapter(search.this,books );


        key=null ;

        books.clear();
        gridadptr.notifyDataSetChanged();
        gridView = findViewById(R.id.gridid);
        gridView.setAdapter(gridadptr);
        addelements(gridadptr);









        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem + visibleItemCount >= totalItemCount){
                    // Toast.makeText(view.getContext(), "end", Toast.LENGTH_SHORT).show();
                    if (!isloading){

                        isloading=true ;
                        addelements(gridadptr);



                    }                }
            }
        });

    }

    public void addelements(CourseGVAdapter rva){

        get().addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (!isloading) {books.clear();}
                for (DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType() == DocumentChange.Type.ADDED){
                        books.add(
                                new
                                        onebook(dc.getDocument().getString("id") , dc.getDocument().getString("image") ,dc.getDocument().getString("title") , dc.getDocument().getString("categorie") , dc.getDocument().getDouble("lat")  , dc.getDocument().getDouble("lng"))) ;
                        key =  dc.getDocument();
                    }



                }
                rva.notifyDataSetChanged();
                if(!isloading){
                    gridView.smoothScrollToPosition(0);}
                isloading=false ;

                //  rv.smoothScrollToPosition(0);
            }

        });



    }













    public Query get(){
        if (key== null ) {

            return FirebaseFirestore.getInstance().collection("books").limit(20) ;
        }
        else {
            return FirebaseFirestore.getInstance().collection("books").startAfter(key).limit(20);
        }
    }




}