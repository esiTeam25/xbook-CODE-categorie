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

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class messagesadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<onechat> msglist ;

    public messagesadapter(ArrayList<onechat> msglist) {
        this.msglist = msglist;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1 ){
            return  new msgsntholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.msgsnt , parent , false)) ;
        }
     if(viewType==2){
            return  new msgrcvdholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.msgrcvd , parent , false)) ;
        }
     if(viewType==3){
         return  new photosntholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.photosnt , parent , false)) ;

     }
     else {
         return  new photorcvdholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.photorcvd , parent , false)) ;

     }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)==1 ){
            msgsntholder msgsnthldr = (msgsntholder) holder;
            onechat o = msglist.get(position);
            msgsnthldr.msg.setText(o.getMsg());
            msgsnthldr.time.setText( o.getDate());
        }
        if(getItemViewType(position)==2){
            msgrcvdholder msgrcvdhldr = (msgrcvdholder) holder;
            onechat o = msglist.get(position);
            msgrcvdhldr.msg.setText(o.getMsg());
            msgrcvdhldr.time.setText( o.getDate());

        }
        if(getItemViewType(position)==3){
            photosntholder photosnthldr = (photosntholder) holder;
            onechat o = msglist.get(position);
            byte[] decodedString2 = Base64.decode(o.getMsg(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString2, 0, decodedString2.length);
            photosnthldr.photo.setImageBitmap(decodedByte);
        }
        if(getItemViewType(position)==4){
            photorcvdholder photorcvdhldr = (photorcvdholder) holder;
            onechat o = msglist.get(position);
            byte[] decodedString2 = Base64.decode(o.getMsg(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString2, 0, decodedString2.length);
            photorcvdhldr.photo.setImageBitmap(decodedByte);

        }


    }




    @Override
    public int getItemCount() {
        return msglist.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(msglist.get(position).getMsg().length()<100) {
            if (msglist.get(position).getSenderid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                return 1;
            } else {
                return 2;
            }
        }
        else {
            if (msglist.get(position).getSenderid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                return 3;
            } else {
                return 4;
            }
        }
    }





    class msgsntholder extends RecyclerView.ViewHolder {
        TextView msg ,time ;

        public msgsntholder(@NonNull View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.msgid);
            time = itemView.findViewById(R.id.timeid);
        }
    }
    class photosntholder extends RecyclerView.ViewHolder{
        ImageView photo ;

        public photosntholder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photoid);
        }
    }





    public class msgrcvdholder extends RecyclerView.ViewHolder {
        TextView msg , time  ;

        public msgrcvdholder(@NonNull View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.msgid);
            time = itemView.findViewById(R.id.timeid);
        }
    }



    class photorcvdholder extends RecyclerView.ViewHolder{
        ImageView photo ;

        public photorcvdholder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.photoid);
        }
    }


}

