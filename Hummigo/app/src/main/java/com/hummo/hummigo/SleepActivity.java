package com.hummo.hummigo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SleepActivity extends AppCompatActivity {
    private Button sleepbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        getSupportActionBar().hide();
        sleepbtn=findViewById(R.id.sleepbtn);
        sleepbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sleepint= new Intent(SleepActivity.this,DailyGoals.class);
                startActivity(sleepint);
            }
        });




    }
}