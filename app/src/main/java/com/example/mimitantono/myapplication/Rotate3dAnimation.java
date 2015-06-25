package com.example.mimitantono.myapplication;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by mimitantono on 23/06/15.
 */

public class Rotate3dAnimation extends Animation {
    private final float x_1;
    private final float x_2;
    private final float y_1;
    private final float y_2;
    private final float z_1;
    private final float z_2;
    private Camera camera;
    private int width = 0;
    private int height = 0;
    private int rotations = 0;

    public Rotate3dAnimation(float x1, float toXDegrees, float fromYDegrees,
                             float toYDegrees, float fromZDegrees, float toZDegrees, int rotations) {
        this.x_1 = x1;
        this.x_2 = toXDegrees;
        this.y_1 = fromYDegrees;
        this.y_2 = toYDegrees;
        this.z_1 = fromZDegrees;
        this.z_2 = toZDegrees;
        this.rotations = rotations / 2;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        this.width = width / 2;
        this.height = height / 2;
        camera = new Camera();
    }

    @Override
    protected void applyTransformation(float time, Transformation t) {
        float xDegrees = x_1 + ((x_2 - x_1) * time * rotations);
        float yDegrees = y_1 + ((y_2 - y_1) * time * rotations);
        float zDegrees = z_1 + ((z_2 - z_1) * time * rotations);

        final Matrix matrix = t.getMatrix();

        camera.save();
        camera.rotate(xDegrees, yDegrees, zDegrees);
        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(-this.width, -this.height);
        matrix.postTranslate(this.width, this.height);
    }

}
