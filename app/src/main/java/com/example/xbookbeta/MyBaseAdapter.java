package com.example.xbookbeta;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyBaseAdapter extends BaseAdapter {
    Context c;
    ArrayList<onebook> items ;

    MyBaseAdapter(Context c, ArrayList<onebook> books)
    {
        this.c = c;
        items = books;
    }

    @Override
    public int getCount()
    {
        return items.size();
    }

    @Override
    public Object getItem(int i)
    {
        return null;
    }

    @Override
    public long getItemId(int i)
    {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.onegrid, null);
        }
        ImageView imageView = view.findViewById(R.id.bookImageId);
        TextView courseTV = view.findViewById(R.id.titleid);


        onebook courseModel = items.get(i);
        byte[] decodedString2 = Base64.decode(courseModel.getBookimage(), Base64.DEFAULT);
        Bitmap decodedByte2 = BitmapFactory.decodeByteArray(decodedString2, 0, decodedString2.length);
        imageView.setImageBitmap(decodedByte2);
        courseTV.setText(courseModel.getTitle());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.startActivity(new Intent(c , bookandpublisherdetails.class));
                bookandpublisherdetails.key = courseModel.getUserid()  ;

            }
        });
        return view;
    }
}