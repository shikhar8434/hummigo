package com.hummo.hummigo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.hummo.hummigo.stories.Slide;
import com.hummo.hummigo.stories.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager2 viewPager2;
    private List<Slide> slideList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        createSlides();
        //viewpager = findViewById(R.id.viewPagerSlider);
        viewPager2 = findViewById(R.id.viewPagerSlider);
        viewPagerAdapter = new ViewPagerAdapter(slideList, FeedActivity.this);
        //viewpager.setAdapter(viewPagerAdapter);
        viewPager2.setPageTransformer(new DepthPageTransformer());
        //viewPager2.setOverScrollMode(View.OVER_SCROLL_NEVER);
        viewPager2.setAdapter(viewPagerAdapter);

        ActionBar actionBar;
        actionBar = getSupportActionBar();


        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#56ab2f"));


        actionBar.setBackgroundDrawable(colorDrawable);


    }

    private void createSlides() {
        Slide slide1 = new Slide();
        slide1.setAuthor("Seemant");
        slide1.setDate("24th Oct");
        slide1.setDesc("fdasdf dasfkajdflas dsfkajdflkdsfj kldfjalsdkfjlkdsf jlkadsjfs" +
                "dsfjklasdfj dfkjsldflsdf djfklsdjflsdkaf dkfjdslfksd jlkdfjsd fdsf" +
                "fjlksdflksdf dfjlkdfjldskf jdlfsdlfkdf jdlkfjsdlfkjdlf slkdfjlksdfjdlfk" +
                "dsfjlasfkdsaf dfjdlskfjdlskf");
        slide1.setHeading("This is a demo post straight from Android Studio");
        slide1.setId(1);
        slide1.setLikesCount(15);
        slide1.setImageURL("https://images.pexels.com/photos/1112290/pexels-photo-1112290.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");


        slideList.add(slide1);

        Slide slide2 = new Slide();
        slide2.setAuthor("Shekhar");
        slide2.setDate("24th Oct");
        slide2.setDesc("fdasdf dasfkajdflas dsfkajdflkdsfj kldfjalsdkfjlkdsf jlkadsjfs" +
                "dsfjklasdfj dfkjsldflsdf djfklsdjflsdkaf dkfjdslfksd jlkdfjsd fdsf" +
                "fjlksdflksdf dfjlkdfjldskf jdlfsdlfkdf jdlkfjsdlfkjdlf slkdfjlksdfjdlfk" +
                "dsfjlasfkdsaf dfjdlskfjdlskf");
        slide2.setHeading("This is a demo post straight from Android Studio");
        slide2.setId(2);
        slide2.setLikesCount(15);
        slide2.setImageURL("https://images.pexels.com/photos/1112290/pexels-photo-1112290.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");

        slideList.add(slide2);

        Slide slide3 = new Slide();
        slide3.setAuthor("Shekhar");
        slide3.setDate("24th Oct");
        slide3.setDesc("fdasdf dasfkajdflas dsfkajdflkdsfj kldfjalsdkfjlkdsf jlkadsjfs" +
                "dsfjklasdfj dfkjsldflsdf djfklsdjflsdkaf dkfjdslfksd jlkdfjsd fdsf" +
                "fjlksdflksdf dfjlkdfjldskf jdlfsdlfkdf jdlkfjsdlfkjdlf slkdfjlksdfjdlfk" +
                "dsfjlasfkdsaf dfjdlskfjdlskf");
        slide3.setHeading("This is a demo post straight from Android Studio");
        slide3.setId(3);
        slide3.setLikesCount(15);
        slide3.setImageURL("https://images.pexels.com/photos/1112290/pexels-photo-1112290.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");

        slideList.add(slide3);

        Slide slide4 = new Slide();
        slide4.setAuthor("Shekhar");
        slide4.setDate("24th Oct");
        slide4.setDesc("fdasdf dasfkajdflas dsfkajdflkdsfj kldfjalsdkfjlkdsf jlkadsjfs" +
                "dsfjklasdfj dfkjsldflsdf djfklsdjflsdkaf dkfjdslfksd jlkdfjsd fdsf" +
                "fjlksdflksdf dfjlkdfjldskf jdlfsdlfkdf jdlkfjsdlfkjdlf slkdfjlksdfjdlfk" +
                "dsfjlasfkdsaf dfjdlskfjdlskf");
        slide4.setHeading("This is a demo post straight from Android Studio");
        slide4.setId(4);
        slide4.setLikesCount(15);
        slide4.setImageURL("https://images.pexels.com/photos/1112290/pexels-photo-1112290.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");

        slideList.add(slide4);
    }

    @RequiresApi(21)
    public static class DepthPageTransformer implements ViewPager2.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1f);
                view.setTranslationX(0f);
                view.setTranslationZ(0f);
                view.setScaleX(1f);
                view.setScaleY(1f);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationY(pageWidth * -position);
                // Move it behind the left page
                view.setTranslationZ(-1f);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }

}