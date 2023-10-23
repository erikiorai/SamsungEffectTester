package com.samsung.android.visualeffect.lock.particle;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

public class Particle {
    private int bigRadius = 66;
    private float dx;
    private float dy;
    private int life;
    private Paint paint = new Paint();
    private int rad;
    private int smallRadius = 25;
    private float x;
    private float y;
    private String TAG = "VisualEffectParticleEffect";
    private final float fpsUsual = 1000 / (float) 60; // tie to millis, assume 60fps for usual screens
    private float gravity = 4.0f; // px/usual frame
    private float maxSpeed = 7.0f;
    private int dotAlpha = 200;
    private int randomTotal = 20;
    private boolean isAlive = false;
    private boolean isUnlocked = false;

    private float ratio;

    public Particle(float ratio) {
        this.ratio = ratio;
        this.smallRadius = (int) (smallRadius * ratio);
        this.bigRadius = (int) (bigRadius * ratio);
        this.paint.setAntiAlias(true);
    }

    public void initialize(float x, float y, int color) {
        Random rnd = new Random();
        life = rnd.nextInt(100) + 50;
        float rndTotal = rnd.nextInt(randomTotal) / (float) randomTotal;
        rad = rnd.nextInt(10) == 0 ? (int) (bigRadius * rndTotal) : (int) (smallRadius * rndTotal);
        /*dx = (float) ((this.maxSpeed * Math.random()) - (this.maxSpeed / 2.0f));
        dy = (float) (((this.maxSpeed * Math.random()) - (this.maxSpeed / 2.0f)) - this.gravity);
*/
        isAlive = true;
        isUnlocked = false;
        this.x = x;
        this.y = y;
        paint.setColor(color);
        physSet = false;
    }

    public void move() {
        x += dx;
        y += dy;
    }

    private boolean physSet = false;
    private float millis = 0.0f;
    public void setPhysics(float millis) {
        if (!physSet && !isUnlocked) {
            this.millis = millis;
            life = (int) ((fpsUsual * life) / millis);
            float targetMaxSpeed = fpsUsual / maxSpeed;
            float targetGravity = fpsUsual / gravity; // pix/ms
            float maxSpeed = (millis / targetMaxSpeed) * ratio;
            float gravity = (millis / targetGravity) * ratio;
            dx = (float) ((maxSpeed * Math.random()) - (maxSpeed / 2.0f));
            dy = (float) (((maxSpeed * Math.random()) - (maxSpeed / 2.0f)) - gravity);
            physSet = true;
        }
    }

    public void unlock(float speed) {
        isUnlocked = true;
        dx *= speed;
        dy *= speed;
        life = (int) ((fpsUsual * 19) / millis);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int getTop() {
        return (int) (y - rad);
    }

    public int getBottom() {
        return (int) (y + rad);
    }

    public int getLeft() {
        return (int) (x - rad);
    }

    public int getRight() {
        return (int) (x + rad);
    }

    public void draw(Canvas canvas) {
        int alphaReduceStartFrame = (int) (isUnlocked ? ((fpsUsual * 20) / millis) : ((fpsUsual * 30) / millis));
        int alpha = life < alphaReduceStartFrame ? (dotAlpha * life) / alphaReduceStartFrame : dotAlpha;
        paint.setAlpha(alpha);
        canvas.drawCircle(x, y, rad, paint);
        if (life <= 0) {
            isAlive = false;
        } else {
            life--;
        }
    }
}