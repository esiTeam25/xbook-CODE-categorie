package com.example.xbookbeta;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

//profilebooksadapter
public class profilebooksadapter extends RecyclerView.Adapter<profilebooksadapter.bookholder> {
    private ArrayList<onebook> bookslist ;
    String profileimagefor;


    public profilebooksadapter(ArrayList<onebook> bookslist) {
        this.bookslist = bookslist;
    }


    @NonNull
    @Override
    public profilebooksadapter.bookholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new bookholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.onegrid, parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull bookholder holder, int position) {
        onebook u = bookslist.get(position);




        if(u.getBookimage() != null) {
            byte[] decodedString2 = Base64.decode(u.getBookimage(), Base64.DEFAULT);
            Bitmap decodedByte2 = BitmapFactory.decodeByteArray(decodedString2, 0, decodedString2.length);
            holder.bookimage.setImageBitmap(decodedByte2);
        }
        if(u.getTitle()!=null) {
            holder.title.setText(u.getTitle());}

    }









    @Override
    public int getItemCount() {
        return bookslist.size();
    }


    public class bookholder extends RecyclerView.ViewHolder{
        TextView  title  ;
        ImageView bookimage ;

        public bookholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleid);
            bookimage = itemView.findViewById(R.id.bookImageId);
        }
    }
}