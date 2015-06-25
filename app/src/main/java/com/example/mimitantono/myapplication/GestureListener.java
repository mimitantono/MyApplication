package com.example.mimitantono.myapplication;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

class GestureListener extends GestureDetector.SimpleOnGestureListener implements Animation.AnimationListener {

    private ImageView imageView;
    private TextView hint;
    private boolean imageIsVisible = true;
    private AnimationSet tremblingAnimation = MyAnimations.newTremblingAnimation();


    public GestureListener(ImageView imageView, TextView hint) {
        this.imageView = imageView;
        this.hint = hint;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2,
                           float velocityX, float velocityY) {
        int slope = getSlope(e1, e2);
        int[] degrees = getDegrees(slope);
        int rotations = getRotations(e1, e2);

        imageView.startAnimation(getRotationAnimation(degrees, rotations));

        Log.i("Rotations", "" + getRotations(e1, e2));
        return false;
    }

    private AnimationSet getRotationAnimation(int[] degrees, int rotation) {
        AnimationSet sets = new AnimationSet(false);

        Rotate3dAnimation rotateAnimation =
                new Rotate3dAnimation(degrees[0], degrees[1], degrees[2], degrees[3],
                        0, 0, rotation);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setDuration(2000);

        sets.addAnimation(rotateAnimation);
        return sets;
    }

    private int getRotations(MotionEvent e1, MotionEvent e2) {
        long period = e2.getEventTime() - e1.getEventTime();
        double distance = Math.sqrt(Math.pow(e2.getX() - e1.getX(), 2)
                + Math.pow(e2.getY() - e1.getY(), 2));
        return (int) (distance / period);
    }

    private int getSlope(MotionEvent e1, MotionEvent e2) {
        int slope =
                (int) Math.toDegrees(Math.atan2(e1.getY() - e2.getY(), e2.getX() - e1.getX()));
        Log.i("Slope", String.valueOf(slope));
        return slope;
    }

    private int[] getDegrees(int slope) {
        int degrees[] = new int[4];
        if (slope > 45 && slope <= 135) {
            // top
            return new int[]{0, 360, 0, 0};
        } else if (slope >= 135 && slope < 180 || slope < -135 && slope > -180) {
            // left
            return new int[]{0, 0, 0, -360};
        } else if (slope < -45 && slope >= -135) {
            // down
            return new int[]{0, -360, 0, 0};
        } else if (slope > -45 && slope <= 45) {
            // right
            return new int[]{0, 0, 0, 360};
        }

        return degrees;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        imageView.startAnimation(tremblingAnimation);
        super.onLongPress(e);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.i("Motion", "onSingleTapConfirmed");
        imageView.clearAnimation();
        return super.onSingleTapConfirmed(e);
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        AlphaAnimation alphaAnimation;
        if (imageIsVisible) {
            alphaAnimation = new AlphaAnimation(1, 0);
        } else {
            alphaAnimation = new AlphaAnimation(0, 1);
        }
        imageIsVisible = !imageIsVisible;
        alphaAnimation.setDuration(1500);
        alphaAnimation.setAnimationListener(this);
        imageView.startAnimation(alphaAnimation);
        return super.onDoubleTap(e);
    }

    @Override
    public void onAnimationStart(Animation animation) {
        if (imageIsVisible) {
            hint.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (imageIsVisible) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.INVISIBLE);
            hint.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }
}