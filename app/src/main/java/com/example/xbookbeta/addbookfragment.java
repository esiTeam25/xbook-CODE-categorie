package com.example.xbookbeta;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ortiz.touchview.TouchImageView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import uk.co.mgbramwell.geofire.android.GeoFire;


public class addbookfragment extends Fragment {
Spinner categorieSpinner , stateSpinner ;
TouchImageView  bookImage;
String categorie[] ={"select categorie" , "categorie1" , "categorie2" , "categorie3" , "categorie4" , "categorie5", "categorie6", "categorie7", "categorie8", "categorie9", "categorie0"} ;
String state[] = {"select state" , "Fair" ,  "Good"  , "new"};
ArrayAdapter <String> adapter , adapter2 ;
EditText title , description  ;
String imageStringToUpload = null  ;
String prflname , prflimage ;
Boolean gonetosettings = false ;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference myCollection = db.collection("books");
    private GeoFire geoFire = new GeoFire(myCollection);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v = inflater.inflate(R.layout.fragment_addbookfragment, container, false);

      categorieSpinner = v.findViewById(R.id.categorieSpinnerId);
      stateSpinner = v.findViewById(R.id.stateSpinnerId);
      bookImage= v.findViewById(R.id.bookImageId);
      title = v.findViewById(R.id.bookTitleId);
      description = v.findViewById(R.id.bookDescriptionId);
      adapter = new ArrayAdapter<String>(v.getContext() , android.R.layout.simple_list_item_1 , categorie) ;
      adapter2 =new ArrayAdapter<String>(v.getContext() , android.R.layout.simple_list_item_1 , state) ;
      categorieSpinner.setAdapter(adapter);
      stateSpinner.setAdapter(adapter2);











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
                                     .start(getContext(), addbookfragment.this);
                         }
                     })
                     .setOnPickCancel(new IPickCancel() {
                         @Override
                         public void onCancelClick() {

                         }
                     }).show((FragmentActivity) v.getContext());
         } ;

     });
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

        builder.setTitle("Allow location permission");
        builder.setMessage("location permission need to be allowed");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID));
                startActivity(i);
                gonetosettings = true ;

                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });



     v.findViewById(R.id.addBookButton).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
          if(FirstActivity.locationToUpload == null){
              AlertDialog alert = builder.create();
              alert.show();
}
else{
             if(imageStringToUpload == null){
                 Toast.makeText(v.getContext(), "select Image", Toast.LENGTH_SHORT).show();
             }else if( title.getText().toString().equals("")){
                 Toast.makeText(v.getContext(), "please insert title", Toast.LENGTH_SHORT).show();

             }
             else if(categorieSpinner.getSelectedItem().toString().equals("select categorie")){
                 Toast.makeText(v.getContext(), "please select categorie", Toast.LENGTH_SHORT).show();
             }else if(stateSpinner.getSelectedItem().toString().equals("select state")){
                 Toast.makeText(v.getContext(), "please select state", Toast.LENGTH_SHORT).show();

             }else {
                 ProgressDialog wait = new ProgressDialog(v.getContext());
                 wait.setTitle("wait");
                 wait.setMessage("wait");
                 wait.show();
                 HashMap<String, Object> dataaa = new HashMap<>();
                 dataaa.put("title", title.getText().toString());
                dataaa.put("id", FirebaseAuth.getInstance().getCurrentUser().getUid().toString()) ;
                 dataaa.put("categorie", categorieSpinner.getSelectedItem().toString());
                 dataaa.put("state", stateSpinner.getSelectedItem().toString());
                 dataaa.put("image", imageStringToUpload);
                 dataaa.put("lat", FirstActivity.locationToUpload.latitude);
                 dataaa.put("lng" , FirstActivity.locationToUpload.longitude);

                 FirebaseFirestore.getInstance().collection("books").add(dataaa)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                             @Override
                             public void onSuccess(DocumentReference documentReference) {
                                 geoFire.setLocation(documentReference.getId() , FirstActivity.locationToUpload.latitude , FirstActivity.locationToUpload.longitude   );

                                wait.dismiss();

                             }
                         });



             }
  }
   }
     });





        return v ;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(gonetosettings) {
            startActivity(new Intent(getContext(), FirstActivity.class));

        }
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                Uri resultUri = result.getUri();
                   // bookImage = getView().findViewById(R.id.bookImageId);
                   // bookImage.setImageURI(resultUri);
                   // bookImage.setImageBitmap(result.getBitmap());

                try {
                    Bitmap bitmap = getResizedBitmap(MediaStore.Images.Media.getBitmap( getContext().getContentResolver(), resultUri ), 700);

                  imageStringToUpload = convertBitmapToString(bitmap) ;
                    bookImage.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext() , e.getMessage(), Toast.LENGTH_SHORT).show();
                }





            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
                Toast.makeText( getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();


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