package com.samsung.android.visualeffect.lock.blind;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

/* loaded from: classes.dex */
@SuppressLint("AppCompatCustomView")
public class Blind extends ImageView {
    private int blindWidth;
    private int blindX;
    private float midPoint;

    public Blind(Context context, Bitmap pieceBitmap, int blindX, int blindWidth) {
        super(context);
        this.blindX = blindX;
        this.blindWidth = blindWidth;
        this.midPoint = ((float) blindWidth / 2) + blindX;
        setImageBitmap(pieceBitmap);
    }

    public float getMidPoint() {
        return this.midPoint;
    }

    public int getBlindX() {
        return this.blindX;
    }

    public int getBlindWidth() {
        return this.blindWidth;
    }

    public void changeBitmap(Bitmap bitmap) {
        setImageBitmap(bitmap);
    }
}