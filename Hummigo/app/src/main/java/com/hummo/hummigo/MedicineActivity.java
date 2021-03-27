package com.hummo.hummigo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hummo.hummigo.medicine.AddMedicine;
import com.hummo.hummigo.medicine.MedicineAdapter;
import com.hummo.hummigo.medicine.MedicineHelper;

import java.util.ArrayList;

public class MedicineActivity extends AppCompatActivity {

    private FloatingActionButton addfab;

    private RecyclerView medilist;

    private FirebaseDatabase db= FirebaseDatabase.getInstance();
    private DatabaseReference root;
    private MedicineAdapter medadapter;
    private ArrayList<MedicineHelper> list;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        addfab= (FloatingActionButton)findViewById(R.id.addfab);

        medilist=(RecyclerView)findViewById(R.id.medilist);
        medilist.setHasFixedSize(true);
        list=new ArrayList<>();
        medadapter= new MedicineAdapter(this,list);
        medilist.setAdapter(medadapter);
        ActionBar actionBar;
        actionBar = getSupportActionBar();


        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#56ab2f"));


        actionBar.setBackgroundDrawable(colorDrawable);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();

        root= FirebaseDatabase.getInstance().getReference("Users").child(uid).child("medicine");

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    MedicineHelper model= dataSnapshot.getValue(MedicineHelper.class);
                    list.add(model);
                }
                medadapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        medilist.setLayoutManager(new LinearLayoutManager(this));
        addfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fabint= new Intent(MedicineActivity.this, AddMedicine.class);
                startActivity(fabint);
            }
        });
    }
}