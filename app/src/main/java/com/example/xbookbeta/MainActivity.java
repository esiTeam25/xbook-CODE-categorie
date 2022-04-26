package com.example.xbookbeta;

import static android.content.ContentValues.TAG;

import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private Toolbar tlbr ;
    private TabLayout tl ;
    private ViewPager vp ;
    private long pressedTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // tlbr = findViewById(R.id.tlbrid);
        tl = findViewById(R.id.tl);
        vp = findViewById(R.id.vpid);
        vp.setAdapter(new vadapter(getSupportFragmentManager()));
        tl.setupWithViewPager(vp);
if ( FirstActivity.locationToUpload!=null ){
    Toast.makeText(MainActivity.this, FirstActivity.locationToUpload.latitude + "*" + FirstActivity.locationToUpload.latitude, Toast.LENGTH_SHORT).show();
}
vp.setOffscreenPageLimit(1);
       FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("location")
                .setValue( FirstActivity.locationToUpload ) ;


     /*   HashMap<String , Double> locationfiresotre = new HashMap<>();
        locationfiresotre.put("latitude" , FirstActivity.locationToUpload.latitude);
        locationfiresotre.put("longitude" , FirstActivity.locationToUpload.longitude);


        FirebaseFirestore.getInstance().collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
                .update("latitude" ,FirstActivity.locationToUpload.latitude );
        FirebaseFirestore.getInstance().collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
                .update("longitude" ,FirstActivity.locationToUpload.longitude);*/
    }


    public class vadapter extends FragmentPagerAdapter {
        public vadapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 5;
        }


        @NonNull
        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0 :
                    return new homefragment();
                case 1 :
                    return new searchfragment();
                case 2:
                      return new addbookfragment();
                      case 3 :
                    return new chatfragment();
            }
            return new profilefragment();
        }


        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0 :
                    return  "HOME";
                case 1 :
                    return "SEARCH" ;
                case 2 :
                    return "ADD" ;
                case 3 :
                    return "CHAT" ;
            }
            return "PROFILE"  ;
        }
    }

    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();

            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();    }
}