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

import com.facebook.shimmer.ShimmerFrameLayout;
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
    ShimmerFrameLayout shimmerFrameLayout ;


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
        shimmerFrameLayout = v.findViewById(R.id.shimmerLayout);
        getRecentUpdates();




        return v ;
    }











    @Override
    public void onResume() {
        super.onResume();
        //getRecentUpdates();
    }


    public void getRecentUpdates() {

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
                                    ,ds.child("id").getValue().toString()
                                    ,ds.child("key").getValue().toString()

                                    ,ds.child("msg").getValue().toString()
                                    ,ds.child("time").getValue()

                            )


                    ) ;

                    }
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.INVISIBLE);
Collections.reverse(rvlst);
                adptr.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}