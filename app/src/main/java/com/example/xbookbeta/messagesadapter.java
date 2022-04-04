package com.example.xbookbeta;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        else{
            return  new msgrcvdholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.msgrcvd , parent , false)) ;
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
        else {
            msgrcvdholder msgrcvdhldr = (msgrcvdholder) holder;
            onechat o = msglist.get(position);
            msgrcvdhldr.msg.setText(o.getMsg());
            msgrcvdhldr.time.setText( o.getDate());

        }

    }




    @Override
    public int getItemCount() {
        return msglist.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(msglist.get(position).getSenderid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            return 1 ;
        }else
        {
            return 2 ;
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





    public class msgrcvdholder extends RecyclerView.ViewHolder {
        TextView msg , time  ;

        public msgrcvdholder(@NonNull View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.msgid);
            time = itemView.findViewById(R.id.timeid);
        }
    }

}

