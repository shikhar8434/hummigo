package com.hummo.hummigo.excercise;

import static java.lang.Math.atan2;
import static java.lang.Math.max;
import static java.lang.Math.min;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import com.google.common.primitives.Ints;
import com.google.mlkit.vision.common.PointF3D;
import com.hummo.hummigo.ExcerciseActivity;
import com.hummo.hummigo.excercise.GraphicOverlay;
import com.hummo.hummigo.excercise.GraphicOverlay.Graphic;
import com.google.mlkit.vision.pose.Pose;
import com.google.mlkit.vision.pose.PoseLandmark;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/** Draw the detected pose in preview. */
public class PoseGraphic extends Graphic {

    private static final float DOT_RADIUS = 8.0f;
    private static final float IN_FRAME_LIKELIHOOD_TEXT_SIZE = 30.0f;
    private static final float STROKE_WIDTH = 10.0f;


    public static Pose pose;
    private final boolean showInFrameLikelihood;
    private final boolean visualizeZ;
    private final boolean rescaleZForVisualization;
    private float zMin = Float.MAX_VALUE;
    private float zMax = Float.MIN_VALUE;

    private final Paint leftPaint;
    private final Paint rightPaint;
    private final Paint whitePaint;

    public static double rightElbowAngle;
    public static double leftElbowAngle;
    public static double leftKneeAngle;
    public static double rightKneeAngle;
    public static double rightLegAngle;
    public static double leftLegAngle;
    public static int cardtap= ExcerciseActivity.tap;


    PoseGraphic(
            GraphicOverlay overlay,
            Pose pose,
            boolean showInFrameLikelihood,
            boolean visualizeZ,
            boolean rescaleZForVisualization) {
        super(overlay);
        this.pose = pose;
        this.showInFrameLikelihood = showInFrameLikelihood;
        this.visualizeZ = visualizeZ;
        this.rescaleZForVisualization = rescaleZForVisualization;

        whitePaint = new Paint();
        whitePaint.setStrokeWidth(STROKE_WIDTH);
        whitePaint.setColor(Color.WHITE);
        whitePaint.setTextSize(IN_FRAME_LIKELIHOOD_TEXT_SIZE);
        leftPaint = new Paint();
        leftPaint.setStrokeWidth(STROKE_WIDTH);
        leftPaint.setColor(Color.GREEN);
        rightPaint = new Paint();
        rightPaint.setStrokeWidth(STROKE_WIDTH);
        rightPaint.setColor(Color.YELLOW);


    }

    @Override
    public void draw(Canvas canvas) {
        List<PoseLandmark> landmarks = pose.getAllPoseLandmarks();
        if (landmarks.isEmpty()) {
            return;
        }

        // Draw all the points
        for (PoseLandmark landmark : landmarks) {
            drawPoint(canvas, landmark, whitePaint);
            if (visualizeZ && rescaleZForVisualization) {
                zMin = min(zMin, landmark.getPosition3D().getZ());
                zMax = max(zMax, landmark.getPosition3D().getZ());
            }
        }

        PoseLandmark leftShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER);
        PoseLandmark rightShoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER);
        PoseLandmark leftElbow = pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW);
        PoseLandmark rightElbow = pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW);
        PoseLandmark leftWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST);
        PoseLandmark rightWrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST);
        PoseLandmark leftHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP);
        PoseLandmark rightHip = pose.getPoseLandmark(PoseLandmark.RIGHT_HIP);
        PoseLandmark leftKnee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE);
        PoseLandmark rightKnee = pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE);
        PoseLandmark leftAnkle = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE);
        PoseLandmark rightAnkle = pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE);

        PoseLandmark leftPinky = pose.getPoseLandmark(PoseLandmark.LEFT_PINKY);
        PoseLandmark rightPinky = pose.getPoseLandmark(PoseLandmark.RIGHT_PINKY);
        PoseLandmark leftIndex = pose.getPoseLandmark(PoseLandmark.LEFT_INDEX);
        PoseLandmark rightIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_INDEX);
        PoseLandmark leftThumb = pose.getPoseLandmark(PoseLandmark.LEFT_THUMB);
        PoseLandmark rightThumb = pose.getPoseLandmark(PoseLandmark.RIGHT_THUMB);
        PoseLandmark leftHeel = pose.getPoseLandmark(PoseLandmark.LEFT_HEEL);
        PoseLandmark rightHeel = pose.getPoseLandmark(PoseLandmark.RIGHT_HEEL);
        PoseLandmark leftFootIndex = pose.getPoseLandmark(PoseLandmark.LEFT_FOOT_INDEX);
        PoseLandmark rightFootIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_FOOT_INDEX);

        drawLine(canvas, leftShoulder, rightShoulder, whitePaint);
        drawLine(canvas, leftHip, rightHip, whitePaint);

        // Left body
        drawLine(canvas, leftShoulder, leftElbow, leftPaint);
        drawLine(canvas, leftElbow, leftWrist, leftPaint);
        drawLine(canvas, leftShoulder, leftHip, leftPaint);
        drawLine(canvas, leftHip, leftKnee, leftPaint);
        drawLine(canvas, leftKnee, leftAnkle, leftPaint);
        drawLine(canvas, leftWrist, leftThumb, leftPaint);
        drawLine(canvas, leftWrist, leftPinky, leftPaint);
        drawLine(canvas, leftWrist, leftIndex, leftPaint);
        drawLine(canvas, leftIndex, leftPinky, leftPaint);
        drawLine(canvas, leftAnkle, leftHeel, leftPaint);
        drawLine(canvas, leftHeel, leftFootIndex, leftPaint);

        // Right body
        drawLine(canvas, rightShoulder, rightElbow, rightPaint);
        drawLine(canvas, rightElbow, rightWrist, rightPaint);
        drawLine(canvas, rightShoulder, rightHip, rightPaint);
        drawLine(canvas, rightHip, rightKnee, rightPaint);
        drawLine(canvas, rightKnee, rightAnkle, rightPaint);
        drawLine(canvas, rightWrist, rightThumb, rightPaint);
        drawLine(canvas, rightWrist, rightPinky, rightPaint);
        drawLine(canvas, rightWrist, rightIndex, rightPaint);
        drawLine(canvas, rightIndex, rightPinky, rightPaint);
        drawLine(canvas, rightAnkle, rightHeel, rightPaint);
        drawLine(canvas, rightHeel, rightFootIndex, rightPaint);

        rightElbowAngle = getAngle(
                pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER),
                pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW),
                pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST));

        leftElbowAngle = getAngle(
                pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER),
                pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW),
                pose.getPoseLandmark(PoseLandmark.LEFT_WRIST));
        leftKneeAngle = getAngle(
                pose.getPoseLandmark(PoseLandmark.LEFT_HIP),
                pose.getPoseLandmark(PoseLandmark.LEFT_KNEE),
                pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE));
        leftLegAngle = getAngle(
                pose.getPoseLandmark(PoseLandmark.LEFT_HIP),
                pose.getPoseLandmark(PoseLandmark.LEFT_KNEE),
                pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE));
        rightKneeAngle = getAngle(
                pose.getPoseLandmark(PoseLandmark.RIGHT_HIP),
                pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE),
                pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE));
        rightLegAngle= getAngle(
                pose.getPoseLandmark(PoseLandmark.RIGHT_HIP),
                pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE),
                pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE));










        // Draw inFrameLikelihood for all points
        if (showInFrameLikelihood) {
            for (PoseLandmark landmark : landmarks) {
                canvas.drawText(
                        String.format(Locale.US, "%.2f", landmark.getInFrameLikelihood()),
                        translateX(landmark.getPosition().x),
                        translateY(landmark.getPosition().y),
                        whitePaint);
            }
        }
    }
    public static double getMyAngle(){

        if(cardtap==1){
            return rightElbowAngle;
        }
        else if(cardtap==2){
            return leftElbowAngle;
        }
        else if(cardtap==3){
            return rightKneeAngle;
        }
        else if(cardtap==4){
            return leftKneeAngle;
        }
        else if(cardtap==5){
            return rightLegAngle;
        }
        else if(cardtap==6){
            return leftLegAngle;
        }

        return 0;

    }

    void drawPoint(Canvas canvas, PoseLandmark landmark, Paint paint) {
        PointF point = landmark.getPosition();
        canvas.drawCircle(translateX(point.x), translateY(point.y), DOT_RADIUS, paint);
    }

    void drawLine(Canvas canvas, PoseLandmark startLandmark, PoseLandmark endLandmark, Paint paint) {
        // When visualizeZ is true, sets up the paint to draw body line in different colors based on
        // their z values.
        if (visualizeZ) {
            PointF3D start = startLandmark.getPosition3D();
            PointF3D end = endLandmark.getPosition3D();

            // Gets the range of z value.
            float zLowerBoundInScreenPixel;
            float zUpperBoundInScreenPixel;

            if (rescaleZForVisualization) {
                zLowerBoundInScreenPixel = min(-0.001f, scale(zMin));
                zUpperBoundInScreenPixel = max(0.001f, scale(zMax));
            } else {
                // By default, assume the range of z value in screen pixel is [-canvasWidth, canvasWidth].
                float defaultRangeFactor = 1f;
                zLowerBoundInScreenPixel = -defaultRangeFactor * canvas.getWidth();
                zUpperBoundInScreenPixel = defaultRangeFactor * canvas.getWidth();
            }

            // Gets average z for the current body line
            float avgZInImagePixel = (start.getZ() + end.getZ()) / 2;
            float zInScreenPixel = scale(avgZInImagePixel);

            if (zInScreenPixel < 0) {
                // Sets up the paint to draw the body line in red if it is in front of the z origin.
                // Maps values within [zLowerBoundInScreenPixel, 0) to [255, 0) and use it to control the
                // color. The larger the value is, the more red it will be.
                int v = (int) (zInScreenPixel / zLowerBoundInScreenPixel * 255);
                v = Ints.constrainToRange(v, 0, 255);
                paint.setARGB(255, 255, 255 - v, 255 - v);
            } else {
                // Sets up the paint to draw the body line in blue if it is behind the z origin.
                // Maps values within [0, zUpperBoundInScreenPixel] to [0, 255] and use it to control the
                // color. The larger the value is, the more blue it will be.
                int v = (int) (zInScreenPixel / zUpperBoundInScreenPixel * 255);
                v = Ints.constrainToRange(v, 0, 255);
                paint.setARGB(255, 255 - v, 255 - v, 255);
            }

            canvas.drawLine(
                    translateX(start.getX()),
                    translateY(start.getY()),
                    translateX(end.getX()),
                    translateY(end.getY()),
                    paint);

        } else {
            PointF start = startLandmark.getPosition();
            PointF end = endLandmark.getPosition();
            canvas.drawLine(
                    translateX(start.x), translateY(start.y), translateX(end.x), translateY(end.y), paint);
        }
    }
    static double getAngle(PoseLandmark firstPoint, PoseLandmark midPoint, PoseLandmark lastPoint) {
        double result =
                Math.toDegrees(
                        atan2(lastPoint.getPosition().y - midPoint.getPosition().y,
                                lastPoint.getPosition().x - midPoint.getPosition().x)
                                - atan2(firstPoint.getPosition().y - midPoint.getPosition().y,
                                firstPoint.getPosition().x - midPoint.getPosition().x));
        result = Math.abs(result); // Angle should never be negative
        if (result > 180) {
            result = (360.0 - result); // Always get the acute representation of the angle
        }
        return result;
    }


}
