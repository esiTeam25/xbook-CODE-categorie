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
import com.ismaeldivita.chipnavigation.ChipNavigationBar;


public class MainActivity extends AppCompatActivity {

    private Toolbar tlbr ;
    private TabLayout tl ;
    private ViewPager vp ;
    private long pressedTime;
    ChipNavigationBar chipNavigationBar ;
Fragment fragment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // tlbr = findViewById(R.id.tlbrid);
        chipNavigationBar = findViewById(R.id.tabbar);
        chipNavigationBar.setItemSelected(R.id.home , true);
        getSupportFragmentManager().beginTransaction().replace(R.id.pager , new homefragment()).commit();
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case R.id.home:
                        fragment = new homefragment();
                        break ;
                    case R.id.nearby:
                        fragment = new MapsFragmentNearby();
                        break ;
                    case R.id.chat:
                        fragment = new chatfragment();
                        break ;
                }
                if (fragment != null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.pager , fragment).commit() ;
                }
            }
        });

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