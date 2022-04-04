package com.example.xbookbeta;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class categorieslist extends RecyclerView.Adapter<categorieslist.categorieholder>  {
    private ArrayList<String> categorieslist ;
    TextView textView;

    public categorieslist(ArrayList<String> categorieslist) {
        this.categorieslist = categorieslist;
    }


    @NonNull
    @Override
    public categorieholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new categorieholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.onecategorie , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull categorieholder holder, int position) {
      String c = categorieslist.get(position);
      holder.categoriename.setText(c);





        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return categorieslist.size();
    }

    public class categorieholder extends  RecyclerView.ViewHolder{
TextView categoriename ;
        public categorieholder(@NonNull View itemView) {
            super(itemView);
            categoriename = itemView.findViewById(R.id.categorietextid);

        }
    }
}
