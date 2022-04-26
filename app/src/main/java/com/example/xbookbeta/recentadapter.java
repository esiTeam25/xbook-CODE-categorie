package com.example.xbookbeta;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Base64;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class recentadapter extends RecyclerView.Adapter<recentadapter.userholder> {
    ArrayList<recentuser> userlist ;
    String imagestr ;
    public recentadapter(ArrayList<recentuser> userlist) {
        this.userlist = userlist;
    }

    @NonNull
    @Override
    public userholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new userholder( LayoutInflater.from(parent.getContext()).inflate(R.layout.oneuser2 ,  parent , false ) );
    }

    @Override
    public void onBindViewHolder(@NonNull userholder holder, int position) {
        recentuser u = userlist.get(position);
      /*  FirebaseFirestore.getInstance().collection("recent").document("plus")
                .collection(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
                .document(u.getId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.get("state").equals("n")){
                    holder.msg.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.msg.setTypeface(Typeface.DEFAULT_BOLD);
                    holder.msg.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);
                }
            }
        }) ;*/
        FirebaseDatabase.getInstance().getReference().child("recent").child("plus").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(u.getId()).child("state").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.getValue().toString().equals("s")){
                    holder.msg.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.msg.setTypeface(Typeface.DEFAULT_BOLD);
                    holder.msg.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        holder.name.setText(u.getName());
        FirebaseDatabase.getInstance().getReference("users").child(u.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                imagestr= "";
                imagestr = snapshot.child("image").getValue().toString();
                if(!imagestr.equals("")) {
                    byte[] decodedString = Base64.decode(imagestr, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    holder.prflimg.setImageBitmap(decodedByte);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.msg.setText(u.getMsg());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( view.getContext() , chat.class );
                i.putExtra("id" , u.getId());
                i.putExtra("x" , "1");
                view.getContext().startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }


    class  userholder extends RecyclerView.ViewHolder {
        TextView name , msg ;
        CircleImageView prflimg;
        public userholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameid);
            msg = itemView.findViewById(R.id.msgid);
            prflimg = itemView.findViewById(R.id.prflimgid);
        }
    }
}
