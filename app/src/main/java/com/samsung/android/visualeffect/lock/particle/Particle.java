package com.samsung.android.visualeffect.lock.particle;

import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.Random;

/* loaded from: classes.dex */
public class Particle {
    private int bigRadius;
    private float dx;
    private float dy;
    private int life;
    private Paint paint;
    private int rad;
    private int smallRadius;
    private float x;
    private float y;
    private String TAG = "VisualEffectParticleEffect";
    private float gravity = 4.0f;
    private float maxSpeed = 7.0f;
    private int dotAlpha = 200;
    private int randomTotal = 20;
    private boolean isAlive = false;
    private boolean isUnlocked = false;

    public Particle(float ratio) {
        this.smallRadius = 25;
        this.bigRadius = 66;
        this.gravity *= ratio;
        this.maxSpeed *= ratio;
        this.smallRadius = (int) (this.smallRadius * ratio);
        this.bigRadius = (int) (this.bigRadius * ratio);
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
    }

    public void initialize(float x, float y, int color) {
        Random rnd = new Random();
        this.life = rnd.nextInt(100) + 50;
        float rndTotal = rnd.nextInt(this.randomTotal) / (float) this.randomTotal;
        this.rad = rnd.nextInt(10) == 0 ? (int) (this.bigRadius * rndTotal) : (int) (this.smallRadius * rndTotal);
        this.dx = (float) ((this.maxSpeed * Math.random()) - (this.maxSpeed / 2.0f));
        this.dy = (float) (((this.maxSpeed * Math.random()) - (this.maxSpeed / 2.0f)) - this.gravity);
        this.isAlive = true;
        this.isUnlocked = false;
        this.x = x;
        this.y = y;
        this.paint.setColor(color);
    }

    public void move() {
        this.x += this.dx;
        this.y += this.dy;
    }

    public void unlock(float speed) {
        this.isUnlocked = true;
        this.dx *= speed;
        this.dy *= speed;
        this.life = 19;
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    public int getTop() {
        return (int) (this.y - this.rad);
    }

    public int getBottom() {
        return (int) (this.y + this.rad);
    }

    public int getLeft() {
        return (int) (this.x - this.rad);
    }

    public int getRight() {
        return (int) (this.x + this.rad);
    }

    public void draw(Canvas canvas) {
        int alphaReduceStartFrame = this.isUnlocked ? 20 : 30;
        int alpha = this.life < alphaReduceStartFrame ? (this.dotAlpha * this.life) / alphaReduceStartFrame : this.dotAlpha;
        this.paint.setAlpha(alpha);
        canvas.drawCircle(this.x, this.y, this.rad, this.paint);
        if (this.life <= 0) {
            this.isAlive = false;
        } else {
            this.life--;
        }
    }
}