package com.samsung.android.visualeffect.lock.particle;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

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
    private float fpsRatio;
    private float gravity = 4.0f; // px/usual frame
    private float maxSpeed = 7.0f;
    private int dotAlpha = 200;
    private int randomTotal = 20;
    private boolean isAlive = false;
    private boolean isUnlocked = false;

    private float ratio;

    public Particle(float ratio, float fpsRatio) {
        this.ratio = ratio;
        this.fpsRatio = fpsRatio;
        maxSpeed = (maxSpeed * this.fpsRatio) * ratio;
        gravity = (gravity * this.fpsRatio) * ratio;
        this.smallRadius = (int) (smallRadius * ratio);
        this.bigRadius = (int) (bigRadius * ratio);
        this.paint.setAntiAlias(true);
    }

    public void initialize(float x, float y, int color) {
        Random rnd = new Random();
        life = (int) (rnd.nextInt((int) ((100 / fpsRatio))) + (50 / fpsRatio));
        float rndTotal = rnd.nextInt(randomTotal) / (float) randomTotal;
        rad = rnd.nextInt(10) == 0 ? (int) (bigRadius * rndTotal) : (int) (smallRadius * rndTotal);
        dx = (float) ((maxSpeed * Math.random()) - (maxSpeed / (2.0f)));
        dy = (float) (((maxSpeed * Math.random()) - (maxSpeed / (2.0f)) - gravity));

        isAlive = true;
        isUnlocked = false;
        this.x = x;
        this.y = y;
        paint.setColor(color);
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public void unlock(float speed) {
        isUnlocked = true;
        dx *= speed;
        dy *= speed;
        life = (int) (19 / fpsRatio);
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
        int alphaReduceStartFrame = (int) (isUnlocked ? (20 / fpsRatio) : (30 / fpsRatio));
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