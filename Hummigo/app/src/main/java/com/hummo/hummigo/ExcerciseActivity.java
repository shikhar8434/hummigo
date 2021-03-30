package com.hummo.hummigo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ExcerciseActivity extends AppCompatActivity {

    public static int tap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercise);

        ImageView image1 = findViewById(R.id.ex1);
        ImageView image2 = findViewById(R.id.ex2);
        ImageView image3 = findViewById(R.id.ex3);
        ImageView image4 = findViewById(R.id.ex4);
        ImageView image5= findViewById(R.id.ex5);
        ImageView image6=findViewById(R.id.ex6);

        ActionBar actionBar;
        actionBar = getSupportActionBar();


        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#56ab2f"));


        actionBar.setBackgroundDrawable(colorDrawable);


        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tap=1;
                Intent intent= new Intent(ExcerciseActivity.this,CameraXLivePreviewActivity.class);
                startActivity(intent);
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tap=2;
                Intent intent2= new Intent(ExcerciseActivity.this,CameraXLivePreviewActivity.class);
                startActivity(intent2);
            }
        });
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tap=3;
                Intent intent3= new Intent(ExcerciseActivity.this,CameraXLivePreviewActivity.class);
                startActivity(intent3);
            }
        });
        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tap=4;
                Intent intent4= new Intent(ExcerciseActivity.this,CameraXLivePreviewActivity.class);
                startActivity(intent4);
            }
        });

        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tap=5;
                Intent intent5= new Intent(ExcerciseActivity.this,CameraXLivePreviewActivity.class);
                startActivity(intent5);

            }
        });
        image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tap=6;
                Intent intent6= new Intent(ExcerciseActivity.this,CameraXLivePreviewActivity.class);
                startActivity(intent6);

            }
        });


    }
}