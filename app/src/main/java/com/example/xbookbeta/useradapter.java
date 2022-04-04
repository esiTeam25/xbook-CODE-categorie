package com.example.xbookbeta;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class useradapter extends RecyclerView.Adapter<useradapter.userholder> {
    private ArrayList<oneuser> userslist ;

    public useradapter(ArrayList<oneuser> userslist) {
        this.userslist = userslist;
    }

    @NonNull
    @Override
    public userholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new userholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.oneuser , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull userholder holder, int position) {
        oneuser u = userslist.get(position);
        holder.name.setText(u.getName());
        if(!u.getImage().equals("")) {
            byte[] decodedString = Base64.decode(u.getImage(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.prflimg.setImageBitmap(decodedByte);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( view.getContext() , chat.class );
                i.putExtra("id" , u.getId());
                i.putExtra("x" , "2");
                view.getContext().startActivity(i);

            }
        });
    }









    @Override
    public int getItemCount() {
        return userslist.size();
    }


    public class userholder extends RecyclerView.ViewHolder{
        TextView name ;
        CircleImageView prflimg ;

        public userholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameid);
            prflimg= itemView.findViewById(R.id.prflimgid);
        }
    }
}