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
import android.widget.ProgressBar;
import android.widget.Toast;

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

import de.hdodenhof.circleimageview.CircleImageView;


public class homefragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener   {
    private TabLayout tl ;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout ;
    private Button menubutton , notifs ;
    ProgressBar prgrsbr;
    RecyclerView rv ;
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
        cats.add(R.drawable.blue);
        cats.add(R.drawable.orange);
        cats.add(R.drawable.green);
        cats.add(R.drawable.grey);
        cats.add(R.drawable.brown);
        cats.add(R.drawable.red);
        cats.add(R.drawable.pink);
        cats.add(R.drawable.yellow);
        cats.add(R.drawable.bluetwo);
        cats.add(R.drawable.images);
        RecyclerView rv = v.findViewById(R.id.categoriesId) ;
        rv.setAdapter(new categoriesAdapter(cats) );


        DatabaseReference account = FirebaseDatabase.getInstance().getReference().child("users").child( FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
        account.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if ( !snapshot.child("image").getValue().toString().equals("" ) ) {
                    byte[] decodedString = Base64.decode(snapshot.child("image").getValue().toString(), Base64.DEFAULT);
                    prflimg.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
                }
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



        /*


        TabLayout.Tab tab0 = tl.newTab() ;
        tab0.setText("all");
        TabLayout.Tab tab1 = tl.newTab() ;
        tab1.setText("one");
        TabLayout.Tab tab2 = tl.newTab() ;
        tab2.setText("two");
        TabLayout.Tab tab3 = tl.newTab() ;
        tab3.setText("three");
        TabLayout.Tab tab4 = tl.newTab() ;
        tab4.setText("four");
        TabLayout.Tab tab5 = tl.newTab() ;
        tab5.setText("five");
        TabLayout.Tab tab6 = tl.newTab() ;
        tab6.setText("six");
        TabLayout.Tab tab7 = tl.newTab() ;
        tab7.setText("seven");
        TabLayout.Tab tab8 = tl.newTab() ;
        tab8.setText("eight");
        TabLayout.Tab tab9 = tl.newTab() ;
        tab9.setText("nine");

        tl.addTab(tab0);
        tl.addTab(tab1);
        tl.addTab(tab2);
        tl.addTab(tab3);
        tl.addTab(tab4);
        tl.addTab(tab5);
        tl.addTab(tab6);
        tl.addTab(tab7);
        tl.addTab(tab8);
        tl.addTab(tab9);

        key=null ;
        rv = v.findViewById(R.id.recyclerView);
        rv.setLayoutManager( new LinearLayoutManager(v.getContext()));
        rv.setHasFixedSize(true);

        books.clear();
        rva.notifyItemRangeRemoved(0 ,books.size());
        rv.setAdapter(rva);



        addelements(get());



        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(new LinearLayoutManager(getActivity())) {
            @Override
            public void onScrolledToEnd() {
                if (!isloading){

                    isloading=true ;

                    addelements(get());




                }
            }
        });









        tl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {

                    case 0:




                        key=null ;

                        books.clear();
                        rva.notifyItemRangeRemoved(0 ,books.size());
                        rv.setAdapter(rva);



                        addelements(get());



                        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(new LinearLayoutManager(getActivity())) {
                            @Override
                            public void onScrolledToEnd() {
                                if (!isloading){

                                    isloading=true ;

                                    addelements(get());




                                }
                            }
                        });










                        break;
                    case 1:


                        key=null ;

                        books.clear();
                        rva.notifyItemRangeRemoved(0 ,books.size());
                        rv.setAdapter(rva);



                        addelements(getc("categorie1"));



                        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(new LinearLayoutManager(getActivity())) {
                            @Override
                            public void onScrolledToEnd() {
                                if (!isloading){

                                    isloading=true ;

                                    addelements(getc("categorie1"));




                                }
                            }
                        });





                        break;

                    case 2:


                        key=null ;

                        books.clear();
                        rva.notifyItemRangeRemoved(0 ,books.size());
                        rv.setAdapter(rva);



                        addelements(getc("categorie2"));



                        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(new LinearLayoutManager(getActivity())) {
                            @Override
                            public void onScrolledToEnd() {
                                if (!isloading){

                                    isloading=true ;

                                    addelements(getc("categorie2"));




                                }
                            }
                        });





                        break;


                    case 3:


                        key=null ;

                        books.clear();
                        rva.notifyItemRangeRemoved(0 ,books.size());
                        rv.setAdapter(rva);



                        addelements(getc("categorie3"));



                        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(new LinearLayoutManager(getActivity())) {
                            @Override
                            public void onScrolledToEnd() {
                                if (!isloading){

                                    isloading=true ;

                                    addelements(getc("categorie3"));




                                }
                            }
                        });





                        break;


                    case 4:


                        key=null ;

                        books.clear();
                        rva.notifyItemRangeRemoved(0 ,books.size());
                        rv.setAdapter(rva);



                        addelements(getc("categorie4"));



                        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(new LinearLayoutManager(getActivity())) {
                            @Override
                            public void onScrolledToEnd() {
                                if (!isloading){

                                    isloading=true ;

                                    addelements(getc("categorie4"));




                                }
                            }
                        });




                        break;
                    case 5:


                        key=null ;

                        books.clear();
                        rva.notifyItemRangeRemoved(0 ,books.size());
                        rv.setAdapter(rva);



                        addelements(getc("categorie5"));



                        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(new LinearLayoutManager(getActivity())) {
                            @Override
                            public void onScrolledToEnd() {
                                if (!isloading){

                                    isloading=true ;

                                    addelements(getc("categorie5"));




                                }
                            }
                        });




                        break;

                    case 6:


                        key=null ;

                        books.clear();
                        rva.notifyItemRangeRemoved(0 ,books.size());
                        rv.setAdapter(rva);



                        addelements(getc("categorie6"));



                        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(new LinearLayoutManager(getActivity())) {
                            @Override
                            public void onScrolledToEnd() {
                                if (!isloading){

                                    isloading=true ;

                                    addelements(getc("categorie6"));




                                }
                            }
                        });




                        break;

                    case 7:


                        key=null ;

                        books.clear();
                        rva.notifyItemRangeRemoved(0 ,books.size());
                        rv.setAdapter(rva);



                        addelements(getc("categorie7"));



                        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(new LinearLayoutManager(getActivity())) {
                            @Override
                            public void onScrolledToEnd() {
                                if (!isloading){

                                    isloading=true ;

                                    addelements(getc("categorie7"));




                                }
                            }
                        });




                        break;

                    case 8:


                        key=null ;

                        books.clear();
                        rva.notifyItemRangeRemoved(0 ,books.size());
                        rv.setAdapter(rva);



                        addelements(getc("categorie8"));



                        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(new LinearLayoutManager(getActivity())) {
                            @Override
                            public void onScrolledToEnd() {
                                if (!isloading){

                                    isloading=true ;

                                    addelements(getc("categorie8"));




                                }
                            }
                        });




                        break;

                    case 9:


                        key=null ;

                        books.clear();
                        rva.notifyItemRangeRemoved(0 ,books.size());
                        rv.setAdapter(rva);



                        addelements(getc("categorie9"));



                        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(new LinearLayoutManager(getActivity())) {
                            @Override
                            public void onScrolledToEnd() {
                                if (!isloading){

                                    isloading=true ;

                                    addelements(getc("categorie9"));




                                }
                            }
                        });




                        break;



                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });










*/






        return  v ;
    }





    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent ( getActivity(), LoginActivity.class) );
        getActivity().finish();
        return false;
    }


/*

    public void addelements(Query get ){


        prgrsbr.setVisibility(View.VISIBLE);



        get.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (!isloading) {books.clear();}
                for (DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType() == DocumentChange.Type.ADDED){
                        books.add(
                                new
                                        onebook(dc.getDocument().getString("id") , dc.getDocument().getString("image") ,dc.getDocument().getString("title") , dc.getDocument().getString("categorie") , dc.getDocument().getDouble("lat")  , dc.getDocument().getDouble("lng") ,dc.getDocument().getId())) ;
                        key =  dc.getDocument();
                    }



                }
                rva.notifyDataSetChanged();
                rva.notifyItemRangeInserted(books.size() , books.size());
                if(!isloading){
                    rv.smoothScrollToPosition(0);}
                isloading=false ;
                prgrsbr.setVisibility(View.INVISIBLE);

                //  rv.smoothScrollToPosition(0);
            }

        });



    }




    public void fetchData( Query get  ){





    }








    public Query get(){
        if (key== null ) {

            return FirebaseFirestore.getInstance().collection("books").limit(5) ;
        }
        else {
            return FirebaseFirestore.getInstance().collection("books").startAfter(key).limit(5);
        }
    }

    public Query getc(String c){
        if (key== null ) {

            return FirebaseFirestore.getInstance().collection("books").whereEqualTo("categorie" , c).limit(5) ;
        }
        else {
            return FirebaseFirestore.getInstance().collection("books").whereEqualTo("categorie" , c).startAfter(key).limit(5);
        }
    }

*/


}




