package com.example.xbookbeta;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


public class searchfragment extends Fragment {
    private RecyclerView rv ;
    private useradapter adptr ;

    public searchfragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchfragment, container, false) ;
       /* rv = view.findViewById(R.id.rvid);
        ProgressDialog wait = new ProgressDialog(view.getContext());
        wait.setTitle("wait");
        wait.setMessage("wait");
        wait.show();
        ArrayList<oneuser> userslist = new ArrayList<>();
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adptr = new useradapter(userslist);
        rv.setAdapter(adptr);
        DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child("users");
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userslist.clear();
                for ( DataSnapshot ds : snapshot.getChildren()){
                    if(!ds.getKey().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){

                        userslist.add(        new oneuser(  ds.child("name").getValue().toString() , ds.child("image").getValue().toString() ,ds.child("id").getValue().toString()  )         );
                        wait.dismiss();
                    }
                }
                Collections.sort(userslist , (oneuser  a , oneuser b )-> a.getName().compareTo(b.getName()) );
                adptr.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
*/



        return view ;
    }

}