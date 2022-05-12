package com.example.xbookbeta;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class categoriesAdapter extends RecyclerView.Adapter<categoriesAdapter.viewholder> {
   ArrayList<Integer> images ;
    public categoriesAdapter(ArrayList<Integer> images) {
        this.images = images ;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.categorieimage , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
     holder.image.setImageResource(images.get(position));

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        ImageView image ;
        public viewholder(@NonNull View itemView) {
            super(itemView);
           image = itemView.findViewById(R.id.catimage);
        }
    }
}
