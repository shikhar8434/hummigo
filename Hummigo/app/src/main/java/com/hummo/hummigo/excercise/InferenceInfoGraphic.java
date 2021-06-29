package com.hummo.hummigo.excercise;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;

import com.hummo.hummigo.MainActivity;

/** Graphic instance for rendering inference info (latency, FPS, resolution) in an overlay view. */
public class InferenceInfoGraphic extends GraphicOverlay.Graphic {

    private static int TEXT_COLOR = Color.WHITE;
    private static final float TEXT_SIZE = 60.0f;

    private final Paint textPaint;
    private final GraphicOverlay overlay;


    public double angle2 = PoseGraphic.getMyAngle();
    private int angletoint= (int) angle2;
    public String angledouble;
    public static int cardtap= MainActivity.tap;



    public InferenceInfoGraphic(
            GraphicOverlay overlay,
            long frameLatency,
            long detectorLatency,
            @Nullable Integer framesPerSecond) {
        super(overlay);
        this.overlay = overlay;

        textPaint = new Paint();
        textPaint.setColor(TEXT_COLOR);
        textPaint.setTextSize(TEXT_SIZE);
        postInvalidate();
    }

    @Override
    public synchronized void draw(Canvas canvas) {
        float x = TEXT_SIZE * 0.5f;
        float y = TEXT_SIZE * 1.5f;
        angledouble= Double.toString(angle2);

        if(cardtap>4){
            if(angletoint>=170&&angletoint<=190){
                angledouble="Doing Great";
                TEXT_COLOR=Color.GREEN;
            }
            else if(angletoint>=155&&angletoint<=195){
                angledouble="Almost There";
                TEXT_COLOR=Color.YELLOW;
            }
            else{
                angledouble="Keep Trying Mate";
                TEXT_COLOR=Color.RED;
            }
        }
        else {
            if(angletoint>=85&&angletoint<=95){
                angledouble="Doing Great";
                TEXT_COLOR=Color.GREEN;
            }
            else if(angletoint>=70&&angletoint<=110){
                angledouble="Almost There";
                TEXT_COLOR=Color.YELLOW;
            }
            else{
                angledouble="Keep Trying Mate";
                TEXT_COLOR=Color.RED;
            }

        }

        canvas.drawText(
                angledouble
                ,
                x,
                y,
                textPaint);

        // Draw FPS (if valid) and inference latency

    }
}


