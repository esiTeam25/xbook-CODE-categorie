package com.example.xbookbeta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.ortiz.touchview.TouchImageView;

public class bookandpublisherdetails extends AppCompatActivity {
static  Bitmap bookimage = null ;
    TouchImageView bookImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookandpublisherdetails);
        bookImage = findViewById(R.id.bookImageId);
        bookImage.setImageBitmap(bookimage);
        if (getIntent().getExtras().get("id").equals(FirebaseAuth.getInstance().getUid().toString())) {
            findViewById(R.id.sendmessageid).setVisibility(View.INVISIBLE);
            findViewById(R.id.locationid).setVisibility(View.INVISIBLE);
            findViewById(R.id.phonecallid).setVisibility(View.INVISIBLE);
        }

            findViewById(R.id.sendmessageid).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(), chat.class);
                    i.putExtra("id", getIntent().getExtras().getString("id"));

                    i.putExtra("x", "2");
                    startActivity(i);
                }
            });
            findViewById(R.id.locationid).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(bookandpublisherdetails.this, mapactivity.class).putExtra("id", getIntent().getExtras().getString("id")));
                }
            });
            findViewById(R.id.phonecallid).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:0558585327"));
                    startActivity(intent);
                }
            });
        }

}