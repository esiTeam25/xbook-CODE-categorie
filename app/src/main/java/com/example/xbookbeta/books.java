package com.example.xbookbeta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.View;
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

public class books extends AppCompatActivity {
    private TabLayout tl ;
    ProgressBar prgrsbr;
    RecyclerView rv ;
    Boolean end = false ;
    booksadapter rva =  new booksadapter(books); ;
    Boolean isloading = false ;
    DocumentSnapshot key = null ;
    public static ArrayList<onebook> books = new ArrayList<>() ;
    public static int catnum ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        prgrsbr = findViewById(R.id.prgrsbrid);
        tl = findViewById(R.id.tl);




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
        rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager( new LinearLayoutManager(books.this ));
        rv.setHasFixedSize(true);

        books.clear();
        rva.notifyItemRangeRemoved(0 ,books.size());
        rv.setAdapter(rva);



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



                        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(new LinearLayoutManager(books.this )) {
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



                        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(new LinearLayoutManager(books.this )) {
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



                        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(new LinearLayoutManager(books.this )) {
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



                        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(new LinearLayoutManager(books.this )) {
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



                        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(new LinearLayoutManager(books.this )) {
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



                        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(new LinearLayoutManager(books.this )) {
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



                        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(new LinearLayoutManager(books.this )) {
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



                        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(new LinearLayoutManager(books.this )) {
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



                        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(new LinearLayoutManager(books.this )) {
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



                        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(new LinearLayoutManager(books.this )) {
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


switch (catnum){
    case 0 :
        tl.selectTab(tab0);
        addelements(get());



        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(new LinearLayoutManager(books.this )) {
            @Override
            public void onScrolledToEnd() {
                if (!isloading){

                    isloading=true ;

                    addelements(get());




                }
            }
        });



        break ;
    case 3 :
        tl.selectTab(tab3);
        new Handler().postDelayed(
                new Runnable() {
                    @Override public void run() {
                        tl.getTabAt(3).select();
                    }
                }, 100);
        break ;
    case 8 :
        tl.selectTab(tab8);
        new Handler().postDelayed(
                new Runnable() {
                    @Override public void run() {
                        tl.getTabAt(8).select();
                    }
                }, 100);
        break ;

}


    }



















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

}