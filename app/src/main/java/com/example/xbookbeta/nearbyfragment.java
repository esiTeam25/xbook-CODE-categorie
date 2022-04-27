package com.example.xbookbeta;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import uk.co.mgbramwell.geofire.android.GeoFire;
import uk.co.mgbramwell.geofire.android.model.Distance;
import uk.co.mgbramwell.geofire.android.model.DistanceUnit;
import uk.co.mgbramwell.geofire.android.model.QueryLocation;


public class nearbyfragment extends Fragment {
    /*   RecyclerView rv ;
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
   */
    MapView mapView;
    GoogleMap map;
    public static ArrayList<onebook> books = new ArrayList<>() ;

    private GeoFire geoFire = new GeoFire(FirebaseFirestore.getInstance().collection("books"));
    QueryLocation queryLocation = QueryLocation.fromDegrees(FirstActivity.locationToUpload.latitude, FirstActivity.locationToUpload.longitude);
    Distance searchDistance = new Distance(1000.0, DistanceUnit.KILOMETERS);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nearbyfragment, container, false);
        //  key = null;
/*
        rv = view.findViewById(R.id.rvid);
        rv.setAdapter(rva);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rv.setHasFixedSize(true);
*/


        mapView = (MapView) v.findViewById(R.id.mapid);
        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        map.getUiSettings().setMyLocationButtonEnabled(false);


        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(this.getActivity());

        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.1, -87.9), 10);
        map.animateCamera(cameraUpdate);









        return v ;
    }




/*
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








*/





    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }





}