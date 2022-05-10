package com.example.xbookbeta;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class CourseGVAdapter extends ArrayAdapter<onebook> {
    public CourseGVAdapter(@NonNull Context context, ArrayList<onebook> courseModelArrayList) {
        super(context, 0, courseModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.onegrid, parent, false);
        }
        onebook courseModel = getItem(position);
        TextView courseTV = listitemView.findViewById(R.id.titleid);

        ImageView courseIV = listitemView.findViewById(R.id.bookImageId);
        courseTV.setText(courseModel.getTitle());
        byte[] decodedString2 = Base64.decode(courseModel.getBookimage(), Base64.DEFAULT);
        Bitmap decodedByte2 = BitmapFactory.decodeByteArray(decodedString2, 0, decodedString2.length);
        courseIV.setImageBitmap(decodedByte2);
        courseIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mapactivity.point = new LatLng( courseModel.getLatitude() , courseModel.getLongitude());
              // mapactivity.name = courseModel.getTitle() ;


               // bookandpublisherdetails.bookimage = courseModel.getBookimage() ;
                //mapactivity.image =  courseModel.getBookimage() ;
              //  Intent i = ;
                //i.putExtra("id" , courseModel.getUserid());
                bookandpublisherdetails.key = courseModel.getUserid();
                view.getContext().startActivity(new Intent( view.getContext() , bookandpublisherdetails.class ));

            }
        });
        return listitemView;
    }
}
