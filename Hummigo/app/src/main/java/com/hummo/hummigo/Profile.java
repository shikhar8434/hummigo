package com.hummo.hummigo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.mlkit.vision.text.TextRecognition;

public class Profile extends AppCompatActivity {

    AppCompatButton bttn;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private DatabaseReference myRef2=database.getReference();
    private DatabaseReference myRef3=database.getReference();
    private FirebaseAuth mAuth;
    TextView tvprofcal,tvprofwat,tvprofstep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvprofcal=findViewById(R.id.tvcalprof);
        tvprofstep=findViewById(R.id.tvstepprof);
        tvprofwat=findViewById(R.id.tvwatprof);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            String personName = acct.getDisplayName();
            TextView name= findViewById(R.id.name);
            name.setText(personName);
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();



        }




        ActionBar actionBar;
        actionBar = getSupportActionBar();


        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#56ab2f"));


        actionBar.setBackgroundDrawable(colorDrawable);

        bttn=findViewById(R.id.sign_out);
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(Profile.this, LoginActivity.class);
                startActivity(intent);
                FirebaseAuth.getInstance().signOut();


            }
        });

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();
        myRef= FirebaseDatabase.getInstance().getReference("Users").child(uid).child("goals");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String watr_drink= snapshot.child("water_score").getValue().toString();
                watr_drink+=" mL";
                tvprofwat.setText(watr_drink);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "error", Toast.LENGTH_SHORT).show();

            }
        });
        myRef2= FirebaseDatabase.getInstance().getReference("Users").child(uid).child("goals").child("calories");
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String totalcalstr=snapshot.child("totalcalorie").getValue().toString();
                totalcalstr+=" kcal";
                tvprofcal.setText(totalcalstr);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "error", Toast.LENGTH_SHORT).show();

            }
        });
        myRef3= FirebaseDatabase.getInstance().getReference("Users").child(uid).child("goals").child("steps");
        myRef3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String totalstepstr=snapshot.child("stepcount").getValue().toString();
                totalstepstr+=" steps";
                tvprofstep.setText(totalstepstr);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "error", Toast.LENGTH_SHORT).show();

            }
        });





    }
}