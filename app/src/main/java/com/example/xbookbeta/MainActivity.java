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
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;


public class MainActivity extends AppCompatActivity   {

    private Toolbar tlbr ;
    private TabLayout tl ;
    private ViewPager vp ;
    private long pressedTime;
    ChipNavigationBar chipNavigationBar ;
    Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vp = findViewById(R.id.pager);
        vp.setAdapter(new vadapter(getSupportFragmentManager()));
        chipNavigationBar = findViewById(R.id.tabbar);
        chipNavigationBar.setItemSelected(R.id.home , true);



        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case R.id.home:
                        vp.setCurrentItem(0);
                        break ;
                    case R.id.nearby:
                        vp.setCurrentItem(1);
                        break ;
                    case R.id.chat:
                        vp.setCurrentItem(2);
                        break ;
                }

            }
        });


    }


    public class vadapter extends FragmentPagerAdapter {
        public vadapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 3;
        }


        @NonNull
        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0 :
                    return new homefragment();
                case 1 :

                    return new MapsFragmentNearby();

            }
   return new chatfragment();
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