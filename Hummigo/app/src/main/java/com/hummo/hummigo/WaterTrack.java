package com.hummo.hummigo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hummo.hummigo.dailygoals.WaterHelp;
import com.hummo.hummigo.medicine.AddMedicine;
import com.hummo.hummigo.medicine.MedicineHelper;

public class WaterTrack extends AppCompatActivity {
    private int progress = 0;
    private Button buttonIncrement;
    private Button buttonDecrement;
    private Button watrsavebtn;
    ProgressBar progressBar;
    TextView textView,waterml;

    private FirebaseDatabase db= FirebaseDatabase.getInstance();
    private DatabaseReference root;


    private FirebaseAuth mAuth;

    private static int maxscore=5000;
    private static int perc_scored=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_track);
        getSupportActionBar().hide();

       buttonIncrement=findViewById(R.id.inc_btn);
       buttonDecrement=findViewById(R.id.dec_btn);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        textView = (TextView) findViewById(R.id.text_view_progress);
        waterml=findViewById(R.id.waterml);
        watrsavebtn=findViewById(R.id.watr_save_btn);

        // when clicked on buttonIncrement progress in increased by 10%
        buttonIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(progress<=90){
                    progress+=10;
                    int wq= progress*40;
                    if(wq<1500)
                    {
                        perc_scored=0;
                    }
                    else if(wq>=1500&&wq<=2500)
                    {
                        perc_scored+=50;
                    }
                    else if(wq>2500&&wq<=5000)
                    {
                        perc_scored+=100;
                    }
                    else {
                        perc_scored+=100;
                    }
                    waterml.setText(""+wq+"mL");
                    updateProgressBar();
                }
            }
        });

        buttonDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (progress>=10)
                {
                    progress-=10;
                    int wq2= progress*40;
                    if(wq2<1500)
                    {
                        perc_scored=0;
                    }
                    else if(wq2>=1500&&wq2<=2500){
                        perc_scored+=50;
                    }
                    else if(wq2>2500&&wq2<=5000)
                    {
                        perc_scored+=100;
                    }
                    else {
                        perc_scored+=100;
                    }
                    waterml.setText(""+wq2+"mL");
                    updateProgressBar();
                }
            }
        });

        watrsavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                WaterHelp waterHelp= new WaterHelp(perc_scored);
                mAuth = FirebaseAuth.getInstance();

                FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = current_user.getUid();


                root= FirebaseDatabase.getInstance().getReference("Users").child(uid).child("goals");
                root.setValue(waterHelp).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(WaterTrack.this,"Saved",Toast.LENGTH_SHORT).show();
                    }
                });




            }
        });




    }

    // updateProgressBar() method sets
    // the progress of ProgressBar in text
    private void updateProgressBar() {
        progressBar.setProgress(progress);
        textView.setText(String.valueOf(progress));
    }
}

