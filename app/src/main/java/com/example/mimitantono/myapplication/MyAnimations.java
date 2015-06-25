package com.example.mimitantono.myapplication;

import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Created by mimitantono on 24/06/15.
 */
public class MyAnimations {
    public static AnimationSet newTremblingAnimation() {
        RotateAnimation rotateAnimation =
                new RotateAnimation(-5, 5, Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setRepeatMode(Animation.REVERSE);
        rotateAnimation.setDuration(70);

        TranslateAnimation translateAnimation = new TranslateAnimation(-5, 5, 0, 0);
        translateAnimation.setRepeatCount(Animation.INFINITE);
        translateAnimation.setRepeatMode(Animation.REVERSE);
        translateAnimation.setDuration(50);

        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(translateAnimation);
        return animationSet;
    }
}
