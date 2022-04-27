package com.example.xbookbeta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


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
                    return new MapsFragmentNearby();
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
                    return "NEARBY" ;
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