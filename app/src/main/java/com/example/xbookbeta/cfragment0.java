package com.example.xbookbeta;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class cfragment0 extends Fragment {


    RecyclerView rv ;
    Boolean end = false ;
    booksadapter rva =  new booksadapter(books); ;
    Boolean isloading = false ;
    // String key = null ;
    ProgressBar prgrsbr;
    DocumentSnapshot key = null ;
    public static ArrayList<onebook> books = new ArrayList<>() ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_cfragment0, container, false);



        key=null ;

        books.clear();
        rva.notifyItemRangeRemoved(0 ,books.size());
        rv = v.findViewById(R.id.recyclerView);
        prgrsbr = v.findViewById(R.id.prgrsbrid);
        rv.setAdapter(rva);
        rv.setHasFixedSize(true);
        rv.setLayoutManager( new LinearLayoutManager(v.getContext()));


        addelements();

        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(new LinearLayoutManager(v.getContext())) {
            @Override
            public void onScrolledToEnd() {
                Toast.makeText(v.getContext(), "end", Toast.LENGTH_SHORT).show();
                if (!isloading){

                    isloading=true ;
                    addelements();



                }
            }
        });

































        return v ;
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
                                        onebook(dc.getDocument().getString("id") , dc.getDocument().getString("image") ,dc.getDocument().getString("title") , dc.getDocument().getString("categorie") , dc.getDocument().getDouble("lat")  , dc.getDocument().getDouble("lng"))) ;
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

    /*    final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                prgrsbr.setVisibility(View.INVISIBLE);            }
        }, 700);*/


    }













    public Query get(){
        if (key== null ) {

            return FirebaseFirestore.getInstance().collection("books").limit(5) ;
        }
        else {
            return FirebaseFirestore.getInstance().collection("books").startAfter(key).limit(5);
        }
    }




}