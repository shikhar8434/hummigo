package com.hummo.hummigo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;
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
import com.hummo.hummigo.dailygoals.Stephelp;


public class StepsCount extends AppCompatActivity {
    private Handler mHandler;
    private Runnable _timer1;
    private int stepCounter = 0;
    private int lastStep = 0;
    private boolean showedGoalReach = false;
    private ProgressBar stepsprogress;
    private double stepperc;
    private TextView stepprogtv;
    private Button stepsave;

    private FirebaseDatabase db= FirebaseDatabase.getInstance();
    private DatabaseReference root;
    private FirebaseAuth mAuth;
    ImageView imbackstep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_count);
        stepsprogress=findViewById(R.id.step_progress);
        stepsave=findViewById(R.id.stepsave);
        getSupportActionBar().hide();

        imbackstep=findViewById(R.id.imbackstep);
        imbackstep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stepbackint= new Intent(StepsCount.this,DailyGoals.class);
                startActivity(stepbackint);
            }
        });


        stepCounter = (int) DebugActivity.mStepCounter;
        mHandler = new Handler();
        startRepeatingTask();

        stepsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Stephelp stephelp= new Stephelp(stepCounter);
                mAuth = FirebaseAuth.getInstance();

                FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = current_user.getUid();
                root= FirebaseDatabase.getInstance().getReference("Users").child(uid).child("goals").child("steps");
                root.setValue(stephelp).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(StepsCount.this,"Saved",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    private void updateView(){
        if(DebugActivity.mStepCounter > stepCounter) {
            stepCounter = (int)DebugActivity.mStepCounter;
            if(stepCounter >= 500 && !showedGoalReach){
                showedGoalReach = true;
                Context context = getApplicationContext();
                CharSequence text = "Good Job! You've reached your goal!";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            TextView stepCountStr = (TextView) this.findViewById(R.id.tvsteps);
            stepCountStr.setText(new String("Step Count: " + stepCounter));
            TextView progressText = (TextView) this.findViewById(R.id.tvstepprog);
            progressText.setText(new String("Step Goal: " + 500 + ". Progress: " + stepCounter + " / 500"));






            ProgressBar progressBar = (ProgressBar) this.findViewById(R.id.step_progress);
            ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", lastStep, stepCounter); //animate only from last known step to current step count
            animation.setDuration(5000); // in milliseconds
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();
            lastStep = stepCounter;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        final Context context = this;

        //noinspection SimplifiableIfStatement
        if (id == R.id.addfab) {
            Intent intent = new Intent(context, DebugActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                updateView();
            } finally {
                mHandler.postDelayed(mStatusChecker, 500);
            }
        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }
}
