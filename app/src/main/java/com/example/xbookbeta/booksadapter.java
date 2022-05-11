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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class booksadapter extends RecyclerView.Adapter<booksadapter.bookholder> {
    private ArrayList<onebook> bookslist ;
    String profileimagefor;


    public booksadapter(ArrayList<onebook> bookslist) {
        this.bookslist = bookslist;
    }


    @NonNull
    @Override
    public booksadapter.bookholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new bookholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.onebook , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull bookholder holder, int position) {
        onebook u = bookslist.get(position);

        DatabaseReference account = FirebaseDatabase.getInstance().getReference().child("users")
                .child(u.getUserid());
        account.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.profilename.setText(snapshot.child("name").getValue().toString());
                profileimagefor = snapshot.child("image").getValue().toString();
                if ( !snapshot.child("image").getValue().toString().equals("" ) ) {
                    byte[] decodedString = Base64.decode(snapshot.child("image").getValue().toString(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    holder.profileimage.setImageBitmap(decodedByte);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



if(u.getBookimage() != null) {
    byte[] decodedString2 = Base64.decode(u.getBookimage(), Base64.DEFAULT);
    Bitmap decodedByte2 = BitmapFactory.decodeByteArray(decodedString2, 0, decodedString2.length);
    holder.bookimage.setImageBitmap(decodedByte2);
}
        if(u.getTitle()!=null) {
        holder.title.setText(u.getTitle());}
            if(u.getCategorie()!=null) {
        holder.categorie.setText(u.getCategorie());}
if(FirstActivity.locationToUpload!=null) {
    holder.distance.setText(String.format("%.2f", SphericalUtil.computeDistanceBetween(new LatLng(u.getLatitude(), u.getLongitude()), FirstActivity.locationToUpload) / 1000) + "km");
}
if(FirstActivity.savedBooks.contains(u.getKey())) holder.save.setVisibility(View.INVISIBLE);
        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> dataaa = new HashMap<>();
                dataaa.put("key" , u.getKey());
                FirebaseFirestore.getInstance().collection(FirebaseAuth.getInstance().getUid()).add(dataaa).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(view.getContext(), "saved", Toast.LENGTH_SHORT).show();
                        holder.save.setText("saved");
                        holder.save.setClickable(false);
                        FirstActivity.savedBooks.add(u.getKey());
                    }
                });

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapactivity.point = new LatLng( u.getLatitude() , u.getLongitude());
                mapactivity.name = u.getTitle() ;
                bookandpublisherdetails.name = holder.profilename.getText().toString();
                bookandpublisherdetails.bookimage = u.getBookimage() ;
                bookandpublisherdetails.profileimage = profileimagefor ;
                bookandpublisherdetails.key = u.getKey();
                mapactivity.image = u.getBookimage(); ;
                Intent i = new Intent( view.getContext() , bookandpublisherdetails.class );
                i.putExtra("id" , u.getUserid());
                view.getContext().startActivity(i);

            }
        });

    }









    @Override
    public int getItemCount() {
        return bookslist.size();
    }


    public class bookholder extends RecyclerView.ViewHolder{
        TextView profilename , title , categorie , distance  ;
        CircleImageView profileimage ;
        ImageView bookimage ;
        Button save  ;

        public bookholder(@NonNull View itemView) {
            super(itemView);
            profilename = itemView.findViewById(R.id.profileNameId);
            title = itemView.findViewById(R.id.bookTitleId);
            categorie = itemView.findViewById(R.id.bookcategorieid);
            distance = itemView.findViewById(R.id.distanceid);
            profileimage = itemView.findViewById(R.id.profileImageId);
            bookimage = itemView.findViewById(R.id.bookImageId);
            save = itemView.findViewById(R.id.saveid) ;
        }
    }
}