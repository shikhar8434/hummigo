package com.hummo.hummigo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DailyGoals extends AppCompatActivity {

    GridView contrigrid;
    CardView watrcard,calcard,stepscard,sleepcard;
    TextView tvwatscore,tvcal,tvsteps;


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private DatabaseReference myRef2=database.getReference();
    private DatabaseReference myRef3=database.getReference();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_goals);

        ActionBar actionBar;
        actionBar = getSupportActionBar();


        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#56ab2f"));


        actionBar.setBackgroundDrawable(colorDrawable);

        contrigrid= findViewById(R.id.contri_grid);
        ArrayList<GridModel> courseModelArrayList = new ArrayList<GridModel>();

        for (int i=0;i<14;i++)
        {
            for(int j=0;j<6;j++)
            {
                if(j%3==0){
                    courseModelArrayList.add(new GridModel(R.drawable.mygrad3));
                }
                else if(i%3==0&&j%2==0)
                {
                    courseModelArrayList.add(new GridModel(R.drawable.mygrad2));
                }
                else{
                courseModelArrayList.add(new GridModel(R.drawable.mygrad));}
            }
        }
        GridAdapt adapter = new GridAdapt(this, courseModelArrayList);
        contrigrid.setAdapter(adapter);

        watrcard=findViewById(R.id.watrcard);
        watrcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent watrintent= new Intent(DailyGoals.this,WaterTrack.class);
                startActivity(watrintent);
            }
        });
        calcard=findViewById(R.id.calcard);
        calcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent calint= new Intent(DailyGoals.this,CalorieCount.class);
                startActivity(calint);
            }
        });
        stepscard=findViewById(R.id.stepscard);
        stepscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stepint= new Intent(DailyGoals.this,DebugActivity.class);
                startActivity(stepint);
            }
        });
        sleepcard=findViewById(R.id.sleepcard);
        sleepcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sleepint= new Intent(DailyGoals.this,SleepActivity.class);
                startActivity(sleepint);
            }
        });

        tvwatscore=findViewById(R.id.tv_watscore);
        tvcal=findViewById(R.id.tvcal);
        tvsteps=findViewById(R.id.tvstep);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();
        myRef= FirebaseDatabase.getInstance().getReference("Users").child(uid).child("goals");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("water_score").exists()){
                String watr_drink= snapshot.child("water_score").getValue().toString();
                watr_drink+=" mL";
                tvwatscore.setText(watr_drink);}
                else {
                    Toast.makeText(DailyGoals.this, "add water data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DailyGoals.this, "error", Toast.LENGTH_SHORT).show();

            }
        });
        myRef2= FirebaseDatabase.getInstance().getReference("Users").child(uid).child("goals");
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("calories").exists()){
                String totalcalstr=snapshot.child("calories").child("totalcalorie").getValue().toString();
                totalcalstr+=" kcal";
                tvcal.setText(totalcalstr);}
                else {
                    Toast.makeText(DailyGoals.this, "add calories data", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DailyGoals.this, "error", Toast.LENGTH_SHORT).show();

            }
        });
        myRef3= FirebaseDatabase.getInstance().getReference("Users").child(uid).child("goals");
        myRef3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("steps").exists()){
                String totalstepstr=snapshot.child("steps").child("stepcount").getValue().toString();
                totalstepstr+=" steps";
                tvsteps.setText(totalstepstr);}
                else {
                    Toast.makeText(DailyGoals.this, "add steps data ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DailyGoals.this, "error", Toast.LENGTH_SHORT).show();

            }
        });


        

    }
}
