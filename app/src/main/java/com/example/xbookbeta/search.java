package com.example.xbookbeta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    SearchView searchView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);



        MyBaseAdapter gridadptr = new MyBaseAdapter(this,books);


        key=null ;
        books.clear();
        gridadptr.notifyDataSetChanged();
        gridView = findViewById(R.id.gridid);
        gridView.setAdapter(gridadptr);
        searchView=findViewById(R.id.searchView);
        gridadptr.notifyDataSetChanged();
        AbsListView.OnScrollListener listen = new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem + visibleItemCount >= totalItemCount){
                    // Toast.makeText(view.getContext(), "end", Toast.LENGTH_SHORT).show();
                    if (!isloading){

                        isloading=true ;
                        gridadptr.notifyDataSetChanged();



                    }                }
            }
        } ;
 gridView.setOnScrollListener(listen);
 addelements(gridadptr);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
             books.clear();
                gridView.setOnScrollListener(null);
                gridadptr.notifyDataSetChanged();
                ProgressDialog wait = new ProgressDialog(search.this);
                wait.setTitle("wait");
                wait.setMessage("wait");
                wait.show();
                FirebaseFirestore.getInstance().collection("books").whereEqualTo("title" , query).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        books.clear();
                        gridadptr.notifyDataSetChanged();
                        for(DocumentSnapshot dc : task.getResult()){
                            books.add(
                                    new
                                            onebook(dc.getId() , dc.getString("image") ,dc.getString("title") , dc.getString("categorie") , dc.getDouble("lat")  , dc.getDouble("lng") )) ;
                        }
                        gridadptr.notifyDataSetChanged();
wait.dismiss();
                        if(books.isEmpty()) Toast.makeText(search.this, "there is no results", Toast.LENGTH_SHORT).show();

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //    adapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    public void addelements(MyBaseAdapter rva){

        get().addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (!isloading) {books.clear();}
                for (DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType() == DocumentChange.Type.ADDED){
                        books.add(
                                new
                                        onebook(dc.getDocument().getId() , dc.getDocument().getString("image") ,dc.getDocument().getString("title") , dc.getDocument().getString("categorie") , dc.getDocument().getDouble("lat")  , dc.getDocument().getDouble("lng"))) ;
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