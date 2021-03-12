package com.hummo.hummigo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    CardView header,header2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        header=findViewById(R.id.headercard);
        header2=findViewById(R.id.header2);

        header.setBackgroundResource(R.drawable.cardviewbg1);
    }
}