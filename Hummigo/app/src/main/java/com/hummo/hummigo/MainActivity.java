package com.hummo.hummigo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity implements SleepDialog.SleepDialogListener {

    CardView header,header2,card1,card2,card3,card4;
    DrawerLayout drawerLayout;
    Toolbar mainToolBar;
    LinearLayout stories;
    ImageView sleep,water;
     TextView sleeptv,watertv;

    String Day;

    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        header=findViewById(R.id.headercard);
        header2=findViewById(R.id.header2);
        card1=findViewById(R.id.card1);
        card2=findViewById(R.id.card2);
        card3=findViewById(R.id.card3);
        card4=findViewById(R.id.card4);
        drawerLayout=findViewById(R.id.drawer_layout);
        stories=findViewById(R.id.stories);
        sleep=findViewById(R.id.imageView5);
        water=findViewById(R.id.imageView3);
        sleeptv=findViewById(R.id.textView5);
        watertv=findViewById(R.id.textView6);




        sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSleepDialog();
            }
        });
        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSleepDialog();
            }
        });

        stories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,FeedActivity.class);
                startActivity(intent);
            }
        });

        ActionBar actionBar;
        actionBar = getSupportActionBar();


        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#56ab2f"));


        actionBar.setBackgroundDrawable(colorDrawable);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.main_drawer_layout);
        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);


        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            String personName = acct.getDisplayName();
            TextView message = (TextView) navigationView.getHeaderView(0).findViewById(R.id.day_tv);
            message.setText(personName);
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();



        }







        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,ChatActivity.class);
                startActivity(intent);
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,MedicineActivity.class);
                startActivity(intent);
            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, TextRecognitionActivity.class);
                startActivity(intent);
            }
        });
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,ExcerciseActivity.class);
                startActivity(intent);
            }
        });


        header.setBackgroundResource(R.drawable.cardviewbg1);


    }

    public void openSleepDialog() {
        SleepDialog sleepDialog = new SleepDialog();
        sleepDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void applyTexts(String username, String password) {
        sleeptv.setText(username);
        watertv.setText(password);
    }





    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {




                case R.id.profile_menu:
                    Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(MainActivity.this,Profile.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.medi_app_menu:
                    Toast.makeText(MainActivity.this, "Medicines", Toast.LENGTH_SHORT).show();
                    Intent intent2= new Intent(MainActivity.this,MedicineActivity.class);
                    startActivity(intent2);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                default:
                    loginOrLogout();

                    drawerLayout.closeDrawer(GravityCompat.START);
            }
            ;
            return false;
        }
    };

    private void loginOrLogout() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            Intent logoutint= new Intent(MainActivity.this,LoginActivity.class);
            startActivity(logoutint);
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Logged Out", Toast.LENGTH_LONG).show();
            recreate();
        }
    }
}