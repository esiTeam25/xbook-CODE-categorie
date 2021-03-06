package com.example.xbookbeta;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
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

import de.hdodenhof.circleimageview.CircleImageView;


public class homefragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener   {
    private TabLayout tl ;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout ;
    private ImageView menubutton , notifs ;
    ProgressBar prgrsbr;
    ShimmerFrameLayout shimmerFrameLayout2 ;
    RecyclerView rv ;
    homeadapter rva =  new homeadapter(bookssss) ;
    public static ArrayList<onebook> bookssss = new ArrayList<>() ;

    Boolean end = false ;
  //  booksadapter rva =  new booksadapter(books); ;
    Boolean isloading = false ;
    DocumentSnapshot key = null ;
    //public static ArrayList<onebook> books = new ArrayList<>() ;
    CircleImageView prflimg ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_homefragment, container, false);
        drawerLayout = v.findViewById(R.id.drawerLayoutid);
        navigationView = v.findViewById(R.id.navigationViewid);
        menubutton = v.findViewById(R.id.menubuttonid);
        //prgrsbr = v.findViewById(R.id.prgrsbrid);
        notifs = v.findViewById(R.id.notifsid);
        navigationView.setNavigationItemSelectedListener(this);
        //tl = v.findViewById(R.id.tl);
        prflimg = v.findViewById(R.id.profileImageId);
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

                startActivity(new Intent(v.getContext() , search.class));
               // startActivity(new Intent(v.getContext() , books.class));

            }

        });

        Toast.makeText(getContext(), FirstActivity.savedBooks.size()+"", Toast.LENGTH_SHORT).show();
        ArrayList<Integer> cats = new ArrayList<>();
        cats.add(R.drawable.img);
        cats.add(R.drawable.img);
        cats.add(R.drawable.img);
        cats.add(R.drawable.img);
        cats.add(R.drawable.img);
        cats.add(R.drawable.img);
        cats.add(R.drawable.img);
        cats.add(R.drawable.img);
        cats.add(R.drawable.img);
        cats.add(R.drawable.img);
        RecyclerView rv = v.findViewById(R.id.categoriesId) ;
        rv.setAdapter(new categoriesAdapter(cats) );


        DatabaseReference account = FirebaseDatabase.getInstance().getReference().child("users").child( FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
        account.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if ( !snapshot.child("image").getValue().toString().equals("" ) ) {
                    byte[] decodedString = Base64.decode(snapshot.child("image").getValue().toString(), Base64.DEFAULT);
                    v.findViewById(R.id.shimmerLayout3).setVisibility(View.INVISIBLE);
                    prflimg.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       ShimmerFrameLayout shimmerFrameLayout  = v.findViewById(R.id.shimmerLayout);
        shimmerFrameLayout.startShimmer();
        RecyclerView usersrv =v.findViewById(R.id.usersid);
       ArrayList<userandid> users = new ArrayList<userandid>() ;
        topuseradapter usersadapter = new topuseradapter(users);
        usersrv.setHasFixedSize(true);
        usersrv.setAdapter(usersadapter);
        FirebaseDatabase.getInstance().getReference().child("users").orderByChild("likes").limitToLast(6).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren() ) users.add(new userandid(snapshot1.child("image").getValue().toString() ,snapshot1.child("id").getValue().toString() ));
                Collections.reverse(users);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.INVISIBLE);
                 usersadapter.notifyDataSetChanged();


           }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        prflimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext() , profileActivity.class));
            }
        });

         v.findViewById(R.id.addbook).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(getContext() , addbook.class));
             }
         });





        rv = v.findViewById(R.id.rvid);
        rv.setAdapter(rva);
        rv.setHasFixedSize(true);
        LinearLayoutManager lm = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(lm);
        bookssss.clear();

        shimmerFrameLayout2  =v.findViewById(R.id.shimmerLayout2);
        shimmerFrameLayout2.startShimmer();
        addelements();

















        return  v ;
    }





    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent ( getActivity(), LoginActivity.class) );
        getActivity().finish();
        return false;
    }




    public void addelements(){




        FirebaseFirestore.getInstance().collection("books").orderBy("likes", Query.Direction.DESCENDING).limit(5).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                //bookssss.clear();
                shimmerFrameLayout2.setVisibility(View.VISIBLE);


                for (DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType() == DocumentChange.Type.ADDED){
                        bookssss.add(
                                new
                                        onebook( dc.getDocument().getString("id") , dc.getDocument().getString("image") ,dc.getDocument().getId() , dc.getDocument().getLong("likes").toString())) ;
                    }



                }
                shimmerFrameLayout2.stopShimmer();
                shimmerFrameLayout2.setVisibility(View.INVISIBLE);
                rva.notifyDataSetChanged();

            }

        });


    }




}




