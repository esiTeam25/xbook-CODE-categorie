package com.example.xbookbeta;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class chatfragment extends Fragment {

    private recentadapter adptr ;
    private RecyclerView rv ;
    private ArrayList<recentuser> rvlst ;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chatfragment, container, false);
        rv = v.findViewById(R.id.rvid);
        rvlst = new ArrayList<>();
        rv.setHasFixedSize(true);
        rv.setLayoutManager( new LinearLayoutManager(getContext()));
        adptr = new recentadapter(rvlst);
        rv.setAdapter(adptr);




        return v ;
    }
/*
    @Override
    public void onPause() {
        super.onPause();
        rv.removeAllViewsInLayout();
        rvlst.clear();
        adptr.notifyItemRangeRemoved(rvlst.size() , rvlst.size());
        Toast.makeText(getContext(), "onpause", Toast.LENGTH_SHORT).show();


    }*/

    @Override
    public void onResume() {
        super.onResume();
        getRecentUpdates();
    }


    private final EventListener<QuerySnapshot> eventListener  =(value, error) -> {
       rv.removeAllViewsInLayout();
        rvlst.clear();
        adptr.notifyItemRangeRemoved(rvlst.size() , rvlst.size());

                    for (DocumentChange dc : value.getDocumentChanges()){

                            rvlst.add (
                                    new recentuser(
                                            dc.getDocument().getString("name" ),
                                            ""
                                            ,dc.getDocument().getId().toString()
                                            ,dc.getDocument().getString("msg")
                                            ,dc.getDocument().get("time")

                                    )
                            ) ;


                            //gillette();
                        }

                    // Toast.makeText(getContext(), "done", Toast.LENGTH_SHORT).show();
                   // Collections.reverse(rvlst);
                    adptr.notifyDataSetChanged();








    };


    public void getRecentUpdates() {
       /* FirebaseFirestore.getInstance().collection("recent").document("plus")
                .collection(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).orderBy("time" , Query.Direction.DESCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot dc : queryDocumentSnapshots){
                            rvlst.add (
                                    new recentuser(
                                            dc.getString("name" ).toString(),
                                            ""
                                            ,dc.getId().toString()
                                            ,dc.getString("msg").toString()
                                            ,dc.get("time")

                                    )


                            ) ;
                            Toast.makeText(getContext(), dc.getString("name" ), Toast.LENGTH_SHORT).show();
                        }
                        //Collections.reverse(rvlst);
                        adptr.notifyDataSetChanged();  }
                });*/
        FirebaseDatabase.getInstance().getReference().child("recent").child("plus").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .orderByChild("time").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rv.removeAllViewsInLayout();
                rvlst.clear();
                adptr.notifyItemRangeRemoved(rvlst.size() , rvlst.size());
                for ( DataSnapshot ds : snapshot.getChildren()){

                    rvlst.add (
                            new recentuser(
                                 ds.child("name").getValue().toString(),
                                    ""
                                    ,ds.getKey().toString()
                                    ,ds.child("msg").getValue().toString()
                                    ,ds.child("time").getValue()

                            )


                    ) ;
                    //Collections.reverse(rvlst);
                    //adptr.notifyDataSetChanged();
                    }
Collections.reverse(rvlst);
                adptr.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


/*
    public void gillette() {
        FirebaseFirestore.getInstance().collection("recent").document("plus")
                .collection(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).orderBy("time")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                rv.removeAllViewsInLayout();
                rvlst.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {

                    rvlst.add (
                            new recentuser(
                                    document.get("name" ).toString(),
                                    ""
                                    ,document.getId().toString()
                                    ,document.get("msg").toString()
                                    ,document.get("time")

                            )


                    ) ;

                }
                Collections.reverse(rvlst);
                adptr.notifyDataSetChanged();
                //   adptr.notifyItemRangeInserted(rvlst.size(),rvlst.size());
            }



        });

    }*/
}