package com.hummo.hummigo.medicine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hummo.hummigo.MedicineActivity;
import com.hummo.hummigo.R;

import java.util.Calendar;

public class AddMedicine extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private EditText medname,meddesc;
    private NumberPicker numpick;
    private Button savebtn,show;
    private FirebaseDatabase db= FirebaseDatabase.getInstance();
    private DatabaseReference root;


    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        meddesc=findViewById(R.id.description);
        medname=findViewById(R.id.name);
        savebtn=findViewById(R.id.savebtn);
        numpick=findViewById(R.id.numpick);
        numpick.setMaxValue(10);
        numpick.setMinValue(1);

        ActionBar actionBar;
        actionBar = getSupportActionBar();


        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#56ab2f"));


        actionBar.setBackgroundDrawable(colorDrawable);

        show=findViewById(R.id.showbtn);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name= medname.getText().toString();
                String desc= meddesc.getText().toString();
                int priority=numpick.getValue();

                MedicineHelper medihelp= new MedicineHelper(name,desc,priority);

                mAuth = FirebaseAuth.getInstance();

                FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = current_user.getUid();

                root= FirebaseDatabase.getInstance().getReference("Users").child(uid).child("medicine");


                root.child(String.valueOf(priority)).setValue(medihelp).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(AddMedicine.this,"Saved",Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AddMedicine.this, MedicineActivity.class);
                startActivity(intent);

            }
        });

        Button buttonTimePicker = findViewById(R.id.btn_timepicker);
        buttonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        startAlarm(c);
    }
    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        final int id = (int) System.currentTimeMillis();
        PendingIntent appIntent = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_ONE_SHOT);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), appIntent);
    }
}

