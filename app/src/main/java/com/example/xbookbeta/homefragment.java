package com.example.xbookbeta;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;


public class homefragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener   {
    private TabLayout tl ;
    private ViewPager vp ;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout ;
    private Button menubutton , notifs ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_homefragment, container, false);
        drawerLayout = v.findViewById(R.id.drawerLayoutid);
        navigationView = v.findViewById(R.id.navigationViewid);
        menubutton = v.findViewById(R.id.menubuttonid);
        notifs = v.findViewById(R.id.notifsid);
        navigationView.setNavigationItemSelectedListener(this);
        tl = v.findViewById(R.id.tl);
        vp = v.findViewById(R.id.vpid);
        vp.setAdapter(new vadapter(getChildFragmentManager()));
        tl.setupWithViewPager(vp);
        menubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawerLayout.isDrawerOpen(GravityCompat.START)){drawerLayout.closeDrawer(GravityCompat.START); }
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        notifs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FirstActivity.locationToUpload==null){

                    final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage("this functionality needs GPS autorisation")
                            .setCancelable(true)
                            ;
                    final AlertDialog alert = builder.create();
                    alert.show();

                }else{
                startActivity(new Intent(v.getContext() , locationbooks.class));}
            }
        });





























        return  v ;
    }





    public class vadapter extends FragmentPagerAdapter {
        public vadapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 10;
        }


        @NonNull
        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0 :
                    return new cfragment0();
                case 2 :
                    return new cfragment2();
                case 3 :
                    return new cfragment3();
                case 4 :
                    return new cfragment4();
                case 5 :
                    return new cfragment5();
                case 6 :
                    return new cfragment6();
                case 7 :
                    return new cfragment7();
                case 8 :
                    return new cfragment8();
                case 9 :
                    return new cfragment9();

            }
            return new cfragment1();
        }


        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0 :
                    return "categorie0" ;
                case 2 :
                    return  "categorie2" ;
                case 3 :
                    return  "categorie3" ;
                case 4 :
                    return  "categorie4" ;
                case 5 :
                    return  "categorie5" ;
                case 6 :
                    return  "categorie6" ;
                case 7 :
                    return  "categorie7" ;
                case 8 :
                    return "categorie8" ;
                case 9 :
                    return  "categorie9" ;

            }
            return "categorie1" ;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent ( getActivity(), LoginActivity.class) );
        getActivity().finish();
        return false;
    }





}




