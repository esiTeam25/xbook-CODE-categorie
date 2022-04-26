package com.example.xbookbeta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import uk.co.mgbramwell.geofire.android.GeoFire;
import uk.co.mgbramwell.geofire.android.model.Distance;
import uk.co.mgbramwell.geofire.android.model.DistanceUnit;
import uk.co.mgbramwell.geofire.android.model.QueryLocation;


public class locationbooks extends AppCompatActivity {
    RecyclerView rv ;
    Boolean end = false ;
    booksadapter rva =  new booksadapter(books); ;
    Boolean isloading = false ;

    ProgressBar prgrsbr;
    DocumentSnapshot key = null ;
    public static ArrayList<onebook> books = new ArrayList<>() ;

    private GeoFire geoFire = new GeoFire(FirebaseFirestore.getInstance().collection("books"));
    QueryLocation queryLocation = QueryLocation.fromDegrees(FirstActivity.locationToUpload.latitude, FirstActivity.locationToUpload.longitude);
    Distance searchDistance = new Distance(1.0, DistanceUnit.KILOMETERS);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationbooks);





            key = null;

            books.clear();
            rva.notifyItemRangeRemoved(0, books.size());
            rv = findViewById(R.id.recyclerView);
            prgrsbr = findViewById(R.id.prgrsbrid);
            rv.setAdapter(rva);
            rv.setHasFixedSize(true);
            rv.setLayoutManager(new LinearLayoutManager(locationbooks.this));

            addelements();

            rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(new LinearLayoutManager(locationbooks.this)) {
                @Override
                public void onScrolledToEnd() {
                    Toast.makeText(locationbooks.this, "end", Toast.LENGTH_SHORT).show();
                    if (!isloading) {

                        isloading = true;
                        addelements();


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