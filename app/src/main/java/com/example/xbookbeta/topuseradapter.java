package com.example.xbookbeta;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class topuseradapter extends RecyclerView.Adapter<topuseradapter.viewholder> {
    ArrayList<userandid> users ;

    public topuseradapter(ArrayList<userandid> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.one_top_user , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position) {
        if(!users.get(position).equals("")) {
            byte[] decodedString = Base64.decode(users.get(position).image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.userImage.setImageBitmap(decodedByte);
            holder.userImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    otherprofile.id = users.get(position).id;
                    view.getContext().startActivity(new Intent(view.getContext() , otherprofile.class));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        CircleImageView userImage ;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.topuserid);


        }
    }
}
