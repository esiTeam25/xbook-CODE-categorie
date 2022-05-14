package com.example.xbookbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ortiz.touchview.TouchImageView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import uk.co.mgbramwell.geofire.android.GeoFire;

public class addbook extends AppCompatActivity {

    Spinner categorieSpinner , stateSpinner ;
    TouchImageView bookImage;
    String categorie[] ={"select categorie" , "categorie1" , "categorie2" , "categorie3" , "categorie4" , "categorie5", "categorie6", "categorie7", "categorie8", "categorie9", "categorie0"} ;
    String state[] = {"select state" , "Fair" ,  "Good"  , "new"};
    ArrayAdapter<String> adapter , adapter2 ;
    EditText title , description  ;
    String imageStringToUpload = null  ;
    String lowimageStringToUpload = null  ;

    String prflname , prflimage ;
    Boolean gonetosettings = false ;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference myCollection = db.collection("books");
    private GeoFire geoFire = new GeoFire(myCollection);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbook);








        categorieSpinner = findViewById(R.id.categorieSpinnerId);
        stateSpinner = findViewById(R.id.stateSpinnerId);
        bookImage= findViewById(R.id.bookImageId);
        title = findViewById(R.id.bookTitleId);
        description = findViewById(R.id.bookDescriptionId);
        adapter = new ArrayAdapter<String>(addbook.this , android.R.layout.simple_list_item_1 , categorie) ;
        adapter2 =new ArrayAdapter<String>(addbook.this , android.R.layout.simple_list_item_1 , state) ;
        categorieSpinner.setAdapter(adapter);
        stateSpinner.setAdapter(adapter2);

        if(FirstActivity.locationToUpload==null){

            findViewById(R.id.nogps).setVisibility(View.VISIBLE);
            findViewById(R.id.allid).setVisibility(View.INVISIBLE);

        }else {

            findViewById(R.id.nogps).setVisibility(View.INVISIBLE);
            findViewById(R.id.allid).setVisibility(View.VISIBLE);

            bookImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //       PickImageDialog.build(new PickSetup()).show((FragmentActivity) v.getContext());

                    PickImageDialog.build(new PickSetup())
                            .setOnPickResult(new IPickResult() {
                                @Override
                                public void onPickResult(PickResult r) {
                                    //  bookImage.setImageURI(r.getUri());
                                    CropImage.activity(r.getUri()).setAspectRatio(5, 8)
                                            .start( addbook.this);
                                }
                            })
                            .setOnPickCancel(new IPickCancel() {
                                @Override
                                public void onCancelClick() {

                                }
                            }).show(addbook.this);
                }

                ;

            });


            findViewById(R.id.addBookButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (imageStringToUpload == null) {
                        Toast.makeText(addbook.this, "select Image", Toast.LENGTH_SHORT).show();
                    } else if (title.getText().toString().equals("")) {
                        Toast.makeText(addbook.this, "please insert title", Toast.LENGTH_SHORT).show();

                    } else if (categorieSpinner.getSelectedItem().toString().equals("select categorie")) {
                        Toast.makeText(addbook.this, "please select categorie", Toast.LENGTH_SHORT).show();
                    } else if (stateSpinner.getSelectedItem().toString().equals("select state")) {
                        Toast.makeText(addbook.this, "please select state", Toast.LENGTH_SHORT).show();

                    } else {
                        ProgressDialog wait = new ProgressDialog(addbook.this);
                        wait.setTitle("wait");
                        wait.setMessage("wait");
                        wait.show();
                        HashMap<String, Object> dataaa = new HashMap<>();
                        dataaa.put("title", title.getText().toString());
                        dataaa.put("id", FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                        dataaa.put("categorie", categorieSpinner.getSelectedItem().toString());
                        dataaa.put("state", stateSpinner.getSelectedItem().toString());
                        dataaa.put("image", imageStringToUpload);
                        dataaa.put("lowimage", lowimageStringToUpload);
                        dataaa.put("description" , description.getText().toString());
                        dataaa.put("likes" , 0) ;
                        dataaa.put("lat", FirstActivity.locationToUpload.latitude);
                        dataaa.put("lng", FirstActivity.locationToUpload.longitude);

                        FirebaseFirestore.getInstance().collection("books").add(dataaa)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        geoFire.setLocation(documentReference.getId(), FirstActivity.locationToUpload.latitude, FirstActivity.locationToUpload.longitude);


                                        DatabaseReference account = FirebaseDatabase.getInstance().getReference().child("users")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                                        account.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                long number = (long) snapshot.child("books").getValue() ;
                                                snapshot.getRef().child("books").setValue(number+1);
                                                wait.dismiss();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });





                                    }
                                });


                    }
                }

            });
        }













    }









    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                Uri resultUri = result.getUri();


                try {
                    Bitmap bitmap = getResizedBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri), 400);

                    imageStringToUpload = convertBitmapToString(bitmap);
                    lowimageStringToUpload = convertBitmapToString(getResizedBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri), 100));
                    bookImage.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(addbook.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
                Toast.makeText(addbook.this, error.getMessage(), Toast.LENGTH_SHORT).show();


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