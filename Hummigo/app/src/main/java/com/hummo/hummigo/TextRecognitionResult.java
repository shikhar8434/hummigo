package com.hummo.hummigo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.util.Locale;

public class TextRecognitionResult extends AppCompatActivity {

    private BottomAppBar botAppBar;
    private EditText tv;
    TextToSpeech t1;
    FloatingActionButton fab;

    public StringBuilder restext= TextRecognitionActivity.result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_recognition_result);

        botAppBar = findViewById(R.id.bottomMenuBar);
        //  attach menu to your BottomAppBar
        botAppBar.replaceMenu(R.menu.bottom_menu);
        tv=findViewById(R.id.textspace);
        tv.setText(restext);
        fab=findViewById(R.id.selAllFab);

        ActionBar actionBar;
        actionBar = getSupportActionBar();


        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#56ab2f"));


        actionBar.setBackgroundDrawable(colorDrawable);



        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toSpeak = tv.getText().toString();
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });




        botAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_zoom_out:
                        Toast.makeText(TextRecognitionResult.this, "Clicked edit menu item", Toast.LENGTH_SHORT).show();
                        tv.setTextSize(getResources().getDimension(R.dimen.textsizeout));



                        return true;
                    case R.id.action_save:
                        Toast.makeText(TextRecognitionResult.this, "Clicked save menu item", Toast.LENGTH_SHORT).show();
                        if(Build.VERSION.SDK_INT> Build.VERSION_CODES.M)
                        {
                            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED)
                            {
                                String[] parmission={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                requestPermissions(parmission,1000);
                            }
                            else savepdf();
                        }
                        else savepdf();

                        return true;
                    case R.id.action_share:
                        Toast.makeText(TextRecognitionResult.this, "Clicked share menu item", Toast.LENGTH_SHORT).show();
                        String s = tv.getText().toString();
                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, s);
                        startActivity(Intent.createChooser(sharingIntent, "Share text via"));
                        return true;
                    case R.id.action_zoom_in:
                        Toast.makeText(TextRecognitionResult.this, "Clicked zoom menu item", Toast.LENGTH_SHORT).show();
                        tv.setTextSize(getResources().getDimension(R.dimen.textsize));
                        return true;
                }
                return false;
            }
        });



    }

    private  void savepdf()
    {
        Document doc=new Document();
        String mfile= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            mfile = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        }
        String mfilepath= Environment.getExternalStorageDirectory()+"/"+mfile+".pdf";
        Font smallBold=new Font(Font.FontFamily.TIMES_ROMAN,24 ,Font.BOLD);
        try{
            PdfWriter.getInstance(doc,new FileOutputStream(mfilepath));
            doc.open();
            String mtext=tv.getText().toString();
            doc.addAuthor("Shikhar");
            doc.add(new Paragraph(mtext,smallBold));
            doc.close();
            Toast.makeText(this, ""+mfile+".pdf"+" is saved to "+mfilepath, Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(this,"This is Error msg : " +e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case  1000:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    savepdf();
                }
                else Toast.makeText(this, "permission denied..", Toast.LENGTH_SHORT).show();
        }
    }
}