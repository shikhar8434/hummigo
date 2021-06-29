package com.hummo.hummigo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hummo.hummigo.dailygoals.CalorieHelp;

public class CalorieCount extends AppCompatActivity {

    private CardView cardcarb,cardprot,cardfat;
    private Button calsave;

    private int progress1=0,progress2=0,progress3=0,protcal,carbcal,fatcal,totalcal;
    private ProgressBar progressBar1,progressBar2,progressBar3;

    private FirebaseDatabase db= FirebaseDatabase.getInstance();
    private DatabaseReference root;
    private TextView tvcarb,tvprot,tvfat;
    ImageView calback;


    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_count);
        getSupportActionBar().hide();

        cardprot=findViewById(R.id.card_prot);
        cardcarb=findViewById(R.id.card_carb);
        cardfat=findViewById(R.id.card_fat);
        tvcarb=findViewById(R.id.carbtv);
        tvprot=findViewById(R.id.prottv);
        tvfat=findViewById(R.id.fattv);
        calback=findViewById(R.id.calback);
        calback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stepbackint= new Intent(CalorieCount.this,DailyGoals.class);
                startActivity(stepbackint);
            }
        });





        progressBar1=findViewById(R.id.cprogress_bar1);
        progressBar2=findViewById(R.id.cprogress_bar2);
        progressBar3=findViewById(R.id.cprogress_bar3);

        cardprot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(progress1<=90){
                    progress1+=10;
                    protcal= progress1*20;
                    totalcal+=protcal;
                    tvprot.setText(""+protcal+"kcal");


                    updateProgressBar1();
                }
            }
        });

        cardcarb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(progress2<=90){
                    progress2+=10;
                    carbcal=progress2*20;
                    totalcal+=carbcal;
                    tvcarb.setText(""+carbcal+"kcal");

                    updateProgressBar2();
                }
            }
        });
        cardfat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(progress3<=90){
                    progress3+=10;
                    fatcal= progress3*20;
                    totalcal+=fatcal;
                    tvfat.setText(""+fatcal+"kcal");

                    updateProgressBar3();
                }
            }
        });


        calsave=findViewById(R.id.calsavebtn);
        calsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalorieHelp calorieHelp= new CalorieHelp(totalcal);
                mAuth = FirebaseAuth.getInstance();

                FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = current_user.getUid();
                root= FirebaseDatabase.getInstance().getReference("Users").child(uid).child("goals").child("calories");
                root.setValue(calorieHelp).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(CalorieCount.this,"Saved",Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });





    }

    private void updateProgressBar1() {
        progressBar1.setProgress(progress1);

    }
    private void updateProgressBar2() {
        progressBar2.setProgress(progress2);

    }
    private void updateProgressBar3() {
        progressBar3.setProgress(progress3);

    }
}