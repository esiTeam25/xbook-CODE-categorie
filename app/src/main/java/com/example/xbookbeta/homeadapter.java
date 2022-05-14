package com.example.xbookbeta;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
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
public class homeadapter extends RecyclerView.Adapter<homeadapter.bookholder> {
    private ArrayList<onebook> bookslist ;


    public homeadapter(ArrayList<onebook> bookslist) {
        this.bookslist = bookslist;
    }


    @NonNull
    @Override
    public homeadapter.bookholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new bookholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.gridtwo, parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull bookholder holder, int position) {
        onebook u = bookslist.get(position);




        if(u.getBookimage() != null) {
            byte[] decodedString2 = Base64.decode(u.getBookimage(), Base64.DEFAULT);
            Bitmap decodedByte2 = BitmapFactory.decodeByteArray(decodedString2, 0, decodedString2.length);
            holder.bookimage.setImageBitmap(decodedByte2);
        }

            holder.title.setText(u.getLikes() );

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        TextView  title  ;
        ImageView bookimage ;

        public bookholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.likes);
            bookimage = itemView.findViewById(R.id.bookImageId);
        }
    }

}