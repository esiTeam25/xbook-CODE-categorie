package com.example.xbookbeta;

import static android.content.ContentValues.TAG;

import static java.text.DateFormat.getDateTimeInstance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.theartofdev.edmodo.cropper.CropImage;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import org.w3c.dom.Document;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class
chat extends AppCompatActivity {
    private CircleImageView back , prflimg ;
    private TextView name ;
    private RecyclerView rv ;
    private CircleImageView send , sendphoto ;
    private messagesadapter adptr ;
    private EditText mes;
    private String namestr , imgstr , imgstrr , text;
    private ArrayList<onechat> msgslist  ;
    private ArrayList<String> msgslist2;
    public Boolean ver ;
    ProgressBar prgrsbr ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        prgrsbr = findViewById(R.id.prgrsbrid);
        prgrsbr.setVisibility(View.VISIBLE);
        back = findViewById(R.id.backid);
        rv = findViewById(R.id.rvid);
        send = findViewById(R.id.sendid);
        sendphoto= findViewById(R.id.sendphotoid);
        mes  = findViewById(R.id.edittextid);
        msgslist = new ArrayList<>();
        msgslist2 = new ArrayList<>();
        adptr = new messagesadapter(msgslist);
        rv.setHasFixedSize(true);
        rv.setAdapter(adptr);
        rv.setLayoutManager(new LinearLayoutManager(this));
        prflimg = findViewById(R.id.prflimgid);
        name = findViewById(R.id.nameid);
        namestr = "";
        text = "" ;
        imgstr = "";
        imgstrr = "";
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        msgslist.clear();
        adptr.notifyItemRangeRemoved(msgslist.size() , msgslist.size());

    /*    FirebaseFirestore.getInstance().collection("chat")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection(getIntent().getExtras().getString("id"))
                .orderBy("realtime")
                .addSnapshotListener(eventListener);
*/
        FirebaseDatabase.getInstance().getReference().child("chat").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(!snapshot.hasChild( getIntent().getExtras().getString("key")+getIntent().getExtras().getString("id") )) {
                            prgrsbr.setVisibility(View.INVISIBLE);
                        }
                        }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        FirebaseDatabase.getInstance().getReference().child("chat").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
                .child(getIntent().getExtras().getString("key")+getIntent().getExtras().getString("id")).orderByChild("realtime")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            onechat one = new onechat(
                                    snapshot.child("senderid").getValue().toString(),
                                    snapshot.child("receiverid").getValue().toString(),
                                    snapshot.child("msg").getValue().toString(),
                                    getTimeDate((long) snapshot.child("realtime").getValue())

                            );
                            msgslist.add(one);
                        adptr.notifyDataSetChanged();
                        adptr.notifyItemRangeInserted(msgslist.size(), msgslist.size());
                        rv.smoothScrollToPosition(msgslist.size());
                        prgrsbr.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });



        if(getIntent().getExtras().getString("x").equals("1")) {



            FirebaseDatabase.getInstance().getReference().child("recent").child("plus")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(getIntent().getExtras().getString("key")+getIntent().getExtras().getString("id")).child("state").setValue("s");


        }
        else {



            FirebaseDatabase.getInstance().getReference().child("recent").child("plus")
                    .child(getIntent().getExtras().getString("id")+FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(getIntent().getExtras().getString("id"))){
                        FirebaseDatabase.getInstance().getReference().child("recent").child("plus")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child(getIntent().getExtras().getString("key")+getIntent().getExtras().getString("id")).child("state").setValue("s");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });





        }







       /* FirebaseDatabase.getInstance().getReference().child("users").child(getIntent().getExtras().getString("id")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setText(snapshot.child("name").getValue().toString());
                if(!snapshot.child("image").getValue().toString().equals("")){
                    imgstr = snapshot.child("image").getValue().toString() ;
                    byte[] decodedString = Base64.decode( imgstr, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    prflimg.setImageBitmap(decodedByte);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        FirebaseDatabase.getInstance().getReference().child("users").child(getIntent().getExtras().getString("id"))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        name.setText(snapshot.child("name").getValue().toString());
                        if(!snapshot.child("image").getValue().toString().equals("")){
                            imgstr = snapshot.child("image").getValue().toString() ;
                            byte[] decodedString = Base64.decode( imgstr, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            prflimg.setImageBitmap(decodedByte);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

       /* FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                namestr= snapshot.child("name").getValue().toString() ;
                if(!snapshot.child("image").getValue().toString().equals("")){
                    imgstrr = snapshot.child("image").getValue().toString() ;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        namestr= snapshot.child("name").getValue().toString() ;
                        if(!snapshot.child("image").getValue().toString().equals("")){
                            imgstrr = snapshot.child("image").getValue().toString() ;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mes.getText().toString().equals("")){
                    text = mes.getText().toString() ;
                    HashMap<String , Object> message = new HashMap<>();
                    message.put("senderid" , FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                    message.put("receiverid",getIntent().getExtras().getString("id") );
                    message.put("msg" , text );
                    message.put("time" , new Date());
                    message.put("realtime", ServerValue.TIMESTAMP );



                   /* FirebaseFirestore.getInstance().collection("chat").document(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
                            .collection(getIntent().getExtras().getString("id")).add(message);*/
                    FirebaseDatabase.getInstance().getReference().child("chat").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
                            .child(getIntent().getExtras().getString("key")+getIntent().getExtras().getString("id")).push().setValue(message);


                   /* FirebaseFirestore.getInstance().collection("chat").document(getIntent().getExtras().getString("id"))
                            .collection(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).add(message);*/

                    FirebaseDatabase.getInstance().getReference().child("chat").child(getIntent().getExtras().getString("id"))
                            .child(getIntent().getExtras().getString("key")+FirebaseAuth.getInstance().getCurrentUser().getUid()).push().setValue(message);




               /*     FirebaseDatabase.getInstance().getReference().child("recent").child("plus")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(getIntent().getExtras().getString("id"))){

                                FirebaseDatabase.getInstance().getReference().child("recent").child("plus").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child(getIntent().getExtras().getString("id")).child("msg").setValue(text);

                                FirebaseDatabase.getInstance().getReference().child("recent").child("plus").child(getIntent().getExtras().getString("id"))
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("msg").setValue(text);

                                FirebaseDatabase.getInstance().getReference().child("recent").child("plus").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child(getIntent().getExtras().getString("id")).child("time").setValue(ServerValue.TIMESTAMP);

                                FirebaseDatabase.getInstance().getReference().child("recent").child("plus").child(getIntent().getExtras().getString("id"))
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("time").setValue(ServerValue.TIMESTAMP);

                                FirebaseDatabase.getInstance().getReference().child("recent").child("plus").child(getIntent().getExtras().getString("id"))
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("state").setValue("n");
                                msgslist2.add("done") ;

                            }
                            if ( msgslist2.size()==0 ){*/
                                HashMap<String  ,Object> mp = new HashMap<>();
                                mp.put("name" ,name.getText().toString());
                                mp.put("msg", text );
                                mp.put("state" , "s");
                                mp.put("key" ,getIntent().getExtras().getString("key"));
                                mp.put("id",getIntent().getExtras().getString("id") );
                                mp.put("time", ServerValue.TIMESTAMP  );
                                //  mp.put("image" , imgstr);
                               /* FirebaseFirestore.getInstance().collection("recent").document("plus")
                                        .collection(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
                                        .document(getIntent().getExtras().getString("id")).set(mp);*/
                                FirebaseDatabase.getInstance().getReference().child("recent").child("plus")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child(getIntent().getExtras().getString("key")+getIntent().getExtras().getString("id")).setValue(mp);





                                HashMap<String  ,Object> mp2 = new HashMap<>();
                                mp2.put("name" ,namestr);
                                mp2.put("msg",text);
                                mp2.put("state" , "n");
                    mp2.put("key" ,getIntent().getExtras().getString("key"));
                    mp2.put("id", FirebaseAuth.getInstance().getCurrentUser().getUid().toString() );
                                mp2.put("time",ServerValue.TIMESTAMP );
                                //  mp2.put("image" , imgstrr);
                               /* FirebaseFirestore.getInstance().collection("recent").document("plus")
                                        .collection(getIntent().getExtras().getString("id"))
                                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).set(mp2);*/
                    FirebaseDatabase.getInstance().getReference().child("recent").child("plus")
                            .child(getIntent().getExtras().getString("id"))
                            .child(getIntent().getExtras().getString("key")+FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(mp2);

                         /*   }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }


                    });*/


/*
                 FirebaseFirestore.getInstance().collection("recent").document("plus")
                            .collection(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {


                            for (QueryDocumentSnapshot document : task.getResult()) {

                                if(document.getId().toString().equals(getIntent().getExtras().getString("id"))){
                                    FirebaseFirestore.getInstance().collection("recent").document("plus")
                                            .collection(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
                                            .document(getIntent().getExtras().getString("id"))
                                            .update("msg" , text ) ;


                                    FirebaseFirestore.getInstance().collection("recent").document("plus")
                                            .collection(getIntent().getExtras().getString("id"))
                                            .document(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
                                            .update("msg" , text ) ;


                                    FirebaseFirestore.getInstance().collection("recent").document("plus")
                                            .collection(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
                                            .document(getIntent().getExtras().getString("id"))
                                            .update("time" , FieldValue.serverTimestamp()  ) ;
                                    FirebaseFirestore.getInstance().collection("recent").document("plus")
                                            .collection(getIntent().getExtras().getString("id"))
                                            .document(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
                                            .update("time" , FieldValue.serverTimestamp()  ) ;

                                    FirebaseFirestore.getInstance().collection("recent").document("plus")
                                            .collection(getIntent().getExtras().getString("id"))
                                            .document(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
                                            .update("state" , "n"  ) ;

                                    msgslist2.add("done") ;
                                }

                            }



                            if ( msgslist2.size()==0 ){
                                HashMap<String  ,Object> mp = new HashMap<>();
                                mp.put("name" ,name.getText().toString());
                                mp.put("msg", text );
                                mp.put("state" , "s");
                                mp.put("id",getIntent().getExtras().getString("id") );
                                mp.put("time", FieldValue.serverTimestamp()  );
                                //  mp.put("image" , imgstr);
                                FirebaseFirestore.getInstance().collection("recent").document("plus")
                                        .collection(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
                                        .document(getIntent().getExtras().getString("id")).set(mp);
                                HashMap<String  ,Object> mp2 = new HashMap<>();

                                mp2.put("name" ,namestr);
                                mp2.put("msg",text);
                                mp2.put("state" , "n");
                                mp.put("id", FirebaseAuth.getInstance().getCurrentUser().getUid().toString() );
                                mp2.put("time", FieldValue.serverTimestamp()  );
                                //  mp2.put("image" , imgstrr);
                                FirebaseFirestore.getInstance().collection("recent").document("plus")
                                        .collection(getIntent().getExtras().getString("id"))
                                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).set(mp2);

                            }




                        }



                    });




*/





                }
                mes.setText(null );
            }
        });



















        sendphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //       PickImageDialog.build(new PickSetup()).show((FragmentActivity) v.getContext());

                PickImageDialog.build(new PickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                //  bookImage.setImageURI(r.getUri());
                                CropImage.activity(r.getUri()).setAspectRatio(5, 8)
                                        .start(chat.this);
                            }
                        })
                        .setOnPickCancel(new IPickCancel() {
                            @Override
                            public void onCancelClick() {

                            }
                        }).show(chat.this);
            } ;

        });












    }











    /*private final EventListener<QuerySnapshot> eventListener  =(value, error) -> {
        if (error != null) {
            return;
        }
        if (value != null) {
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    onechat one = new onechat(
                            documentChange.getDocument().getString("senderid"),
                            documentChange.getDocument().getString("receiverid"),
                            documentChange.getDocument().getString("msg"),
                            documentChange.getDocument().getDate("time")
                    );


                    msgslist.add(one);
                }

            }

            adptr.notifyDataSetChanged();
            adptr.notifyItemRangeInserted(msgslist.size(), msgslist.size());
            rv.smoothScrollToPosition(msgslist.size());
prgrsbr.setVisibility(View.INVISIBLE);

        }




    };*/









    public static String getTimeDate(long timestamp){
        try{
            DateFormat dateFormat = getDateTimeInstance();
            Date netDate = (new Date(timestamp));
            return dateFormat.format(netDate);
        } catch(Exception e) {
            return "date";
        }
    }


















  /*  private final EventListener<QuerySnapshot> eventListener4  =(value, error) -> {
        if (error != null) {
            return;
        }
        if (value != null) {
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.MODIFIED) {

                    );


                }

            }

        }




    };
*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                Uri resultUri = result.getUri();


                try {
                    Bitmap bitmap = getResizedBitmap(MediaStore.Images.Media.getBitmap( this.getContentResolver(), resultUri ), 700);

                    text = mes.getText().toString() ;
                    HashMap<String , Object> message = new HashMap<>();
                    message.put("senderid" , FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                    message.put("receiverid",getIntent().getExtras().getString("id") );
                    message.put("msg" , convertBitmapToString(bitmap) );
                    message.put("time" , new Date());
                    message.put("realtime", ServerValue.TIMESTAMP );



                   /* FirebaseFirestore.getInstance().collection("chat").document(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
                            .collection(getIntent().getExtras().getString("id")).add(message);*/
                    FirebaseDatabase.getInstance().getReference().child("chat").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
                            .child(getIntent().getExtras().getString("key")+getIntent().getExtras().getString("id")).push().setValue(message);


                   /* FirebaseFirestore.getInstance().collection("chat").document(getIntent().getExtras().getString("id"))
                            .collection(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).add(message);*/

                    FirebaseDatabase.getInstance().getReference().child("chat").child(getIntent().getExtras().getString("id"))
                            .child(getIntent().getExtras().getString("key")+FirebaseAuth.getInstance().getCurrentUser().getUid()).push().setValue(message);




               /*     FirebaseDatabase.getInstance().getReference().child("recent").child("plus")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(getIntent().getExtras().getString("id"))){

                                FirebaseDatabase.getInstance().getReference().child("recent").child("plus").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child(getIntent().getExtras().getString("id")).child("msg").setValue(text);

                                FirebaseDatabase.getInstance().getReference().child("recent").child("plus").child(getIntent().getExtras().getString("id"))
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("msg").setValue(text);

                                FirebaseDatabase.getInstance().getReference().child("recent").child("plus").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child(getIntent().getExtras().getString("id")).child("time").setValue(ServerValue.TIMESTAMP);

                                FirebaseDatabase.getInstance().getReference().child("recent").child("plus").child(getIntent().getExtras().getString("id"))
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("time").setValue(ServerValue.TIMESTAMP);

                                FirebaseDatabase.getInstance().getReference().child("recent").child("plus").child(getIntent().getExtras().getString("id"))
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("state").setValue("n");
                                msgslist2.add("done") ;

                            }
                            if ( msgslist2.size()==0 ){*/
                    HashMap<String  ,Object> mp = new HashMap<>();
                    mp.put("name" ,name.getText().toString());
                    mp.put("msg", "you sent a photo" );
                    mp.put("state" , "s");
                    mp.put("id",getIntent().getExtras().getString("id") );
                    mp.put("key" ,getIntent().getExtras().getString("key"));

                    mp.put("time", ServerValue.TIMESTAMP  );
                    //  mp.put("image" , imgstr);
                               /* FirebaseFirestore.getInstance().collection("recent").document("plus")
                                        .collection(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
                                        .document(getIntent().getExtras().getString("id")).set(mp);*/
                    FirebaseDatabase.getInstance().getReference().child("recent").child("plus")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child(getIntent().getExtras().getString("key")+getIntent().getExtras().getString("id")).setValue(mp);





                    HashMap<String  ,Object> mp2 = new HashMap<>();
                    mp2.put("name" ,namestr);
                    mp2.put("msg", "photo was sent by "+ namestr);
                    mp2.put("state" , "n");
                    mp.put("id", FirebaseAuth.getInstance().getCurrentUser().getUid().toString() );
                    mp.put("key" ,getIntent().getExtras().getString("key"));

                    mp2.put("time",ServerValue.TIMESTAMP );
                    //  mp2.put("image" , imgstrr);
                               /* FirebaseFirestore.getInstance().collection("recent").document("plus")
                                        .collection(getIntent().getExtras().getString("id"))
                                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).set(mp2);*/
                    FirebaseDatabase.getInstance().getReference().child("recent").child("plus")
                            .child(getIntent().getExtras().getString("id"))
                            .child(getIntent().getExtras().getString("key")+FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(mp2);

                         /*   }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }


                    });*/



                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(chat.this , e.getMessage(), Toast.LENGTH_SHORT).show();
                }





            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
                Toast.makeText( chat.this, error.getMessage(), Toast.LENGTH_SHORT).show();


            }

        }




    }

    public  String convertBitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        String result = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return result;
    }



    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }































}