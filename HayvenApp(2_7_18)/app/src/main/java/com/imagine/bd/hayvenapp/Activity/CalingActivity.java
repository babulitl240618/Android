package com.imagine.bd.hayvenapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.imagine.bd.hayvenapp.R;

public class CalingActivity extends AppCompatActivity {
    ImageView imageView, imageView1, imgHangUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.caling_activity);
        imageView = (ImageView) findViewById(R.id.imgProfile);
        imageView1 = (ImageView) findViewById(R.id.notificationChat);
        imgHangUp = (ImageView) findViewById(R.id.imgHangUp);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CalingActivity.this, EvryoneNavigetGroupActivity.class));
                //Intent i = new Intent(context, Contacts.class);
                //startActivity(i);
            }
        });
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        imgHangUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
