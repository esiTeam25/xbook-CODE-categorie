package com.example.xbookbeta;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class CourseGVAdapter extends ArrayAdapter<onebook> implements View.OnClickListener {
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
              bookandpublisherdetails.key = courseModel.getUserid()  ;
              getContext().startActivity(new Intent(getContext() , bookandpublisherdetails.class));
          }
      });

        return listitemView;
    }

    @Override
    public void onClick(View view) {
        getContext().startActivity(new Intent(getContext(), bookandpublisherdetails.class));
    }
}
