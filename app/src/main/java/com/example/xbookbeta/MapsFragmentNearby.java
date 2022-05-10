package com.example.xbookbeta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import uk.co.mgbramwell.geofire.android.GeoFire;
import uk.co.mgbramwell.geofire.android.model.Distance;
import uk.co.mgbramwell.geofire.android.model.DistanceUnit;
import uk.co.mgbramwell.geofire.android.model.QueryLocation;

public class MapsFragmentNearby extends Fragment implements  GoogleMap.OnMarkerClickListener{



    public static ArrayList<onebook> books = new ArrayList<>() ;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        @Override
        public void onMapReady(GoogleMap googleMap) {
            if(FirstActivity.locationToUpload!=null) {

                LatLng sydney = new LatLng(FirstActivity.locationToUpload.latitude, FirstActivity.locationToUpload.longitude);
                // googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14f));
                 GeoFire geoFire = new GeoFire(FirebaseFirestore.getInstance().collection("books"));
                QueryLocation queryLocation = QueryLocation.fromDegrees(FirstActivity.locationToUpload.latitude, FirstActivity.locationToUpload.longitude);
                Distance searchDistance = new Distance(5.0, DistanceUnit.KILOMETERS);
                geoFire.query()
                        .whereNearTo(queryLocation, searchDistance).limit(20)
                        .build().addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        int i =0 ;

                        for (DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){

                            /*    books.add(

                                        new
                                                onebook(dc.getDocument().getString("id") , dc.getDocument().getString("image") ,dc.getDocument().getString("title") , dc.getDocument().getString("categorie") , dc.getDocument().getDouble("lat")  , dc.getDocument().getDouble("lng"))) ;
                            */
                            }

                            byte[] decodedString2 = Base64.decode(dc.getDocument().getString("lowimage"), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString2, 0, decodedString2.length);
                            googleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(dc.getDocument().getDouble("lat"), dc.getDocument().getDouble("lng")))
                                    .title(dc.getDocument().getString("title")).icon(BitmapDescriptorFactory.fromBitmap(getResizedBitmap(getRoundedCornerBitmap(decodedByte , 10) , 100 , 160))))
                            ;



                            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                @Override
                                public boolean onMarkerClick(Marker marker) {
                                    Intent i = new Intent( getContext() , bookandpublisherdetails.class );
bookandpublisherdetails.key=dc.getDocument().getId();
getContext().startActivity(i);
                                    Toast.makeText(getContext(), "aoihg", Toast.LENGTH_SHORT).show();                                    return false;
                                }
                            });


                        }


                    }

                });

















            }

        }
    };






    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_maps_nearby, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       /* SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);

        }*/
    }







    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {




            if(FirstActivity.locationToUpload==null) {

               AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("this functionality needs GPS autorisation")
                        .setCancelable(true)
                ;
                 AlertDialog alert = builder.create();
                alert.show();
            }else{
                SupportMapFragment mapFragment =
                    (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                if (mapFragment != null) {
                    mapFragment.getMapAsync(callback);

                }


            }

        }else{
            // fragment is no longer visible
        }
    }






    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }




    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }


    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {

        return false;
    }
}