package com.example.xbookbeta;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

import uk.co.mgbramwell.geofire.android.GeoFire;
import uk.co.mgbramwell.geofire.android.model.Distance;
import uk.co.mgbramwell.geofire.android.model.DistanceUnit;
import uk.co.mgbramwell.geofire.android.model.QueryLocation;


public class nearbyfragment extends Fragment {
    RecyclerView rv ;
    Boolean end = false ;
    booksadapter rva =  new booksadapter(books); ;
    Boolean isloading = false ;
    DocumentSnapshot key = null ;
    public static ArrayList<onebook> books = new ArrayList<>() ;

    private GeoFire geoFire = new GeoFire(FirebaseFirestore.getInstance().collection("books"));
    QueryLocation queryLocation = QueryLocation.fromDegrees(FirstActivity.locationToUpload.latitude, FirstActivity.locationToUpload.longitude);
    Distance searchDistance = new Distance(1000.0, DistanceUnit.KILOMETERS);
    EndlessRecyclerOnScrollListener listen = new EndlessRecyclerOnScrollListener(new LinearLayoutManager(getContext())) {
        @Override
        public void onScrolledToEnd() {
            if (!isloading) {
                isloading = true;
                addelements();


            }
        }
    } ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchfragment, container, false) ;
      //  key = null;

        rv = view.findViewById(R.id.rvid);
        rv.setAdapter(rva);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rv.setHasFixedSize(true);

        return view ;
    }





    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Toast.makeText(getActivity(), "here", Toast.LENGTH_SHORT).show();

            addelements();
            rv.addOnScrollListener(listen);

        }else{
            // fragment is no longer visible
        }
    }





    public void addelements(){
        get().addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
               // if (!isloading) {books.clear();}
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
               /* if(!isloading){
                    rv.smoothScrollToPosition(0);}*/
                isloading=false ;

            }

        });


    }













    public Query get(){
        if(key==null){
            return geoFire.query()
                    .whereNearTo(queryLocation, searchDistance).limit(5)
                    .build() ;

        }else{
            return geoFire.query()
                    .whereNearTo(queryLocation, searchDistance)
                    .startAfter(key).limit(5).build() ;
        }

    }


















}