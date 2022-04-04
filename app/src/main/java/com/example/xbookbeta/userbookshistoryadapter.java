package com.example.xbookbeta;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class userbookshistoryadapter extends RecyclerView.Adapter<userbookshistoryadapter .bookholder>{
    private ArrayList<onebook> bookslist ;

    public userbookshistoryadapter(ArrayList<onebook> bookslist) {
        this.bookslist = bookslist;
    }


    @NonNull
    @Override
    public userbookshistoryadapter.bookholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new userbookshistoryadapter.bookholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.userbook , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull userbookshistoryadapter.bookholder holder, int position) {
        onebook u = bookslist.get(position);
 byte[] decodedString = Base64.decode(u.getBookimage(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.bookimage.setImageBitmap(decodedByte);
        holder.title.setText(u.getTitle());
        holder.categorie.setText(u.getCategorie());
        holder.distance.setText(u.getDistance());
        /*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( view.getContext() , chat.class );
                i.putExtra("id" , u.getId());
                i.putExtra("x" , "2");
                view.getContext().startActivity(i);
            }
        });*/
    }









    @Override
    public int getItemCount() {
        return bookslist.size();
    }


    public class bookholder extends RecyclerView.ViewHolder{
        TextView  title , categorie , distance  ;
        ImageView bookimage ;

        public bookholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.bookTitleId);
            categorie = itemView.findViewById(R.id.bookcategorieid);
            distance = itemView.findViewById(R.id.distanceid);
            bookimage = itemView.findViewById(R.id.bookImageId);
        }
    }
}
